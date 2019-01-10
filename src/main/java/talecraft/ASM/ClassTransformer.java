package talecraft.ASM;
import static org.objectweb.asm.Opcodes.*;

import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer{
	/**
	 * Classes which will be transformed
	 */
	private static final String[] classesBeingTransformed =
		{
				"net.minecraft.client.Minecraft",
				"net.minecraftforge.fml.client.SplashProgress"
		};


	@Override
	public byte[] transform(String name, String transformedName, byte[] classBeingTransformed)
	{

		boolean isObfuscated = !name.equals(transformedName);
		int index = Arrays.asList(classesBeingTransformed).indexOf(transformedName);
		return index != -1 ? transform(index, classBeingTransformed, isObfuscated) : classBeingTransformed;
	}

	private static byte[] transform(int index, byte[] classBeingTransformed, boolean isObfuscated)
	{
		try
		{
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(classBeingTransformed);
			classReader.accept(classNode, 0);
			switch(index)
			{
			case 0:
				transformMinecraft(classNode, isObfuscated);
				break;
			case 1:
				transformSplashProgress(classNode, isObfuscated);
				break;

			}

			ModClassWriter classWriter = new ModClassWriter(ModClassWriter.COMPUTE_MAXS | ModClassWriter.COMPUTE_FRAMES);
			classNode.accept(classWriter);
			return classWriter.toByteArray();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return classBeingTransformed;
	}

	//In case the forge splash screen was turned off
	private static void transformMinecraft(ClassNode classNode, boolean isObfuscated) {
		for(MethodNode method : classNode.methods) {
			if(method.name.equals("<clinit>")) {
				System.out.println("clinit");
				for(AbstractInsnNode instruction : method.instructions.toArray()) {
					//replace mojang logo
					if(instruction instanceof LdcInsnNode) {
						System.out.println(((LdcInsnNode)instruction).cst);
						if(((LdcInsnNode)instruction).cst.equals("textures/gui/title/mojang.png")) {
							System.out.println("Replacing...");
							LdcInsnNode ldc = new LdcInsnNode("talecraft:textures/talecraft_loading.png");
							method.instructions.insert(instruction,ldc);
							method.instructions.remove(instruction);
							System.out.println("done");
						}
					}
				}
			}
		}

	}

	private static void transformSplashProgress(ClassNode classNode, boolean isObfuscated) {


		for(MethodNode method : classNode.methods) {
			if(method.name.equals("start")) {
				System.out.println("start");
				for(AbstractInsnNode instruction : method.instructions.toArray()) {

					//replace mojang logo
					if(instruction instanceof LdcInsnNode) {
						System.out.println(((LdcInsnNode)instruction).cst);
						if(((LdcInsnNode)instruction).cst.equals("textures/gui/title/mojang.png")) {
							System.out.println("Replacing...");
							LdcInsnNode ldc = new LdcInsnNode("talecraft:textures/talecraft_loading.png");
							method.instructions.insert(instruction,ldc);
							method.instructions.remove(instruction);
							System.out.println("done");
						}

						//Permanently remove memory bar from loading screen (because it is above the talecraft logo)
						if(((LdcInsnNode)instruction).cst.equals("showMemory")){
							method.instructions.remove(instruction.getNext().getNext());
							method.instructions.remove(instruction.getNext());
							method.instructions.insert(instruction,new InsnNode(ICONST_0));
							method.instructions.remove(instruction);
						}
					}
				}
			}
		}

	}
}
