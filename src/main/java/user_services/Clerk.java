package user_services;

import hotel_management.Reservation;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "clerk")
public class Clerk extends Account{
	public void checkIn(Reservation res, Guest g) {
		
	}
	public void checkOut(Reservation res, Guest g) {
		
	}
	public void modifyRoomInfo(int roomID) {
		
	}
}
