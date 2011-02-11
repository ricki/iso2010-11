package exceptions;

public class WrongLoginException extends Exception {

	private static final long serialVersionUID = 4537478030192183927L;
	
	public WrongLoginException (String msg) {
		super (msg);
	}
}