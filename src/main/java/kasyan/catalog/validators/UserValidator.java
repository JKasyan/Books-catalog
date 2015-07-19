package kasyan.catalog.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kasyan.catalog.dto.User;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator{
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	private Matcher matcher;
	
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
				"email.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"password.required");
		
		matcher = pattern.matcher(user.getEmail());
		if(!matcher.matches()){
			errors.rejectValue("email", "email.incorrect");
		}
		
		if(user.getPassword().length() > 20){
			errors.rejectValue("password", "password.max.length");
		}
		
		if(user.getPassword().length() < 5){
			errors.rejectValue("password", "password.min.length");
		}
	}

}
