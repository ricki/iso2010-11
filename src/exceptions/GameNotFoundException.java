package exceptions;

public class GameNotFoundException extends Exception {

	private static final long serialVersionUID = 171130139931367524L;

	public GameNotFoundException(String msg) {
		super (msg);
	}
}
