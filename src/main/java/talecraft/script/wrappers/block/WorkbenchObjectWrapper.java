package talecraft.script.wrappers.block;

import talecraft.TaleCraft;
import talecraft.script.wrappers.IObjectWrapper;
import talecraft.util.WorkbenchManager;

import java.util.List;

public class WorkbenchObjectWrapper implements IObjectWrapper {

    private final WorkbenchManager manager;

    public WorkbenchObjectWrapper(WorkbenchManager manager) {
        this.manager = manager;
    }

    @Override
    public Object internal() {
        return manager;
    }

    @Override
    public List<String> getOwnPropertyNames() {
        return TaleCraft.globalScriptManager.getOwnPropertyNames(this);
    }

    public void removeRecipe(int index) {
        manager.remove(index);
    }

}
