package de.maxhenkel.car.gui;

import java.io.IOException;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.net.MessageOpenGui;
import de.maxhenkel.car.net.MessageSpawnCar;
import de.maxhenkel.car.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiCarWorkshopCrafting extends GuiBase {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_car_workshop_crafting.png");

    private static final int fontColor = 4210752;

    private IInventory playerInv;
    private TileEntityCarWorkshop tile;
    private EntityPlayer player;
    private float rotoation;

    private GuiButton buttonSpawn;
    private GuiButton buttonRepair;

    public GuiCarWorkshopCrafting(ContainerCarWorkshopCrafting container) {
        super(container);
        this.player = container.getPlayer();
        this.playerInv = container.getPlayerInventory();
        this.tile = container.getTile();

        xSize = 176;
        ySize = 222;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.buttonRepair = addButton(new GuiButton(0, guiLeft + 105, guiTop + 72, 60, 20,
                new TextComponentTranslation("button.repair_car").getFormattedText()));

        this.buttonSpawn = addButton(new GuiButton(1, guiLeft + 105, guiTop + 106, 60, 20,
                new TextComponentTranslation("button.spawn_car").getFormattedText()));
        this.buttonSpawn.enabled = false;//false? //TODO MAYBE CHANGE BACK
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Titles
        fontRenderer.drawString(tile.getDisplayName().getUnformattedText(), 8, 6, fontColor);
        fontRenderer.drawString(playerInv.getDisplayName().getFormattedText(), 8, ySize - 96 + 2,
                fontColor);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        EntityCarBase carTop = tile.getCarOnTop();
        EntityGenericCar car = tile.getCurrentCraftingCar();

        if(carTop!=null){
            drawCar(carTop);
            buttonSpawn.enabled = false;
        }else{
            if(car!=null){
                drawCar(car);
            }
            buttonSpawn.enabled = true;
        }

        /*if (carTop != null) {
            drawCar(carTop);
        } else {

        }


        if (car != null && carTop == null) {
            buttonSpawn.enabled = true;
            drawCar(car);
        } else {
            buttonSpawn.enabled = false;
        }*/

    }

    private void drawCar(EntityCarBase car) {
        MathTools.drawCarOnScreen(xSize / 2, 55, 23, rotoation, car);
        float parts = Minecraft.getMinecraft().getRenderPartialTicks();
        rotoation += parts / 4;
        if (!(rotoation < 360)) {
            rotoation = 0F;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.id == buttonSpawn.id) {
            if (tile.getWorld().isRemote) {
                if(tile.isCurrentCraftingCarValid()){
                    CommonProxy.simpleNetworkWrapper.sendToServer(new MessageSpawnCar(tile.getPos()));
                }else{
                    for(ITextComponent message:tile.getMessages()){
                        player.sendMessage(message);
                    }
                }
            }
        } else if (button.id == buttonRepair.id) {
            if (tile.getWorld().isRemote) {
                CommonProxy.simpleNetworkWrapper.sendToServer(new MessageOpenGui(tile.getPos(), GuiHandler.GUI_CAR_WORKSHOP_REPAIR, player).open(player));
            }
        }
    }

}
