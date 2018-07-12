package net.albedo.bloodfallen.gui.gui.components.selectors;

import java.util.ArrayList;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;

public class SelectorSystem<T extends SelectorButton> {
	
	public ArrayList<T> buttons = new ArrayList<T>();
	public T selectedButton;
	
	public void setOnly(T button){
		
		for(T b : buttons){
			if(b != button){
				b.setSelected(false);
			}
		}
		
		button.setSelected(true);
		selectedButton = button;
	}
	
	public T add(T b){
		buttons.add(b);
		return b;
	}
}
