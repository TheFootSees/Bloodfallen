package net.albedo.bloodfallen.management.values;

import net.albedo.bloodfallen.modules.Module;


public class ModeValue<T extends Enum> extends Value<T> {
	public ModeValue(Module module, String name, T value) {
		super(module, name, value);
	}

	@Override
	public void load(String data) {
		value = (T) value.getDeclaringClass().getEnumConstants()[Integer.valueOf(data)];
	}

	@Override
	public String save() {
		return Integer.toString(value.ordinal());
	}
}
