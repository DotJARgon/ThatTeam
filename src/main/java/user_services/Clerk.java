package user_services;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Clerk class is responsible for having the behavior of a clerk
 * account, extending from the account class in order to have all
 * the basic functionality an account requires as well as its own
 * unique functionality, which is to help the corresponding guest object it
 * is given to modify.
 * @author  Alexzander DeVries
 * @version  1.6
 * @since 3/25/23
 */
@XmlRootElement(name = "clerk")
public class Clerk extends Account{
	Guest helpingGuest;
	
	public void setGuest(Guest g) {
		helpingGuest = g;
	}
	public Guest getGuest() {
		return helpingGuest;
	}
}
