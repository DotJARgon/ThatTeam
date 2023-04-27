package user_services;
import hotel_management.HotelManagement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "admin")
public class Admin extends Account{
	public void addClerk(String promotedAcc) {
		Account promoted = new Clerk();
		if(HotelManagement.getHotelManagement().getAccounts().contains(promotedAcc)){
			promoted = HotelManagement.getHotelManagement().getAccounts().get(promotedAcc);
			HotelManagement.getHotelManagement().getAccounts().remove(promotedAcc);
			HotelManagement.getHotelManagement().getAccounts().put(promotedAcc, promoted);
		}
	}
	public void resetPassword() {
		//TODO: For someone involved in crypto!

	}
}
