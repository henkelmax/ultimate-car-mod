package de.maxhenkel.car.blocks;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.BlockPaint.EnumPaintType;
import de.maxhenkel.car.blocks.fluid.CarFluidBlock;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

    private static final DeferredRegister.Blocks BLOCK_REGISTER = DeferredRegister.createBlocks(CarMod.MODID);

    public static final DeferredHolder<Block, BlockAsphalt> ASPHALT = BLOCK_REGISTER.registerBlock("asphalt", BlockAsphalt::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockAsphaltSlope> ASPHALT_SLOPE = BLOCK_REGISTER.registerBlock("asphalt_slope", BlockAsphaltSlope::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockAsphaltSlopeFlat> ASPHALT_SLOPE_FLAT_UPPER = BLOCK_REGISTER.registerBlock("asphalt_slope_flat_upper", p -> new BlockAsphaltSlopeFlat(p, true), BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockAsphaltSlopeFlat> ASPHALT_SLOPE_FLAT_LOWER = BLOCK_REGISTER.registerBlock("asphalt_slope_flat_lower", p -> new BlockAsphaltSlopeFlat(p, false), BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockAsphaltSlab> ASPHALT_SLAB = BLOCK_REGISTER.registerBlock("asphalt_slab", BlockAsphaltSlab::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockGasStation> GAS_STATION = BLOCK_REGISTER.registerBlock("gas_station", BlockGasStation::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockGasStationTop> GAS_STATION_TOP = BLOCK_REGISTER.registerBlock("gas_station_top", BlockGasStationTop::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockCanolaCrop> CANOLA_CROP = BLOCK_REGISTER.registerBlock("canola", BlockCanolaCrop::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockOilMill> OIL_MILL = BLOCK_REGISTER.registerBlock("oilmill", BlockOilMill::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockBlastFurnace> BLAST_FURNACE = BLOCK_REGISTER.registerBlock("blastfurnace", BlockBlastFurnace::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockBackmixReactor> BACKMIX_REACTOR = BLOCK_REGISTER.registerBlock("backmix_reactor", BlockBackmixReactor::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockGenerator> GENERATOR = BLOCK_REGISTER.registerBlock("generator", BlockGenerator::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockSplitTank> SPLIT_TANK = BLOCK_REGISTER.registerBlock("split_tank", BlockSplitTank::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockSplitTankTop> SPLIT_TANK_TOP = BLOCK_REGISTER.registerBlock("split_tank_top", BlockSplitTankTop::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockTank> TANK = BLOCK_REGISTER.registerBlock("tank", BlockTank::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockGuardRail> GUARD_RAIL = BLOCK_REGISTER.registerBlock("guard_rail", BlockGuardRail::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockCarWorkshop> CAR_WORKSHOP = BLOCK_REGISTER.registerBlock("car_workshop", BlockCarWorkshop::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockCarWorkshopOutter> CAR_WORKSHOP_OUTTER = BLOCK_REGISTER.registerBlock("car_workshop_outter", BlockCarWorkshopOutter::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockCable> CABLE = BLOCK_REGISTER.registerBlock("cable", BlockCable::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockFluidExtractor> FLUID_EXTRACTOR = BLOCK_REGISTER.registerBlock("fluid_extractor", BlockFluidExtractor::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockFluidPipe> FLUID_PIPE = BLOCK_REGISTER.registerBlock("fluid_pipe", BlockFluidPipe::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockDynamo> DYNAMO = BLOCK_REGISTER.registerBlock("dynamo", BlockDynamo::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockCrank> CRANK = BLOCK_REGISTER.registerBlock("crank", BlockCrank::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockSign> SIGN = BLOCK_REGISTER.registerBlock("sign", BlockSign::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockSignPost> SIGN_POST = BLOCK_REGISTER.registerBlock("sign_post", BlockSignPost::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, BlockCarPressurePlate> CAR_PRESSURE_PLATE = BLOCK_REGISTER.registerBlock("car_pressure_plate", BlockCarPressurePlate::new, BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, LiquidBlock> BIO_DIESEL = BLOCK_REGISTER.registerBlock("bio_diesel", (p) -> new CarFluidBlock(p, () -> ModFluids.BIO_DIESEL.get()), BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, LiquidBlock> CANOLA_METHANOL_MIX = BLOCK_REGISTER.registerBlock("canola_methanol_mix", (p) -> new CarFluidBlock(p,() -> ModFluids.CANOLA_METHANOL_MIX.get()), BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, LiquidBlock> CANOLA_OIL = BLOCK_REGISTER.registerBlock("canola_oil", (p) -> new CarFluidBlock(p,() -> ModFluids.CANOLA_OIL.get()), BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, LiquidBlock> GLYCERIN = BLOCK_REGISTER.registerBlock("glycerin", (p) -> new CarFluidBlock(p,() -> ModFluids.GLYCERIN.get()), BlockBehaviour.Properties.of());
    public static final DeferredHolder<Block, LiquidBlock> METHANOL = BLOCK_REGISTER.registerBlock("methanol", (p) -> new CarFluidBlock(p,() -> ModFluids.METHANOL.get()), BlockBehaviour.Properties.of());

    public static final DeferredHolder<Block, BlockPaint>[] PAINTS;
    public static final DeferredHolder<Block, BlockPaint>[] YELLOW_PAINTS;

    static {
        PAINTS = new DeferredHolder[EnumPaintType.values().length];
        for (int i = 0; i < PAINTS.length; i++) {
            int paintIndex = i;
            PAINTS[i] = BLOCK_REGISTER.registerBlock(EnumPaintType.values()[i].getPaintName(), (p) -> new BlockPaint(p, EnumPaintType.values()[paintIndex], false), BlockBehaviour.Properties.of());
        }

        YELLOW_PAINTS = new DeferredHolder[EnumPaintType.values().length];
        for (int i = 0; i < YELLOW_PAINTS.length; i++) {
            int paintIndex = i;
            YELLOW_PAINTS[i] = BLOCK_REGISTER.registerBlock(EnumPaintType.values()[i].getPaintName() + "_yellow", (p) -> new BlockPaint(p, EnumPaintType.values()[paintIndex], true), BlockBehaviour.Properties.of());
        }
    }

    public static void init(IEventBus eventBus) {
        BLOCK_REGISTER.register(eventBus);
    }

}
