package talecraft.client.gui.replaced_guis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class SelectionListEntryBase extends GuiListExtended.IGuiListEntry<SelectionListEntryBase> implements AutoCloseable {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat();
	private static final ResourceLocation ICON_MISSING = new ResourceLocation("textures/misc/unknown_server.png");
	private static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/world_selection.png");
	private final Minecraft client;
	private final SelectorBase worldSelScreen;
	public SelectionListEntryBase(SelectionListBase listWorldSelIn, WorldSummary worldSummaryIn, ISaveFormat saveFormat) {
		this.worldSelScreen = listWorldSelIn.getSelector();
		this.client = Minecraft.getInstance();
	}

	public void drawEntry(int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {}

	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		return false;
	}

	@Nullable
	private DynamicTexture func_195033_j() {
		return null;
	}

	public void close() {}

	public void func_195000_a(float p_195000_1_) {}
}
