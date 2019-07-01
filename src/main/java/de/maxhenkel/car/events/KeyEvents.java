package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import de.maxhenkel.car.net.MessageCenterCar;
import de.maxhenkel.car.net.MessageStarting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyEvents {

    private KeyBinding keyForward;
    private KeyBinding keyBack;
    private KeyBinding keyLeft;
    private KeyBinding keyRight;

    private KeyBinding keyCarGui;
    private KeyBinding keyStart;
    private KeyBinding keyHorn;
    private KeyBinding keyCenter;

    private boolean wasStartPressed;
    private boolean wasGuiPressed;
    private boolean wasHornPressed;
    private boolean wasCenterPressed;

    public KeyEvents() {
        this.keyForward = new KeyBinding("key.car_forward", GLFW.GLFW_KEY_W, "category.car");
        ClientRegistry.registerKeyBinding(keyForward);

        this.keyBack = new KeyBinding("key.car_back", GLFW.GLFW_KEY_S, "category.car");
        ClientRegistry.registerKeyBinding(keyBack);

        this.keyLeft = new KeyBinding("key.car_left", GLFW.GLFW_KEY_A, "category.car");
        ClientRegistry.registerKeyBinding(keyLeft);

        this.keyRight = new KeyBinding("key.car_right", GLFW.GLFW_KEY_D, "category.car");
        ClientRegistry.registerKeyBinding(keyRight);

        this.keyCarGui = new KeyBinding("key.car_gui", GLFW.GLFW_KEY_I, "category.car");
        ClientRegistry.registerKeyBinding(keyCarGui);

        this.keyStart = new KeyBinding("key.car_start", GLFW.GLFW_KEY_R, "category.car");
        ClientRegistry.registerKeyBinding(keyStart);

        this.keyHorn = new KeyBinding("key.car_horn", GLFW.GLFW_KEY_H, "category.car");
        ClientRegistry.registerKeyBinding(keyHorn);

        this.keyCenter = new KeyBinding("key.center_car", GLFW.GLFW_KEY_SPACE, "category.car");
        ClientRegistry.registerKeyBinding(keyCenter);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.isCanceled()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        PlayerEntity player = minecraft.player;

        Entity riding = player.getRidingEntity();

        if (!(riding instanceof EntityCarBatteryBase)) {
            return;
        }

        EntityCarBatteryBase car = (EntityCarBatteryBase) riding;

        if (player.equals(car.getDriver())) {
            car.updateControls(keyForward.isKeyDown(), keyBack.isKeyDown(), keyLeft.isKeyDown(), keyRight.isKeyDown(), player);

            if (keyStart.isKeyDown()) {
                if (!wasStartPressed) {
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageStarting(true, false, player));
                    //car.setStarting(true, false);
                    wasStartPressed = true;
                }
            } else {
                if (wasStartPressed) {
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageStarting(false, true, player));
                    // car.setStarting(false, true);
                }
                wasStartPressed = false;
            }

            if (keyHorn.isKeyDown()) {
                if (!wasHornPressed) {
                    car.onHornPressed(player);
                    wasHornPressed = true;
                }
            } else {
                wasHornPressed = false;
            }

            if (keyCenter.isKeyDown()) {
                if (!wasCenterPressed) {
                    //car.centerCar();
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageCenterCar(player));
                    player.sendStatusMessage(new TranslationTextComponent("message.center_car"), true);
                    wasCenterPressed = true;
                }
            } else {
                wasCenterPressed = false;
            }
        }

        if (keyCarGui.isKeyDown()) {
            if (!wasGuiPressed) {
                car.openCarGUi(player);
                wasGuiPressed = true;
            }
        } else {
            wasGuiPressed = false;
        }

    }

}
