package user_services;
import hotel_management.HotelManagement;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Admin class is responsible for having the behavior of an admin
 * account, extending from the account class in order to have all
 * the basic functionality an account requires
 * @author  Alexzander DeVries
 * @version  1.6
 * @since 3/25/23
 */
@XmlRootElement(name = "admin")
public class Admin extends Account{

}
