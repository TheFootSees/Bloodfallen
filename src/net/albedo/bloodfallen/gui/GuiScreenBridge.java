package net.albedo.bloodfallen.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.mapper.WrapperBridge;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiScreen;


public class GuiScreenBridge extends WrapperBridge implements GuiScreen {
	protected int width, height;

	public GuiScreenBridge() {
		super(GuiScreen.class);
	}

	@Override
	public final void handleInput() {
		if (Mouse.isCreated())
			while (Mouse.next()) {
				final int x = Mouse.getEventX() * width / Display.getWidth(), y = height - Mouse.getEventY() * height / Display.getHeight() - 1, button = Mouse.getEventButton();
				if (Mouse.getEventButtonState())
					mouseClicked(x, y, button);
				else if (button != -1)
					mouseReleased(x, y, button);
			}

		if (Keyboard.isCreated())
			while (Keyboard.next())
				if (Keyboard.getEventKeyState())
					keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
	}

	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
	}

	
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
	}

	
	public void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE)
			Albedo.getMinecraft().displayGuiScreen(null);
	}

	@Override
	public void initGui(Minecraft minecraft, int width, int height) {
		Keyboard.enableRepeatEvents(true);
		this.width = width;
		this.height = height;
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		initGui(minecraft, width, height);
	}

	@Override
	public boolean shouldPauseGame() {
		return false;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void onClose() {
	}
}
