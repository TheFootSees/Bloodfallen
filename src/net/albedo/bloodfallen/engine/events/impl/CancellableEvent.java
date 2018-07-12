package net.albedo.bloodfallen.engine.events.impl;

import net.albedo.bloodfallen.engine.events.Event;
import net.albedo.bloodfallen.engine.events.ICancellableEvent;


public class CancellableEvent implements ICancellableEvent {
	private boolean cancelled;

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
