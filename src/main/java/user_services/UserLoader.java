package user_services;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import file_utilities.CSVParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
    public static void saveUsers(AccountList accountList) {

        try {
            JAXBContext context = JAXBContext.newInstance(AccountList.class, Account.class, Guest.class, Clerk.class, Admin.class);
            Marshaller m = context.createMarshaller();
            m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write to System.out
            m.marshal(accountList, System.out);

            // Write to File
            m.marshal(accountList, new File("output-JAXB.xml"));
        }
        catch(JAXBException e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, Guest> loadGuests() {
        ArrayList<String[]> allEntries = CSVParser.loadCSV(USER_FILE);
        ConcurrentHashMap<String, Guest> users = new ConcurrentHashMap<>();
        if(allEntries != null) {
            for(String[] line : allEntries) {
                String username = line[0];
                String password = line[1];
                String firstName = line[2];
                String lastName = line[3];
                String address = line[4];
                address += "," + line[5]; // city
                address += "," + line[6]; //state
                address += "," + line[7]; //zipCode
                address += "," + line[8];//country
                int id = Integer.parseInt(line[9]);
                int creditNum = Integer.parseInt(line[10]);
                Calendar creditExp = new GregorianCalendar();
                creditExp.set(Calendar.DATE, 1);
                creditExp.set(Calendar.MONTH, Integer.parseInt(line[11]));
                creditExp.set(Calendar.YEAR, Integer.parseInt(line[12]));
                Vector<Integer> resNums = new Vector<>();
                for(int i = 13; i < line.length; i++){
                    resNums.add(Integer.parseInt(line[i]));
                }
                //TODO: Test the use of getTime
                Guest user = new Guest(username, password, firstName, lastName, id, address, creditNum, creditExp.getTime());
                user.setReservations(resNums);
                users.put(username, user);
            }
        }
        return users;
    }
    public static void saveGuests(Set<Guest> users) {
        ArrayList<Object[]> allGuests = new ArrayList<>();

        for(Guest user : users) {
            ArrayList<Object> props = new ArrayList<>();
            props.add(user.getUsername());
            props.add(user.getPassword());
            props.add(user.getFirstName());
            props.add(user.getLastName());
            props.add(user.getAddress());
            props.add(user.getId());
            props.add(user.getCardNum());
            Calendar exp = new GregorianCalendar();
            exp.setTime(user.getCardExpiration());
            props.add(exp.get(Calendar.MONTH));
            props.add(exp.get(Calendar.YEAR));
            for(Integer i : user.getReservations()) {
                props.add(i);
            }

            allGuests.add(props.toArray());
        }

        CSVParser.writeCSV(USER_FILE, allGuests);
    }
}
