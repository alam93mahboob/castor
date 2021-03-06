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


package org.exolab.castor.jdo;




/**
 * An open connection to the database. This object represents an open
 * connection to the database that can be used to perform transactional
 * operations on the database.
 * <p>
 * Database operations can only be performed in the context of a
 * transaction. Client applications should begin and commit a transaction
 * using the {@link #begin} and {@link #commit} methods. Server
 * applications should use implicit transaction demaraction by the
 * container or explicit transaction demarcation using <tt>javax.transaction.UserTransaction</tt>.
 * <p>
 * All objects queried and created during a transaction are persistent.
 * Changes to persistent objects will be stored in the database when
 * the transaction commits. Changes will not be stored if the transaction
 * is rolled back or fails to commit.
 * <p>
 * Once the transaction has committed or rolled back, all persistent
 * objects become transient. Opening a new transaction does not make
 * these objects persistent.
 * <p>
 * For example:
 * <pre>
 * Database     db;
 * Query        oql;
 * QueryResults results;
 * Product      prod;
 *
 * <font color="red">// Open a database and start a transaction</font>
 * db = jdo.getDatabase();
 * db.begin();
 * <font color="red">// Select all the products in a given group</font>
 * oql = db.getOQLQuery( "SELECT p FROM Product p WHERE group=$");
 * oql.bind( groupId );
 * results = oql.execute();
 * while ( results.hasMore() ) {
 *   <font color="red">// A 25% mark down for each product and mark as sale</font>
 *   prod = (Product) results.next();
 *   prod.markDown( 0.25 );
 *   prod.setOnSale( true );
 * }
 * <font color="red">// Commit all changes, close the database</font>
 * db.commit();
 * db.close();
 * </pre>
 *
 * @author <a href="arkin@exoffice.com">Assaf Arkin</a>
 * @version $Revision$ $Date$
 * @see JDO#getDatabase
 * @see Query
 */
public interface Database
{


    /**
     * Read only access. Used with queries and the {@link #load}
     * method to load objects as read-only.
     * <p>
     * Read-only objects are not persistent and changes to these
     * objects are not reflected in the database when the transaction
     * commits.
     */
    public static final short ReadOnly = 0;


    /**
     * Shared access. Used with queries and the {@link #load}
     * method to load objects with shared access.
     * <p>
     * Shared access allows the same record to be accessed by two
     * concurrent transactions, each with it's own view (object).
     * <p>
     * These objects acquire a read lock which escalated to a write
     * lock when the transaction commits if the object has been
     * modified. Dirty checking is enabled for all fields marked
     * as such, and a cached copy is used to populate the object.
     */
    public static final short Shared = 1;


    /**
     * Exclusive access. Used with queries and the {@link #load}
     * method to load objects with exclusive access.
     * <p>
     * Exclusive access prevents two concurrent transactions from
     * accessing the same record. In exclusive mode objects acquire
     * a write lock, and concurrent transactions will block until
     * the lock is released at commit time.
     * <p>
     * Dirty checking is enabled for all fields marked as such.
     * When an object is first loaded in the transaction, it will
     * be synchronized with the database and not populated from
     * the cache.
     */
    public static final short Exclusive = 2;


    /**
     * Database lock access. Used with queries and the {@link #load}
     * method to load objects with a database lock.
     * <p>
     * Database lock prevents two concurrent transactions from
     * accessing the same record either through Castor or direct
     * database access by acquiring a write lock in the select
     * statement. Concurrent transactions will block until the lock
     * is released at commit time.
     * <p>
     * When an object is first loaded in the transaction, it will
     * be synchronized with the database and not populated from
     * the cache. Dirty checking is not required.
     */
    public static final short DbLocked = 3;


    /**
     * Creates an OQL query with no statement. {@link OQLQuery#create}
     * must be called before the query can be executed.
     *
     * @return An OQL query
     */
    public OQLQuery getOQLQuery();


    /**
     * Creates an OQL query from the supplied statement.
     *
     * @param query An OQL query statement
     * @return An OQL query
     * @throws QueryException The query syntax is invalid
     */
    public OQLQuery getOQLQuery( String oql )
        throws QueryException;


    /**
     * Creates an empty query. The query must be created before
     * it can be executed.
     *
     * @return A query
     */
    public Query getQuery();


