package talecraft.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import talecraft.TaleCraft;
import talecraft.blocks.TCIBlockCommandReceiver;
import talecraft.invoke.IInvokeSource;

public abstract class TCTileEntity extends TileEntity implements IInvokeSource, TCIBlockCommandReceiver, ITickable  {
	public TCTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

	private boolean isTileEntityInitialized = false;

	@Override
	public void setPos(BlockPos pos) {
		super.setPos(pos);

		if(!isTileEntityInitialized) {
//			nativeObject = TaleCraft.globalScriptManager.createNewBlockScope(getEntityWorld(), this.pos);
			this.init();
			isTileEntityInitialized = true;
		}
	}

	public void init() {

	}


	@Override
	public void tick(){}

	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);
		readFromNBT_do(compound);
	}

	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		compound = super.write(compound);
		return writeToNBT_do(compound);
	}

	public abstract void readFromNBT_do(NBTTagCompound comp);
	public abstract NBTTagCompound writeToNBT_do(NBTTagCompound comp);
	
	@Override
	public void commandReceived(String command, NBTTagCompound data) {
		// empty

		if(command.equals("re_init")) {
			isTileEntityInitialized = false;
			setPos(pos);
			return;
		}

		TaleCraft.logger.info("Unknown block command '" + command + "' @ " + getPos());
	}

//	@Override
//	public final Scriptable getInvokeScriptScope() {
//		return nativeObject;
//	}
	
//	@Override
//	public ITextComponent getDisplayName() {
//		return new TextComponentString(getName());
//	}
//	
//	@Override
//	public void sendMessage(ITextComponent message) {
//		// nope!
//	}
//
//	@Override
//	public boolean canUseCommand(int permLevel, String commandName) {
//		return true;
//	}

	@Override
	public BlockPos getInvokePosition() {
		return this.pos;
	}
	
	@Override
	public BlockPos getPos() {
		return this.pos;
	}
	
//	@Override
//	public Vec3d getPositionVector() {
//		return new Vec3d(this.pos.getX(),this.pos.getY(),this.pos.getZ());
//	}

	@Override
	public World getInvokeWorld() {
		return this.getWorld();
	}
//	@Override
//	public World getEntityWorld() {
//		return this.getWorld();
//	}

//	@Override
//	public Entity getCommandSenderEntity() {
//		return null; // nope, can't implement!
//	}
//
//	@Override
//	public boolean sendCommandFeedback() {
//		return false; // nope
//	}

//	@Override
//	public void setCommandStat(Type type, int amount) {
//		// nope
//	}

//	@Override
//	public ICommandSender getInvokeAsCommandSender() {
//		return this;
//	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 2048; // 128 blocks!
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return TaleCraft.isBuildMode() ? (pass == 0) : false;
	}

//	@Override
//	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
//		return false;
//	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT_do(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, write(new NBTTagCompound()));
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return write(new NBTTagCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		read(tag);
	}

//	@Override
//	public MinecraftServer getServer() {
//		return FMLCommonHandler.instance().getMinecraftServerInstance();
//	}

}
