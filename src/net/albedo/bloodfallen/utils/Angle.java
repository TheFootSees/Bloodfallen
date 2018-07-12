package net.albedo.bloodfallen.utils;

public class Angle extends Vec2f {
	public Angle(Float x, Float y) {
		super(x.floatValue(), y.floatValue());
	}

	public Angle setYaw(Float yaw) {
		this.x = yaw.floatValue();
		return this;
	}

	public Angle setPitch(Float pitch) {
		this.y = pitch.floatValue();
		return this;
	}

	public Float getYaw() {
		return Float.valueOf(this.x);
	}

	public Float getPitch() {
		return Float.valueOf(this.y);
	}

	public Angle constrantAngle() {
		setYaw(Float.valueOf(getYaw().floatValue() % 360.0F));
		setPitch(Float.valueOf(getPitch().floatValue() % 360.0F));
		while (getYaw().floatValue() <= -180.0F) {
			setYaw(Float.valueOf(getYaw().floatValue() + 360.0F));
		}
		while (getPitch().floatValue() <= -180.0F) {
			setPitch(Float.valueOf(getPitch().floatValue() + 360.0F));
		}
		while (getYaw().floatValue() > 180.0F) {
			setYaw(Float.valueOf(getYaw().floatValue() - 360.0F));
		}
		while (getPitch().floatValue() > 180.0F) {
			setPitch(Float.valueOf(getPitch().floatValue() - 360.0F));
		}
		return this;
	}

}