    /**
     * Load an object of the specified type and given identity.
     * Once loaded the object is persistent. Calling this method with
     * the same identity in the same transaction will return the same
     * object. This method is equivalent to a query that returns a
     * single object.
     *
     * @param type The object's type
     * @param identity The object's identity
     * @throws ObjectNotFoundException No object of the given type and
     *  identity was found in persistent storage
     * @throws LockNotGrantedException Timeout or deadlock occured
     *  attempting to acquire a lock on the object
     * @throws TransactionNotInProgressException Method called while
     *   transaction is not in progress
     * @throws PersistenceException An error reported by the
     *  persistence engine
     */
    public Object load( Class type, Object identity )
        throws ObjectNotFoundException, LockNotGrantedException,
               TransactionNotInProgressException, PersistenceException;


    /**
     * Load an object of the specified type and given identity.
     * Once loaded the object is persistent. Calling this method with
     * the same identity in the same transaction will return the same
     * object. This method is equivalent to a query that returns a
     * single object.
     *
     * @param type The object's type
     * @param identity The object's identity
     * @param accessMode The access mode
     * @throws ObjectNotFoundException No object of the given type and
     *  identity was found in persistent storage
     * @throws LockNotGrantedException Timeout or deadlock occured
     *  attempting to acquire a lock on the object
     * @throws TransactionNotInProgressException Method called while
     *   transaction is not in progress
     * @throws PersistenceException An error reported by the
     *  persistence engine
     */
    public Object load( Class type, Object identity, short accessMode )
        throws ObjectNotFoundException, LockNotGrantedException,
               TransactionNotInProgressException, PersistenceException;


    /**
     * Creates a new object in persistent storage. The object will be
     * persisted only if the transaction commits.
     * <p>
     * If the object has an identity then duplicate identity check happens
     * in this method, and the object is visible to queries in this
     * transaction. If the identity is null, duplicate identity check
     * occurs when the transaction completes and the object is not
     * visible to queries until the transaction commits.
     *
     * @param object The object to create
     * @throws TransactionNotInProgressException Method called while
     *   transaction is not in progress
     * @throws DuplicateIdentityException An object with this identity
     *  already exists in persistent storage
     * @throws ClassNotPersistenceCapableException The class is not
     *  persistent capable
     * @throws PersistenceException An error reported by the
     *  persistence engine
     */
    public void create( Object object )
        throws ClassNotPersistenceCapableException, DuplicateIdentityException,
               TransactionNotInProgressException, PersistenceException;


    /**
     * Removes the object from persistent storage. The deletion will
     * take effect only if the transaction is committed, but the
     * object is no longer visible to queries in the current transaction
     * and locks for access from other transactions will block until
     * this transaction completes.
     *
     * @param object The object to remove
     * @throws TransactionNotInProgressException Method called while
     *   transaction is not in progress
     * @throws ObjectNotPersistentException The object has not been
     *  queried or created in this transaction
     * @throws LockNotGrantedException Timeout or deadlock occured
     *  attempting to acquire a lock on the object
     * @throws PersistenceException An error reported by the
     *  persistence engine
     */
    public void remove( Object object )
        throws ObjectNotPersistentException, LockNotGrantedException, 
               TransactionNotInProgressException, PersistenceException;


    /**
     * Creates a new object in persistent storage. The object will be
     * persisted only if the transaction commits.
     * <p>
     * If the object has an identity then duplicate identity check happens
     * in this method, and the object is visible to queries in this
     * transaction. If the identity is null, duplicate identity check
     * occurs when the transaction completes and the object is not
     * visible to queries until the transaction commits.
     * <p>
     * It is recommended to use this method in "long" transaction scenario:
     * the object was read in one of the previous "short" transaction, 
     * modified and now is being "included"in the current "short" transaction.
     * The object must implement interface {@link TimeStampable} in order to 
     * perform dirty checking.
     *
     * @param object The object to create
     * @throws TransactionNotInProgressException Method called while
     *   transaction is not in progress
     * @throws ClassNotPersistenceCapableException The class is not
     *  persistent capable
     * @throws PersistenceException An error reported by the
     *  persistence engine
     * @throws ObjectModifiedException Dirty checking mechanism may immediately
     *  report that the object was modified in the database during the long 
     *  transaction.
     */
    public void update( Object object )
        throws ClassNotPersistenceCapableException, ObjectModifiedException,
               TransactionNotInProgressException, PersistenceException;


