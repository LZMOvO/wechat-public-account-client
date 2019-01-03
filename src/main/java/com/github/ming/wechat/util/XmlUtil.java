package com.github.ming.wechat.util;

import org.eclipse.persistence.jaxb.JAXBContextFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml & bean 转换工具类
 *
 * @author Zeming
 * @date 2018/2/4 14:16
 */
public class XmlUtil {

    /**
     * bean转xml
     *
     * @param bean bean
     * @return
     */
    public static String beanToXml(Object bean) {
        try {
            JAXBContext context = JAXBContextFactory.createContext(new Class<?>[]{bean.getClass()}, null);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(bean, writer);
            String result = writer.toString();
            writer.close();
            return result;
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * xml转bean
     *
     * @param xml  xml
     * @param load bean class
     * @param <T>  class
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(String xml, Class<T> load) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(load);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
