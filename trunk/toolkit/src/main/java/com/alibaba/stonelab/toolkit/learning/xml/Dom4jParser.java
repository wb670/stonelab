/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.xml;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Stone.J 2010-8-3 下午03:10:38
 */
public class Dom4jParser {

    private static final String XML_LOCATION   = "/learning/xml/note.xml";
    private static final String SHEMA_LOCATION = "/learning/xml/note.xsd";

    public static void main(String[] args) throws Exception {
        dom4j();
    }

    public static void dom4j() throws Exception {
        SAXReader reader = new SAXReader(true);
        reader.setEntityResolver(new EntityResolver());
        reader.setFeature("http://xml.org/sax/features/validation", true);
        reader.setFeature("http://apache.org/xml/features/validation/schema", true);
        Document doc = reader.read(Dom4jParser.class.getResourceAsStream(XML_LOCATION));
        System.out.println(doc);
    }

    public static class EntityResolver implements org.xml.sax.EntityResolver {

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            System.out.println("resolveEntity:" + publicId + ";" + systemId);
            InputStream in = Dom4jParser.class.getResourceAsStream(SHEMA_LOCATION);
            return new InputSource(in);
        }

    }
}
