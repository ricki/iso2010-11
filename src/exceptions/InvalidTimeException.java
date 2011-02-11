package exceptions;


public class InvalidTimeException extends Exception {

	private static final long serialVersionUID = -8416290514218785629L;
	
	public InvalidTimeException (String msg) {
		super (msg);
	}
}