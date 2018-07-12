package net.albedo.bloodfallen.gui.gui.components;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleManager;

public class EnabledModsDisplay extends AbstractComponent {
	
//	private FontRenderer fontrendere
	@Override
	public int getHeight() {
		
		int enabledMods = 0;
		
		for(Module m : Albedo.getIrrlicht().getModuleManager().modules){
			if(m.isToggled()){
				enabledMods++;
			}
		}
		
		return enabledMods * 12;
	}
	
	@Override
	public void draw(int x, int y) {
		
		int enabledMods = 0;
		
		for(Module m : Albedo.getIrrlicht().getModuleManager().modules){
			if(m.isToggled()){
//				fontrenderer.drawStringWithShadow(m.getName(), getX() + 3, getY() + enabledMods*12 + 2, ColourType.TEXT.getColour());
				enabledMods++;
			}
		}
	}

	@Override
	public void onModManagerChange() {
		rect.updateSize();
	}
}
