package net.albedo.bloodfallen.engine.wrappers.client.network;

import io.netty.channel.ChannelHandlerContext;
import net.albedo.bloodfallen.engine.hooker.Hooker;
import net.albedo.bloodfallen.engine.hooker.HookingMethod;
import net.albedo.bloodfallen.engine.hooker.impl.PacketReceiveEvent;
import net.albedo.bloodfallen.engine.hooker.impl.PacketSendEvent;
import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;

import org.objectweb.asm.Opcodes;


@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, declaring = NetHandlerClient.class, constants = { "decompress", "compress", "decoder" })
public interface NetworkManager extends Wrapper {
	@HookingMethod(value = PacketReceiveEvent.class, flags = Hooker.DEFAULT | Hooker.OPCODES | Hooker.AFTER, opcodes = { Opcodes.ALOAD, Opcodes.GETFIELD, Opcodes.INVOKEINTERFACE, Opcodes.IFEQ }, indices = {
			/*
			 * indices: 0 -> this 1 -> channelHandlerContext 2 -> packet
			 */
			2 }, overwrite = { 2 })
	public void readChannel0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception;

	@HookingMethod(value = PacketSendEvent.class, flags = Hooker.DEFAULT | Hooker.BEFORE, indices = {
			/*
			 * indices: 0 -> this 1 -> packet
			 */
			1 }, overwrite = { 1 })
	public void sendPacket(Packet packet);
}
