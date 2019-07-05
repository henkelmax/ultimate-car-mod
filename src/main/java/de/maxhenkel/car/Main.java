package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.*;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSign;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSplitTank;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererTank;
import de.maxhenkel.car.blocks.tileentity.render.TileentitySpecialRendererFuelStation;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.GenericCarModel;
import de.maxhenkel.car.events.*;
import de.maxhenkel.car.gui.*;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.loottable.CopyFluid;
import de.maxhenkel.car.net.*;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.lwjgl.glfw.GLFW;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "car";

    public static SimpleChannel SIMPLE_CHANNEL;

    public static EntityType CAR_ENTITY_TYPE;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    //TODO add grass seed
    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::registerBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(SoundEvent.class, this::registerSounds);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, this::registerEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::registerContainers);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::registerTileEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipes);
        //FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Fluid.class, this::registerFluids);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);

        DataSerializers.registerSerializer(DataSerializerStringList.STRING_LIST);
        DataSerializers.registerSerializer(DataSerializerItemList.ITEM_LIST);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            clientStart();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void clientStart() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Main.this::clientSetup);

        RenderingRegistry.registerEntityRenderingHandler(EntityGenericCar.class, manager -> new GenericCarModel(manager));
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());
        MinecraftForge.EVENT_BUS.register(new BlockEvents());


        LootFunctionManager.registerFunction(new CopyFluid.Serializer());

        SIMPLE_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(Main.MODID, "default"), () -> "1.0.0", s -> true, s -> true);
        SIMPLE_CHANNEL.registerMessage(0, MessageControlCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageControlCar().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(1, MessageCarGui.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCarGui().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(2, MessageStarting.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageStarting().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(3, MessageCrash.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCrash().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(4, MessageStartFuel.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageStartFuel().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(5, MessagePlaySoundLoop.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessagePlaySoundLoop().fromBytes(buf), (msg, fun) -> msg.executeClientSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(6, MessageSyncTileEntity.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageSyncTileEntity().fromBytes(buf), (msg, fun) -> msg.executeClientSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(7, MessageSpawnCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageSpawnCar().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(8, MessageOpenCarWorkshopGui.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageOpenCarWorkshopGui().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(9, MessageRepairCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageRepairCar().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(10, MessageCarHorn.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCarHorn().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(11, MessageEditSign.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageEditSign().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(12, MessageFuelStationAdminAmount.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageFuelStationAdminAmount().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(13, MessageCenterCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCenterCar().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(14, MessageCenterCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCenterCar().fromBytes(buf), (msg, fun) -> msg.executeClientSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(15, MessageEditLicensePlate.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageEditLicensePlate().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
    }

    public static KeyBinding FORWARD_KEY;
    public static KeyBinding BACK_KEY;
    public static KeyBinding LEFT_KEY;
    public static KeyBinding RIGHT_KEY;

    public static KeyBinding CAR_GUI_KEY;
    public static KeyBinding START_KEY;
    public static KeyBinding HORN_KEY;
    public static KeyBinding CENTER_KEY;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFuelStation.class, new TileentitySpecialRendererFuelStation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySplitTank.class, new TileEntitySpecialRendererSplitTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, new TileEntitySpecialRendererTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySign.class, new TileEntitySpecialRendererSign());

        ScreenManager.IScreenFactory factory1 = (ScreenManager.IScreenFactory<ContainerBackmixReactor, GuiBackmixReactor>) (container, playerInventory, name) -> new GuiBackmixReactor(container, playerInventory, name);
        ScreenManager.registerFactory(Main.BACKMIX_REACTOR_CONTAINER_TYPE, factory1);

        ScreenManager.IScreenFactory factory2 = (ScreenManager.IScreenFactory<ContainerBlastFurnace, GuiBlastFurnace>) (container, playerInventory, name) -> new GuiBlastFurnace(container, playerInventory, name);
        ScreenManager.registerFactory(Main.BLAST_FURNACE_CONTAINER_TYPE, factory2);

        ScreenManager.IScreenFactory factory3 = (ScreenManager.IScreenFactory<ContainerCar, GuiCar>) (container, playerInventory, name) -> new GuiCar(container, playerInventory, name);
        ScreenManager.registerFactory(Main.CAR_CONTAINER_TYPE, factory3);

        ScreenManager.IScreenFactory factory15 = (ScreenManager.IScreenFactory<ContainerCarInventory, GuiCarInventory>) (container, playerInventory, name) -> new GuiCarInventory(container, playerInventory, name);
        ScreenManager.registerFactory(Main.CAR_INVENTORY_CONTAINER_TYPE, factory15);

        ScreenManager.IScreenFactory factory4 = (ScreenManager.IScreenFactory<ContainerCarWorkshopCrafting, GuiCarWorkshopCrafting>) (container, playerInventory, name) -> new GuiCarWorkshopCrafting(container, playerInventory, name);
        ScreenManager.registerFactory(Main.CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE, factory4);

        ScreenManager.IScreenFactory factory5 = (ScreenManager.IScreenFactory<ContainerCarWorkshopRepair, GuiCarWorkshopRepair>) (container, playerInventory, name) -> new GuiCarWorkshopRepair(container, playerInventory, name);
        ScreenManager.registerFactory(Main.CAR_WORKSHOP_REPAIR_CONTAINER_TYPE, factory5);

        ScreenManager.IScreenFactory factory6 = (ScreenManager.IScreenFactory<ContainerFluidExtractor, GuiFluidExtractor>) (container, playerInventory, name) -> new GuiFluidExtractor(container, playerInventory, name);
        ScreenManager.registerFactory(Main.FLUID_EXTRACTOR_CONTAINER_TYPE, factory6);

        ScreenManager.IScreenFactory factory7 = (ScreenManager.IScreenFactory<ContainerFuelStation, GuiFuelStation>) (container, playerInventory, name) -> new GuiFuelStation(container, playerInventory, name);
        ScreenManager.registerFactory(Main.FUEL_STATION_CONTAINER_TYPE, factory7);

        ScreenManager.IScreenFactory factory8 = (ScreenManager.IScreenFactory<ContainerFuelStationAdmin, GuiFuelStationAdmin>) (container, playerInventory, name) -> new GuiFuelStationAdmin(container, playerInventory, name);
        ScreenManager.registerFactory(Main.FUEL_STATION_ADMIN_CONTAINER_TYPE, factory8);

        ScreenManager.IScreenFactory factory9 = (ScreenManager.IScreenFactory<ContainerGenerator, GuiGenerator>) (container, playerInventory, name) -> new GuiGenerator(container, playerInventory, name);
        ScreenManager.registerFactory(Main.GENERATOR_CONTAINER_TYPE, factory9);

        ScreenManager.IScreenFactory factory10 = (ScreenManager.IScreenFactory<ContainerLicensePlate, GuiLicensePlate>) (container, playerInventory, name) -> new GuiLicensePlate(container, playerInventory, name);
        ScreenManager.registerFactory(Main.LICENSE_PLATE_CONTAINER_TYPE, factory10);

        ScreenManager.IScreenFactory factory11 = (ScreenManager.IScreenFactory<ContainerOilMill, GuiOilMill>) (container, playerInventory, name) -> new GuiOilMill(container, playerInventory, name);
        ScreenManager.registerFactory(Main.OIL_MILL_CONTAINER_TYPE, factory11);

        ScreenManager.IScreenFactory factory12 = (ScreenManager.IScreenFactory<ContainerPainter, GuiPainter>) (container, playerInventory, name) -> new GuiPainter(container, playerInventory, name);
        ScreenManager.registerFactory(Main.PAINTER_CONTAINER_TYPE, factory12);

        ScreenManager.IScreenFactory factory13 = (ScreenManager.IScreenFactory<ContainerSign, GuiSign>) (container, playerInventory, name) -> new GuiSign(container, name);
        ScreenManager.registerFactory(Main.SIGN_CONTAINER_TYPE, factory13);

        ScreenManager.IScreenFactory factory14 = (ScreenManager.IScreenFactory<ContainerSplitTank, GuiSplitTank>) (container, playerInventory, name) -> new GuiSplitTank(container, playerInventory, name);
        ScreenManager.registerFactory(Main.SPLIT_TANK_CONTAINER_TYPE, factory14);


        this.FORWARD_KEY = new KeyBinding("key.car_forward", GLFW.GLFW_KEY_W, "category.car");
        ClientRegistry.registerKeyBinding(FORWARD_KEY);

        this.BACK_KEY = new KeyBinding("key.car_back", GLFW.GLFW_KEY_S, "category.car");
        ClientRegistry.registerKeyBinding(BACK_KEY);

        this.LEFT_KEY = new KeyBinding("key.car_left", GLFW.GLFW_KEY_A, "category.car");
        ClientRegistry.registerKeyBinding(LEFT_KEY);

        this.RIGHT_KEY = new KeyBinding("key.car_right", GLFW.GLFW_KEY_D, "category.car");
        ClientRegistry.registerKeyBinding(RIGHT_KEY);

        this.CAR_GUI_KEY = new KeyBinding("key.car_gui", GLFW.GLFW_KEY_I, "category.car");
        ClientRegistry.registerKeyBinding(CAR_GUI_KEY);

        this.START_KEY = new KeyBinding("key.car_start", GLFW.GLFW_KEY_R, "category.car");
        ClientRegistry.registerKeyBinding(START_KEY);

        this.HORN_KEY = new KeyBinding("key.car_horn", GLFW.GLFW_KEY_H, "category.car");
        ClientRegistry.registerKeyBinding(HORN_KEY);

        this.CENTER_KEY = new KeyBinding("key.center_car", GLFW.GLFW_KEY_SPACE, "category.car");
        ClientRegistry.registerKeyBinding(CENTER_KEY);

        MinecraftForge.EVENT_BUS.register(new RenderEvents());
        MinecraftForge.EVENT_BUS.register(new KeyEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ModBlocks.getBlocksWithItems().stream().map(block -> block.toItem()).toArray(Item[]::new)
        );

        event.getRegistry().registerAll(
                ModItems.getAll().toArray(new Item[0])
        );
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                ModBlocks.getAll().toArray(new Block[0])
        );
    }

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                ModSounds.getAll().toArray(new SoundEvent[0])
        );
    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        CAR_ENTITY_TYPE = EntityType.Builder.<EntityGenericCar>create(EntityGenericCar::new, EntityClassification.MISC)
                .setTrackingRange(128)
                .setUpdateInterval(1)
                .setShouldReceiveVelocityUpdates(true)
                .size(1F, 1F)
                .setCustomClientFactory((spawnEntity, world) -> new EntityGenericCar(world))
                .build(Main.MODID + ":car");
        CAR_ENTITY_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "car"));
        event.getRegistry().register(CAR_ENTITY_TYPE);
    }

    public static ContainerType<ContainerBackmixReactor> BACKMIX_REACTOR_CONTAINER_TYPE;
    public static ContainerType<ContainerBlastFurnace> BLAST_FURNACE_CONTAINER_TYPE;
    public static ContainerType<ContainerCar> CAR_CONTAINER_TYPE;
    public static ContainerType<ContainerCarInventory> CAR_INVENTORY_CONTAINER_TYPE;
    public static ContainerType<ContainerCarWorkshopCrafting> CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE;
    public static ContainerType<ContainerCarWorkshopRepair> CAR_WORKSHOP_REPAIR_CONTAINER_TYPE;
    public static ContainerType<ContainerFluidExtractor> FLUID_EXTRACTOR_CONTAINER_TYPE;
    public static ContainerType<ContainerFuelStation> FUEL_STATION_CONTAINER_TYPE;
    public static ContainerType<ContainerFuelStationAdmin> FUEL_STATION_ADMIN_CONTAINER_TYPE;
    public static ContainerType<ContainerGenerator> GENERATOR_CONTAINER_TYPE;
    public static ContainerType<ContainerLicensePlate> LICENSE_PLATE_CONTAINER_TYPE;
    public static ContainerType<ContainerOilMill> OIL_MILL_CONTAINER_TYPE;
    public static ContainerType<ContainerPainter> PAINTER_CONTAINER_TYPE;
    public static ContainerType<ContainerSign> SIGN_CONTAINER_TYPE;
    public static ContainerType<ContainerSplitTank> SPLIT_TANK_CONTAINER_TYPE;

    @SubscribeEvent
    public void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        BACKMIX_REACTOR_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerBackmixReactor, TileEntityBackmixReactor>) ContainerBackmixReactor::new));
        BACKMIX_REACTOR_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "backmix_reactor"));
        event.getRegistry().register(BACKMIX_REACTOR_CONTAINER_TYPE);

        BLAST_FURNACE_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerBlastFurnace, TileEntityBlastFurnace>) ContainerBlastFurnace::new));
        BLAST_FURNACE_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "blast_furnace"));
        event.getRegistry().register(BLAST_FURNACE_CONTAINER_TYPE);

        CAR_CONTAINER_TYPE = new ContainerType<>((IContainerFactory<ContainerCar>) (windowId, inv, data) -> {
            EntityGenericCar car = EntityTools.getCarByUUID(inv.player, data.readUniqueId());
            if (car == null) {
                return null;
            }
            return new ContainerCar(windowId, car, inv);
        });
        CAR_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "car"));
        event.getRegistry().register(CAR_CONTAINER_TYPE);

        CAR_INVENTORY_CONTAINER_TYPE = new ContainerType<>((IContainerFactory<ContainerCarInventory>) (windowId, inv, data) -> {
            EntityGenericCar car = EntityTools.getCarByUUID(inv.player, data.readUniqueId());
            if (car == null) {
                return null;
            }
            return new ContainerCarInventory(windowId, car, inv);
        });
        CAR_INVENTORY_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "car_inventory"));
        event.getRegistry().register(CAR_INVENTORY_CONTAINER_TYPE);

        CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerCarWorkshopCrafting, TileEntityCarWorkshop>) ContainerCarWorkshopCrafting::new));
        CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "car_workshop_crafting"));
        event.getRegistry().register(CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE);

        CAR_WORKSHOP_REPAIR_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerCarWorkshopRepair, TileEntityCarWorkshop>) ContainerCarWorkshopRepair::new));
        CAR_WORKSHOP_REPAIR_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "car_workshop_repair"));
        event.getRegistry().register(CAR_WORKSHOP_REPAIR_CONTAINER_TYPE);

        FLUID_EXTRACTOR_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerFluidExtractor, TileEntityFluidExtractor>) ContainerFluidExtractor::new));
        FLUID_EXTRACTOR_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "fluid_extractor"));
        event.getRegistry().register(FLUID_EXTRACTOR_CONTAINER_TYPE);

        FUEL_STATION_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerFuelStation, TileEntityFuelStation>) ContainerFuelStation::new));
        FUEL_STATION_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "fuel_station"));
        event.getRegistry().register(FUEL_STATION_CONTAINER_TYPE);

        FUEL_STATION_ADMIN_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerFuelStationAdmin, TileEntityFuelStation>) ContainerFuelStationAdmin::new));
        FUEL_STATION_ADMIN_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "fuel_station_admin"));
        event.getRegistry().register(FUEL_STATION_ADMIN_CONTAINER_TYPE);

        GENERATOR_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerGenerator, TileEntityGenerator>) ContainerGenerator::new));
        GENERATOR_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "generator"));
        event.getRegistry().register(GENERATOR_CONTAINER_TYPE);

        LICENSE_PLATE_CONTAINER_TYPE = new ContainerType<>((id, inv) -> {
            ItemStack licensePlate = null;
            for (Hand hand : Hand.values()) {
                ItemStack stack = inv.player.getHeldItem(hand);
                if (stack.getItem() instanceof ItemLicensePlate) {
                    licensePlate = stack;
                    break;
                }
            }
            if (licensePlate != null) {
                return new ContainerLicensePlate(id, licensePlate);
            }
            return null;
        });
        LICENSE_PLATE_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "license_plate"));
        event.getRegistry().register(LICENSE_PLATE_CONTAINER_TYPE);

        OIL_MILL_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerOilMill, TileEntityOilMill>) ContainerOilMill::new));
        OIL_MILL_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "oil_mill"));
        event.getRegistry().register(OIL_MILL_CONTAINER_TYPE);

        PAINTER_CONTAINER_TYPE = new ContainerType<>((IContainerFactory<ContainerPainter>) (windowId, inv, data) -> new ContainerPainter(windowId, inv, data.readBoolean()));
        PAINTER_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "painter"));
        event.getRegistry().register(PAINTER_CONTAINER_TYPE);

        SIGN_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerSign, TileEntitySign>) (windowId, tileEntity, inv) -> new ContainerSign(windowId, tileEntity)));
        SIGN_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "sign"));
        event.getRegistry().register(SIGN_CONTAINER_TYPE);

        SPLIT_TANK_CONTAINER_TYPE = new ContainerType<>(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerSplitTank, TileEntitySplitTank>) ContainerSplitTank::new));
        SPLIT_TANK_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "split_tank"));
        event.getRegistry().register(SPLIT_TANK_CONTAINER_TYPE);
    }

    public static TileEntityType GENERATOR_TILE_ENTITY_TYPE;
    public static TileEntityType BACKMIX_REACTOR_TILE_ENTITY_TYPE;
    public static TileEntityType BLAST_FURNACE_TILE_ENTITY_TYPE;
    public static TileEntityType CABLE_TILE_ENTITY_TYPE;
    public static TileEntityType CAR_WORKSHOP_TILE_ENTITY_TYPE;
    public static TileEntityType DYNAMO_TILE_ENTITY_TYPE;
    public static TileEntityType FLUID_EXTRACTOR_TILE_ENTITY_TYPE;
    public static TileEntityType OIL_MILL_TILE_ENTITY_TYPE;
    public static TileEntityType SIGN_TILE_ENTITY_TYPE;
    public static TileEntityType SPLIT_TANK_TILE_ENTITY_TYPE;
    public static TileEntityType TANK_TILE_ENTITY_TYPE;
    public static TileEntityType FUEL_STATION_TILE_ENTITY_TYPE;

    @SubscribeEvent
    public void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        GENERATOR_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityGenerator::new, ModBlocks.GENERATOR).build(null);
        GENERATOR_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "generator"));
        event.getRegistry().register(GENERATOR_TILE_ENTITY_TYPE);

        BACKMIX_REACTOR_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityBackmixReactor::new, ModBlocks.BACKMIX_REACTOR).build(null);
        BACKMIX_REACTOR_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "backmix_reactor"));
        event.getRegistry().register(BACKMIX_REACTOR_TILE_ENTITY_TYPE);

        BLAST_FURNACE_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityBlastFurnace::new, ModBlocks.BLAST_FURNACE).build(null);
        BLAST_FURNACE_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "blast_furnace"));
        event.getRegistry().register(BLAST_FURNACE_TILE_ENTITY_TYPE);

        CABLE_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityCable::new, ModBlocks.CABLE).build(null);
        CABLE_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "cable"));
        event.getRegistry().register(CABLE_TILE_ENTITY_TYPE);

        CAR_WORKSHOP_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityCarWorkshop::new, ModBlocks.CAR_WORKSHOP).build(null);
        CAR_WORKSHOP_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "car_workshop"));
        event.getRegistry().register(CAR_WORKSHOP_TILE_ENTITY_TYPE);

        DYNAMO_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityDynamo::new, ModBlocks.DYNAMO).build(null);
        DYNAMO_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "dynamo"));
        event.getRegistry().register(DYNAMO_TILE_ENTITY_TYPE);

        FLUID_EXTRACTOR_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityFluidExtractor::new, ModBlocks.FLUID_EXTRACTOR).build(null);
        FLUID_EXTRACTOR_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "fluid_extractor"));
        event.getRegistry().register(FLUID_EXTRACTOR_TILE_ENTITY_TYPE);

        OIL_MILL_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityOilMill::new, ModBlocks.OIL_MILL).build(null);
        OIL_MILL_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "oil_mill"));
        event.getRegistry().register(OIL_MILL_TILE_ENTITY_TYPE);

        SIGN_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntitySign::new, ModBlocks.SIGN).build(null);
        SIGN_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "sign"));
        event.getRegistry().register(SIGN_TILE_ENTITY_TYPE);

        SPLIT_TANK_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntitySplitTank::new, ModBlocks.SPLIT_TANK).build(null);
        SPLIT_TANK_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "split_tank"));
        event.getRegistry().register(SPLIT_TANK_TILE_ENTITY_TYPE);

        TANK_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityTank::new, ModBlocks.TANK).build(null);
        TANK_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "tank"));
        event.getRegistry().register(TANK_TILE_ENTITY_TYPE);

        FUEL_STATION_TILE_ENTITY_TYPE = TileEntityType.Builder.create(TileEntityFuelStation::new, ModBlocks.FUEL_STATION).build(null);
        FUEL_STATION_TILE_ENTITY_TYPE.setRegistryName(new ResourceLocation(MODID, "fuel_station"));
        event.getRegistry().register(FUEL_STATION_TILE_ENTITY_TYPE);
    }

    public static IRecipeSerializer CRAFTING_SPECIAL_KEY;

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CRAFTING_SPECIAL_KEY = new SpecialRecipeSerializer<>(ReciepeKey::new);
        CRAFTING_SPECIAL_KEY.setRegistryName(new ResourceLocation(MODID, "crafting_special_key"));
        event.getRegistry().register(CRAFTING_SPECIAL_KEY);
    }

    /*@SubscribeEvent
    public void registerFluids(RegistryEvent.Register<Fluid> event) {
        event.getRegistry().registerAll(
                ModFluids.CANOLA_OIL
        );
    }*/

}
