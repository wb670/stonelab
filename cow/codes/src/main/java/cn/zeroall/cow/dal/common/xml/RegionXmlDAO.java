package cn.zeroall.cow.dal.common.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;

import cn.zeroall.chameleon.vtree.Node;
import cn.zeroall.cow.dal.common.dao.RegionDAO;
import cn.zeroall.cow.dal.common.po.RegionPO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 17, 2009
 */
public class RegionXmlDAO implements RegionDAO {

    private static final Log log = LogFactory.getLog(RegionXmlDAO.class);

    private String xmlFile;
    private String xmlEncoding;

    @Override
    public List<RegionPO> findByType(String type) {
        XStream xstream = new XStream();
        xstream.alias("nodes", List.class);
        xstream.alias("node", Node.class);
        xstream.registerConverter(new NodeConvert());

        List<RegionPO> list = null;
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(getXml()), xmlEncoding);
            list = (List<RegionPO>) xstream.fromXML(reader);
        } catch (UnsupportedEncodingException e) {
            log.error("read from xmlFile error, encoding is unsupported.", e);
        } catch (FileNotFoundException e) {
            log.error("read from xmlFile error, xml file is not found.", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("close xml file error.", e);
                }
            }
        }
        return list;
    }

    private File getXml() throws FileNotFoundException {
        return ResourceUtils.getFile(xmlFile);
    }

    @Override
    public void add(RegionPO area) {
        throw new UnsupportedOperationException("add method is unsupported.");
    }

    @Override
    public void delete(RegionPO area) {
        throw new UnsupportedOperationException("delete method is unsupported.");
    }

    @Override
    public void update(RegionPO area) {
        throw new UnsupportedOperationException("update method is unsupported.");
    }

    public String getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public String getXmlEncoding() {
        return xmlEncoding;
    }

    public void setXmlEncoding(String xmlEncoding) {
        this.xmlEncoding = xmlEncoding;
    }

    public static class NodeConvert implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
            return;
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            RegionPO node = new RegionPO();
            node.setId(Integer.valueOf(reader.getAttribute("id")));
            node.setParentId(Integer.valueOf(reader.getAttribute("parentId")));
            node.setName(reader.getAttribute("name"));
            node.setDescription(reader.getAttribute("description"));
            return node;
        }

        @Override
        public boolean canConvert(Class type) {
            return (type == Node.class);
        }
    }

}
