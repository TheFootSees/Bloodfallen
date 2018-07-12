package net.albedo.bloodfallen.modules;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.lwjgl.input.Keyboard;

import net.albedo.bloodfallen.engine.events.EventManager;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.management.values.Value;
import net.albedo.bloodfallen.management.values.ValueTarget;
import net.albedo.bloodfallen.modules.values.AbstractValue;
import net.albedo.bloodfallen.tools.FontRENDERER;


public class Module {
	
	protected Category type;
	private AbstractValue[] options;
	protected int keybind;
	public boolean toggled;
	protected String name;
	protected String description;

    public Module() {
		super();
		this.keybind = -1;
	}
	
    public Module(AbstractValue[] options) {
		super();
		this.options = options;
		this.keybind = -1;
		
	}
    
    public boolean hasOptions(){
		return options != null;
	}
	
	public AbstractValue[] getOptions() {
		return options;
	}

	public void setOptions(AbstractValue[] options) {
		this.options = options;
	}
	
	public final String getKeyName(){
		return keybind == -1? "None":Keyboard.getKeyName(keybind);
	}
	
	public final int getKeybind() {
		return keybind;
	}
	
	public Category getCategory() {
		return type;
	}
	
	public final void setKeybind(int keybind){
		this.keybind = keybind;
	}

	
//	public final void init() {
//		Stream.of(getClass().getDeclaredFields()).forEach(field -> {
//			if (field.getAnnotationsByType(ValueTarget.class) != null && Value.class.isAssignableFrom(field.getType()))
//				try {
//					field.setAccessible(true);
//					values.add((Value) field.get(this));
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				}
//		});
//	}

	
	public void onEnable() {
		EventManager.register(this);
	}

	
	public void onDisable() {
		EventManager.unregister(this);
	}

	public void toggle(){
		toggled = !toggled;
		if(toggled){
			onEnable();
		}else{
			onDisable();
		}
	}

	
	public boolean isToggled() {
		return toggled;
	}
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}
	


}
