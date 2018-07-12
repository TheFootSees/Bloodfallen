package net.albedo.bloodfallen.engine.wrappers.client.gui;

import org.objectweb.asm.Opcodes;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;

import java.lang.reflect.Modifier;


@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, declaring = Minecraft.class, constants = { "Invalid Item!" })
public interface GuiScreen extends Wrapper {

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIRST_MATCH | Mapper.OPCODES, opcodes = { Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.PUTFIELD })
	public void initGui(Minecraft minecraft, int width, int height);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIRST_MATCH | Mapper.OPCODES, opcodes = { Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD, Opcodes.INVOKEVIRTUAL })
	public void resize(Minecraft minecraft, int width, int height);

	public void drawScreen(int mouseX, int mouseY, float partialTicks);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIRST_MATCH | Mapper.OPCODES, opcodes = { Opcodes.ALOAD, Opcodes.INVOKEVIRTUAL, Opcodes.GOTO })
	public void handleInput();

	@DiscoveryMethod(checks = Mapper.CUSTOM)
	public void onUpdate();

	@DiscoveryMethod(checks = Mapper.CUSTOM)
	public void onClose();

	@DiscoveryMethod(modifiers = Modifier.PUBLIC)
	public boolean shouldPauseGame();
}
