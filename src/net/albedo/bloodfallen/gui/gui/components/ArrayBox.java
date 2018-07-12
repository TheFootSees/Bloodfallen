package net.albedo.bloodfallen.gui.gui.components;

import java.awt.Font;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.Window;

//TODO: Maybe merge this with the NumberSelector
public class ArrayBox<T> extends AbstractComponent{
	
	private T[] options;
	private int index;
//	private FontRenderer text;
	
	public ArrayBox(T[] options) {
		super();
		this.options = options;
//		text = new UnicodeFontRenderer(new Font("Roboto Regular", 0, 20));
	}
	
	@Override
	public void draw(int x, int y) {
		
//		text.drawStringWithShadow("<",
//				getX() + 3,
//				getY() -1,
//				mouseOverLeft(x,y,getX(),getY())? ColourType.TEXT.getModifiedColour():ColourType.TEXT.getColour());
//		
//		text.drawStringWithShadow(">",
//				getX() + rect.getWidth() - 3 -text.getStringWidth(">"),
//				getY() -1,
//				mouseOverRight(x,y,getX(),getY())? ColourType.TEXT.getModifiedColour():ColourType.TEXT.getColour());
//		
//		String whole = options[index].toString();
//		
//		text.drawStringWithShadow(whole,
//				getX() + rect.getWidth()/2 - text.getStringWidth(whole)/2,
//				getY() -1,
//				ColourType.TEXT.getColour());
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		
		if(mouseOverLeft(x, y, getX(), getY())){
			
//			Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
			
			index--;
			
			if(index < 0){
				index = options.length-1;
			}
			
		}else if(mouseOverRight(x, y, getX(), getY())){
			
//			Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
			
			index++;
			
			if(index >= options.length){
				index = 0;
			}
		}
	}
	
	private boolean mouseOverLeft(int x, int y, int cx, int cy){
		return x > cx && x < cx+rect.getWidth()/2 && y > cy && y < cy+getHeight();
	}
	
	private boolean mouseOverRight(int x, int y, int cx, int cy){
		return x > cx+rect.getWidth()/2 && x < cx+rect.getWidth() && y > cy && y < cy+getHeight();
	}
	
	public T[] getOptions() {
		return options;
	}
	
	public void setOptions(T[] options) {
		this.options = options;
	}
	
	public T getSelectedOption(){
		return options[index];
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public int getHeight() {
		return 12;
	}
}
