package talecraft.network;
import static talecraft.TaleCraft.network;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.google.common.base.Function;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class NetworkRegistry {
	private static int discriminator = 0;
	public static void init() {
//		register();
		NetworkRegistry.<StringNBTCommandPacket>register(StringNBTCommandPacket.class, (a,b) -> a.encode(a, b), (a) ->{int cmdLength = a.readInt(); String c = a.readString(cmdLength); return new StringNBTCommandPacket(c, a.readCompoundTag());}, (a,b) -> {a.onMessageReceived(a, b);});
	}
	private static <MSG> void register(Class<MSG> handler, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<Context>> messageConsumer){
		network.<MSG>registerMessage(discriminator, handler, encoder, decoder, messageConsumer);
		discriminator++;
	}
}
