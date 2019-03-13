package talecraft.invoke;

import net.minecraft.nbt.NBTTagCompound;
import talecraft.TaleCraft;

public interface IInvoke {

	public String getType();
	public void getColor(float[] color_out);

	public void write(NBTTagCompound compound);
	public void read(NBTTagCompound compound);

	public static class Serializer {

		public static final NBTTagCompound write(IInvoke invoke) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("type", invoke.getType());
			invoke.write(compound);
			return compound;
		}

//		public static final IScriptInvoke readSI(NBTTagCompound compoundTag) {
//			IInvoke invoke = read(compoundTag);
//
//			if(invoke instanceof IScriptInvoke) {
//				// TaleCraft.logger.info("Loaded IScriptInvoke : " + invoke.getType() + " : " + ((IScriptInvoke)invoke).getScriptName());
//				return (IScriptInvoke) invoke;
//			}
//
//			TaleCraft.logger.severe("Invalid Invoke Object! Required is IScriptInvoke, given is " + invoke.getType() + "!");
//			return new EmbeddedScriptInvoke("");
//		}

		public static final IInvoke read(NBTTagCompound compoundTag) {
			String type = compoundTag.getString("type");

			if(type == null || type.isEmpty())
				return NullInvoke.instance;

			if("NullInvoke".equals(type)) {
				return NullInvoke.instance;
			}

			if("BlockTriggerInvoke".equals(type)) {
				BlockTriggerInvoke invoke = new BlockTriggerInvoke();
				invoke.read(compoundTag);
				return invoke;
			}

//			if("FileScriptInvoke".equals(type)) {
//				FileScriptInvoke invoke = new FileScriptInvoke();
//				invoke.readFromNBT(compoundTag);
//				return invoke;
//			}

//			if("EmbeddedScriptInvoke".equals(type)) {
//				EmbeddedScriptInvoke invoke = new EmbeddedScriptInvoke();
//				invoke.readFromNBT(compoundTag);
//				return invoke;
//			}

			if("CommandInvoke".equals(type)) {
				CommandInvoke invoke = new CommandInvoke();
				invoke.read(compoundTag);
				return invoke;
			}

			TaleCraft.logger.severe("(FATAL) Corrupted or unknown Invoke Data found: " + compoundTag + ".");
			return NullInvoke.instance;
		}

	}

}
