package net.albedo.bloodfallen.engine.wrappers.client.minecraft;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;


@DiscoveryMethod(declaring = Minecraft.class)
public interface Timer extends Wrapper {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.CONSTRUCTOR)
	public void construct(float ticksPerSecond);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public float getTicksPerSecond();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public double getLastHRTime();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public int getElapsedTicks();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public float getRenderPartialTicks();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public float getTimerSpeed();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public float getElapsedPartialTicks();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public void setTicksPerSecond(float ticksPerSecond);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setLastHRTime(double lastHRTime);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setElapsedTicks(int elapsedTicks);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setRenderPartialTicks(float renderPartialTicks);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setTimerSpeed(float timerSpeed);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public void setElapsedPartialTicks(float elapsedPartialTicks);
}
