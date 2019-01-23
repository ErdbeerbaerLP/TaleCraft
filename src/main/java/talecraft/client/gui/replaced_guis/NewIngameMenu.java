package talecraft.client.gui.replaced_guis;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.fml.client.GuiOldSaveLoadConfirm;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.StartupQuery;
import talecraft.TaleCraft;
import talecraft.client.gui.misc.GuiCopyingWorld;

public class NewIngameMenu extends GuiIngameMenu {
	GuiButtonExt testBtn;
	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		super.initGui();
		addButton(testBtn=new GuiButtonExt(15, width/2, height/2, "TEST"));
		if(TaleCraft.asClient().isBuildMode())this.buttonList.get(4).width = this.buttonList.get(4).width -60;
		testBtn.visible = false;
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.buttonList.get(4).enabled = false;
		this.buttonList.get(4).displayString = I18n.format("menu.shareToLan")+ " (Disabled)";
		if(TaleCraft.asClient().isBuildMode()) {
		testBtn.x = this.buttonList.get(4).x + 140;
		testBtn.y = this.buttonList.get(4).y;
		testBtn.width = 60;
		testBtn.visible = true;
		}
		
	}
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		// TODO Auto-generated method stub
		
		switch (button.id)
        {
		case 1:
            button.enabled = false;
            File savesDir = new File(this.mc.mcDataDir, "saves");
            this.mc.world.sendQuittingDisconnectingPacket();
            this.mc.loadWorld((WorldClient)null);
            this.mc.displayGuiScreen(new GuiCopyingWorld());
            final String[] savesList = savesDir.list();
            //Save world back to thier folders!
            for(String s : savesList){
            	if(s.startsWith(".TC")) continue;
            	if(s.equals("TC_TEST")) {
            		FileUtils.deleteDirectory(new File(savesDir, s));
            		continue;
            	}
            	boolean type = !s.contains("@SAV");
            	FileUtils.copyDirectoryToDirectory(new File(savesDir, s), new File(savesDir, type?".TC_MAPS":".TC_SAVES"));
            	FileUtils.deleteDirectory(new File(savesDir, s));
            	
            }
            this.mc.displayGuiScreen(new CustomMainMenu());
            
			return;
		case 15:
            button.enabled = false;
            File savesDir2 = new File(this.mc.mcDataDir, "saves");
            this.mc.world.sendQuittingDisconnectingPacket();
            final WorldClient world = mc.world;
            this.mc.loadWorld((WorldClient)null);
            this.mc.displayGuiScreen(new GuiCopyingWorld());
            final String[] savesList2 = savesDir2.list();
            //Save world back to thier folders!
            for(String s : savesList2){
            	if(s.startsWith(".TC")) continue;
            	boolean type = !s.contains("@SAV");
            	FileUtils.copyDirectoryToDirectory(new File(savesDir2, s), new File(savesDir2, type?".TC_MAPS":".TC_SAVES"));
            	FileUtils.deleteDirectory(new File(savesDir2, s));
            	FileUtils.copyDirectory(new File(new File(this.mc.mcDataDir, "saves/.TC_MAPS" ),s), new File(savesDir2,"TC_TEST"));
            	s = "TC_TEST";
            	FileInputStream inStream = new FileInputStream(new File(new File(savesDir2,s), "level.dat"));
				NBTTagCompound worldTag = CompressedStreamTools.readCompressed(inStream);
				
				NBTTagCompound worldDataTag = worldTag.getCompoundTag("Data");
				worldDataTag.setInteger("GameType", 2);
				worldDataTag.setBoolean("allowCommands", false);
				worldDataTag.setBoolean("DifficultyLocked", true);
				NBTTagCompound playerDataTag = worldDataTag.getCompoundTag("Player");
				playerDataTag.setInteger("playerGameType", 2);
				NBTTagList posList = new NBTTagList();
				posList.appendTag(new NBTTagDouble(worldDataTag.getInteger("SpawnX")));
				posList.appendTag(new NBTTagDouble(worldDataTag.getInteger("SpawnY")));
				posList.appendTag(new NBTTagDouble(worldDataTag.getInteger("SpawnZ")));
				playerDataTag.setTag("Pos", posList);
				worldDataTag.setTag("Player", playerDataTag);
				worldTag.setTag("Data", worldDataTag);
				inStream.close();
				FileOutputStream outStream = new FileOutputStream(new File(new File(savesDir2,s), "level.dat"));
				CompressedStreamTools.writeCompressed(worldTag, outStream);
				outStream.close();
            	tryLoadExistingWorld(new File(savesDir2,s), s);
            }
			break;
        }
		super.actionPerformed(button);
	}
	public void tryLoadExistingWorld(File dir, String fileName)
    {
        NBTTagCompound leveldat;
        try
        {
            leveldat = CompressedStreamTools.readCompressed(new FileInputStream(new File(dir, "level.dat")));
        }
        catch (Exception e)
        {
            try
            {
                leveldat = CompressedStreamTools.readCompressed(new FileInputStream(new File(dir, "level.dat_old")));
            }
            catch (Exception e1)
            {
                FMLLog.log.warn("There appears to be a problem loading the save {}, both level files are unreadable.", fileName);
                return;
            }
        }
        NBTTagCompound fmlData = leveldat.getCompoundTag("FML");
        if (fmlData.hasKey("ModItemData"))
        {
            mc.displayGuiScreen(new GuiOldSaveLoadConfirm(fileName, "TC_TEST", null));
        }
        else
        {
            try
            {
                mc.launchIntegratedServer(fileName, "TC_TEST", null);
            }
            catch (StartupQuery.AbortedException e)
            {
                // ignore
            }
        }
    }
}
