package talecraft.script.wrappers.item;

import net.minecraft.item.Item;
import talecraft.TaleCraft;
import talecraft.script.wrappers.IObjectWrapper;

import java.util.List;

public class ItemObjectWrapper implements IObjectWrapper {
    private final Item item;

    public ItemObjectWrapper(Item item) {
        this.item = item;
    }

    @Override
    public Item internal() {
        return item;
    }

    @Override
    public List<String> getOwnPropertyNames() {
        return TaleCraft.globalScriptManager.getOwnPropertyNames(this);
    }

    public String getUnlocalizedName() {
        return item.getUnlocalizedName();
    }

}
