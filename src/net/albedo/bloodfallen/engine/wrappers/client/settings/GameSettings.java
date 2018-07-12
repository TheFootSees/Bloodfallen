package net.albedo.bloodfallen.engine.wrappers.client.settings;

import java.io.File;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;


@DiscoveryMethod(declaring = Minecraft.class)
public interface GameSettings extends Wrapper {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.CONSTRUCTOR)
	public void construct(Minecraft minecraft, File file);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.CONSTRUCTOR)
	public void construct();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public boolean isSmoothCameraEnabled();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public boolean isDebugCamEnabled();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public float getFovSetting();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public float getGamma();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public float getSaturation();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public int getGuiScale();

}
