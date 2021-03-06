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
 *    permission of Intalio, Inc.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Intalio, Inc. Exolab is a registered
 *    trademark of Intalio, Inc.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY INTALIO, INC. AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * INTALIO, INC. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 1999 (C) Intalio, Inc. All Rights Reserved.
 *
 * $Id$
 */


package org.exolab.castor.jdo.engine;

import java.io.Reader;
import java.io.Writer;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;
import org.exolab.castor.util.Messages;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.mapping.TypeConvertor;
import org.exolab.castor.util.MimeBase64Encoder;
import org.exolab.castor.util.MimeBase64Decoder;



/**
 * @author <a href="arkin@intalio.com">Assaf Arkin</a>
 * @version $Revision$ $Date$
 */
public final class SQLTypes
{


    /**
     * Separator between words in SQL name.
     */
    private static final char SQLWordSeparator = '_';


    /**
     * Separators between type name and parameter, e.g. "char[01]"
     */
    private static final char LeftParamSeparator = '[';


    private static final char RightParamSeparator = ']';


    /**
     * Returns the Java type from the SQL type name.
     *
     * @param sqlTypeName SQL type name (e.g. numeric)
     * @return The suitable Java type
     * @throws MappingException The SQL type is not recognized.
     */
    public static Class typeFromName( String sqlTypeName )
        throws MappingException
    {
        int sep;

        sep = sqlTypeName.indexOf( LeftParamSeparator );
        if ( sep >= 0 )
            sqlTypeName = sqlTypeName.substring( 0, sep );

        for ( int i = 0 ; i < _typeInfos.length ; ++i ) {
            if ( sqlTypeName.equals( _typeInfos[ i ].sqlTypeName ) )
                return _typeInfos[ i ].javaType;
        }
        throw new MappingException( "jdo.sqlTypeNotSupported", sqlTypeName );
    }


    /**
     * Returns the Java type from the SQL type name.
     *
     * @param sqlTypeName SQL type name (e.g. numeric)
     * @return The suitable Java type
     * @throws MappingException The SQL type is not recognized.
     */
    public static int sqlTypeFromName( String sqlTypeName )
        throws MappingException
    {
        int sep;

        sep = sqlTypeName.indexOf( LeftParamSeparator );
        if ( sep >= 0 )
            sqlTypeName = sqlTypeName.substring( 0, sep );

        for ( int i = 0 ; i < _typeInfos.length ; ++i ) {
            if ( sqlTypeName.equals( _typeInfos[ i ].sqlTypeName ) )
                return _typeInfos[ i ].sqlType;
        }
        throw new MappingException( "jdo.sqlTypeNotSupported", sqlTypeName );
    }


    /**
     * Extracts parameter for type convertor from the name of the SQL type
     * of the form "SQL_type.domain".
     * If the type is not parameterized, returns null.
     *
     * @param sqlTypeName SQL type name (e.g. char[01])
     * @return Parameter (e.g. "01") or null
     */
    public static String paramFromName( String sqlTypeName )
    {
        int left;
        int right;

        left = sqlTypeName.indexOf( LeftParamSeparator );
        right = sqlTypeName.indexOf( RightParamSeparator );
        if ( right < 0 )
            right = sqlTypeName.length();

        if ( left >= 0 )
            return sqlTypeName.substring( left + 1, right );
        else
            return null;
    }


    /**
     * Returns the Java type from the SQL type.
     *
     * @param sqlType SQL type name (see JDBC API)
     * @return The suitable Java type
     * @throws MappingException The SQL type is not recognized.
     */
    public static Class typeFromSQLType( int sqlType )
        throws MappingException
    {
        for ( int i = 0 ; i < _typeInfos.length ; ++i ) {
            if ( sqlType == _typeInfos[ i ].sqlType )
                return _typeInfos[ i ].javaType;
        }
        throw new MappingException( "jdo.sqlTypeNotSupported", new Integer( sqlType ) );
    }


    /**
     * Returns the SQL type from the specified Java type. Returns <tt>OTHER</tt>
     * if the Java type has no suitable SQL type mapping. The argument
     * <tt>sqlType</tt> must be the return value from a previous call to
     * {@link #typeFromSQLType} or {@link #typeFromName}.
     *
     * @param javaType The Java class of the SQL type
     * @return SQL type from the specified Java type
     */
    public static int getSQLType( Class javaType )
    {
        for ( int i = 0 ; i < _typeInfos.length ; ++i ) {
            if ( javaType.isAssignableFrom( _typeInfos[ i ].javaType ) )
                return _typeInfos[ i ].sqlType;
        }
        return java.sql.Types.OTHER;
    }


