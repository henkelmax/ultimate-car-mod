package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockPaint.EnumPaintType;
import de.maxhenkel.car.blocks.fluid.CarFluidBlock;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.reflection.ReflectionUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

    private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final RegistryObject<BlockAsphalt> ASPHALT = BLOCK_REGISTER.register("asphalt", () -> new BlockAsphalt());
    public static final RegistryObject<BlockAsphaltSlope> ASPHALT_SLOPE = BLOCK_REGISTER.register("asphalt_slope", () -> new BlockAsphaltSlope());
    public static final RegistryObject<BlockAsphaltSlopeFlat> ASPHALT_SLOPE_FLAT_UPPER = BLOCK_REGISTER.register("asphalt_slope_flat_upper", () -> new BlockAsphaltSlopeFlat(true));
    public static final RegistryObject<BlockAsphaltSlopeFlat> ASPHALT_SLOPE_FLAT_LOWER = BLOCK_REGISTER.register("asphalt_slope_flat_lower", () -> new BlockAsphaltSlopeFlat(false));
    public static final RegistryObject<BlockAsphaltSlab> ASPHALT_SLAB = BLOCK_REGISTER.register("asphalt_slab", () -> new BlockAsphaltSlab());
    public static final RegistryObject<BlockGasStation> GAS_STATION = BLOCK_REGISTER.register("gas_station", () -> new BlockGasStation());
    public static final RegistryObject<BlockGasStationTop> GAS_STATION_TOP = BLOCK_REGISTER.register("gas_station_top", () -> new BlockGasStationTop());
    public static final RegistryObject<BlockCanolaCrop> CANOLA_CROP = BLOCK_REGISTER.register("canola", () -> new BlockCanolaCrop());
    public static final RegistryObject<BlockOilMill> OIL_MILL = BLOCK_REGISTER.register("oilmill", () -> new BlockOilMill());
    public static final RegistryObject<BlockBlastFurnace> BLAST_FURNACE = BLOCK_REGISTER.register("blastfurnace", () -> new BlockBlastFurnace());
    public static final RegistryObject<BlockBackmixReactor> BACKMIX_REACTOR = BLOCK_REGISTER.register("backmix_reactor", () -> new BlockBackmixReactor());
    public static final RegistryObject<BlockGenerator> GENERATOR = BLOCK_REGISTER.register("generator", () -> new BlockGenerator());
    public static final RegistryObject<BlockSplitTank> SPLIT_TANK = BLOCK_REGISTER.register("split_tank", () -> new BlockSplitTank());
    public static final RegistryObject<BlockSplitTankTop> SPLIT_TANK_TOP = BLOCK_REGISTER.register("split_tank_top", () -> new BlockSplitTankTop());
    public static final RegistryObject<BlockTank> TANK = BLOCK_REGISTER.register("tank", () -> new BlockTank());
    public static final RegistryObject<BlockGuardRail> GUARD_RAIL = BLOCK_REGISTER.register("guard_rail", () -> new BlockGuardRail());
    public static final RegistryObject<BlockCarWorkshop> CAR_WORKSHOP = BLOCK_REGISTER.register("car_workshop", () -> new BlockCarWorkshop());
    public static final RegistryObject<BlockCarWorkshopOutter> CAR_WORKSHOP_OUTTER = BLOCK_REGISTER.register("car_workshop_outter", () -> new BlockCarWorkshopOutter());
    public static final RegistryObject<BlockCable> CABLE = BLOCK_REGISTER.register("cable", () -> new BlockCable());
    public static final RegistryObject<BlockFluidExtractor> FLUID_EXTRACTOR = BLOCK_REGISTER.register("fluid_extractor", () -> new BlockFluidExtractor());
    public static final RegistryObject<BlockFluidPipe> FLUID_PIPE = BLOCK_REGISTER.register("fluid_pipe", () -> new BlockFluidPipe());
    public static final RegistryObject<BlockDynamo> DYNAMO = BLOCK_REGISTER.register("dynamo", () -> new BlockDynamo());
    public static final RegistryObject<BlockCrank> CRANK = BLOCK_REGISTER.register("crank", () -> new BlockCrank());
    public static final RegistryObject<BlockSign> SIGN = BLOCK_REGISTER.register("sign", () -> new BlockSign());
    public static final RegistryObject<BlockSignPost> SIGN_POST = BLOCK_REGISTER.register("sign_post", () -> new BlockSignPost());
    public static final RegistryObject<BlockCarPressurePlate> CAR_PRESSURE_PLATE = BLOCK_REGISTER.register("car_pressure_plate", () -> new BlockCarPressurePlate());
    public static final RegistryObject<LiquidBlock> BIO_DIESEL = BLOCK_REGISTER.register("bio_diesel", () -> new CarFluidBlock(() -> ModFluids.BIO_DIESEL.get()));
    public static final RegistryObject<LiquidBlock> CANOLA_METHANOL_MIX = BLOCK_REGISTER.register("canola_methanol_mix", () -> new CarFluidBlock(() -> ModFluids.CANOLA_METHANOL_MIX.get()));
    public static final RegistryObject<LiquidBlock> CANOLA_OIL = BLOCK_REGISTER.register("canola_oil", () -> new CarFluidBlock(() -> ModFluids.CANOLA_OIL.get()));
    public static final RegistryObject<LiquidBlock> GLYCERIN = BLOCK_REGISTER.register("glycerin", () -> new CarFluidBlock(() -> ModFluids.GLYCERIN.get()));
    public static final RegistryObject<LiquidBlock> METHANOL = BLOCK_REGISTER.register("methanol", () -> new CarFluidBlock(() -> ModFluids.METHANOL.get()));

    public static final RegistryObject<BlockPaint>[] PAINTS;
    public static final RegistryObject<BlockPaint>[] YELLOW_PAINTS;

    static {
        PAINTS = new RegistryObject[EnumPaintType.values().length];
        for (int i = 0; i < PAINTS.length; i++) {
            int paintIndex = i;
            PAINTS[i] = BLOCK_REGISTER.register(EnumPaintType.values()[i].getPaintName(), () -> new BlockPaint(EnumPaintType.values()[paintIndex], false));
        }

        YELLOW_PAINTS = new RegistryObject[EnumPaintType.values().length];
        for (int i = 0; i < YELLOW_PAINTS.length; i++) {
            int paintIndex = i;
            YELLOW_PAINTS[i] = BLOCK_REGISTER.register(EnumPaintType.values()[i].getPaintName() + "_yellow", () -> new BlockPaint(EnumPaintType.values()[paintIndex], true));
        }
    }

    public static void init() {
        BLOCK_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
