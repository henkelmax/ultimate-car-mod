package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.inventory.IContainerListener;

public class ContainerFuelStation extends ContainerBase{

	private TileEntityFuelStation tile;
	
	public ContainerFuelStation(TileEntityFuelStation fuelStation) {
		this.tile=fuelStation;
	}
	
	@Override
	protected int getSizeInventory() {
		return 0;
	}
	
	private int fuelCounter;
	private int storedFluid;

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = listeners.get(i);

			if (fuelCounter != tile.getField(0)) {
				icontainerlistener.sendProgressBarUpdate(this, 0, tile.getField(0));
			}

			if (storedFluid != tile.getField(1)) {
				icontainerlistener.sendProgressBarUpdate(this, 1, tile.getField(1));
			}

		}

		fuelCounter = tile.getField(0);
		storedFluid = tile.getField(1);
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendProgressBarUpdate(this, 0, fuelCounter);
		listener.sendProgressBarUpdate(this, 1, storedFluid);
	}

	@Override
	public void updateProgressBar(int id, int data) {
		tile.setField(id, data);
	}

}
