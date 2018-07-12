package net.albedo.bloodfallen.modules.impl.movement;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.engine.wrappers.client.entity.PlayerSp;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;
import net.albedo.bloodfallen.utils.MovementUtils;

@ModuleInfo(name = "Sprint", desc = "Sprint automatically", category = Category.MOVEMENT)
public class Sprint extends Module{

	public static boolean isMoving() {
		final PlayerSp player = Albedo.getMinecraft().getPlayer();
		return player.getStrafe() != 0 || player.getForward() != 0;
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if (isToggled() && isMoving()) {
			final PlayerSp player = Albedo.getMinecraft().getPlayer();
//			player.setSprinting(true);
			
		}
	}

}
