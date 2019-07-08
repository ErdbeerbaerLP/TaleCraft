package talecraft.client.gui.replaced_guis.map.download;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import talecraft.client.gui.qad.QADButton;
import talecraft.client.gui.qad.QADGuiScreen;
import talecraft.client.gui.qad.QADLabel;
import talecraft.client.gui.qad.QADScrollPanel;

public class GuiDLMapInfo extends QADGuiScreen {
	public GuiScreen parent;
	public DownloadableMap map;
	public GuiDLMapInfo(GuiMapList worldSelScreen, DownloadableMap map) {
		// TODO Auto-generated constructor stub
		this.map = map;
		this.parent = worldSelScreen;
	}
	
	
	private QADButton btnBack;
	private QADButton btnDownload;
	private QADButton btnDownloadMods;
	private QADLabel mapName;
	private QADLabel mapAuthor;
	private QADLabel mapInfo;
	private QADButton mapURL;
	private QADScrollPanel descPanel;
	
	@Override
	public void buildGui() {
		// TODO Auto-generated method stub
		addComponent(btnBack = new QADButton("BACK"));
		addComponent(btnDownload = new QADButton("Download!"));
		addComponent(btnDownloadMods = new QADButton("Download required mods"));
		addComponent(mapName = new QADLabel(map.name));
		addComponent(mapURL = new QADButton("Visit Website"));
		addComponent(descPanel = new QADScrollPanel());
		addComponent(mapAuthor = new QADLabel("By: "+map.author));
		addComponent(mapInfo = new QADLabel((map.hasScripts?"Has Scripts":"Has no scripts")+" | "+(map.additionalModsRequired?"Requires additional mods":"No mods needed")));
		descPanel.setSize(width/2, height/2);
		descPanel.setPosition((width/2)-(descPanel.getWidth()/2), (height/2)-(descPanel.getHeight()/2));
		
		final boolean downloaded = new File("."+File.separator+"saves"+File.separator+".TC_DOWNLOADS"+File.separator+DownloadZip.getFileName(map.dlURL)).exists();
		mapURL.setAction(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(GuiDLMapInfo.this, map.mapURL, 13, false);
	            guiconfirmopenlink.disableSecurityWarning();
	            mc.displayGuiScreen(guiconfirmopenlink);
			}
		});
		btnBack.setAction(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mc.displayGuiScreen(parent);
			}
		});
		
		if(!downloaded) btnDownload.setAction(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mc.displayGuiScreen(new GuiDownloading(GuiDLMapInfo.this, new DownloadZip(map.dlURL)));
				buildGui();
			}
		});
		else {
			btnDownload.setAction(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mc.displayGuiScreen(new GuiDownloading(GuiDLMapInfo.this, map.dlURL));
				
				}
			});
			btnDownload.setText("Unzip again");
		}
		
		btnDownloadMods.setEnabled(false);
		btnDownloadMods.setWidth(130);
		mapURL.setWidth(80);
		mapName.setCentered();
		mapAuthor.setCentered();
		mapInfo.setCentered();
		descPanel.allowLeftMouseButtonScrolling = true;
		updateDescription();
		
	}
	@Override
	public void onScreenResized() {
		updateDescription();
	}
	private void updateDescription() {
		// TODO Auto-generated method stub
		String desc = map.description;
		desc = desc.replace("\\\\n", "\n");
		desc = desc.replace("\\n", "\n");
		List<String> descLines1 = new ArrayList<String>();
		descLines1.addAll(this.fontRenderer.listFormattedStringToWidth(desc, descPanel.getContainerWidth() - 15));
		descPanel.removeAllComponents();
		int y = 10;
		descPanel.setViewportHeight(0);
		for(String line: descLines1) {
			descPanel.setViewportHeight(y+10);
			descPanel.addComponent(new QADLabel(line, 10, y));
			y = y+10;
		}
	}
	@Override
	public void updateGui() {
		// TODO Auto-generated method stub
		btnBack.setWidth(30);
		btnBack.setPosition(width/2+20, height-40);
		btnDownload.setWidth(60);
		btnDownload.setPosition(width/2-50, height-40);
		mapName.setPosition(width/2, 20);
		descPanel.setSize(width/2, height/2);
		descPanel.setPosition((width/2)-(descPanel.getWidth()/2), (height/2)-(descPanel.getHeight()/2));
		btnDownloadMods.setPosition(btnDownload.getX()-62-80, btnDownload.getY());
		mapAuthor.setPosition(width/2, mapName.getY()+20);
		mapInfo.setPosition(width/2, descPanel.getY()+descPanel.getHeight()+10);
		mapURL.setPosition(btnBack.getX()+40, btnBack.getY());
		mapURL.setEnabled(!map.mapURL.isEmpty());
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
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(map.mapURL));
                }
                catch (Throwable throwable)
                {
                    System.err.println("Couldn't open link: "+ throwable.getMessage());
                }
            }

            this.mc.displayGuiScreen(this);
        }
	}

}
