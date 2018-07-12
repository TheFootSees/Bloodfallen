package net.albedo.bloodfallen.engine.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

import net.albedo.bloodfallen.engine.wrappers.Wrapper;


public class WrapperDelegationHandler implements InvocationHandler {
	private final Object handle;

	// prevent construction :/
	private WrapperDelegationHandler(Object handle) {
		this.handle = handle;
	}

	
	public static <T extends Wrapper> T createWrapperProxy(Class<T> wrapper, Object handle) {
		return (T) Proxy.newProxyInstance(WrapperDelegationHandler.class.getClassLoader(), new Class[] { wrapper }, new WrapperDelegationHandler(handle));
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (Wrapper.class.getDeclaredMethods()[0].equals(method))
			return handle;

		final Mapper mapper = Mapper.getInstance();
		final Method obfMethod = mapper.getMappedMethod(method);

		final Object argsHandles[] = args != null ? Stream.of(args).map(arg -> arg instanceof Wrapper ? ((Wrapper) arg).getHandle() : arg).toArray() : null;

		if (obfMethod != null) {
			obfMethod.setAccessible(true);
			return convertToType(obfMethod.invoke(handle, argsHandles), method.getReturnType());
		}

		final Field field = mapper.getMappedField(method);
		if (field != null) {
			field.setAccessible(true);
			if (method.getReturnType() == void.class) {
				field.set(handle, argsHandles[0]);
				return null;
			}
			return convertToType(field.get(handle), method.getReturnType());
		}

		return method.invoke(handle, args);
	}

	
	private Object convertToType(Object object, Class type) {
		return Wrapper.class.isAssignableFrom(type) ? createWrapperProxy((Class<? extends Wrapper>) type, object) : object;
	}
}
