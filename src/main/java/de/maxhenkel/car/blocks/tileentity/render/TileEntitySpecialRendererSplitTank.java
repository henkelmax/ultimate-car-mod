package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.tools.FluidStackWrapper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import org.lwjgl.opengl.GL11;

import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.Fluid;

public class TileEntitySpecialRendererSplitTank extends TileEntityRenderer<TileEntitySplitTank> {

    @Override
    public void render(TileEntitySplitTank te, double x, double y, double z, float partialTicks, int destroyStage) {
        //bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);

        float bioDiesel = te.getBioDieselPerc() / 2F;
        float glycerin = te.getGlycerinPerc() / 2F;

        if (bioDiesel > 0) {
            renderFluid(ModFluids.BIO_DIESEL, bioDiesel, 1F / 16F);
        }

        if (glycerin > 0) {
            renderFluid(ModFluids.GLYCERIN, glycerin, bioDiesel + 1F / 16F);
        }

        GlStateManager.popMatrix();
    }

    public void renderFluid(Fluid fluid, float amount, float yStart) {
        bindTexture(FluidStackWrapper.getTexture(fluid));

        GlStateManager.pushMatrix();
        GlStateManager.scalef(0.98F, 0.98F, 0.98F);
        GlStateManager.translatef(0.01F, 0.01F, 0.01F);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		/*TextureAtlasSprite texture = Minecraft.getInstance().getTextureMap().getAtlasSprite(fluid.getStill().toString());
		
		final double uMin = texture.getMinU();
		final double uMax = texture.getMaxU();
		final double vMin = texture.getMinV();
		final double vMax = texture.getMaxV();*/

        double uMin = 0D;
        double uMax = 1D;
        double vMin = 0D;
        double vMax = 1D / 32D;

        final double vHeight = vMax - vMin;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        float s = 0.0F;

        // North
        buffer.pos(1 - s, yStart, 0 + s).tex(uMax, vMin).endVertex();
        buffer.pos(0 + s, yStart, 0 + s).tex(uMin, vMin).endVertex();
        buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMin, vMin + vHeight * amount).endVertex();
        buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin + vHeight * amount).endVertex();

        // South
        buffer.pos(1 - s, yStart, 1 - s).tex(uMin, vMin).endVertex();
        buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin + vHeight * amount).endVertex();
        buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMax, vMin + vHeight * amount).endVertex();
        buffer.pos(0 + s, yStart, 1 - s).tex(uMax, vMin).endVertex();

        // East
        buffer.pos(1 - s, yStart, 0 + s).tex(uMin, vMin).endVertex();
        buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMin, vMin + vHeight * amount).endVertex();
        buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMax, vMin + vHeight * amount).endVertex();
        buffer.pos(1 - s, yStart, 1 - s).tex(uMax, vMin).endVertex();

        // West
        buffer.pos(0 + s, yStart, 1 - s).tex(uMin, vMin).endVertex();
        buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin + vHeight * amount).endVertex();
        buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin + vHeight * amount).endVertex();
        buffer.pos(0 + s, yStart, 0 + s).tex(uMax, vMin).endVertex();

        // Down
        buffer.pos(1 - s, yStart, 0 + s).tex(uMax, vMin).endVertex();
        buffer.pos(1 - s, yStart, 1 - s).tex(uMin, vMin).endVertex();
        buffer.pos(0 + s, yStart, 1 - s).tex(uMin, vMax).endVertex();
        buffer.pos(0 + s, yStart, 0 + s).tex(uMax, vMax).endVertex();

        // Up
        buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMax, vMax).endVertex();
        buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMin, vMax).endVertex();
        buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin).endVertex();
        buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin).endVertex();

        tessellator.draw();

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();

        GlStateManager.popMatrix();
    }


}
