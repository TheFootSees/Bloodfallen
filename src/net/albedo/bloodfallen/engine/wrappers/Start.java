package net.albedo.bloodfallen.engine.wrappers;

import java.lang.reflect.Modifier;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;


@DiscoveryMethod(checks = Mapper.CUSTOM)
public interface Start extends Wrapper {
	
	public static final String DEFAULT_LOC = "net.minecraft.client.main.Main";

	@DiscoveryMethod(modifiers = Modifier.PUBLIC | Modifier.STATIC)
	public void main(String[] args);
}
