package exceptions;

public class InvalidSessionException extends Exception {

	private static final long serialVersionUID = -7260194304362605883L;
	
	public InvalidSessionException (String msg) {
		super (msg);
	}
}