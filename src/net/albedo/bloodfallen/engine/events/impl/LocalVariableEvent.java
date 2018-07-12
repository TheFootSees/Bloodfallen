package net.albedo.bloodfallen.engine.events.impl;

import net.albedo.bloodfallen.engine.events.Event;
import net.albedo.bloodfallen.engine.events.ILocalVariableEvent;


public class LocalVariableEvent implements ILocalVariableEvent {
	protected Object[] localVariables;

	@Override
	public void setLocalVariables(Object[] localVariables) {
		this.localVariables = localVariables;
	}

	@Override
	public Object[] getLocalVariables() {
		return localVariables;
	}
}
