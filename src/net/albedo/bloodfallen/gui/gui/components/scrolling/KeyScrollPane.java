package net.albedo.bloodfallen.gui.gui.components.scrolling;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.components.selectors.KeybindButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.ModuleButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.SelectorSystem;
import net.albedo.bloodfallen.gui.gui.hub.keybind.KeybindSetButton;
import net.albedo.bloodfallen.modules.Module;;

public class KeyScrollPane extends FilterableScrollPane{

	private KeybindSetButton keybindButton;
	
	public KeyScrollPane(int height, SelectorSystem<KeybindButton> system) {
		super(height);
		
		
		for (Module m : Albedo.getIrrlicht().getModuleManager().modules) {
			addFilterableComponent(system.add(new KeybindButton(m, system)));

		}
	}
}
