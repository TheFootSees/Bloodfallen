package net.albedo.bloodfallen.modules.impl.misc;

import org.lwjgl.opengl.Display;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventManager;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;

@ModuleInfo(name = "Panic",desc = "Panic Mode", category = Category.MISC)
public class Uninject extends Module{

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if (isToggled()) {
			
			final Minecraft minecraft = Albedo.getMinecraft();
			minecraft.displayGuiScreen(null);
			
			Albedo.BLOODFALLEN = "";

			for (Module m : Albedo.getIrrlicht().getModuleManager().modules) {
				m.onDisable();
				EventManager.unregister(m);
				m.setToggled(false);
			}
		}
	}

}
