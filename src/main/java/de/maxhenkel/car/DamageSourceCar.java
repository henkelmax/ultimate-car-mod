package de.maxhenkel.car;

import net.minecraft.util.DamageSource;

public class DamageSourceCar extends DamageSource{

	public static final DamageSourceCar DAMAGE_CAR=new DamageSourceCar();
	
	public DamageSourceCar() {
		super("hit_car");
	}

	@Override
	public boolean canHarmInCreative() {
		return false;
	}
	
	@Override
	public boolean isDamageAbsolute() {
		return false;
	}
	
	@Override
	public boolean isDifficultyScaled() {
		return false;
	}
	
	@Override
	public boolean isUnblockable() {
		return true;
	}
	
	@Override
	public boolean isExplosion() {
		return false;
	}
	
	@Override
	public boolean isFireDamage() {
		return false;
	}
	
	@Override
	public boolean isMagicDamage() {
		return false;
	}
	
	@Override
	public boolean isProjectile() {
		return false;
	}
	
}
