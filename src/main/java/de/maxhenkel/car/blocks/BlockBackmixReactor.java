package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBackmixReactor extends BlockGui{

	protected BlockBackmixReactor() {
		super(Material.IRON, "backmix_reactor");
		setHardness(3.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBackmixReactor();
	}

	@Override
	public int getGUIID() {
		return GuiHandler.GUI_BACKMIX_REACTOR;
	}

}