    /**
     * Convert from Java name to SQL name. Performs trivial conversion
     * by lowering case of all letters and adding underscore between
     * words, a word is identifier as starting with an upper case. Only
     * the last part of the Java name (following the last period) is used.
     * <p>
     * For example:
     * </ul>
     * <li>prodId becomes prod_id (field -> column)
     * <li>jdbc.ProdGroup becomes prod_group (object -> table)
     * <li>hobbies.getALife becomes get_a_life (compound field -> column)
     * </ul>
     *
     * @param javaName The Java identifier name
     * @return An equivalent SQL identifier name
     */
    public static String javaToSqlName( String javaName )
    {
        StringBuffer sql;
        int          i;
        char         ch;
        boolean      wasLower;

        // Get only the last part of the Java name (whether it's
        // class name with package, or field name with parent)
        if ( javaName.indexOf( '.' ) > 0 ) {
            javaName = javaName.substring( javaName.lastIndexOf( '.' ) + 1 );
        }

        sql = new StringBuffer( javaName.length() );
        wasLower = false;
        for ( i = 0 ; i < javaName.length() ; ++i ) {
            ch = javaName.charAt( i );
            // Our potential break point is an upper case letter
            // signalling the next word (thus must not be the first)
            if ( i > 0 && Character.isUpperCase( ch ) ) {
                // New word: previous letter was lower case
                if ( wasLower )
                    sql.append( SQLWordSeparator );
                else
                    // New word: next letter is lower case
                    if ( i < javaName.length() - 1 &&
                         Character.isLowerCase( javaName.charAt( i + 1 ) ) )
                        sql.append( SQLWordSeparator );
            }
            wasLower = Character.isLowerCase( ch );
            sql.append( Character.toLowerCase( ch ) );
        }
        return sql.toString();
    }


    /**
     * Convert from SQL name to Java name. Performs trivial conversion
     * by treating each underscore as a word separator and converting the
     * first letter of each word to upper case. If a scope is specified,
     * the scope is prepended to the Java name separated by a period.
     * <p>
     * For example:
     * </ul>
     * <li>prod_id, false becomes prodId (column -> field)
     * <li>prod_group, true becomes ProdGroup (table -> object)
     * <li>get_a_life, false, "product" becomes product.getALife (column -> field)
     * </ul>
     *
     * @param sqlName The SQL identifier name
     * @param className True if class name (first letter must be upper case)
     * @param scope Optional scope preceding name (package name, compound field)
     * @return An equivalent Java identifier name
     */
    public static String sqlToJavaName( String sqlName, boolean className, String scope )
    {
        StringBuffer java;
        int          i;

        java = new StringBuffer( sqlName.length() );
        if ( scope != null )
            java.append( scope ).append( '.' );
        for ( i = 0 ; i < sqlName.length() ; ++i ) {
            if ( i == 0 && className ) {
                java.append( Character.toUpperCase( sqlName.charAt( i ) ) );
            } else  if ( sqlName.charAt( i ) == SQLWordSeparator ) {
                ++i;
                if ( i < sqlName.length() )
                    java.append( Character.toUpperCase( sqlName.charAt( i ) ) );
            } else {
                java.append( Character.toLowerCase( sqlName.charAt( i ) ) );
            }
        }
        return java.toString();
    }


    public static Object getObject( ResultSet rs, int index, int sqlType )
            throws SQLException
    {
        Object value;
        long longVal;
        int intVal;
        boolean boolVal;
        double doubleVal;
        float floatVal;
        short shortVal;
        byte byteVal;

        switch ( sqlType ) {
        case Types.CHAR:
        case Types.VARCHAR:
        case Types.LONGVARCHAR:
            return rs.getString(index);
        case Types.DECIMAL:
        case Types.NUMERIC:
            return rs.getBigDecimal( index );
        case Types.INTEGER:
            intVal = rs.getInt( index );
            return ( rs.wasNull() ? null : new Integer( intVal ) );
        case Types.TIME:
            return rs.getTime( index );
        case Types.DATE:
            return rs.getDate( index );
        case Types.TIMESTAMP:
            return rs.getTimestamp( index );
        case Types.FLOAT:
        case Types.DOUBLE:
            doubleVal = rs.getDouble( index );
            return ( rs.wasNull() ? null : new Double( doubleVal ) );
        case Types.REAL:
            floatVal = rs.getFloat( index );
            return ( rs.wasNull() ? null : new Float( floatVal ) );
        case Types.SMALLINT:
            shortVal = rs.getShort( index );
            return ( rs.wasNull() ? null : new Short( shortVal ) );
        case Types.TINYINT:
            byteVal = rs.getByte( index );
            return ( rs.wasNull() ? null : new Byte( byteVal ) );
        case Types.LONGVARBINARY:
        case Types.VARBINARY:
        case Types.BINARY:
            return rs.getBytes(index);
        case Types.BLOB:
            Blob blob = rs.getBlob( index );
            return (blob == null ? null :  blob.getBinaryStream());
        case Types.CLOB:
            return rs.getClob( index );
        case Types.BIGINT:
            longVal = rs.getLong( index );
            return ( rs.wasNull() ? null : new Long( longVal ) );
        case Types.BIT:
            boolVal = rs.getBoolean( index );
            return ( rs.wasNull() ? null : new Boolean( boolVal ) );
        default:
            value = rs.getObject( index );
            return ( rs.wasNull()? null : value );
        }
    }


