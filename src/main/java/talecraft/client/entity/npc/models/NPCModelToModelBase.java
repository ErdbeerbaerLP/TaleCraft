package talecraft.client.entity.npc.models;

import net.minecraft.client.model.*;
import talecraft.entity.NPC.EnumNPCModel;

import java.util.HashMap;

public class NPCModelToModelBase {

    private static HashMap<EnumNPCModel, ModelBase> map;

    public static ModelBase getModel(EnumNPCModel model) {
        if (map == null) registerAll();
        return map.get(model);
    }

    private static void registerAll() {
        map = new HashMap<>();
        registerItem(EnumNPCModel.Player, new ModelPlayer(0f, false));
        registerItem(EnumNPCModel.Zombie, new ModelZombie());
        registerItem(EnumNPCModel.Villager, new ModelVillager(0f));
        registerItem(EnumNPCModel.Skeleton, new ModifiedModelSkeleton());
        registerItem(EnumNPCModel.Creeper, new ModelCreeper());
        registerItem(EnumNPCModel.Pig, new ModelPig(0f));
        registerItem(EnumNPCModel.Cow, new ModelCow());
    }

    private static void registerItem(EnumNPCModel model, ModelBase base) {
        base.isChild = false;
        base.isRiding = false;
        map.put(model, base);
    }


}
