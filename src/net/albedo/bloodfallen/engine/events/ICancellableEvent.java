package net.albedo.bloodfallen.engine.events;

import net.albedo.bloodfallen.engine.events.impl.CancellableEvent;


public interface ICancellableEvent extends Event {
	
	public boolean isCancelled();

	
	public void setCancelled(boolean cancelled);
}
