/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.8.3 (2000502)</a>,
 * using an XML Schema.
 * $Id$
 *
 * Note: This file has been modified by hand.
 */

package org.exolab.castor.mapping.xml;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.xml.*;
import org.exolab.castor.xml.validators.*;
import org.exolab.castor.xml.handlers.*;
import org.exolab.castor.xml.util.XMLFieldDescriptorImpl;


/**
 * 
 * @version $Revision$ $Date$
**/
public class XmlDescriptor implements org.exolab.castor.xml.XMLClassDescriptor {


      //--------------------/
     //- Member Variables -/
    //--------------------/

    private org.exolab.castor.xml.XMLFieldDescriptor[] elements;

    private org.exolab.castor.xml.XMLFieldDescriptor[] attributes;

    private org.exolab.castor.xml.util.XMLFieldDescriptorImpl contentDesc;

    private java.lang.String nsPrefix;

    private java.lang.String nsURI;

    private java.lang.String xmlName;

    private org.exolab.castor.xml.XMLFieldDescriptor identity;


      //----------------/
     //- Constructors -/
    //----------------/

    public XmlDescriptor() {
        xmlName = "xml";
        XMLFieldDescriptorImpl  desc           = null;
        XMLFieldHandler         handler        = null;
        FieldValidator          fieldValidator = null;
        //-- initialize attribute descriptors
        
        attributes = new XMLFieldDescriptorImpl[4];
        //-- _node
        desc = new XMLFieldDescriptorImpl(java.lang.String.class, "_node", "node", NodeType.Attribute);
        desc.setImmutable(true);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                Xml target = (Xml) object;
                return target.getNode();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    Xml target = (Xml) object;
                    target.setNode( (java.lang.String) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public void resetValue( Object object )
            {
                setValue( object, null );
            }
            public Object newInstance( Object parent ) {
                return null;
            }
        } );
        desc.setHandler(handler);
        attributes[0] = desc;
        
        //-- validation code for: _node
        fieldValidator = new FieldValidator();
        fieldValidator.setValidator(new StringValidator());
        desc.setValidator(fieldValidator);
        
