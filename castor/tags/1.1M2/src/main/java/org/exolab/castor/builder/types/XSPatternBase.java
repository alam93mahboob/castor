/*
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
 * Copyright 2000-2002 (C) Intalio, Inc. All Rights Reserved.
 *
 * $Id$
 */
package org.exolab.castor.builder.types;

import java.util.Iterator;
import java.util.LinkedList;

import org.exolab.javasource.JSourceCode;

/**
 * A base class for types which support the pattern facet.
 * @author <a href="mailto:kvisco@intalio.com">Keith Visco</a>
 * @author <a href="mailto:edward.kuns@aspect.com">Edward Kuns</a>
 * @version $Revision$ $Date: 2005-03-05 06:42:06 -0700 (Sat, 05 Mar 2005) $
 */
public abstract class XSPatternBase extends XSType {

    /** The value of the pattern facet. */
    private LinkedList _patterns = new LinkedList();

    /**
     * Creates a new XSPatternBase with the given type.
     * @param type that this XSType represents
     */
    protected XSPatternBase(final short type) {
        super(type);
    } //-- XSPatternBase

    /**
     * Adds a pattern branch for this XSType.  To successfully pass the pattern
     * facets, only one branch needs to pass.
     * @param pattern the regular expression for this XSType.
     */
    public void addPattern(final String pattern) {
        _patterns.add(pattern);
    } //-- setPattern

    /**
     * Generate the source code for pattern facet validation.
     *
     * @param jsc
     *            the JSourceCode to fill in.
     * @param validatorName
     *            the name of the TypeValidator that the patterns should be
     *            added to.
     */
    public void codePatternFacet(final JSourceCode jsc, final String validatorName) {
        for (Iterator i = _patterns.iterator(); i.hasNext(); ) {
            jsc.add(validatorName + ".addPattern(\"" + escapePattern((String) i.next()) + "\");");
        }
    }

} //-- XSPatternBase
