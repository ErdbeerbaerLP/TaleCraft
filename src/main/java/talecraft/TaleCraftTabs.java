package talecraft;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TaleCraftTabs {
	// Empty stub method for 'touching' the class
	public static final void init() {}

	public static ItemGroup tab_TaleCraftTab = new ItemGroup("talecraftTab") {
		
		@Override
		public ItemStack createIcon() {
//			return new ItemStack(TaleCraftItems.filler);
			return new ItemStack(Items.APPLE);
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void fill(NonNullList<ItemStack> items) {
			// Add useful items from 'Vanilla'
			items.add(new ItemStack(Blocks.COMMAND_BLOCK));
			items.add(new ItemStack(Blocks.SPAWNER));
			items.add(new ItemStack(Blocks.BARRIER));
			items.add(new ItemStack(Blocks.STRUCTURE_BLOCK));
			super.fill(items);
		}
	};

	public static ItemGroup tab_TaleCraftDecorationTab = new ItemGroup("talecraftDecoTab") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.APPLE, 1);
		}
		
		@OnlyIn(Dist.CLIENT)
		public int getIconItemDamage() {
			return (int) ((System.nanoTime() / 100D) % 16);
		}
	};
	
	public static ItemGroup tab_TaleCraftWorldTab = new ItemGroup("talecraftWorldTab") {
		@Override
		public ItemStack createIcon() {
//			return new ItemStack(TaleCraftItems.goldKey);
			return new ItemStack(Items.APPLE);
		}
	};
	
	public static ItemGroup tab_TaleCraftWeaponTab = new ItemGroup("talecraftWeaponTab") {
		@Override
		public ItemStack createIcon() {
//			return new ItemStack(TaleCraftItems.bomb);
			return new ItemStack(Items.APPLE);
		}
	};

}
