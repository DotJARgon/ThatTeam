package user_services;

import billing_services.Billing;
import billing_services.BillingCalculator;
import hotel_management.HotelManagement;
import hotel_management.Reservation;
import hotel_management.Room;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "clerk")
public class Clerk extends Account{
	Guest helpingGuest;
	
	public void checkIn(Reservation res, Guest g) {

	}
	public void checkOut(Reservation res, Guest g) {
		
	}
	public void modifyRoomInfo(int roomID, int newNumBeds, Room.BedType newBedType, boolean newSmoke, Room.QualityType newQuality) {
		Room changeRoom = HotelManagement.getHotelManagement().getRoomByID(roomID);
		changeRoom.setNumBeds(newNumBeds);
		changeRoom.setBedType(newBedType);
		changeRoom.setCanSmoke(newSmoke);
		changeRoom.setQualityType(newQuality);
		HotelManagement.getHotelManagement().getRooms().remove(roomID);
		HotelManagement.getHotelManagement().getRooms().put(roomID, changeRoom);
	}
	//TEST TO MAKE SURE THIS WORKS
	public void cancelReservation(Reservation res, Guest g) {
		int resID = HotelManagement.getHotelManagement().getAllReservations().get(res.getID()).getID();
		for (int i : g.getReservations()) {
			if (i == resID) {
				Reservation r = HotelManagement.getHotelManagement().getAllReservations().get(resID);
				BillingCalculator.calculateCancelledCost(r);
			}
		}
	}
	public void setGuest(Guest g) {
		helpingGuest = g;
	}
	public Guest getGuest() {
		return helpingGuest;
	}
}
