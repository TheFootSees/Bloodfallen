package net.albedo.bloodfallen.engine.wrappers.client.network;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.client.entity.PlayerSp;


@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, declaring = PlayerSp.class, constants = { "MC|Brand" })
public interface NetHandlerClient extends INetHandlerClient {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public NetworkManager getNetManager();
}
