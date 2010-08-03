/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.autoconf;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

/**
 * @author li.jinl 2010-7-13 下午01:48:43
 */
public class Autoconf {

    private static final String    MSG_NOT_USED = "Not Used: PROP={0} AutoConfFile={1}";

    private Set<Propinfo>          props        = new HashSet<Propinfo>();

    private Set<String>            usedProps    = new HashSet<String>();
    private List<AutoconfTemplate> templates    = new ArrayList<AutoconfTemplate>();

    public Autoconf(List<String> files){
        init(files);
    }

    public void validate() {
        for (Propinfo prop : props) {
            boolean flag = usedProps.contains(prop.getProp());
            for (AutoconfTemplate at : templates) {
                flag |= at.contains(prop.getProp());
            }
            if (!flag) {
                System.out.println(MessageFormat.format(MSG_NOT_USED, prop.getProp(), prop.getDefFile()));
            }
        }
    }

    private void init(List<String> files) {
        for (String file : files) {
            String f = StringUtils.replace(file, "\\", "/");
            String base = StringUtils.substringBeforeLast(file, "/");

            try {
                parser(base, f);
            } catch (Exception e) {
                throw new AutoconfException("Parser error.");
            }
        }

    }

    private void parser(String base, String file) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new File(file));
        List<DefaultElement> pnodes = doc.selectNodes("config/group/property");

        StringBuilder defaults = new StringBuilder();
        for (DefaultElement node : pnodes) {
            String prop = node.attributeValue("name");
            props.add(new Propinfo(StringUtils.replace(prop, ".", "_"), file));
            // default valus
            defaults.append(node.attributeValue("defaultValue"));
        }

        List<DefaultElement> tnodes = doc.selectNodes("config/script/generate");
        for (DefaultElement node : tnodes) {
            templates.add(new AutoconfTemplate(base + "/" + node.attribute("template").getText()));
        }

        Set<String> tmp = AutoconfUtil.parsePlaceholder(defaults.toString());
        for (String str : tmp) {
            usedProps.add(StringUtils.replace(str, ".", "_"));
        }
    }

    public static final class Propinfo {

        private String prop;
        private String defFile;

        public Propinfo(String prop, String defFile){
            this.prop = prop;
            this.defFile = defFile;
        }

        public String getProp() {
            return prop;
        }

        public String getDefFile() {
            return defFile;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((defFile == null) ? 0 : defFile.hashCode());
            result = prime * result + ((prop == null) ? 0 : prop.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Propinfo other = (Propinfo) obj;
            if (defFile == null) {
                if (other.defFile != null) {
                    return false;
                }
            } else if (!defFile.equals(other.defFile)) {
                return false;
            }
            if (prop == null) {
                if (other.prop != null) {
                    return false;
                }
            } else if (!prop.equals(other.prop)) {
                return false;
            }
            return true;
        }

    }

}
