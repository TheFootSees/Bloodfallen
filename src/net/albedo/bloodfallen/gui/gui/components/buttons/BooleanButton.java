package net.albedo.bloodfallen.gui.gui.components.buttons;

import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.modules.values.BooleanValue;
import net.albedo.bloodfallen.utils.RenderUtils;

public class BooleanButton extends Button{

	private BooleanValue val;
	
	public BooleanButton(BooleanValue val) {
		super();
		this.val = val;
	}
	
	@Override
	public void draw(int x, int y) {

		String addon = val.getValue() ? "True" : "False";
		String displayString = val.getName() + ": " + addon;
		
		GuiManager.fr.drawString(displayString, getX() + rect.getWidth() / 2 - GuiManager.fr.getStringWidth(displayString) / 2, getY() - 1, 0xFFFFFFFF);
	}
	
	public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() + 36 && mouseY >= this.getY() + 1 && mouseY <= this.getY() + this.getHeight() - 1;
    }
	
	@Override
	public int getHeight() {
		return 12;
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