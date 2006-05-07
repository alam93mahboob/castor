/*
 * Copyright 2005 Tim Telcik, Werner Guttmann, Ralf Joachim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.castor.cache.distributed;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.castor.cache.CacheAcquireException;

/**
 * JCACHE implementation of Castor JDO Cache.
 * 
 * JCACHE is the Java Temporary Caching API (JSR-107).
 * 
 * For more details of JCACHE, see http://www.jcp.org/en/jsr/detail?id=107
 * 
 * @see http://www.jcp.org/en/jsr/detail?id=107
 * @author <a href="mailto:ttelcik AT hbf DOT com DOT au">Tim Telcik</a>
 * @author <a href="mailto:werner DOT guttmann AT gmx DOT net">Werner Guttmann</a>
 * @author <a href="mailto:ralf DOT joachim AT syscon-world DOT de">Ralf Joachim</a>
 * @version $Revision$ $Date$
 * @since 1.0
 */
public final class JCache extends AbstractDistributedCache {
    //--------------------------------------------------------------------------

    /** The <a href="http://jakarta.apache.org/commons/logging/">Jakarta Commons
     *  Logging </a> instance used for all logging. */
    private static final Log LOG = LogFactory.getLog(JCache.class);

    /** The type of the cache. */
    public static final String TYPE = "jcache";
    
    /** The classname of the implementations factory class. */
    public static final String IMPLEMENTATION = "javax.util.jcache.CacheAccessFactory";
    
    /** Parameter types for calling getMapAccess() method on IMPLEMENTATION. */
    private static final Class[] TYPES_GET_MAP_ACCESS = new Class[] {String.class};
    
    //--------------------------------------------------------------------------
    // operations for life-cycle management of cache
    
    /**
     * {@inheritDoc}
     * @see org.castor.cache.Cache#initialize(java.util.Map)
     */
    public void initialize(final Properties params) throws CacheAcquireException {
        initialize(IMPLEMENTATION, params);
    }

    /**
     * Normally called to initialize JCache. To be able to test the method without
     * having <code>javax.util.jcs.CacheAccessFactory</code> implementation, it can
     * also be called with a test implementations classname.
     * 
     * @param implementation Cache implementation classname to initialize.
     * @param params Parameters to initialize the cache (e.g. name, capacity).
     * @throws CacheAcquireException If cache can not be initialized.
     */
    public void initialize(final String implementation, final Properties params)
    throws CacheAcquireException {
        super.initialize(params);

        try {
            ClassLoader ldr = this.getClass().getClassLoader();
            Class cls = ldr.loadClass(implementation);
            Object factory = invokeStaticMethod(cls, "getInstance", null, null); 
            setCache((Map) invokeMethod(factory, "getMapAccess",
                    TYPES_GET_MAP_ACCESS, new Object[] {getName()}));
        } catch (Exception e) {
            String msg = "Error creating JCache cache: " + e.getMessage();
            LOG.error(msg, e);
            throw new CacheAcquireException(msg, e);
        }
    }

    //--------------------------------------------------------------------------
    // getters/setters for cache configuration

    /**
     * {@inheritDoc}
     * @see org.castor.cache.Cache#getType()
     */
    public String getType() { return TYPE; }

    //--------------------------------------------------------------------------
}