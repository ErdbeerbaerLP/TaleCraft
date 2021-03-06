package talecraft.script.wrappers.scoreboard;

import net.minecraft.scoreboard.IScoreCriteria.EnumRenderType;
import net.minecraft.scoreboard.ScoreObjective;
import talecraft.TaleCraft;
import talecraft.script.wrappers.IObjectWrapper;

import java.util.List;

public class ScoreObjectiveObjectWrapper implements IObjectWrapper {
    private final ScoreObjective objective;

    public ScoreObjectiveObjectWrapper(ScoreObjective objective) {
        this.objective = objective;
    }

    @Override
    public ScoreObjective internal() {
        return objective;
    }

    @Override
    public List<String> getOwnPropertyNames() {
        return TaleCraft.globalScriptManager.getOwnPropertyNames(this);
    }

    public String getName() {
        return objective.getName();
    }

    public String getDisplayName() {
        return objective.getName();
    }

    public EnumRenderType getRenderType() {
        return objective.getRenderType();
    }

    public ScoreObjectWrapper getValue(String name) {
        return new ScoreObjectWrapper(objective.getScoreboard().getOrCreateScore(name, objective));
    }

    public ScoreboardCriteriaObjectWrapper getCriteria() {
        return new ScoreboardCriteriaObjectWrapper(objective.getCriteria());
    }

    public ScoreboardObjectWrapper getScoreboard() {
        return new ScoreboardObjectWrapper(objective.getScoreboard());
    }

}
