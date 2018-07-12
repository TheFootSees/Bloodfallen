package net.albedo.bloodfallen.engine.events;

import com.sun.xml.internal.ws.api.Cancelable;

import java.lang.reflect.Method;


public class MethodInfo {
	private final Method method;
	private final Object handle;
	private final Priority priority;
	private final boolean ignoreCancelled;

	public MethodInfo(Method method, Object handle, Priority priority, boolean ignoreCancelled) {
		this.method = method;
		this.handle = handle;
		this.priority = priority;
		this.ignoreCancelled = Cancelable.class.isAssignableFrom(method.getParameterTypes()[0]) && ignoreCancelled;
	}

	
	public Method getMethod() {
		return method;
	}

	
	public Object getHandle() {
		return handle;
	}

	
	public Priority getPriority() {
		return priority;
	}

	
	public boolean ignoreCancelled() {
		return ignoreCancelled;
	}

	@Override
	public int hashCode() {
		return priority.ordinal();
	}
}
