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
 * This file was originally developed by Keith Visco during the
 * course of employment at Intalio Inc.
 * All portions of this file developed by Keith Visco after Jan 19 2005 are
 * Copyright (C) 2005 Keith Visco. All Rights Reserved.
 * 
 * $Id$
 */


package org.exolab.castor.xml;

/**
 * An interface for finding or "resolving" XMLClassDescriptor classes.
 * 
 * <BR/>
 * <B>Note:</B>
 * This interface is used by the marshalling Framework for
 * resolving XMLClassDescriptors for non-primitive types.
 * There are no guarantees that this class will be called for
 * java native classes.
 * 
 * @author <a href="mailto:keith AT kvisco DOT com">Keith Visco</a>
 * @version $Revision$ $Date: 2005-02-28 17:41:38 -0700 (Mon, 28 Feb 2005) $
 */
public interface ClassDescriptorResolver {
    
    
    /** 
     * <BR />
     * <B>Note:</B> This method will be removed soon (kv).
     */
    public XMLMappingLoader getMappingLoader();
    
    /**
     * Returns the XMLClassDescriptor for the given class
     * 
     * @param type the Class to find the XMLClassDescriptor for
     * @return the XMLClassDescriptor for the given class
     */
    public XMLClassDescriptor resolve(Class type)
        throws ResolverException;
    
    /**
     * Returns the XMLClassDescriptor for the given class name
     * 
     * @param className the class name to find the XMLClassDescriptor for
     * @return the XMLClassDescriptor for the given class name
     */
    public XMLClassDescriptor resolve(String className)
        throws ResolverException;
    
    /**
     * Returns the XMLClassDescriptor for the given class name
     * 
     * @param className the class name to find the XMLClassDescriptor for
     * @param loader the ClassLoader to use
     * @return the XMLClassDescriptor for the given class name
     */
    public XMLClassDescriptor resolve(String className, ClassLoader loader)
        throws ResolverException;
    
    /**
     * Returns the first XMLClassDescriptor that matches the given
     * XML name and namespaceURI. Null is returned if no descriptor
     * can be found.
     *
     * @param className the class name to find the XMLClassDescriptor for
     * @param loader the ClassLoader to use
     * @return the XMLClassDescriptor for the given XML name
     */
    public XMLClassDescriptor resolveByXMLName
        (String xmlName, String namespaceURI, ClassLoader loader)
        throws ResolverException;

    /**
     * Returns an enumeration of XMLClassDescriptor objects that
     * match the given xml name
     *
     * @param className the class name to find the XMLClassDescriptor for
     * @param loader the ClassLoader to use
     * @return an enumeration of XMLClassDescriptor objects.
     */
    public ClassDescriptorEnumeration resolveAllByXMLName
        (String xmlName, String namespaceURI, ClassLoader loader)
        throws ResolverException;
    
    /**
     * Sets the mapping loader for this ClassDescriptorResolver
     *
     * <BR />
     * <B>Note:</B> This method will be removed soon (kv).
     */
    public void setMappingLoader(XMLMappingLoader xmlMappingLoader);
    
} //-- ClassDescriptorResolver
