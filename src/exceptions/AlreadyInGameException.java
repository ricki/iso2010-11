package exceptions;

public class AlreadyInGameException extends Exception {
	private static final long serialVersionUID = -1845472957679933689L;
	public AlreadyInGameException (String msg) {
		super (msg);
	}
}
