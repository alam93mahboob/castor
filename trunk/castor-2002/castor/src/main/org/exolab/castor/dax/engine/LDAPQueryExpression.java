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


package org.exolab.castor.dax.engine;


import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import org.exolab.castor.persist.spi.QueryExpression;


/**
 *
 * @author <a href="arkin@exoffice.com">Assaf Arkin</a>
 * @version $Revision$ $Date$
 */
public class LDAPQueryExpression
    implements QueryExpression
{


    private String _filter;


    LDAPQueryExpression( String filter )
    {
        _filter = filter;
    }
    
    public void setDistinct( boolean distinct )
    {
    }

    public void addColumn( String tableName, String columnName )
    {
    }

    public void addSelect( String selectClause )
    {
    }

    public void addTable( String tableName )
    {
    }
    
    public void addParameter( String tableName, String columnName, String condOp )
    {
    }


    public void addCondition( String tableName, String columnName,
                              String condOp, String value )
    {
    }

    public String encodeColumn( String tableName, String columnName )
    {
	// ! not yet implemented => ToDo !
	return tableName + "." + columnName;
    }


    public void addInnerJoin( String leftTable, String leftColumn,
                              String rightTable, String rightColumn )
    {
    }


    public void addOuterJoin( String leftTable, String leftColumn,
                              String rightTable, String rightColumn )
    {
    }


    public void addWhereClause( String where )
    {
    }


    public void addOrderClause( String where )
    {
    }


    public String getStatement( boolean lock )
    {
        return _filter;
    }


    public String toString()
    {
        return getStatement( false );
    }


    public Object clone()
    {
        LDAPQueryExpression clone;

        clone = new LDAPQueryExpression( _filter );
        return clone;
    }


}


