package hotel_management;
import java.util.*;
import java.util.Random;
import java.security.*;
import java.math.*;
//we trying
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
    private boolean isManager;
    private Map<String, String> securityQuestions = new HashMap<>();
    public Vector<Integer> reservations = new Vector<>();
    Account() {
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
        isManager = false;
    }
    Account(String username, String password, String email, String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String country, int id, Vector<Integer> roomNumber, boolean isManager) {
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
        this.reservations = roomNumber; 
        this.isManager = isManager;
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
        return reservations;
    }
    public void setReservations(Vector<Integer> reservations) {
        this.reservations = reservations;
    }
    public boolean isManager() {
        return isManager;
    }
    public void setManager(boolean manager) {
        isManager = manager;
    }
    void registerUser(String username, String password, String email, String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode, String country, int id, Vector<Integer> roomNumber, boolean isManager) {
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
        this.reservations = roomNumber; 
        this.isManager = isManager;
    }
    void setSecurityQuestions(String question1, String answer1, String question2, String answer2, String question3, String answer3) {
        String toBeHashedQ, toBeHashedA;
        toBeHashedQ = md5(question1 + username);
        question1 = toBeHashedQ;
        toBeHashedA = md5(answer1 + username);
        answer1 = toBeHashedA;
        securityQuestions.put(question1, answer1);
        toBeHashedQ = md5(question2 + username);
        question2 = toBeHashedQ;
        toBeHashedA = md5(answer2 + username);
        answer2 = toBeHashedA;
        securityQuestions.put(question2, answer2);
        toBeHashedQ = md5(question3 + username);
        question3 = toBeHashedQ;
        toBeHashedA = md5(answer3 + username);
        answer3 = toBeHashedA;
        securityQuestions.put(question3, answer3);
    }

    public Map.Entry<String, String> getRandomSecQ() {
        int random = (int) (Math.random() * 3);
        Map.Entry<String, String> entry = securityQuestions.entrySet().toArray(new Map.Entry[0])[random];
        return entry;
    }

    public boolean checkSecurityQuestion(String question, String answer) {
        if (securityQuestions.get(question).equals(answer)) {
            return true;
        }
        return false;
    }

    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    private String md5(String input) {
        String md5 = null;
        if(null == input) return null;
        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}

