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

/**
 * The Name Validation class. This class handles validation
 * for XML Name production types such as NCName and NMToken
 * @author <a href="mailto:kvisco@exoffice.com">Keith Visco</a>
 * @version $Revision$ $Date$
**/
public class NameValidator implements TypeValidator {
    
    
    public static final short NCNAME  = 0;
    public static final short NMTOKEN = 1;
    
    private short type = NCNAME;
    
    private boolean required = false;
    
    /**
     * Creates a new NameValidator with the default validation
     * set to NCName
    **/
    public NameValidator() {
        super();
    } //-- NameValidator

    /**
     * Creates a new NameValidator with the given validation type
     * @param type the validation type for this NameValidator
    **/
    public NameValidator(short type) {
        super();
        this.type = type;
    } //-- NMTokenValidator
    
    /** 
     * Sets whether or not a String is required (non null)
     * @param required the flag indicating whether Strings are required
    **/
    public void setRequired(boolean required) {
        this.required = required;
    } //-- setRequired
    
    public void validate(String value) 
        throws ValidationException 
    {
        if (value == null) {
            if (required) {
                String err = "this is a required field and cannot be null.";
                throw new ValidationException(err);
            }
        }
        
        switch (type) {
            
            case NMTOKEN:
                if (!ValidationUtils.isNMToken(value)) {
                    String err = "\"";
                    err += value + "\" is not a valid NMToken."; 
                    throw new ValidationException(err);
                }
            case NCNAME:
            default: 
                if (!ValidationUtils.isNCName(value)) {
                    String err = "\"";
                    err += value + "\" is not a valid NCName."; 
                    throw new ValidationException(err);
                }
                break;
        }
        
    } //-- validate
    
    /**
     * Validates the given Object
     * @param object the Object to validate
    **/
    public void validate(Object object)
        throws ValidationException 
    {
        if (object != null) 
            validate(object.toString());
        else 
            validate(null);
        
    } //-- validate
    
} //-- NameValidator