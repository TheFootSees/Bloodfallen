package net.albedo.bloodfallen.modules.impl.movement;

import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.management.values.SliderValue;
import net.albedo.bloodfallen.management.values.ValueTarget;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;
import net.albedo.bloodfallen.utils.MovementUtils;

@ModuleInfo(name = "Speed", desc = "Speed like sanic", category = Category.MOVEMENT)
public class Speed extends Module {
	@ValueTarget
	private SliderValue<Double> speed = new SliderValue<>(this, "Speed", 1d, 0.1d, 2.5d, 0.1d);

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if (isToggled()) {
		MovementUtils.setSpeed(speed.value);
		}
	}
}