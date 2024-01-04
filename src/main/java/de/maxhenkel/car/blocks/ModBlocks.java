package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockPaint.EnumPaintType;
import de.maxhenkel.car.blocks.fluid.CarFluidBlock;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

    private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK, Main.MODID);

    public static final DeferredHolder<Block, BlockAsphalt> ASPHALT = BLOCK_REGISTER.register("asphalt", () -> new BlockAsphalt());
    public static final DeferredHolder<Block, BlockAsphaltSlope> ASPHALT_SLOPE = BLOCK_REGISTER.register("asphalt_slope", () -> new BlockAsphaltSlope());
    public static final DeferredHolder<Block, BlockAsphaltSlopeFlat> ASPHALT_SLOPE_FLAT_UPPER = BLOCK_REGISTER.register("asphalt_slope_flat_upper", () -> new BlockAsphaltSlopeFlat(true));
    public static final DeferredHolder<Block, BlockAsphaltSlopeFlat> ASPHALT_SLOPE_FLAT_LOWER = BLOCK_REGISTER.register("asphalt_slope_flat_lower", () -> new BlockAsphaltSlopeFlat(false));
    public static final DeferredHolder<Block, BlockAsphaltSlab> ASPHALT_SLAB = BLOCK_REGISTER.register("asphalt_slab", () -> new BlockAsphaltSlab());
    public static final DeferredHolder<Block, BlockGasStation> GAS_STATION = BLOCK_REGISTER.register("gas_station", () -> new BlockGasStation());
    public static final DeferredHolder<Block, BlockGasStationTop> GAS_STATION_TOP = BLOCK_REGISTER.register("gas_station_top", () -> new BlockGasStationTop());
    public static final DeferredHolder<Block, BlockCanolaCrop> CANOLA_CROP = BLOCK_REGISTER.register("canola", () -> new BlockCanolaCrop());
    public static final DeferredHolder<Block, BlockOilMill> OIL_MILL = BLOCK_REGISTER.register("oilmill", () -> new BlockOilMill());
    public static final DeferredHolder<Block, BlockBlastFurnace> BLAST_FURNACE = BLOCK_REGISTER.register("blastfurnace", () -> new BlockBlastFurnace());
    public static final DeferredHolder<Block, BlockBackmixReactor> BACKMIX_REACTOR = BLOCK_REGISTER.register("backmix_reactor", () -> new BlockBackmixReactor());
    public static final DeferredHolder<Block, BlockGenerator> GENERATOR = BLOCK_REGISTER.register("generator", () -> new BlockGenerator());
    public static final DeferredHolder<Block, BlockSplitTank> SPLIT_TANK = BLOCK_REGISTER.register("split_tank", () -> new BlockSplitTank());
    public static final DeferredHolder<Block, BlockSplitTankTop> SPLIT_TANK_TOP = BLOCK_REGISTER.register("split_tank_top", () -> new BlockSplitTankTop());
    public static final DeferredHolder<Block, BlockTank> TANK = BLOCK_REGISTER.register("tank", () -> new BlockTank());
    public static final DeferredHolder<Block, BlockGuardRail> GUARD_RAIL = BLOCK_REGISTER.register("guard_rail", () -> new BlockGuardRail());
    public static final DeferredHolder<Block, BlockCarWorkshop> CAR_WORKSHOP = BLOCK_REGISTER.register("car_workshop", () -> new BlockCarWorkshop());
    public static final DeferredHolder<Block, BlockCarWorkshopOutter> CAR_WORKSHOP_OUTTER = BLOCK_REGISTER.register("car_workshop_outter", () -> new BlockCarWorkshopOutter());
    public static final DeferredHolder<Block, BlockCable> CABLE = BLOCK_REGISTER.register("cable", () -> new BlockCable());
    public static final DeferredHolder<Block, BlockFluidExtractor> FLUID_EXTRACTOR = BLOCK_REGISTER.register("fluid_extractor", () -> new BlockFluidExtractor());
    public static final DeferredHolder<Block, BlockFluidPipe> FLUID_PIPE = BLOCK_REGISTER.register("fluid_pipe", () -> new BlockFluidPipe());
    public static final DeferredHolder<Block, BlockDynamo> DYNAMO = BLOCK_REGISTER.register("dynamo", () -> new BlockDynamo());
    public static final DeferredHolder<Block, BlockCrank> CRANK = BLOCK_REGISTER.register("crank", () -> new BlockCrank());
    public static final DeferredHolder<Block, BlockSign> SIGN = BLOCK_REGISTER.register("sign", () -> new BlockSign());
    public static final DeferredHolder<Block, BlockSignPost> SIGN_POST = BLOCK_REGISTER.register("sign_post", () -> new BlockSignPost());
    public static final DeferredHolder<Block, BlockCarPressurePlate> CAR_PRESSURE_PLATE = BLOCK_REGISTER.register("car_pressure_plate", () -> new BlockCarPressurePlate());
    public static final DeferredHolder<Block, LiquidBlock> BIO_DIESEL = BLOCK_REGISTER.register("bio_diesel", () -> new CarFluidBlock(() -> ModFluids.BIO_DIESEL.get()));
    public static final DeferredHolder<Block, LiquidBlock> CANOLA_METHANOL_MIX = BLOCK_REGISTER.register("canola_methanol_mix", () -> new CarFluidBlock(() -> ModFluids.CANOLA_METHANOL_MIX.get()));
    public static final DeferredHolder<Block, LiquidBlock> CANOLA_OIL = BLOCK_REGISTER.register("canola_oil", () -> new CarFluidBlock(() -> ModFluids.CANOLA_OIL.get()));
    public static final DeferredHolder<Block, LiquidBlock> GLYCERIN = BLOCK_REGISTER.register("glycerin", () -> new CarFluidBlock(() -> ModFluids.GLYCERIN.get()));
    public static final DeferredHolder<Block, LiquidBlock> METHANOL = BLOCK_REGISTER.register("methanol", () -> new CarFluidBlock(() -> ModFluids.METHANOL.get()));

    public static final DeferredHolder<Block, BlockPaint>[] PAINTS;
    public static final DeferredHolder<Block, BlockPaint>[] YELLOW_PAINTS;

    static {
        PAINTS = new DeferredHolder[EnumPaintType.values().length];
        for (int i = 0; i < PAINTS.length; i++) {
            int paintIndex = i;
            PAINTS[i] = BLOCK_REGISTER.register(EnumPaintType.values()[i].getPaintName(), () -> new BlockPaint(EnumPaintType.values()[paintIndex], false));
        }

        YELLOW_PAINTS = new DeferredHolder[EnumPaintType.values().length];
        for (int i = 0; i < YELLOW_PAINTS.length; i++) {
            int paintIndex = i;
            YELLOW_PAINTS[i] = BLOCK_REGISTER.register(EnumPaintType.values()[i].getPaintName() + "_yellow", () -> new BlockPaint(EnumPaintType.values()[paintIndex], true));
        }
    }

    public static void init(IEventBus eventBus) {
        BLOCK_REGISTER.register(eventBus);
    }

}
