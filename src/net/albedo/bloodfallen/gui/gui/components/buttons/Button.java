package net.albedo.bloodfallen.gui.gui.components.buttons;

import java.awt.Font;

import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.gui.utils.Colours;

public abstract class Button extends AbstractComponent{
		
//	private FontRenderer text;
	
	public Button(){
//		text = new UnicodeFontRenderer(new Font("Roboto Regular", 0, 20));
	}
	
	@Override
	public void draw(int x, int y) {


		if(isEnabled()){
//			text.drawStringWithShadow(getText(), getX() + (isCentered() ? (rect.getWidth()/2 - text.getStringWidth(getText())/2):3), getY() + getHeight()/2 - 2 - text.getMaxHeight()/2,
//					mouseOverButton(x, y, getX(), getY())? ColourType.HIGHLIGHT.getModifiedColour():ColourType.HIGHLIGHT.getColour());
		}else{
//			text.drawStringWithShadow(getText(),
//					getX() + (isCentered() ? (rect.getWidth()/2 - text.getStringWidth(getText())/2):3),
//					getY() + getHeight()/2 - 2 - text.getMaxHeight()/2,
//					mouseOverButton(x, y, getX(), getY())? ColourType.TEXT.getModifiedColour():ColourType.TEXT.getColour());
		}
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		if(mouseOverButton(x, y, getX(), getY())){
//			Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
			toggle();
		}
	}
	
	public boolean mouseOverButton(int x, int y, int cx, int cy){
		return x > cx && x < cx+rect.getWidth() && y > cy && y < cy+getHeight();
	}
	
	@Override
	public int getHeight() {
		return 12;
	}
	
	public abstract boolean isCentered();
	public abstract boolean isEnabled();
	public abstract void toggle();
	public abstract String getText();
}
