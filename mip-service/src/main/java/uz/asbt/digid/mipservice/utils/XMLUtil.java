package uz.asbt.digid.mipservice.utils;

import uz.asbt.digid.mipservice.models.DataCEPResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLUtil {

    public static String jaxbObjectToXML(Object obj) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(obj, sw);
        String xmlContent = sw.toString();
        return xmlContent;
    }

    public static DataCEPResponse jaxbXMLToObject(String str) throws JAXBException {
        StringReader sr = new StringReader(str);
        JAXBContext jaxbContext = JAXBContext.newInstance(DataCEPResponse.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        DataCEPResponse unmarshal = (DataCEPResponse) unmarshaller.unmarshal(sr);
        return unmarshal;
    }

}
