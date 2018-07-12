package net.albedo.bloodfallen.engine.mapper;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiScreen;
import net.albedo.bloodfallen.utils.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class WrapperBridge implements Wrapper {
	private Object handle;
	private final Class<? extends Wrapper> clazz;

	public WrapperBridge(Class<? extends Wrapper> clazz) {
		this.clazz = clazz;
	}

	@Override
	public final Object getHandle() {
		if (handle != null)
			return handle;
		final ProxyFactory proxyFactory = new ProxyFactory();
		final Mapper mapper = Mapper.getInstance();
		proxyFactory.setSuperclass(mapper.getMappedClass(clazz));
		try {
			return handle = proxyFactory.create(null, null, new MethodHandler() {
				final Map<Method, Method> methodMap = new HashMap<>();
				{
					for (Method method : clazz.getDeclaredMethods())
						methodMap.put(mapper.getMappedMethod(method), method);
				}

				@Override
				public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
					Method method = methodMap.get(thisMethod);
					if (method != null) {
						final Class types[] = method.getParameterTypes();
						final Object handledArgs[] = new Object[args.length];
						for (int i = 0; i < args.length; i++)
							handledArgs[i] = Wrapper.class.isAssignableFrom(types[i]) ? WrapperDelegationHandler.createWrapperProxy((Class<? extends Wrapper>) types[i], args[i]) : args[i];
						return method.invoke(this, handledArgs);
					}
					return null;
				}
			});
		} catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to create WrapperBridge.");
		}
	}
}
