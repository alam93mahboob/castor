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
 */


package org.exolab.castor.jdo.drivers;


import java.util.Enumeration;
import java.util.Vector;

import org.exolab.castor.jdo.engine.JDBCSyntax;
import org.exolab.castor.persist.spi.PersistenceFactory;


/**
 * QueryExpression for Informix 7.x.
 *
 * @author <a href="mailto:santiago.arriaga@catnet.com.mx">Santiago Arriaga</a>
 */
public final class InformixQueryExpression
    extends JDBCQueryExpression
{


    public InformixQueryExpression( PersistenceFactory factory )
    {
        super( factory );
    }


    public String getStatement( boolean lock )
    {
        StringBuffer sql = new StringBuffer(128);

        addSelectClause(sql);
        addFromClause(sql);
        boolean first = addJoinClause(sql);
        addWhereClause( sql, first );
        addOrderByClause(sql);
        addForUpdateClause(sql, lock);
 
        return sql.toString();
    }

    private void addSelectClause(StringBuffer buffer)
    {
        buffer.append( JDBCSyntax.Select );
        if ( _distinct )
          buffer.append( JDBCSyntax.Distinct );

        if ( _select == null )  
          buffer.append( getColumnList() );
        else
          buffer.append( _select ).append(" ");
    }
    
    private void addFromClause(StringBuffer buffer)
    {
        buffer.append( JDBCSyntax.From );

        Enumeration tables = getFromTables();
        while ( tables.hasMoreElements() )
        {
            buffer.append( (String) tables.nextElement() );
            if ( tables.hasMoreElements() )
                buffer.append( JDBCSyntax.TableSeparator );
        }
    }

    /**
     * This method returns an enumeration of the tables that are included in
     * the from clause of a join, the resulting enumeration includes the
     * necessary outer join syntax if needed
     */
    private Enumeration getFromTables()
    {
        Vector vector = new Vector();
        Vector outerTables = getOuterTables();
        String table = null;

        for(Enumeration enum = _tables.elements(); enum.hasMoreElements();)
        {
            table = (String)enum.nextElement();
            if (outerTables.contains(table))
                vector.addElement("OUTER " + _factory.quoteName(table));
            else
                vector.addElement(_factory.quoteName(table));
        }

        return vector.elements();
    }

    /**
     * This method obtains which are the tables on which an outer join is being
     * performed from the _joins collection. Just the right table in an outer
     * join is needed to create the syntax of an informix outer join
     */
    private Vector getOuterTables()
    {
        Vector tables = new Vector();
        Join join = null;

        for ( int i = 0 ; i < _joins.size() ; ++i )
        {
            join = (Join) _joins.elementAt( i );
            if (join.outer)
                tables.addElement(join.rightTable);
        }

        return tables;
    }

    private boolean addJoinClause(StringBuffer buffer)
    {
        boolean first = true;

        for ( int i = 0 ; i < _joins.size() ; ++i )
        {
            if ( first )
            {
                buffer.append( JDBCSyntax.Where );
                first = false;
            }
            else
                buffer.append( JDBCSyntax.And );

            addJoin(buffer, (Join) _joins.elementAt( i ));
        }

        return first;
    }

    private void addJoin(StringBuffer buffer, Join join)
    {
        for ( int j = 0 ; j < join.leftColumns.length ; ++j )
        {
            if ( j > 0 )
                buffer.append( JDBCSyntax.And );

            buffer.append
              ( quoteTableAndColumn( join.leftTable, join.leftColumns[j] ) );
            buffer.append( OpEquals );
            buffer.append
              ( quoteTableAndColumn( join.rightTable, join.rightColumns[j] ) );
        }
    }

    private String quoteTableAndColumn(String table, String column)
    {
        return _factory.quoteName
            (table + JDBCSyntax.TableColumnSeparator + column);
    }

    private void addOrderByClause(StringBuffer buffer)
    {
        if ( _order != null )
          buffer.append(JDBCSyntax.OrderBy).append(_order);
    }

    private void addForUpdateClause(StringBuffer buffer, boolean lock)
    {
        // Use FOR UPDATE to lock selected tables.
        if ( lock )
            buffer.append( " FOR UPDATE" );
    }

}


