package cn.zeroall.cow.dal.product.xml;

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

import cn.zeroall.cow.dal.product.dao.WashingDAO;
import cn.zeroall.cow.dal.product.po.WashingPO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public class WashingXmlDAO implements WashingDAO {

    private static final Log log = LogFactory.getLog(WashingXmlDAO.class);

    private String xmlFile;
    private String xmlEncoding;

    @Override
    public List<WashingPO> findWashings() {
        XStream xstream = new XStream();
        xstream.alias("washings", List.class);
        xstream.alias("washing", WashingPO.class);
        xstream.registerConverter(new WashingConvert());

        List<WashingPO> list = null;
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(getXml()), xmlEncoding);
            list = (List<WashingPO>) xstream.fromXML(reader);
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

    private static class WashingConvert implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
            // do nothing..
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            WashingPO washingPO = new WashingPO();
            washingPO.setId(Integer.valueOf(reader.getAttribute("id")));
            washingPO.setName(reader.getAttribute("name"));
            washingPO.setImgName(reader.getAttribute("imgName"));
            return washingPO;
        }

        @Override
        public boolean canConvert(Class type) {
            return type == WashingPO.class;
        }

    }

}
