package net.albedo.bloodfallen.management.values;

import net.albedo.bloodfallen.modules.Module;


public class SliderValue<T extends Number> extends Value<T> {
	public final T min, max, increment;

	public SliderValue(Module module, String name, T value, T min, T max, T increment) {
		super(module, name, value);
		this.min = min;
		this.max = max;
		this.increment = increment;
	}

	@Override
	public void load(String data) {
		if (value instanceof Integer)
			value = (T) Integer.valueOf(data);
		else if (value instanceof Double)
			value = (T) Double.valueOf(data);
		else if (value instanceof Float)
			value = (T) Float.valueOf(data);
		else if (value instanceof Short)
			value = (T) Short.valueOf(data);
	}

	@Override
	public String save() {
		return value.toString();
	}
}
