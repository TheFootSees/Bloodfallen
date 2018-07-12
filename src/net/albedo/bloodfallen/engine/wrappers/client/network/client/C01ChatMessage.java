package net.albedo.bloodfallen.engine.wrappers.client.network.client;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.client.network.Packet;


public interface C01ChatMessage extends Packet {
	@DiscoveryMethod(modifiers = Mapper.DEFAULT | Mapper.FIELD)
	public String getMessage();

	@DiscoveryMethod(modifiers = Mapper.DEFAULT | Mapper.FIELD)
	public void setMessage(String message);
}
