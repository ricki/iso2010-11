package exceptions;

public class NotCurrentPlayerGameException extends Exception {

	private static final long serialVersionUID = -566764146183429953L;
	
	public NotCurrentPlayerGameException (String msg) {
		super (msg);
	}
}