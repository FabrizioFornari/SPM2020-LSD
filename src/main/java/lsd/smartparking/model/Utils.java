package lsd.smartparking.model;

public abstract class Utils {

	public static void checkFields(String field) {
		if (field == null || field.isEmpty()) {
			throw new IllegalArgumentException("The field cannot be null or empty");
		}
	}
	
	public static void checkFieldsLength(String field, int n) {
		if (field.length() > n) {
			throw new IllegalArgumentException("The field is too long");
		}
	}
	
}