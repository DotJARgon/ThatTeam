package file_utilities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * The XMLList class is responsible for properly preparing the desired
 * format for a xml file for the desired classes using generics
 * @author  Marcelo Carpenter
 * @version  1.6
 * @since 4/18/23
 */
@XmlRootElement(namespace = "xml_wrapper")
public class XMLList<T> {
    @XmlElementWrapper(name = "xml_items")
    // XmlElement sets the name of the entities
    @XmlElement(name = "items")
    public List<T> items;
    public XMLList() {}
    public XMLList(List<T> items) {this.items = items;}
}