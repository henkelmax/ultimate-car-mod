package de.maxhenkel.car.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class RenderEvents {

    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void onRender(ViewportEvent.ComputeCameraAngles evt) {
        if (getCar() != null && !mc.options.getCameraType().isFirstPerson()) {
            evt.getCamera().move(-evt.getCamera().getMaxZoom(Main.CLIENT_CONFIG.carZoom.get() - 4D), 0D, 0D);
        }
    }

    @SubscribeEvent
    public void onRender(InputEvent.MouseScrollingEvent evt) {
        if (getCar() != null && !mc.options.getCameraType().isFirstPerson()) {
            Main.CLIENT_CONFIG.carZoom.set(Math.max(1D, Math.min(20D, Main.CLIENT_CONFIG.carZoom.get() - evt.getScrollDelta())));
            Main.CLIENT_CONFIG.carZoom.save();
            evt.setCanceled(true);
        }
    }

    private static EntityGenericCar getCar() {
        if (mc.player == null) {
            return null;
        }
        Entity e = mc.player.getVehicle();
        if (e instanceof EntityGenericCar) {
            return (EntityGenericCar) e;
        }
        return null;
    }

    public static boolean onRenderExperienceBar(PoseStack poseStack, int i) {
        Player player = mc.player;
        EntityGenericCar car = getCar();

        if (car == null || player == null) {
            return false;
        }

        if (!player.equals(car.getDriver())) {
            return false;
        }
        renderFuelBar(poseStack, ((float) car.getFuelAmount()) / ((float) car.getMaxFuel()));
        renderSpeed(poseStack, car.getKilometerPerHour());
        return true;
    }

    public static void renderFuelBar(PoseStack matrixStack, float percent) {
        percent = Mth.clamp(percent, 0F, 1F);
        int x = mc.getWindow().getGuiScaledWidth() / 2 - 91;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);

        int k = mc.getWindow().getGuiScaledHeight() - 32 + 3;
        mc.gui.blit(matrixStack, x, k, 0, 64, 182, 5);

        int j = (int) (percent * 182F);

        if (j > 0) {
            mc.gui.blit(matrixStack, x, k, 0, 69, j, 5);
        }
    }

    public static void renderSpeed(PoseStack matrixStack, float speed) {
        String s = String.valueOf(MathUtils.round(Math.abs(speed), 2));
        int i1 = (mc.getWindow().getGuiScaledWidth() - mc.gui.getFont().width(s)) / 2;
        int j1 = mc.getWindow().getGuiScaledHeight() - 31 - 4;
        mc.gui.getFont().draw(matrixStack, s, i1 + 1, j1, 0);
        mc.gui.getFont().draw(matrixStack, s, i1 - 1, j1, 0);
        mc.gui.getFont().draw(matrixStack, s, i1, j1 + 1, 0);
        mc.gui.getFont().draw(matrixStack, s, i1, j1 - 1, 0);
        mc.gui.getFont().draw(matrixStack, s, i1, j1, 8453920);

    }

    @SubscribeEvent
    public void renderToolTip(RenderTooltipEvent.Pre event) {
        ItemStack stack = event.getItemStack();

        if (!stack.hasTag()) {
            return;
        }
        CompoundTag compound = stack.getTag();
        if (!compound.contains("trading_item") && !compound.getBoolean("trading_item")) {
            return;
        }
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void renderPlayerPre(RenderPlayerEvent.Pre event) {
        EntityGenericCar car = getCar();
        if (car != null) {
            event.getPoseStack().pushPose();
            event.getPoseStack().scale(EntityVehicleBase.SCALE_FACTOR, EntityVehicleBase.SCALE_FACTOR, EntityVehicleBase.SCALE_FACTOR);
            event.getPoseStack().translate(0D, (event.getEntity().getBbHeight() - (event.getEntity().getBbHeight() * EntityVehicleBase.SCALE_FACTOR)) / 1.5D + car.getPlayerYOffset(), 0D);
        }
    }

    @SubscribeEvent
    public void renderPlayerPost(RenderPlayerEvent.Post event) {
        if (getCar() != null) {
            event.getPoseStack().popPose();
        }
    }

}
