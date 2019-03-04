
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDirtMessageScreen;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.world.World;
import net.minecraftforge.fml.StartupQuery;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import talecraft.TaleCraft;
import talecraft.client.gui.misc.GuiCopyingWorld;

public class NewIngameMenu extends GuiIngameMenu {
	GuiButtonExt testBtn;
	private World currentWorld;
	public NewIngameMenu() {
		currentWorld = TaleCraft.lastVisitedWorld;
	}
	//GuiIngameMenu
	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		super.initGui();
		GuiButtonExt guibutton = new GuiButtonExt(1, this.width / 2 - 100, this.height / 4 + 120 + -16, I18n.format("menu.returnToMenu")) {
			/**
			 * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
			 */
			public void onClick(double mouseX, double mouseY) {
				this.enabled = false;
				File savesDir = new File(NewIngameMenu.this.mc.gameDir, "saves");
				NewIngameMenu.this.mc.world.sendQuittingDisconnectingPacket();
				NewIngameMenu.this.mc.loadWorld((WorldClient)null);

				Thread t = new Thread() {
					@Override
					public void run() {
						try {
							if(currentWorld==null ||(currentWorld != null && !currentWorld.getSaveHandler().getWorldDirectory().getName().equals("TC_TEST")))
								mc.displayGuiScreen(new GuiCopyingWorld("Saving...."));
							else
								mc.displayGuiScreen(new GuiCopyingWorld("Deleting Test..."));
							sleep(TimeUnit.SECONDS.toMillis(1));
							final String[] savesList = savesDir.list();
							//Save worlds back to thier folders!
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
							NewIngameMenu.this.mc.addScheduledTask(new Runnable() {

								@Override
								public void run() {
									mc.displayGuiScreen(new CustomMainMenu());
								}
							});
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				t.start();
				
			}
		};
		buttons.set(0, guibutton);
		
		addButton(testBtn=new GuiButtonExt(15, width/2, height/2, "TEST") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				this.enabled = false;

				File savesDir2 = new File(NewIngameMenu.this.mc.gameDir, "saves");
				NewIngameMenu.this.mc.world.sendQuittingDisconnectingPacket();
				final WorldClient world = mc.world;
				NewIngameMenu.this.mc.loadWorld((WorldClient)null);

				Thread t2 = new Thread() {
					@Override
					public void run() {
						try {
							if(currentWorld != null && !currentWorld.getSaveHandler().getWorldDirectory().getName().equals("TC_TEST")) {
								mc.displayGuiScreen(new GuiCopyingWorld("Saving Map..."));
								sleep(TimeUnit.SECONDS.toMillis(1));
								//Save world back to thier folders!
								FileUtils.copyDirectory(new File(savesDir2,currentWorld.getSaveHandler().getWorldDirectory().getName()), new File(new File(mc.gameDir, "saves/.TC_MAPS"),currentWorld.getSaveHandler().getWorldDirectory().getName()));
								mc.displayGuiScreen(new GuiCopyingWorld("Creating Test-Save..."));
								sleep(TimeUnit.SECONDS.toMillis(1));
								final String[] savesList = savesDir2.list();
								for(String s : savesList){
									if(s.startsWith(".TC")) continue;
									if(s.equals("TC_TEST")) {
										FileUtils.deleteDirectory(new File(savesDir2, s));
										continue;
									}
									boolean type = !s.contains("@SAV");
									FileUtils.copyDirectoryToDirectory(new File(savesDir2, s), new File(savesDir2, type?".TC_MAPS":".TC_SAVES"));
									FileUtils.deleteDirectory(new File(savesDir2, s));
								}
								FileUtils.copyDirectory(new File(new File(mc.gameDir, "saves/.TC_MAPS"),currentWorld.getSaveHandler().getWorldDirectory().getName()), new File(savesDir2,"TC_TEST"));

								final String s = "TC_TEST";
								FileInputStream inStream = new FileInputStream(new File(new File(savesDir2,s), "level.dat"));
								NBTTagCompound worldTag = CompressedStreamTools.readCompressed(inStream);

								NBTTagCompound worldDataTag = worldTag.getCompound("Data");
								worldDataTag.setInt("GameType", 2);
								worldDataTag.setBoolean("allowCommands", false);
								worldDataTag.setBoolean("DifficultyLocked", true);
								NBTTagCompound playerDataTag = worldDataTag.getCompound("Player");
								playerDataTag.setInt("playerGameType", 2);
								//No longer moving to spawn
								worldDataTag.setTag("Player", playerDataTag);
								worldTag.setTag("Data", worldDataTag);
								inStream.close();
								FileOutputStream outStream = new FileOutputStream(new File(new File(savesDir2,s), "level.dat"));
								CompressedStreamTools.writeCompressed(worldTag, outStream);
								outStream.close();
								FileOutputStream outStream2 = new FileOutputStream(new File(new File(savesDir2,s), "test.dat"));
								NBTTagCompound cin = new NBTTagCompound();
								cin.setString("OrigLevelName", worldDataTag.getString("LevelName"));
								cin.setString("OrigLevelFolder", currentWorld.getSaveHandler().getWorldDirectory().getName());
								CompressedStreamTools.writeCompressed(cin, outStream2);
								outStream2.close();

								mc.addScheduledTask(new Runnable() {

									@Override
									public void run() {
										tryLoadExistingWorld(new File(savesDir2,s), s, "TC_TEST");
									}
								});
							}else{
								mc.displayGuiScreen(new GuiCopyingWorld("Deleting Test Save..."));
								System.out.println(new File(new File(savesDir2,"TC_TEST"), "test.dat").exists());
								FileInputStream testinStream = new FileInputStream(new File(new File(savesDir2,"TC_TEST"), "test.dat"));
								NBTTagCompound c = CompressedStreamTools.readCompressed(testinStream);
								System.out.println(c);
								String worldFolder = c.getString("OrigLevelFolder");
								String worldName = c.getString("OrigLevelName");
								System.out.println(worldFolder);	
								System.out.println(worldName);
								testinStream.close();
								sleep(TimeUnit.SECONDS.toMillis(1));
								final String[] savesList = savesDir2.list();
								for(String s : savesList){
									if(s.startsWith(".TC")) continue;
									if(s.equals("TC_TEST")) {
										FileUtils.deleteDirectory(new File(savesDir2, s));
										continue;
									}
									boolean type = !s.contains("@SAV");
									FileUtils.copyDirectoryToDirectory(new File(savesDir2, s), new File(savesDir2, type?".TC_MAPS":".TC_SAVES"));
									FileUtils.deleteDirectory(new File(savesDir2, s));
								}
								mc.displayGuiScreen(new GuiCopyingWorld("Re-Loading Map..."));
								sleep(TimeUnit.SECONDS.toMillis(1));
								FileUtils.copyDirectory(new File(new File(mc.gameDir, "saves/.TC_MAPS"),worldFolder), new File(savesDir2,worldFolder));

								mc.addScheduledTask(new Runnable() {

									@Override
									public void run() { 
										System.out.println(worldFolder);
										System.out.println(worldName);
										System.out.println(new File(savesDir2,worldFolder).getAbsolutePath());
										tryLoadExistingWorld(new File(savesDir2,worldFolder), worldFolder, worldName);
									}
								});
							}

						} catch (InterruptedException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				t2.start();
			}
		});
		if(TaleCraft.isBuildMode() || (currentWorld != null && currentWorld.getSaveHandler().getWorldDirectory().getName().equals("TC_TEST")))this.buttons.get(4).width = this.buttons.get(4).width -60;
		testBtn.visible = false;
	}
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub

		if(currentWorld == null) currentWorld = TaleCraft.lastVisitedWorld;
		this.buttons.get(4).enabled = false;
		this.buttons.get(4).displayString = I18n.format("menu.shareToLan")+ " (Disabled)";
		if(TaleCraft.isBuildMode()) {
			testBtn.x = this.buttons.get(4).x + 140;
			testBtn.y = this.buttons.get(4).y;
			testBtn.width = 60;
			testBtn.visible = true;
		}
		if(currentWorld != null && currentWorld.getSaveHandler().getWorldDirectory().getName().equals("TC_TEST")) {
			testBtn.x = this.buttons.get(4).x + 140;
			testBtn.y = this.buttons.get(4).y;
			testBtn.width = 60;
			testBtn.visible = true;
			testBtn.displayString = "Edit";
		}
		super.render(mouseX, mouseY, partialTicks);
	}
	public void tryLoadExistingWorld(File dir, String fileName, String saveName)
	{
		
			try
			{
				mc.launchIntegratedServer(fileName, saveName, null);
			}
			catch (StartupQuery.AbortedException e)
			{
				// ignore
			}
		
	}
}