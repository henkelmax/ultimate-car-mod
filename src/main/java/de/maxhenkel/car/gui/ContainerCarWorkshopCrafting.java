package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCarWorkshopCrafting extends ContainerBase{

	protected EntityPlayer player;
	protected TileEntityCarWorkshop tile;
	
	public ContainerCarWorkshopCrafting(TileEntityCarWorkshop tile, EntityPlayer player) {
		super(tile, player.inventory);
		this.player=player;
		this.tile=tile;
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 5; x++) {
				this.addSlotToContainer(new Slot(tileInventory, x + y * 5, 8 + x * 18, 73 + y * 18));
			}
		}
		
		addInvSlots();
	}
	
	@Override
	public int getInvOffset() {
		return 56;
	}

	public EntityPlayer getPlayer() {
		return player;
	}
	
	public TileEntityCarWorkshop getTile() {
		return tile;
	}
	
}
