package talecraft.client.gui.replaced_guis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import talecraft.Reference;
import talecraft.client.gui.misc.GuiWorldInfo;
/**
 * Modified version of GuiListWorldSelectionEntry
 * Changed to show world description, talecraft version and author
 * @author ErdbeerbaerLP
 *
 */
public class NewGuiListWorldSelectionEntry implements GuiListExtended.IGuiListEntry
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat();
    private static final ResourceLocation ICON_MISSING = new ResourceLocation("textures/misc/unknown_server.png");
    private static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/world_selection.png");
    private final Minecraft client;
    private final NewWorldSelector worldSelScreen;
    private final WorldSummary worldSummary;
    private final ResourceLocation iconLocation;
    private final NewGuiListWorldSelection containingListSel;
    private File iconFile;
    private DynamicTexture icon;
    private long lastClickTime;

    public NewGuiListWorldSelectionEntry(NewGuiListWorldSelection newGuiListWorldSelection, WorldSummary worldSummaryIn, ISaveFormat saveFormat)
    {
        this.containingListSel = newGuiListWorldSelection;
        this.worldSelScreen = newGuiListWorldSelection.getGuiWorldSelection();
        this.worldSummary = worldSummaryIn;
        this.client = Minecraft.getMinecraft();
        this.iconLocation = new ResourceLocation("worlds/" + worldSummaryIn.getFileName() + "/icon");
        this.iconFile = saveFormat.getFile(worldSummaryIn.getFileName(), "icon.png");

        if (!this.iconFile.isFile())
        {
            this.iconFile = null;
        }

        this.loadServerIcon();
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks)
    {
        String s = this.worldSummary.getDisplayName();
        final File worldDat = new File("./saves/"+this.worldSummary.getFileName()+"/talecraft/info.dat");
        String s1;
        try {
			final NBTTagCompound worldComp = CompressedStreamTools.read(worldDat);
			
        	s1 = worldComp.hasKey("description") ? TextFormatting.WHITE+worldComp.getString("description"):"No description provided!";
        	if(worldComp.getString("description").isEmpty()) s1= "No description provided!";
        }catch (Exception e) {
			// TODO: handle exception
        	s1 = "Error";
		}
        String s2 = "";
        if(worldDat.exists()) {
        if (StringUtils.isEmpty(s))
        {
            s = I18n.format("selectWorld.world") + " " + (slotIndex + 1);
        }

        if (this.worldSummary.requiresConversion())
        {
            s2 = I18n.format("selectWorld.conversion") + " " + s2;
        }
        else
        {
            s2 = "";

        String s3 = "<VERSION>";
        try {
			final NBTTagCompound worldComp = CompressedStreamTools.read(worldDat);
			if(worldComp.hasKey("author") && !worldComp.getString("author").trim().isEmpty()) {
				s2 = s2+"Author: "+worldComp.getString("author")+", ";
			}else s2 = s2+"Author: unknown, ";
        }catch (Exception e) {
			// TODO: handle exception
		}
        s2 = s2 + "Talecraft Version: ";
        	try {
				final NBTTagCompound worldComp = CompressedStreamTools.read(worldDat);
				if(worldComp.hasKey("version")) {
				s2 = s2 + (Reference.MOD_VERSION.equals(worldComp.getString("version"))?"":TextFormatting.RED) + worldComp.getString("version");
				}else {
					s2 = s2 + TextFormatting.RED+ "Unknown";
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				s2 = s2 +TextFormatting.RED+"ERROR";
			}
//        	s2 = s2 + ", Talecraft Version: " + s3;

        	
        }
            
        }else {
        	s1 = TextFormatting.RED+ "No world information file!";
        	s2 = TextFormatting.RED+"To fix open this world once";
        }
        
        if(s1.contains("\\n")) {
        	s1 = s1.split("\\\\n")[0].toString()+TextFormatting.WHITE+" <...>";
        }
        else if(s1.length() > 42) {
        	s1 = s1.substring(0,42)+TextFormatting.WHITE+" <...>";
        }
        
        this.client.fontRenderer.drawString(s, x + 32 + 3, y + 1, 16777215);
        this.client.fontRenderer.drawString(s1, x + 32 + 3, y + this.client.fontRenderer.FONT_HEIGHT + 3, 8421504);
        this.client.fontRenderer.drawString(s2, x + 32 + 3, y + this.client.fontRenderer.FONT_HEIGHT + this.client.fontRenderer.FONT_HEIGHT + 3, 8421504);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(this.icon != null ? this.iconLocation : ICON_MISSING);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        GlStateManager.disableBlend();
        //x + 32 + 3, y + this.client.fontRenderer.FONT_HEIGHT + 3
        
        if (this.client.gameSettings.touchscreen || isSelected)
        {
            this.client.getTextureManager().bindTexture(ICON_OVERLAY_LOCATION);
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int j = mouseX - x;
            int i = j < 32 ? 32 : 0;

            if (this.worldSummary.markVersionInList())
            {
                Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, (float)i, 32, 32, 256.0F, 256.0F);

                if (this.worldSummary.askToOpenWorld())
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, (float)i, 32, 32, 256.0F, 256.0F);

                    if (j < 32)
                    {
                        this.worldSelScreen.setVersionTooltip(TextFormatting.RED + I18n.format("selectWorld.tooltip.fromNewerVersion1") + "\n" + TextFormatting.RED + I18n.format("selectWorld.tooltip.fromNewerVersion2"));
                    }
                }
                else
                {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, (float)i, 32, 32, 256.0F, 256.0F);

                    if (j < 32)
                    {
                        this.worldSelScreen.setVersionTooltip(TextFormatting.GOLD + I18n.format("selectWorld.tooltip.snapshot1") + "\n" + TextFormatting.GOLD + I18n.format("selectWorld.tooltip.snapshot2"));
                    }
                }
            }
            else
            {
                Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, (float)i, 32, 32, 256.0F, 256.0F);
            }
            	
        }
    }
    /**
     * Called when the mouse is clicked within this entry. Returning true means that something within this entry was
     * clicked and the list should not be dragged.
     */
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
    {
        this.containingListSel.selectWorld(slotIndex);

        if (relativeX <= 32 && relativeX < 32)
        {
            this.joinWorld();
            return true;
        }
        else if (Minecraft.getSystemTime() - this.lastClickTime < 250L)
        {
            this.joinWorld();
            return true;
        }
        else
        {
            this.lastClickTime = Minecraft.getSystemTime();
            return false;
        }
    }

    public void joinWorld()
    {
    	final File worldDat = new File("./saves/"+this.worldSummary.getFileName()+"/talecraft/info.dat");
    	NBTTagCompound worldComp = null;
		try {
			worldComp = CompressedStreamTools.read(worldDat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.worldSummary.askToOpenWorld())
        {
            this.client.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
            {
                public void confirmClicked(boolean result, int id)
                {
                    if (result)
                    {
                        NewGuiListWorldSelectionEntry.this.loadWorld();
                    }
                    else
                    {
                    	NewGuiListWorldSelectionEntry.this.client.displayGuiScreen(NewGuiListWorldSelectionEntry.this.worldSelScreen);
                    }
                }
            }, I18n.format("selectWorld.versionQuestion"), I18n.format("selectWorld.versionWarning", this.worldSummary.getVersionName()), I18n.format("selectWorld.versionJoinButton"), I18n.format("gui.cancel"), 0));
        }
        else if(worldComp != null && worldComp.hasKey("version") && !worldComp.getString("version").equals(Reference.MOD_VERSION)) {
        	this.client.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
            {
                public void confirmClicked(boolean result, int id)
                {
                    if (result)
                    {
                        NewGuiListWorldSelectionEntry.this.loadWorld();
                    }
                    else
                    {
                    	NewGuiListWorldSelectionEntry.this.client.displayGuiScreen(NewGuiListWorldSelectionEntry.this.worldSelScreen);
                    }
                }
            }, I18n.format("selectWorld.versionQuestion"), "Thiw world was loaded in Talecraft version "+worldComp.getString("version")+ " and loading it in this version could cause corruption!" , I18n.format("selectWorld.versionJoinButton"), I18n.format("gui.cancel"), 0));

        }
        else
        {
            this.loadWorld();
        }
        
    }

    public void deleteWorld()
    {
        this.client.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
        {
            public void confirmClicked(boolean result, int id)
            {
                if (result)
                {
                	NewGuiListWorldSelectionEntry.this.client.displayGuiScreen(new GuiScreenWorking());
                    ISaveFormat isaveformat = NewGuiListWorldSelectionEntry.this.client.getSaveLoader();
                    isaveformat.flushCache();
                    isaveformat.deleteWorldDirectory(NewGuiListWorldSelectionEntry.this.worldSummary.getFileName());
                    NewGuiListWorldSelectionEntry.this.containingListSel.refreshList();
                }

                NewGuiListWorldSelectionEntry.this.client.displayGuiScreen(NewGuiListWorldSelectionEntry.this.worldSelScreen);
            }
        }, I18n.format("selectWorld.deleteQuestion"), "'" + this.worldSummary.getDisplayName() + "' " + I18n.format("selectWorld.deleteWarning"), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 0));
    }

    public void editWorld()
    {
        this.client.displayGuiScreen(new NewWorldEditor(this.worldSelScreen, this.worldSummary.getFileName()));
    }

    public void recreateWorld()
    {
        this.client.displayGuiScreen(new GuiScreenWorking());
        GuiCreateWorld guicreateworld = new GuiCreateWorld(this.worldSelScreen);
        ISaveHandler isavehandler = this.client.getSaveLoader().getSaveLoader(this.worldSummary.getFileName(), false);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();
        isavehandler.flush();

        if (worldinfo != null)
        {
            guicreateworld.recreateFromExistingWorld(worldinfo);
            this.client.displayGuiScreen(guicreateworld);
        }
    }

    private void loadWorld()
    {
        this.client.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));

        if (this.client.getSaveLoader().canLoadWorld(this.worldSummary.getFileName()))
        {
            net.minecraftforge.fml.client.FMLClientHandler.instance().tryLoadExistingWorld(new GuiWorldSelection(worldSelScreen), this.worldSummary);
        }
    }

    private void loadServerIcon()
    {
        boolean flag = this.iconFile != null && this.iconFile.isFile();

        if (flag)
        {
            BufferedImage bufferedimage;

            try
            {
                bufferedimage = ImageIO.read(this.iconFile);
                Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide");
                Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high");
            }
            catch (Throwable throwable)
            {
                LOGGER.error("Invalid icon for world {}", this.worldSummary.getFileName(), throwable);
                this.iconFile = null;
                return;
            }

            if (this.icon == null)
            {
                this.icon = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
                this.client.getTextureManager().loadTexture(this.iconLocation, this.icon);
            }

            bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.icon.getTextureData(), 0, bufferedimage.getWidth());
            this.icon.updateDynamicTexture();
        }
        else if (!flag)
        {
            this.client.getTextureManager().deleteTexture(this.iconLocation);
            this.icon = null;
        }
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
    }

    public void updatePosition(int slotIndex, int x, int y, float partialTicks)
    {
    }

	public void showWorldInfo() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiWorldInfo(this.worldSummary));
	}
}
