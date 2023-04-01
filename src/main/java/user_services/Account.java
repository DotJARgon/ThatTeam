package user_services;

import java.util.Vector;
import java.security.*;
public class Account {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private int id;
    private Vector<Integer> roomNums;
    public Account() {
        username = "";
        password = "";
        email = "";
        firstName = "";
        lastName = "";
        phoneNumber = "";
        address = "";
        city = "";
        state = "";
        zipCode = "";
        country = "";
        id = 0;
        roomNums = null;
    }
    Account(String username, String password, String email, String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String country, int id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.id = id;
        roomNums = new Vector<Integer>();
    }

    Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Vector<Integer> getReservations() {
        return roomNums;
    }
    public void setReservations(Vector<Integer> roomNums) {
        this.roomNums = roomNums;
    }
    private String md5(String ptxt, String salt) { //salt is meant to be the username
        String ptxtSalt = ptxt + salt; // concatenate the password and salt
        String result = null; // initialize the generated password
        try {  //code is from https://www.mkyong.com/java/java-md5-hashing-example/, thinking of using a different hashing algorithm, like sha256
          MessageDigest md = MessageDigest.getInstance("MD5"); 
          md.update(ptxtSalt.getBytes());
          byte[] bytes = md.digest();
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
          }
          result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
        return result;
    }
}
