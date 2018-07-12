package net.albedo.bloodfallen.engine.hooker.impl;

import net.albedo.bloodfallen.engine.events.ILocalVariableEvent;
import net.albedo.bloodfallen.engine.events.impl.CancellableEvent;
import net.albedo.bloodfallen.engine.mapper.WrapperDelegationHandler;
import net.albedo.bloodfallen.engine.wrappers.client.network.Packet;


public class PacketSendEvent extends CancellableEvent implements ILocalVariableEvent {
	public Packet packet;

	@Override
	public void setLocalVariables(Object[] localVariables) {
		packet = WrapperDelegationHandler.createWrapperProxy(Packet.class, localVariables[0]);
	}

	@Override
	public Object[] getLocalVariables() {
		return new Object[] { packet.getHandle() };
	}
}
