package talecraft.network.packets;


import java.util.function.Supplier;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import talecraft.server.ServerHandler;

public class StringNBTCommandPacket{
	public NBTTagCompound data;
	public String command;

	public StringNBTCommandPacket() {
		data = new NBTTagCompound();
		command = "";
	}

	public StringNBTCommandPacket(String cmdIN, NBTTagCompound dataIN) {
		this.data = dataIN != null ? dataIN : new NBTTagCompound();
		this.command = cmdIN;
	}

	public StringNBTCommandPacket(String cmd) {
		data = new NBTTagCompound();
		command = cmd;
	}
	public PacketBuffer encode(StringNBTCommandPacket packet, PacketBuffer buf) {
		buf.writeInt(command.length());
		buf.writeString(command);
//		buf.writeBoolean(data == null);
		buf.writeCompoundTag(data);
		return buf;
	}
	public Object onMessageReceived(StringNBTCommandPacket a, Supplier<Context> b) {
		System.out.println("DIST: "+b.get().getDirection().name());
		ServerHandler.handleSNBTCommand(b.get().getSender().connection, a);
		return null;
	}
}
