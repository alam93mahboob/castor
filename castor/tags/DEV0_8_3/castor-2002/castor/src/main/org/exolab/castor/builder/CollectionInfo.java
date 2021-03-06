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

package org.exolab.castor.builder;

import org.exolab.castor.builder.types.*;
import org.exolab.castor.xml.JavaXMLNaming;
import org.exolab.javasource.*;

import java.util.Vector;

/**
 * A helper used for generating source that deals with Collections
 * @author <a href="mailto:kvisco@exoffice.com">Keith Visco</a>
 * @version $Revision$ $Date$
**/
public class CollectionInfo extends FieldInfo {
 
        
    private XSList xsList      = null;
    private String contentName = null;
    private XSType contentType = null;
    
    private FieldInfo content   = null;
    private String elementName;
    
    /**
     * Creates a new CollectionInfo
     * @param contextType the content type of the collection, ie. the type
     * of objects that the collection will contain
     * @param name the name of the Collection
     * @param elementName the element name for each element in collection
    **/
    public CollectionInfo(XSType contentType, String name, String elementName) 
    {
        super(new XSList(contentType), name);
        xsList = (XSList) getSchemaType();
        this.contentType = contentType;
        this.contentName = "v" + JavaXMLNaming.toJavaClassName(elementName);
        this.elementName = elementName;
        content = new FieldInfo(contentType, contentName);
    } //-- CollectionInfo
    
    /**
     * Creates code for initialization of this Member
     * @param jsc the JSourceCode in which to add the source to
    **/
    public void generateInitializerCode(JSourceCode jsc) {
        jsc.add(getName());
        jsc.append(" = new Vector();");
    } //-- generateConstructorCode
        
    public String getReadMethodName() {
        StringBuffer sb = new StringBuffer("get");
        sb.append(JavaXMLNaming.toJavaClassName(getElementName()));
        return sb.toString();
    } //-- getReadMethodName
    
    public String getWriteMethodName() {
        StringBuffer sb = new StringBuffer();
        sb.append("add");
        sb.append(JavaXMLNaming.toJavaClassName(getElementName()));
        return sb.toString();
    } //-- getWriteMethodName
    
    //------------------/
    //- Public Methods -/
    //------------------/
    
    public JMethod[] createAccessMethods() {
        
        Vector methods = new Vector(10);
        
        JMethod method = null;
        
        JParameter contentParam 
            = new JParameter(getContentType().getJType(), getContentName());
            
        JSourceCode jsc = null;
        
          //---------------------/
         //- Create add method -/
        //---------------------/
        
        String cName = JavaXMLNaming.toJavaClassName(getElementName());
        
        method = new JMethod(null, "add"+cName);
        methods.addElement(method);
        
        method.addException(SGTypes.IndexOutOfBoundsException);
        method.addParameter(contentParam);
        
        createAddMethod_Impl(method);
                    

          //---------------------/
         //- Create get method -/
        //---------------------/
        
        
        JType jType = getContentType().getJType();
        method = new JMethod(jType, "get"+cName);
        methods.addElement(method);
        method.addException(SGTypes.IndexOutOfBoundsException);
        method.addParameter(new JParameter(JType.Int, "index"));
                    
        createGetMethod_Impl(method);
                    
        
          //-----------------------/
         //- Create get[] method -/
        //-----------------------/

        jType = jType.createArray();
        method = new JMethod(jType, "get"+cName);
        methods.addElement(method);
        
        createGetByIndexMethod_Impl(method);

        
          //---------------------/
         //- Create set method -/
        //---------------------/
        
        method = new JMethod(null, "set"+cName);
        methods.addElement(method);
        method.addException(SGTypes.IndexOutOfBoundsException);
        method.addParameter(contentParam);
        method.addParameter(new JParameter(JType.Int, "index"));
        
        createSetMethod_Impl(method);
        
        
          //--------------------------/
         //- Create getCount method -/
        //--------------------------/
        
        method = new JMethod(JType.Int, "get"+cName+"Count");
        methods.addElement(method);
        
        createGetCountMethod_Impl(method);
        
        
          //---------------------------/
         //- Create Enumerate Method -/
        //---------------------------/
        
        method = new JMethod(SGTypes.Enumeration, "enumerate"+cName);
        methods.addElement(method);
        
        createEnumerateMethod_Impl(method);
        
        
          //--------------------------------/
         //- Create remove(Object) Method -/
        //--------------------------------/
        
        //-- commented out until I fix primitives
        //method = new JMethod(JType.Boolean, "remove"+cName);
        //methods.addElement(method);
        //method.addParameter(contentParam);
        
        //createRemoveByObjectMethod_Impl(method);
        

          //--------------------------------/
         //- Create remove(int i) Method -/
        //--------------------------------/
        
        jType = getContentType().getJType();
        method = new JMethod(jType, "remove"+cName);
        methods.addElement(method);
        method.addParameter(new JParameter(JType.Int, "index"));
        
        createRemoveByIndexMethod_Impl(method);
        
        
          //-----------------------------/
         //- Create removeAll() Method -/
        //-----------------------------/
        
        method = new JMethod(null, "removeAll"+cName);
        methods.addElement(method);
        
        createRemoveAllMethod_Impl(method);
        
        
        /* Return JMethod[] */
        
        JMethod[] jmArray = new JMethod[methods.size()];
        methods.copyInto(jmArray);
        
        return jmArray;
        
    } //-- createAccessMethods
    
