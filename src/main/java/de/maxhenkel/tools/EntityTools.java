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
     * @param player
     * @param uuid   the UUID of the car
     * @return the car or null
     */
    @Nullable
    public static EntityGenericCar getCarByUUID(PlayerEntity player, UUID uuid) {
        double distance = 10D;
        return player.world.getEntitiesWithinAABB(EntityGenericCar.class, new AxisAlignedBB(player.getPosX() - distance, player.getPosY() - distance, player.getPosZ() - distance, player.getPosX() + distance, player.getPosY() + distance, player.getPosZ() + distance), entity -> entity.getUniqueID().equals(uuid)).stream().findAny().orElse(null);
    }

    public static void drawCarOnScreen(MatrixStack matrixStack, EntityCarBase car, int posX, int posY, float scale, float rotation) {
        matrixStack.push();
        matrixStack.translate(posX, posY, 100D);
        matrixStack.scale(1F, 1F, -1F);
        matrixStack.scale(scale, scale, scale);

        matrixStack.rotate(Vector3f.YP.rotationDegrees(135F + rotation));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(180F));
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
        entityrenderermanager.setRenderShadow(false);

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        entityrenderermanager.renderEntityStatic(car, 0D, 0D, 0D, 0F, 1F, matrixStack, buffer, 0xF000F0);
        buffer.finish();
        entityrenderermanager.setRenderShadow(true);
        matrixStack.pop();
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
            EntityTools.drawCarOnScreen(matrixStack, car, posX, posY, scale, rotation + rotationPerTick * minecraft.getRenderPartialTicks());
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
