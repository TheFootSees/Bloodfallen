package net.albedo.bloodfallen.utils;


public class StringUtils {
	// prevent construction :/
	private StringUtils() {
	}

	
	public static String capitalize(String s, char delimiter) {
		boolean capitalize = true;
		final char chars[] = s.toCharArray();
		for (int i = 0; i < s.length(); i++) {
			chars[i] = capitalize ? Character.toUpperCase(chars[i]) : Character.toLowerCase(chars[i]);
			capitalize = chars[i] == delimiter;
		}
		return new String(chars);
	}
}
