
public class user {
	
	protected String name, location, password, email, phone;
	// Takes the user info from the user and stores them
	public void signUp(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
		location = "";
		phone = "";
	}
	// Takes the user info from the user and updates them
	public void updateInfo(String name, String location, String password, String email, String phone) {
		this.name = name;
		this.location = location;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}
	// Checks if the login details match the details of this user
	public boolean login(String password, String email) {
		if (this.email == email && this.email == email) {
			return true;
		} else {
			return false;
		}
	}
	// Setters and Getters
	public void setName(String name){
		this.name = name;
	} 
	public String getName(){
		return name;
	}
	public void setPassword(String password){
		this.password = password;
	} 
	public String getPassword(){
		return password;
	}
	
	public void setEmail(String email){
		this.email = email;
	} 
	public String getEmail(){
		return email;
	}
	public void setLocation(String location){
		this.location = location;
	} 
	public String getLocation(){
		return location;
	}
	public void setPhone(String phone){
		this.phone = phone;
	} 
	public String getPhone(){
		return phone;
	}
}
