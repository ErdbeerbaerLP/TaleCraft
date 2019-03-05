package talecraft;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import talecraft.blocks.UnderwaterBarrier;
import talecraft.blocks.tileentity.TileEntityBarrier;

public class TaleCraftRegistered {
	

	/**
	 * Load this class
	 */
	public static void load() {}


	// ----- Creative Tabs -----

	public static final ItemGroup TC_TAB = new TaleCraftTabs.TAB_TC();
	public static final ItemGroup TC_TAB_DECO = new TaleCraftTabs.TAB_TC_DECO();
	public static final ItemGroup TC_TAB_WORLD = new TaleCraftTabs.TAB_TC_WORLD();
	public static final ItemGroup TC_TAB_WEAPON = new TaleCraftTabs.TAB_TC_WEAPON();

	// ----- Blocks -----
	
	@ObjectHolder(TaleCraft.MOD_ID+":barrier")
	public static final UnderwaterBarrier WATER_BARRIER = null;
	
	// ----- Items -----

	// ----- TileEntities -----

	public static TileEntityType<TileEntityBarrier> TE_BARRIER;
	
	// ----- Entities -----

	// ----- Materials -----

	public static final Material MATERIAL_ADMINIUM = new Material(MaterialColor.AIR, false, true, false, true, false, false, false, EnumPushReaction.BLOCK);



}
