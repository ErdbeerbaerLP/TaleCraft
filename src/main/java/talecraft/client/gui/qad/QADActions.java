package talecraft.client.gui.qad;

import net.minecraft.client.Minecraft;

public class QADActions {

    public static Runnable newBackToGameAction() {
        return () -> Minecraft.getMinecraft().displayGuiScreen(null);
    }

}
