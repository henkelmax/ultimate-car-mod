package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.BlockPaint.EnumPaintType;
import de.maxhenkel.car.blocks.liquid.BlockBioDiesel;
import de.maxhenkel.car.blocks.liquid.BlockCanolaMethanolMix;
import de.maxhenkel.car.blocks.liquid.BlockCanolaOil;
import de.maxhenkel.car.blocks.liquid.BlockGlycerin;
import de.maxhenkel.car.blocks.liquid.BlockMethanol;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static final BlockTar TAR=new BlockTar();
	public static final BlockTarSlope TAR_SLOPE=new BlockTarSlope();
	public static final BlockTarSlopeFlat TAR_SLOPE_FLAT_UPPER=new BlockTarSlopeFlat(true);
	public static final BlockTarSlopeFlat TAR_SLOPE_FLAT_LOWER=new BlockTarSlopeFlat(false);
	public static final BlockTarSlab TAR_SLAB=new BlockTarSlab();
	public static final BlockFuelStation FUEL_STATION=new BlockFuelStation();
	public static final BlockFuelStationTop FUEL_STATION_TOP=new BlockFuelStationTop();
	public static final BlockCanolaCrop CANOLA_CROP=new BlockCanolaCrop();
	public static final BlockOilMill OIL_MILL=new BlockOilMill();
	public static final BlockBlastFurnace BLAST_FURNACE=new BlockBlastFurnace();
	public static final BlockCanolaOil CANOLA_OIL=new BlockCanolaOil();
	public static final BlockMethanol METHANOL=new BlockMethanol();
	public static final BlockBackmixReactor BACKMIX_REACTOR=new BlockBackmixReactor();
	public static final BlockCanolaMethanolMix CANOLA_METHANOL_MIX=new BlockCanolaMethanolMix();
	public static final BlockGlycerin GLYCERIN=new BlockGlycerin();
	public static final BlockBioDiesel BIO_DIESEL=new BlockBioDiesel();
	public static final BlockGenerator GENERATOR=new BlockGenerator();
	public static final BlockSplitTank SPLIT_TANK=new BlockSplitTank();
	public static final BlockSplitTankTop SPLIT_TANK_TOP=new BlockSplitTankTop();
	public static final BlockTank TANK=new BlockTank();
	public static final BlockCrashBarrier CRASH_BARRIER=new BlockCrashBarrier();
	public static final BlockCarWorkshop CAR_WORKSHOP=new BlockCarWorkshop();
	public static final BlockCarWorkshopOutter CAR_WORKSHOP_OUTTER=new BlockCarWorkshopOutter();
	public static final BlockCable CABLE=new BlockCable();
	public static final BlockFluidExtractor FLUID_EXTRACTOR=new BlockFluidExtractor();
	public static final BlockFluidPipe FLUID_PIPE=new BlockFluidPipe();
	public static final BlockDynamo DYNAMO=new BlockDynamo();
	public static final BlockCrank CRANK=new BlockCrank();
	public static final BlockSignPost SIGN_POST=new BlockSignPost();
	public static final BlockSign SIGN=new BlockSign();
	
	public static final BlockPaint[] PAINTS;
	public static final BlockPaint[] YELLOW_PAINTS;
	
	static{
		PAINTS=new BlockPaint[EnumPaintType.values().length];
		for(int i=0; i<PAINTS.length; i++){
			PAINTS[i]=new BlockPaint(EnumPaintType.values()[i], false);
		}
		
		YELLOW_PAINTS=new BlockPaint[EnumPaintType.values().length];
		for(int i=0; i<YELLOW_PAINTS.length; i++){
			YELLOW_PAINTS[i]=new BlockPaint(EnumPaintType.values()[i], true);
		}
	}
	
	public static void registerBlocks(){
		registerBlock(ModBlocks.TAR);
		registerBlock(ModBlocks.TAR_SLOPE);
		registerBlock(ModBlocks.TAR_SLOPE_FLAT_UPPER);
		registerBlock(ModBlocks.TAR_SLOPE_FLAT_LOWER);
		registerBlock(ModBlocks.TAR_SLAB);
		registerBlock(ModBlocks.FUEL_STATION);
		registerBlockOnly(ModBlocks.FUEL_STATION_TOP);
		registerBlockOnly(ModBlocks.CANOLA_CROP);
		registerBlock(ModBlocks.CANOLA_OIL);
		registerBlock(ModBlocks.OIL_MILL);
		registerBlock(ModBlocks.BLAST_FURNACE);
		registerBlock(ModBlocks.METHANOL);
		registerBlock(ModBlocks.BACKMIX_REACTOR);
		registerBlock(ModBlocks.CANOLA_METHANOL_MIX);
		registerBlock(ModBlocks.GLYCERIN);
		registerBlock(ModBlocks.BIO_DIESEL);
		registerBlock(ModBlocks.GENERATOR);
		registerBlock(ModBlocks.SPLIT_TANK);
		registerBlockOnly(ModBlocks.SPLIT_TANK_TOP);
		registerBlock(ModBlocks.TANK);
		registerBlock(ModBlocks.CRASH_BARRIER);
		registerBlock(ModBlocks.CAR_WORKSHOP);
		registerBlock(ModBlocks.CAR_WORKSHOP_OUTTER);
		registerBlock(ModBlocks.CABLE);
		registerBlock(ModBlocks.FLUID_PIPE);
		registerBlock(ModBlocks.FLUID_EXTRACTOR);
		registerBlock(ModBlocks.DYNAMO);
		registerBlock(ModBlocks.CRANK);
		registerBlock(ModBlocks.SIGN_POST);
		registerBlock(ModBlocks.SIGN);
		
		for(BlockPaint block:ModBlocks.PAINTS){
			registerBlock(block);
		}
		
		for(BlockPaint block:ModBlocks.YELLOW_PAINTS){
			registerBlock(block);
		}
	}
	
	public static void registerBlocksClient(){
		addRenderBlock(ModBlocks.TAR);
		addRenderBlock(ModBlocks.TAR_SLOPE);
		addRenderBlock(ModBlocks.TAR_SLOPE_FLAT_UPPER);
		addRenderBlock(ModBlocks.TAR_SLOPE_FLAT_LOWER);
		addRenderBlock(ModBlocks.TAR_SLAB);
		addRenderBlock(ModBlocks.FUEL_STATION);
		addRenderBlock(ModBlocks.OIL_MILL);
		addRenderBlock(ModBlocks.BLAST_FURNACE);
		addRenderBlock(ModBlocks.CANOLA_OIL);
		addRenderBlock(ModBlocks.METHANOL);
		addRenderBlock(ModBlocks.BACKMIX_REACTOR);
		addRenderBlock(ModBlocks.CANOLA_METHANOL_MIX);
		addRenderBlock(ModBlocks.GLYCERIN);
		addRenderBlock(ModBlocks.BIO_DIESEL);
		addRenderBlock(ModBlocks.GENERATOR);
		addRenderBlock(ModBlocks.SPLIT_TANK);
		addRenderBlock(ModBlocks.TANK);
		addRenderBlock(ModBlocks.CRASH_BARRIER);
		addRenderBlock(ModBlocks.CAR_WORKSHOP);
		addRenderBlock(ModBlocks.CAR_WORKSHOP_OUTTER);
		addRenderBlock(ModBlocks.CABLE);
		addRenderBlock(ModBlocks.FLUID_EXTRACTOR);
		addRenderBlock(ModBlocks.FLUID_PIPE);
		addRenderBlock(ModBlocks.DYNAMO);
		addRenderBlock(ModBlocks.CRANK);
		addRenderBlock(ModBlocks.SIGN_POST);
		addRenderBlock(ModBlocks.SIGN);
		
		for (BlockPaint block : ModBlocks.PAINTS) {
			addRenderBlock(block);
		}

		for (BlockPaint block : ModBlocks.YELLOW_PAINTS) {
			addRenderBlock(block);
		}
	}
	
	private static void addRenderBlock(Block b) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
	}
	
	private static void registerBlockOnly(Block b) {
		GameRegistry.register(b);
	}

	private static void registerBlock(Block b) {
		GameRegistry.register(b);
		GameRegistry.register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
	}
	
}
