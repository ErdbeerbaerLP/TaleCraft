package talecraft.script.wrappers.block;

import net.minecraft.block.Block;
import talecraft.TaleCraft;
import talecraft.script.wrappers.IObjectWrapper;

import java.util.List;

public class BlockObjectWrapper implements IObjectWrapper {
    private Block block;

    public BlockObjectWrapper(Block block) {
        this.block = block;
    }

    public String getName() {
        return block.getLocalizedName();
    }

    public String getInternalName() {
        return block.getUnlocalizedName();
    }

    public BlockStateObjectWrapper getDefaultBlockState() {
        return new BlockStateObjectWrapper(block.getDefaultState());
    }

    @Override
    public Block internal() {
        return block;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return block.equals(obj);
    }

    @Override
    public int hashCode() {
        return block.hashCode();
    }

    @Override
    public List<String> getOwnPropertyNames() {
        return TaleCraft.globalScriptManager.getOwnPropertyNames(this);
    }

}
