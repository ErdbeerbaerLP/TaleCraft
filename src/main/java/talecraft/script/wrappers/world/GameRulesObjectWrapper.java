package talecraft.script.wrappers.world;

import net.minecraft.world.GameRules;
import talecraft.TaleCraft;
import talecraft.script.wrappers.IObjectWrapper;

import java.util.List;

public class GameRulesObjectWrapper implements IObjectWrapper {
    private final GameRules rules;

    public GameRulesObjectWrapper(GameRules gameRules) {
        this.rules = gameRules;
    }

    @Override
    public GameRules internal() {
        return rules;
    }

    @Override
    public List<String> getOwnPropertyNames() {
        return TaleCraft.globalScriptManager.getOwnPropertyNames(this);
    }

    public boolean hasRule(String name) {
        return rules.hasRule(name);
    }

    public String getRule(String name) {
        return rules.getString(name);
    }

    public int getRuleInt(String name) {
        return rules.getInt(name);
    }

    public boolean getRuleBool(String name) {
        return rules.getBoolean(name);
    }

    public void setRule(String name, String value) {
        rules.setOrCreateGameRule(name, value);
    }

    public String[] getRules() {
        return rules.getRules();
    }

}
