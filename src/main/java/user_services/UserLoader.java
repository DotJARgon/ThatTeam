package user_services;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import file_utilities.XMLList;
import file_utilities.XMLParser;
/**
 * The UserLoader class is responsible for storing and loading
 * account objects into a xml file in the proper format
 * @author  Marcelo Carpenter
 * @version  1.6
 * @since 4/18/23
 */
public class UserLoader {
    public static final String USER_FILE = "users.that.xml";

    /**
     * loadUsers will read a text file in xml format, creating a hashmap with all
     * the account objects and their usernames.
     * @return returns a hashMap containing the accounts and their usernames
     * corresponding to them
     */
    public static ConcurrentHashMap<String, Account> loadUsers() {
        ConcurrentHashMap<String, Account> users = new ConcurrentHashMap<>();
        try {
            List<Account> accounts = XMLParser.load(USER_FILE, XMLList.class, Account.class, Guest.class, Clerk.class, Admin.class);
            for(Account a : accounts) users.put(a.getUsername(), a);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return users;
        }
    }

    /**
     * saveUsers will use the XMLParser class in order to save all the
     * account objects being passed into a xml file.
     * @param accountList the list of account objects being converted into a xml
     *                    file
     */
    public static void saveUsers(List<Account> accountList) {
        XMLParser.save(accountList, USER_FILE, XMLList.class, Account.class, Guest.class, Clerk.class, Admin.class);
    }
}
