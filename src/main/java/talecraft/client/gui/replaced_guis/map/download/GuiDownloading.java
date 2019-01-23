package talecraft.client.gui.replaced_guis.map.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiDownloading extends GuiScreen {
	private GuiScreen parentGui;
	private DownloadZip zip;
	private boolean unzipOnly = false;
	private String fileName;
	public GuiDownloading(GuiScreen parent, DownloadZip zip) {
		this.parentGui = parent;
		this.zip = zip;
		// TODO Auto-generated constructor stub
	}
	public GuiDownloading(GuiDLMapInfo guiDLMapInfo, URL url) {
		// TODO Auto-generated constructor stub
		this.unzipOnly = true;
		this.parentGui = guiDLMapInfo;
		this.fileName = DownloadZip.getFileName(url);
	}
	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		buttonList.clear();
		this.buttonList.add(new GuiButtonExt(0, width/2-100, height/2+30, "BACK"));
		buttonList.get(0).visible = false;
	}
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		// TODO Auto-generated method stub
		switch(button.id) {
		case 0:
			this.mc.displayGuiScreen(new GuiDLMapInfo((GuiMapList) ((GuiDLMapInfo)parentGui).parent, ((GuiDLMapInfo)parentGui).map));
			break;
		}
	}
	
	
	
	private boolean unzipStarted = false;
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		drawDefaultBackground();
		buttonList.get(0).x = width/2-100;
		buttonList.get(0).y = height/2+30;
		String str1 = "";
		String str2 = "Status: ";
		String str3 = "Do NOT close the game while this is shown!";
		if(!unzipOnly) {
		if(zip.getStatus() == DownloadZip.DOWNLOADING) str1 = "Downloading map...";
		else if(zip.getStatus() == DownloadZip.ERROR) {
			str1 = "Error";
			buttonList.get(0).visible = true;
		}
		else if(zip.getStatus() == DownloadZip.COMPLETE) {
			str1 = "Unzipping...";
			str2 = "Status: Unzipping...";
		}
		else if(zip.getStatus() == DownloadZip.PAUSED) str1 = "Paused ?! Downloading map...";
		else if(zip.getStatus() == DownloadZip.CANCELLED) str1 = "Cancelled";
		int progress = Math.round(zip.getProgress());
		str2 = "Status: "+progress+"%";
		//		System.out.println(zip.getSize());
		drawCenteredString(fontRenderer, str1, width/2, height/2-30, 16777215);
		drawCenteredString(fontRenderer, str2, width/2, height/2-10, 16777215);
		drawCenteredString(fontRenderer, str3, width/2, height/2, 16777215);
		}else {
			str1 = "Unzipping...";
			str2 = "Status: Unzipping...";
		}
		if(unzipOnly || zip.getStatus() == DownloadZip.COMPLETE) { 
//			this.mc.displayGuiScreen(parentGui);
			if(this.unzipStarted) return;
			this.unzipStarted = true;
			byte[] buffer = new byte[1024];
	    	
		     try{
		    		
		    	//create output directory is not exists
		    	File folder = new File("./saves/.TC_MAPS/");
		    	if(!folder.exists()){
		    		folder.mkdir();
		    	}
		    		
		    	//get the zip file content
		    	ZipInputStream zis = 
		    		new ZipInputStream(new FileInputStream(unzipOnly ? new File(new File(mc.mcDataDir, "saves/.TC_DOWNLOADS"), fileName): zip.getFile()));
		    	//get the zipped file list entry
		    	ZipEntry ze = zis.getNextEntry();
		    		
		    	while(ze!=null){
		    			
		    	   String fileName = ze.getName();
		           File newFile = new File("./saves/.TC_MAPS/" + File.separator + fileName);
//		           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
		           
		            //create all non exists folders
		            //else you will hit FileNotFoundException for compressed folder
		            new File(newFile.getParent()).mkdirs();
		            if(ze.isDirectory()) newFile.mkdirs();
			        else { newFile.createNewFile();
		            FileOutputStream fos = new FileOutputStream(newFile);             

		            int len;
		            while ((len = zis.read(buffer)) > 0) {
		       		fos.write(buffer, 0, len);
		            }
		        		
		            fos.close();   
			        }
		            ze = zis.getNextEntry();
		    	}
		    	
		        zis.closeEntry();
		    	zis.close();
		    		
		    	System.out.println("Done");
		    	this.mc.displayGuiScreen(new GuiDLMapInfo((GuiMapList) ((GuiDLMapInfo)parentGui).parent, ((GuiDLMapInfo)parentGui).map));
				}catch(IOException ex){
		       ex.printStackTrace(); 
		       
		    }
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
