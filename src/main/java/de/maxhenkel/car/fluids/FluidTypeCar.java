package de.maxhenkel.car.fluids;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class FluidTypeCar extends FluidType {

    public FluidTypeCar(String descriptionId) {
        super(Properties.create().canConvertToSource(false)
                .canDrown(true)
                .canExtinguish(false)
                .canHydrate(false)
                .canPushEntity(true)
                .canSwim(true)
                .lightLevel(0)
                .supportsBoating(false)
                .fallDistanceModifier(0F)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                .descriptionId(descriptionId));
    }

    public IClientFluidTypeExtensions getExtensions() {
        return new IClientFluidTypeExtensions() {

            private static final Identifier UNDERWATER_LOCATION = Identifier.withDefaultNamespace("textures/misc/underwater.png");

            @Override
            public Identifier getRenderOverlayTexture(Minecraft mc) {
                return UNDERWATER_LOCATION;
            }

        };
    }
}
