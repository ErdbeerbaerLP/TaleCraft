package talecraft.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PlayerHelper {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isOp(EntityPlayerMP player) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        if (server.isSinglePlayer()) {
            return true;
        }

        String name = player.getName();
        String[] listOps = server.getPlayerList().getOppedPlayerNames();

        for (String string : listOps) {
            if (string.equals(name))
                return true;
        }

        return false;
    }

}
