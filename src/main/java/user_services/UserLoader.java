package user_services;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import file_utilities.XMLList;
import file_utilities.XMLParser;

public class UserLoader {
    public static final String USER_FILE = "users.that.xml";

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
    public static void saveUsers(List<Account> accountList) {
        XMLParser.save(accountList, USER_FILE, XMLList.class, Account.class, Guest.class, Clerk.class, Admin.class);
    }
}
