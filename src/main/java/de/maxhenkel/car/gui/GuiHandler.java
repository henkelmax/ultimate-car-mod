package de.maxhenkel.car.gui;

@Deprecated
public class GuiHandler /*implements IGuiHandler*/{
/*
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
	public static final int GUI_SIGN=12;
	public static final int GUI_FUELSTATION_ADMIN=13;
	public static final int GUI_NUMBER_PLATE=14;
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		
		if(id==GUI_CAR){
			EntityCarInventoryBase car=getCar(player);
			if(car!=null){
				return new ContainerCar(car, player.inventory);
			}
		}else if(id==GUI_PAINTER){
			return new ContainerPainter(player, false);
		}else if(id==GUI_PAINTER_YELLOW){
			return new ContainerPainter(player, true);
		}else if(id==GUI_FUELSTATION){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityFuelStation){
				return new ContainerFuelStation((TileEntityFuelStation) te, player.inventory);
			}
		}else if(id==GUI_FUELSTATION_ADMIN){
            TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
            if(te!=null&&te instanceof TileEntityFuelStation){
                return new ContainerFuelStationAdmin((TileEntityFuelStation) te, player.inventory);
            }
        }else if(id==GUI_OIL_MILL){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityOilMill){
				return new ContainerOilMill((TileEntityOilMill) te, player.inventory);
			}
		}else if(id==GUI_BLAST_FURNACE){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityBlastFurnace){
				return new ContainerBlastFurnace((TileEntityBlastFurnace) te, player.inventory);
			}
		}else if(id==GUI_BACKMIX_REACTOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityBackmixReactor){
				return new ContainerBackmixReactor((TileEntityBackmixReactor) te, player.inventory);
			}
		}else if(id==GUI_GENERATOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityGenerator){
				return new ContainerGenerator((TileEntityGenerator) te, player.inventory);
			}
		}else if(id==GUI_SPLITTANK){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntitySplitTank){
				return new ContainerSplitTank((TileEntitySplitTank) te, player.inventory);
			}
		}else if(id==GUI_CAR_WORKSHOP_CRAFTING){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityCarWorkshop){
				return new ContainerCarWorkshopCrafting((TileEntityCarWorkshop) te, player);
			}
		}else if(id==GUI_CAR_WORKSHOP_REPAIR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityCarWorkshop){
				return new ContainerCarWorkshopRepair((TileEntityCarWorkshop) te, player);
			}
		}else if(id==GUI_FLUID_EXTRACTOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityFluidExtractor){
				return new ContainerFluidExtractor((TileEntityFluidExtractor) te, player);
			}
		}else if(id==GUI_SIGN){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntitySign){
				return new ContainerSign((TileEntitySign) te);
			}
		}else if(id==GUI_NUMBER_PLATE){
		    return new ContainerLicensePlate();
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
				return new GuiFuelStation((TileEntityFuelStation) te, player.inventory);
			}
		}else if(id==GUI_FUELSTATION_ADMIN){
            TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
            if(te!=null&&te instanceof TileEntityFuelStation){
                return new GuiFuelStationAdmin((TileEntityFuelStation) te, player.inventory);
            }
        }else if(id==GUI_OIL_MILL){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityOilMill){
				return new GuiOilMill(new ContainerOilMill((TileEntityOilMill) te, player.inventory));
			}
		}else if(id==GUI_BLAST_FURNACE){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityBlastFurnace){
				return new GuiBlastFurnace(new ContainerBlastFurnace((TileEntityBlastFurnace) te, player.inventory));
			}
		}else if(id==GUI_BACKMIX_REACTOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityBackmixReactor){
				return new GuiBackmixReactor((TileEntityBackmixReactor) te, player.inventory);
			}
		}else if(id==GUI_GENERATOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityGenerator){
				return new GuiGenerator((TileEntityGenerator) te, player.inventory);
			}
		}else if(id==GUI_SPLITTANK){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntitySplitTank){
				return new GuiSplitTank((TileEntitySplitTank) te, player.inventory);
			}
		}else if(id==GUI_CAR_WORKSHOP_CRAFTING){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityCarWorkshop){
				return new GuiCarWorkshopCrafting(new ContainerCarWorkshopCrafting((TileEntityCarWorkshop) te, player));
			}
		}else if(id==GUI_CAR_WORKSHOP_REPAIR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityCarWorkshop){
				return new GuiCarWorkshopRepair(new ContainerCarWorkshopRepair((TileEntityCarWorkshop) te, player));
			}
		}else if(id==GUI_FLUID_EXTRACTOR){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntityFluidExtractor){
				return new GuiFluidExtractor((TileEntityFluidExtractor) te, player);
			}
		}else if(id==GUI_SIGN){
			TileEntity te=world.getTileEntity(new BlockPos(x, y, z));
			if(te!=null&&te instanceof TileEntitySign){
				return new GuiSign((TileEntitySign) te);
			}
		}else if(id==GUI_NUMBER_PLATE){
            ItemStack stack=player.getHeldItem(EnumHand.MAIN_HAND);
            if(stack.getItem() instanceof ItemLicensePlate){
                return new GuiLicensePlate(player, stack);
            }else{
                stack=player.getHeldItem(EnumHand.OFF_HAND);
                if(stack.getItem() instanceof ItemLicensePlate){
                    return new GuiLicensePlate(player, stack);
                }
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
	}*/

}
