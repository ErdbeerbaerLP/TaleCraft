package talecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;

public class PlayerHelper {

	public static final boolean isOp(EntityPlayerMP player) {
		MinecraftServer server =Minecraft.getInstance().world.getServer();

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
