package user_services;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.Vector;


@XmlRootElement(name = "guest")
public class Guest extends Account {
	private Vector<Integer> reservations;
	private String address, corporation;
	private int creditCardNum;
	private Date creditCardExpiration;

	public Guest() {
		super();
		reservations = new Vector<>();
		address = "";
		creditCardNum = 0;
		creditCardExpiration = new Date();
		corporation = new String("");
	}

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
