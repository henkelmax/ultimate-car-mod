package de.maxhenkel.car;

import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSign;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererSplitTank;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererTank;
import de.maxhenkel.car.blocks.tileentity.render.TileentitySpecialRendererGasStation;
import de.maxhenkel.car.blocks.tileentity.render.item.TankSpecialRenderer;
import de.maxhenkel.car.entity.model.GenericCarModel;
import de.maxhenkel.car.events.KeyEvents;
import de.maxhenkel.car.events.PlayerEvents;
import de.maxhenkel.car.events.RenderEvents;
import de.maxhenkel.car.events.SoundEvents;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.gui.*;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

@Mod(value = CarMod.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CarMod.MODID, value = Dist.CLIENT)
public class CarModClient {

    public static KeyMapping.Category KEY_CATEGORY_CAR;

    public static KeyMapping FORWARD_KEY;
    public static KeyMapping BACK_KEY;
    public static KeyMapping LEFT_KEY;
    public static KeyMapping RIGHT_KEY;

    public static KeyMapping CAR_GUI_KEY;
    public static KeyMapping START_KEY;
    public static KeyMapping HORN_KEY;
    public static KeyMapping CENTER_KEY;

    public CarModClient(IEventBus eventBus) {
        OBJModel.registerRenderPipeline(eventBus);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(CarMod.GAS_STATION_TILE_ENTITY_TYPE.get(), TileentitySpecialRendererGasStation::new);
        BlockEntityRenderers.register(CarMod.SPLIT_TANK_TILE_ENTITY_TYPE.get(), TileEntitySpecialRendererSplitTank::new);
        BlockEntityRenderers.register(CarMod.TANK_TILE_ENTITY_TYPE.get(), c -> new TileEntitySpecialRendererTank(c.entityModelSet()));
        BlockEntityRenderers.register(CarMod.SIGN_TILE_ENTITY_TYPE.get(), TileEntitySpecialRendererSign::new);

        NeoForge.EVENT_BUS.register(new RenderEvents());
        NeoForge.EVENT_BUS.register(new SoundEvents());
        NeoForge.EVENT_BUS.register(new KeyEvents());
        NeoForge.EVENT_BUS.register(new PlayerEvents());

        EntityRenderers.register(CarMod.CAR_ENTITY_TYPE.get(), GenericCarModel::new);

        ItemBlockRenderTypes.setRenderLayer(ModFluids.CANOLA_OIL.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.CANOLA_OIL_FLOWING.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.METHANOL.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.METHANOL_FLOWING.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.CANOLA_METHANOL_MIX.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.CANOLA_METHANOL_MIX_FLOWING.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GLYCERIN.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.GLYCERIN_FLOWING.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BIO_DIESEL.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.BIO_DIESEL_FLOWING.get(), ChunkSectionLayer.TRANSLUCENT);
    }

    @SubscribeEvent
    static void onRegisterKeyBinds(RegisterKeyMappingsEvent event) {
        KEY_CATEGORY_CAR = new KeyMapping.Category(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "car"));
        event.registerCategory(KEY_CATEGORY_CAR);

        FORWARD_KEY = new KeyMapping("key.car_forward", GLFW.GLFW_KEY_W, KEY_CATEGORY_CAR);
        BACK_KEY = new KeyMapping("key.car_back", GLFW.GLFW_KEY_S, KEY_CATEGORY_CAR);
        LEFT_KEY = new KeyMapping("key.car_left", GLFW.GLFW_KEY_A, KEY_CATEGORY_CAR);
        RIGHT_KEY = new KeyMapping("key.car_right", GLFW.GLFW_KEY_D, KEY_CATEGORY_CAR);
        CAR_GUI_KEY = new KeyMapping("key.car_gui", GLFW.GLFW_KEY_I, KEY_CATEGORY_CAR);
        START_KEY = new KeyMapping("key.car_start", GLFW.GLFW_KEY_R, KEY_CATEGORY_CAR);
        HORN_KEY = new KeyMapping("key.car_horn", GLFW.GLFW_KEY_H, KEY_CATEGORY_CAR);
        CENTER_KEY = new KeyMapping("key.center_car", GLFW.GLFW_KEY_SPACE, KEY_CATEGORY_CAR);

        event.register(FORWARD_KEY);
        event.register(BACK_KEY);
        event.register(LEFT_KEY);
        event.register(RIGHT_KEY);
        event.register(CAR_GUI_KEY);
        event.register(START_KEY);
        event.register(HORN_KEY);
        event.register(CENTER_KEY);
    }

    @SubscribeEvent
    static void onRegisterScreens(RegisterMenuScreensEvent containers) {
        containers.<ContainerBackmixReactor, GuiBackmixReactor>register(CarMod.BACKMIX_REACTOR_CONTAINER_TYPE.get(), GuiBackmixReactor::new);
        containers.<ContainerBlastFurnace, GuiBlastFurnace>register(CarMod.BLAST_FURNACE_CONTAINER_TYPE.get(), GuiBlastFurnace::new);
        containers.<ContainerCar, GuiCar>register(CarMod.CAR_CONTAINER_TYPE.get(), GuiCar::new);
        containers.<ContainerCarInventory, GuiCarInventory>register(CarMod.CAR_INVENTORY_CONTAINER_TYPE.get(), GuiCarInventory::new);
        containers.<ContainerCarWorkshopCrafting, GuiCarWorkshopCrafting>register(CarMod.CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE.get(), GuiCarWorkshopCrafting::new);
        containers.<ContainerCarWorkshopRepair, GuiCarWorkshopRepair>register(CarMod.CAR_WORKSHOP_REPAIR_CONTAINER_TYPE.get(), GuiCarWorkshopRepair::new);
        containers.<ContainerFluidExtractor, GuiFluidExtractor>register(CarMod.FLUID_EXTRACTOR_CONTAINER_TYPE.get(), GuiFluidExtractor::new);
        containers.<ContainerGasStation, GuiGasStation>register(CarMod.GAS_STATION_CONTAINER_TYPE.get(), GuiGasStation::new);
        containers.<ContainerGasStationAdmin, GuiGasStationAdmin>register(CarMod.GAS_STATION_ADMIN_CONTAINER_TYPE.get(), GuiGasStationAdmin::new);
        containers.<ContainerGenerator, GuiGenerator>register(CarMod.GENERATOR_CONTAINER_TYPE.get(), GuiGenerator::new);
        containers.<ContainerLicensePlate, GuiLicensePlate>register(CarMod.LICENSE_PLATE_CONTAINER_TYPE.get(), GuiLicensePlate::new);
        containers.<ContainerOilMill, GuiOilMill>register(CarMod.OIL_MILL_CONTAINER_TYPE.get(), GuiOilMill::new);
        containers.<ContainerPainter, GuiPainter>register(CarMod.PAINTER_CONTAINER_TYPE.get(), GuiPainter::new);
        containers.<ContainerSign, GuiSign>register(CarMod.SIGN_CONTAINER_TYPE.get(), GuiSign::new);
        containers.<ContainerSplitTank, GuiSplitTank>register(CarMod.SPLIT_TANK_CONTAINER_TYPE.get(), GuiSplitTank::new);
    }

    @SubscribeEvent
    static void registerItemModels(RegisterSpecialModelRendererEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "tank"), TankSpecialRenderer.Unbaked.MAP_CODEC);
    }

}
