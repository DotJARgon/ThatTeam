package user_services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import file_utilities.CSVParser;

public class UserLoader {
    public static final String USER_FILE = "users.csv";

    public static ConcurrentHashMap<String, Account> loadUsers() {
        ArrayList<String[]> allEntries = CSVParser.loadCSV(USER_FILE);
        ConcurrentHashMap<String, Account> users = new ConcurrentHashMap<>();
        if(allEntries != null) {
            for(String[] line : allEntries) {
                String username = line[0];
                String password = line[1];
                String firstName = line[2];
                String lastName = line[3];
                int id = Integer.parseInt(line[4]);
                Vector<Integer> numRooms = new Vector<>();
                for(int i = 12; i < line.length; i++){
                    numRooms.add(Integer.parseInt(line[i]));
                }
                Account user = new Account(username, password, firstName, lastName, id);
                users.put(username, user);
            }
        }
        return users;
    }
    public static void saveUsers(Set<Account> users) {
    	ArrayList<Object[]> allUsers = new ArrayList<>();

        for(Account user : users) {
            ArrayList<Object> props = new ArrayList<>();
            props.add(user.getUsername());
            props.add(user.getPassword());
            props.add(user.getFirstName());
            props.add(user.getLastName());
            props.add(user.getId());

            allUsers.add(props.toArray());
        }

        CSVParser.writeCSV(USER_FILE, allUsers);
    }

    public static ConcurrentHashMap<String, Guest> loadGuests() {
        ArrayList<String[]> allEntries = CSVParser.loadCSV(USER_FILE);
        ConcurrentHashMap<String, Guest> users = new ConcurrentHashMap<>();
        if(allEntries != null) {
            for(String[] line : allEntries) {
                String username = line[0];
                String password = line[1];
                String firstName = line[3];
                String lastName = line[4];
                //String phoneNumber = line[5];
                String address = line[6];
                address += "," + line[7]; // city
                address += "," + line[8]; //state
                address += "," + line[9]; //zipCode
                address += "," + line[10];//country
                int id = Integer.parseInt(line[11]);
                int creditNum = Integer.parseInt(line[12]);//DNE yet
                //TODO: implement Calendar
                Calendar creditExp = new GregorianCalendar();
                Vector<Integer> numRooms = new Vector<>();
                for(int i = 12; i < line.length; i++){
                    numRooms.add(Integer.parseInt(line[i]));
                }
                Guest user = new Guest(username, password, firstName, lastName, id, address, creditNum, new Date());
                users.put(username, user);
            }
        }
        return users;
    }
    public static void saveGuests(Set<Guest> users) {
        ArrayList<Object[]> allUsers = new ArrayList<>();

        for(Guest user : users) {
            ArrayList<Object> props = new ArrayList<>();
            props.add(user.getUsername());
            props.add(user.getPassword());
            props.add(user.getFirstName());
            props.add(user.getLastName());
            props.add(user.getAddress());
            props.add(user.getId());
            props.add(user.getCardNum());
            //TODO: Add exp date.

            for(Integer i : user.getReservations()) {
                props.add(i);
            }

            allUsers.add(props.toArray());
        }

        CSVParser.writeCSV(USER_FILE, allUsers);
    }
}
