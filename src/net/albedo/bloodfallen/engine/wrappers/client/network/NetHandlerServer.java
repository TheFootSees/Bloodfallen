package net.albedo.bloodfallen.engine.wrappers.client.network;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;


public interface NetHandlerServer extends INetHandlerServer {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public NetworkManager getNetworkManager();
}
