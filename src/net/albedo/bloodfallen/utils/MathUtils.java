package net.albedo.bloodfallen.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MathUtils {
	// prevent construction :/
	private MathUtils() {
	}

	
	public static double round(double number, int places) {
		assert places > 0;
		final double multiplier = Math.pow(10, places);
		return (int) (number * multiplier) / multiplier;
	}

	
	public static double roundClean(double number, int places) {
		assert places > 0;
		return new BigDecimal(number).setScale(places, RoundingMode.HALF_UP).doubleValue();
	}
}
