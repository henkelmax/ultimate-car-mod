package de.maxhenkel.tools;

import com.mojang.math.Axis;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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

    public static void drawCarOnScreen(GuiGraphics graphics, EntityGenericCar car, int x1, int y1, int x2, int y2, float scale, float rotation) {
        float ySize = y2 - y1;
        float yOffset = (ySize / 2F) / scale;
        renderEntityInInventory(graphics, x1, y1, x2, y2, scale, new Vector3f(0F, yOffset, 0F), Axis.YP.rotationDegrees(135F + rotation).mul(Axis.ZP.rotationDegrees(180F)), car);
    }

    public static void renderEntityInInventory(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, float scale, Vector3f translation, Quaternionf rotation, Entity entity) {
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super Entity, ?> entityrenderer = entityrenderdispatcher.getRenderer(entity);
        EntityRenderState entityrenderstate = entityrenderer.createRenderState(entity, 1F);
        entityrenderstate.lightCoords = LightTexture.FULL_BRIGHT;
        entityrenderstate.hitboxesRenderState = null;
        entityrenderstate.shadowPieces.clear();
        entityrenderstate.outlineColor = 0;
        guiGraphics.submitEntityRenderState(entityrenderstate, scale, translation, rotation, null, x1, y1, x2, y2);
    }

    public static class CarRenderer {
        private float rotation;
        private final float rotationPerTick;
        private final Minecraft minecraft;

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

        public void render(GuiGraphics guiGraphics, EntityGenericCar car, int x1, int y1, int x2, int y2, int scale) {
            EntityTools.drawCarOnScreen(guiGraphics, car, x1, y1, x2, y2, scale, rotation + rotationPerTick * minecraft.getDeltaTracker().getRealtimeDeltaTicks());
        }
    }

    public static class SimulatedCarRenderer {
        private float rotation;
        private final float rotationPerTick;
        private final SimulatedTicker ticker;

        public SimulatedCarRenderer(float rotationPerTick) {
            this.rotationPerTick = rotationPerTick;
            ticker = new SimulatedTicker();
        }

        public SimulatedCarRenderer() {
            this(3.6F);
        }

        public void render(GuiGraphics guiGraphics, EntityGenericCar car, int x1, int y1, int x2, int y2, int scale) {
            ticker.render(new Renderer() {
                @Override
                public void render(float partialTicks) {
                    EntityTools.drawCarOnScreen(guiGraphics, car, x1, y1, x2, y2, scale, rotation + rotationPerTick * partialTicks);
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
