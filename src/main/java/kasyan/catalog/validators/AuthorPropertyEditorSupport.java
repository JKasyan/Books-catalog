package kasyan.catalog.validators;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;

import org.apache.log4j.Logger;

import kasyan.catalog.dto.Author;

public class AuthorPropertyEditorSupport extends PropertyEditorSupport {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		logger.debug(text);
		String[] s = text.split("\\s+");
		logger.debug(Arrays.toString(s));
		Author author = new Author();
		author.setName(s[0]);
		author.setSecondName(s[1]);
		logger.debug(author);
		setValue(author);
	}
}