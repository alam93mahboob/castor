/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 1999 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id$
 */


package org.exolab.castor.jdo.drivers;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.exolab.castor.persist.spi.KeyGenerator;
import org.exolab.castor.persist.spi.QueryExpression;
import org.exolab.castor.persist.spi.PersistenceFactory;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.engine.JDBCSyntax;
import org.exolab.castor.util.Messages;

/**
 * MAX key generator.
 * @author <a href="on@ibis.odessa.ua">Oleg Nitz</a>
 * @version $Revision$ $Date$
 * @see MaxKeyGeneratorFactory
 */
public final class MaxKeyGenerator implements KeyGenerator
{
    private static final BigDecimal ONE = new BigDecimal( 1 );

    private final PersistenceFactory _factory;
    
    /**
     * Initialize the MAX key generator.
     */
    public MaxKeyGenerator( PersistenceFactory factory )
    {
        _factory = factory;
    }

    /**
     * Generate a new key for the specified table as "MAX(primary_key) + 1".
     *
     * If there is no records in the table, then the value 1 is returned.
     *
     * @param conn An open connection within the given transaction
     * @param tableName The table name
     * @param primKeyName The primary key name
     * @param props A temporary replacement for Principal object
     * @return A new key
     * @throws PersistenceException An error occured talking to persistent
     *  storage
     */
    public Object generateKey( Connection conn, String tableName, String primKeyName,
            Properties props )
            throws PersistenceException
    {
        String sql;
        String pk;
        Statement stmt = null;
        ResultSet rs;
        QueryExpression query;
        Object identity = null;

        try {
            // Create SQL sentence of the form
            // "SELECT pk FROM table WHERE pk=(SELECT MAX(pk) FROM table)"
            // with database-dependent keyword for lock
            query = _factory.getQueryExpression();
            query.addColumn( tableName, primKeyName);
            pk = tableName + JDBCSyntax.TableColumnSeparator + primKeyName;
            query.addCondition( tableName, primKeyName, QueryExpression.OpEquals,
                    "(SELECT MAX(" + pk + ") FROM " + tableName + ")");

            // SELECT and put lock on the last record
            sql = query.getStatement( true );

            stmt = conn.createStatement();
            rs = stmt.executeQuery( sql );

            if ( rs.next() ) {
                identity = rs.getBigDecimal( 1 ).add( ONE );
            } else {
                identity = ONE;
            }
        } catch ( SQLException ex ) {
            throw new PersistenceException( Messages.format(
                    "persist.keyGenSQL", ex.toString() ) );
        } finally {
            if ( stmt != null ) {
                try {
                    stmt.close();
                } catch ( SQLException ex ) {
                }
            }
        }
        if (identity == null) {
            throw new PersistenceException( Messages.format(
                    "persist.keyGenOverflow", getClass().getName() ) );
        }
        return identity;
    }

    /**
     * Is key generated before INSERT? 
     */
    public boolean isBeforeInsert() {
        return true;
    }

    /**
     * Is key generated in the same connection as INSERT?
     */
    public boolean isInSameConnection() {
        return true;
    }
}

