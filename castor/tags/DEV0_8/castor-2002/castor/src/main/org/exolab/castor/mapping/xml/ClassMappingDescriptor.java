/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.8 (20000324)</a>,
 * using an XML Schema.
 * $Id
 */

package org.exolab.castor.mapping.xml;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.xml.*;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.util.XMLFieldDescriptorImpl;

/**
 * 
 * @version $Revision$ $Date$
**/
public class ClassMappingDescriptor implements org.exolab.castor.xml.XMLClassDescriptor {


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

    public ClassMappingDescriptor() {
        xmlName = "class";
        XMLFieldDescriptorImpl desc = null;
        XMLFieldHandler handler = null;
        //-- initialize attribute descriptors
        
        attributes = new XMLFieldDescriptorImpl[4];
        //-- _access
        desc = new XMLFieldDescriptorImpl(java.lang.String.class, "_access", "access", NodeType.Attribute);
        desc.setImmutable(true);
        desc.setHandler( new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ClassMapping target = (ClassMapping) object;
                return target.getAccess();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ClassMapping target = (ClassMapping) object;
                    target.setAccess( (java.lang.String) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return null;
            }
        } );
        attributes[0] = desc;
        
        //-- _identity
        desc = new XMLFieldDescriptorImpl(java.lang.String.class, "_identity", "identity", NodeType.Attribute);
        desc.setImmutable(true);
        desc.setHandler( new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ClassMapping target = (ClassMapping) object;
                return target.getIdentity();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ClassMapping target = (ClassMapping) object;
                    target.setIdentity( (java.lang.String) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return null;
            }
        } );
        attributes[1] = desc;
        
        //-- _extends
        desc = new XMLFieldDescriptorImpl(java.lang.Object.class, "_extends", "extends", NodeType.Attribute);
        desc.setReference(true);
        desc.setHandler( new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ClassMapping target = (ClassMapping) object;
                return target.getExtends();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ClassMapping target = (ClassMapping) object;
                    target.setExtends( (java.lang.Object) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return new java.lang.Object();
            }
        } );
        attributes[2] = desc;
        
        //-- _name
        desc = new XMLFieldDescriptorImpl(java.lang.String.class, "_name", "name", NodeType.Attribute);
        this.identity = desc;
        desc.setHandler( new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ClassMapping target = (ClassMapping) object;
                return target.getName();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ClassMapping target = (ClassMapping) object;
                    target.setName( (java.lang.String) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return new java.lang.String();
            }
        } );
        desc.setRequired(true);
        attributes[3] = desc;
        
        //-- initialize element descriptors
        
        elements = new XMLFieldDescriptorImpl[4];
        //-- _description
        desc = new XMLFieldDescriptorImpl(java.lang.String.class, "_description", "description", NodeType.Element);
        desc.setImmutable(true);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ClassMapping target = (ClassMapping) object;
                return target.getDescription();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ClassMapping target = (ClassMapping) object;
                    target.setDescription( (java.lang.String) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return null;
            }
        } );
        desc.setHandler(handler);
        desc.setMultivalued(false);
        elements[0] = desc;
        
        //-- _mapTo
        desc = new XMLFieldDescriptorImpl(MapTo.class, "_mapTo", "map-to", NodeType.Element);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ClassMapping target = (ClassMapping) object;
                return target.getMapTo();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ClassMapping target = (ClassMapping) object;
                    target.setMapTo( (MapTo) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return new MapTo();
            }
        } );
        desc.setHandler(handler);
        desc.setMultivalued(false);
        elements[1] = desc;
        
        //-- _fieldMappingList
        desc = new XMLFieldDescriptorImpl(FieldMapping.class, "_fieldMappingList", "field", NodeType.Element);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ClassMapping target = (ClassMapping) object;
                return target.getFieldMapping();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ClassMapping target = (ClassMapping) object;
                    target.addFieldMapping( (FieldMapping) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return new FieldMapping();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(true);
        elements[2] = desc;
        
        //-- _containerList
        desc = new XMLFieldDescriptorImpl(Container.class, "_containerList", "container", NodeType.Element);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ClassMapping target = (ClassMapping) object;
                return target.getContainer();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ClassMapping target = (ClassMapping) object;
                    target.addContainer( (Container) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return new Container();
            }
        } );
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(true);
        elements[3] = desc;
        
    } //-- org.exolab.castor.mapping.xml.ClassMappingDescriptor()


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
        return org.exolab.castor.mapping.xml.ClassMapping.class;
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
    public java.lang.String getXMLName() {
        return xmlName;
    } //-- java.lang.String getXMLName() 

}
