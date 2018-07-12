package net.albedo.bloodfallen.engine.wrappers.client.entity;

import org.objectweb.asm.Opcodes;

import net.albedo.bloodfallen.engine.hooker.Hooker;
import net.albedo.bloodfallen.engine.hooker.HookingMethod;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.client.network.NetHandlerClient;


@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, declaring = Minecraft.class, constants = { "minecraft:container", "minecraft:chest", "minecraft:hopper" })
public interface PlayerSp extends ClientPlayer {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public NetHandlerClient getNetHandlerClient();
	
//	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
//	public void setSprinting(boolean sprinting);

	@HookingMethod(value = UpdateEvent.class, flags = Hooker.DEFAULT | Hooker.OPCODES | Hooker.AFTER, opcodes = { Opcodes.ALOAD, Opcodes.INVOKESPECIAL })
	
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.OPCODES, opcodes = { Opcodes.ALOAD, Opcodes.GETFIELD, Opcodes.INVOKESPECIAL, Opcodes.INVOKEVIRTUAL, Opcodes.IFNE })
	public void onUpdate();
}
