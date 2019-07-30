package talecraft.client.gui.misc;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import talecraft.client.gui.qad.*;
import talecraft.util.WorldFileDataHelper;

/**
 * This GUI allows you to edit the world´s description and author
 */
public class GuiMapControl extends QADGuiScreen {
    QADButton btnSave;
    QADTextField authorNameField;
    QADColorTextField descField;
    QADLabel authorLabel;
    String author = "";
    MinecraftServer mp;
    NBTTagCompound tag;

    @Override
    public void buildGui() {
        mp = FMLCommonHandler.instance().getMinecraftServerInstance();
        tag = WorldFileDataHelper.getTagFromFile(mp.getEntityWorld(), "info");
        {

            QADPanel panel = new QADPanel();
            panel.setPosition(0, 0);
            panel.setSize(9999, 32);
            panel.setBackgroundColor(0);
            addComponent(panel);

            addComponent(new QADLabel("World: " + mp.getEntityWorld().getWorldInfo().getWorldName(), 100, 2));
            addComponent(new QADLabel("Player: " + mc.player.getDisplayNameString(), 100, 12));
            addComponent(authorLabel = new QADLabel("Map Author: " + (tag.hasKey("author") ? tag.getString("author") : "unknown"), 100, 22));
            // Header
            addComponent(QADFACTORY.createLabel("Map Editor", 2, 2)).setFontHeight(fontRenderer.FONT_HEIGHT * 2);
            // addComponent(QADFACTORY.createLabel("", 2, 2+10));

        }

        author = tag.hasKey("author") ? tag.getString("author") : "";
        String desc = tag.hasKey("description") ? tag.getString("description") : "";
        addComponent(descField = new QADColorTextField(100, 100, 100, 30, desc));
        addComponent(QADFACTORY.createLabel("World description", descField.xPosition, descField.yPosition - 20));
        addComponent(btnSave = new QADButton(width / 2, height - 100, 60, "SAVE"));
        addComponent(authorNameField = new QADTextField(100, 60, 60, 10, author));
        addComponent(QADFACTORY.createLabel("World Author", authorNameField.xPosition, authorNameField.yPosition - 10));
        btnSave.setAction(new Runnable() {
            @Override
            public void run() {
                NBTTagCompound tag = WorldFileDataHelper.getTagFromFile(mp.getEntityWorld(), "info");
                tag.setString("author", authorNameField.getText());
                tag.setString("description", descField.getText());
                WorldFileDataHelper.saveNBTToWorld(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld(), "info", tag);
                mc.displayGuiScreen(null);
            }
        });
        authorNameField.setMaxStringLength(30);
        descField.setMaxStringLength(800);
    }

    @Override
    public void updateGui() {
        // TODO Auto-generated method stub
        super.updateGui();
        btnSave.setX(width / 2);
        btnSave.setY(height - 30);
//		if(descField.getText().length() > 6) {
//			descField.setWidth(100+(descField.getText().length()-6)*5);
//		}else {
//			descField.setWidth(100);
//		}
    }
}
