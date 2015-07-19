package kasyan.catalog.validators;

import java.util.Calendar;

import kasyan.catalog.dto.Book;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BookValidator implements Validator {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public boolean supports(Class<?> clazz) {
		return Book.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Book book = (Book) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
				"title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortDescription",
				"description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datePublish",
				"date.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "authors",
				"authors.required");

		if (book.getTitle().length() > 45) {
			errors.rejectValue("title", "title.max.length");
		}

		if (book.getTitle().length() < 3) {
			errors.rejectValue("title", "title.min.length");
		}

		if (!isDigit(book.getDatePublish())) {
			errors.rejectValue("datePublish", "date.digit");
		}
		

		if(isDigit(book.getDatePublish()) && !"".equals(book.getDatePublish())){
			int currentYear =  Calendar
					.getInstance().get(Calendar.YEAR);
			int publishYear = Integer.valueOf(book.getDatePublish());
			if( book.getDatePublish().length() > 4 || publishYear > currentYear){
				errors.rejectValue("datePublish", "date.max.length");
			}
		}

		if (book.getShortDescription().length() > 45) {
			errors.rejectValue("shortDescription", "description.max.length");
		}

		if (book.getShortDescription().length() < 10) {
			errors.rejectValue("shortDescription", "description.min.length");
		}

	}

	private boolean isDigit(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (!Character.isDigit(text.charAt(i))) {
				return false;
			}
		}
		logger.debug(text+" is digit");
		return true;
	}

}
