package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCarWorkshopRepair extends ContainerBase{

	protected EntityPlayer player;
	protected TileEntityCarWorkshop tile;
	
	public ContainerCarWorkshopRepair(TileEntityCarWorkshop tile, EntityPlayer player) {
		super(tile, player.inventory);
		this.player=player;
		this.tile=tile;
		
		this.addSlotToContainer(new SlotOneItem(tile.getRepairInventory(), 0, 50, 61, ModItems.SCREW_DRIVER));
		this.addSlotToContainer(new SlotOneItem(tile.getRepairInventory(), 1, 80, 61, ModItems.WRENCH));
		this.addSlotToContainer(new SlotOneItem(tile.getRepairInventory(), 2, 110, 61, ModItems.HAMMER));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 198));
		}

	}
	
	@Override
	public boolean hasCustomInvPos() {
		return true;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public TileEntityCarWorkshop getTile() {
		return tile;
	}
	
}
