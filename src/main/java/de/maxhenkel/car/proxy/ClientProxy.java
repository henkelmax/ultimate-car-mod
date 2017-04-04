package de.maxhenkel.car.proxy;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSplitTank;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererTank;
import de.maxhenkel.car.blocks.tileentity.render.TileentitySpecialRendererFuelStation;
import de.maxhenkel.car.entity.car.EntityCarBigWood;
import de.maxhenkel.car.entity.car.EntityCarTransporter;
import de.maxhenkel.car.entity.car.EntityCarWood;
import de.maxhenkel.car.entity.model.bigwood.RenderFactoryBigWoodCar;
import de.maxhenkel.car.entity.model.transporter.RenderFactoryTransporter;
import de.maxhenkel.car.entity.model.wood.RenderFactoryWoodCar;
import de.maxhenkel.car.events.KeyEvents;
import de.maxhenkel.car.events.PlayerEvents;
import de.maxhenkel.car.events.RenderEvents;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);

		RenderingRegistry.registerEntityRenderingHandler(EntityCarWood.class, new RenderFactoryWoodCar());
		RenderingRegistry.registerEntityRenderingHandler(EntityCarBigWood.class, new RenderFactoryBigWoodCar());
		RenderingRegistry.registerEntityRenderingHandler(EntityCarTransporter.class, new RenderFactoryTransporter());

		registerFluidModel(ModBlocks.METHANOL);
		registerFluidModel(ModBlocks.CANOLA_OIL);
		registerFluidModel(ModBlocks.CANOLA_METHANOL_MIX);
		registerFluidModel(ModBlocks.GLYCERIN);
		registerFluidModel(ModBlocks.BIO_DIESEL);
		
		ModBlocks.registerBlocksClient();

		ModItems.registerItemsClient();
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		MinecraftForge.EVENT_BUS.register(new KeyEvents());
		MinecraftForge.EVENT_BUS.register(new RenderEvents());
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFuelStation.class,
				new TileentitySpecialRendererFuelStation());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySplitTank.class,
				new TileEntitySpecialRendererSplitTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class,
				new TileEntitySpecialRendererTank());

	}

	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}

	private void registerFluidModel(IFluidBlock fluidBlock) {

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Main.MODID + ":" +fluidBlock.getFluid().getName(), fluidBlock.getFluid().getName());

		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelResourceLocation;
			}
		});
	}

}
