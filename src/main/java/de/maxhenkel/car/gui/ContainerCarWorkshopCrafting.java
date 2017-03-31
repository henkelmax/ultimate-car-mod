package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerCarWorkshopCrafting extends ContainerBase{

	private IInventory playerInv;
	private TileEntityCarWorkshop tile;
	private EntityPlayer player;
	
	public ContainerCarWorkshopCrafting(EntityPlayer player, TileEntityCarWorkshop tile) {
		this.player=player;
		this.tile=tile;
		this.playerInv=player.inventory;
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 5; x++) {
				this.addSlotToContainer(new Slot(tile, x + y * 5, 8 + x * 18, 73 + y * 18));
			}
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 198));
		}
		
	}

	@Override
	protected int getSizeInventory() {
		return 0;
	}

	public IInventory getPlayerInv() {
		return playerInv;
	}

	public TileEntityCarWorkshop getTile() {
		return tile;
	}

	public EntityPlayer getPlayer() {
		return player;
	}
	
}
