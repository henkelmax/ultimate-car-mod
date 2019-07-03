package de.maxhenkel.tools;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IRegistryDelegate;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class FluidStackWrapper extends FluidStack {

    private Fluid fluid;

    public FluidStackWrapper(Fluid fluid, int amount) {
        super(fluid, amount);
        setFluid(fluid);
    }

    public FluidStackWrapper(FluidStack stack, int amount) {
        super(stack, amount);
        setFluid(fluid);
    }

    public FluidStackWrapper(Fluid fluid, int amount, CompoundNBT nbt) {
        super(fluid, amount, nbt);
        setFluid(fluid);
    }

    private void setFluid(Fluid fluid) {
        try {
            Field fluidDelegate = FluidStack.class.getDeclaredField("fluidDelegate");

            fluidDelegate.setAccessible(true);
            fluidDelegate.set(this, new IRegistryDelegate<Fluid>() {

                @Override
                public Fluid get() {
                    return fluid;
                }

                @Override
                public ResourceLocation name() {
                    return new ResourceLocation(Main.MODID, fluid.getName());
                }

                @Override
                public Class<Fluid> type() {
                    return Fluid.class;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static FluidStack loadFluidStackFromNBT(CompoundNBT nbt) {
        if (nbt == null) {
            return null;
        }
        if (!nbt.contains("FluidName", Constants.NBT.TAG_STRING)) {
            return null;
        }

        String fluidName = nbt.getString("FluidName");
        Fluid fluid = byName(fluidName);
        FluidStack stack = new FluidStackWrapper(fluid, nbt.getInt("Amount"));

        if (nbt.contains("Tag")) {
            stack.tag = nbt.getCompound("Tag");
        }
        return stack;
    }

    public static Fluid byName(String name) {
        switch (name) {
            case "bio_diesel":
                return ModFluids.BIO_DIESEL;
            case "canola_methanol_mix":
                return ModFluids.CANOLA_METHANOL_MIX;
            case "canola_oil":
                return ModFluids.CANOLA_OIL;
            case "glycerin":
                return ModFluids.GLYCERIN;
            case "methanol":
                return ModFluids.METHANOL;
            default:
                return null;
        }
    }

    public static String getName(Fluid fluid) {
        if (fluid == ModFluids.BIO_DIESEL) {
            return "bio_diesel";
        } else if (fluid == ModFluids.CANOLA_METHANOL_MIX) {
            return "canola_methanol_mix";
        } else if (fluid == ModFluids.CANOLA_OIL) {
            return "canola_oil";
        } else if (fluid == ModFluids.GLYCERIN) {
            return "glycerin";
        } else if (fluid == ModFluids.METHANOL) {
            return "methanol";
        } else {
            return null;
        }
    }

    public static ResourceLocation getTexture(Fluid fluid){
        if (fluid == ModFluids.BIO_DIESEL) {
            return new ResourceLocation(Main.MODID, "textures/block/bio_diesel_still.png");
        } else if (fluid == ModFluids.CANOLA_METHANOL_MIX) {
            return new ResourceLocation(Main.MODID, "textures/block/canola_methanol_mix_still.png");
        } else if (fluid == ModFluids.CANOLA_OIL) {
            return new ResourceLocation(Main.MODID, "textures/block/canola_oil_still.png");
        } else if (fluid == ModFluids.GLYCERIN) {
            return new ResourceLocation(Main.MODID, "textures/block/glycerin_still.png");
        } else if (fluid == ModFluids.METHANOL) {
            return new ResourceLocation(Main.MODID, "textures/block/methanol_still.png");
        } else {
            return null;
        }
    }

    @Override
    public CompoundNBT writeToNBT(CompoundNBT nbt) {
        nbt.putString("FluidName", getName(getFluid()));
        nbt.putInt("Amount", amount);

        if (tag != null) {
            nbt.put("Tag", tag);
        }
        return nbt;
    }

    public String getLocalizedName() {
        return this.getFluid().getLocalizedName(this);
    }

    public String getUnlocalizedName() {
        return this.getFluid().getUnlocalizedName(this);
    }
}
