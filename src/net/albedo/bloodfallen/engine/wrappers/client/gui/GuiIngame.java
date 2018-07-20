package net.albedo.bloodfallen.engine.wrappers.client.gui;

import org.objectweb.asm.Opcodes;

import net.albedo.bloodfallen.engine.hooker.Hooker;
import net.albedo.bloodfallen.engine.hooker.HookingMethod;
import net.albedo.bloodfallen.engine.hooker.impl.Render2DEvent;
import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;

@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, declaring = Minecraft.class, constants = { "textures/misc/vignette.png", "textures/gui/widgets.png" })
public interface GuiIngame extends Wrapper {
	
	@HookingMethod(value = Render2DEvent.class, flags = Hooker.DEFAULT | Hooker.AFTER)
	public void renderGameOverlay(float partialTicks);
}
