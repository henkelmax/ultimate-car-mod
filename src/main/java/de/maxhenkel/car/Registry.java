package de.maxhenkel.car;

import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import de.maxhenkel.car.blocks.tileentity.TileEntityBlastFurnace;
import de.maxhenkel.car.blocks.tileentity.TileEntityCable;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.blocks.tileentity.TileEntityDynamo;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.car.blocks.tileentity.TileEntityOilMill;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
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
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
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

        event.getRegistry().register(new ReciepeKey().setRegistryName(new ResourceLocation(Main.MODID, "key")));
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        registerSound(event.getRegistry(), ModSounds.engine_stop);
        registerSound(event.getRegistry(), ModSounds.engine_starting);
        registerSound(event.getRegistry(), ModSounds.engine_start);
        registerSound(event.getRegistry(), ModSounds.engine_idle);
        registerSound(event.getRegistry(), ModSounds.engine_high);
        registerSound(event.getRegistry(), ModSounds.engine_fail);
        registerSound(event.getRegistry(), ModSounds.sport_engine_stop);
        registerSound(event.getRegistry(), ModSounds.sport_engine_starting);
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
        registerSound(event.getRegistry(), ModSounds.ratchet);
    }

    public static void registerSound(IForgeRegistry<SoundEvent> registry, SoundEvent sound) {
        registry.register(sound);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        registerFluids();

        for (Block block : ModBlocks.getAll()) {
            registerBlock(event.getRegistry(), block);
        }

        for (BlockPaint block : ModBlocks.PAINTS) {
            registerBlock(event.getRegistry(), block);
        }

        for (BlockPaint block : ModBlocks.YELLOW_PAINTS) {
            registerBlock(event.getRegistry(), block);
        }

        GameRegistry.registerTileEntity(TileEntityFuelStation.class, new ResourceLocation(Main.MODID, "fuel_station"));
        GameRegistry.registerTileEntity(TileEntityOilMill.class, new ResourceLocation(Main.MODID, "oil_mill"));
        GameRegistry.registerTileEntity(TileEntityBlastFurnace.class, new ResourceLocation(Main.MODID, "blast_furnace"));
        GameRegistry.registerTileEntity(TileEntityBackmixReactor.class, new ResourceLocation(Main.MODID, "backmix_reactor"));
        GameRegistry.registerTileEntity(TileEntityGenerator.class, new ResourceLocation(Main.MODID, "generator"));
        GameRegistry.registerTileEntity(TileEntitySplitTank.class, new ResourceLocation(Main.MODID, "split_tank"));
        GameRegistry.registerTileEntity(TileEntityTank.class, new ResourceLocation(Main.MODID, "tank"));
        GameRegistry.registerTileEntity(TileEntityCarWorkshop.class, new ResourceLocation(Main.MODID, "car_workshop"));
        GameRegistry.registerTileEntity(TileEntityCable.class, new ResourceLocation(Main.MODID, "cable"));
        GameRegistry.registerTileEntity(TileEntityFluidExtractor.class, new ResourceLocation(Main.MODID, "fluid_extractor"));
        GameRegistry.registerTileEntity(TileEntityDynamo.class, new ResourceLocation(Main.MODID, "dynamo"));
        GameRegistry.registerTileEntity(TileEntitySign.class, new ResourceLocation(Main.MODID, "sign"));

        if (Config.canolaSeedDrop) {
            MinecraftForge.addGrassSeed(new ItemStack(ModItems.CANOLA_SEEDS), 8);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Block block : ModBlocks.getBlocksWithItems()) {
            registerItemBlock(event.getRegistry(), block);
        }

        for (BlockPaint block : ModBlocks.PAINTS) {
            registerItemBlock(event.getRegistry(), block);
        }

        for (BlockPaint block : ModBlocks.YELLOW_PAINTS) {
            registerItemBlock(event.getRegistry(), block);
        }

        for (Item item : ModItems.getAll()) {
            registerItem(event.getRegistry(), item);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Block block : ModBlocks.getBlocksWithItems()) {
            addRenderBlock(block);
        }

        for (BlockPaint block : ModBlocks.PAINTS) {
            addRenderBlock(block);
        }

        for (BlockPaint block : ModBlocks.YELLOW_PAINTS) {
            addRenderBlock(block);
        }

        for (Item item : ModItems.getAll()) {
            addRenderItem(item);
        }

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

    @SubscribeEvent
    public static void capabilityAttach(AttachCapabilitiesEvent<TileEntity> event) {
        if (!(event.getObject() instanceof TileEntityBase)) {
            return;
        }
        if (event.getObject() instanceof IFluidHandler) {
            IFluidHandler handler = (IFluidHandler) event.getObject();
            event.addCapability(new ResourceLocation(Main.MODID, "fluid"), new ICapabilityProvider() {

                @Override
                public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                    if (capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked")
                @Override
                public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                    if (capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
                        return (T) handler;
                    }
                    return null;
                }
            });
        }
        if (event.getObject() instanceof IEnergyStorage) {
            IEnergyStorage handler = (IEnergyStorage) event.getObject();
            event.addCapability(new ResourceLocation(Main.MODID, "energy"), new ICapabilityProvider() {

                @Override
                public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                    if (capability.equals(CapabilityEnergy.ENERGY)) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked")
                @Override
                public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                    if (capability.equals(CapabilityEnergy.ENERGY)) {
                        return (T) handler;
                    }
                    return null;
                }
            });
        }


    }

}
