package talecraft.client.gui.replaced_guis;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import talecraft.TaleCraft;
import talecraft.client.gui.misc.GuiCopyingWorld;

public class NewGameOverScreen extends GuiGameOver {

	public NewGameOverScreen(ITextComponent causeOfDeathIn) {
		super(causeOfDeathIn);
		// TODO Auto-generated constructor stub

	}


	@Override
	public void confirmClicked(boolean result, int id) {
		if (result)
		{
			File savesDir = new File(this.mc.mcDataDir, "saves");
			if (this.mc.world != null)
			{
				this.mc.world.sendQuittingDisconnectingPacket();
			}


			this.mc.loadWorld((WorldClient)null);

			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						if(TaleCraft.lastVisitedWorld==null ||(TaleCraft.lastVisitedWorld != null && !TaleCraft.lastVisitedWorld.getSaveHandler().getWorldDirectory().getName().equals("TC_TEST")))
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
						NewGameOverScreen.this.mc.addScheduledTask(new Runnable() {

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
		else
		{
			this.mc.player.respawnPlayer();
			this.mc.displayGuiScreen((GuiScreen)null);
		}
	}
}
