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
	private String address, corporation;
	private int creditCardNum;
	private Date creditCardExpiration;

	/**
	 * The default constructor of the Guest object
	 */
	public Guest() {
		super();
		reservations = new Vector<>();
		address = "";
		creditCardNum = 0;
		//Random date, used to test input for credit card information
		creditCardExpiration = new Date(2023, Calendar.JUNE, 21);
		corporation = "";
	}

	/**
	 * A constructor for the Guest object
	 * @param u the username of the guest
	 * @param p the password of the guest, properly salted
	 * @param fn the firstname of the guest
	 * @param ln the last name of the guest
	 * @param i the unique user id of the guest
	 * @param a the address of the guest for billing
	 * @param n the credit card number of the guest account
	 * @param exp the credit card expiration date of the guest
	 *            account
	 * @param qS the security question of the guest
	 * @param qA the security question answer of the guest
	 */
	public Guest(String u, String p, String fn, String ln, int i, String a, int n, Date exp, String qS, String qA) {
		super(u, p, fn, ln, i, qS, qA);
		reservations = new Vector<>();
		address = a;
		creditCardNum = n;
		creditCardExpiration = exp;
		corporation = new String("");
	}

	public Guest(String username, String password, String securityA, String securityB) {
		super(username,password,securityA,securityB);
		corporation = new String("");
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

	public int getCardNum() {
		return creditCardNum;
	}

	public void setCardNum(int creditCardNum) {
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
}
