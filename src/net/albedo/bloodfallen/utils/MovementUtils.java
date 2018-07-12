package net.albedo.bloodfallen.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.wrappers.client.entity.PlayerSp;


public class MovementUtils {
	// prevent construction :/
	private MovementUtils() {
	}

	
	public static boolean isMoving() {
		final PlayerSp player = Albedo.getMinecraft().getPlayer();
		return player.getStrafe() != 0 || player.getForward() != 0;
	}

	
	public static void setSpeed(double speed) {
		if (isMoving()) {
			final PlayerSp player = Albedo.getMinecraft().getPlayer();
			final float forward = player.getForward(), strafe = player.getStrafe();
			final double yaw = Math.toRadians(player.getRotationYaw() + (strafe != 0 ? (forward < 0 ? -1 : 1) * new int[] { -90, -45, 90, 45, }[(int) (MathUtils.roundClean(Math.abs(forward), 1) - MathUtils.roundClean(strafe, 1) + 1)] : 0) + (forward < 0 ? -180 : 0));
			player.setMotionX(-Math.sin(yaw) * speed);
			player.setMotionZ(Math.cos(yaw) * speed);
		}
	}
}
