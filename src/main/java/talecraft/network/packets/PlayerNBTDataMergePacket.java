package talecraft.network.packets;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PlayerNBTDataMergePacket {

	public NBTTagCompound data;

	public PlayerNBTDataMergePacket() {
		data = new NBTTagCompound();
	}

	public PlayerNBTDataMergePacket(NBTTagCompound in) {
		data = in;
	}

	public PacketBuffer encode(PlayerNBTDataMergePacket packet, PacketBuffer buf) {
		buf.writeCompoundTag(data);
		return buf;
	}

	public static PlayerNBTDataMergePacket decode(PacketBuffer buf) {
		return new PlayerNBTDataMergePacket(buf.readCompoundTag());
	}

	public Object onMessage(PlayerNBTDataMergePacket a, Supplier<Context> b) {
		final PlayerNBTDataMergePacket mpakDataMerge = a;
		DistExecutor.runWhenOn(Dist.CLIENT, ()->()->{
			Minecraft micr = Minecraft.getInstance();
				if(micr.player != null) {
					micr.player.getEntityData().merge((mpakDataMerge.data));
			}
		});
		return null;
	}
}
