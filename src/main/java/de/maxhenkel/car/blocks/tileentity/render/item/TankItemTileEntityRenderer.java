package de.maxhenkel.car.blocks.tileentity.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.blocks.BlockTank;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererTank;
import de.maxhenkel.corelib.client.ItemRenderer;
import de.maxhenkel.corelib.client.RendererProviders;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TankItemTileEntityRenderer extends ItemRenderer {

    private TileEntityTank tileEntityTank;
    private TileEntitySpecialRendererTank tankRenderer;

    public TankItemTileEntityRenderer() {

    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack stack, MultiBufferSource source, int light, int overlay) {
        if (tileEntityTank == null) {
            tileEntityTank = new TileEntityTank(BlockPos.ZERO, ModBlocks.TANK.get().defaultBlockState());
            tankRenderer = new TileEntitySpecialRendererTank(RendererProviders.createBlockEntityRendererContext());
        }

        BlockTank.applyItemData(itemStack, tileEntityTank);
        tankRenderer.render(tileEntityTank, 0F, stack, source, light, overlay);
    }

}
