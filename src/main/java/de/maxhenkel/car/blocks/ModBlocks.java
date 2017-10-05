package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.BlockPaint.EnumPaintType;
import de.maxhenkel.car.blocks.liquid.BlockBioDiesel;
import de.maxhenkel.car.blocks.liquid.BlockCanolaMethanolMix;
import de.maxhenkel.car.blocks.liquid.BlockCanolaOil;
import de.maxhenkel.car.blocks.liquid.BlockGlycerin;
import de.maxhenkel.car.blocks.liquid.BlockMethanol;

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
	
}
