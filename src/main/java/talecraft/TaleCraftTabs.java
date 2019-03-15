package talecraft;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TaleCraftTabs {

	public static class TAB_TC extends ItemGroup{

		public TAB_TC() {
			super("talecraft_main");
			// TODO Auto-generated constructor stub
		}
		@Override
		public ItemStack createIcon() {
//			return new ItemStack(TaleCraftItems.filler);
			return new ItemStack(TaleCraftRegistered.ITEM_WAND);
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void fill(NonNullList<ItemStack> items) {
			// Add useful items from 'Vanilla'
			items.add(new ItemStack(Blocks.COMMAND_BLOCK));
			items.add(new ItemStack(Blocks.SPAWNER));
			items.add(new ItemStack(TaleCraftRegistered.WATER_BARRIER));
			items.add(new ItemStack(Blocks.STRUCTURE_BLOCK));
			items.add(new ItemStack(TaleCraftRegistered.ITEM_WAND));
			items.add(new ItemStack(TaleCraftRegistered.LIGHT_BLOCK));
			items.add(new ItemStack(TaleCraftRegistered.COLLISION_TRIGGER));
			items.add(new ItemStack(TaleCraftRegistered.URL_BLOCK));
			super.fill(items);
		}
		
	}
	public static class TAB_TC_DECO extends ItemGroup{

		public TAB_TC_DECO() {
			super("talecraft_deco");
			// TODO Auto-generated constructor stub
		}
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.APPLE, 1);
		}
		
	}
	public static class TAB_TC_WORLD extends ItemGroup{

		public TAB_TC_WORLD() {
			super("talecraft_world");
			// TODO Auto-generated constructor stub
		}
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.APPLE, 1);
		}
		
	}
	public static class TAB_TC_WEAPON extends ItemGroup{

		public TAB_TC_WEAPON() {
			super("talecraft_weapons");
			// TODO Auto-generated constructor stub
		}
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.APPLE, 1);
		}
		
	}
	

}
