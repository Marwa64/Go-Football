/**
 * <h1>The Administrator class</h1>
 * <p> 	This class represents the administrator that can activate, deactivate, or delete any playground
 */
public class administrator {
	private String email, password;
	/**
	 * Parameterized Constructor that sets the value of all the attributes
	 */
	public administrator(String email, String password) {
		this.email = email;
		this.password = password;
	}
	/**
	 * The activatePlayground method changes the status of the playground to true
	 * @param ownerPlayground is the playground that should be activated
	 */
	public void activatePlayground(playground ownerPlayground) {
		ownerPlayground.setStatus(true);
	}
	/**
	 * The deactivatePlayground method changes the status of the playground to false
	 * @param ownerPlayground is the playground that should be deactivated
	 */
	public void deactivatePlayground(playground ownerPlayground) {
		ownerPlayground.setStatus(false);
	}
	/**
	 * The setEmail method sets the email
	 * @param email is the new email of the administrator
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the email of the administrator
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * The setPassword method sets the password
	 * @param password is the new password of the administrator
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the password of the administrator
	 */
	public String getPassword() {
		return password;
	}
}
