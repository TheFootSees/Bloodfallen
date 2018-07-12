package net.albedo.bloodfallen.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class ASMUtils {
	private static final Map<String, ClassNode> CLASS_NODE_CACHE = new HashMap<>();

	// prevent construction :/
	private ASMUtils() {
	}

	
	public static ClassNode getClassNode(Class clazz) {
		try {
			return getClassNode(Type.getInternalName(clazz) + ".class");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static ClassNode getClassNode(String internalName) throws IOException {
		if (CLASS_NODE_CACHE.containsKey(internalName))
			return CLASS_NODE_CACHE.get(internalName);
		final InputStream inputStream = ASMUtils.class.getResourceAsStream("/" + internalName);
		assert inputStream != null;
		final ClassNode classNode = getClassNode(IOUtils.readFully(inputStream, -1, true));
		CLASS_NODE_CACHE.put(internalName, classNode);
		return classNode;
	}

	
	public static ClassNode getClassNode(byte[] bytes) {
		final ClassNode classNode = new ClassNode();
		new ClassReader(bytes).accept(classNode, ClassReader.SKIP_FRAMES);
		return classNode;
	}

	
	public static byte[] getBytes(ClassNode classNode) {
		final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(classWriter);
		return classWriter.toByteArray();
	}

	
	public static AbstractInsnNode getLastInstruction(InsnList insnList, int opcode) {
		return getLastInstruction(insnList, opcode, 0);
	}

	
	public static AbstractInsnNode getLastInstruction(InsnList insnList, int opcode, int offset) {
		for (int i = insnList.size() - 1 - offset; i > 0; i--) {
			final AbstractInsnNode abstractInsnNode = insnList.get(i);
			if (abstractInsnNode.getOpcode() == opcode)
				return abstractInsnNode;
		}
		return null;
	}

	
	public static MethodNode getMethodNode(Method method)
    {
        for(MethodNode methodNode : (List<MethodNode>) ASMUtils.getClassNode(method.getDeclaringClass()).methods)
            if(methodNode.name.equals(method.getName()) && methodNode.desc.equals(Type.getMethodDescriptor(method)))
                return methodNode;
        return null;
    }

	public static MethodNode getMethodNode(Constructor constructor)
    {
        for(MethodNode methodNode : (List<MethodNode>) ASMUtils.getClassNode(constructor.getDeclaringClass()).methods)
            if(methodNode.name.equals("<init>") && methodNode.desc.equals(Type.getConstructorDescriptor(constructor)))
                return methodNode;
        return null;
    }
	
	
	public static Method getMethod(Class clazz, String desc) {
		for (Method method : clazz.getDeclaredMethods())
			if ((method.getName() + Type.getMethodDescriptor(method)).equals(desc))
				return method;
		return null;
	}

	
	public static boolean containsString(Class clazz, String constants[]) {
		if (clazz.isPrimitive() || clazz.isArray())
			return false;

		return ((List<MethodNode>) getClassNode(clazz).methods).stream().anyMatch(methodNode -> {
			final Iterator iterator = methodNode.instructions.iterator();
			while (iterator.hasNext()) {
				final AbstractInsnNode insnNode = (AbstractInsnNode) iterator.next();
				if (insnNode.getType() == AbstractInsnNode.LDC_INSN && Stream.of(constants).anyMatch(s -> s.equals(((LdcInsnNode) insnNode).cst.toString())))
					return true;
			}
			return false;
		});
	}

	
	public static Method[] getDeclaredMethodsInOrder(Class clazz) {
		final Method methods[] = clazz.getDeclaredMethods();
		final ClassNode classNode = getClassNode(clazz);
		final Method reordered[] = new Method[methods.length];
		for (int i = 0; i < classNode.methods.size(); i++) {
			final MethodNode methodNode = (MethodNode) classNode.methods.get(i);
			for (Method method : methods)
				if ((method.getName() + Type.getMethodDescriptor(method)).equals(methodNode.name + methodNode.desc)) {
					reordered[i] = method;
					break;
				}
		}
		return reordered;
	}

	
	public static Method getMethod(MethodNode methodNode, Class clazz) {
		for (Method method : clazz.getDeclaredMethods())
			if ((method.getName() + Type.getMethodDescriptor(method)).equals(methodNode.name + methodNode.desc))
				return method;
		return null;
	}

	
	public static AbstractInsnNode getFirst(InsnList insnList, int opcode) {
		final Iterator<AbstractInsnNode> iterator = insnList.iterator();
		while (iterator.hasNext()) {
			final AbstractInsnNode insnNode = iterator.next();
			if (insnNode.getOpcode() == opcode)
				return insnNode;
		}
		return null;
	}

	
	public static AbstractInsnNode getLast(InsnList insnList, int opcode) {
		for (int i = insnList.size() - 1; i > 0; i--) {
			final AbstractInsnNode abstractInsnNode = insnList.get(i);
			if (abstractInsnNode.getOpcode() == opcode)
				return abstractInsnNode;
		}
		return null;
	}

	
	public static void insert(InsnList insnList, int opcodes[], InsnList injection, boolean before) {
		final AbstractInsnNode instructions[] = insnList.toArray();
		for (int i = 0; i < instructions.length; i++) {
			final AbstractInsnNode abstractInsnNode = instructions[i];
			if (checkInstructionMatch(insnList, opcodes, i)) {
				if (before)
					insnList.insertBefore(abstractInsnNode, injection);
				else
					insnList.insert(instructions[i + opcodes.length - 1], injection);
				break;
			}
		}
	}

	
	public static boolean checkInstructionMatch(InsnList insnList, int opcodes[], int offset) {
		final AbstractInsnNode instructions[] = insnList.toArray();
		int i = offset;
		for (; i < instructions.length && i - offset < opcodes.length; i++)
			if (instructions[i].getOpcode() != opcodes[i - offset])
				return false;
		return i - offset == opcodes.length;
	}

	
	public static boolean hasInstructionMatch(InsnList insnList, int opcodes[]) {
		final AbstractInsnNode instructions[] = insnList.toArray();
		for (int i = 0; i < instructions.length; i++)
			if (checkInstructionMatch(insnList, opcodes, i))
				return true;
		return false;
	}

	
	public static String formatInstructions(InsnList insnList) {
		final Printer printer = new Textifier();
		final TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(printer);
		final StringBuilder stringBuilder = new StringBuilder();
		final Iterator<AbstractInsnNode> insnNodeIterator = insnList.iterator();
		while (insnNodeIterator.hasNext()) {
			final AbstractInsnNode insnNode = insnNodeIterator.next();
			insnNode.accept(traceMethodVisitor);
			final StringWriter stringWriter = new StringWriter();
			printer.print(new PrintWriter(stringWriter));
			printer.getText().clear();
			stringBuilder.append(stringWriter.toString());
		}
		return stringBuilder.toString();
	}
}
