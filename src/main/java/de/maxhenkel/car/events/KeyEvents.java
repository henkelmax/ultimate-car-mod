package de.maxhenkel.car.events;

import org.lwjgl.input.Keyboard;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
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
	
	private boolean wasStartPressed;
	private boolean wasGuiPressed;

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
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (event.isCanceled()) {
			return;
		}

		Minecraft minecraft = Minecraft.getMinecraft();

		//World world = minecraft.theWorld;
		
		EntityPlayer player=minecraft.thePlayer;

		Entity riding=player.getRidingEntity();
		
		if(!(riding instanceof EntityCarBase)){
			return;
		}
		
		EntityCarBase car=(EntityCarBase) riding;
		
		if(player.equals(car.getDriver())){
			car.updateControls(keyForward.isKeyDown(), keyBack.isKeyDown(), keyLeft.isKeyDown(), keyRight.isKeyDown());
		}
		
		if(keyCarGui.isKeyDown()){
			if(!wasGuiPressed){
				car.openCarGUi(player);
				wasGuiPressed=true;
			}
		}else{
			wasGuiPressed=false;
		}
		
		if(keyStart.isKeyDown()){
			if(!wasStartPressed){
				car.startCarEngine();
				wasStartPressed=true;
			}
		}else{
			wasStartPressed=false;
		}
	}

}
