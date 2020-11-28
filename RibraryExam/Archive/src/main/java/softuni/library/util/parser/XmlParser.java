package softuni.library.util.parser;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O parserXml(Class<O> objectClass, String filePath) throws JAXBException;

   <O> void exportXml(O object, Class<O> objectClass, String filePath) throws JAXBException;
}
