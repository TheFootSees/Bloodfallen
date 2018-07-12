package net.albedo.bloodfallen.gui.gui;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.modules.ModuleManager;

public class WindowClosable extends Window{

	public WindowClosable(String title, ModuleManager modManager) {
		super(title, modManager, 85);
		this.extended = true;
	}
	
	public WindowClosable(String title, ModuleManager modManager, int width){
		super(title, modManager, width);
		this.extended = true;
	}
	
	@Override
	public boolean hasPinnedButton() {
		return false;
	}

	@Override
	public void onExtensionToggle() {
		Albedo.getIrrlicht().gui.windows.remove(this);
	}
}
