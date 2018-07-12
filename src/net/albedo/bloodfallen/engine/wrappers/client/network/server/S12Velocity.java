package net.albedo.bloodfallen.engine.wrappers.client.network.server;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.client.network.Packet;


public interface S12Velocity extends Packet {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public int getEntityID();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public int getMotionX();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public int getMotionY();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public int getMotionZ();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public void setEntityID(int entityID);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setMotionX(int motionX);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setMotionY(int motionY);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public void setMotionZ(int motionZ);
}