    public static void setObject( PreparedStatement stmt, int index, Object value, int sqlType )
            throws SQLException
    {
        if (value == null) {
            stmt.setNull( index, sqlType );
        } else {
            // Special processing for BLOB and CLOB types, because they are mapped by Castor to
            // java.io.InputStream and java.io.Reader, respectively,
            // while JDBC driver expects java.sql.Blob and java.sql.Clob.
            switch ( sqlType ) {
            case Types.BLOB:
                try {
                    InputStream stream = (InputStream) value;
                    stmt.setBinaryStream(index, stream, stream.available());
                } catch (IOException ex) {
                    throw new SQLException(ex.toString());
                }
                break;
            case Types.CLOB:
                Clob clob = (Clob) value;
                stmt.setCharacterStream(index, clob.getCharacterStream(),
                        (int) Math.min(clob.length(), Integer.MAX_VALUE));
                break;
            default:
                stmt.setObject(index, value, sqlType);
                break;
            }
        }
    }


    static class TypeInfo
    {
        final int    sqlType;

        final String sqlTypeName;

        final Class  javaType;

        TypeInfo( int sqlType, String sqlTypeName, Class javaType )
        {
            this.sqlType     = sqlType;
            this.sqlTypeName = sqlTypeName;
            this.javaType  = javaType;
        }

    }


    /**
     * List of all the SQL types supported by Castor JDO.
     */
    static TypeInfo[] _typeInfos = new TypeInfo[] {
        new TypeInfo( java.sql.Types.BIT,           "bit",           java.lang.Boolean.class ),
        new TypeInfo( java.sql.Types.TINYINT,       "tinyint",       java.lang.Byte.class ),
        new TypeInfo( java.sql.Types.SMALLINT,      "smallint",      java.lang.Short.class ),
        new TypeInfo( java.sql.Types.INTEGER,       "integer",       java.lang.Integer.class ),
        new TypeInfo( java.sql.Types.BIGINT,        "bigint",        java.lang.Long.class ),
        new TypeInfo( java.sql.Types.FLOAT,         "float",         java.lang.Double.class ),
        new TypeInfo( java.sql.Types.DOUBLE,        "double",        java.lang.Double.class ),
        new TypeInfo( java.sql.Types.REAL,          "real",          java.lang.Float.class ),
        new TypeInfo( java.sql.Types.NUMERIC,       "numeric",       java.math.BigDecimal.class ),
        new TypeInfo( java.sql.Types.DECIMAL,       "decimal",       java.math.BigDecimal.class ),
        new TypeInfo( java.sql.Types.CHAR,          "char",          java.lang.String.class ),
        new TypeInfo( java.sql.Types.VARCHAR,       "varchar",       java.lang.String.class ),
        new TypeInfo( java.sql.Types.LONGVARCHAR,   "longvarchar",   java.lang.String.class ),
        new TypeInfo( java.sql.Types.DATE,          "date",          java.sql.Date.class ),
        new TypeInfo( java.sql.Types.TIME,          "time",          java.sql.Time.class ),
        new TypeInfo( java.sql.Types.TIMESTAMP,     "timestamp",     java.sql.Timestamp.class ),
        new TypeInfo( java.sql.Types.BINARY,        "binary",        byte[].class ),
        new TypeInfo( java.sql.Types.VARBINARY,     "varbinary",     byte[].class ),
        new TypeInfo( java.sql.Types.LONGVARBINARY, "longvarbinary", byte[].class ),
        new TypeInfo( java.sql.Types.OTHER,         "other",         java.lang.Object.class ),
        new TypeInfo( java.sql.Types.JAVA_OBJECT,   "javaobject",    java.lang.Object.class ),
        new TypeInfo( java.sql.Types.BLOB,          "blob",          java.io.InputStream.class ),
        new TypeInfo( java.sql.Types.CLOB,          "clob",          java.sql.Clob.class ),
    };


