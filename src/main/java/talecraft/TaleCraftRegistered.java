package talecraft;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import talecraft.blocks.UnderwaterBarrier;
import talecraft.blocks.tileentity.CollisionTriggerBlockTileEntity;
import talecraft.blocks.tileentity.LightBlockTE;
import talecraft.blocks.tileentity.TileEntityBarrier;
import talecraft.blocks.tileentity.URLBlockTileEntity;
import talecraft.blocks.util.CollisionTriggerBlock;
import talecraft.blocks.util.LightBlock;
import talecraft.blocks.util.URLBlock;
import talecraft.items.WandItem;

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
	
	@ObjectHolder(TaleCraft.MOD_ID+":lightblock")
	public static final LightBlock LIGHT_BLOCK = null;
	
	@ObjectHolder(TaleCraft.MOD_ID+":collisiontriggerblock")
	public static final CollisionTriggerBlock COLLISION_TRIGGER = null;
	
	@ObjectHolder(TaleCraft.MOD_ID+":urlblock")
	public static final URLBlock URL_BLOCK = null;
	
	// ----- Items -----
	@ObjectHolder(TaleCraft.MOD_ID+":wand")
	public static final WandItem ITEM_WAND = null;
	
	// ----- TileEntities -----

	public static TileEntityType<TileEntityBarrier> TE_BARRIER;
	public static TileEntityType<LightBlockTE> TE_LIGHT_BLOCK;
	public static TileEntityType<CollisionTriggerBlockTileEntity> TE_COLLISION_TRIGGER;
	public static TileEntityType<URLBlockTileEntity> TE_URL;
	
	// ----- Entities -----

	// ----- Materials -----

	public static final Material MATERIAL_ADMINIUM = new Material(MaterialColor.AIR, false, true, false, true, false, false, false, EnumPushReaction.BLOCK);
	
	



}
