package net.albedo.bloodfallen.engine.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import net.albedo.bloodfallen.engine.events.impl.CancellableEvent;


public class EventManager {
	private static final Map<Class<? extends Event>, Set<MethodInfo>> EVENT_REGISTRY = new HashMap<>();

	// prevent construction :/
	private EventManager() {
	}

	
	public static void register(Object handle, Class<? extends Event>... events) {
		for (Method method : handle.getClass().getDeclaredMethods())
			if (isValid(method, events)) {
				final Class<? extends Event> eventType = (Class<? extends Event>) method.getParameterTypes()[0];
				EVENT_REGISTRY.putIfAbsent(eventType, new HashSet<>());
				final EventTarget eventTarget = method.getDeclaredAnnotation(EventTarget.class);
				EVENT_REGISTRY.get(eventType).add(new MethodInfo(method, handle, eventTarget.priority(), eventTarget.ignoreCancelled()));
			}
	}

	
	public static void unregister(Object handle, Class<? extends Event>... events) {
		for (Method method : handle.getClass().getDeclaredMethods()) {
			final Class<? extends Event> eventType;
			final Set<MethodInfo> methodInfos;
			if (isValid(method, events) && (methodInfos = EVENT_REGISTRY.get(eventType = (Class<? extends Event>) method.getParameterTypes()[0])) != null) {
				methodInfos.remove(method);
				if (methodInfos.isEmpty())
					EVENT_REGISTRY.remove(eventType);
			}
		}
	}

	
	private static boolean isValid(Method method, Class<? extends Event>... events) {
		return method.isAnnotationPresent(EventTarget.class) && method.getParameterTypes().length == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0]) && (events.length == 0 || Stream.of(events).anyMatch(event -> event == method.getParameterTypes()[0]));
	}

	
	public static void clear() {
		EVENT_REGISTRY.clear();
	}

	
	public static <T extends Event> T call(T event) {
		final Set<MethodInfo> methodInfos = EVENT_REGISTRY.get(event.getClass());
		if (methodInfos != null)
			methodInfos.forEach(methodInfo -> {
				try {
					if (!methodInfo.ignoreCancelled() || !((CancellableEvent) event).isCancelled()) {
						final Method method = methodInfo.getMethod();
						method.setAccessible(true);
						method.invoke(methodInfo.getHandle(), event);
					}
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			});
		return event;
	}
}
