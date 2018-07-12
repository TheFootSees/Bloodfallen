package net.albedo.bloodfallen.gui.gui.components.buttons;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.Canvas;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.gui.gui.WindowSubPanel;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.values.AbstractValue;

public class OptionsModButton extends ModButton {

	private boolean extended;
	private WindowSubPanel panel;
//	private FontRenderer text;
	private Window win;
//	private Mod module;

	public OptionsModButton(Module mod) {
		super(mod);
	}

	@Override
	public void init() {

		panel = new WindowSubPanel(rect, this, 8);
		
		for (AbstractValue option : mod.getOptions()) {
			panel.addComponent(option.getComponent(panel));

		}
	}

	@Override
	public void draw(int x, int y) {
		
		panel.draw(x, y);

	}

	@Override
	public void mouseClicked(int x, int y) {
		panel.mouseClicked(x, y);
		if (mouseOverExtendButton(x, y, rect.getRectX() - 110, rect.getRectY())) {
			System.out.println("ok");
			for (Window w : GuiManager.windows) {
				if (w.title.equals(mod.getName())) {
					GuiManager.windows.remove(w);
				}
			}

		}
	}

	@Override
	public boolean isCentered() {
		return super.isCentered();
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {

		panel.mouseReleased(mouseX, mouseY, state);

	}

	public boolean mouseOverExtendButton(int x, int y, int cx, int cy) {
		return x >= cx + rect.getWidth() - 10 && x <= cx + rect.getWidth() - 2 && y >= cy + 2 && y <= cy + 10;
	}

	@Override
	public boolean mouseOverButton(int x, int y, int cx, int cy) {
		return x > cx && x < cx + rect.getWidth() && y > cy && y < cy + 12;
	}

	@Override
	public int getHeight() {

		return super.getHeight() + panel.getHeight() + 1;
	}
}