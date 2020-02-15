package de.maxhenkel.car.blocks.tileentity.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.blocks.BlockTank;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class TankItemTileEntityRenderer extends ItemStackTileEntityRenderer {

    private TileEntityTank tileEntityTank;

    public TankItemTileEntityRenderer() {

    }

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityTank == null) {
            tileEntityTank = new TileEntityTank();
        }

        BlockTank.applyItemData(itemStackIn, tileEntityTank);

        TileEntityRendererDispatcher.instance.renderNullable(tileEntityTank, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
