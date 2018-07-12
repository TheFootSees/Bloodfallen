package net.albedo.bloodfallen.management.values;

import net.albedo.bloodfallen.modules.Module;


public abstract class Value<T> {
	public T value;
	protected Module module;
	protected String name;

	public Value(Module module, String name, T value) {
		this.module = module;
		this.name = name;
		this.value = value;
	}

	
	public String getName() {
		return name;
	}

	
	public Module getModule() {
		return module;
	}

	
	public abstract void load(String data);

	
	public abstract String save();
}
