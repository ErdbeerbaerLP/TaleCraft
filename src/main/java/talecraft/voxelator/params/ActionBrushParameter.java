package talecraft.voxelator.params;

import talecraft.voxelator.BrushParameter;
import talecraft.voxelator.Voxelator;
import talecraft.voxelator.Voxelator.ActionFactory;

@SuppressWarnings("unused")
public final class ActionBrushParameter extends BrushParameter {

    public ActionBrushParameter(String name) {
        super(name);
    }

    @Override
    public BPType getType() {
        return BPType.xACTION;
    }

    public ActionFactory getDefault() {
        return Voxelator.actions.get("replace");
    }
}
