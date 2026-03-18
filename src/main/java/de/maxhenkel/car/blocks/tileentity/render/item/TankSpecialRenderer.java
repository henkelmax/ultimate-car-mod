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
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.function.Consumer;

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
    public void submit(@Nullable TileEntityTank argument, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        if (tank == null) {
            return;
        }
        tankRenderer.extractRenderState(tank, tankRenderState, 0F, Vec3.ZERO, null);
        tankRenderer.submit(tankRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> vecs) {

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

    public static class Unbaked implements SpecialModelRenderer.Unbaked<TileEntityTank> {

        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(Unbaked::new);

        public Unbaked() {

        }

        @Nullable
        @Override
        public SpecialModelRenderer<TileEntityTank> bake(BakingContext context) {
            return new TankSpecialRenderer(context.entityModelSet());
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

    }

}

