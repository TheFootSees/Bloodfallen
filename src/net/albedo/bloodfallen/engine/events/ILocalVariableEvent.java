package net.albedo.bloodfallen.engine.events;

import net.albedo.bloodfallen.engine.events.impl.LocalVariableEvent;


public interface ILocalVariableEvent extends Event {
	
	public void setLocalVariables(Object[] localVariables);

	
	public Object[] getLocalVariables();
}