        //-- _type
        desc = new XMLFieldDescriptorImpl(java.lang.String.class, "_type", "type", NodeType.Attribute);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                Xml target = (Xml) object;
                return target.getType();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    Xml target = (Xml) object;
                    target.setType( (java.lang.String) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public void resetValue( Object object )
            {
                setValue( object, null );
            }
            public Object newInstance( Object parent ) {
                return new java.lang.String();
            }
        } );
        desc.setHandler(handler);
        attributes[1] = desc;
        
        //-- validation code for: _type
        fieldValidator = new FieldValidator();
        fieldValidator.setValidator(new NameValidator(NameValidator.NMTOKEN));
        desc.setValidator(fieldValidator);
        
        //-- _name
        desc = new XMLFieldDescriptorImpl(java.lang.String.class, "_name", "name", NodeType.Attribute);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                Xml target = (Xml) object;
                return target.getName();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    Xml target = (Xml) object;
                    target.setName( (java.lang.String) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public void resetValue( Object object )
            {
                setValue( object, null );
            }
            public Object newInstance( Object parent ) {
                return new java.lang.String();
            }
        } );
        desc.setHandler(handler);
        attributes[2] = desc;
        
        //-- validation code for: _name
        fieldValidator = new FieldValidator();
        fieldValidator.setValidator(new NameValidator(NameValidator.NMTOKEN));
        desc.setValidator(fieldValidator);

        //-- _match
        desc = new XMLFieldDescriptorImpl(java.lang.String.class, "_match", "match", NodeType.Attribute);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                Xml target = (Xml) object;
                return target.getMatch();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    Xml target = (Xml) object;
                    target.setMatch( (java.lang.String) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public void resetValue( Object object )
            {
                setValue( object, null );
            }
            public Object newInstance( Object parent ) {
                return null;
            }
        } );
        desc.setHandler(handler);
        attributes[3] = desc;
        
        //-- validation code for: _match
        fieldValidator = new FieldValidator();
        desc.setValidator(fieldValidator);
        
        //-- initialize element descriptors
        
        elements = new XMLFieldDescriptorImpl[0];
    } //-- org.exolab.castor.mapping.xml.XmlDescriptor()


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public org.exolab.castor.mapping.AccessMode getAccessMode() {
        return null;
    } //-- org.exolab.castor.mapping.AccessMode getAccessMode() 

    /**
    **/
    public org.exolab.castor.xml.XMLFieldDescriptor[] getAttributeDescriptors() {
        return attributes;
    } //-- org.exolab.castor.xml.XMLFieldDescriptor[] getAttributeDescriptors() 

    /**
    **/
    public org.exolab.castor.xml.XMLFieldDescriptor getContentDescriptor() {
        return contentDesc;
    } //-- org.exolab.castor.xml.XMLFieldDescriptor getContentDescriptor() 

    /**
    **/
    public org.exolab.castor.xml.XMLFieldDescriptor[] getElementDescriptors() {
        return elements;
    } //-- org.exolab.castor.xml.XMLFieldDescriptor[] getElementDescriptors() 

    /**
    **/
    public org.exolab.castor.mapping.ClassDescriptor getExtends() {
        return null;
    } //-- org.exolab.castor.mapping.ClassDescriptor getExtends() 

    /**
     * Returns the XML field descriptor matching the given
     * xml name and nodeType. If NodeType is null, then
     * either an AttributeDescriptor, or ElementDescriptor
     * may be returned. Null is returned if no matching
     * descriptor is available.
     *
     * @param name the xml name to match against
     * @param nodeType, the NodeType to match against, or null if
     * the node type is not known.
     * @return the matching descriptor, or null if no matching
     * descriptor is available.
     *
    **/
    public XMLFieldDescriptor getFieldDescriptor
        (String name, NodeType nodeType) 
    {
        
        boolean wild = (nodeType == null);
        
        if (wild || (nodeType == NodeType.Element)) {
            XMLFieldDescriptor desc = null;
            for (int i = 0; i < elements.length; i++) {
                desc = elements[i];
                if (desc == null) continue;
                if (desc.matches(name)) return desc;
            }
        }
        
        if (wild || (nodeType == NodeType.Attribute)) {
            XMLFieldDescriptor desc = null;
            for (int i = 0; i < attributes.length; i++) {
                desc = attributes[i];
                if (desc == null) continue;
                if (desc.matches(name)) return desc;
            }
        }
        
        return null;
        
    } //-- getFieldDescriptor

    /**
    **/
    public org.exolab.castor.mapping.FieldDescriptor[] getFields() {
        int size = attributes.length + elements.length;
        if (contentDesc != null) ++size;
        
        FieldDescriptor[] fields = new FieldDescriptor[size];
        int c = 0;
        for (int i = 0; i < attributes.length; i++)
            fields[c++] = attributes[i];
        
        for (int i = 0; i < elements.length; i++)
            fields[c++] = elements[i];
        
        if (contentDesc != null) fields[c] = contentDesc;
        
        return fields;
    } //-- org.exolab.castor.mapping.FieldDescriptor[] getFields() 

    /**
    **/
    public org.exolab.castor.mapping.FieldDescriptor getIdentity() {
        return identity;
    } //-- org.exolab.castor.mapping.FieldDescriptor getIdentity() 

    /**
    **/
    public java.lang.Class getJavaClass() {
        return org.exolab.castor.mapping.xml.Xml.class;
    } //-- java.lang.Class getJavaClass() 

    /**
    **/
    public java.lang.String getNameSpacePrefix() {
        return nsPrefix;
    } //-- java.lang.String getNameSpacePrefix() 

    /**
    **/
    public java.lang.String getNameSpaceURI() {
        return nsURI;
    } //-- java.lang.String getNameSpaceURI() 

    /**
    **/
    public org.exolab.castor.xml.TypeValidator getValidator() {
        return null;
    } //-- org.exolab.castor.xml.TypeValidator getValidator() 

    /**
    **/
    public java.lang.String getXMLName() {
        return xmlName;
    } //-- java.lang.String getXMLName() 

}
