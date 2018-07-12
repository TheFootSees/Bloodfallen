package net.albedo.bloodfallen.gui.gui.components.scrolling;

import java.util.ArrayList;
import java.util.List;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.components.selectors.SelectorButton;

public class FilterableScrollPane extends ScrollPane{
	
	protected List<SelectorButton> componentsPool = new ArrayList<SelectorButton>();
	
	public FilterableScrollPane(int height) {
		super(height);
	}

	public void addFilterableComponent(SelectorButton c) {
		this.componentsPool.add(c);
		super.addComponent(c);
	}

	public void applyFilter(String filter){
		
		filter = filter.toLowerCase();
		
		components.clear();
		
		if(filter.length() > 0){
			
			for(SelectorButton selectorButton : componentsPool){
				if(selectorButton.getStaticText().toLowerCase().contains(filter)){
					this.addComponent(selectorButton);
				} 
			}
			
		}else{
			
			for(AbstractComponent comp : componentsPool){
				this.addComponentRaw(comp);
				
			}
			
			this.updateSize();
		}
		
	}
}
