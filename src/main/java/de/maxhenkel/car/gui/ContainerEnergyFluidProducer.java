package de.maxhenkel.car.gui;

import java.util.List;
import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;

public abstract class ContainerEnergyFluidProducer extends ContainerBase {

	private TileEntityEnergyFluidProducer tile;
	private IInventory playerInv;

	public ContainerEnergyFluidProducer(IInventory playerInv, TileEntityEnergyFluidProducer tile) {
		this.playerInv = playerInv;
		this.tile = tile;

		this.addSlotToContainer(new SlotInput(tile, 0, 56, 34, getInsertItems()));
		this.addSlotToContainer(new SlotResult(tile, 1, 116, 35));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
	}

	public List<Item> getInsertItems() {
		return tile.getInputItems();
	}

	protected int getSizeInventory(){
		return 2;
	}
	
	private int storedEnergy;
	private int timeToGenerate;
	private int currentMillibuckets;

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = listeners.get(i);

			if (timeToGenerate != tile.getField(0)) {
				icontainerlistener.sendProgressBarUpdate(this, 0, tile.getField(0));
			}

			if (storedEnergy != tile.getField(1)) {
				icontainerlistener.sendProgressBarUpdate(this, 1, tile.getField(1));
			}

			if (currentMillibuckets != tile.getField(2)) {
				icontainerlistener.sendProgressBarUpdate(this, 2, tile.getField(2));
			}
		}

		timeToGenerate = tile.getField(0);
		storedEnergy = tile.getField(1);
		currentMillibuckets = tile.getField(2);
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendProgressBarUpdate(this, 0, timeToGenerate);
		listener.sendProgressBarUpdate(this, 1, storedEnergy);
		listener.sendProgressBarUpdate(this, 2, currentMillibuckets);
	}

	@Override
	public void updateProgressBar(int id, int data) {
		tile.setField(id, data);
	}

	public TileEntityEnergyFluidProducer getTile() {
		return tile;
	}

	public IInventory getPlayerInv() {
		return playerInv;
	}

}
