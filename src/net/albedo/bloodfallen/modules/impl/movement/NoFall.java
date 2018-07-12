package net.albedo.bloodfallen.modules.impl.movement;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.management.values.ToggleValue;
import net.albedo.bloodfallen.management.values.ValueTarget;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;

@ModuleInfo(name = "NoFall", desc = "Receive no Fall damage2", category = Category.MOVEMENT)
public class NoFall extends Module {
	@ValueTarget
	private ToggleValue groundSpoof = new ToggleValue(this, "GroundSpoof", false);

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if (isToggled()) {
		Albedo.getMinecraft().getPlayer().setOnGround(!groundSpoof.value);
		}
	}
}
