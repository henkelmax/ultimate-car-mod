package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import de.maxhenkel.car.blocks.tileentity.TileEntityBlastFurnace;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.car.blocks.tileentity.TileEntityOilMill;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	public static final int GUI_CAR=0;
	public static final int GUI_PAINTER=1;
	public static final int GUI_PAINTER_YELLOW=2;
	public static final int GUI_FUELSTATION=3;
	public static final int GUI_OIL_MILL=4;
	public static final int GUI_BLAST_FURNACE=5;
	public static final int GUI_BACKMIX_REACTOR=6;
	public static final int GUI_GENERATOR=7;
	public static final int GUI_SPLITTANK=8;
	public static final int GUI_CAR_WORKSHOP_CRAFTING=9;
	public static final int GUI_CAR_WORKSHOP_REPAIR=10;
	public static final int GUI_FLUID_EXTRACTOR=11;
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		
		if(id==GUI_CAR){
			EntityCarInventoryBase car=getCar(player);
			if(car!=null){
				return new ContainerCar(player.inventory, car);
			}
		}else if(id==GUI_PAINTER){
			return new ContainerPainter(player, false);
		}else if(id==GUI_PAINTER_YELLOW){
			return new ContainerPainter(player, true);
		}else if(id==GUI_FUELSTATION){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityFuelStation){
				return new ContainerFuelStation((TileEntityFuelStation) te);
			}
		}else if(id==GUI_OIL_MILL){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityOilMill){
				return new ContainerOilMill(player.inventory, (TileEntityOilMill) te);
			}
		}else if(id==GUI_BLAST_FURNACE){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityBlastFurnace){
				return new ContainerBlastFurnace(player.inventory, (TileEntityBlastFurnace) te);
			}
		}else if(id==GUI_BACKMIX_REACTOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityBackmixReactor){
				return new ContainerBackmixReactor(player.inventory, (TileEntityBackmixReactor) te);
			}
		}else if(id==GUI_GENERATOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityGenerator){
				return new ContainerGenerator(player.inventory, (TileEntityGenerator) te);
			}
		}else if(id==GUI_SPLITTANK){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntitySplitTank){
				return new ContainerSplitTank(player.inventory, (TileEntitySplitTank) te);
			}
		}else if(id==GUI_CAR_WORKSHOP_CRAFTING){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityCarWorkshop){
				return new ContainerCarWorkshopCrafting(player, (TileEntityCarWorkshop) te);
			}
		}else if(id==GUI_CAR_WORKSHOP_REPAIR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityCarWorkshop){
				return new ContainerCarWorkshopRepair(player, (TileEntityCarWorkshop) te);
			}
		}else if(id==GUI_FLUID_EXTRACTOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityFluidExtractor){
				return new ContainerFluidExtractor(player, (TileEntityFluidExtractor) te);
			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		
		if(id==GUI_CAR){
			
			EntityCarInventoryBase car=getCar(player);
			if(car!=null){
				return new GuiCar(player.inventory, car);
			}
			
		}else if(id==GUI_PAINTER){
			return new GuiPainter(player, false);
		}else if(id==GUI_PAINTER_YELLOW){
			return new GuiPainter(player, true);
		}else if(id==GUI_FUELSTATION){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityFuelStation){
				return new GuiFuelStation((TileEntityFuelStation) te);
			}
		}else if(id==GUI_OIL_MILL){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityOilMill){
				return new GuiOilMill(new ContainerOilMill(player.inventory, (TileEntityOilMill) te));
			}
		}else if(id==GUI_BLAST_FURNACE){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityBlastFurnace){
				return new GuiBlastFurnace(new ContainerBlastFurnace(player.inventory, (TileEntityBlastFurnace) te));
			}
		}else if(id==GUI_BACKMIX_REACTOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityBackmixReactor){
				return new GuiBackmixReactor(player.inventory, (TileEntityBackmixReactor) te);
			}
		}else if(id==GUI_GENERATOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityGenerator){
				return new GuiGenerator(player.inventory, (TileEntityGenerator) te);
			}
		}else if(id==GUI_SPLITTANK){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntitySplitTank){
				return new GuiSplitTank(player.inventory, (TileEntitySplitTank) te);
			}
		}else if(id==GUI_CAR_WORKSHOP_CRAFTING){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityCarWorkshop){
				return new GuiCarWorkshopCrafting(new ContainerCarWorkshopCrafting(player, (TileEntityCarWorkshop) te));
			}
		}else if(id==GUI_CAR_WORKSHOP_REPAIR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityCarWorkshop){
				return new GuiCarWorkshopRepair(new ContainerCarWorkshopRepair(player, (TileEntityCarWorkshop) te));
			}
		}else if(id==GUI_FLUID_EXTRACTOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityFluidExtractor){
				return new GuiFluidExtractor(player, (TileEntityFluidExtractor) te);
			}
		}
		
		return null;
	}
	
	public EntityCarInventoryBase getCar(EntityPlayer player){
		Entity e=player.getRidingEntity();
		
		if(e==null){
			return null;
		}
		
		if(e instanceof EntityCarInventoryBase){
			return (EntityCarInventoryBase) e;
		}
		
		return null;
	}

}
