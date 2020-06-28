package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.tools.EntityTools;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.net.MessageRepairCar;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiCarWorkshopRepair extends GuiBase<ContainerCarWorkshopRepair> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car_workshop_repair.png");

    private PlayerEntity player;
    private TileEntityCarWorkshop tile;

    private Button buttonBack;
    private Button buttonRepair;

    private EntityTools.CarRenderer carRenderer;

    public GuiCarWorkshopRepair(ContainerCarWorkshopRepair container, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, container, playerInventory, title);
        this.player = container.getPlayer();
        this.tile = container.getTile();
        this.carRenderer = new EntityTools.CarRenderer();

        xSize = 176;
        ySize = 222;
    }

    @Override
    protected void func_231160_c_() {
        super.func_231160_c_();

        this.buttonRepair = func_230480_a_(new Button(guiLeft + xSize - 7 - 60, guiTop + 105, 60, 20, new TranslationTextComponent("button.repair_car"), button -> {
            if (tile.getWorld().isRemote) {
                Main.SIMPLE_CHANNEL.sendToServer(new MessageRepairCar(tile.getPos(), player));
            }
        }));
        this.buttonRepair.field_230693_o_ = false;

        this.buttonBack = func_230480_a_(new Button(guiLeft + 7, guiTop + 105, 60, 20, new TranslationTextComponent("button.back"), button -> {
            if (tile.getWorld().isRemote) {
                Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenCarWorkshopGui(tile.getPos(), player, false));
            }
        }));
        this.buttonBack.field_230693_o_ = true;
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);

        // Titles
        field_230712_o_.func_238422_b_(matrixStack, tile.getDisplayName(), 8, 6, FONT_COLOR);
        field_230712_o_.func_238422_b_(matrixStack, player.inventory.getDisplayName(), 8, ySize - 96 + 2, FONT_COLOR);

        EntityCarBase carTop = tile.getCarOnTop();

        if (!(carTop instanceof EntityCarDamageBase)) {
            buttonRepair.field_230693_o_ = false;
            return;
        }

        EntityCarDamageBase car = (EntityCarDamageBase) carTop;

        if (mouseX >= guiLeft + 52 && mouseX <= guiLeft + 123) {
            if (mouseY >= guiTop + 81 && mouseY <= guiTop + 90) {
                List<IFormattableTextComponent> list = new ArrayList<>();
                list.add(new TranslationTextComponent("tooltip.damage", MathTools.round(car.getDamage(), 2)));
                func_238654_b_(matrixStack, list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (tile.areRepairItemsInside() && car.getDamage() > 0) {
            buttonRepair.field_230693_o_ = true;
        } else {
            buttonRepair.field_230693_o_ = false;
        }
        drawCar(carTop);
    }

    @Override
    public void func_231023_e_() {
        super.func_231023_e_();
        carRenderer.tick();
    }

    private void drawCar(EntityCarBase car) {
        carRenderer.render(car, xSize / 2, 55, 23);
    }

    public double getDamagePercent(EntityCarDamageBase car) {
        float dmg = car.getDamage();
        dmg = Math.min(dmg, 100);
        return MathTools.round(dmg, 2);
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.func_230450_a_(matrixStack, partialTicks, mouseX, mouseY);
        drawDamage(matrixStack);
    }

    public void drawDamage(MatrixStack matrixStack) {
        EntityCarBase car = tile.getCarOnTop();
        if (!(car instanceof EntityCarDamageBase)) {
            return;
        }

        EntityCarDamageBase c = (EntityCarDamageBase) car;

        double percent = 100 - getDamagePercent(c);

        field_230706_i_.getTextureManager().bindTexture(GUI_TEXTURE);
        int scaled = (int) (72 * percent / 100);
        int i = this.guiLeft;
        int j = this.guiTop;
        func_238474_b_(matrixStack, i + 52, j + 81, 176, 0, scaled, 10);
    }

    @Override
    public boolean func_231177_au__() {
        return false;
    }

}
