package net.albedo.bloodfallen.gui.gui.components;

import java.awt.Color;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.gui.gui.components.buttons.Button;
import net.albedo.bloodfallen.gui.utils.Colours;
import net.albedo.bloodfallen.utils.RenderUtils;

public class ColourPicker extends Button{
	
	private ColourType col;
	private GuiManager gui;
	
	public ColourPicker(ColourType col, GuiManager gui) {
		super();
		this.col = col;
		this.gui = gui;
	}
	
	@Override
	public void draw(int x, int y) {
		super.draw(x, y);
		RenderUtils.drawRect(getX() + rect.getWidth() - 10, getY() + 2, getX() + rect.getWidth() - 2, getY() + 10, col.getColour());
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
		col.cycleIndex();
	}
	
	@Override
	public String getText() {
		return col.getName();
	}
}