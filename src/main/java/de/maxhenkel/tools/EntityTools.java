package de.maxhenkel.tools;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityTools {

    /**
     * Gets the first car in the range of 10 blocks of the player
     *
     * @param player the player
     * @param uuid   the UUID of the car
     * @return the car or null
     */
    @Nullable
    public static EntityGenericCar getCarByUUID(PlayerEntity player, UUID uuid) {
        double distance = 10D;
        return player.level.getEntitiesOfClass(EntityGenericCar.class, new AxisAlignedBB(player.getX() - distance, player.getY() - distance, player.getZ() - distance, player.getX() + distance, player.getY() + distance, player.getZ() + distance), entity -> entity.getUUID().equals(uuid)).stream().findAny().orElse(null);
    }

    public static void drawCarOnScreen(MatrixStack matrixStack, EntityCarBase car, int posX, int posY, float scale, float rotation) {
        matrixStack.pushPose();
        matrixStack.translate(posX, posY, 100D);
        matrixStack.scale(1F, 1F, -1F);
        matrixStack.scale(scale, scale, scale);

        matrixStack.mulPose(Vector3f.YP.rotationDegrees(135F + rotation));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        entityrenderermanager.setRenderShadow(false);

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        entityrenderermanager.render(car, 0D, 0D, 0D, 0F, 1F, matrixStack, buffer, 0xF000F0);
        buffer.endBatch();
        entityrenderermanager.setRenderShadow(true);
        matrixStack.popPose();
    }

    public static class CarRenderer {
        private float rotation;
        private float rotationPerTick;
        private Minecraft minecraft;

        public CarRenderer(float rotationPerTick) {
            this.rotationPerTick = rotationPerTick;
            this.minecraft = Minecraft.getInstance();
        }

        public CarRenderer() {
            this(3.6F);
        }

        public void tick() {
            rotation += rotationPerTick;
            if (rotation >= 360F) {
                rotation = 0F;
            }
        }

        public void render(MatrixStack matrixStack, EntityCarBase car, int posX, int posY, int scale) {
            EntityTools.drawCarOnScreen(matrixStack, car, posX, posY, scale, rotation + rotationPerTick * minecraft.getFrameTime());
        }
    }

    public static class SimulatedCarRenderer {
        private float rotation;
        private float rotationPerTick;
        private SimulatedTicker ticker;

        public SimulatedCarRenderer(float rotationPerTick) {
            this.rotationPerTick = rotationPerTick;
            ticker = new SimulatedTicker();
        }

        public SimulatedCarRenderer() {
            this(3.6F);
        }

        public void render(MatrixStack matrixStack, EntityCarBase car, int posX, int posY, int scale) {
            ticker.render(new Renderer() {
                @Override
                public void render(float partialTicks) {
                    EntityTools.drawCarOnScreen(matrixStack, car, posX, posY, scale, rotation + rotationPerTick * partialTicks);
                }

                @Override
                public void tick() {
                    rotation += rotationPerTick;
                    if (rotation >= 360F) {
                        rotation = 0F;
                    }
                }
            });
        }
    }

    public static class SimulatedTicker {
        private static final long ONE_TICK = 50_000_000L;
        private long lastTick;

        public void render(Renderer renderer) {
            long currentNanos = System.nanoTime();

            if (currentNanos - lastTick >= ONE_TICK) {
                renderer.tick();
                lastTick = currentNanos;
            }
            renderer.render((float) (currentNanos - lastTick) / (float) ONE_TICK);
        }
    }

    public interface Renderer {
        void render(float partialTicks);

        void tick();
    }

}
