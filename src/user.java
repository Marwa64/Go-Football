/**
 * <h1>The User class</h1>
 * <p> 	This class represents a general class that is the parent class of the player and playgroundOwner classes
 */
public class user {
	
	protected String name, location, password, email, phone;
	/**
	 * The signUp method takes the user info and stores them
	 * @param name is the name of the user
	 * @param password is the user's password
	 * @param email is the user's email
	 */
	public void signUp(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
		location = "";
		phone = "";
	}
	/**
	 * The updateInfo method takes the user info from the user and updates them
	 */
	public void updateInfo(String name, String location, String password, String email, String phone) {
		this.name = name;
		this.location = location;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}
	/**
	 * The login method checks if the login details match the details of this user
	 * @param password is the password the user entered
	 * @param email is the email the user entered
	 * @return
	 */
	public boolean login(String password, String email) {
		if (this.email == email && this.email == email) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * The setName method sets the name
	 * @param name is the new name for the user
	 */
	public void setName(String name){
		this.name = name;
	} 
	/**
	 * @return the user's name
	 */
	public String getName(){
		return name;
	}
	/**
	 * The setPassword method sets the password
	 * @param password is the new password for the user
	 */
	public void setPassword(String password){
		this.password = password;
	} 
	/**
	 * @return the user's password
	 */
	public String getPassword(){
		return password;
	}
	/**
	 * The setEmail method sets the email
	 * @param email is the new email for the user
	 */
	public void setEmail(String email){
		this.email = email;
	} 
	/**
	 * @return the user's email
	 */
	public String getEmail(){
		return email;
	}
	/**
	 * The setLocation method sets the location
	 * @param location is the user's new location
	 */
	public void setLocation(String location){
		this.location = location;
	} 
	/**
	 * @return the user's location
	 */
	public String getLocation(){
		return location;
	}
	/**
	 * The setPhone method sets the phone number
	 * @param phone is the user's new phone number
	 */
	public void setPhone(String phone){
		this.phone = phone;
	} 
	/**
	 * @return the user's phone number
	 */
	public String getPhone(){
		return phone;
	}
}
