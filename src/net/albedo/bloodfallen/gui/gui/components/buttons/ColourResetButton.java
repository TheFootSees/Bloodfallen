package net.albedo.bloodfallen.gui.gui.components.buttons;

import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.Window;

public class ColourResetButton extends Button{
	
	public ColourResetButton() {
		super();
	}

	@Override
	public boolean isCentered() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void toggle() {
		for(ColourType col : ColourType.values()){
			col.setIndex(col.getDefaultIndex());
		}
	}

	@Override
	public String getText() {
		return "Reset Colours";
	}

}
