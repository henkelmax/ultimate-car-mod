package de.maxhenkel.car.events;

import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderEvents {

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent evt) {
		if (!evt.getType().equals(ElementType.EXPERIENCE)) {
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();

		EntityPlayer player = mc.player;

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
			renderFuelBar(((float)car.getFuelAmount()) / ((float)car.getMaxFuel()));
			renderSpeed(car.getKilometerPerHour());
		}

	}

	public void renderFuelBar(float percent) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc);
		int x = scaledresolution.getScaledWidth() / 2 - 91;

		mc.getTextureManager().bindTexture(Gui.ICONS);

		int k = scaledresolution.getScaledHeight() - 32 + 3;
		mc.ingameGUI.drawTexturedModalRect(x, k, 0, 64, 182, 5);

		int j = (int) (percent * 182.0F);

		if (j > 0) {
			mc.ingameGUI.drawTexturedModalRect(x, k, 0, 69, j, 5);
		}
	}

	public void renderSpeed(float speed) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc);

		String s = String.valueOf(MathTools.round(Math.abs(speed), 2));
		int i1 = (scaledresolution.getScaledWidth() - mc.ingameGUI.getFontRenderer().getStringWidth(s)) / 2;
		int j1 = scaledresolution.getScaledHeight() - 31 - 4;
		mc.ingameGUI.getFontRenderer().drawString(s, i1 + 1, j1, 0);
		mc.ingameGUI.getFontRenderer().drawString(s, i1 - 1, j1, 0);
		mc.ingameGUI.getFontRenderer().drawString(s, i1, j1 + 1, 0);
		mc.ingameGUI.getFontRenderer().drawString(s, i1, j1 - 1, 0);
		mc.ingameGUI.getFontRenderer().drawString(s, i1, j1, 8453920);

	}

    @SubscribeEvent
	public void renderToolTip(RenderTooltipEvent.Pre event){
        ItemStack stack=event.getStack();

        if(!stack.hasTagCompound()){
            return;
        }
        NBTTagCompound compound=stack.getTagCompound();
        if(!compound.hasKey("trading_item")&&compound.getBoolean("trading_item")){
            return;
        }
        event.setCanceled(true);
	}

}
