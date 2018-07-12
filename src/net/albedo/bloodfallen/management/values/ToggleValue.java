package net.albedo.bloodfallen.management.values;

import net.albedo.bloodfallen.modules.Module;


public class ToggleValue extends Value<Boolean> {
	public ToggleValue(Module module, String name, Boolean value) {
		super(module, name, value);
	}

	@Override
	public void load(String data) {
		value = Boolean.valueOf(data);
	}

	@Override
	public String save() {
		return value.toString();
	}
}
