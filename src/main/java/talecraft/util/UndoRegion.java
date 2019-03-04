package talecraft.util;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class UndoRegion{

	private BlockRegion region;
	public final int xPos, yPos, zPos;
	public final int width, height, length;
	public final World world;
	public IBlockState[] blocks;
	
	public UndoRegion(BlockRegion region, World world){
		region = new BlockRegion(region.getMinX() - 1, region.getMinY() - 1, region.getMinZ() - 1, region.getMaxX() + 1, region.getMaxY() + 1, region.getMaxZ() + 1);
		this.region = region;
		xPos = region.getMinX();
		yPos = region.getMinY();
		zPos = region.getMinZ();
		width = region.getWidth() + 1;
		height = region.getHeight() + 1;
		length = region.getLength() + 1;
		this.world = world;
		runIterator();
	}
	
	private UndoRegion(int xPos, int yPos, int zPos, int width, int height, int length, World world){
		BlockRegion region = new BlockRegion(xPos, yPos, zPos, xPos + width, yPos + height, zPos + length);
		this.region = region;
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.width = width;
		this.height = height;
		this.length = length;
		this.world = world;
		runIterator();
	}
	
	private void runIterator(){
		ArrayList<IBlockState> stateList = new ArrayList<IBlockState>();
		for(BlockPos pos : BlockPos.getAllInBox(new BlockPos(region.getMinX(), region.getMinY(), region.getMinZ()), new BlockPos(region.getMaxX(), region.getMaxY(), region.getMaxZ()))){
			IBlockState state = world.getBlockState(pos);
			if(state == null)state = Blocks.AIR.getDefaultState();
			stateList.add(state);
		}
		blocks = new IBlockState[stateList.size()];
		for(int i = 0; i < stateList.size(); i++){
			blocks[i] = stateList.get(i);
		}
	}
	
	public BlockPos getOrigin(){
		return region.getMin();
	}
	
	public NBTTagCompound toNBT(){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInt("x", xPos);
		tag.setInt("y", yPos);
		tag.setInt("z", zPos);
		
		tag.setInt("width", width);
		tag.setInt("height", height);
		tag.setInt("length", length);
		
		tag.setInt("world", world.getDimension().getType().getId());
		
		NBTTagList list = new NBTTagList();
		for(IBlockState state : blocks){
			list.add(new NBTTagInt(Block.getStateId(state)));
		}
		tag.setTag("blocks", list);
		return tag;
	}
	
	public static UndoRegion fromNBT(NBTTagCompound tag){
		UndoRegion ureg = new UndoRegion(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"), tag.getInt("width"), tag.getInt("height"), tag.getInt("length"), DimensionManager.getWorld(Minecraft.getInstance().world.getServer(), DimensionType.getById(tag.getInt("world")), true, true));
		NBTTagList list = tag.getList("blocks", 3);
		IBlockState[] blocks = new IBlockState[list.size()];
		for(int i = 0; i < list.size(); i++){
			blocks[i] = Block.getStateById(list.getInt(i));
		}
		ureg.blocks = blocks;
		return ureg;
	}
	
	
}
