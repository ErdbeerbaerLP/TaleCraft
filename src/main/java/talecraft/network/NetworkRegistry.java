package talecraft.network;
import static talecraft.TaleCraft.network;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.google.common.base.Function;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import talecraft.TaleCraft;
import talecraft.network.packets.PlayerNBTDataMergePacket;
import talecraft.network.packets.StringNBTCommandPacket;
import talecraft.network.packets.StringNBTCommandPacketClient;

public class NetworkRegistry {
	private static int discriminator = 0;
	public static void init() {
		TaleCraft.logger.info("Registering network Packets");
//		register();
		NetworkRegistry.<StringNBTCommandPacket>register(StringNBTCommandPacket.class, (a,b) -> a.encode(a, b), (a) ->{return StringNBTCommandPacket.decode(a);}, (a,b) -> {a.onMessageReceived(a, b);});
		NetworkRegistry.<PlayerNBTDataMergePacket>register(PlayerNBTDataMergePacket.class, (a,b) -> a.encode(a,b), (a)->{return PlayerNBTDataMergePacket.decode(a);}, (a,b)-> {a.onMessage(a, b);});
		NetworkRegistry.<StringNBTCommandPacketClient>register(StringNBTCommandPacketClient.class, (a,b) -> a.encode(a,b), (a)->{return StringNBTCommandPacketClient.decode(a);}, (a,b)-> {a.onMessage(a, b);});
		
	}
	private static <MSG> void register(Class<MSG> handler, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<Context>> messageConsumer){
		network.<MSG>registerMessage(discriminator, handler, encoder, decoder, messageConsumer);
		discriminator++;
	}
}
