package talecraft.client.gui.misc;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.WorldSummary;
import talecraft.client.gui.qad.QADButton;
import talecraft.client.gui.qad.QADFACTORY;
import talecraft.client.gui.qad.QADGuiScreen;
import talecraft.client.gui.replaced_guis.NewWorldSelector;
/**
 * Information screen which is displayed when clicking the <br>
 * "Info" button in the world selection<br>
 * TODO: Finish this
 * @author ErdbeerbaerLP
 *
 */
public class GuiWorldInfo extends QADGuiScreen {
	private WorldSummary summary;
	private QADButton back;

    private final List<String> listLines = Lists.<String>newArrayList();
	public GuiWorldInfo(WorldSummary worldSummary) {
		this.drawCursorLines = false;
		summary = worldSummary;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void buildGui() {
		final File worldDat = new File("./saves/"+this.summary.getFileName()+"/talecraft/info.dat");
        
        NBTTagCompound worldComp = null;
        try {
        	worldComp = CompressedStreamTools.read(worldDat);
        }catch (Exception e) {
			// TODO: handle exception
		}
		addComponent(QADFACTORY.createLabel(summary.getDisplayName(),width/2-30,10));
		
		
		addComponent(back = new QADButton(width/2-40, height-60, 70, "Back"));
		back.setAction(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mc.displayGuiScreen(new NewWorldSelector(null));
			}
		});
		this.listLines.addAll(this.fontRenderer.listFormattedStringToWidth((worldComp != null && worldComp.hasKey("description")) ? worldComp.getString("description").replace("\\n", "\n"):(TextFormatting.RED+"No description provided!"), this.width - 50));
	}
	@Override
	public void updateGui() {
		
		
	}
	@Override
	public void handleKeyboardInput() throws IOException {
		// TODO Auto-generated method stub
		int c0 = Keyboard.getEventKey();
		if(c0 == 1) mc.displayGuiScreen(new NewWorldSelector(null));
		else super.handleKeyboardInput();
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		super.drawScreen(mouseX, mouseY, partialTicks);
		  int i = 90;

			for (String s : this.listLines)
	        {
	            this.drawCenteredString(this.fontRenderer, s, this.width / 2, i, 16777215);
	            i += this.fontRenderer.FONT_HEIGHT;
	        }
	}
}
