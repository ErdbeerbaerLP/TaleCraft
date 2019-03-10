package talecraft.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class PlayerHelper {

	
	public static final boolean isOp(EntityPlayerMP player) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		
		if(server.isSinglePlayer()) {
			return true;
		}

		String name = player.getName().getUnformattedComponentText();
		String[] listOps = server.getPlayerList().getOppedPlayerNames();

		for(String string : listOps) {
			if(string.equals(name))
				return true;
		}

		return false;
	}

}
