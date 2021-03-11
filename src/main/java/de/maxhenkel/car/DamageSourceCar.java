package de.maxhenkel.car;

import net.minecraft.util.DamageSource;

public class DamageSourceCar extends DamageSource {

    public static final DamageSourceCar DAMAGE_CAR = new DamageSourceCar();

    public DamageSourceCar() {
        super("hit_car");
    }

    @Override
    public boolean isBypassInvul() {
        return false;
    }

    @Override
    public boolean isBypassMagic() {
        return false;
    }

    @Override
    public boolean scalesWithDifficulty() {
        return false;
    }

    @Override
    public boolean isBypassArmor() {
        return true;
    }

    @Override
    public boolean isExplosion() {
        return false;
    }

    @Override
    public boolean isFire() {
        return false;
    }

    @Override
    public boolean isMagic() {
        return false;
    }

    @Override
    public boolean isProjectile() {
        return false;
    }

}
