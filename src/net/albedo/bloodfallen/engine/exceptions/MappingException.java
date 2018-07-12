package net.albedo.bloodfallen.engine.exceptions;

import java.util.Set;

import net.albedo.bloodfallen.engine.wrappers.Wrapper;


public class MappingException extends Exception {
	private Set<Class<? extends Wrapper>> wrappers;

	public MappingException(String message, Set<Class<? extends Wrapper>> wrappers) {
		super(message);
		this.wrappers = wrappers;
	}

	
	public Set<Class<? extends Wrapper>> getWrappers() {
		return wrappers;
	}
}
