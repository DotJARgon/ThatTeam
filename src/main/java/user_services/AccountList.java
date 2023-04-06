package user_services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(namespace = "accountsList")
public class AccountList {
    // XmLElementWrapper generates a wrapper element around XML representation
    @XmlElementWrapper(name = "accounts")
    // XmlElement sets the name of the entities
    @XmlElement(name = "user")
    private List<Account> accounts;

    public void setAccountsList(List<Account> accounts) {this.accounts = accounts;}
    public List<Account> getAccountsList() {return this.accounts;}
}
