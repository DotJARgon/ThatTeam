package user_services;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.security.*;

/**
 * The Account class is responsible for the basic activity of an account on
 * the site, which includes storing the account's username, password, first and
 * last name, its own unique user id, and security questions related to it.
 * @author  Bryant Huang
 * @version  1.6
 * @since 3/25/23
 */
@XmlRootElement(name = "account")
public class Account {
    private String username = "";
    private String password = "";
    private String firstName = "";
    private String lastName = "";
    private String securityQ = "";
    private String securityA = "";
    private int id;

    /**
     * The default constructor for an account object
     */
    public Account() {
        password = "";
        username = "";
        firstName = "";
        lastName = "";
        id = 0;
    }

    /**
     * A constructor of the account object, which hashes the password using the md5
     * encryption method
     * @param username the username of the account
     * @param password the password of the account
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param id the unique user id
     * @param securityQ the security question of the account
     * @param securityA the security answer of the question in the account
     */
    public Account(String username, String password, String firstName, String lastName, int id, String securityQ, String securityA) {
        this.username = username;
        this.password = md5(password, username);
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.securityQ = securityQ;
        this.securityA = md5(securityA, securityQ);
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
    public String getSecurityA() {return this.securityA;}
    public void setSecurityQ(String securityQ) {this.securityQ = securityQ  ;}
    public void setHashedSecurityA(String securityA) {this.securityA = md5(securityA, securityQ);}
    public void setSecurityA(String securityA) {this.securityA = securityA;}




    /**
     * Matches checks if two passwords that have been md5 encrypted are the same
     * @param password the password being compared
     * @return returns true or false based on whether the two passwords are the same
     */
    public boolean matches(String password) {
        return this.password.equals(md5(password, username));
    }

    /**
     * resetPassword resets the md5 encrypted password for the account
     * @param qA the security answer to resalt the password
     * @return returns true or false based on whether the security question
     * was answered properly
     */
    public boolean resetPassword(String qA) {
        //System.out.println(md5(qA, this.securityQ) + " = " + this.securityA);
        return this.securityA.equals(md5(qA, this.securityQ));
    }

    /**
     * md5 salts the password of the account using the md5 encrpytion method
     * @param ptxt the password in unsalted form
     * @param salt the username which is then used to salt the password
     * @return returns the salted password after md5 encrypting it
     */
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
