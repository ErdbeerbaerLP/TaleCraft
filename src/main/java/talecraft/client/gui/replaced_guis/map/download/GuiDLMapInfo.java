package talecraft.client.gui.replaced_guis.map.download;

import java.io.File;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import talecraft.client.gui.qad.QADBoxLabel;
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
	private QADScrollPanel descPanel;
	@Override
	public void buildGui() {
		// TODO Auto-generated method stub
		addComponent(btnBack = new QADButton("BACK"));
		addComponent(btnDownload = new QADButton("Download!"));
		addComponent(btnDownloadMods = new QADButton("Download required mods"));
		addComponent(mapName = new QADLabel(map.name));
		addComponent(descPanel = new QADScrollPanel());
		addComponent(mapAuthor = new QADLabel("By: "+map.author));
		addComponent(mapInfo = new QADLabel((map.hasScripts?"Has Scripts":"Has no scripts")+" | "+(map.additionalModsRequired?"Requires additional mods":"No mods needed")));
		final boolean downloaded = new File("."+File.separator+"saves"+File.separator+".TC_DOWNLOADS"+File.separator+DownloadZip.getFileName(map.dlURL)).exists();
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
		mapName.setCentered();
		mapAuthor.setCentered();
		mapInfo.setCentered();
		descPanel.allowLeftMouseButtonScrolling = true;
		
		
		
		String desc = map.description;
		desc = desc.replace("\\\\n", "\n");
		desc = desc.replace("\\n", "\n");
		String[] descLines = desc.split("\n");
		int y = 10;
		descPanel.setViewportHeight(0);
		for(String line: descLines) {
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
	}
	

}
