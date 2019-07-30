package talecraft.network.handlers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagList;
import talecraft.client.gui.items.GuiDecorator;
import talecraft.network.packets.DecoratorGuiPacket;

import java.util.ArrayList;
import java.util.List;

public class DecoratorGuiPacketHandler {
    public static void handle(DecoratorGuiPacket message) {
        List<String> list = new ArrayList<>();
        NBTTagList tl = message.tag.getTagList("decorations_list", 8);
        for (int i = 0; i < tl.tagCount(); i++) {
            list.add(tl.getStringTagAt(i));
        }
        Minecraft.getMinecraft().displayGuiScreen(new GuiDecorator(list, message.tag));
    }
}
