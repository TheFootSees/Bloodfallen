package net.albedo.bloodfallen.gui.gui.components.buttons;

import java.awt.Font;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.WindowSubPanel;

public class SpoilerButton extends Button{
	
	private boolean extended;
	private WindowSubPanel panel;
	private String text;
//	private FontRenderer text1;
	
	public SpoilerButton(String text){
		super();
		this.text = text;
//		text1 = new UnicodeFontRenderer(new Font("Roboto Regular", 0, 20));
	}
	
	@Override
	public void init() {
		this.panel = new WindowSubPanel(rect, this, 12);
	}
	
	@Override
	public void draw(int x, int y) {
		
//		if(extended){
//			text1.drawStringWithShadow(getText(),
//					getX()+3,
//					getY()+2,
//					mouseOverButton(x, y, getX(), getY())? ColourType.HIGHLIGHT.getModifiedColour():ColourType.HIGHLIGHT.getColour());
//		}else{
//			text1.drawStringWithShadow(getText(),
//					getX()+3,
//					getY() - 1,
//					mouseOverButton(x, y, getX(), getY())? ColourType.TEXT.getModifiedColour():ColourType.TEXT.getColour());
//		}
//		
		if(extended){
			panel.draw(x, y);
		}
	}
	
	public void addComponent(AbstractComponent c){
		panel.addComponent(c);
	}
	
	@Override
	public boolean isCentered() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return extended;
	}
	
	@Override
	public void toggle() {
		extended = !extended;
		rect.updateSize();
	}
	
	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public int getHeight() {
		
		if(extended){
			return super.getHeight() + panel.getHeight() + 1;
		}else{
			return super.getHeight();
		}
	}
	
	@Override
	public boolean mouseOverButton(int x, int y, int cx, int cy){
		return x > cx && x < cx+rect.getWidth() && y > cy && y < cy+12;
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		
		if(mouseOverButton(x, y, getX(), getY())){
//			Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
			toggle();
		}else if(extended){
			panel.mouseClicked(x, y);
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		
		if(extended){
			panel.mouseReleased(mouseX, mouseY, state);
		}
	}
	
}
