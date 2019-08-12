package talecraft.script.wrappers.scoreboard;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import talecraft.TaleCraft;
import talecraft.script.wrappers.IObjectWrapper;

import java.util.List;

public class ScoreboardObjectWrapper implements IObjectWrapper {
    private final Scoreboard scoreboard;

    public ScoreboardObjectWrapper(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;

    }

    @Override
    public Scoreboard internal() {
        return scoreboard;
    }

    @Override
    public List<String> getOwnPropertyNames() {
        return TaleCraft.globalScriptManager.getOwnPropertyNames(this);
    }

    public void addPlayerToTeam(String entityName, String teamName) {
        scoreboard.addPlayerToTeam(entityName, teamName);
    }

    public ScoreObjectWrapper getScore(String entityName, String objectiveName) {
        ScoreObjective objective = scoreboard.getObjective(objectiveName);
        //noinspection ConstantConditions
        return new ScoreObjectWrapper(scoreboard.getOrCreateScore(entityName, objective));
    }

    public ScoreObjectiveObjectWrapper getObjective(String objectiveName) {
        return new ScoreObjectiveObjectWrapper(scoreboard.getObjective(objectiveName));
    }

    // TODO: Add a way to create new objectives.
    // TODO: Add a way to remove objectives.

}
