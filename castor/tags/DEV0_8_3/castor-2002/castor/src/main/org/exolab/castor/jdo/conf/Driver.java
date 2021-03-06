/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.8 (20000324)</a>,
 * using an XML Schema.
 * $Id
 */

package org.exolab.castor.jdo.conf;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.*;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.DocumentHandler;

/**
 * 
 * @version $Revision$ $Date$
**/
public class Driver implements java.io.Serializable {


      //--------------------/
     //- Member Variables -/
    //--------------------/

    private java.lang.String _className;

    private java.lang.String _url;

    private java.util.Vector _paramList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Driver() {
        super();
        _paramList = new Vector();
    } //-- org.exolab.castor.jdo.conf.Driver()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * @param vParam
    **/
    public void addParam(Param vParam) 
        throws java.lang.IndexOutOfBoundsException
    {
        _paramList.addElement(vParam);
    } //-- void addParam(Param) 

    /**
    **/
    public java.util.Enumeration enumerateParam() {
        return _paramList.elements();
    } //-- java.util.Enumeration enumerateParam() 

    /**
    **/
    public java.lang.String getClassName() {
        return this._className;
    } //-- java.lang.String getClassName() 

    /**
     * 
     * @param index
    **/
    public Param getParam(int index) 
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _paramList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Param) _paramList.elementAt(index);
    } //-- Param getParam(int) 

    /**
    **/
    public Param[] getParam() {
        int size = _paramList.size();
        Param[] mArray = new Param[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Param) _paramList.elementAt(index);
        }
        return mArray;
    } //-- Param[] getParam() 

    /**
    **/
    public int getParamCount() {
        return _paramList.size();
    } //-- int getParamCount() 

    /**
    **/
    public java.lang.String getUrl() {
        return this._url;
    } //-- java.lang.String getUrl() 

    /**
    **/
    public void removeAllParam() {
        _paramList.removeAllElements();
    } //-- void removeAllParam() 

    /**
     * 
     * @param index
    **/
    public Param removeParam(int index) {
        Object obj = _paramList.elementAt(index);
        _paramList.removeElementAt(index);
        return (Param) obj;
    } //-- Param removeParam(int) 

    /**
     * 
     * @param _className
    **/
    public void setClassName(java.lang.String _className) {
        this._className = _className;
    } //-- void setClassName(java.lang.String) 

    /**
     * 
     * @param vParam
     * @param index
    **/
    public void setParam(Param vParam, int index) 
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _paramList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _paramList.setElementAt(vParam, index);
    } //-- void setParam(Param, int) 

    /**
     * 
     * @param _url
    **/
    public void setUrl(java.lang.String _url) {
        this._url = _url;
    } //-- void setUrl(java.lang.String) 

}
