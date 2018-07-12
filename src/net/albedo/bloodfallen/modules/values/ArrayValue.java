package net.albedo.bloodfallen.modules.values;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.components.ArrayValueBox;

public class ArrayValue extends AbstractValue<Integer>{

	private String[] modes;
	
	public ArrayValue(String name, String saveName, ValuesRegistry registry, Integer defaultValue, String[] modes) {
		super(name, saveName, registry, defaultValue);
		this.modes = modes;
	}

	@Override
	public AbstractComponent getComponent(IRectangle panel) {
		return new ArrayValueBox(this);
	}

	public String getSelectedMode(){
		return modes[getValue()];
	}
	
	public void increment(){
		
		value++;
		
		if(value >= modes.length){
			value = 0;
		}
	}
	
	public void decrement(){
		
		value--;
		
		if(value < 0){
			value = modes.length-1;
		}
	}
}
