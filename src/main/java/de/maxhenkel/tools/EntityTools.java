package de.maxhenkel.tools;

import com.mojang.math.Axis;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

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
    public static EntityGenericCar getCarByUUID(Player player, UUID uuid) {
        double distance = 10D;
        return player.level().getEntitiesOfClass(EntityGenericCar.class, new AABB(player.getX() - distance, player.getY() - distance, player.getZ() - distance, player.getX() + distance, player.getY() + distance, player.getZ() + distance), entity -> entity.getUUID().equals(uuid)).stream().findAny().orElse(null);
    }

    public static void drawCarOnScreen(GuiGraphics graphics, EntityCarBase car, int posX, int posY, float scale, float rotation) {
        graphics.pose().pushPose();
        graphics.pose().translate(posX, posY, 100D);
        graphics.pose().scale(1F, 1F, -1F);
        graphics.pose().scale(scale, scale, scale);

        graphics.pose().mulPose(Axis.YP.rotationDegrees(135F + rotation));
        graphics.pose().mulPose(Axis.ZP.rotationDegrees(180F));
        EntityRenderDispatcher entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        entityrenderermanager.setRenderShadow(false);

        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        entityrenderermanager.render(car, 0D, 0D, 0D, 0F, 1F, graphics.pose(), buffer, 0xF000F0);
        buffer.endBatch();
        entityrenderermanager.setRenderShadow(true);
        graphics.pose().popPose();
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

        public void render(GuiGraphics guiGraphics, EntityCarBase car, int posX, int posY, int scale) {
            EntityTools.drawCarOnScreen(guiGraphics, car, posX, posY, scale, rotation + rotationPerTick * minecraft.getFrameTime());
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

        public void render(GuiGraphics guiGraphics, EntityCarBase car, int posX, int posY, int scale) {
            ticker.render(new Renderer() {
                @Override
                public void render(float partialTicks) {
                    EntityTools.drawCarOnScreen(guiGraphics, car, posX, posY, scale, rotation + rotationPerTick * partialTicks);
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
