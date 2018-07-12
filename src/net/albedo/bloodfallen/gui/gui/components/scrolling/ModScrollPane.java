package net.albedo.bloodfallen.gui.gui.components.scrolling;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.components.selectors.KeybindButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.ModuleButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.SelectorSystem;
import net.albedo.bloodfallen.gui.gui.hub.keybind.KeybindSetButton;
import net.albedo.bloodfallen.modules.Module;

public class ModScrollPane extends FilterableScrollPane{

	private KeybindSetButton keybindButton;
	
	public ModScrollPane(int height, SelectorSystem<ModuleButton> modSystem) {
		super(height);
		
		for(Module m : Albedo.getIrrlicht().getModuleManager().modules){
			
			addFilterableComponent(modSystem.add(new ModuleButton(m, modSystem)));
			
		}
	}
}
