package net.albedo.bloodfallen.utils;

import java.math.BigDecimal;
import java.math.BigInteger;


public class NumberUtils {
	// prevent construction :/
	private NumberUtils() {
	}

	
	public static Number convertToTarget(Number number, Class<? extends Number> clazz) {
		if (clazz.isInstance(number))
			return number;
		else if (clazz.equals(Integer.class))
			return number.intValue();
		else if (clazz.equals(Float.class))
			return number.floatValue();
		else if (clazz.equals(Double.class))
			return number.doubleValue();
		else if (clazz.equals(Long.class))
			return number.longValue();
		else if (clazz.equals(Byte.class))
			return number.byteValue();
		else if (clazz.equals(Short.class))
			return number.shortValue();
		else if (clazz.equals(BigInteger.class))
			return BigInteger.valueOf(number.longValue());
		else if (clazz.equals(BigDecimal.class))
			return new BigDecimal(number.toString());
		throw new RuntimeException("Could determine number type: " + clazz.getName());
	}
}
