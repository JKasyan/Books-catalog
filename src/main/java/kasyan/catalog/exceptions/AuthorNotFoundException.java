package kasyan.catalog.exceptions;

public class AuthorNotFoundException extends Exception {

	private static final long serialVersionUID = -7171698623173532069L;
	
	public AuthorNotFoundException(int id) {
		super("Author with id = "+id+" not found.");
	}

}
