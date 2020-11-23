package lsd.smartparking.model;

public abstract class Utils {

	public static void checkFields(String field) {
		if (field == null || field == "") {
			throw new IllegalArgumentException("The field cannot be null or empty");
		}
	}
	
	public static void checkFieldsLength(String field) {
		if (field.length() > 20) {
			throw new IllegalArgumentException("The field is too long");
		}
	}
	
}
