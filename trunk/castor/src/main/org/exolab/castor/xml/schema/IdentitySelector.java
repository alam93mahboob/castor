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
 * Copyright 2001 (C) Intalio, Inc. All Rights Reserved.
 *
 * $Id$
 */


package org.exolab.castor.xml.schema;

import org.exolab.castor.xml.ValidationException;

/**
 * A class which represents the selector for an IdentityConstraint
 *
 * @author <a href="mailto:kvisco@intalio.com">Keith Visco</a>
 * @version $Revision$ $Date$
**/
public class IdentitySelector extends Annotated {
    /** SerialVersionUID */
    private static final long serialVersionUID = -66732684639814508L;

    /**
     * Identity Selector id (optional)
    **/
    private String _id   = null;
    
    /**
     * XPath expression for selector nodes (required)
    **/
    private String _xpath = null;
    
    /**
     * Creates a new IdentitySelector.
     *
     * @param xpath, the xpath for the IdentitySelector. Must not be null.
     * @exception SchemaException if xpath is null.
    **/
    public IdentitySelector(String xpath) 
        throws SchemaException
    {
        setXPath(xpath);
    } //-- IdentitySelector

    /**
     * Returns the Id of this IdentitySelector, or null if no
     * Id has been set.
     *
     * @return the Id of this IdentitySelector, or null if no
     * Id has been set.
    **/
    public String getId() {
        return _id;
    } //-- getId
    
    /**
     * Returns the XPath of this IdentitySelector. This value will
     * never be null.
     *
     * @return the XPath of this IdentitySelector.
    **/
    public String getXPath() {
        return _xpath;
    } //-- getXPath

    
    /**
     * Sets the Id for this IdentitySelector.
     *
     * @param id the Id for this IdentitySelector. 
    **/
    public void setId(String id) {
        _id = id;
    } //-- setId
    
    /**
     * Sets the XPath expression for this Selector.
     *
     * @param xpath the XPath expression for this IdentitySelector. Must not 
     * be null.
     * @exception SchemaException if xpath is null.
    **/
    public void setXPath(String xpath) 
        throws SchemaException
    {
        if (xpath == null) 
            throw new SchemaException("The xpath of a 'selector' must not be null.");
            
        _xpath = xpath;
    } //-- setXPath
       
    /**
     * Returns the type of this Schema Structure
     * @return the type of this Schema Structure
    **/
    public short getStructureType() {
        return Structure.IDENTITY_SELECTOR;
    } //-- getStructureType

    /**
     * Checks the validity of this Schema defintion.
     * @exception ValidationException when this Schema definition
     * is invalid.
    **/
    public void validate()
        throws ValidationException
    {
        //-- do nothing, if it can be constructed, it's valid.
        //-- validate XPath expression in the future?
    } //-- validate
    
} //-- class: IdentitySelector