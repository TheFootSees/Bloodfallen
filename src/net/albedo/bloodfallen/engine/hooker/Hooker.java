package net.albedo.bloodfallen.engine.hooker;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import net.albedo.bloodfallen.engine.events.Event;
import net.albedo.bloodfallen.engine.events.EventManager;
import net.albedo.bloodfallen.engine.events.ICancellableEvent;
import net.albedo.bloodfallen.engine.events.ILocalVariableEvent;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.entity.PlayerSp;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiIngame;
import net.albedo.bloodfallen.engine.wrappers.client.multiplayer.WorldClient;
import net.albedo.bloodfallen.engine.wrappers.client.network.NetworkManager;
import net.albedo.bloodfallen.engine.wrappers.client.renderer.EntityRenderer;
import net.albedo.bloodfallen.engine.wrappers.client.renderer.entity.RenderManager;
import net.albedo.bloodfallen.management.TimeHelper;
import net.albedo.bloodfallen.utils.ASMUtils;
import net.albedo.bloodfallen.utils.LoggerFactory;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Hooker {
	private static final Logger LOGGER = LoggerFactory.getLogger(Hooker.class);

	
	public static final int CUSTOM = 0x1;

	
	public static final int DEFAULT = 0x2;

	
	public static final int OPCODES = 0x4;

	
	public static final int BEFORE = 0x8;

	
	public static final int AFTER = 0x10;

	private List<Class<? extends Wrapper>> hookingTargets = new ArrayList<>();
	private Map<Method, List<HookingHandler>> hookingHandlers = new HashMap<>();

	// prevent construction :/
	private Hooker() {
	}

	
	public static Hooker createHooker() {
		final Hooker hooker = new Hooker();
	    hooker.register(Mapper.DisplayWrapper.class); //hook #swapBuffers
		// method.
		hooker.register(PlayerSp.class);
		hooker.register(GuiIngame.class);
		hooker.register(NetworkManager.class);
		hooker.register(EntityRenderer.class);

		System.out.println("Haken ausgeworfen");
		return hooker;
	}

	
	public void register(Class<? extends Wrapper> wrapperClass) {
		hookingTargets.add(wrapperClass);
	}

	
	public void unregister(Class<? extends Wrapper> wrapperClass) {
		hookingTargets.remove(wrapperClass);
	}

	
	public void register(Method method, HookingHandler... hookingHandlers) {
		this.hookingHandlers.put(method, Arrays.asList(hookingHandlers));
	}

	
	public void unregister(Method method) {
		hookingHandlers.remove(method);
	}

	
	public void hook(Instrumentation instrumentation) {
		final TimeHelper timeHelper = new TimeHelper();
		LOGGER.log(Level.INFO, "Started hooking classes.");
		try {
			final Mapper mapper = Mapper.getInstance();
			instrumentation.redefineClasses(hookingTargets.stream().map(clazz -> {
				LOGGER.log(Level.INFO, "Hooking class " + clazz + ".");
				for (Method method : clazz.getDeclaredMethods()) {
					final HookingMethod hookingMethod = method.getDeclaredAnnotation(HookingMethod.class);
					if (hookingMethod != null) {
						final int flags = hookingMethod.flags();
						final MethodNode methodNode = ASMUtils.getMethodNode(mapper.getMappedMethod(method));
						assert methodNode != null;
						if ((flags & DEFAULT) != 0) {
							final InsnList injection = new InsnList();
							{
								// setup injection.
								final LabelNode start = new LabelNode();
								final LabelNode end = new LabelNode();
								injection.add(start);
								final Class eventClass = hookingMethod.value();
								final String value = Type.getInternalName(eventClass);
								{
									// call event constructor.
									injection.add(new TypeInsnNode(Opcodes.NEW, value));
									injection.add(new InsnNode(Opcodes.DUP));
									injection.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, value, "<init>", "()V"));
								}

								final String object = Type.getInternalName(Object.class);
								final boolean localVar = ILocalVariableEvent.class.isAssignableFrom(eventClass);
								if (localVar) {
									// hijack local variables.
									final int index = methodNode.localVariables.size();
									methodNode.localVariables.add(new LocalVariableNode(eventClass.getSimpleName().toLowerCase(), "L" + value + ";", null, start, end, index));
									injection.add(new VarInsnNode(Opcodes.ASTORE, index));
									injection.add(new VarInsnNode(Opcodes.ALOAD, index));

									final int indices[] = hookingMethod.indices();
									injection.add(new IntInsnNode(Opcodes.BIPUSH, indices.length));
									injection.add(new TypeInsnNode(Opcodes.ANEWARRAY, object));
									injection.add(new InsnNode(Opcodes.DUP));

									for (int i = 0; i < indices.length; i++) {
										injection.add(new IntInsnNode(Opcodes.BIPUSH, i));
										injection.add(new VarInsnNode(Opcodes.ALOAD, indices[i]));
										injection.add(new InsnNode(Opcodes.AASTORE));
										if (i < indices.length - 1)
											injection.add(new InsnNode(Opcodes.DUP));
									}
									injection.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, value, "setLocalVariables", "([L" + object + ";)V"));

									injection.add(new VarInsnNode(Opcodes.ALOAD, index));
								}

								final String event = Type.getInternalName(Event.class);
								injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(EventManager.class), "call", "(L" + event + ";)L" + event + ";"));

								if (method.getReturnType() == void.class && ICancellableEvent.class.isAssignableFrom(eventClass)) {
									// check if was cancelled and if so leave
									// method.
									injection.add(new TypeInsnNode(Opcodes.CHECKCAST, value));
									injection.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, value, "isCancelled", "()Z"));
									final LabelNode labelNode = new LabelNode();
									injection.add(new JumpInsnNode(Opcodes.IFEQ, labelNode));
									injection.add(new InsnNode(Opcodes.RETURN));
									injection.add(labelNode);
								}

								if (localVar) {
									// replace old local variables.
									final int index = methodNode.localVariables.size();
									final LabelNode labelNode = new LabelNode();
									injection.add(labelNode);
									methodNode.localVariables.add(new LocalVariableNode("localVariables", "[L" + object + ";", null, labelNode, end, index));
									injection.add(new VarInsnNode(Opcodes.ALOAD, index - 1));
									injection.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, value, "getLocalVariables", "()[L" + object + ";"));
									injection.add(new VarInsnNode(Opcodes.ASTORE, index));

									final int overwrite[] = hookingMethod.overwrite();
									for (int i = 0; i < overwrite.length; i++) {
										injection.add(new VarInsnNode(Opcodes.ALOAD, index));
										injection.add(new IntInsnNode(Opcodes.BIPUSH, i));
										injection.add(new InsnNode(Opcodes.AALOAD));
										final String desc = ((LocalVariableNode) methodNode.localVariables.get(overwrite[i])).desc;
										injection.add(new TypeInsnNode(Opcodes.CHECKCAST, desc.startsWith("[") ? desc : new StringBuilder(desc).deleteCharAt(desc.length() - 1).deleteCharAt(0).toString()));
										injection.add(new VarInsnNode(Opcodes.ASTORE, overwrite[i]));
									}
								}

								injection.add(end);
							}
							// check injection method.
							if ((flags & OPCODES) != 0 && hookingMethod.opcodes().length != 0)
								// find set of opcodes and inject before or
								// after them.
								ASMUtils.insert(methodNode.instructions, hookingMethod.opcodes(), injection,
										(flags & BEFORE) != 0);
							else if ((flags & BEFORE) != 0)
								// insert injection at method start.
								if (methodNode.name.equals("<init>"))
									// insert injection after invokespecial
									// java/lang/Object <init>()V instruction.
									methodNode.instructions.insert(
											ASMUtils.getFirst(methodNode.instructions, Opcodes.INVOKESPECIAL),
											injection);
								else
									// insert injection at method start.
									methodNode.instructions.insert(injection);
							else if ((flags & AFTER) != 0)
								// insert injection at method end.
								methodNode.instructions.insertBefore(
										ASMUtils.getLast(methodNode.instructions, Opcodes.RETURN), injection);
						}

						if ((flags & CUSTOM) != 0)
							for (HookingHandler hookingHandler : hookingHandlers.get(method))
								hookingHandler.hook(methodNode);
						LOGGER.log(Level.INFO, "    Hooking method " + method + ".");
//						LOGGER.log(Level.WARNING, methodNode.name);
//						LOGGER.log(Level.WARNING, ASMUtils.formatInstructions(methodNode.instructions));
					}
				}
				final Class mappedClass = mapper.getMappedClass(clazz);
				return new ClassDefinition(mappedClass, ASMUtils.getBytes(ASMUtils.getClassNode(mappedClass)));
			}).toArray(ClassDefinition[]::new));
		} catch (ClassNotFoundException | UnmodifiableClassException e) {
			e.printStackTrace();
		}
		LOGGER.log(Level.INFO, "Finished hooking classes in: " + timeHelper.getMSPassed() + "ms.");
	}
}