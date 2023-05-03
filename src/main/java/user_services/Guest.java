package user_services;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * The Guest class is responsible for having the behavior of a guest
 * account, extending from the account class in order to have all
 * the basic functionality an account requires as well as its own
 * unique functionality
 * @author  Alexzander DeVries
 * @version  1.6
 * @since 3/25/23
 */
@XmlRootElement(name = "guest")
public class Guest extends Account {
	private Vector<Integer> reservations;
	private String address, corporation, cardFirstName, cardLastName, creditCardNum, cw;
	private Date creditCardExpiration;

	/**
	 * The default constructor of the Guest object
	 */
	public Guest() {
		super();
		reservations = new Vector<>();
		address = "";
		creditCardNum = "";
		creditCardExpiration = Calendar.getInstance().getTime();
		cardFirstName = "";
		cardLastName = "";
		creditCardNum = "";
		cw = "";
		corporation = "";
	}

	public Guest(String username, String password, String firstName, String lastName, int id, String securityQ, String securityA) {
		super(username,password,firstName,lastName, id, securityQ,securityA);
		reservations = new Vector<>();
		address = "";
		creditCardNum = "";
		creditCardExpiration = Calendar.getInstance().getTime();
		cardFirstName = "";
		cardLastName = "";
		creditCardNum = "";
		cw = "";
		corporation = "";
	}

	public void addReservation(int r) {
		reservations.add(r);
	}

	public Vector<Integer> getReservations() {
		return reservations;
	}

	public void setReservations(Vector<Integer> roomNums) {
		this.reservations = roomNums;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardNum() {
		return creditCardNum;
	}

	public void setCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public Date getCardExpiration() {
		return creditCardExpiration;
	}

	public void setCardExpiration(Date creditCardExpiration) {
		this.creditCardExpiration = creditCardExpiration;
	}

	/**
	 * cancelReservation will remove the room from the vector of room ids
	 * in the Guest object
	 * @param resID the room id of the room being removed
	 */
	public void cancelReservation(Integer resID) {
		reservations.remove(resID);
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getCardFirstName() {
		return cardFirstName;
	}

	public void setCardFirstName(String cardFirstName) {
		this.cardFirstName = cardFirstName;
	}

	public String getCardLastName() {
		return cardLastName;
	}

	public void setCardLastName(String cardLastName) {
		this.cardLastName = cardLastName;
	}

	public String getCw() {
		return cw;
	}

	public void setCw(String cw) {
		this.cw = cw;
	}
}
