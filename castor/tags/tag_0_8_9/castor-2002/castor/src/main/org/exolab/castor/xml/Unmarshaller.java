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

package org.exolab.castor.xml;

//-- xml related imports
import org.xml.sax.*;
import org.xml.sax.helpers.AttributeListImpl;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.util.Configuration;
import org.exolab.castor.xml.util.*;

import java.io.IOException;
import java.io.Reader;
import java.io.PrintWriter;

/**
 * An unmarshaller to allowing unmarshalling of XML documents to
 * Java Objects. The Class must specify
 * the proper access methods (setters/getters) in order for instances
 * of the Class to be properly unmarshalled.
 * @author <a href="mailto:kvisco@exoffice.com">Keith Visco</a>
 * @version $Revision$ $Date$
**/
public class Unmarshaller {
    
    //----------------------------/
    //- Private Member Variables -/
    //----------------------------/
    
    /**
     * The Class that this Unmarshaller was created with
    **/
    Class _class = null;
    
    /**
     * The class descriptor resolver
    **/
    private ClassDescriptorResolver _cdResolver = null;
    
    /**
     * The EntityResolver used for resolving entities
    **/
    EntityResolver entityResolver = null;

    private ClassLoader loader = null;
    
    /**
     * The print writer used for log information
    **/
    private PrintWriter pw = null;
    
    /**
     * The flag indicating whether or not to display debug information
    **/
    private boolean debug = false;
    
    /**
     * The flag indicating whether or not to validate during
     * unmarshalling
    **/
    private boolean validate = false;
    
    //----------------/
    //- Constructors -/
    //----------------/
    

    /**
     * Creates a new Unmarshaller with the given Class
     * @param c the Class to create the Unmarshaller for, this
     * may be null, if the Unmarshaller#setMapping is called
     * to load a mapping for the root element of xml document.
    **/
    public Unmarshaller(Class c) {
        this(c, null);
    } //-- Unmarshaller(Class)

    /**
     * Creates a new Unmarshaller with the given Class
     * @param c the Class to create the Unmarshaller for, this
     * may be null, if the Unmarshaller#setMapping is called
     * to load a mapping for the root element of xml document.
     * @param loader, the ClassLoader to use.
    **/
    public Unmarshaller(Class c, ClassLoader loader) {
        super();
        this._class = c;
        this.debug = Configuration.debug();
        this.loader = loader;
        _cdResolver = new ClassDescriptorResolverImpl(loader);
    } //-- Unmarshaller(Class)
    
    
    /**
     * Creates a new Unmarshaller with the given Mapping
     * @param mapping, the Mapping to use
    **/
    public Unmarshaller(Mapping mapping) 
        throws MappingException
    {
        super();
        this.debug = Configuration.debug();
        if (mapping != null) {
            setMapping(mapping);
            this.loader = mapping.getClassLoader();
        }
    } //-- Unmarshaller(Mapping)
    
    /**
     * Sets the ClassLoader to use when loading new classes
     * @param loader the ClassLoader to use
    **/
    public void setClassLoader(ClassLoader loader) {
        this.loader = loader;
    } //-- setClassLoader
    
    
    /**
     * Turns debuging on or off. If no Log Writer has been set, then
     * System.out will be used to display debug information
     * @param debug the flag indicating whether to generate debug information.
     * A value of true, will turn debuggin on. 
     * @see #setLogWriter.
    **/
    public void setDebug(boolean debug) {
        this.debug = debug;
    } //-- setDebug
    
