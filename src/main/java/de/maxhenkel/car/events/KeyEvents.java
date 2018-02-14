package de.maxhenkel.car.events;

import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import de.maxhenkel.car.net.MessageCenterCar;
import de.maxhenkel.car.net.MessageStarting;
import de.maxhenkel.car.proxy.CommonProxy;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

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
		this.keyForward = new KeyBinding("key.car_forward", Keyboard.KEY_W, "category.car");
		ClientRegistry.registerKeyBinding(keyForward);
		
		this.keyBack = new KeyBinding("key.car_back", Keyboard.KEY_S, "category.car");
		ClientRegistry.registerKeyBinding(keyBack);
		
		this.keyLeft = new KeyBinding("key.car_left", Keyboard.KEY_A, "category.car");
		ClientRegistry.registerKeyBinding(keyLeft);
		
		this.keyRight = new KeyBinding("key.car_right", Keyboard.KEY_D, "category.car");
		ClientRegistry.registerKeyBinding(keyRight);
		
		this.keyCarGui = new KeyBinding("key.car_gui", Keyboard.KEY_I, "category.car");
		ClientRegistry.registerKeyBinding(keyCarGui);
		
		this.keyStart = new KeyBinding("key.car_start", Keyboard.KEY_R, "category.car");
		ClientRegistry.registerKeyBinding(keyStart);
		
		this.keyHorn = new KeyBinding("key.car_horn", Keyboard.KEY_H, "category.car");
		ClientRegistry.registerKeyBinding(keyHorn);

        this.keyCenter = new KeyBinding("key.center_car", Keyboard.KEY_SPACE, "category.car");
        ClientRegistry.registerKeyBinding(keyCenter);
	}

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.isCanceled()) {
            return;
        }

        Minecraft minecraft = Minecraft.getMinecraft();

        //World world = minecraft.theWorld;

        EntityPlayer player=minecraft.player;

        Entity riding=player.getRidingEntity();

        if(!(riding instanceof EntityCarBatteryBase)){
            return;
        }

        EntityCarBatteryBase car=(EntityCarBatteryBase) riding;

        if(player.equals(car.getDriver())){
            car.updateControls(keyForward.isKeyDown(), keyBack.isKeyDown(), keyLeft.isKeyDown(), keyRight.isKeyDown(), player);

            if(keyStart.isKeyDown()){
                if(!wasStartPressed){
                    CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStarting(true, false, player));
                    //car.setStarting(true, false);
                    wasStartPressed=true;
                }
            }else{
                if(wasStartPressed){
                    CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStarting(false, true, player));

                   // car.setStarting(false, true);
                }
                wasStartPressed=false;
            }

            if(keyHorn.isKeyDown()){
                if(!wasHornPressed){
                    car.onHornPressed(player);
                    wasHornPressed=true;
                }
            }else{
                wasHornPressed=false;
            }

            if(keyCenter.isKeyDown()){
                if(!wasCenterPressed){
                    //car.centerCar();
                    CommonProxy.simpleNetworkWrapper.sendToServer(new MessageCenterCar(player));
                    player.sendStatusMessage(new TextComponentTranslation("message.center_car"), true);
                    wasCenterPressed=true;
                }
            }else{
                wasCenterPressed=false;
            }
        }

        if(keyCarGui.isKeyDown()){
            if(!wasGuiPressed){
                car.openCarGUi(player);
                wasGuiPressed=true;
            }
        }else{
            wasGuiPressed=false;
        }

    }

}
