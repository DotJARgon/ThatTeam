package user_services;

import javax.xml.bind.annotation.XmlRootElement;


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
