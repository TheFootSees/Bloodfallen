package net.albedo.bloodfallen.gui.gui.components.buttons;

import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.modules.values.BooleanValue;

public class BooleanButton extends Button{

	private BooleanValue val;
	
	public BooleanButton(BooleanValue val) {
		super();
		this.val = val;
	}

	@Override
	public boolean isCentered() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return val.getValue();
	}

	@Override
	public void toggle() {
		val.setValue(!val.getValue());
	}

	@Override
	public String getText() {
		return val.getName();
	}

}