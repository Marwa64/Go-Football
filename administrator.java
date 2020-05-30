
public class administrator {
	private String email, password;
	
	public administrator(String email, String password) {
		this.email = email;
		this.password = password;
	}
	public void activatePlayground(playground ownerPlayground) {
		ownerPlayground.setStatus(true);
	}
	public void deactivatePlayground(playground ownerPlayground) {
		ownerPlayground.setStatus(false);
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
}
