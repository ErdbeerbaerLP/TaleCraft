package talecraft.script.wrappers.potion;

import net.minecraft.potion.PotionEffect;
import talecraft.TaleCraft;
import talecraft.script.wrappers.IObjectWrapper;

import java.util.List;

public class PotionEffectObjectWrapper implements IObjectWrapper {
    private final PotionEffect potionEffect;

    public PotionEffectObjectWrapper(PotionEffect potionEffect) {
        this.potionEffect = potionEffect;
    }

    @Override
    public PotionEffect internal() {
        return potionEffect;
    }

    public int getDuration() {
        return potionEffect.getDuration();
    }

    public int getAmplifier() {
        return potionEffect.getAmplifier();
    }

    public boolean isDurationMax() {
        return potionEffect.getIsPotionDurationMax();
    }

    public boolean fromBeacon() {
        return potionEffect.getIsAmbient();
    }

    public PotionObjectWrapper getPotion() {
        return new PotionObjectWrapper(potionEffect.getPotion());
    }

    @Override
    public List<String> getOwnPropertyNames() {
        return TaleCraft.globalScriptManager.getOwnPropertyNames(this);
    }

}
