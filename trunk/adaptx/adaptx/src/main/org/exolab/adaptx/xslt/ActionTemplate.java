/*
 * (C) Copyright Keith Visco 1998, 1999  All rights reserved.
 *
 * The contents of this file are released under an Open Source 
 * Definition (OSD) compliant license; you may not use this file 
 * execpt in compliance with the license. Please see license.txt, 
 * distributed with this file. You may also obtain a copy of the
 * license at http://www.clc-marketing.com/xslp/license.txt
 *
 * The program is provided "as is" without any warranty express or
 * implied, including the warranty of non-infringement and the implied
 * warranties of merchantibility and fitness for a particular purpose.
 * The Copyright owner will not be liable for any damages suffered by
 * you as a result of using the Program. In no event will the Copyright
 * owner be liable for any special, indirect or consequential damages or
 * lost profits even if the Copyright owner has been advised of the
 * possibility of their occurrence.
 *
 * $Id$
 */
package org.exolab.adaptx.xslt;

/**
 * This interface that represents a set of XSLT actions. 
 * @author <a href="mailto:kvisco@ziplink.net">Keith Visco</a>
 * @version $Revision$ $Date$
**/
public interface ActionTemplate {
    
    
    /**
     * Returns an Iteration of the XSLObjects (actions) in this template
     * @return an Iteration of the XSLObjects (actions) in this template
    **/
    public ActionIterator actions();
    
    /**
     * Returns the number of XSLObjects (actions) in this template
     * @return the number of XSLObjects (actions) in this template
    **/
    public int size();
    
} //-- ActionTemplate

