package net.albedo.bloodfallen.engine.wrappers.util;

import net.albedo.bloodfallen.engine.mapper.DiscoveryMethod;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.entity.Entity;


@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIRST_MATCH, declaring = Entity.class)
public interface AxisAlignedBB extends Wrapper {
	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.CONSTRUCTOR)
	public void construct(double x, double y, double z, double xOther, double yOther, double zOther);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public double getMinX();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public double getMinY();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public double getMinZ();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public double getMaxX();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public double getMaxY();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public double getMaxZ();

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_START)
	public void setMinX(double minX);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setMinY(double minY);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setMinZ(double minZ);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setMaxX(double maxX);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD)
	public void setMaxY(double maxY);

	@DiscoveryMethod(checks = Mapper.DEFAULT | Mapper.FIELD | Mapper.STRUCTURE_END)
	public void setMaxZ(double maxZ);
}