    /**
     * Returns a type convertor. A type convertor can be used to convert
     * an object from Java type <tt>fromType</tt> to Java type <tt>toType</tt>.
     *
     * @param fromType The Java type to convert from
     * @param toType The Java type to convert to
     * @throws MappingException No suitable convertor was found
     */
    public static TypeConvertor getConvertor( Class fromType, Class toType )
        throws MappingException
    {
        // first seek for exact match
        // TODO: the closest possible match
        for ( int i = 0 ; i < _typeConvertors.length ; ++i ) {
            if ( _typeConvertors[ i ].fromType.equals( fromType ) &&
                 toType.equals( _typeConvertors[ i ].toType ) )
                return _typeConvertors[ i ].convertor;
        }

        // else seek for any match
        for ( int i = 0 ; i < _typeConvertors.length ; ++i ) {
            if ( _typeConvertors[ i ].fromType.isAssignableFrom( fromType ) &&
                 toType.isAssignableFrom( _typeConvertors[ i ].toType ) )
                return _typeConvertors[ i ].convertor;
        }
        throw new MappingException( "mapping.noConvertor", fromType.getName(), toType.getName() );
    }


    /**
     * Information used to locate a type convertor.
     */
    static class TypeConvertorInfo
    {

        /**
         *  The type being converted to.
         */
        final Class toType;

        /**
         * The type being converted from.
         */
        final Class fromType;

        /**
         * The convertor.
         */
        final TypeConvertor convertor;

        TypeConvertorInfo( Class fromType, Class toType, TypeConvertor convertor )
        {
            this.fromType  = fromType;
            this.toType    = toType;
            this.convertor = convertor;
        }

    }


    /**
     * Date format used by the date convertor.
     */
    private static DateFormat _dateFormat = new SimpleDateFormat();


    /**
     * Date format used by the date convertor when nonempy parameter
     * is specified.
     */
    private static SimpleDateFormat _paramDateFormat = new SimpleDateFormat();

    /**
     * Date format used by the double->date convertor.
     */
    private static DecimalFormat _decimalFormat = new DecimalFormat("#################0");


