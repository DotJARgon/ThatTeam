package file_utilities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(namespace = "xml_wrapper")
public class XMLList<T> {
    @XmlElementWrapper(name = "xml_items")
    // XmlElement sets the name of the entities
    @XmlElement(name = "items")
    public List<T> items;
    public XMLList() {}
    public XMLList(List<T> items) {this.items = items;}
}