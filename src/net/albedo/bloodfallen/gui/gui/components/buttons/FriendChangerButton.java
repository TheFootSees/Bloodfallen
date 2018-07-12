package net.albedo.bloodfallen.gui.gui.components.buttons;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.friends.FriendType;
import net.albedo.bloodfallen.gui.gui.components.ArrayBox;
import net.albedo.bloodfallen.gui.gui.components.selectors.SelectorButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.SelectorSystem;

public class FriendChangerButton extends ArrayBox<FriendType>{

	private SelectorSystem<SelectorButton> system;
	
	public FriendChangerButton(SelectorSystem<SelectorButton> system) {
		super(FriendType.values());
		this.system = system;
	}
	
	

	@Override
	public void mouseClicked(int x, int y) {
		
		super.mouseClicked(x, y);
		
		if(system.selectedButton != null){
			
			Albedo.getIrrlicht().getFriendManager().setFriendType(system.selectedButton.getStaticText(), getSelectedOption());
		}
	}
	
}
