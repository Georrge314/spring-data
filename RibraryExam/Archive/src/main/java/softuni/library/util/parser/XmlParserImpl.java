package softuni.library.util.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserImpl implements XmlParser {
    @Override
    @SuppressWarnings("unchecked")
    public <O> O parserXml(Class<O> objectClass, String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (O) unmarshaller.unmarshal(new File(filePath));
    }

    @Override
    public <O> void exportXml(O object, Class<O> objectClass, String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(filePath));
    }
}