    /**
     * Returns the main read method for this member
     * @return the main read method for this member
    **/
    public JMethod getReadMethod() {
        
        String methodName = getReadMethodName();
        
        JType jType  = getContentType().getJType();
        
        //-- create get method
        JMethod jMethod = new JMethod(jType, methodName);
        JSourceCode jsc = jMethod.getSourceCode();
        jsc.add("return this.");
        jsc.append(getName());
        jsc.append(";");
        
        return jMethod;
    } //-- getReadMethod
    
    public XSList getXSList() {
        return xsList;
    }
    
    public String getContentName() {
        return contentName;
    }
    
    public XSType getContentType() {
        return contentType;
    }
    
    public FieldInfo getContent() {
        return content;
    }

    public String getElementName() {
        return elementName;
    }
    
    /**
     * Return whether or not this member is a multivalued member or not
     * @return true if this member can appear more than once
    **/
    public boolean isMultivalued() {
        return true;
    }

    /** Creates implementation of add method.
    */
    public void createAddMethod_Impl (JMethod method) {
        
        JSourceCode jsc = method.getSourceCode();
        
        int maxSize = getXSList().getMaximumSize();
        if (maxSize > 0) {
            jsc.add("if (!(");
            jsc.append(getName());
            jsc.append(".size() < ");
            jsc.append(Integer.toString(maxSize));
            jsc.append(")) {");
            jsc.indent();
            jsc.add("throw new IndexOutOfBoundsException();");
            jsc.unindent();
            jsc.add("}");
        }
        jsc.add(getName());
        jsc.append(".addElement(");
        jsc.append(getContentType().createToJavaObjectCode(getContentName()));
        jsc.append(");");
    }
                    
    /** Creates implementation of get method.
    */
    public void createGetMethod_Impl (JMethod method) {
        
        JSourceCode jsc = method.getSourceCode();
        JType jType = method.getReturnType();
        
        jsc.add("//-- check bounds for index");
        jsc.add("if ((index < 0) || (index > ");
        jsc.append(getName());
        jsc.append(".size())) {");
        jsc.indent();
        jsc.add("throw new IndexOutOfBoundsException();");
        jsc.unindent();
        jsc.add("}");
        
        jsc.add("");
        jsc.add("return ");
        
        String variableName = getName()+".elementAt(index)";
        
        if (getContentType().getType() == XSType.CLASS) {
            jsc.append("(");
            jsc.append(jType.getName());
            jsc.append(") ");
            jsc.append(variableName);
        }
        else {
            jsc.append(getContentType().createFromJavaObjectCode(variableName));
        }
        jsc.append(";");
    }

