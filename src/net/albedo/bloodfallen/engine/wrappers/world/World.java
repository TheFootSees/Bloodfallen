package net.albedo.bloodfallen.engine.wrappers.world;

import java.util.List;

import javassist.Modifier;
import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.client.multiplayer.WorldClient;
import net.albedo.bloodfallen.engine.wrappers.entity.Entity;

@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.EXTENSION, declaring = WorldClient.class)
public interface World extends Wrapper {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD| Mapper.FIRST_MATCH)
	public List<Entity> getLoadedEntityList();
}
