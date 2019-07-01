package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.*;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSign;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSplitTank;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererTank;
import de.maxhenkel.car.blocks.tileentity.render.TileentitySpecialRendererFuelStation;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.GenericCarModel;
import de.maxhenkel.car.events.KeyEvents;
import de.maxhenkel.car.events.PlayerEvents;
import de.maxhenkel.car.events.RenderEvents;
import de.maxhenkel.car.gui.ContainerGenerator;
import de.maxhenkel.car.net.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "car";

    public static SimpleChannel SIMPLE_CHANNEL;

    public static EntityType CAR_ENTITY_TYPE;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(SoundEvent.class, this::registerSounds);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, this::registerEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::registerContainers);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::registerTileEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::configEvent);

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
    public void configEvent(ModConfig.ModConfigEvent event) {
        if (event.getConfig().getType() == ModConfig.Type.SERVER) {
            Config.loadServer();
        } else if (event.getConfig().getType() == ModConfig.Type.CLIENT) {
            Config.loadClient();
        }
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new KeyEvents());
        MinecraftForge.EVENT_BUS.register(new RenderEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFuelStation.class, new TileentitySpecialRendererFuelStation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySplitTank.class, new TileEntitySpecialRendererSplitTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, new TileEntitySpecialRendererTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySign.class, new TileEntitySpecialRendererSign());


        SIMPLE_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(Main.MODID, "default"), () -> "1.0.0", s -> true, s -> true);
        SIMPLE_CHANNEL.registerMessage(0, MessageControlCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageControlCar().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(1, MessageCarGui.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCarGui().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(2, MessageStarting.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageStarting().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(3, MessageCrash.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCrash().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(4, MessageStartFuel.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageStartFuel().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(5, MessagePlaySoundLoop.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessagePlaySoundLoop().fromBytes(buf), (msg, fun) -> msg.executeClientSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(6, MessageSyncTileEntity.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageSyncTileEntity().fromBytes(buf), (msg, fun) -> msg.executeClientSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(7, MessageSpawnCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageSpawnCar().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(8, MessageOpenGui.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageOpenGui().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(9, MessageRepairCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageRepairCar().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(10, MessageCarHorn.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCarHorn().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(11, MessageEditSign.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageEditSign().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(12, MessageFuelStationAdminAmount.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageFuelStationAdminAmount().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(13, MessageCenterCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCenterCar().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(14, MessageCenterCar.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageCenterCar().fromBytes(buf), (msg, fun) -> msg.executeClientSide(fun.get()));
        SIMPLE_CHANNEL.registerMessage(15, MessageEditLicensePlate.class, (msg, buf) -> msg.toBytes(buf), (buf) -> new MessageEditLicensePlate().fromBytes(buf), (msg, fun) -> msg.executeServerSide(fun.get()));
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderEvents());


        // ScreenManager.IScreenFactory factory = (ScreenManager.IScreenFactory<ContainerAlbumInventory, AlbumInventoryScreen>) (container, playerInventory, name) -> new AlbumInventoryScreen(playerInventory, container, name);
        // ScreenManager.registerFactory(Main.ALBUM_INVENTORY_CONTAINER, factory);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                //FRAME_ITEM = new ImageFrameItem(),
        );
    }

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                //ModSounds.TAKE_IMAGE
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
        CAR_ENTITY_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "image_frame"));
        event.getRegistry().register(CAR_ENTITY_TYPE);
    }

    public static ContainerType<ContainerGenerator> GENERATOR_CONTAINER_TYPE;

    @SubscribeEvent
    public void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        GENERATOR_CONTAINER_TYPE = new ContainerType<>(ContainerGenerator::new);
        GENERATOR_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MODID, "album_inventory"));
        event.getRegistry().register(GENERATOR_CONTAINER_TYPE);
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
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        /*CRAFTING_SPECIAL_IMAGE_CLONING = new SpecialRecipeSerializer<>(RecipeImageCloning::new);
        CRAFTING_SPECIAL_IMAGE_CLONING.setRegistryName(MODID, "crafting_special_imagecloning");
        event.getRegistry().register(CRAFTING_SPECIAL_IMAGE_CLONING);*/
    }

}
