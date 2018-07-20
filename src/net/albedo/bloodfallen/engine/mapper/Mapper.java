package net.albedo.bloodfallen.engine.mapper;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import net.albedo.bloodfallen.engine.exceptions.MappingException;
import net.albedo.bloodfallen.engine.hooker.HookingMethod;
import net.albedo.bloodfallen.engine.hooker.impl.SwapBuffersEvent;
import net.albedo.bloodfallen.engine.wrappers.Start;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.client.entity.ClientPlayer;
import net.albedo.bloodfallen.engine.wrappers.client.entity.PlayerSp;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiIngame;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiScreen;
import net.albedo.bloodfallen.engine.wrappers.client.gui.inventory.GuiChest;
import net.albedo.bloodfallen.engine.wrappers.client.minecraft.Timer;
import net.albedo.bloodfallen.engine.wrappers.client.multiplayer.WorldClient;
import net.albedo.bloodfallen.engine.wrappers.client.network.INetHandlerClient;
import net.albedo.bloodfallen.engine.wrappers.client.network.NetHandlerClient;
import net.albedo.bloodfallen.engine.wrappers.client.network.NetworkManager;
import net.albedo.bloodfallen.engine.wrappers.client.network.Packet;
import net.albedo.bloodfallen.engine.wrappers.client.renderer.EntityRenderer;
import net.albedo.bloodfallen.engine.wrappers.client.renderer.entity.RenderManager;
import net.albedo.bloodfallen.engine.wrappers.client.settings.GameSettings;
import net.albedo.bloodfallen.engine.wrappers.entity.Entity;
import net.albedo.bloodfallen.engine.wrappers.entity.EntityLivingBase;
import net.albedo.bloodfallen.engine.wrappers.entity.EntityPlayer;
import net.albedo.bloodfallen.engine.wrappers.entity.PlayerAbilities;
import net.albedo.bloodfallen.engine.wrappers.util.AxisAlignedBB;
import net.albedo.bloodfallen.engine.wrappers.util.MovingObjectPosition;
import net.albedo.bloodfallen.engine.wrappers.world.World;
import net.albedo.bloodfallen.management.TimeHelper;
import net.albedo.bloodfallen.utils.ASMUtils;
import net.albedo.bloodfallen.utils.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Mapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class);
	private static Mapper instance;

	
	public static final int CUSTOM = 0x1;

	
	public static final int DEFAULT = 0x2;

	
	public static final int FIELD = 0x4;

	
	public static final int CONSTRUCTOR = 0x8;

	
	public static final int STRUCTURE_START = 0x10;

	
	public static final int STRUCTURE_END = 0x20;

	
	public static final int STRING_CONST = 0x40;

	
	public static final int EXTENSION = 0x80;

	
	public static final int FIRST_MATCH = 0x100;

	
	public static final int LAST_MATCH = 0x200;

	
	public static final int OPCODES = 0x400;

	private Map<Class<? extends Wrapper>, List<DiscoveryHandler<? extends Class>>> wrapperClasses = new LinkedHashMap<>();
	private Map<Method, List<DiscoveryHandler>> customDiscoveryMethods = new HashMap<>();
	public Map<Class<? extends Wrapper>, Class> mappedClasses = new HashMap<>();
	private Map<Method, Method> mappedMethods = new HashMap<>();
	private Map<Method, Field> mappedFields = new HashMap<>();
	private boolean success;

	// prevent construction :/
	private Mapper() {
	}

	
	@DiscoveryMethod(checks = CUSTOM)
	public interface DisplayWrapper extends Wrapper {
		@DiscoveryMethod(checks = CUSTOM)
		@HookingMethod(SwapBuffersEvent.class)
		public void swapBuffers() throws LWJGLException;
	}

	
	private static Mapper createMapper() {
		final Mapper mapper = new Mapper();
		mapper.register(Start.class, m -> Class.forName(Start.DEFAULT_LOC));
		// obtain #swapBuffers method.
		mapper.register(DisplayWrapper.class, m -> Display.class);
		
		mapper.register(ASMUtils.getMethod(DisplayWrapper.class, "swapBuffers()V"), m -> ASMUtils.getMethod(Display.class, "swapBuffers()V"));
		mapper.register(Minecraft.class, m -> {
			final Class start = m.getMappedClass(Start.class);
			if (start != null)
				return Class.forName(((TypeInsnNode) ASMUtils.getLastInstruction(ASMUtils.getMethodNode(ASMUtils.getMethod(m.getMappedClass(Start.class), "main([Ljava/lang/String;)V")).instructions, Opcodes.NEW)).desc.replace("/", "."));
			throw new ClassNotFoundException("Could not find minecraft class.");
		});
		mapper.register(ASMUtils.getMethod(Minecraft.class, "getLeftClickDelay()I"), m -> {
			final Method method = m.getMappedMethod(ASMUtils.getMethod(Minecraft.class, "clickMouse()V"));
			if (method != null) {
				final Field field = m.getMappedClass(Minecraft.class).getDeclaredField(((FieldInsnNode) ASMUtils.getFirst(ASMUtils.getMethodNode(method).instructions, Opcodes.GETFIELD)).name);
				m.mappedFields.put(ASMUtils.getMethod(Minecraft.class, "setLeftClickDelay(I)V"), field);
				return field;
			}
			return null;
		});
		
		mapper.register(ASMUtils.getMethod(Minecraft.class, "getRightClickDelayTimer()I"), m -> {
			final Method method = m.getMappedMethod(ASMUtils.getMethod(Minecraft.class, "runTick()V"));
			if (method != null) {
				final Field field = m.getMappedClass(Minecraft.class).getDeclaredField(((FieldInsnNode) ASMUtils.getFirst(ASMUtils.getMethodNode(method).instructions, Opcodes.GETFIELD)).name);
				m.mappedFields.put(ASMUtils.getMethod(Minecraft.class, "setRightClickDelayTimer(I)V"), field);
				return field;
			}
			return null;
		});
		
		
		mapper.register(PlayerSp.class);
		mapper.register(ClientPlayer.class);
		mapper.register(EntityPlayer.class);
		mapper.register(PlayerAbilities.class);
		mapper.register(EntityLivingBase.class);
		mapper.register(Entity.class);
		mapper.register(AxisAlignedBB.class);
		mapper.register(Timer.class);
		mapper.register(EntityRenderer.class);
		mapper.register(WorldClient.class);
		mapper.register(World.class);
		
		mapper.register(GuiChest.class, m -> {
			return Class.forName("ayr");
		});
		
		mapper.register(MovingObjectPosition.class);
		
		mapper.register(RenderManager.class);
		mapper.register(GuiScreen.class);
		mapper.register(ASMUtils.getMethod(GuiScreen.class, "onUpdate()V"), m -> {
			final Method method = m.getMappedMethod(ASMUtils.getMethod(Minecraft.class, "tick()V"));
			if (method != null) {
				MethodNode methodNode = ASMUtils.getMethodNode(method);
				assert methodNode != null;
				int i = 0;
				for (; i < methodNode.instructions.size(); i++) {
					final AbstractInsnNode insnNode = methodNode.instructions.get(i);
					if (insnNode.getType() == AbstractInsnNode.LDC_INSN && "Ticking screen".equals(((LdcInsnNode) insnNode).cst.toString()))
						break;
				}
				final MethodInsnNode methodInsnNode = (MethodInsnNode) ASMUtils.getLastInstruction(methodNode.instructions, Opcodes.INVOKEVIRTUAL, methodNode.instructions.size() - i);
				return ASMUtils.getMethod(m.getMappedClass(GuiScreen.class), methodInsnNode.name + methodInsnNode.desc);
			}
			return null;
		});
		mapper.register(ASMUtils.getMethod(GuiScreen.class, "onClose()V"), m -> {
			final Method method = m.getMappedMethod(ASMUtils.getMethod(Minecraft.class, "displayGuiScreen(L" + Type.getInternalName(GuiScreen.class) + ";)V"));
			if (method != null) {
				final MethodInsnNode methodInsnNode = (MethodInsnNode) ASMUtils.getFirst(ASMUtils.getMethodNode(method).instructions, Opcodes.INVOKEVIRTUAL);
				return ASMUtils.getMethod(m.getMappedClass(GuiScreen.class), methodInsnNode.name + methodInsnNode.desc);
			}
			return null;
		});
	
		mapper.register(NetHandlerClient.class);
		mapper.register(INetHandlerClient.class, m -> m.getMappedClass(NetHandlerClient.class).getInterfaces()[0]);
		// mapper.register(S12Velocity.class);
		mapper.register(NetworkManager.class);
		mapper.register(Packet.class, m -> m.getMappedClass(INetHandlerClient.class).getDeclaredMethods()[0].getParameterTypes()[0].getInterfaces()[0]);
		mapper.register(GameSettings.class);
		mapper.register(GuiIngame.class);
		return mapper;
	}

	
	public static Mapper getInstance() {
		return instance != null ? instance : (instance = createMapper());
	}

	
	public void register(Class<? extends Wrapper> wrapper, DiscoveryHandler<? extends Class>... discoveryHandlers) {
		wrapperClasses.put(wrapper, Arrays.asList(discoveryHandlers));
	}

	
	public void unregister(Class<? extends Wrapper> wrapper) {
		wrapperClasses.remove(wrapper);
	}

	
	public void register(Method method, DiscoveryHandler... discoveryHandlers) {
		customDiscoveryMethods.put(method, Arrays.asList(discoveryHandlers));
	}

	
	public void unregister(Method method) {
		customDiscoveryMethods.remove(method);
	}

	
	public void generate() throws MappingException {
		final TimeHelper timer = new TimeHelper();
		success = false;
		LOGGER.log(Level.INFO, "Started generation of mappings.");
		final Set<Class<? extends Wrapper>> wrappers = new LinkedHashSet<>(wrapperClasses.keySet());
		outer: while (!wrappers.isEmpty()) {
			final Iterator<Class<? extends Wrapper>> wrapperIterator = wrappers.iterator();
			while (wrapperIterator.hasNext()) {
				final Class<? extends Wrapper> wrapper = wrapperIterator.next();
				if (!mappedClasses.containsKey(wrapper)) {
					final DiscoveryMethod discoveryMethod = wrapper.getDeclaredAnnotation(DiscoveryMethod.class);
					final int flags = discoveryMethod != null ? discoveryMethod.checks() : DEFAULT;

					// check if a invalid flag is attached to the class.
					if ((flags & FIELD) != 0 || (flags & STRUCTURE_START) != 0 || (flags & STRUCTURE_END) != 0 || (flags & CONSTRUCTOR) != 0 || (flags & OPCODES) != 0)
						LOGGER.log(Level.WARNING, "The wrappers class provided has a invalid flags attached to it: " + wrapper.getName() + ":" + Integer.toBinaryString(flags));

					final Class declaringClass;
					// check default flag.
					if ((flags & DEFAULT) != 0 &&
					// all default checks need extra information provided by
					// #DiscoveryMethod.
							discoveryMethod != null &&
							// check if the declaring class is valid.
							discoveryMethod.declaring() != Wrapper.class && (declaringClass = getMappedClass(discoveryMethod.declaring())) != null) {
						// perform default checks.
						if (Stream.of(wrapper.getDeclaredMethods())
								// check if there is a method with a constructor
								// flag present.
								.anyMatch(method -> {
									final DiscoveryMethod discovery = method.getDeclaredAnnotation(DiscoveryMethod.class);
									return discovery != null && (discovery.checks() & CONSTRUCTOR) != 0;
								}))
							inner: {
								// attempt to find class with the constructor.
								Class result = null;
								// get expected constructors parameters
								final List<List<Class>> parameters = Stream.of(wrapper.getDeclaredMethods()).filter(m -> {
									final DiscoveryMethod methodDiscoveryMethod = m.getDeclaredAnnotation(DiscoveryMethod.class);
									return methodDiscoveryMethod != null && (methodDiscoveryMethod.checks() & CONSTRUCTOR) != 0;
								}).map(method -> Stream.of(method.getParameterTypes()).map(this::convertToMappedClass).collect(Collectors.toList())).collect(Collectors.toList());

								// math constructors of the fields with the
								// provided ones.
								for (Class clazz : Stream.of(declaringClass.getDeclaredFields()).map(Field::getType).collect(Collectors.toList())) {
									// get current constructor parameters.
									final List<List<Class>> constructorsParameters = Stream.of(clazz.getConstructors()).map(constructor -> Arrays.asList(constructor.getParameterTypes())).collect(Collectors.toList());

									// check if all of the constructor
									// parameters match.
									if (parameters.stream().allMatch(constructorsParameters::contains)) {
										if ((flags & LAST_MATCH) == 0 && result != null)
											break inner;
										result = clazz;
										if ((flags & FIRST_MATCH) != 0)
											break;
									}
								}

								// check if a result was found.
								if (result != null) {
									mappedClasses.put(wrapper, result);
									continue outer;
								}
							}
						else if ((flags & STRING_CONST) != 0 && discoveryMethod.constants().length != 0)
							inner: {
								Class result = null;
								// go to declaring class and get all the fields.
								for (Class clazz : Stream.of(declaringClass.getDeclaredFields())
										// map the fields to their type.
										.map(Field::getType).collect(Collectors.toList()))
									if (ASMUtils.containsString(clazz, discoveryMethod.constants())) {
										if ((flags & LAST_MATCH) == 0 && result != null)
											break inner;
										result = clazz;
										if ((flags & FIRST_MATCH) != 0)
											break;
									}

								if (result != null) {
									mappedClasses.put(wrapper, result);
									continue outer;
								}
							}
						else if ((flags & EXTENSION) != 0 && declaringClass.getSuperclass() != null) {
							mappedClasses.put(wrapper, declaringClass.getSuperclass());
							continue outer;
						}
					}

					// check if the class has a custom flag attached to it.
					if ((flags & CUSTOM) != 0)
						for (DiscoveryHandler<? extends Class> discoveryHandler : wrapperClasses.get(wrapper))
							try {
								Class discovered = discoveryHandler.discover(this);
								if (discovered != null) {
									mappedClasses.put(wrapper, discovered);
									continue outer;
								}
							} catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
								e.printStackTrace();
							}
				} else // class was found attempt to map all the methods.
				{
					int offset = -1;
					final List<Method> methods = new LinkedList<>(Arrays.asList(ASMUtils.getDeclaredMethodsInOrder(wrapper)));
					final Iterator<Method> methodIterator = methods.iterator();
					while (methodIterator.hasNext()) {
						final Method method = methodIterator.next();
						offset++;

						final DiscoveryMethod discoveryMethod = method.getDeclaredAnnotation(DiscoveryMethod.class);
						final int flags = discoveryMethod != null ? discoveryMethod.checks() : DEFAULT;

						// check for invalid flags.
						if (discoveryMethod != null && discoveryMethod.declaring() != Wrapper.class)
							LOGGER.log(Level.WARNING, "The method provided has a invalid declaring tag attached to it: " + wrapper.getName() + "#" + method.getName());

						// if the method is a constructor remove it.
						if ((flags & CONSTRUCTOR) != 0 || getMappedMethod(method) != null || getMappedField(method) != null) {
							methodIterator.remove();
							continue;
						}

						// perform a default check.
						if ((flags & DEFAULT) != 0)
							if ((flags & FIELD) != 0)
								inner: {
									// attempt to find the field.
									List<Method> structure = null;
									// check if field is part of a structure
									// i.e. fields whose order is very unlikely
									// to change.
									if ((flags & STRUCTURE_START) != 0) {
										structure = new ArrayList<>();
										final Method wrapperMethods[] = ASMUtils.getDeclaredMethodsInOrder(wrapper);
										// find the remaining fields that are
										// part of the structure.
										for (int i = offset; i < wrapperMethods.length; i++) {
											final Method wrapperMethod = wrapperMethods[i];
											structure.add(wrapperMethod);
											final DiscoveryMethod discovery = wrapperMethod.getDeclaredAnnotation(DiscoveryMethod.class);
											if (discovery != null && (discovery.checks() & STRUCTURE_END) != 0)
												break;
										}
									}

									Field result = null;
									int resultOffset = 0;
									final Field fields[] = getMappedClass(wrapper).getDeclaredFields();
									loop: for (int i = 0; i < fields.length; i++) {
										final Field field = fields[i];
										// check field modifiers.
										if ((discoveryMethod == null || discoveryMethod.modifiers() == 0 || field.getModifiers() == discoveryMethod.modifiers()) &&
										// check field type.
												field.getType() == convertToMappedClass(method.getReturnType() != void.class ? method.getReturnType() : method.getParameterTypes().length == 1 ? method.getParameterTypes()[0] : null)) {
											// check if structure.
											if (structure != null) {
												if (i + structure.size() - 1 >= fields.length)
													break;

												// make sure the rest of the
												// fields fit into the structure
												// provided.
												for (int j = i; j < fields.length && j - i < structure.size(); j++) {
													DiscoveryMethod discovery;
													final Method structureMethod = structure.get(j - i);
													if (convertToMappedClass(structureMethod.getReturnType() != void.class ? structureMethod.getReturnType() : structureMethod.getParameterTypes().length == 1 ? structureMethod.getParameterTypes()[0] : null) != fields[j].getType() || ((discovery = structureMethod.getDeclaredAnnotation(DiscoveryMethod.class)) != null && discovery.modifiers() != 0 && discovery.modifiers() != fields[j].getModifiers()))
														continue loop;
												}
											}

											if ((flags & LAST_MATCH) == 0 && result != null)
												break inner;
											result = field;
											resultOffset = i;
											if ((flags & FIRST_MATCH) != 0)
												break;
										}
									}

									// check if the field/s where found.
									if (result != null) {
										// map fields.
										if (structure != null)
											for (int i = resultOffset; i < fields.length && i - resultOffset < structure.size(); i++)
												mappedFields.put(structure.get(i - resultOffset), fields[i]);
										else
											mappedFields.put(method, result);
										continue outer;
									}
								}
							else if ((flags & STRING_CONST) != 0) {
								// attempt to find method based on a string
								// constant.
								final Class obfClass = getMappedClass(wrapper);
								final ClassNode classNode = ASMUtils.getClassNode(obfClass);
								for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {
									final Iterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
									while (iterator.hasNext()) {
										final AbstractInsnNode insnNode = iterator.next();
										if (insnNode.getType() == AbstractInsnNode.LDC_INSN && Stream.of(discoveryMethod.constants()).anyMatch(s -> s.equals(((LdcInsnNode) insnNode).cst.toString()))) {
											mappedMethods.put(method, ASMUtils.getMethod(methodNode, obfClass));
											continue outer;
										}
									}
								}
							} else
								inner: {
									// attempt to find the method using its
									// return-type/mods/parameters.
									Method result = null;

									// get the methods parameters.
									final List<Class> parameters = Stream.of(method.getParameterTypes()).map(this::convertToMappedClass).collect(Collectors.toList());

									for (Method obfMethod : getMappedClass(wrapper).getDeclaredMethods())
										// check method modifiers.
										if ((discoveryMethod == null || discoveryMethod.modifiers() == 0 || obfMethod.getModifiers() == discoveryMethod.modifiers()) &&
										// check method return-types.
												(convertToMappedClass(method.getReturnType()) == obfMethod.getReturnType() &&
												// check method parameters.
														parameters.equals(Arrays.asList(obfMethod.getParameterTypes())))
												&&
												// check exceptions.
												(method.getExceptionTypes().length != 0 || Arrays.asList(method.getExceptionTypes()).equals(Arrays.asList(obfMethod.getExceptionTypes()))) &&
												// check opcodes.
												((flags & OPCODES) == 0 || (discoveryMethod.opcodes().length != 0 && ASMUtils.hasInstructionMatch(ASMUtils.getMethodNode(obfMethod).instructions, discoveryMethod.opcodes())))) {
											// check if method is unique.
											if ((flags & LAST_MATCH) == 0 && result != null)
												break inner;
											result = obfMethod;
											if ((flags & FIRST_MATCH) != 0)
												break;
										}

									if (result != null) {
										mappedMethods.put(method, result);
										continue outer;
									}
								}

						// perform a custom check.
						if ((flags & CUSTOM) != 0) {
							final List<DiscoveryHandler> discoveryHandlers = customDiscoveryMethods.get(method);
							if (discoveryHandlers != null)
								for (DiscoveryHandler discoveryHandler : discoveryHandlers)
									try {
										Object discovered = discoveryHandler.discover(this);
										if (discovered != null) {
											if (discovered instanceof Method)
												mappedMethods.put(method, (Method) discovered);
											else if (discovered instanceof Field)
												mappedFields.put(method, (Field) discovered);
											else
												LOGGER.log(Level.WARNING, "A discovery method returned a invalid type." + wrapper);
											continue outer;
										}
									} catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
										e.printStackTrace();
									}
						}
					}

					if (methods.size() == 0) {
						LOGGER.log(Level.INFO, "Discovered class " + wrapper + ":" + mappedClasses.get(wrapper) + ".");
						for (Method method : wrapper.getDeclaredMethods())
							LOGGER.log(Level.INFO, "  " + (mappedMethods.get(method) != null ? "Discovered method " + method.getName() + ":" + mappedMethods.get(method).getName() : "Discovered field " + method.getName() + ":" + mappedFields.get(method)));
						wrapperIterator.remove();
						continue outer;
					}
				}
			}
			throw new MappingException("Could not generate mappings for " + wrappers.toString() + " wrappers", wrappers);
		}
		LOGGER.log(Level.INFO, "Mappings were generated in: " + timer.getMSPassed() + "ms.");
		success = true;
	}

	
	public Class convertToMappedClass(Class clazz) {
		return Wrapper.class.isAssignableFrom(clazz) ? getMappedClass((Class<? extends Wrapper>) clazz) : clazz;
	}

	
	public Class getMappedClass(Class<? extends Wrapper> wrapper) {
		return mappedClasses.get(wrapper);
	}
	
	public Method getMappedMethod(Method method) {
		return mappedMethods.get(method);
	}

	
	public Field getMappedField(Method method) {
		return mappedFields.get(method);
	}

	
	public boolean isSuccess() {
		return success;
	}
}
