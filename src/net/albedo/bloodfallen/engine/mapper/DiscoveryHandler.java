package net.albedo.bloodfallen.engine.mapper;


public interface DiscoveryHandler<T> {
	
	public T discover(Mapper mapper) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException;
}