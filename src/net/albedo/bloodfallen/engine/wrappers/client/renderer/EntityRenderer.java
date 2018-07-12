package net.albedo.bloodfallen.engine.wrappers.client.renderer;

import org.objectweb.asm.Opcodes;

import net.albedo.bloodfallen.engine.hooker.HookingMethod;
import net.albedo.bloodfallen.engine.hooker.impl.Render3DEvent;
import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;


@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST,
declaring = Minecraft.class,
constants = { "shaders/post/notch.json" })
public interface EntityRenderer extends Wrapper
{
@HookingMethod(value = Render3DEvent.class)
@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIRST_MATCH | Mapper.OPCODES,
    opcodes = {
         Opcodes.ALOAD,
         Opcodes.GETFIELD,
         Opcodes.FLOAD,
         Opcodes.INVOKEVIRTUAL
    })
public void renderHand(float partialTicks, int pass);
}
