package de.maxhenkel.car;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.*;
import de.maxhenkel.car.commands.CommandCarDemo;
import de.maxhenkel.car.config.ClientConfig;
import de.maxhenkel.car.config.FuelConfig;
import de.maxhenkel.car.config.ServerConfig;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import de.maxhenkel.car.events.*;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.gui.*;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.loottable.CopyFluid;
import de.maxhenkel.car.net.*;
import de.maxhenkel.car.recipes.*;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.villagers.VillagerEvents;
import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.corelib.config.DynamicConfig;
import de.maxhenkel.corelib.dataserializers.DataSerializerItemList;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.transfer.fluid.BucketResourceHandler;
import net.neoforged.neoforge.transfer.fluid.ItemAccessFluidHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod(CarMod.MODID)
@EventBusSubscriber(modid = CarMod.MODID)
public class CarMod {

    public static final String MODID = "car";

    public static final Logger LOGGER = LogManager.getLogger(CarMod.MODID);

    private static final DeferredRegister<EntityType<?>> ENTITY_REGISTER = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CarMod.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityGenericCar>> CAR_ENTITY_TYPE = ENTITY_REGISTER.register("car", () -> {
        return CommonRegistry.registerEntity(CarMod.MODID, "car", MobCategory.MISC, EntityGenericCar.class, builder -> {
            builder.setTrackingRange(128)
                    .setUpdateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(1F, 1F);
        });
    });

