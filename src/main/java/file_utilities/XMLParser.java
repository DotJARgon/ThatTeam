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
/**
 * The XMLParser class is responsible for saving and loading the desired
 * classes into and out of xml format in order to store variables properly
 * @author  Marcelo Carpenter
 * @version  1.6
 * @since 4/18/23
 */
public class XMLParser {
    /**
     * This function saves the desired class types and values into a xml file
     * based on the proper xml format
     * @param items The list of items being entered
     * @param location The file being used as a xml file
     * @param classes The class types being converted into a xml file
     */
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

    /**
     *
     * @param location The file being read as a xml file
     * @param classes The class types being converted into objects from the
     *                xml file
     * @return List of desired objects from the read xml file
     * @throws FileNotFoundException if the file does not exist, throw exception
     */
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
