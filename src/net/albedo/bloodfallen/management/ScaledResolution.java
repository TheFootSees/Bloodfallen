package net.albedo.bloodfallen.management;

import org.lwjgl.opengl.Display;

import net.albedo.bloodfallen.engine.wrappers.client.settings.GameSettings;


public class ScaledResolution {
	private int scaleFactor = 1, scaledWidth = Display.getWidth(), scaledHeight = Display.getHeight();

	public ScaledResolution(GameSettings gameSettings) {
		int guiScale = (guiScale = gameSettings.getGuiScale()) == 0 ? 1000 : guiScale;
		while (scaleFactor < guiScale && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240)
			scaleFactor++;
		scaledWidth = scaledWidth / scaleFactor;
		scaledHeight = scaledHeight / scaleFactor;
	}

	
	public int getScaleFactor() {
		return scaleFactor;
	}

	
	public int getScaledWidth() {
		return scaledWidth;
	}

	
	public int getScaledHeight() {
		return scaledHeight;
	}
}
