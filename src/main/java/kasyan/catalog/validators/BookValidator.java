package kasyan.catalog.validators;

import kasyan.catalog.dto.Book;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BookValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return Book.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Book book = (Book) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Title is required!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortDescription", "Description is required!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datePublish", "Date is required!");
		
		if(book.getTitle().length()>45){
			errors.rejectValue("title", "Title should be no more 45 characters!");
		}
		
		if(book.getDatePublish().length() > 4){
			errors.rejectValue("datePublish", "The time machine is not exist :-)");
		}
		
		for(int i = 0; i< book.getDatePublish().length();i++){
			if(!Character.isDigit(book.getDatePublish().charAt(i))){
				errors.rejectValue("datePublish", "Date must contain only digits");
			}
		}
		
		if(book.getShortDescription().length() > 45){
			errors.rejectValue("shortDescription", "Description should be no more 45 characters!");
		}
		
		if(book.getShortDescription().length() < 10){
			errors.rejectValue("shortDescription", "Description should be at least 10 characters!");
		}
		
	}

}
