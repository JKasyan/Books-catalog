package kasyan.catalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Book not found")
public class BookNotFoundException extends Exception{

	private static final long serialVersionUID = -5770724351544263413L;
	
	public BookNotFoundException(int id){
		super("Book with id = "+id+" not found.");
	}

}
