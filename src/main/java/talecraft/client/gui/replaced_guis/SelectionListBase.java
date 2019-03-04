package talecraft.client.gui.replaced_guis;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SelectionListBase extends GuiListExtended<SelectionListEntryBase> {
	private final SelectorBase worldSelection;
	/** Index to the currently selected world */
	private int selectedIdx = -1;
	@Nullable
	private List<WorldSummary> field_212331_y = null;

	public SelectionListBase(SelectorBase p_i49846_1_, Minecraft p_i49846_2_, int p_i49846_3_, int p_i49846_4_, int p_i49846_5_, int p_i49846_6_, int p_i49846_7_, Supplier<String> p_i49846_8_, @Nullable SelectionListBase p_i49846_9_) {
		super(p_i49846_2_, p_i49846_3_, p_i49846_4_, p_i49846_5_, p_i49846_6_, p_i49846_7_);
		this.worldSelection = p_i49846_1_;
	}

	public void func_212330_a(Supplier<String> p_212330_1_, boolean p_212330_2_) {}

	protected int getScrollBarX() {
		return super.getScrollBarX() + 20;
	}

	/**
	 * Gets the width of the list
	 */
	public int getListWidth() {
		return super.getListWidth() + 50;
	}

	public void selectWorld(int idx) {
		this.selectedIdx = idx;
		this.worldSelection.selectWorld(this.getSelectedWorld());
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	protected boolean isSelected(int slotIndex) {
		return slotIndex == this.selectedIdx;
	}

	@Nullable
	public SelectionListEntryBase getSelectedWorld() {
		return this.selectedIdx >= 0 && this.selectedIdx < this.getSize() ? this.getChildren().get(this.selectedIdx) : null;
	}

	public SelectorBase getSelector() {
		return this.worldSelection;
	}
}