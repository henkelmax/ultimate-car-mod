package de.maxhenkel.car.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class RenderEvents {

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent evt) {
        if (!evt.getType().equals(ElementType.EXPERIENCE)) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        PlayerEntity player = mc.player;

        Entity e = player.getRidingEntity();

        if (e == null) {
            return;
        }

        if (!(e instanceof EntityCarFuelBase)) {
            return;
        }

        EntityCarFuelBase car = (EntityCarFuelBase) e;

        if (player.equals(car.getDriver())) {
            evt.setCanceled(true);
            renderFuelBar(evt.getMatrixStack(), ((float) car.getFuelAmount()) / ((float) car.getMaxFuel()));
            renderSpeed(evt.getMatrixStack(), car.getKilometerPerHour());
        }

    }

    public void renderFuelBar(MatrixStack matrixStack, float percent) {
        percent = MathHelper.clamp(percent, 0F, 1F);
        Minecraft mc = Minecraft.getInstance();
        int x = mc.getMainWindow().getScaledWidth() / 2 - 91;

        mc.getTextureManager().bindTexture(AbstractGui.field_230665_h_);

        int k = mc.getMainWindow().getScaledHeight() - 32 + 3;
        mc.ingameGUI.func_238474_b_(matrixStack, x, k, 0, 64, 182, 5);

        int j = (int) (percent * 182F);

        if (j > 0) {
            mc.ingameGUI.func_238474_b_(matrixStack, x, k, 0, 69, j, 5);
        }
    }

    public void renderSpeed(MatrixStack matrixStack, float speed) {
        Minecraft mc = Minecraft.getInstance();

        String s = String.valueOf(MathUtils.round(Math.abs(speed), 2));
        int i1 = (mc.getMainWindow().getScaledWidth() - mc.ingameGUI.getFontRenderer().getStringWidth(s)) / 2;
        int j1 = mc.getMainWindow().getScaledHeight() - 31 - 4;
        mc.ingameGUI.getFontRenderer().func_238421_b_(matrixStack, s, i1 + 1, j1, 0);
        mc.ingameGUI.getFontRenderer().func_238421_b_(matrixStack, s, i1 - 1, j1, 0);
        mc.ingameGUI.getFontRenderer().func_238421_b_(matrixStack, s, i1, j1 + 1, 0);
        mc.ingameGUI.getFontRenderer().func_238421_b_(matrixStack, s, i1, j1 - 1, 0);
        mc.ingameGUI.getFontRenderer().func_238421_b_(matrixStack, s, i1, j1, 8453920);

    }

    @SubscribeEvent
    public void renderToolTip(RenderTooltipEvent.Pre event) {
        ItemStack stack = event.getStack();

        if (!stack.hasTag()) {
            return;
        }
        CompoundNBT compound = stack.getTag();
        if (!compound.contains("trading_item") && !compound.getBoolean("trading_item")) {
            return;
        }
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void renderPlayerPre(RenderPlayerEvent.Pre event) {
        PlayerEntity player = event.getPlayer();
        if (player.getRidingEntity() instanceof EntityCarBase) {
            EntityCarBase car = (EntityCarBase) event.getPlayer().getRidingEntity();
            event.getMatrixStack().push();
            event.getMatrixStack().scale(EntityVehicleBase.SCALE_FACTOR, EntityVehicleBase.SCALE_FACTOR, EntityVehicleBase.SCALE_FACTOR);
            event.getMatrixStack().translate(0D, (event.getPlayer().getHeight() - (event.getPlayer().getHeight() * EntityVehicleBase.SCALE_FACTOR)) / 1.5D + car.getPlayerYOffset(), 0D);
        }
    }

    @SubscribeEvent
    public void renderPlayerPost(RenderPlayerEvent.Post event) {
        if (event.getPlayer().getRidingEntity() instanceof EntityCarBase) {
            event.getMatrixStack().pop();
        }
    }

}
