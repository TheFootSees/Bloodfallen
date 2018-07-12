package net.albedo.bloodfallen.modules.impl.movement;

import org.lwjgl.opengl.Display;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;

@ModuleInfo(name = "Fly", desc = "Fly like a sign", category = Category.MOVEMENT)
public class Fly extends Module {
	
	
	@Override
	public void onDisable() {
		Albedo.getMinecraft().getPlayer().getPlayerAbilities().setFlying(false);
		super.onDisable();
	}

	@EventTarget
	public void onUpdate(UpdateEvent event) {
        if (isToggled()) {
            Albedo.getMinecraft().getPlayer().getPlayerAbilities().setFlying(true);
        }
    }
}
