package user_services;

import hotel_management.HotelManagement;
import hotel_management.Reservation;
import hotel_management.Room;

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
	public void setGuest(Guest g) {
		helpingGuest = g;
	}
	public Guest getGuest() {
		return helpingGuest;
	}
}
