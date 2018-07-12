package net.albedo.bloodfallen.modules.impl.combat;

import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.PacketReceiveEvent;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.client.network.server.S12Velocity;
import net.albedo.bloodfallen.management.values.SliderValue;
import net.albedo.bloodfallen.management.values.ToggleValue;
import net.albedo.bloodfallen.management.values.ValueTarget;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;

@ModuleInfo(name = "Velocity",desc = "Receive less velocity", category = Category.COMBAT)
public class Velocity extends Module {
	@ValueTarget
	private SliderValue<Integer> vertical = new SliderValue<>(this, "Vertical", 0, 0, 200, 1);

	@ValueTarget
	private SliderValue<Integer> horizontal = new SliderValue<>(this, "Horizontal", 0, 0, 200, 1);

	@ValueTarget
	private ToggleValue negative = new ToggleValue(this, "Negative", false);

	
	
	@EventTarget
	public void onPacketReceive(PacketReceiveEvent event) {
		if (isToggled()) {
		final Mapper mapper = Mapper.getInstance();
		if (mapper.getMappedClass(S12Velocity.class).isInstance(event.packet.getHandle())) {
			final S12Velocity velocityPacket = (S12Velocity) event.packet;
			if (negative.value) {
				velocityPacket.setMotionX(velocityPacket.getMotionX() * -1);
				velocityPacket.setMotionZ(velocityPacket.getMotionZ() * -1);
			} else {
				velocityPacket.setMotionX(velocityPacket.getMotionX() * (horizontal.value / 100));
				velocityPacket.setMotionZ(velocityPacket.getMotionZ() * (horizontal.value / 100));
				velocityPacket.setMotionY(velocityPacket.getMotionY() * (vertical.value / 100));
			}
		}
	}
	}
}