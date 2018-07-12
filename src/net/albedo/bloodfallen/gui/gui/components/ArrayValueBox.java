package net.albedo.bloodfallen.gui.gui.components;

import java.awt.Font;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.modules.values.ArrayValue;

public class ArrayValueBox extends AbstractComponent{
	
	private ArrayValue val;
//	private FontRenderer text;
	
	public ArrayValueBox(ArrayValue val) {
		super();
		this.val = val;
	}
	
	@Override
	public void draw(int x, int y) {
		
//		text.drawStringWithShadow("<",
//				getX() + 3,
//				getY(),
//				mouseOverLeft(x,y,getX(),getY())? ColourType.TEXT.getModifiedColour():ColourType.TEXT.getColour());
//		
//		text.drawStringWithShadow(">",
//				getX() + rect.getWidth() - 3 - text.getStringWidth(">"),
//				getY(),
//				mouseOverRight(x,y,getX(),getY())? ColourType.TEXT.getModifiedColour():ColourType.TEXT.getColour());
//		
//		String whole = val.getSelectedMode();
//		
//		text.drawStringWithShadow(whole,
//				getX() + rect.getWidth()/2 - text.getStringWidth(whole)/2,
//				getY(),
//				ColourType.TEXT.getColour());
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		
		if(mouseOverLeft(x, y, getX(), getY())){
			
//			Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
			
			val.increment();
			
		}else if(mouseOverRight(x, y, getX(), getY())){
			
//			Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
			
			val.decrement();
		}
	}
	
	private boolean mouseOverLeft(int x, int y, int cx, int cy){
		return x > cx && x < cx+rect.getWidth()/2 && y > cy && y < cy+getHeight();
	}
	
	private boolean mouseOverRight(int x, int y, int cx, int cy){
		return x > cx+rect.getWidth()/2 && x < cx+rect.getWidth() && y > cy && y < cy+getHeight();
	}
		
	public String getSelectedOption(){
		return val.getSelectedMode();
	}
	
	@Override
	public int getHeight() {
		return 12;
	}
}
