package talecraft.client.gui.items.voxelator;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import talecraft.client.gui.qad.QADDropdownBox.ListModel;
import talecraft.client.gui.qad.QADDropdownBox.ListModelItem;
import talecraft.client.gui.vcui.VCUIRenderer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ListBlockStateListModel implements ListModel {
    private final NBTTagCompound tag;
    private final List<ListModelItem> items;
    private final List<ListModelItem> filtered;

    public ListBlockStateListModel(NBTTagCompound tag) {
        this.tag = tag;
        items = new ArrayList<>();
        filtered = new ArrayList<>();
        List<ItemStack> stacks = new ArrayList<>();
        for (Object block : Block.REGISTRY) {
            stacks.add(new ItemStack((Block) block));
        }
        for (ItemStack item : stacks) {
            Item itm = item.getItem();
            //noinspection ConstantConditions
            if (itm == null) continue;
            NonNullList<ItemStack> subitems = NonNullList.create();
            itm.getSubItems(CreativeTabs.INVENTORY, subitems);
            for (final ItemStack stack : subitems) {
                items.add(new BlockStateItem(stack));
            }
        }
    }

    @Override
    public void onSelection(ListModelItem selected) {
        BlockStateItem stateItem = (BlockStateItem) selected;
        Set<String> tempSet = new LinkedHashSet<>(tag.getKeySet());
        for (String string : tempSet) {
            tag.removeTag(string);
        }
        tag.merge(stateItem.stack.serializeNBT());
    }

    @Override
    public boolean hasItems() {
        return getItemCount() > 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public List<ListModelItem> getItems() {
        return items;
    }

    @Override
    public void applyFilter(String filterString) {
        filtered.clear();
        for (ListModelItem lmi : items) {
            BlockStateItem bsi = (BlockStateItem) lmi;
            if (bsi.stack.getItem().getItemStackDisplayName(bsi.stack).toLowerCase().contains(filterString.toLowerCase())) {
                filtered.add(lmi);
            }
        }
    }

    @Override
    public List<ListModelItem> getFilteredItems() {
        return filtered;
    }

    @Override
    public boolean hasIcons() {
        return true;
    }

    @Override
    public void drawIcon(VCUIRenderer renderer, float partialTicks, boolean light, ListModelItem item) {
        renderer.drawItemStack(((BlockStateItem) item).stack, 2, 2);
    }

    public static class BlockStateItem implements ListModelItem {

        private final ItemStack stack;

        public BlockStateItem(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public String getText() {
            return stack.getItem().getItemStackDisplayName(stack);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof BlockStateItem)) return false;
            return ItemStack.areItemStacksEqual(stack, ((BlockStateItem) obj).stack);
        }

        @Override
        public int hashCode() {
            return stack.hashCode();
        }

    }

}
