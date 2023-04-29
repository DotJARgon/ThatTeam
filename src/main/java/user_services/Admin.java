package user_services;
import hotel_management.HotelManagement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "admin")
public class Admin extends Account{
	public void addClerk(String promotedAcc) {
		Clerk promoted = new Clerk();
		if(HotelManagement.getHotelManagement().getAccounts().contains(promotedAcc)){
			Account acc = HotelManagement.getHotelManagement().getAccounts().get(promotedAcc);
			promoted.setUsername(acc.getUsername());
			promoted.setFirstName(acc.getFirstName());
			promoted.setLastName(acc.getLastName());
			promoted.setId(acc.getId());
			promoted.setPassword(acc.getPassword());
			HotelManagement.getHotelManagement().getAccounts().remove(promotedAcc);
			HotelManagement.getHotelManagement().getAccounts().put(promotedAcc, promoted);
		}
	}
	public void resetPassword() {
		//TODO: For someone involved in crypto!

	}
}
