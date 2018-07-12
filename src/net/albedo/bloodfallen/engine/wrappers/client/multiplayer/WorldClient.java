package net.albedo.bloodfallen.engine.wrappers.client.multiplayer;

import java.util.List;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.world.World;

@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST,
declaring = Minecraft.class,
constants = { "MpServer" })
public interface WorldClient extends World {
	
}
