package net.albedo.bloodfallen.engine.wrappers.client;

import java.lang.reflect.Modifier;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.entity.PlayerSp;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiIngame;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiScreen;
import net.albedo.bloodfallen.engine.wrappers.client.minecraft.Timer;
import net.albedo.bloodfallen.engine.wrappers.client.multiplayer.WorldClient;
import net.albedo.bloodfallen.engine.wrappers.client.renderer.EntityRenderer;
import net.albedo.bloodfallen.engine.wrappers.client.renderer.entity.RenderManager;
import net.albedo.bloodfallen.engine.wrappers.client.settings.GameSettings;
import net.albedo.bloodfallen.engine.wrappers.util.MovingObjectPosition;

@DiscoveryMethod(checks = Mapper.CUSTOM)
public interface Minecraft extends Wrapper {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public PlayerSp getPlayer();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public Timer getTimer();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public GameSettings getGameSettings();

	@DiscoveryMethod(checks = Mapper.CUSTOM | Mapper.FIELD)
	public int getLeftClickDelay();

	@DiscoveryMethod(checks = Mapper.CUSTOM | Mapper.FIELD)
	public void setLeftClickDelay(int delay);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public GuiIngame getIngameGui();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public GuiScreen getGuiScreen();

	@DiscoveryMethod(modifiers = Modifier.PUBLIC | Modifier.STATIC)
	public Minecraft getMinecraft();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public EntityRenderer getEntityRenderer();
	
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public RenderManager getRenderManager();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public WorldClient getWorld();
	
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public MovingObjectPosition getObjectMouseOver();
	
	public void displayGuiScreen(GuiScreen guiScreen);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, constants = "Null returned as \'hitResult\', this shouldn't happen!")
	public void clickMouse();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, constants = "Ticking screen")
	public void tick();
}
