package user_services;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.security.*;

/*
this.resetPasswordPage = new ResetPage();
        this.main.add(this.resetPasswordPage);
 */
@XmlRootElement(name = "account")
//@XmlType(propOrder = { "username", "password", "firstName", "lastName", "id" })
public class Account {
    private String username = "";
    private String password = "";
    private String firstName = "";
    private String lastName = "";
    private String securityQ = "";
    private String securityA = "";
    private int id = 0;
    public Account() {
        password = "";
        username = "";
        firstName = "";
        lastName = "";
        id = 0;
    }
    public Account(String username, String password, String firstName, String lastName, int id, String securityQ, String securityA) {
        this.username = username;
        this.password = md5(password, username);
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.securityQ = securityQ;
        this.securityA = md5(securityA, securityQ);
    }
    public Account(String username, String password, String securityA, String securityQ) {
        this.username = username;
        this.password = md5(password, username);
        this.securityQ = securityQ;
        this.securityA = md5(securityA, securityQ);
        System.out.println("user: " + this.username + " pass: " + this.password + " securityQ: " + this.securityQ + " securityA " + this.securityA);
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
    public void setHashedPassword(String password) {
        this.password = md5(password, username);
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSecurityQ() {return this.securityQ;}

    public boolean matches(String password) {
        return this.password.equals(md5(password, username));
    }
    public boolean resetPassword(String qA) {
        return this.securityA.equals(md5(qA, securityQ));
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