    /**
     * Acquire a soft write lock on the object. Read locks are implicitly
     * available when the object is queried. A write lock is only
     * granted for objects that are created or deleted or for objects
     * loaded in <tt>exclusive</t> mode - this method can obtain such a
     * lock explicitly.
     * <p>
     * A soft lock is acquired in memory, not in the database. To acquire
     * a lock in the database, use the <tt>locked</tt> access mode.
     * <p>
     * If the object already has a write lock in this
     * transaction or a read lock in this transaction but no read lock
     * in any other transaction, a write lock is obtained. If this
     * object has a read lock in any other transaction this method
     * will block until the other transaction will release its lock.
     * If the timeout has elapsed or a deadlock has been detected,
     * an exception will be thrown but the current lock will be retained.
     *
     * @param object The object to lock
     * @throws TransactionNotInProgressException Method called while
     *   transaction is not in progress
     * @throws ObjectNotPersistentException The object has not been
     *  queried or created in this transaction
     * @throws LockNotGrantedException Timeout or deadlock occured
     *  attempting to acquire a lock on the object
     * @throws PersistenceException An error reported by the
     *  persistence engine
     */
    public void lock( Object object )
        throws LockNotGrantedException, ObjectNotPersistentException,
               TransactionNotInProgressException,  PersistenceException;


    /**
     * Begin a new transaction. A transaction must be open in order
     * to query and persist objects.
     *
     * @throws PersistenceException A transaction is already open on
     *  this database, or an error reported by the persistence engine
     */
    public void begin()
        throws PersistenceException;


    /**
     * Commits and closes the transaction. All changes made to persistent
     * objects during the transaction are made persistent, objects
     * created during the transaction are made durable, and objects
     * removed during the transaction are removed from the database.
     * <p>
     * If the transaction cannot commit, the entire transaction rolls
     * back and a {@link TransactionAbortedException} exception is
     * thrown.
     * <p>
     * After this method returns, the transaction is closed and all
     * persistent objects are transient. Using {@link #begin} to open a
     * new transaction will not restore objects to their persistent
     * stage.
     *
     * @throws TransactionNotInProgressException Method called while
     *  transaction is not in progress
     * @throws TransactionAbortedException The transaction cannot
     *  commit and has been rolled back
     */
    public void commit()
        throws TransactionNotInProgressException, TransactionAbortedException;


    /**
     * Rolls back and closes the transaction. All changes made to
     * persistent objects during the transaction are lost, objects
     * created during the transaction are not made durable and objects
     * removed during the transaction continue to exist.
     *
     * @throws TransactionNotInProgressException Method called while
     *  transaction is not in progress
     */
    public void rollback()
        throws TransactionNotInProgressException;


    /**
     * Returns true if a transaction is currently active.
     *
     * @return True if a transaction is active
     */
    public boolean isActive();


    /**
     * Returns true if the database is closed.
     *
     * @return True if the database is closed
     */
    public boolean isClosed();


    /**
     * Closes the database. If a client transaction is in progress the
     * transaction will be rolled back and an exception thrown.
     * If an app-server transaction is in progress, the transaction
     * will commit/rollback when triggered by the application server.  
     *
     * @throws PersistenceException An error occured while
     *  attempting to close the database
     */
    public void close()
        throws PersistenceException;


    /**
     * Returns true if the object is persistent. An object is persistent
     * if it was created or queried in this transaction. If the object
     * was created or queried in another transaction, or there is no
     * open transaction, this method returns null.
     *
     * @param object The object
     * @return True if persistent in this transaction
     */
    public boolean isPersistent( Object object );


    /**
     * Returns the object's identity. If the identity was determined when
     * the object was created, or if the object was retrieved, that identity
     * is returned. If the identity has been modified, this will not be
     * reflected until the transaction commits. Null is returned if the
     * identity is null, the object does not have any identity, or the
     * object is not persistent.
     *
     * @param object The object
     * @return The object's identity, or null
     */
    public Object getIdentity( Object object );


    /**
     * @deprecated See {@link #create}
     */
    public void makePersistent( Object object )
        throws ClassNotPersistenceCapableException,
               DuplicateIdentityException, PersistenceException;


    /**
     * @deprecated See {@link #remove}
     */
    public void deletePersistent( Object object )
        throws ObjectNotPersistentException, LockNotGrantedException, 
               PersistenceException;


    /**
     * @deprecated Use {@link #commit} and {@link #rollback} instead;
     *  this method cannot be implemented properly with multiple type
     *  of locks and will not be supported in future versions of the
     *  API
     */
    public void checkpoint()
        throws TransactionNotInProgressException, TransactionAbortedException;

    /**
     * Get the underlying JDBC Connection.
     * Only for internal / advanced use !
     * Never try to close it (is done by castor).
     */
    public Object /* java.sql.Connection */ getConnection()
    throws org.exolab.castor.jdo.PersistenceException ;

}

