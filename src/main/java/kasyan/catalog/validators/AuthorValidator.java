package kasyan.catalog.validators;

import kasyan.catalog.dto.Author;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AuthorValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Author.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Author author = (Author) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondName", "secondName.required");
		
		if(author.getName().length()>45){
			errors.rejectValue("name", "name.max.length");
		}
		
		if(author.getSecondName().length()>45){
			errors.rejectValue("name", "name.min.length");
		}
	}

}