    /** Creates implementation of get[] method.
    */
    public void createGetByIndexMethod_Impl (JMethod method) {

        JSourceCode jsc = method.getSourceCode();
        JType jType = method.getReturnType();
                    
        jsc.add("int size = ");
        jsc.append(getName());
        jsc.append(".size();");
        
        String variableName = getName()+".elementAt(index)";
        
        jsc.add(jType.getLocalName());
        jsc.append("[] mArray = new ");
        jsc.append(jType.getLocalName());
        jsc.append("[size];");
        jsc.add("for (int index = 0; index < size; index++) {");
        jsc.indent();
        jsc.add("mArray[index] = ");
        if (getContentType().getType() == XSType.CLASS) {
            jsc.append("(");
            jsc.append(jType.getLocalName());
            jsc.append(") ");
            jsc.append(variableName);
        }
        else {
            jsc.append(getContentType().createFromJavaObjectCode(variableName));
        }
        jsc.append(";");
        jsc.unindent();
        jsc.add("}");
        jsc.add("return mArray;");
    }

    /** Creates implementation of set method.
    */
    public void createSetMethod_Impl (JMethod method) {

        JSourceCode jsc = method.getSourceCode();
        
        jsc.add("//-- check bounds for index");
        jsc.add("if ((index < 0) || (index > ");
        jsc.append(getName());
        jsc.append(".size())) {");
        jsc.indent();
        jsc.add("throw new IndexOutOfBoundsException();");
        jsc.unindent();
        jsc.add("}");
        
        int maxSize = getXSList().getMaximumSize();
        if (maxSize != 0) {
            jsc.add("if (!(");
            jsc.append(getName());
            jsc.append(".size() < ");
            jsc.append(Integer.toString(maxSize));
            jsc.append(")) {");
            jsc.indent();
            jsc.add("throw new IndexOutOfBoundsException();");
            jsc.unindent();
            jsc.add("}");
        }
        jsc.add(getName());
        jsc.append(".setElementAt(");
        jsc.append(getContentType().createToJavaObjectCode(getContentName()));
        jsc.append(", index);");
    }

    /** Creates implementation of getCount method.
    */
    public void createGetCountMethod_Impl (JMethod method) {
        
        JSourceCode jsc = method.getSourceCode();
        
        jsc.add("return ");
        jsc.append(getName());
        jsc.append(".size();");
    }

    /** Creates implementation of Enumerate method.
    */
    public void createEnumerateMethod_Impl (JMethod method) {
        
        JSourceCode jsc = method.getSourceCode();
        
        jsc.add("return ");
        jsc.append(getName());
        jsc.append(".elements();");
    }

    /** Creates implementation of remove(Object) method.
    */
    public void createRemoveByObjectMethod_Impl (JMethod method) {
        
        JSourceCode jsc = method.getSourceCode();
        
        jsc.add("return ");
        jsc.append(getName());
        jsc.append(".removeElement(");
        jsc.append(getContentName());
        jsc.append(");");
    }

    /** Creates implementation of remove(int i) method.
    */
    public void createRemoveByIndexMethod_Impl (JMethod method) {
        
        JSourceCode jsc = method.getSourceCode();
        JType jType = method.getReturnType();
        
        jsc.add("Object obj = ");
        jsc.append(getName());
        jsc.append(".elementAt(index);");
        jsc.add(getName());
        jsc.append(".removeElementAt(index);");
        jsc.add("return ");
        if (getContentType().getType() == XSType.CLASS) {
            jsc.append("(");
            jsc.append(jType.getName());
            jsc.append(") obj;");
        }
        else {
            jsc.append(getContentType().createFromJavaObjectCode("obj"));
            jsc.append(";");
        }
    }

    /** Creates implementation of removeAll() method.
    */
    public void createRemoveAllMethod_Impl (JMethod method) {

        JSourceCode jsc = method.getSourceCode();
        
        jsc.add(getName());
        jsc.append(".removeAllElements();");
    }
    

} //-- CollectionInfo

