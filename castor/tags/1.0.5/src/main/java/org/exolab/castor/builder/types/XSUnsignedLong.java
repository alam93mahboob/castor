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
 * Copyright 1999-2002 (C) Intalio, Inc. All Rights Reserved.
 *
 * $Id: XSLong.java 6317 2006-10-17 14:24:18Z wguttmn $
 */

package org.exolab.castor.builder.types;

import java.math.BigInteger;
import java.util.Enumeration;

import org.exolab.castor.xml.schema.Facet;
import org.exolab.castor.xml.schema.SimpleType;
import org.exolab.javasource.JClass;
import org.exolab.javasource.JSourceCode;
import org.exolab.javasource.JType;
/**
 * The XML Schema long type
 * @author <a href="mailto:kvisco@intalio.com">Keith Visco</a>
 * @version $Revision: 6317 $ $Date: 2006-04-25 15:08:23 -0600 (Tue, 25 Apr 2006) $
**/
public class XSUnsignedLong extends XSPatternBase {

	//- Constraints for long type
	BigInteger maxInclusive = null;
    BigInteger maxExclusive = null;
    BigInteger minInclusive = null;
    BigInteger minExclusive = null;

	/**
	 * The JType represented by this XSType
	**/
	private static JType jType = new JClass("java.math.BigInteger");

	public XSUnsignedLong() {
		super(XSType.UNSIGNED_LONG_TYPE);
        setMinInclusive(BigInteger.valueOf(0));
        setMaxInclusive(new BigInteger("18446744073709551615"));
        
	} //-- XSLong

	/**
	 * Returns the JType that this XSType represents
	 * @return the JType that this XSType represents
	**/
	public JType getJType() {
		return jType;
	}

	/**
	 * Returns the maximum exclusive value that this XSLong can hold.
	 * @return the maximum exclusive value that this XSLong can hold. If
	 * no maximum exclusive value has been set, Null will be returned
	 * @see #getMaxInclusive
	**/
	public BigInteger getMaxExclusive() {
		return maxExclusive;
	} //-- getMaxExclusive

	/**
	 * Returns the maximum inclusive value that this XSLong can hold.
	 * @return the maximum inclusive value that this XSLong can hold. If
	 * no maximum inclusive value has been set, Null will be returned
	 * @see #getMaxExclusive
	**/
	public BigInteger getMaxInclusive() {
		return maxInclusive;
	} //-- getMaxInclusive

	/**
	 * Returns the minimum exclusive value that this XSLong can hold.
	 * @return the minimum exclusive value that this XSLong can hold. If
	 * no minimum exclusive value has been set, Null will be returned
	 * @see #getMinInclusive()
	 * @see #setMaxInclusive(long)
	**/
	public BigInteger getMinExclusive() {
		return minExclusive;
	} //-- getMinExclusive

	/**
	 * Returns the minimum inclusive value that this XSLong can hold.
	 * @return the minimum inclusive value that this XSLong can hold. If
	 * no minimum inclusive value has been set, Null will be returned
	 * @see #getMinExclusive
	**/
	public BigInteger getMinInclusive() {
		return minInclusive;
	} //-- getMinInclusive

	public boolean hasMaximum() {
		return ((maxInclusive != null) || (maxExclusive != null));
	} //-- hasMaximum

	public boolean hasMinimum() {
		return ((minInclusive != null) || (minExclusive != null));
	} //-- hasMinimum

	//public String toString() {
	//    return value.toString();
	//}

	/**
	 * Sets the maximum exclusive value that this XSLong can hold.
	 * @param max the maximum exclusive value this XSLong can be
	 * @see #setMaxInclusive(Long)
	**/
	public void setMaxExclusive(BigInteger max) {
		maxExclusive = max;
		maxInclusive = null;
	} //-- setMaxExclusive

	/**
	 * Sets the maximum inclusive value that this XSLong can hold.
	 * @param max the maximum inclusive value this XSLong can be
	 * @see #setMaxExclusive(Long)
	**/
	public void setMaxInclusive(BigInteger max) {
		maxInclusive = max;
		maxExclusive = null;
	} //-- setMaxInclusive

	/**
	 * Sets the minimum exclusive value that this XSLong can hold.
	 * @param min the minimum exclusive value this XSLong can be
	 * @see #setMinInclusive(Long)
	**/
	public void setMinExclusive(BigInteger min) {
		minExclusive = min;
		minInclusive = null;
	} //-- setMinExclusive

	/**
	 * Sets the minimum inclusive value that this XSLong can hold.
	 * @param min the minimum inclusive value this XSLong can be
	 * @see #setMinExclusive(long)
	**/
	public void setMinInclusive(BigInteger min) {
		minInclusive = min;
		minExclusive = null;
	} //-- setMinInclusive

