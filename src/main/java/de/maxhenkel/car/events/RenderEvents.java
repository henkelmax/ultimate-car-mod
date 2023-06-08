package de.maxhenkel.car.events;

import com.mojang.blaze3d.systems.RenderSystem;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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

    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
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

    public static boolean onRenderExperienceBar(GuiGraphics guiGraphics, int i) {
        Player player = mc.player;
        EntityGenericCar car = getCar();

        if (car == null || player == null) {
            return false;
        }

        if (!player.equals(car.getDriver())) {
            return false;
        }
        renderFuelBar(guiGraphics, ((float) car.getFuelAmount()) / ((float) car.getMaxFuel()));
        renderSpeed(guiGraphics, car.getKilometerPerHour());
        return true;
    }

    public static void renderFuelBar(GuiGraphics guiGraphics, float percent) {
        percent = Mth.clamp(percent, 0F, 1F);
        int x = mc.getWindow().getGuiScaledWidth() / 2 - 91;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        int k = mc.getWindow().getGuiScaledHeight() - 32 + 3;
        guiGraphics.blit(GUI_ICONS_LOCATION, x, k, 0, 64, 182, 5);

        int j = (int) (percent * 182F);

        if (j > 0) {
            guiGraphics.blit(GUI_ICONS_LOCATION, x, k, 0, 69, j, 5);
        }
    }

    public static void renderSpeed(GuiGraphics guiGraphics, float speed) {
        Font font = mc.gui.getFont();
        String s = String.valueOf(MathUtils.round(Math.abs(speed), 2));
        int i1 = (mc.getWindow().getGuiScaledWidth() - font.width(s)) / 2;
        int j1 = mc.getWindow().getGuiScaledHeight() - 31 - 4;
        guiGraphics.drawString(font, s, i1 + 1, j1, 0, false);
        guiGraphics.drawString(font, s, i1 - 1, j1, 0, false);
        guiGraphics.drawString(font, s, i1, j1 + 1, 0, false);
        guiGraphics.drawString(font, s, i1, j1 - 1, 0, false);
        guiGraphics.drawString(font, s, i1, j1, 8453920, false);
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
