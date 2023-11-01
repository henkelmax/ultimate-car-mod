package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.client.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public class TileEntitySpecialRendererSplitTank implements BlockEntityRenderer<TileEntitySplitTank> {

    private static final FluidStack BIO_DIESEL_STACK = new FluidStack(ModFluids.BIO_DIESEL.get(), 1);
    private static final FluidStack GLYCERIN_STACK = new FluidStack(ModFluids.GLYCERIN.get(), 1);

    protected BlockEntityRendererProvider.Context renderer;

    public TileEntitySpecialRendererSplitTank(BlockEntityRendererProvider.Context renderer) {
        this.renderer = renderer;
    }

    @Override
    public void render(TileEntitySplitTank te, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        matrixStack.pushPose();
        float bioDiesel = te.getBioDieselPerc() / 2F;
        float glycerin = te.getGlycerinPerc() / 2F;

        if (bioDiesel > 0) {
            renderFluid(BIO_DIESEL_STACK, bioDiesel, 1F / 16F, matrixStack, buffer, light, overlay);
        }

        if (glycerin > 0) {
            renderFluid(GLYCERIN_STACK, glycerin, bioDiesel + 1F / 16F, matrixStack, buffer, light, overlay);
        }
        matrixStack.popPose();
    }

    public void renderFluid(FluidStack fluid, float amount, float yStart, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        matrixStack.pushPose();
        matrixStack.scale(0.98F, 0.98F, 0.98F);
        matrixStack.translate(0.01F, 0.01F, 0.01F);

        VertexConsumer builder = buffer.getBuffer(Sheets.translucentItemSheet());

        IClientFluidTypeExtensions type = IClientFluidTypeExtensions.of(fluid.getFluid());

        TextureAtlasSprite texture = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(type.getStillTexture(fluid));

        float uMin = texture.getU0();
        float uMax = texture.getU1();
        float vMin = texture.getV0();
        float vMax = texture.getV1();

        float vHeight = vMax - vMin;

        float s = 0F;

        // North
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 0F + s, uMax, vMin, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 0F + s, uMin, vMin, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, light, overlay);

        // South
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 1F - s, uMin, vMin, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 1F - s, uMax, vMin, light, overlay);

        // East
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 0F + s, uMin, vMin, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 1F - s, uMax, vMin, light, overlay);

        // West
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 1F - s, uMin, vMin, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 0F + s, uMax, vMin, light, overlay);

        // Up
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMax, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMax, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin, light, overlay);
        RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin, light, overlay);

        matrixStack.popPose();
    }

}
