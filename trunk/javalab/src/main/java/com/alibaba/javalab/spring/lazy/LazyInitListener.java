/**
 * Function: 
 * 
 * File Created at 2010-6-2
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.spring.lazy;

import org.springframework.beans.factory.parsing.AliasDefinition;
import org.springframework.beans.factory.parsing.ComponentDefinition;
import org.springframework.beans.factory.parsing.DefaultsDefinition;
import org.springframework.beans.factory.parsing.ImportDefinition;
import org.springframework.beans.factory.parsing.ReaderEventListener;
import org.springframework.beans.factory.xml.DocumentDefaultsDefinition;

/**
 * @author li.jinl
 */
public class LazyInitListener implements ReaderEventListener {

    private static final String LAZY_INIT = "true";

    @Override
    public void defaultsRegistered(DefaultsDefinition defaultsDefinition) {
        //set lazy init true
        if (defaultsDefinition instanceof DocumentDefaultsDefinition) {
            DocumentDefaultsDefinition defaults = (DocumentDefaultsDefinition) defaultsDefinition;
            defaults.setLazyInit(LAZY_INIT);
        }
    }

    @Override
    public void aliasRegistered(AliasDefinition aliasDefinition) {
        //no-op
    }

    @Override
    public void componentRegistered(ComponentDefinition componentDefinition) {
        //no-op
    }

    @Override
    public void importProcessed(ImportDefinition importDefinition) {
        //no-op
    }

}
