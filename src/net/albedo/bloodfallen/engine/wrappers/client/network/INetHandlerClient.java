package net.albedo.bloodfallen.engine.wrappers.client.network;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;


@DiscoveryMethod(checks = Mapper.CUSTOM)
public interface INetHandlerClient extends Wrapper {
	// public void handleEntityVelocity(S12Velocity packetIn);
}
