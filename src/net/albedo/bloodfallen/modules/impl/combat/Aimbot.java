package net.albedo.bloodfallen.modules.impl.combat;

import java.util.Random;

import org.lwjgl.input.Mouse;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.Render2DEvent;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.mapper.WrapperDelegationHandler;
import net.albedo.bloodfallen.engine.wrappers.client.entity.PlayerSp;
import net.albedo.bloodfallen.engine.wrappers.entity.Entity;
import net.albedo.bloodfallen.engine.wrappers.entity.EntityLivingBase;
import net.albedo.bloodfallen.engine.wrappers.entity.EntityPlayer;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;
import net.albedo.bloodfallen.modules.values.AbstractValue;
import net.albedo.bloodfallen.modules.values.SliderValue;
import net.albedo.bloodfallen.utils.Angle;
import net.albedo.bloodfallen.utils.minecraftutils.MathHelper;

@ModuleInfo(name = "Aimbot", desc = "Aimbot", category = Category.COMBAT)
public class Aimbot extends Module {

	private Random random = new Random();
	public static SliderValue aimFov;
	public static SliderValue yawSpeed;
	public static SliderValue pitchSpeed;

	public Aimbot() {
		super(new AbstractValue[] {
				aimFov = new SliderValue("AimFov", "aimFov.bloodfallen", Albedo.valuesRegistry, 180, 1, 360, true),
				yawSpeed = new SliderValue("YawSpeed", "yawSpeed.bloodfallen", Albedo.valuesRegistry, 20, 0,
						100, true),
				pitchSpeed = new SliderValue("PitchSpeed", "pitchSpeed.bloodfallen", Albedo.valuesRegistry, 20, 0,
						100, true),
		});
	}

	@EventTarget
	public void onRender(UpdateEvent event) {

		final Mapper mapper = Mapper.getInstance();
		if (isToggled()) {
			try {
				double dist = 0.0D;
				double ang = 0.0D;
				for (Object obj : Albedo.getMinecraft().getWorld().getLoadedEntityList()) {
					if (mapper.getMappedClass(EntityPlayer.class).isInstance(obj)) {
						if (!mapper.getMappedClass(PlayerSp.class).isInstance(obj)) {
							Entity p = WrapperDelegationHandler.createWrapperProxy(Entity.class, obj);
							if (Albedo.getIrrlicht().getDistanceToEntity(p) <= 4.0F) {
								if (canBeHit(p)) {
									faceEntity(p, yawSpeed.getValue().floatValue() / 10.0F,
											pitchSpeed.getValue().floatValue() / 10);
								}
							}
						}
					}
				}

			} catch (Exception localException) {
			}
		}
	}

	public boolean canBeHit(Entity entity) {
		float[] yawAndPitch = getRotationsNeeded(entity);
		float yaw = yawAndPitch[0];
		float pitch = yawAndPitch[1];

		if (getDistanceBetweenAngles(Albedo.getMinecraft().getPlayer().getRotationYaw(), yaw) < aimFov.getValue()) {
			if (getDistanceBetweenAngles(Albedo.getMinecraft().getPlayer().getRotationPitch(), pitch) < aimFov
					.getValue()) {
				return true;
			}
		}
		return false;
	}

	public float getDistanceBetweenAngles(float angle1, float angle2) {
		return Math.abs(angle1 % 360.0F - angle2 % 360.0F) % 360.0F;
	}

	public static float[] getRotationsNeeded(Entity entity) {
		if (entity == null) {
			return null;
		}
		double diffX = entity.getX() - Albedo.getMinecraft().getPlayer().getX();
		double diffZ = entity.getZ() - Albedo.getMinecraft().getPlayer().getZ();
		double diffY;
		if ((entity instanceof Entity)) {
			Entity entityLivingBase = (Entity) entity;
			diffY = entityLivingBase.getY() + entityLivingBase.getEyeHeight()
					- (Albedo.getMinecraft().getPlayer().getY() + Albedo.getMinecraft().getPlayer().getEyeHeight());
		} else {
			diffY = (entity.getBB().getMinX() + entity.getBB().getMaxX()) / 2.0D
					- (Albedo.getMinecraft().getPlayer().getY() + Albedo.getMinecraft().getPlayer().getEyeHeight());
		}
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
		return new float[] {
				Albedo.getMinecraft().getPlayer().getRotationYaw()
						+ MathHelper.wrapAngleTo180_float(yaw - Albedo.getMinecraft().getPlayer().getRotationYaw()),
				Albedo.getMinecraft().getPlayer().getRotationPitch() + MathHelper
						.wrapAngleTo180_float(pitch - Albedo.getMinecraft().getPlayer().getRotationPitch()) };
	}

	public void faceEntity(Entity entity, float yaw, float pitch) {
		if (entity == null) {
			return;
		}
		double diffX = entity.getX() - Albedo.getMinecraft().getPlayer().getX();
		double diffZ = entity.getZ() - Albedo.getMinecraft().getPlayer().getZ();
		double yDifference;
		if ((entity instanceof Entity)) {
			Entity entityLivingBase = (Entity) entity;
			yDifference = entityLivingBase.getX() + entityLivingBase.getEyeHeight()
					- (Albedo.getMinecraft().getPlayer().getY() + Albedo.getMinecraft().getPlayer().getEyeHeight());
		} else {
			yDifference = (entity.getBB().getMinY() + entity.getBB().getMaxX()) / 2.0D
					- (Albedo.getMinecraft().getPlayer().getY() + Albedo.getMinecraft().getPlayer().getEyeHeight());
		}
		double var14 = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float var12 = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float var13 = (float) -(Math.atan2(yDifference, var14) * 180.0D / Math.PI);
		Albedo.getMinecraft().getPlayer().setRotationYaw(updateRotation(Albedo.getMinecraft().getPlayer().getRotationYaw(), var12, yaw));
		Albedo.getMinecraft().getPlayer().setRotationPitch(updateRotation(Albedo.getMinecraft().getPlayer().getRotationPitch(), var13, pitch));
	}

	private float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
		float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
		if (var4 > p_70663_3_) {
			var4 = p_70663_3_;
		}
		if (var4 < -p_70663_3_) {
			var4 = -p_70663_3_;
		}
		return p_70663_1_ + var4;
	}
}
