package talecraft.blocks.util;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import talecraft.blocks.TCBlock;
import talecraft.blocks.TCITriggerableBlock;
import talecraft.invoke.EnumTriggerState;

@SuppressWarnings("deprecation")
public class RedstoneActivatorBlock extends TCBlock implements TCITriggerableBlock {
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public RedstoneActivatorBlock() {
        super();
        setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.FALSE));
    }

    @Deprecated
    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Deprecated
    @Override
    public int getWeakPower(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    @Override
    public void trigger(World world, BlockPos pos, EnumTriggerState triggerState) {
        if (world.isRemote)
            return;

        switch (triggerState) {
            case ON:
                world.setBlockState(pos, this.getDefaultState().withProperty(POWERED, Boolean.TRUE));
                break;
            case OFF:
                world.setBlockState(pos, this.getDefaultState().withProperty(POWERED, Boolean.FALSE));
                break;
            case INVERT:
                if (world.getBlockState(pos).getValue(POWERED)) {
                    world.setBlockState(pos, this.getDefaultState().withProperty(POWERED, Boolean.FALSE));
                } else {
                    world.setBlockState(pos, this.getDefaultState().withProperty(POWERED, Boolean.TRUE));
                }
                break;
            case IGNORE:
                world.setBlockState(pos, this.getDefaultState().withProperty(POWERED, Boolean.TRUE));
                break;
            default:
                world.setBlockState(pos, this.getDefaultState().withProperty(POWERED, Boolean.TRUE));
                break;
        }
    }

    @Deprecated
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWERED, (meta & 1) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        if (state.getValue(POWERED)) {
            i |= 1;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, POWERED);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(POWERED, Boolean.FALSE);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
