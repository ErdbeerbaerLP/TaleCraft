package talecraft.network.packets;

import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import talecraft.TaleCraft;

public class StringNBTCommandPacketClient {

	public NBTTagCompound data;
	public String command;

	public StringNBTCommandPacketClient() {
		data = new NBTTagCompound();
		command = "";
	}

	public StringNBTCommandPacketClient(String cmdIN, NBTTagCompound dataIN) {
		this.data = dataIN != null ? dataIN : new NBTTagCompound();
		this.command = cmdIN;
	}

	public StringNBTCommandPacketClient(String cmd) {
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
	public static StringNBTCommandPacketClient decode(PacketBuffer a) {
		int cmdLength = a.readInt();
		String c = a.readString(cmdLength);
		NBTTagCompound t = a.readCompoundTag();
		return new StringNBTCommandPacketClient(c, t);
	}
	
		public Object onMessage(StringNBTCommandPacketClient message, Supplier<Context> b) {
			TaleCraft.asClient().getNetworkHandler().handleClientCommand(message.command, message.data);
			return null;
		}
}
