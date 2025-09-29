package de.maxhenkel.car.blocks.tileentity.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import de.maxhenkel.car.blocks.BlockTank;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.blocks.tileentity.render.TankRenderState;
import de.maxhenkel.car.blocks.tileentity.render.TileEntitySpecialRendererTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Set;

public class TankSpecialRenderer implements SpecialModelRenderer<TileEntityTank> {

    protected static final Minecraft minecraft = Minecraft.getInstance();

    private final TileEntitySpecialRendererTank tankRenderer;
    private TileEntityTank tank;
    private TankRenderState tankRenderState;
    private CameraRenderState cameraRenderState;

    public TankSpecialRenderer(EntityModelSet modelSet) {
        tankRenderer = new TileEntitySpecialRendererTank(modelSet);
        tankRenderState = new TankRenderState();
        cameraRenderState = new CameraRenderState();
    }

    @Override
    public void submit(@Nullable TileEntityTank tank, ItemDisplayContext context, PoseStack stack, SubmitNodeCollector collector, int light, int overlay, boolean b, int i) {
        if (tank == null) {
            return;
        }
        tankRenderer.extractRenderState(tank, tankRenderState, 0F, Vec3.ZERO, null);
        tankRenderer.submit(tankRenderState, stack, collector, cameraRenderState);
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

    public static class Unbaked implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(Unbaked::new);

        public Unbaked() {

        }

        @Override
        @Nullable
        public SpecialModelRenderer<?> bake(BakingContext context) {
            return new TankSpecialRenderer(context.entityModelSet());
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

    }

}

