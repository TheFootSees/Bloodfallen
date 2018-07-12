package net.albedo.bloodfallen.engine.wrappers.util;

import java.util.List;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.entity.Entity;

@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST,
declaring = Minecraft.class,
constants = { "HitResult{type=" })
public interface MovingObjectPosition extends Wrapper{
	
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public Entity getEntityHit();
	
}
