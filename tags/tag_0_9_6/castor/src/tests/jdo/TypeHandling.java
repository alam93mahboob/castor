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
 * Copyright 1999-2001 (C) Intalio, Inc. All Rights Reserved.
 *
 * $Id$
 */


package jdo;


import java.util.Date;
import java.util.Enumeration;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Connection;
import java.sql.SQLException;
import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import harness.TestHarness;
import harness.CastorTestCase;


/**
 * Test on different Literal types support by Castor. This test detect any
 * malfunction set/get of a Literal types, type conversions and modification 
 * checks on diffenent literal types fields of data objects.
 */
public class TypeHandling extends CastorTestCase {


    private JDOCategory     _category;

    private Database        _db;

    private OQLQuery        _oql;

    /**
     * Constructor
     *
     * @param category The test suite of these tests
     */
    public TypeHandling( TestHarness category ) {

        super( category, "TC20", "Type handling" );
        _category = (JDOCategory) category;
    }


    public void setUp() 
        throws PersistenceException, SQLException {

        TestTypes     types;
        Enumeration   enumeration;

        // Open transaction in order to perform JDO operations
        _db = _category.getDatabase();

        // Delete all records to avoid problems with changed mapping
        Connection conn = _category.getJDBCConnection();
        conn.createStatement().execute( "DELETE FROM test_types" );

        // Determine if test object exists, if not create it.
        // If it exists, set the name to some predefined value
        // that this test will later override.
        _db.begin();
        _oql = _db.getOQLQuery( "SELECT types FROM jdo.TestTypes types WHERE id = $(integer)1" );
        // This one tests that bind performs type conversion
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            stream.println( "Updating object: " + types );
        } else {
            types = new TestTypes();
            stream.println( "Creating new object: " + types );
            _db.create( types );
        }
        _db.commit();
    }

    private void testDateTimeConversion()
            throws PersistenceException {

        TestTypes     types;

        Enumeration   enumeration;

        stream.println( "Testing date/time conversion" );
        _db.begin();
        // This one tests that bind performs type conversion
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            stream.println( "Date type: " + types.getDate().getClass() );
            stream.println( "Time type: " + types.getTime().getClass() );
            stream.println( "Deleting object: " + types );
            _db.remove( types );
        } else {
            stream.println( "Error: Could not load types object" );
            fail("Could not load types object");
        }
        _db.commit();

        _db.begin();
        types = new TestTypes();
        stream.println( "Creating new object: " + types );
        _db.create( types );
        _db.commit();
        stream.println( "OK: Handled date/time types" );

    }

    private void testSimpleFloat()
            throws PersistenceException {

        TestTypes    types;
        Enumeration   enumeration;

        stream.println( "Testing null in integer and long fields" );
        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            types.setDoubleValue( 11.12345d );
            types.setFloatValue(  22.46802f );
        }
        _db.commit();

        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            if ( types.getDoubleValue() != 11.12345d || types.getFloatValue() != 22.46802f )
                fail( "double and float doesn't work");
        }
        _db.commit();
    }

    private void testNullIntegerAndLong()
            throws PersistenceException {

        TestTypes     types;
        Enumeration   enumeration;

        stream.println( "Testing null in integer and long fields" );
        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            types.setIntValue( 5 );
            types.deleteIntValue();
            types.setLongValue( null );
        }
        _db.commit();

        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            if ( types.getIntValue() != 0 || types.hasIntValue() ) {
                stream.println( "Error: null integer value was not set" );
                fail("null integer value was not set");
            }
            if ( types.getLongValue() != null ) {
                stream.println( "Error: null long value was not set" );
                fail("null long value was not set");
            }
            types.setIntValue( 5 );
            types.setLongValue( new Long( 5 ) );
        } else {
            stream.println( "Error: failed to load object" );
            fail("failed to load object");
        }
        _db.commit();
     
        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            if ( types.getIntValue() != 5 || ! types.hasIntValue() ) {
                stream.println( "Error: non-null integer value was not set" );
                fail("non-null integer value was not set");
            }
            if ( ! types.getLongValue().equals( new Long( 5 ) ) ) {
                stream.println( "Error: non-null long value was not set" );
                fail("non-null long value was not set");
            }
        } else {
            stream.println( "Error: failed to load object" );
            fail("failed to load object");
        }
        _db.commit();

        stream.println( "OK: null in integer and long field passed" );
    }

    private void testSimpleChar()
            throws PersistenceException {

        TestTypes     types;
        Enumeration   enumeration;

        stream.println( "Testing value in char field" );
        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            types.setCharValue( 'A' );
        }
        _db.commit();
        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            if ( types.getCharValue() != 'A' ) {
                stream.println( "Error: char value was not set" );
                fail("char value was not set");
            }
        } else {
            stream.println( "Error: failed to load object" );
            fail("failed to load object");
        }
        _db.commit();
        stream.println( "OK: value in character field passed" );
    }

    private void testBooleanIsMethod() 
            throws PersistenceException 
	{
        
        TestTypes       types;
        Enumeration     en;
        
        stream.println("Testing boolean get/is methods.");
        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        en = _oql.execute();
        if ( en.hasMoreElements() ) {
            types = (TestTypes) en.nextElement();
            types.setBoolValue( true );
            types.setBoolIsMethod( true );
        }
        _db.commit();

        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        en = _oql.execute();
        if ( en.hasMoreElements() ) {
            types = (TestTypes) en.nextElement();
            if ( types.getBoolValue() != true ) {
                stream.println( "Error: (get) bool value was not set" );
                fail("(get) bool value was not set");
            }
            
            if ( types.isBoolIsMethod() != true ){
                stream.println( "Error: (is) bool value was not set" );
                fail( "(is) bool value was not set" );
            }
        } else {
            stream.println( "Error: failed to load object" );
            fail("failed to load object");
        }
        _db.commit();
        
        stream.println( "OK: Boolean get/is methods passed" );
    }
    
    private void testCharToBoolean()
            throws PersistenceException {

        TestTypes     types;
        Enumeration   enumeration;

        stream.println( "Testing the boolean->char[01] conversion" );
        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            types.setBoolValue( true );
        }
        _db.commit();

        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            if ( types.getBoolValue() != true ) {
                stream.println( "Error: bool value was not set" );
                fail("bool value was not set");
            }
        } else {
            stream.println( "Error: failed to load object" );
            fail("failed to load object");
        }
        _db.commit();
        stream.println( "OK: The boolean->char[01] conversion passed" );
    }

    private void testDateParameterized() 
            throws PersistenceException, ParseException {

        TestTypes     types;
        Enumeration   enumeration;
        Date          date;
        Date          time;
        Date          timestamp;
        SimpleDateFormat df;

        stream.println( "Testing date->int/numeric/char parameterized conversion" );
        df = new SimpleDateFormat();
        df.applyPattern("yyyy/MM/dd");
        date = df.parse("2000/05/27");
        df.applyPattern("yyyy/MM/dd HH:mm:ss.SSS");
        time = df.parse("2000/05/27 02:16:01.234");
        df.applyPattern("yyyy/MM/dd HH:mm:ss.SSS");
        timestamp = df.parse("2000/05/27 02:16:01.234");
        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            types.setDate2( date );
            types.setTime2( time );
            types.setTimestamp2( timestamp );
        }
        _db.commit();

        _db.begin();
        _oql.bind( TestTypes.DefaultId );
        enumeration = _oql.execute();
        if ( enumeration.hasMoreElements() ) {
            types = (TestTypes) enumeration.nextElement();
            if ( !date.equals( types.getDate2() ) ) {
                stream.println( "Error: date/int value was not set" );
                fail("date/int vlaue was not set");
            }
            if ( !time.equals( types.getTime2() ) ) {
                stream.println( "Error: time/string value was not set" );
                fail("time/string vlaue was not set");
            }
            if ( !timestamp.equals( types.getTimestamp2() ) ) {
                stream.println( "timestamp was set to: " + df.format(timestamp) );
                stream.println( "       timestamp oid: " + timestamp.hashCode() );
                stream.println( "        timestamp is: " + df.format(types.getTimestamp2()) );
                stream.println( "       timestamp oid: " + types.getTimestamp2().hashCode() );
                stream.println( "Error: timestamp/numeric value was not set" );
                fail("timestamp/numeric value was not set");
            }
        } else {
            stream.println( "Error: failed to load object" );
            fail("failed to load object");
        }
        _db.commit();
        stream.println( "OK: date->int/numeric/char conversion passed" );
    }

    public void tearDown() 
            throws PersistenceException {
        if ( _db.isActive() ) _db.rollback();
        _db.close();
    }
    
    public void runTest()
    	throws PersistenceException, ParseException 
	{
    	testDateTimeConversion();
    	testNullIntegerAndLong();
    	testSimpleChar();
    	testCharToBoolean();
    	testDateParameterized();
    	testBooleanIsMethod();
    	// testSimpleFloat();
    }
    

}
