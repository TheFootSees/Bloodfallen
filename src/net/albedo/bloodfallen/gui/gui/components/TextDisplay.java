package net.albedo.bloodfallen.gui.gui.components;

import java.awt.Font;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;

public class TextDisplay extends AbstractComponent{

	private String text;
//	private FontRenderer text1;
	
	public TextDisplay(String text) {
		super();
		this.text = text;
//		text1 = new UnicodeFontRenderer(new Font("Roboto Regular", 0, 20));
	}

	@Override
	public int getHeight() {
		return 12;
	}

	@Override
	public void draw(int x, int y) {
//		text1.drawStringWithShadow(text, getX() + 3, getY() + 2, 0xFFFFFFFF);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
