package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;

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
