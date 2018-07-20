package net.albedo.bloodfallen.modules.impl.misc;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;

@ModuleInfo(name = "FastPlace", desc = "Lets you build faster", category = Category.MISC)
public class FastPlace extends Module {

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(isToggled()) {
			Minecraft minecraft = Albedo.getMinecraft();
			minecraft.setRightClickDelayTimer(0);
		}
	}
	
	@Override
	public void onDisable() {
		Minecraft minecraft = Albedo.getMinecraft();
		minecraft.setRightClickDelayTimer(4);
	}
	
}
