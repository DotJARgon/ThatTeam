package file_utilities;

import user_services.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class XMLParser {

    public static <T> void save(List<T> items, String location, Class<?>... classes) {
        XMLList<T> xmlItems = new XMLList<>(items);
        try {
            JAXBContext context = JAXBContext.newInstance(classes);
            Marshaller m = context.createMarshaller();
            m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write to System.out
            m.marshal(xmlItems, System.out);

            // Write to File
            m.marshal(xmlItems, new File(location));
        }
        catch(JAXBException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> load(String location, Class<?>... classes) throws FileNotFoundException {
        XMLList<T> xml;
        try {
            JAXBContext context = JAXBContext.newInstance(classes);
            Unmarshaller um = context.createUnmarshaller();
            xml = (XMLList<T>) um.unmarshal(new FileReader(location));
        } catch (JAXBException e) {
            e.printStackTrace();

            throw new FileNotFoundException();
        }
        return xml.items;
    }
}
