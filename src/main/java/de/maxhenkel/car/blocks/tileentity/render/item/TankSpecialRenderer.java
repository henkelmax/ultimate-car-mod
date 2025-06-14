package de.maxhenkel.car.blocks.tileentity.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import de.maxhenkel.car.blocks.BlockTank;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class TankSpecialRenderer implements SpecialModelRenderer<TileEntityTank> {

    protected static final Minecraft minecraft = Minecraft.getInstance();

    private final TileEntitySpecialRendererTank tankRenderer;
    private TileEntityTank tank;

    public TankSpecialRenderer(EntityModelSet modelSet) {
        tankRenderer = new TileEntitySpecialRendererTank(modelSet);
    }

    @Override
    public void render(@Nullable TileEntityTank tank, ItemDisplayContext itemDisplayContext, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay, boolean b) {
        if (tank == null) {
            return;
        }
        tankRenderer.render(tank, 0F, stack, bufferSource, light, overlay, Vec3.ZERO);
    }

    @Override
    public void getExtents(Set<Vector3f> vecs) {

    }

    @Nullable
    @Override
    public TileEntityTank extractArgument(ItemStack stack) {
        if (tank == null) {
            tank = new TileEntityTank(BlockPos.ZERO, ModBlocks.TANK.get().defaultBlockState());
        }
        BlockTank.applyItemData(stack, tank);
        return tank;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Unbaked implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(Unbaked::new);

        public Unbaked() {

        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new TankSpecialRenderer(modelSet);
        }
    }

}

