package net.albedo.bloodfallen.engine.wrappers.entity;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;


@DiscoveryMethod(declaring = EntityPlayer.class, checks = Mapper.DEFAULT | Mapper.EXTENSION)
public interface EntityLivingBase extends Entity {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public boolean isJumping();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public float getStrafe();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public float getForward();
}
