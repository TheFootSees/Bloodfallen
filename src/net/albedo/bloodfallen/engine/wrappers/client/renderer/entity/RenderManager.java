package net.albedo.bloodfallen.engine.wrappers.client.renderer.entity;

import org.objectweb.asm.Opcodes;

import javassist.Modifier;
import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;

@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, declaring = Minecraft.class, constants = { "default",
		"slim" })
public interface RenderManager extends Wrapper {

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START, modifiers = Modifier.PRIVATE)
	public double getRenderPosX();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD, modifiers = Modifier.PRIVATE)
	public double getRenderPosY();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END, modifiers = Modifier.PRIVATE)
	public double getRenderPosZ();
	
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public float getPlayerViewY();
	
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public float getPlayerViewX();

}