	/**
	 * Reads and sets the facets for XSTimeDuration
	 * override the readFacet method of XSType
	 * @param simpleType the Simpletype containing the facets
	 * @see org.exolab.castor.builder.types.XSType#getFacets
	 */
	public void setFacets(SimpleType simpleType) {

		//-- copy valid facets
		Enumeration enumeration = getFacets(simpleType);
		while (enumeration.hasMoreElements()) {

			Facet facet = (Facet) enumeration.nextElement();
			String name = facet.getName();

			//-- maxExclusive
			if (Facet.MAX_EXCLUSIVE.equals(name))
				setMaxExclusive(new BigInteger(facet.getValue()));
			//-- maxInclusive
			else if (Facet.MAX_INCLUSIVE.equals(name))
				setMaxInclusive(new BigInteger(facet.getValue()));
			//-- minExclusive
			else if (Facet.MIN_EXCLUSIVE.equals(name))
				setMinExclusive(new BigInteger(facet.getValue()));
			//-- minInclusive
			else if (Facet.MIN_INCLUSIVE.equals(name))
				setMinInclusive(new BigInteger(facet.getValue()));
			//-- pattern
			else if (Facet.PATTERN.equals(name))
				setPattern(facet.getValue());
		} //setFacets

	} //-- readLongFacets
	/**
	 * Returns the String necessary to convert an instance of this XSType
	 * to an Object. This method is really only useful for primitive types
	 * @param variableName the name of the instance variable
	 * @return the String necessary to convert an instance of this XSType
	 * to an Object
	**/
	public String createToJavaObjectCode(String variableName) {
	    StringBuffer sb = new StringBuffer("");
	    sb.append(variableName);
	    sb.append("");
	    return sb.toString();
	} //-- toJavaObject

	/**
	 * Returns the String necessary to convert an Object to
	 * an instance of this XSType. This method is really only useful
	 * for primitive types
	 * @param variableName the name of the Object
	 * @return the String necessary to convert an Object to an
	 * instance of this XSType
	**/
	public String createFromJavaObjectCode(String variableName) {
		StringBuffer sb = new StringBuffer("((java.math.BigInteger)");
		sb.append(variableName);
		sb.append(")");
		return sb.toString();
	} //-- fromJavaObject

		/**
	 * Creates the validation code for an instance of this XSType. The validation
     * code should if necessary create a newly configured TypeValidator, that
     * should then be added to a FieldValidator instance whose name is provided.
	 * 
	 * @param fixedValue a fixed value to use if any
	 * @param jsc the JSourceCode to fill in.
     * @param fieldValidatorInstanceName the name of the FieldValidator
     * that the configured TypeValidator should be added to.
	 */
	public void validationCode (JSourceCode jsc, String fixedValue, String fieldValidatorInstanceName) {

		if (jsc == null)
			jsc = new JSourceCode();
		
		jsc.add("org.exolab.castor.xml.validators.BigIntegerValidator typeValidator = new org.exolab.castor.xml.validators.BigIntegerValidator();");
		if (hasMinimum()) {
			BigInteger min = getMinExclusive();
			if (min != null)
				jsc.add("typeValidator.setMinExclusive(new java.math.BigInteger(\"");
			else {
				min = getMinInclusive();
				jsc.add("typeValidator.setMinInclusive(new java.math.BigInteger(\"");
			}
			jsc.append(min.toString());
			jsc.append("\"));");
		}
		if (hasMaximum()) {
			BigInteger max = getMaxExclusive();
			if (max != null)
				jsc.add("typeValidator.setMaxExclusive(new java.math.BigInteger(\"");
			else {
				max = getMaxInclusive();
				jsc.add("typeValidator.setMaxInclusive(new java.math.BigInteger(\"");
			}
			jsc.append(max.toString());
			jsc.append("\"));");
		}

		//-- fixed values
		if (fixedValue != null) {
			//-- make sure we have a valid value...
			BigInteger value = new BigInteger(fixedValue);

			jsc.add("typeValidator.setFixed(new BigInteger(\"");
			jsc.append(fixedValue);
			jsc.append("\");");
		}
		//-- pattern facet
		String pattern = getPattern();
		if (pattern != null) {
			jsc.add("typeValidator.setPattern(\"");
			jsc.append(escapePattern(pattern));
			jsc.append("\");");
		}
		jsc.add(fieldValidatorInstanceName+".setValidator(typeValidator);");
		
	}

        public String newInstanceCode() {
            return "new "+getJType().getName()+"(\"0\");";
        }
    
    

} //-- XSLong