    private static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, CarMod.MODID);
    public static DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<CopyFluid>> COPY_FLUID = LOOT_FUNCTION_TYPE_REGISTER.register("copy_fluid", () -> new LootItemFunctionType(CopyFluid.CODEC));

    private static final DeferredRegister<PoiType> POI_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, CarMod.MODID);
    public static final DeferredHolder<PoiType, PoiType> POINT_OF_INTEREST_TYPE_GAS_STATION_ATTENDANT = POI_TYPE_REGISTER.register("gas_station_attendant", () ->
            new PoiType(ImmutableSet.copyOf(ModBlocks.GAS_STATION.get().getStateDefinition().getPossibleStates()), 1, 1)
    );

    private static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION_REGISTER = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, CarMod.MODID);
    //TODO Rename translation key
    //TODO Fix villagers not having trades
    public static final DeferredHolder<VillagerProfession, VillagerProfession> VILLAGER_PROFESSION_GAS_STATION_ATTENDANT = VILLAGER_PROFESSION_REGISTER.register("gas_station_attendant", () ->
            new VillagerProfession(Component.translatable("entity.minecraft.villager.car.gas_station_attendant"), poi -> poi.is(POINT_OF_INTEREST_TYPE_GAS_STATION_ATTENDANT.getKey()), poi -> poi.is(POINT_OF_INTEREST_TYPE_GAS_STATION_ATTENDANT.getKey()), ImmutableSet.of(), ImmutableSet.of(), ModSounds.GAS_STATION_ATTENDANT.get())
    );

    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, CarMod.MODID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<BlastFurnaceRecipe>> RECIPE_TYPE_BLAST_FURNACE = RECIPE_TYPE_REGISTER.register("blast_furnace", () ->
            RecipeType.simple(Identifier.fromNamespaceAndPath(CarMod.MODID, "blast_furnace"))
    );
    public static final DeferredHolder<RecipeType<?>, RecipeType<OilMillRecipe>> RECIPE_TYPE_OIL_MILL = RECIPE_TYPE_REGISTER.register("oil_mill", () ->
            RecipeType.simple(Identifier.fromNamespaceAndPath(CarMod.MODID, "oil_mill"))
    );

    private static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER_REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, CarMod.MODID);
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<NonNullList<ItemStack>>> ITEM_LIST = ENTITY_DATA_SERIALIZER_REGISTER.register("serializer_item_list", () -> DataSerializerItemList.create());

    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, CarMod.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FUEL_DATA_COMPONENT = DATA_COMPONENT_TYPE_REGISTER.register("fuel", () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());
    //TODO Add the tooltip if this component is present
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_STACK_DATA_COMPONENT = DATA_COMPONENT_TYPE_REGISTER.register("fluid", () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> TRADING_ITEM_DATA_COMPONENT = DATA_COMPONENT_TYPE_REGISTER.register("trading_item", () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> PAINTER_INDEX_DATA_COMPONENT = DATA_COMPONENT_TYPE_REGISTER.register("index", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> CAR_UUID_DATA_COMPONENT = DATA_COMPONENT_TYPE_REGISTER.register("car", () -> DataComponentType.<UUID>builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> LICENSE_PLATE_TEXT_DATA_COMPONENT = DATA_COMPONENT_TYPE_REGISTER.register("plate_text", () -> DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static ServerConfig SERVER_CONFIG;
    public static FuelConfig FUEL_CONFIG;
    public static ClientConfig CLIENT_CONFIG;

    public CarMod(IEventBus eventBus) {
        SERVER_CONFIG = CommonRegistry.registerConfig(MODID, ModConfig.Type.SERVER, ServerConfig.class, true);
        FUEL_CONFIG = CommonRegistry.registerDynamicConfig(DynamicConfig.DynamicConfigType.SERVER, CarMod.MODID, "fuel", FuelConfig.class);
        CLIENT_CONFIG = CommonRegistry.registerConfig(MODID, ModConfig.Type.CLIENT, ClientConfig.class);

        ModFluids.init(eventBus);
        ModBlocks.init(eventBus);
        ModItems.init(eventBus);
        ModSounds.init(eventBus);
        ModCreativeTabs.init(eventBus);

        ENTITY_REGISTER.register(eventBus);
        MENU_TYPE_REGISTER.register(eventBus);
        BLOCK_ENTITY_REGISTER.register(eventBus);
        LOOT_FUNCTION_TYPE_REGISTER.register(eventBus);
        RECIPE_TYPE_REGISTER.register(eventBus);
        RECIPE_SERIALIZER_REGISTER.register(eventBus);
        POI_TYPE_REGISTER.register(eventBus);
        VILLAGER_PROFESSION_REGISTER.register(eventBus);
        ENTITY_DATA_SERIALIZER_REGISTER.register(eventBus);
        DATA_COMPONENT_TYPE_REGISTER.register(eventBus);
    }

    @SubscribeEvent
    static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandCarDemo.register(event.getDispatcher());
    }

    @SubscribeEvent
    static void commonSetup(FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(new BlockEvents());

        NeoForge.EVENT_BUS.register(new VillagerEvents());

        ComposterBlock.COMPOSTABLES.put(ModItems.CANOLA_SEEDS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CANOLA_CAKE.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CANOLA.get(), 0.65F);
    }

    @SubscribeEvent
    static void onRegisterPayloadHandler(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("0");
        CommonRegistry.registerMessage(registrar, MessageControlCar.class);
        CommonRegistry.registerMessage(registrar, MessageCarGui.class);
        CommonRegistry.registerMessage(registrar, MessageStarting.class);
        CommonRegistry.registerMessage(registrar, MessageCrash.class);
        CommonRegistry.registerMessage(registrar, MessageStartFuel.class);
        CommonRegistry.registerMessage(registrar, MessageSyncTileEntity.class);
        CommonRegistry.registerMessage(registrar, MessageSpawnCar.class);
        CommonRegistry.registerMessage(registrar, MessageOpenCarWorkshopGui.class);
        CommonRegistry.registerMessage(registrar, MessageRepairCar.class);
        CommonRegistry.registerMessage(registrar, MessageCarHorn.class);
        CommonRegistry.registerMessage(registrar, MessageEditSign.class);
        CommonRegistry.registerMessage(registrar, MessageGasStationAdminAmount.class);
        CommonRegistry.registerMessage(registrar, MessageCenterCar.class);
        CommonRegistry.registerMessage(registrar, MessageCenterCarClient.class);
        CommonRegistry.registerMessage(registrar, MessageEditLicensePlate.class);
    }

    @SubscribeEvent
    static void onItemTooltip(ItemTooltipEvent event) {
        SimpleFluidContent fluid = event.getItemStack().get(FLUID_STACK_DATA_COMPONENT);
        if (fluid != null) {
            //TODO Don't copy
            FluidStack fluidStack = fluid.copy();
            //TODO Properly sort tooltip lines
            event.getToolTip().add(1, Component.translatable("tooltip.fluid", Component.literal(fluidStack.getHoverName().getString()).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(2, Component.translatable("tooltip.amount", Component.literal(String.valueOf(fluidStack.getAmount())).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        }
    }

    private static final DeferredRegister<MenuType<?>> MENU_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.MENU, CarMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ContainerBackmixReactor>> BACKMIX_REACTOR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("backmix_reactor", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerBackmixReactor, TileEntityBackmixReactor>) ContainerBackmixReactor::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerBlastFurnace>> BLAST_FURNACE_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("blast_furnace", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerBlastFurnace, TileEntityBlastFurnace>) ContainerBlastFurnace::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerCar>> CAR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car", () ->
            IMenuTypeExtension.create((IContainerFactory<ContainerCar>) (windowId, inv, data) -> {
                EntityGenericCar car = EntityTools.getCarByUUID(inv.player, data.readUUID());
                if (car == null) {
                    return null;
                }
                return new ContainerCar(windowId, car, inv);
            })
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerCarInventory>> CAR_INVENTORY_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car_inventory", () ->
            IMenuTypeExtension.create((IContainerFactory<ContainerCarInventory>) (windowId, inv, data) -> {
                EntityGenericCar car = EntityTools.getCarByUUID(inv.player, data.readUUID());
                if (car == null) {
                    return null;
                }
                return new ContainerCarInventory(windowId, car, inv);
            })
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerCarWorkshopCrafting>> CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car_workshop_crafting", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerCarWorkshopCrafting, TileEntityCarWorkshop>) ContainerCarWorkshopCrafting::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerCarWorkshopRepair>> CAR_WORKSHOP_REPAIR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("car_workshop_repair", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerCarWorkshopRepair, TileEntityCarWorkshop>) ContainerCarWorkshopRepair::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerFluidExtractor>> FLUID_EXTRACTOR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("fluid_extractor", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerFluidExtractor, TileEntityFluidExtractor>) ContainerFluidExtractor::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerGasStation>> GAS_STATION_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("gas_station", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerGasStation, TileEntityGasStation>) ContainerGasStation::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerGasStationAdmin>> GAS_STATION_ADMIN_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("gas_station_admin", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerGasStationAdmin, TileEntityGasStation>) ContainerGasStationAdmin::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerGenerator>> GENERATOR_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("generator", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerGenerator, TileEntityGenerator>) ContainerGenerator::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerLicensePlate>> LICENSE_PLATE_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("license_plate", () ->
            IMenuTypeExtension.create((windowId, inv, data) -> {
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
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerOilMill>> OIL_MILL_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("oil_mill", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerOilMill, TileEntityOilMill>) ContainerOilMill::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerPainter>> PAINTER_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("painter", () ->
            IMenuTypeExtension.create((IContainerFactory<ContainerPainter>) (windowId, inv, data) -> new ContainerPainter(windowId, inv, data.readBoolean()))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerSign>> SIGN_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("sign", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerSign, TileEntitySign>) ContainerSign::new))
    );
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerSplitTank>> SPLIT_TANK_CONTAINER_TYPE = MENU_TYPE_REGISTER.register("split_tank", () ->
            IMenuTypeExtension.create(new ContainerFactoryTileEntity((ContainerFactoryTileEntity.ContainerCreator<ContainerSplitTank, TileEntitySplitTank>) ContainerSplitTank::new))
    );

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CarMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityGenerator>> GENERATOR_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("generator", () ->
            new BlockEntityType<>(TileEntityGenerator::new, ModBlocks.GENERATOR.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityBackmixReactor>> BACKMIX_REACTOR_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("backmix_reactor", () ->
            new BlockEntityType<>(TileEntityBackmixReactor::new, ModBlocks.BACKMIX_REACTOR.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityBlastFurnace>> BLAST_FURNACE_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("blast_furnace", () ->
            new BlockEntityType<>(TileEntityBlastFurnace::new, ModBlocks.BLAST_FURNACE.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityCable>> CABLE_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("cable", () ->
            new BlockEntityType<>(TileEntityCable::new, ModBlocks.CABLE.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityCarWorkshop>> CAR_WORKSHOP_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("car_workshop", () ->
            new BlockEntityType<>(TileEntityCarWorkshop::new, ModBlocks.CAR_WORKSHOP.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityDynamo>> DYNAMO_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("dynamo", () ->
            new BlockEntityType<>(TileEntityDynamo::new, ModBlocks.DYNAMO.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityFluidExtractor>> FLUID_EXTRACTOR_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("fluid_extractor", () ->
            new BlockEntityType<>(TileEntityFluidExtractor::new, ModBlocks.FLUID_EXTRACTOR.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityOilMill>> OIL_MILL_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("oil_mill", () ->
            new BlockEntityType<>(TileEntityOilMill::new, ModBlocks.OIL_MILL.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntitySign>> SIGN_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("sign", () ->
            new BlockEntityType<>(TileEntitySign::new, ModBlocks.SIGN.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntitySplitTank>> SPLIT_TANK_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("split_tank", () ->
            new BlockEntityType<>(TileEntitySplitTank::new, ModBlocks.SPLIT_TANK.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityTank>> TANK_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("tank", () ->
            new BlockEntityType<>(TileEntityTank::new, ModBlocks.TANK.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileEntityGasStation>> GAS_STATION_TILE_ENTITY_TYPE = BLOCK_ENTITY_REGISTER.register("gas_station", () ->
            new BlockEntityType<>(TileEntityGasStation::new, ModBlocks.GAS_STATION.get())
    );

    @SubscribeEvent
    static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(ModFluids.BIO_DIESEL_TYPE.get().getExtensions(), ModFluids.BIO_DIESEL_TYPE.get());
        event.registerFluidType(ModFluids.CANOLA_OIL_TYPE.get().getExtensions(), ModFluids.CANOLA_OIL_TYPE.get());
        event.registerFluidType(ModFluids.METHANOL_TYPE.get().getExtensions(), ModFluids.METHANOL_TYPE.get());
        event.registerFluidType(ModFluids.GLYCERIN_TYPE.get().getExtensions(), ModFluids.GLYCERIN_TYPE.get());
        event.registerFluidType(ModFluids.CANOLA_METHANOL_MIX_TYPE.get().getExtensions(), ModFluids.CANOLA_METHANOL_MIX_TYPE.get());
    }

    @SubscribeEvent
    static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        registerBlockCapabilities(event, GENERATOR_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, BACKMIX_REACTOR_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, BLAST_FURNACE_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, CABLE_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, CAR_WORKSHOP_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, DYNAMO_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, FLUID_EXTRACTOR_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, OIL_MILL_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, SIGN_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, SPLIT_TANK_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, TANK_TILE_ENTITY_TYPE);
        registerBlockCapabilities(event, GAS_STATION_TILE_ENTITY_TYPE);

        event.registerItem(Capabilities.Fluid.ITEM, (object, context) -> context == null ? null : new BucketResourceHandler(context),
                ModItems.BIO_DIESEL_BUCKET.get(),
                ModItems.CANOLA_OIL_BUCKET.get(),
                ModItems.METHANOL_BUCKET.get(),
                ModItems.GLYCERIN_BUCKET.get(),
                ModItems.CANOLA_METHANOL_MIX_BUCKET.get()
        );

        event.registerItem(Capabilities.Fluid.ITEM, (object, context) -> context == null ? null : new ItemAccessFluidHandler(context, FUEL_DATA_COMPONENT.get(), CarMod.SERVER_CONFIG.canisterMaxFuel.get()),
                ModItems.CANISTER.get()
        );

        event.registerItem(Capabilities.Energy.ITEM, (object, context) -> ModItems.BATTERY.get().getEnergyHandler(object), ModItems.BATTERY.get());

        registerEntityCapabilities(event, CAR_ENTITY_TYPE);
    }

    private static <T extends TileEntityBase> void registerBlockCapabilities(RegisterCapabilitiesEvent event, DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> holder) {
        event.registerBlockEntity(Capabilities.Fluid.BLOCK, holder.get(), (object, context) -> object.getFluidHandler());
        event.registerBlockEntity(Capabilities.Energy.BLOCK, holder.get(), (object, context) -> object.getEnergyStorage());
        event.registerBlockEntity(Capabilities.Item.BLOCK, holder.get(), (object, context) -> object.getItemHandler());
    }

    private static <T extends EntityVehicleBase> void registerEntityCapabilities(RegisterCapabilitiesEvent event, DeferredHolder<EntityType<?>, EntityType<EntityGenericCar>> holder) {
        event.registerEntity(Capabilities.Fluid.ENTITY, holder.get(), (car, context) -> car);
        //TODO Implement capability access to the external inventory
        //event.registerEntity(Capabilities.Item.ENTITY, holder.get(), (car, context) -> car);
    }

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CarMod.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializerKey> CRAFTING_SPECIAL_KEY = RECIPE_SERIALIZER_REGISTER.register("crafting_special_key", RecipeSerializerKey::new
    );
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BlastFurnaceRecipe>> CRAFTING_BLAST_FURNACE = RECIPE_SERIALIZER_REGISTER.register("blast_furnace", () ->
            new RecipeSerializerBlastFurnace(BlastFurnaceRecipe::new)
    );
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<OilMillRecipe>> CRAFTING_OIL_MILL = RECIPE_SERIALIZER_REGISTER.register("oil_mill", () ->
            new RecipeSerializerOilMill(OilMillRecipe::new)
    );

}
