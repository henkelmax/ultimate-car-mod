package de.maxhenkel.car;

import com.google.common.collect.ImmutableSet;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.*;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSign;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSplitTank;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererTank;
import de.maxhenkel.car.blocks.tileentity.render.TileentitySpecialRendererGasStation;
import de.maxhenkel.car.commands.CommandCarDemo;
import de.maxhenkel.car.config.ClientConfig;
import de.maxhenkel.car.config.FuelConfig;
import de.maxhenkel.car.config.ServerConfig;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.GenericCarModel;
import de.maxhenkel.car.events.*;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.gui.*;
import de.maxhenkel.car.integration.IMC;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.loottable.CopyFluid;
import de.maxhenkel.car.net.*;
import de.maxhenkel.car.recipes.*;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.villagers.VillagerEvents;
import de.maxhenkel.corelib.ClientRegistry;
import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.corelib.config.DynamicConfig;
import de.maxhenkel.corelib.dataserializers.DataSerializerItemList;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.network.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "car";

    public static final Logger LOGGER = LogManager.getLogger(Main.MODID);

    public static SimpleChannel SIMPLE_CHANNEL;

    private static final DeferredRegister<EntityType<?>> ENTITY_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MODID);
    public static final RegistryObject<EntityType<EntityGenericCar>> CAR_ENTITY_TYPE = ENTITY_REGISTER.register("car", Main::createCarEntityType);

    public static LootItemFunctionType COPY_FLUID;

    private static final DeferredRegister<PoiType> POI_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.POI_TYPES, Main.MODID);
    public static final RegistryObject<PoiType> POINT_OF_INTEREST_TYPE_GAS_STATION_ATTENDANT = POI_TYPE_REGISTER.register("gas_station_attendant", () ->
            new PoiType(ImmutableSet.copyOf(ModBlocks.GAS_STATION.get().getStateDefinition().getPossibleStates()), 1, 1)
    );

    private static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION_REGISTER = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Main.MODID);
    public static final RegistryObject<VillagerProfession> VILLAGER_PROFESSION_GAS_STATION_ATTENDANT = VILLAGER_PROFESSION_REGISTER.register("gas_station_attendant", () ->
            new VillagerProfession("gas_station_attendant", poi -> poi.is(POINT_OF_INTEREST_TYPE_GAS_STATION_ATTENDANT.getKey()), poi -> poi.is(POINT_OF_INTEREST_TYPE_GAS_STATION_ATTENDANT.getKey()), ImmutableSet.of(), ImmutableSet.of(), ModSounds.GAS_STATION_ATTENDANT.get())
    );

    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Main.MODID);
    public static final RegistryObject<RecipeType<BlastFurnaceRecipe>> RECIPE_TYPE_BLAST_FURNACE = RECIPE_TYPE_REGISTER.register("blast_furnace", () ->
            new RecipeType<BlastFurnaceRecipe>() {
            }
    );
    public static final RegistryObject<RecipeType<OilMillRecipe>> RECIPE_TYPE_OIL_MILL = RECIPE_TYPE_REGISTER.register("oil_mill", () ->
            new RecipeType<OilMillRecipe>() {
            }
    );

    private static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Main.MODID);
    public static final RegistryObject<EntityDataSerializer<NonNullList<ItemStack>>> ITEM_LIST = ENTITY_DATA_SERIALIZER_REGISTER.register("serializer_item_list", () -> DataSerializerItemList.create());

    public static ServerConfig SERVER_CONFIG;
    public static FuelConfig FUEL_CONFIG;
    public static ClientConfig CLIENT_CONFIG;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(IMC::enqueueIMC);

        SERVER_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.SERVER, ServerConfig.class, true);
        FUEL_CONFIG = CommonRegistry.registerDynamicConfig(DynamicConfig.DynamicConfigType.SERVER, Main.MODID, "fuel", FuelConfig.class);
        CLIENT_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.CLIENT, ClientConfig.class);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Main.this::clientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Main.this::onRegisterKeyBinds);
        });

        ModFluids.init();
        ModBlocks.init();
        ModItems.init();
        ModSounds.init();
        ModCreativeTabs.init();

        MENU_TYPE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_TYPE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_SERIALIZER_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        POI_TYPE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        VILLAGER_PROFESSION_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_DATA_SERIALIZER_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandCarDemo.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new BlockEvents());

        MinecraftForge.EVENT_BUS.register(new VillagerEvents());

        COPY_FLUID = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, new ResourceLocation(Main.MODID, "copy_fluid"), new LootItemFunctionType(CopyFluid.CODEC));

        SIMPLE_CHANNEL = CommonRegistry.registerChannel(Main.MODID, "default");
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 0, MessageControlCar.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 1, MessageCarGui.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 2, MessageStarting.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 3, MessageCrash.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 4, MessageStartFuel.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 6, MessageSyncTileEntity.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 7, MessageSpawnCar.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 8, MessageOpenCarWorkshopGui.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 9, MessageRepairCar.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 10, MessageCarHorn.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 11, MessageEditSign.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 12, MessageGasStationAdminAmount.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 13, MessageCenterCar.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 14, MessageCenterCarClient.class);
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 15, MessageEditLicensePlate.class);

        ComposterBlock.COMPOSTABLES.put(ModItems.CANOLA_SEEDS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CANOLA_CAKE.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CANOLA.get(), 0.65F);

        Villager.WANTED_ITEMS = ImmutableSet.<Item>builder()
                .addAll(Villager.WANTED_ITEMS)
                .add(ModItems.CANOLA_SEEDS.get())
                .add(ModItems.CANOLA.get())
                .build();
    }

    public static KeyMapping FORWARD_KEY;
    public static KeyMapping BACK_KEY;
    public static KeyMapping LEFT_KEY;
    public static KeyMapping RIGHT_KEY;

    public static KeyMapping CAR_GUI_KEY;
    public static KeyMapping START_KEY;
    public static KeyMapping HORN_KEY;
    public static KeyMapping CENTER_KEY;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(GAS_STATION_TILE_ENTITY_TYPE.get(), TileentitySpecialRendererGasStation::new);
        BlockEntityRenderers.register(SPLIT_TANK_TILE_ENTITY_TYPE.get(), TileEntitySpecialRendererSplitTank::new);
        BlockEntityRenderers.register(TANK_TILE_ENTITY_TYPE.get(), TileEntitySpecialRendererTank::new);
        BlockEntityRenderers.register(SIGN_TILE_ENTITY_TYPE.get(), TileEntitySpecialRendererSign::new);

        ClientRegistry.<ContainerBackmixReactor, GuiBackmixReactor>registerScreen(Main.BACKMIX_REACTOR_CONTAINER_TYPE.get(), GuiBackmixReactor::new);
        ClientRegistry.<ContainerBlastFurnace, GuiBlastFurnace>registerScreen(Main.BLAST_FURNACE_CONTAINER_TYPE.get(), GuiBlastFurnace::new);
        ClientRegistry.<ContainerCar, GuiCar>registerScreen(Main.CAR_CONTAINER_TYPE.get(), GuiCar::new);
        ClientRegistry.<ContainerCarInventory, GuiCarInventory>registerScreen(Main.CAR_INVENTORY_CONTAINER_TYPE.get(), GuiCarInventory::new);
        ClientRegistry.<ContainerCarWorkshopCrafting, GuiCarWorkshopCrafting>registerScreen(Main.CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE.get(), GuiCarWorkshopCrafting::new);
        ClientRegistry.<ContainerCarWorkshopRepair, GuiCarWorkshopRepair>registerScreen(Main.CAR_WORKSHOP_REPAIR_CONTAINER_TYPE.get(), GuiCarWorkshopRepair::new);
        ClientRegistry.<ContainerFluidExtractor, GuiFluidExtractor>registerScreen(Main.FLUID_EXTRACTOR_CONTAINER_TYPE.get(), GuiFluidExtractor::new);
        ClientRegistry.<ContainerGasStation, GuiGasStation>registerScreen(Main.GAS_STATION_CONTAINER_TYPE.get(), GuiGasStation::new);
        ClientRegistry.<ContainerGasStationAdmin, GuiGasStationAdmin>registerScreen(Main.GAS_STATION_ADMIN_CONTAINER_TYPE.get(), GuiGasStationAdmin::new);
        ClientRegistry.<ContainerGenerator, GuiGenerator>registerScreen(Main.GENERATOR_CONTAINER_TYPE.get(), GuiGenerator::new);
        ClientRegistry.<ContainerLicensePlate, GuiLicensePlate>registerScreen(Main.LICENSE_PLATE_CONTAINER_TYPE.get(), GuiLicensePlate::new);
        ClientRegistry.<ContainerOilMill, GuiOilMill>registerScreen(Main.OIL_MILL_CONTAINER_TYPE.get(), GuiOilMill::new);
        ClientRegistry.<ContainerPainter, GuiPainter>registerScreen(Main.PAINTER_CONTAINER_TYPE.get(), GuiPainter::new);
        ClientRegistry.<ContainerSign, GuiSign>registerScreen(Main.SIGN_CONTAINER_TYPE.get(), GuiSign::new);
        ClientRegistry.<ContainerSplitTank, GuiSplitTank>registerScreen(Main.SPLIT_TANK_CONTAINER_TYPE.get(), GuiSplitTank::new);

        MinecraftForge.EVENT_BUS.register(new RenderEvents());
        MinecraftForge.EVENT_BUS.register(new SoundEvents());
        MinecraftForge.EVENT_BUS.register(new KeyEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());

        EntityRenderers.register(CAR_ENTITY_TYPE.get(), GenericCarModel::new);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRegisterKeyBinds(RegisterKeyMappingsEvent event) {
        FORWARD_KEY = new KeyMapping("key.car_forward", GLFW.GLFW_KEY_W, "category.car");
        BACK_KEY = new KeyMapping("key.car_back", GLFW.GLFW_KEY_S, "category.car");
        LEFT_KEY = new KeyMapping("key.car_left", GLFW.GLFW_KEY_A, "category.car");
        RIGHT_KEY = new KeyMapping("key.car_right", GLFW.GLFW_KEY_D, "category.car");
        CAR_GUI_KEY = new KeyMapping("key.car_gui", GLFW.GLFW_KEY_I, "category.car");
        START_KEY = new KeyMapping("key.car_start", GLFW.GLFW_KEY_R, "category.car");
        HORN_KEY = new KeyMapping("key.car_horn", GLFW.GLFW_KEY_H, "category.car");
        CENTER_KEY = new KeyMapping("key.center_car", GLFW.GLFW_KEY_SPACE, "category.car");

        event.register(FORWARD_KEY);
        event.register(BACK_KEY);
        event.register(LEFT_KEY);
        event.register(RIGHT_KEY);
        event.register(CAR_GUI_KEY);
        event.register(START_KEY);
        event.register(HORN_KEY);
        event.register(CENTER_KEY);
    }

    private static EntityType<EntityGenericCar> createCarEntityType() {
        return CommonRegistry.registerEntity(Main.MODID, "car", MobCategory.MISC, EntityGenericCar.class, builder -> {
            builder.setTrackingRange(128)
                    .setUpdateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(1F, 1F)
                    .setCustomClientFactory((spawnEntity, world) -> new EntityGenericCar(world));
        });
    }

    private static final DeferredRegister<MenuType<?>> MENU_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MODID);

    public static final RegistryObject<MenuType<ContainerBackmixReactor>> BACKMIX_REACTOR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("backmix_reactor", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerBackmixReactor, TileEntityBackmixReactor>) ContainerBackmixReactor::new))
    );
    public static final RegistryObject<MenuType<ContainerBlastFurnace>> BLAST_FURNACE_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("blast_furnace", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerBlastFurnace, TileEntityBlastFurnace>) ContainerBlastFurnace::new))
    );
    public static final RegistryObject<MenuType<ContainerCar>> CAR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car", () ->
            IForgeMenuType.create((IContainerFactory<ContainerCar>) (windowId, inv, data) -> {
                EntityGenericCar car = EntityTools.getCarByUUID(inv.player, data.readUUID());
                if (car == null) {
                    return null;
                }
                return new ContainerCar(windowId, car, inv);
            })
    );
    public static final RegistryObject<MenuType<ContainerCarInventory>> CAR_INVENTORY_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car_inventory", () ->
            IForgeMenuType.create((IContainerFactory<ContainerCarInventory>) (windowId, inv, data) -> {
                EntityGenericCar car = EntityTools.getCarByUUID(inv.player, data.readUUID());
                if (car == null) {
                    return null;
                }
                return new ContainerCarInventory(windowId, car, inv);
            })
    );
    public static final RegistryObject<MenuType<ContainerCarWorkshopCrafting>> CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car_workshop_crafting", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerCarWorkshopCrafting, TileEntityCarWorkshop>) ContainerCarWorkshopCrafting::new))
    );
    public static final RegistryObject<MenuType<ContainerCarWorkshopRepair>> CAR_WORKSHOP_REPAIR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car_workshop_repair", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerCarWorkshopRepair, TileEntityCarWorkshop>) ContainerCarWorkshopRepair::new))
    );
    public static final RegistryObject<MenuType<ContainerFluidExtractor>> FLUID_EXTRACTOR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("fluid_extractor", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerFluidExtractor, TileEntityFluidExtractor>) ContainerFluidExtractor::new))
    );
    public static final RegistryObject<MenuType<ContainerGasStation>> GAS_STATION_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("gas_station", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerGasStation, TileEntityGasStation>) ContainerGasStation::new))
    );
    public static final RegistryObject<MenuType<ContainerGasStationAdmin>> GAS_STATION_ADMIN_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("gas_station_admin", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerGasStationAdmin, TileEntityGasStation>) ContainerGasStationAdmin::new))
    );
    public static final RegistryObject<MenuType<ContainerGenerator>> GENERATOR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("generator", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerGenerator, TileEntityGenerator>) ContainerGenerator::new))
    );
    public static final RegistryObject<MenuType<ContainerLicensePlate>> LICENSE_PLATE_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("license_plate", () ->
            IForgeMenuType.create((windowId, inv, data) -> {
                ItemStack licensePlate = null;
                for (InteractionHand hand : InteractionHand.values()) {
                    ItemStack stack = inv.player.getItemInHand(hand);
                    if (stack.getItem() instanceof ItemLicensePlate) {
                        licensePlate = stack;
                        break;
                    }
                }
                if (licensePlate != null) {
                    return new ContainerLicensePlate(windowId, licensePlate);
                }
                return null;
            })
    );
    public static final RegistryObject<MenuType<ContainerOilMill>> OIL_MILL_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("oil_mill", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerOilMill, TileEntityOilMill>) ContainerOilMill::new))
    );
    public static final RegistryObject<MenuType<ContainerPainter>> PAINTER_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("painter", () ->
            IForgeMenuType.create((IContainerFactory<ContainerPainter>) (windowId, inv, data) -> new ContainerPainter(windowId, inv, data.readBoolean()))
    );
    public static final RegistryObject<MenuType<ContainerSign>> SIGN_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("sign", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerSign, TileEntitySign>) ContainerSign::new))
    );
    public static final RegistryObject<MenuType<ContainerSplitTank>> SPLIT_TANK_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("split_tank", () ->
            IForgeMenuType.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerSplitTank, TileEntitySplitTank>) ContainerSplitTank::new))
    );

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<BlockEntityType<TileEntityGenerator>> GENERATOR_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("generator", () ->
            BlockEntityType.Builder.of(TileEntityGenerator::new, ModBlocks.GENERATOR.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityBackmixReactor>> BACKMIX_REACTOR_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("backmix_reactor", () ->
            BlockEntityType.Builder.of(TileEntityBackmixReactor::new, ModBlocks.BACKMIX_REACTOR.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityBlastFurnace>> BLAST_FURNACE_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("blast_furnace", () ->
            BlockEntityType.Builder.of(TileEntityBlastFurnace::new, ModBlocks.BLAST_FURNACE.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityCable>> CABLE_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("cable", () ->
            BlockEntityType.Builder.of(TileEntityCable::new, ModBlocks.CABLE.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityCarWorkshop>> CAR_WORKSHOP_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("car_workshop", () ->
            BlockEntityType.Builder.of(TileEntityCarWorkshop::new, ModBlocks.CAR_WORKSHOP.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityDynamo>> DYNAMO_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("dynamo", () ->
            BlockEntityType.Builder.of(TileEntityDynamo::new, ModBlocks.DYNAMO.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityFluidExtractor>> FLUID_EXTRACTOR_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("fluid_extractor", () ->
            BlockEntityType.Builder.of(TileEntityFluidExtractor::new, ModBlocks.FLUID_EXTRACTOR.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityOilMill>> OIL_MILL_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("oil_mill", () ->
            BlockEntityType.Builder.of(TileEntityOilMill::new, ModBlocks.OIL_MILL.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntitySign>> SIGN_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("sign", () ->
            BlockEntityType.Builder.of(TileEntitySign::new, ModBlocks.SIGN.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntitySplitTank>> SPLIT_TANK_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("split_tank", () ->
            BlockEntityType.Builder.of(TileEntitySplitTank::new, ModBlocks.SPLIT_TANK.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityTank>> TANK_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("tank", () ->
            BlockEntityType.Builder.of(TileEntityTank::new, ModBlocks.TANK.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<TileEntityGasStation>> GAS_STATION_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("gas_station", () ->
            BlockEntityType.Builder.of(TileEntityGasStation::new, ModBlocks.GAS_STATION.get()).build(null)
    );

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MODID);
    public static final RegistryObject<RecipeSerializer<KeyRecipe>> CRAFTING_SPECIAL_KEY = RECIPE_SERIALIZER_REGISTER.register("crafting_special_key", () ->
            new SimpleCraftingRecipeSerializer<>(KeyRecipe::new)
    );
    public static final RegistryObject<RecipeSerializer<BlastFurnaceRecipe>> CRAFTING_BLAST_FURNACE = RECIPE_SERIALIZER_REGISTER.register("blast_furnace", () ->
            new RecipeSerializerBlastFurnace(BlastFurnaceRecipe::new)
    );
    public static final RegistryObject<RecipeSerializer<OilMillRecipe>> CRAFTING_OIL_MILL = RECIPE_SERIALIZER_REGISTER.register("oil_mill", () ->
            new RecipeSerializerOilMill(OilMillRecipe::new)
    );

}
