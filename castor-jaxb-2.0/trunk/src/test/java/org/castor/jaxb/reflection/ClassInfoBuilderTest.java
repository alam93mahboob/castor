/*
 * Copyright 2007 Joachim Grueneis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.castor.jaxb.reflection;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

import org.castor.jaxb.reflection.info.ClassInfo;
import org.castor.jaxb.reflection.info.JaxbClassNature;
import org.castor.jaxb.reflection.info.JaxbFieldNature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Joachim Grueneis, jgrueneis_at_gmail_dot_com
 * @version $Id$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/castor-jaxb-test-context.xml" })
public class ClassInfoBuilderTest {
    
    /**
     * Object under test.
     */
    @Autowired
    private ClassInfoBuilder classInfoBuilder;

    @Test
    public final void testNull() {
        try {
            classInfoBuilder.buildClassInfo(null);
            Assert.fail("'null' cannot be turned into a ClassInfo");
        } catch (IllegalArgumentException e) {
            // right as expected
        }
    }

    @Test
    public final void testObject() {
        try {
            ClassInfo ci = classInfoBuilder.buildClassInfo(Object.class);
            Assert.assertNull("'Object.class' cannot be turned into a ClassInfo", ci);
        } catch (IllegalArgumentException e) {
            // right as expected
        }
    }

    @XmlRootElement(name = "Artist")
    private class Artist {
        private String _name;
        private Date _birthday;
        private String _biography;
        @XmlElement(name = "Name", type = String.class)
        public final String getName() {
            return _name;
        }
        public final void setName(final String name) {
            _name = name;
        }
        @XmlElement(name = "Birthday", type = Date.class)
        public final Date getBirthday() {
            return _birthday;
        }
        public final void setBirthday(final Date birthday) {
            _birthday = birthday;
        }
        @XmlElement(name = "Biography", type = String.class)
        public final String getBiography() {
            return _biography;
        }
        public final void setBiography(final String biography) {
            _biography = biography;
        }
    }

    @Test
    public final void testArtist() {
        JaxbClassNature ci = new JaxbClassNature(classInfoBuilder.buildClassInfo(Artist.class));
        Assert.assertNotNull("ClassInfo generated must not be null", ci);
        Assert.assertEquals("Artist", ci.getRootElementName());
        Assert.assertEquals(Artist.class, ci.getType());
//        Assert.assertEquals(3, ci.getFieldInfos().size());
        for (JaxbFieldNature fieldInfo : ci.getFields()) {
            if ("Name".equals(fieldInfo.getElementName())) {
                Assert.assertEquals("Name", fieldInfo.getElementName());
                Assert.assertEquals(String.class, fieldInfo.getElementType());
            } else if ("Birthday".equals(fieldInfo.getElementName())) {
                Assert.assertEquals("Birthday", fieldInfo.getElementName());
                Assert.assertEquals(Date.class, fieldInfo.getElementType());
            } else if ("Biography".equals(fieldInfo.getElementName())) {
                Assert.assertEquals("Biography", fieldInfo.getElementName());
                Assert.assertEquals(String.class, fieldInfo.getElementType());
            } else {
                Assert.fail("Unknown fieldInfo: " + fieldInfo);
            }
        }
    }
    
    /**
     * A class without any annotation.
     */
    private class NoXmlElementAnnotations {
        private String noElementAnnotation;
        private int _intWithNoAnnotation;
    }

    @Test
    public void testNoXmlElementAnnotations() {
        JaxbClassNature ci = new JaxbClassNature(classInfoBuilder.buildClassInfo(NoXmlElementAnnotations.class));
        Assert.assertNotNull("ClassInfo generated must not be null", ci);
        Assert.assertNull(
                "Without XmlRootElement annotation this has to be null", ci.getRootElementName());
        Assert.assertNull(
                "Without XmlRootElement annotation this has to be null",
                ci.getRootElementNamespace());
        Assert.assertEquals(NoXmlElementAnnotations.class, ci.getType());
        Assert.assertEquals("One property leads to one field info", 2, ci.getFields().size());
        List < JaxbFieldNature > fis = ci.getFields();
        Assert.assertNull("Without XmlElement no element name is set", fis.get(0).getElementName());
        Assert.assertNull("Without XmlAttribute no attribute name is set", fis.get(0).getAttributeName());
        Assert.assertNull("Without XmlElement no element name is set", fis.get(1).getElementName());
        Assert.assertNull("Without XmlAttribute no attribute name is set", fis.get(1).getAttributeName());
    }
    
    /**
     * A class with annotations but without any names given.
     */
    @XmlRootElement
    private class EmptyXmlElementAnnotations {
        @XmlElement
        private String emptyElementAnnotation;
        @XmlAttribute
        private String emptyAttributeAnnotation;
    }

    @Test
    public void testEmptyXmlElementAnnotations() {
        JaxbClassNature ci = new JaxbClassNature(classInfoBuilder.buildClassInfo(EmptyXmlElementAnnotations.class));
        Assert.assertNotNull("ClassInfo generated must not be null", ci);
        Assert.assertNull(ci.getRootElementName());
        Assert.assertNull(ci.getRootElementNamespace());
        Assert.assertEquals(EmptyXmlElementAnnotations.class, ci.getType());
        Assert.assertEquals("Two properties lead to two field infos", 2, ci.getFields().size());
        List < JaxbFieldNature > fis = ci.getFields();
        Assert.assertNull("XmlElement.name is not set", fis.get(0).getElementName());
        Assert.assertNull("Without XmlAttribute no attribute name is set", fis.get(0).getAttributeName());
        Assert.assertNull("Without XmlElement no element name is set", fis.get(1).getElementName());
        Assert.assertNull("XmlAttribute.name is not set", fis.get(1).getAttributeName());
    }
    
    /**
     * A class with annotations that contain names.
     */
    @XmlRootElement(name = "NamedXmlElement")
    private class NamedXmlElementAnnotations {
        @XmlElement(name = "NamedElement")
        private String namedElementAnnotation;
        @XmlAttribute(name = "NamedAttribute")
        private String namedAttributeAnnotation;
    }

    @Test
    public void testNamedXmlElementAnnotations() {
        JaxbClassNature ci = new JaxbClassNature(classInfoBuilder.buildClassInfo(NamedXmlElementAnnotations.class));
        Assert.assertNotNull("ClassInfo generated must not be null", ci);
        Assert.assertEquals("NamedXmlElement", ci.getRootElementName());
        Assert.assertNull(ci.getRootElementNamespace());
        Assert.assertEquals(NamedXmlElementAnnotations.class, ci.getType());
        Assert.assertEquals("Two properties lead to two field infos", 2, ci.getFields().size());
        List < JaxbFieldNature > fis = ci.getFields();
        Assert.assertEquals("NamedElement", fis.get(0).getElementName());
        Assert.assertNull("Without XmlAttribute no attribute name is set", fis.get(0).getAttributeName());
        Assert.assertNull("Without XmlElement no element name is set", fis.get(1).getElementName());
        Assert.assertEquals("NamedAttribute", fis.get(1).getAttributeName());
    }
    
    public enum FranzWithoutAnnotations {
        TEST1(1),
        TEST2(2),
        TEST3(3);
        private int _i;
        private FranzWithoutAnnotations(final int i) {
            _i = i;
        }
        public int getI() {
            return _i;
        }
    }

    @Test
    public void testEnumNoAnnotations() {
        ClassInfo ci = classInfoBuilder.buildClassInfo(FranzWithoutAnnotations.class);
        JaxbClassNature jcn = new JaxbClassNature(ci);
        Assert.assertNotNull("ClassInfo generated must not be null", jcn);
        Assert.assertNull(jcn.getRootElementName());
        Assert.assertNull(jcn.getRootElementNamespace());
        Assert.assertEquals(FranzWithoutAnnotations.class, jcn.getType());
        // here I should check if it is an enum...
    }
    
    @XmlEnum
    public enum FranzWithAnnotations {
        TEST1(1),
        TEST2(2),
        TEST3(3);
        private int _i;
        private FranzWithAnnotations(final int i) {
            _i = i;
        }
        public int getI() {
            return _i;
        }
    }

    @Test
    public void testEnumWithAnnotations() {
        ClassInfo ci = classInfoBuilder.buildClassInfo(FranzWithAnnotations.class);
        JaxbClassNature jcn = new JaxbClassNature(ci);
        Assert.assertNotNull("ClassInfo generated must not be null", jcn);
        Assert.assertNull(jcn.getRootElementName());
        Assert.assertNull(jcn.getRootElementNamespace());
        Assert.assertEquals(FranzWithAnnotations.class, jcn.getType());
    }
    
//    JAXBElement<T> a;
//    
//    public class MixedContentSample {
//        @XmlMixed
//        private List _mixedContent;        
//    }
//    
//    @XmlElementRef
//    public class ElementRefSample {
//        
//    }
}
