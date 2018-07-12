package net.albedo.bloodfallen.gui.gui.hub.keybind;

import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.components.buttons.Button;
import net.albedo.bloodfallen.gui.gui.components.selectors.KeybindButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.ModuleButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.SelectorSystem;

public class KeybindSetButton extends Button{
	
	private boolean focus;
	private SelectorSystem<KeybindButton> system;
	
	public KeybindSetButton(SelectorSystem<KeybindButton> system) {
		super();
		this.system = system;
		
	}

	@Override
	public boolean isCentered() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void toggle() {
		focus = true;
	}

	@Override
	public String getText() {
		
		if(system.selectedButton != null){
			return focus? "Press any Key":"Set Keybind";
		}else{
			return "";
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		
		if(mouseOverButton(x, y, getX(), getY())){
//			Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
			toggle();
		}else{
			focus = false;
		}
	}

	@Override
	public void keyPress(int key, char c) {
		
		if(system.selectedButton != null && focus){
			system.selectedButton.getMod().setKeybind(key);
			focus = false;
		}
	}
}
