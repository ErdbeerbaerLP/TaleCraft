package talecraft.client.entity.npc.model;

import net.minecraft.client.model.ModelRenderer;
import talecraft.client.entity.npc.model.NPCModelPatternPart.PartRotation;
import talecraft.client.entity.npc.model.NPCModelPatternPart.PartTransformation;

import java.util.HashMap;

public class NPCModelData {

    public HashMap<String, NPCModelPatternPart> parts = new HashMap<>();
    public HashMap<String, ModelRenderer> animation = new HashMap<>();

    public NPCModelPattern currentModel;

    public int textureHeight;
    public int textureWidth;

    private NPCModel npcmodel;

    public NPCModelData(NPCModel model) {
        this.npcmodel = model;
        //noinspection ConstantConditions
        loadModel(NPCModelLoader.loadModel("internal:player"));
    }

    public void loadModel(NPCModelPattern model) {
        parts.clear();
        animation.clear();
        for (NPCModelPatternPart part : model.parts) {
            parts.put(part.name, part);
            ModelRenderer mr = new ModelRenderer(npcmodel, part.name);
            mr.setTextureOffset(part.texture.x, part.texture.y);
            PartTransformation trans = part.translation;
            PartTransformation size = part.size;
            PartRotation rotation = part.rotation;
            mr.addBox(trans.x, trans.y, trans.z, size.x, size.y, size.z, part.mirror);
            mr.setRotationPoint(rotation.x, rotation.y, rotation.z);
            animation.put(part.type, mr);
        }
        currentModel = model;
        this.textureHeight = model.texture.height;
        this.textureWidth = model.texture.width;
    }

}
