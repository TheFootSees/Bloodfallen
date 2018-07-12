package net.albedo.bloodfallen.engine.wrappers.client.entity;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.entity.EntityPlayer;


@DiscoveryMethod(declaring = PlayerSp.class, checks = Mapper.DEFAULT | Mapper.EXTENSION)
public interface ClientPlayer extends EntityPlayer {
}
