package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerGenerator extends ContainerBase{

	private IInventory playerInv;
	private TileEntityGenerator tile;
	
	public ContainerGenerator(IInventory playerInv, TileEntityGenerator tile) {
		this.tile=tile;
		this.playerInv=playerInv;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
		
	}
	
	private int storedEnergy;
	private int currentMillibuckets;

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = listeners.get(i);

			if (storedEnergy != tile.getField(0)) {
				icontainerlistener.sendProgressBarUpdate(this, 0, tile.getField(0));
			}

			if (currentMillibuckets != tile.getField(1)) {
				icontainerlistener.sendProgressBarUpdate(this, 1, tile.getField(1));
			}

		}

		storedEnergy = tile.getField(0);
		currentMillibuckets = tile.getField(1);
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendProgressBarUpdate(this, 0, storedEnergy);
		listener.sendProgressBarUpdate(this, 1, currentMillibuckets);
	}

	@Override
	public void updateProgressBar(int id, int data) {
		tile.setField(id, data);
	}

	@Override
	protected int getSizeInventory() {
		return tile.getSizeInventory();
	}

}
