package user_services;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.Vector;


@XmlRootElement(name = "guest")
public class Guest extends Account {
    private Vector<Integer> reservations;
    private String address;
    private int creditCardNum;
    private Date creditCardExpiration;
    
    public Guest(){
    	super();
    	reservations = new Vector<>();
    	address = "";
    	creditCardNum = 0;
    	creditCardExpiration = new Date();
    }
    public Guest(String u, String p, String fn, String ln, int i, String a, int n, Date exp){
    	super(u, p,fn,ln,i);
    	reservations = new Vector<>();
    	address = a;
    	creditCardNum = n;
    	creditCardExpiration = exp;
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
}
