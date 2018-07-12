package net.albedo.bloodfallen.gui.gui.components.buttons;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.modules.Module;


public class ModButton extends Button{
	
	protected Module mod;
	
	public ModButton(Module mod) {
		super();
		this.mod = mod;
	}
	
	@Override
	public boolean isEnabled() {
		return mod.isToggled();
	}
	
	@Override
	public void toggle() {
		mod.toggle();
	}
	
	@Override
	public String getText() {
		return mod.getName();
	}
	
	@Override
	public boolean isCentered() {
		return false;
	}
	
	public Module getMod() {
		return mod;
	}
}
