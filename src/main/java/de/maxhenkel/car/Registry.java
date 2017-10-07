package de.maxhenkel.car;

import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import de.maxhenkel.car.blocks.tileentity.TileEntityBlastFurnace;
import de.maxhenkel.car.blocks.tileentity.TileEntityCable;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.blocks.tileentity.TileEntityDynamo;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.car.blocks.tileentity.TileEntityOilMill;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Registry {

	@SideOnly(Side.CLIENT)
	public static void addRenderItem(Item item) {
		if (item.getHasSubtypes()) {
			NonNullList<ItemStack> list = NonNullList.create();
			item.getSubItems(ModCreativeTabs.TAB_CAR, list);
			for (int i = 0; i < list.size(); i++) {
				ResourceLocation loc = new ResourceLocation(Main.MODID,
						item.getRegistryName().getResourcePath() + "_" + i);
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(loc, "inventory"));
			}
		} else {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void addRenderBlock(Block b) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0,
				new ModelResourceLocation(b.getRegistryName(), "inventory"));
	}

	public static void registerItem(IForgeRegistry<Item> registry, Item i) {
		registry.register(i);
	}

	public static void registerBlock(IForgeRegistry<Block> registry, Block b) {
		registry.register(b);
	}

	public static void registerItemBlock(IForgeRegistry<Item> registry, Block b) {
		registerItem(registry, new ItemBlock(b).setRegistryName(b.getRegistryName()));
	}

	public static void regiserRecipe(IForgeRegistry<IRecipe> registry, IRecipe recipe) {
		registry.register(recipe);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		Reciepes.registerReciepes();
		if (Config.keyRecipe) {
			event.getRegistry().register(new ReciepeKey().setRegistryName(new ResourceLocation(Main.MODID, "key")));
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		registerSound(event.getRegistry(), ModSounds.engine_stop);
		registerSound(event.getRegistry(), ModSounds.engine_start);
		registerSound(event.getRegistry(), ModSounds.engine_idle);
		registerSound(event.getRegistry(), ModSounds.engine_high);
		registerSound(event.getRegistry(), ModSounds.engine_fail);
		registerSound(event.getRegistry(), ModSounds.sport_engine_stop);
		registerSound(event.getRegistry(), ModSounds.sport_engine_start);
		registerSound(event.getRegistry(), ModSounds.sport_engine_idle);
		registerSound(event.getRegistry(), ModSounds.sport_engine_high);
		registerSound(event.getRegistry(), ModSounds.sport_engine_fail);
		registerSound(event.getRegistry(), ModSounds.gas_ststion);
		registerSound(event.getRegistry(), ModSounds.car_crash);
		registerSound(event.getRegistry(), ModSounds.generator);
		registerSound(event.getRegistry(), ModSounds.car_horn);
		registerSound(event.getRegistry(), ModSounds.car_lock);
		registerSound(event.getRegistry(), ModSounds.car_unlock);
	}

	@SideOnly(Side.CLIENT)
	public static void registerSound(IForgeRegistry<SoundEvent> registry, SoundEvent sound) {
		registry.register(sound);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		registerFluids();

		registerBlock(event.getRegistry(), ModBlocks.TAR);
		registerBlock(event.getRegistry(), ModBlocks.TAR_SLOPE);
		registerBlock(event.getRegistry(), ModBlocks.TAR_SLOPE_FLAT_UPPER);
		registerBlock(event.getRegistry(), ModBlocks.TAR_SLOPE_FLAT_LOWER);
		registerBlock(event.getRegistry(), ModBlocks.TAR_SLAB);
		registerBlock(event.getRegistry(), ModBlocks.FUEL_STATION);
		registerBlock(event.getRegistry(), ModBlocks.FUEL_STATION_TOP);// Only block
		registerBlock(event.getRegistry(), ModBlocks.CANOLA_CROP);// Only block
		registerBlock(event.getRegistry(), ModBlocks.CANOLA_OIL);
		registerBlock(event.getRegistry(), ModBlocks.OIL_MILL);
		registerBlock(event.getRegistry(), ModBlocks.BLAST_FURNACE);
		registerBlock(event.getRegistry(), ModBlocks.METHANOL);
		registerBlock(event.getRegistry(), ModBlocks.BACKMIX_REACTOR);
		registerBlock(event.getRegistry(), ModBlocks.CANOLA_METHANOL_MIX);
		registerBlock(event.getRegistry(), ModBlocks.GLYCERIN);
		registerBlock(event.getRegistry(), ModBlocks.BIO_DIESEL);
		registerBlock(event.getRegistry(), ModBlocks.GENERATOR);
		registerBlock(event.getRegistry(), ModBlocks.SPLIT_TANK);
		registerBlock(event.getRegistry(), ModBlocks.SPLIT_TANK_TOP);// Only block
		registerBlock(event.getRegistry(), ModBlocks.TANK);
		registerBlock(event.getRegistry(), ModBlocks.CRASH_BARRIER);
		registerBlock(event.getRegistry(), ModBlocks.CAR_WORKSHOP);
		registerBlock(event.getRegistry(), ModBlocks.CAR_WORKSHOP_OUTTER);
		registerBlock(event.getRegistry(), ModBlocks.CABLE);
		registerBlock(event.getRegistry(), ModBlocks.FLUID_PIPE);
		registerBlock(event.getRegistry(), ModBlocks.FLUID_EXTRACTOR);
		registerBlock(event.getRegistry(), ModBlocks.DYNAMO);
		registerBlock(event.getRegistry(), ModBlocks.CRANK);

		for (BlockPaint block : ModBlocks.PAINTS) {
			registerBlock(event.getRegistry(), block);
		}

		for (BlockPaint block : ModBlocks.YELLOW_PAINTS) {
			registerBlock(event.getRegistry(), block);
		}

		GameRegistry.registerTileEntity(TileEntityFuelStation.class, "TileEntityFuelStation");
		GameRegistry.registerTileEntity(TileEntityOilMill.class, "TileEntityOilMill");
		GameRegistry.registerTileEntity(TileEntityBlastFurnace.class, "TileEntityBlastFurnace");
		GameRegistry.registerTileEntity(TileEntityBackmixReactor.class, "TileEntityBackmixReactor");
		GameRegistry.registerTileEntity(TileEntityGenerator.class, "TileEntityGenerator");
		GameRegistry.registerTileEntity(TileEntitySplitTank.class, "TileEntitySplitTank");
		GameRegistry.registerTileEntity(TileEntityTank.class, "TileEntityTank");
		GameRegistry.registerTileEntity(TileEntityCarWorkshop.class, "TileEntityCarWorkshop");
		GameRegistry.registerTileEntity(TileEntityCable.class, "TileEntityCable");
		GameRegistry.registerTileEntity(TileEntityFluidExtractor.class, "TileEntityFluidExtractor");
		GameRegistry.registerTileEntity(TileEntityDynamo.class, "TileEntityDynamo");

		if (Config.canolaSeedDrop) {
			MinecraftForge.addGrassSeed(new ItemStack(ModItems.CANOLA_SEEDS), 8);
		}
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		registerItemBlock(event.getRegistry(), ModBlocks.TAR);
		registerItemBlock(event.getRegistry(), ModBlocks.TAR_SLOPE);
		registerItemBlock(event.getRegistry(), ModBlocks.TAR_SLOPE_FLAT_UPPER);
		registerItemBlock(event.getRegistry(), ModBlocks.TAR_SLOPE_FLAT_LOWER);
		registerItemBlock(event.getRegistry(), ModBlocks.TAR_SLAB);
		registerItemBlock(event.getRegistry(), ModBlocks.FUEL_STATION);
		registerItemBlock(event.getRegistry(), ModBlocks.CANOLA_OIL);
		registerItemBlock(event.getRegistry(), ModBlocks.OIL_MILL);
		registerItemBlock(event.getRegistry(), ModBlocks.BLAST_FURNACE);
		registerItemBlock(event.getRegistry(), ModBlocks.METHANOL);
		registerItemBlock(event.getRegistry(), ModBlocks.BACKMIX_REACTOR);
		registerItemBlock(event.getRegistry(), ModBlocks.CANOLA_METHANOL_MIX);
		registerItemBlock(event.getRegistry(), ModBlocks.GLYCERIN);
		registerItemBlock(event.getRegistry(), ModBlocks.BIO_DIESEL);
		registerItemBlock(event.getRegistry(), ModBlocks.GENERATOR);
		registerItemBlock(event.getRegistry(), ModBlocks.SPLIT_TANK);
		registerItemBlock(event.getRegistry(), ModBlocks.TANK);
		registerItemBlock(event.getRegistry(), ModBlocks.CRASH_BARRIER);
		registerItemBlock(event.getRegistry(), ModBlocks.CAR_WORKSHOP);
		registerItemBlock(event.getRegistry(), ModBlocks.CAR_WORKSHOP_OUTTER);
		registerItemBlock(event.getRegistry(), ModBlocks.CABLE);
		registerItemBlock(event.getRegistry(), ModBlocks.FLUID_PIPE);
		registerItemBlock(event.getRegistry(), ModBlocks.FLUID_EXTRACTOR);
		registerItemBlock(event.getRegistry(), ModBlocks.DYNAMO);
		registerItemBlock(event.getRegistry(), ModBlocks.CRANK);

		for (BlockPaint block : ModBlocks.PAINTS) {
			registerItemBlock(event.getRegistry(), block);
		}

		for (BlockPaint block : ModBlocks.YELLOW_PAINTS) {
			registerItemBlock(event.getRegistry(), block);
		}

		registerItem(event.getRegistry(), ModItems.PAINTER);
		registerItem(event.getRegistry(), ModItems.PAINTER_YELLOW);
		registerItem(event.getRegistry(), ModItems.CANOLA_SEEDS);
		registerItem(event.getRegistry(), ModItems.CANOLA);
		registerItem(event.getRegistry(), ModItems.RAPECAKE);

		registerItem(event.getRegistry(), ModItems.IRON_STICK);
		registerItem(event.getRegistry(), ModItems.ENGINE_PISTON);
		registerItem(event.getRegistry(), ModItems.ENGINE_3_CYLINDER);
		registerItem(event.getRegistry(), ModItems.ENGINE_6_CYLINDER);
		registerItem(event.getRegistry(), ModItems.CAR_SEAT);
		registerItem(event.getRegistry(), ModItems.WINDSHIELD);
		registerItem(event.getRegistry(), ModItems.WHEEL);
		registerItem(event.getRegistry(), ModItems.AXLE);
		registerItem(event.getRegistry(), ModItems.CONTAINER);
		registerItem(event.getRegistry(), ModItems.CAR_BODY_PART_WOOD);
		registerItem(event.getRegistry(), ModItems.CAR_TANK);
		registerItem(event.getRegistry(), ModItems.CONTROL_UNIT);
		registerItem(event.getRegistry(), ModItems.CANISTER);
		registerItem(event.getRegistry(), ModItems.REPAIR_KIT);
		registerItem(event.getRegistry(), ModItems.WRENCH);
		registerItem(event.getRegistry(), ModItems.SCREW_DRIVER);
		registerItem(event.getRegistry(), ModItems.HAMMER);
		registerItem(event.getRegistry(), ModItems.CABLE_INSULATOR);
		registerItem(event.getRegistry(), ModItems.KEY);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
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

		for (BlockPaint block : ModBlocks.PAINTS) {
			addRenderBlock(block);
		}

		for (BlockPaint block : ModBlocks.YELLOW_PAINTS) {
			addRenderBlock(block);
		}

		addRenderItem(ModItems.PAINTER);
		addRenderItem(ModItems.PAINTER_YELLOW);
		addRenderItem(ModItems.CANOLA_SEEDS);
		addRenderItem(ModItems.CANOLA);
		addRenderItem(ModItems.RAPECAKE);

		addRenderItem(ModItems.IRON_STICK);
		addRenderItem(ModItems.ENGINE_PISTON);
		addRenderItem(ModItems.ENGINE_3_CYLINDER);
		addRenderItem(ModItems.ENGINE_6_CYLINDER);
		addRenderItem(ModItems.CAR_SEAT);
		addRenderItem(ModItems.WINDSHIELD);
		addRenderItem(ModItems.WHEEL);
		addRenderItem(ModItems.AXLE);
		addRenderItem(ModItems.CONTAINER);
		addRenderItem(ModItems.CAR_BODY_PART_WOOD);
		addRenderItem(ModItems.CAR_TANK);
		addRenderItem(ModItems.CONTROL_UNIT);
		addRenderItem(ModItems.CANISTER);
		addRenderItem(ModItems.REPAIR_KIT);
		addRenderItem(ModItems.WRENCH);
		addRenderItem(ModItems.SCREW_DRIVER);
		addRenderItem(ModItems.HAMMER);
		addRenderItem(ModItems.CABLE_INSULATOR);
		addRenderItem(ModItems.KEY);

		registerFluidModel(ModBlocks.METHANOL);
		registerFluidModel(ModBlocks.CANOLA_OIL);
		registerFluidModel(ModBlocks.CANOLA_METHANOL_MIX);
		registerFluidModel(ModBlocks.GLYCERIN);
		registerFluidModel(ModBlocks.BIO_DIESEL);
	}

	@SideOnly(Side.CLIENT)
	private static void registerFluidModel(IFluidBlock fluidBlock) {

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(
				Main.MODID + ":" + fluidBlock.getFluid().getName(), fluidBlock.getFluid().getName());

		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelResourceLocation;
			}
		});
	}

	public static void registerFluids() {
		FluidRegistry.registerFluid(ModFluids.CANOLA_OIL);
		FluidRegistry.addBucketForFluid(ModFluids.CANOLA_OIL);

		FluidRegistry.registerFluid(ModFluids.METHANOL);
		FluidRegistry.addBucketForFluid(ModFluids.METHANOL);

		FluidRegistry.registerFluid(ModFluids.CANOLA_METHANOL_MIX);
		FluidRegistry.addBucketForFluid(ModFluids.CANOLA_METHANOL_MIX);

		FluidRegistry.registerFluid(ModFluids.GLYCERIN);
		FluidRegistry.addBucketForFluid(ModFluids.GLYCERIN);

		FluidRegistry.registerFluid(ModFluids.BIO_DIESEL);
		FluidRegistry.addBucketForFluid(ModFluids.BIO_DIESEL);
	}

}
