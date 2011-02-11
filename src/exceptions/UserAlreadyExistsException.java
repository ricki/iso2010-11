package exceptions;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -4971949997653617881L;
	
	public UserAlreadyExistsException (String msg) {
		super (msg);
	}
}
