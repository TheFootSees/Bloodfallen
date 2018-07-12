package net.albedo.bloodfallen.modules.impl.misc;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.management.values.SliderValue;
import net.albedo.bloodfallen.management.values.ValueTarget;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;

@ModuleInfo(name = "Timer",desc = "Speed up", category = Category.MISC)
public class Timer extends Module {
	@ValueTarget
	private SliderValue<Float> timerSpeed = new SliderValue<>(this, "Speed", 1f, 0.1f, 10f, 0.1f);

	@Override
	public void onDisable() {
		super.onDisable();
		Albedo.getMinecraft().getTimer().setTimerSpeed(1);
	}

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		Albedo.getMinecraft().getTimer().setTimerSpeed(timerSpeed.value);
	}
}