    /**
     * List of all the default convertors between Java types.
     */
    static TypeConvertorInfo[] _typeConvertors = new TypeConvertorInfo[] {
        // Convertors to boolean
        new TypeConvertorInfo( java.lang.Short.class, java.lang.Boolean.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Boolean( ( (Short) obj ).shortValue() != 0 );
            }
            public String toString() { return "Short->Boolean"; }
        } ),
        new TypeConvertorInfo( java.lang.Integer.class, java.lang.Boolean.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Boolean( ( (Integer) obj ).intValue() != 0 );
            }
            public String toString() { return "Integer->Boolean"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, java.lang.Boolean.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                switch ( ( (String) obj ).length() ) {
                    case 0: return Boolean.FALSE;
                    case 1: char ch = ( (String) obj ).charAt( 0 );
                        if (param == null || param.length() != 2 )
                            return ( ch == 'T' || ch == 't'  ) ? Boolean.TRUE : Boolean.FALSE;
                        else
                            return ( ch == param.charAt( 1 ) ) ? Boolean.TRUE : Boolean.FALSE;
                    case 4: return ( (String) obj ).equalsIgnoreCase( "true" ) ? Boolean.TRUE : Boolean.FALSE;
                    case 5: return ( (String) obj ).equalsIgnoreCase( "false" ) ? Boolean.TRUE : Boolean.FALSE;
                }
                return Boolean.FALSE;
            }
            public String toString() { return "String->Boolean"; }
        } ),
        new TypeConvertorInfo( java.math.BigDecimal.class, java.lang.Boolean.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Boolean( ( (java.math.BigDecimal) obj).intValue() != 0 );
            }
            public String toString() { return "BigDecimal->Boolean"; }
        } ),
        // Convertors to integer
        new TypeConvertorInfo( java.lang.Byte.class, java.lang.Integer.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Integer( ( (Byte) obj ).intValue() );
            }
            public String toString() { return "Byte->Integer"; }
        } ),
        new TypeConvertorInfo( java.lang.Short.class, java.lang.Integer.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Integer( ( (Short) obj ).intValue() );
            }
            public String toString() { return "Short->Integer"; }
        } ),
        new TypeConvertorInfo( java.lang.Long.class, java.lang.Integer.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Integer( ( (Long) obj ).intValue() );
            }
            public String toString() { return "Long->Integer"; }
        } ),
        new TypeConvertorInfo( java.lang.Float.class, java.lang.Integer.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Integer( ( (Float) obj ).intValue() );
            }
            public String toString() { return "Float->Integer"; }
        } ),
        new TypeConvertorInfo( java.lang.Double.class, java.lang.Integer.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Integer( ( (Double) obj ).intValue() );
            }
            public String toString() { return "Double->Integer"; }
        } ),
        new TypeConvertorInfo( java.math.BigDecimal.class, java.lang.Integer.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Integer( ( (BigDecimal) obj ).intValue() );
            }
            public String toString() { return "BigDecimal->Integer"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, java.lang.Integer.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return Integer.valueOf( (String) obj );
            }
            public String toString() { return "String->Integer"; }
        } ),
        new TypeConvertorInfo( java.util.Date.class, java.lang.Integer.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                _paramDateFormat.applyPattern( org.exolab.castor.mapping.loader.Types.getFullDatePattern( param ) );
                return new Integer( _paramDateFormat.format( (java.util.Date) obj ) );
            }
            public String toString() { return "Date->Integer"; }
        } ),
        // Convertors to long
        new TypeConvertorInfo( java.lang.Integer.class, java.lang.Long.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Long( ( (Integer) obj ).longValue() );
            }
            public String toString() { return "Integer->Long"; }
        } ),
        new TypeConvertorInfo( java.lang.Short.class, java.lang.Long.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Long( ( (Short) obj ).longValue() );
            }
            public String toString() { return "Short->Long"; }
        } ),
        new TypeConvertorInfo( java.lang.Float.class, java.lang.Long.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Long( ( (Float) obj ).longValue() );
            }
            public String toString() { return "Float->Long"; }
        } ),
        new TypeConvertorInfo( java.lang.Double.class, java.lang.Long.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Long( ( (Double) obj ).longValue() );
            }
            public String toString() { return "Double->Long"; }
        } ),
        new TypeConvertorInfo( java.math.BigDecimal.class, java.lang.Long.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Long( ( (BigDecimal) obj ).longValue() );
            }
            public String toString() { return "BigDecimal->Long"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, java.lang.Long.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return Long.valueOf( (String) obj );
            }
            public String toString() { return "String->Long"; }
        } ),
        // Convertors to short
        new TypeConvertorInfo( java.lang.Byte.class, java.lang.Short.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Short( ( (Byte) obj ).shortValue() );
            }
            public String toString() { return "Byte->Short"; }
        } ),
        new TypeConvertorInfo( java.lang.Integer.class, java.lang.Short.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Short( ( (Integer) obj ).shortValue() );
            }
            public String toString() { return "Integer->Short"; }
        } ),
        new TypeConvertorInfo( java.lang.Long.class, java.lang.Short.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Short( ( (Long) obj ).shortValue() );
            }
            public String toString() { return "Long->Short"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, java.lang.Short.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return Short.valueOf( (String) obj );
            }
            public String toString() { return "String->Short"; }
        } ),
        // Convertors to byte
        new TypeConvertorInfo( java.lang.Short.class, java.lang.Byte.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Byte( ( (Short) obj ).byteValue() );
            }
            public String toString() { return "Short->Byte"; }
        } ),
        new TypeConvertorInfo( java.lang.Integer.class, java.lang.Byte.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Byte( ( (Integer) obj ).byteValue() );
            }
            public String toString() { return "Integer->Byte"; }
        } ),
        // Convertors to double
        new TypeConvertorInfo( java.lang.Float.class, java.lang.Double.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Double( ( (Float) obj ).floatValue() );
            }
            public String toString() { return "Float->Double"; }
        } ),
        new TypeConvertorInfo( java.lang.Integer.class, java.lang.Double.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Double( (double) ( (Integer) obj ).intValue() );
            }
            public String toString() { return "Integer->Double"; }
        } ),
        new TypeConvertorInfo( java.lang.Long.class, java.lang.Double.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Double( (double) ( (Long) obj ).longValue() );
            }
            public String toString() { return "Long->Double"; }
        } ),
        new TypeConvertorInfo( java.math.BigDecimal.class, java.lang.Double.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Double( ( (BigDecimal) obj ).doubleValue() );
            }
            public String toString() { return "BigDecimal->Double"; }
        } ),
        new TypeConvertorInfo( java.util.Date.class, java.lang.Double.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                _paramDateFormat.applyPattern( org.exolab.castor.mapping.loader.Types.getFullDatePattern( param ) );
                return new Double( _paramDateFormat.format( (java.util.Date) obj ) );
            }
            public String toString() { return "Date->Double"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, java.lang.Double.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return Double.valueOf( (String) obj );
            }
            public String toString() { return "Double->String"; }
        } ),
        // Convertors to float
        new TypeConvertorInfo( java.lang.Double.class, java.lang.Float.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Float( ( (Double) obj ).floatValue() );
            }
            public String toString() { return "Double->Float"; }
        } ),
        new TypeConvertorInfo( java.lang.Integer.class, java.lang.Float.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Float( (float) ( (Integer) obj ).intValue() );
            }
            public String toString() { return "Integer->Float"; }
        } ),
        new TypeConvertorInfo( java.lang.Long.class, java.lang.Float.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Float( (float) ( (Long) obj ).longValue() );
            }
            public String toString() { return "Long->Float"; }
        } ),
        new TypeConvertorInfo( java.math.BigDecimal.class, java.lang.Float.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new Float( ( (BigDecimal) obj ).floatValue() );
            }
            public String toString() { return "BigDecimal->Float"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, java.lang.Float.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return Float.valueOf( (String) obj );
            }
            public String toString() { return "String->Float"; }
        } ),
        // Convertors to big decimal
        new TypeConvertorInfo( java.lang.Double.class, java.math.BigDecimal.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                // Don't remove "toString" below! Otherwise the result is incorrect.
                return new BigDecimal( ( (Double) obj ).toString() );
            }
            public String toString() { return "Double->BigDecimal"; }
        } ),
        new TypeConvertorInfo( java.lang.Float.class, java.math.BigDecimal.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                // Don't remove "toString" below! Otherwise the result is incorrect.
                return new BigDecimal( ( (Float) obj ).toString() );
            }
            public String toString() { return "Float->BigDecimal"; }
        } ),
        new TypeConvertorInfo( java.lang.Integer.class, java.math.BigDecimal.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return BigDecimal.valueOf( ( (Integer) obj ).intValue() );
            }
            public String toString() { return "Integer->BigDecimal"; }
        } ),
        new TypeConvertorInfo( java.lang.Long.class, java.math.BigDecimal.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return BigDecimal.valueOf( ( (Long) obj ).longValue() );
            }
            public String toString() { return "Long->BigDecimal"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, java.math.BigDecimal.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new BigDecimal( (String) obj );
            }
            public String toString() { return "String->BigDecimal"; }
        } ),
        new TypeConvertorInfo( java.util.Date.class, java.math.BigDecimal.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                _paramDateFormat.applyPattern( org.exolab.castor.mapping.loader.Types.getFullDatePattern( param ) );
                return new BigDecimal( _paramDateFormat.format( (java.util.Date) obj ) + ".0" );
            }
            public String toString() { return "Date->BigDecimal"; }
        } ),
        new TypeConvertorInfo( java.lang.Boolean.class, java.math.BigDecimal.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return BigDecimal.valueOf( ( (Boolean) obj).booleanValue() ? 1 : 0 );
            }
            public String toString() { return "Boolean->BigDecimal"; }
        } ),
        // Convertors to string
        new TypeConvertorInfo( java.lang.Short.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj.toString();
            }
            public String toString() { return "Short->String"; }
        } ),
        new TypeConvertorInfo( java.lang.Integer.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj.toString();
            }
            public String toString() { return "Integer->String"; }
        } ),
        new TypeConvertorInfo( java.lang.Long.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj.toString();
            }
            public String toString() { return "Long->String"; }
        } ),
        new TypeConvertorInfo( java.lang.Float.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj.toString();
            }
            public String toString() { return "Float->String"; }
        } ),
        new TypeConvertorInfo( java.lang.Double.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj.toString();
            }
            public String toString() { return "Double->String"; }
        } ),
        new TypeConvertorInfo( java.lang.Object.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj.toString();
            }
            public String toString() { return "Object->String"; }
        } ),
        new TypeConvertorInfo( java.util.Date.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                if ( param == null || param.length() == 0 )
                    return obj.toString();
                else {
                    _paramDateFormat.applyPattern( param );
                    return _paramDateFormat.format( (java.util.Date) obj );
                }
            }
            public String toString() { return "Date->String"; }
        } ),
        new TypeConvertorInfo( java.lang.Character.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new String( obj.toString() );
            }
            public String toString() { return "Character->String"; }
        } ),
        new TypeConvertorInfo( char[].class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new String( (char[]) obj );
            }
            public String toString() { return "chars->String"; }
        } ),
        new TypeConvertorInfo( byte[].class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                MimeBase64Encoder encoder;

                encoder = new MimeBase64Encoder();
                encoder.translate( (byte[]) obj );
                return new String( encoder.getCharArray() );
            }
            public String toString() { return "bytes->String"; }
        } ),
        new TypeConvertorInfo( java.lang.Boolean.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                if ( param == null || param.length() != 2 )
                    return ( (Boolean) obj ).booleanValue() ? "T" : "F";
                else
                    return ( (Boolean) obj ).booleanValue() ? param.substring( 1, 2 ) : param.substring( 0, 1 );
            }
            public String toString() { return "Boolean->String"; }
        } ),
        // Convertors to character/byte array
        new TypeConvertorInfo( java.lang.String.class, java.lang.Character.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                String str = (String ) obj;
                return ( new Character( str.length() == 0 ? 0 : str.charAt( 0 ) ) );
            }
            public String toString() { return "String->Character"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, char[].class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return ( (String ) obj ).toCharArray();
            }
            public String toString() { return "String->chars"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, byte[].class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                MimeBase64Decoder decoder;

                decoder = new MimeBase64Decoder();
                decoder.translate( (String ) obj );
                return decoder.getByteArray();
            }
            public String toString() { return "String->bytes"; }
        } ),
        // Convertors to date
        new TypeConvertorInfo( java.lang.String.class, java.util.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                try {
                    if ( param == null || param.length() == 0 )
                        return _dateFormat.parse( (String) obj );
                    else {
                        _paramDateFormat.applyPattern( param );
                        return _paramDateFormat.parse( (String) obj );
                    }
                } catch ( ParseException except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
            }
            public String toString() { return "String->Date"; }
        } ),
        new TypeConvertorInfo( java.lang.Integer.class, java.util.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                try {
                    _paramDateFormat.applyPattern( org.exolab.castor.mapping.loader.Types.getFullDatePattern( param ) );
                    return _paramDateFormat.parse( obj.toString() );
                } catch ( ParseException except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
            }
            public String toString() { return "Integer->Date"; }
        } ),
        new TypeConvertorInfo( java.math.BigDecimal.class, java.util.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                try {
                    _paramDateFormat.applyPattern( org.exolab.castor.mapping.loader.Types.getFullDatePattern( param ) );
                    return _paramDateFormat.parse( obj.toString() );
                } catch ( ParseException except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
            }
            public String toString() { return "BigDecimal->Date"; }
        } ),
        new TypeConvertorInfo( java.lang.Double.class, java.util.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                try {
                    _paramDateFormat.applyPattern( org.exolab.castor.mapping.loader.Types.getFullDatePattern( param ) );
                    return _paramDateFormat.parse( _decimalFormat.format(obj).trim() );
                } catch ( ParseException except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
            }
            public String toString() { return "Double->Date"; }
        } ),
        new TypeConvertorInfo( java.util.Date.class, java.sql.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new java.sql.Date( ( (java.util.Date) obj ).getTime() );
            }
            public String toString() { return "util.Date->sql.Date"; }
        } ),
        new TypeConvertorInfo( java.sql.Date.class, java.util.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj;
            }
            public String toString() { return "sql.Date->util.Date"; }
        } ),
        new TypeConvertorInfo( java.util.Date.class, java.sql.Time.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new java.sql.Time( ( (java.util.Date) obj ).getTime() );
            }
            public String toString() { return "util.Date->sql.Time"; }
        } ),
        new TypeConvertorInfo( java.sql.Time.class, java.util.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj;
            }
            public String toString() { return "sql.Time->util.Date"; }
        } ),
        new TypeConvertorInfo( java.util.Date.class, java.sql.Timestamp.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                long time = ( (java.util.Date) obj ).getTime();
                java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
                timestamp.setNanos((int) ((time % 1000) * 1000000));
                //timestamp.setNanos(0);  // this can workaround the bug in SAP DB
                return timestamp;
            }
            public String toString() { return "util.Date->sql.Timestamp"; }
        } ),

        new TypeConvertorInfo( java.sql.Timestamp.class, java.util.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {

                java.sql.Timestamp timestamp = (java.sql.Timestamp) obj;
                return new java.util.Date(timestamp.getTime() + timestamp.getNanos() / 1000000);
            }
            public String toString() { return "sql.Timestamp->util.Date"; }
        } ),
        new TypeConvertorInfo( java.lang.String.class, java.sql.Timestamp.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                long time;
                if ( param == null || param.length() ==  0 ) {
                    param = "yyyy-MM-dd HH:mm:ss.SSS";
                }
                try {
                    _paramDateFormat.applyPattern( param );
                    time = _paramDateFormat.parse( (String) obj ).getTime();
                } catch ( ParseException except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
                java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
                timestamp.setNanos((int) ((time % 1000) * 1000000));
                //timestamp.setNanos(0);  // this can workaround the bug in SAP DB
                return timestamp;
            }
            public String toString() { return "String->sql.Timestamp"; }
        } ),

        new TypeConvertorInfo( java.sql.Timestamp.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                if ( param == null || param.length() ==  0 ) {
                    param = "yyyy-MM-dd HH:mm:ss.SSS";
                }
                java.sql.Timestamp timestamp = (java.sql.Timestamp) obj;
                _paramDateFormat.applyPattern( param );
                return _paramDateFormat.format( new java.util.Date(timestamp.getTime() + timestamp.getNanos() / 1000000) );
            }
            public String toString() { return "sql.Timestamp->String"; }
        } ),
        // InputStream convertors
        new TypeConvertorInfo( byte[].class, java.io.InputStream.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new java.io.ByteArrayInputStream((byte[]) obj);
            }
            public String toString() { return "bytes->io.InputStream"; }
        } ),
        new TypeConvertorInfo( java.io.InputStream.class, byte[].class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                try {
                    java.io.InputStream is = (java.io.InputStream) obj;
                    java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
                    byte[] buffer = new byte[256];
                    int len = 0;
                    int b;
                    while ( (len = is.read(buffer)) > 0 )
                        bos.write( buffer, 0, len );
                    return bos.toByteArray();
                } catch ( java.io.IOException except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
            }
            public String toString() { return "io.InputStream->bytes"; }
        } ),
        // Reader convertors
        new TypeConvertorInfo( java.lang.String.class, java.sql.Clob.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                String str = (java.lang.String) obj;
                return new ClobImpl(new java.io.StringReader(str), str.length());
            }
            public String toString() { return "String->sql.Clob"; }
        } ),
        new TypeConvertorInfo( char[].class, java.sql.Clob.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                char[] chars = (char[]) obj;
                return new ClobImpl(new java.io.CharArrayReader(chars), chars.length);
            }
            public String toString() { return "chars->sql.Clob"; }
        } ),
        new TypeConvertorInfo( java.sql.Clob.class, java.lang.String.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                try {
                    java.io.Reader reader = ((java.sql.Clob) obj).getCharacterStream();
                    java.io.CharArrayWriter writer = new java.io.CharArrayWriter();
                    char[] buffer = new char[256];
                    int len = 0;
                    int b;
                    while ( (len = reader.read(buffer)) > 0 )
                        writer.write( buffer, 0, len );
                    return writer.toString();
                } catch ( Exception except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
            }
            public String toString() { return "sql.Clob->String"; }
        } ),
        new TypeConvertorInfo( java.sql.Clob.class, char[].class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                try {
                    java.io.Reader reader = ((java.sql.Clob) obj).getCharacterStream();
                    java.io.CharArrayWriter writer = new java.io.CharArrayWriter();
                    char[] buffer = new char[256];
                    int len = 0;
                    int b;
                    while ( (len = reader.read(buffer)) > 0 )
                        writer.write( buffer, 0, len );
                    return writer.toCharArray();
                } catch ( Exception except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
            }
            public String toString() { return "sql.Clob->String"; }
        } ),

        new TypeConvertorInfo( java.util.Date.class, org.exolab.castor.types.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new org.exolab.castor.types.Date((java.util.Date) obj);
            }
            public String toString() { return "util.Date->castor.types.Date"; }
        } ),

        new TypeConvertorInfo( org.exolab.castor.types.Date.class, java.util.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                Object result = null;
                try {
                    result = ((org.exolab.castor.types.Date)obj).toDate();
                } catch (java.text.ParseException e) {
                    //we can never reach that point
                }
                return result;
            }
            public String toString() { return "castor.types.Date->util.Date"; }
        } ),

        new TypeConvertorInfo( java.sql.Date.class, org.exolab.castor.types.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new org.exolab.castor.types.Date((java.util.Date) obj);
            }
            public String toString() { return "sql.Date->castor.types.Date"; }
        } ),

        new TypeConvertorInfo( org.exolab.castor.types.Date.class, java.sql.Date.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                Object result = null;
                try {
                    result = new java.sql.Date( ((org.exolab.castor.types.Date)obj).toDate().getTime() );
                } catch (java.text.ParseException e) {
                    //we can never reach that point
                }
                return result;
            }
            public String toString() { return "castor.types.Date->sql.Date"; }
        } ),

        // It's a special case for Serializable
        // Because Serializable need the right ClassLoader when serializing, we convert to byte[] and vice-versa
        new TypeConvertorInfo( java.io.Serializable.class, java.io.InputStream.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return new java.io.ByteArrayInputStream((byte[]) obj);
            }
            public String toString() { return "bytes->io.InputStream"; }
        } ),
        new TypeConvertorInfo( java.io.InputStream.class, java.io.Serializable.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                try {
                    java.io.InputStream is = (java.io.InputStream) obj;
                    java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
                    byte[] buffer = new byte[256];
                    int len = 0;
                    int b;
                    while ( (len = is.read(buffer)) > 0 )
                        bos.write( buffer, 0, len );
                    return bos.toByteArray();
                } catch ( java.io.IOException except ) {
                    throw new IllegalArgumentException( except.toString() );
                }
            }
            public String toString() { return "io.InputStream->bytes"; }
        } ),

        // It's a special case for Serializable
        // Because Serializable need the right ClassLoader when serializing, we convert to byte[] and vice-versa
        new TypeConvertorInfo( java.io.Serializable.class, byte[].class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj;
            }
            public String toString() { return "bytes->io.InputStream"; }
        } ),
        new TypeConvertorInfo( byte[].class, java.io.Serializable.class, new TypeConvertor() {
            public Object convert( Object obj, String param ) {
                return obj;
            }
            public String toString() { return "io.InputStream->bytes"; }
        } )

    };

}













