package talecraft.client.gui.misc;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiConfirmOpenLink;
import talecraft.client.gui.qad.QADButton;
import talecraft.client.gui.qad.QADGuiScreen;
import talecraft.client.gui.qad.QADLabel;
import talecraft.client.gui.replaced_guis.CustomMainMenu;

public class GuiCertError extends QADGuiScreen {
	final QADButton btnNo = new QADButton("No");
	final QADButton btnYes = new QADButton("Yes");
	final QADLabel l = new QADLabel("You need \"Lets EncryptCraft\" to use this feature.");
	final QADLabel l2 = new QADLabel("Do you want to close the game, open the mods folder and the website of the mod?");
	@Override
	public void buildGui() {
		btnYes.setAction(()->{
			GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(GuiCertError.this, "https://www.curseforge.com/minecraft/mc-mods/letsencryptcraft/files", 13, false);
			guiconfirmopenlink.disableSecurityWarning();
			mc.displayGuiScreen(guiconfirmopenlink);
		});
		btnNo.setAction(()->{
			mc.displayGuiScreen(new CustomMainMenu());
		});
		this.addComponent(btnNo);
		this.addComponent(btnYes);
		this.addComponent(l);
		this.addComponent(l2);
	}
	@Override
	public void updateGui() {
		l.setCentered();
		l.setPosition(width/2, height/2);
		l2.setCentered();
		l2.setPosition(l.getX(), l.getY()+11);
		btnNo.setWidth(30);
		btnNo.setPosition(width/2, height-40);
		btnYes.setWidth(30);
		btnYes.setPosition(btnNo.getX()-40, btnNo.getY());
	}
	@Override
	public void handleKeyboardInput() throws IOException {
		int keyCode = Keyboard.getEventKey();

		if(keyCode == 1) {
			mc.displayGuiScreen(new CustomMainMenu());
			return;
		}
		super.handleKeyboardInput();
	}
	@Override
	public void confirmClicked(boolean result, int id) {
		// TODO Auto-generated method stub
		if (id == 13)
		{
			if (result)
			{
				try
				{
					Class<?> oclass = Class.forName("java.awt.Desktop");
					Object object = oclass.getMethod("getDesktop").invoke((Object)null);
					oclass.getMethod("browse", URI.class).invoke(object, new URI("https://www.curseforge.com/minecraft/mc-mods/letsencryptcraft/files"));
					oclass.getMethod("open", File.class).invoke(object, new File("./mods"));
				}
				catch (Throwable throwable)
				{
					System.err.println("Couldn't open link: "+ throwable.getMessage());
				}
			}
			mc.shutdown();
//			mc.displayGuiScreen(this);
		}
	}

}
