package net.valueglobal.jira.plugins.feedback.utils;
import java.util.HashMap;

public class MapRemovingNullCharacterFromStringValues extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String NULL_CHARACTER = "\u0000";

	@Override
	public Object put(String key, Object value) {
		Object transformedValue = value instanceof String ? removeNullCharacters((String) value)
				: value;
		return super.put(key, transformedValue);
	}

	private String removeNullCharacters(String inputText) {
		return inputText.contains(NULL_CHARACTER) ? inputText.replace(NULL_CHARACTER, "")
				: inputText;
	}

}
