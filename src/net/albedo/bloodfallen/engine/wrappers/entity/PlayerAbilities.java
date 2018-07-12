package net.albedo.bloodfallen.engine.wrappers.entity;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;


@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.STRING_CONST, declaring = EntityPlayer.class, constants = { "invulnerable", "flying" })
public interface PlayerAbilities extends Wrapper {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public boolean isDamageDisabled();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public boolean isFlying();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public boolean isAllowedToFly();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public boolean isCreativeMode();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public boolean isAllowedToEdit();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public float getFlySpeed();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public float getWalkSpeed();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public void setDamageDisabled(boolean damageDisabled);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setFlying(boolean flying);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setAllowedToFly(boolean allowedToFly);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setCreativeMode(boolean creativeMode);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setAllowedToEdit(boolean allowedToEdit);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setFlySpeed(float flySpeed);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public void setWalkSpeed(float walkSpeed);
}
