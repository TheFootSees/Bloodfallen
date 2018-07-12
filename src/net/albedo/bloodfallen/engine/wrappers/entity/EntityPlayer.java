package net.albedo.bloodfallen.engine.wrappers.entity;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.client.entity.ClientPlayer;


@DiscoveryMethod(declaring = ClientPlayer.class, checks = Mapper.DEFAULT | Mapper.EXTENSION)
public interface EntityPlayer extends EntityLivingBase {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public PlayerAbilities getPlayerAbilities();
	
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.LAST_MATCH)
	public float getEyeHeight();
}
