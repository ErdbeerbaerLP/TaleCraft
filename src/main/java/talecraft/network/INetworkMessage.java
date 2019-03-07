package talecraft.network;

import com.google.common.base.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import talecraft.TaleCraft;

public abstract class INetworkMessage<MSG> {
	private Object message;
	private INetworkMessage(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
	}



	public PacketBuffer encodeString(MSG a, PacketBuffer b) {
		// TODO Auto-generated method stub
		b.writeInt(0);
		TaleCraft.logger.info(a.getClass().toGenericString());
		b.writeString((String)message);
		return b;
	}
	public PacketBuffer encodeInt(MSG a, PacketBuffer b) {
		b.writeVarInt((int)message);
		TaleCraft.logger.info(a.getClass().toGenericString());
		return b;
	}


	public abstract Object onMessageReceived(MSG a, Supplier<Context> b);

}

