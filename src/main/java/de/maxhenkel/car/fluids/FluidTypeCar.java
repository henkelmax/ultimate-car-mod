package de.maxhenkel.car.fluids;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class FluidTypeCar extends FluidType {

    private final Identifier stillTexture;
    private final Identifier flowingTexture;

    public FluidTypeCar(String descriptionId, Identifier stillTexture, Identifier flowingTexture) {
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
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
    }

    public IClientFluidTypeExtensions getExtensions() {
        return new IClientFluidTypeExtensions() {

            private static final Identifier UNDERWATER_LOCATION = Identifier.withDefaultNamespace("textures/misc/underwater.png");
            private static final Identifier WATER_OVERLAY = Identifier.withDefaultNamespace("block/water_overlay");

            @Override
            public Identifier getStillTexture() {
                return stillTexture;
            }

            @Override
            public Identifier getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public Identifier getOverlayTexture() {
                return WATER_OVERLAY;
            }

            @Override
            public Identifier getRenderOverlayTexture(Minecraft mc) {
                return UNDERWATER_LOCATION;
            }

        };
    }
}
