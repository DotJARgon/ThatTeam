package user_services;

import file_utilities.CSVParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

public class UserLoader {
    public static final String USER_FILE = "users.csv";

    public static Vector<Account> loadUsers() {
        ArrayList<String[]> allEntries = CSVParser.loadCSV(USER_FILE);
        Vector<Account> users = new Vector<>();
        if(allEntries != null) {
            for(String[] line : allEntries) {
                String username = line[0];
                String password = line[1];
                String email = line[2];
                String firstName = line[3];
                String lastName = line[4];
                String phoneNumber = line[5];
                String address = line[6];
                String city = line[7];
                String state = line[8];
                String zipCode = line[9];
                String country = line[10];
                int id = Integer.parseInt(line[11]);
                Vector<Integer> numRooms = new Vector<>();
                for(int i = 12; i < line.length; i++){
                    numRooms.add(Integer.parseInt(line[i]));
                }
                Account user = new Account(username, password, email, firstName, lastName, phoneNumber, address, city, state, zipCode, country, id, numRooms);
                users.add(user);
            }
        }

        return users;
    }

    public static void saveUsers(Set<Account> users) {
        ArrayList<Object[]> allUsers = new ArrayList<>();

        for(Account user : users) {
            Object[] properties = new Object[] {
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getCity(),
                    user.getState(),
                    user.getZipCode(),
                    user.getCountry(),
                    user.getId(),
                    user.getReservations()
            };
            allUsers.add(properties);
        }

        CSVParser.writeCSV(USER_FILE, allUsers);
    }
}
