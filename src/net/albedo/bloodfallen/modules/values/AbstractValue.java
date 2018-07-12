package net.albedo.bloodfallen.modules.values;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.IRectangle;

public abstract class AbstractValue<T>{
	
	protected T value;
	protected String name;
	private ValuesRegistry registry;
	private String saveName;
	
	public AbstractValue(String name, String saveName, ValuesRegistry registry, T defaultValue){
		
		if(registry.hasValue(saveName)){
			this.value = (T) registry.get(saveName);
		}else{
			this.value = defaultValue;
		}
		
		this.registry = registry;
		this.name = name;
		this.saveName = saveName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
		registry.set(saveName, value);
	}
	
	public abstract AbstractComponent getComponent(IRectangle panel);

}