    /**
     * Sets the EntityResolver to use when resolving system and
     * public ids with respect to entites and Document Type.
     * @param entityResolver the EntityResolver to use when
     * resolving System and Public ids.
    **/
    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    } //-- entityResolver
    
    /**
     * Sets the PrintWriter used for logging
     * @param printWriter the PrintWriter to use for logging
    **/
    public void setLogWriter(PrintWriter printWriter) {
        this.pw = printWriter;
    } //-- setLogWriter

    /**
     * Sets the Mapping to use during unmarshalling.
     * @param mapping the Mapping to use during unmarshalling.
     * @see setResolver
    **/
    public void setMapping( Mapping mapping )
        throws MappingException
    {
        if (_cdResolver == null)
            _cdResolver = new ClassDescriptorResolverImpl(loader);
        
        _cdResolver.setMappingLoader( (XMLMappingLoader) mapping.getResolver( Mapping.XML, null ) );
    } //-- setMapping
    
    /**
     * Sets the ClassDescriptorResolver to use during unmarshalling
     * @param cdr the ClassDescriptorResolver to use
     * @see setMapping
     * <BR />     
     * <B>Note:</B> This method will nullify any Mapping
     * currently being used by this Unmarshaller
    **/
    public void setResolver( ClassDescriptorResolver cdr ) {
        
        if (cdr != null)
            _cdResolver = cdr;
        else
            _cdResolver = new ClassDescriptorResolverImpl(loader);
            
    } //-- setResolver
    
    /**
     * Sets the flag for validation
     * @param validate, a boolean to indicate whether or not 
     * validation should be done during umarshalling. <br />
     * By default validation will be performed.
    **/
    public void setValidation(boolean validate) {
        this.validate = validate;
    } //-- setValidation
    
    /**
     * Unmarshals Objects of this Unmarshaller's Class type. 
     * The Class must specify the proper access methods 
     * (setters/getters) in order for instances of the Class
     * to be properly unmarshalled.
     * @param reader the Reader to read the XML from
     * @exception MarshalException when there is an error during
     * the unmarshalling process
     * @exception ValidationException when there is a validation error
    **/
    public Object unmarshal(Reader reader) 
        throws MarshalException, ValidationException
    {
        return unmarshal(new InputSource(reader));
    } //-- unmarshal(Reader reader)

    /**
     * Unmarshals Objects of this Unmarshaller's Class type. 
     * The Class must specify the proper access methods 
     * (setters/getters) in order for instances of the Class
     * to be properly unmarshalled.
     * @param eventProducer the EventProducer which produces
     * the SAX events
     * @exception MarshalException when there is an error during
     * the unmarshalling process
     * @exception ValidationException when there is a validation error
    **/
    public Object unmarshal(EventProducer eventProducer) 
        throws MarshalException, ValidationException
    {
        UnmarshalHandler handler = new UnmarshalHandler(_class);
        handler.setResolver(_cdResolver);
        handler.setLogWriter(pw);
        handler.setDebug(debug);
        eventProducer.setDocumentHandler(handler);
        try {
            eventProducer.start();
        }
        catch(org.xml.sax.SAXException sx) {
            MarshalException marshalEx = new MarshalException(sx);
            if(handler.getDocumentLocator()!=null)
            {
                FileLocation location = new FileLocation();
                location.setFilename(handler.getDocumentLocator().getSystemId());
                location.setLineNumber(handler.getDocumentLocator().getLineNumber());
                location.setColumnNumber(handler.getDocumentLocator().getColumnNumber());
                marshalEx.setLocation(location);
            }
            throw marshalEx;
        }
        return handler.getObject();
        
    } //-- unmarshal(EventProducer)
        
        

    /**
     * Unmarshals Objects of this Unmarshaller's Class type. 
     * The Class must specify the proper access methods 
     * (setters/getters) in order for instances of the Class
     * to be properly unmarshalled.
     * @param source the InputSource to read the XML from
     * @exception MarshalException when there is an error during
     * the unmarshalling process
     * @exception ValidationException when there is a validation error
    **/
    public Object unmarshal(InputSource source) 
        throws MarshalException, ValidationException
    {
        Parser parser = Configuration.getParser();
        
        if (parser == null)
            throw new MarshalException("unable to create parser");
            
        if (entityResolver != null)
            parser.setEntityResolver(entityResolver);
            
        UnmarshalHandler handler = new UnmarshalHandler(_class);
        handler.setResolver(_cdResolver);
        handler.setLogWriter(pw);
        handler.setDebug(debug);
        parser.setDocumentHandler(handler);
            
        //parser.setErrorHandler(unmarshaller);
        try {
            parser.parse(source);
        }
        catch(java.io.IOException ioe) {
            throw new MarshalException(ioe);
        }
        catch(org.xml.sax.SAXException sx) {
            
            MarshalException marshalEx = new MarshalException(sx);
            if ( handler.getDocumentLocator() != null ) {
                FileLocation location = new FileLocation();
                location.setFilename(handler.getDocumentLocator().getSystemId());
                location.setLineNumber(handler.getDocumentLocator().getLineNumber());
                location.setColumnNumber(handler.getDocumentLocator().getColumnNumber());
                marshalEx.setLocation(location);
            }
            throw marshalEx;
        }
        
        return handler.getObject();
    } //-- unmarshal(InputSource)
    
    /**
     * Unmarshals Objects of the given Class type. The Class must specify
     * the proper access methods (setters/getters) in order for instances
     * of the Class to be properly unmarshalled.
     * @param c the Class to create a new instance of
     * @param reader the Reader to read the XML from
     * @exception MarshalException when there is an error during
     * the unmarshalling process
     * @exception ValidationException when there is a validation error
    **/
    public static Object unmarshal(Class c, Reader reader) 
        throws MarshalException, ValidationException
    {
        Unmarshaller unmarshaller = new Unmarshaller(c);
        return unmarshaller.unmarshal(reader);
    } //-- void unmarshal(Writer) 

    /**
     * Unmarshals Objects of the given Class type. The Class must specify
     * the proper access methods (setters/getters) in order for instances
     * of the Class to be properly unmarshalled.
     * @param c the Class to create a new instance of
     * @param source the InputSource to read the XML from
     * @exception MarshalException when there is an error during
     * the unmarshalling process
     * @exception ValidationException when there is a validation error
    **/
    public static Object unmarshal(Class c, InputSource source) 
        throws MarshalException, ValidationException
    {
        Unmarshaller unmarshaller = new Unmarshaller(c);
        return unmarshaller.unmarshal(source);
    } //-- void unmarshal(Writer) 
    
} //-- Unmarshaller

