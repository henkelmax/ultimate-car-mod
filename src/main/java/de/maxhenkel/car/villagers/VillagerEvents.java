package de.maxhenkel.car.villagers;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

public class VillagerEvents {

    @SubscribeEvent
    public void villagerTrades(VillagerTradesEvent event) {
        if (event.getType().equals(Main.VILLAGER_PROFESSION_GAS_STATION_ATTENDANT.get())) {
            event.getTrades().put(1, new ArrayList<>(Arrays.asList(
                    new EmeraldForItemsTrade(ModItems.CANOLA.get(), 20, 16, 2),
                    new Trade(Items.EMERALD, 16, ModBlocks.ASPHALT.get(), 4, 16, 2)
            )));
            event.getTrades().put(2, new ArrayList<>(Arrays.asList(
                    new MultiTrade(
                            new Trade(Items.EMERALD, 8, ModItems.PAINTER.get(), 1, 8, 3),
                            new Trade(Items.EMERALD, 8, ModItems.PAINTER_YELLOW.get(), 1, 8, 3)
                    ),
                    new MultiTrade(
                            new Trade(Items.EMERALD, 16, ModItems.WRENCH.get(), 1, 3, 6),
                            new Trade(Items.EMERALD, 16, ModItems.SCREW_DRIVER.get(), 1, 3, 6),
                            new Trade(Items.EMERALD, 16, ModItems.HAMMER.get(), 1, 3, 6)
                    )
            )));
            event.getTrades().put(3, new ArrayList<>(Arrays.asList(
                    new Trade(Items.EMERALD, 18, ModItems.CANISTER.get(), 1, 3, 8),
                    new Trade(Items.EMERALD, 24, ModItems.REPAIR_KIT.get(), 1, 3, 8)
            )));
            event.getTrades().put(4, new ArrayList<>(Arrays.asList(
                    new MultiTrade(
                            new Trade(Items.EMERALD, 32, ModItems.BATTERY.get(), 1, 3, 12),
                            new Trade(Items.EMERALD, 32, ModItems.LICENSE_PLATE.get(), 1, 3, 12)
                    )
            )));
        }
    }

    static class EmeraldForItemsTrade extends Trade {
        public EmeraldForItemsTrade(ItemLike buyingItem, int buyingAmount, int maxUses, int givenExp) {
            super(buyingItem, buyingAmount, Items.EMERALD, 1, maxUses, givenExp);
        }
    }

    static class MultiTrade implements VillagerTrades.ItemListing {
        private final VillagerTrades.ItemListing[] trades;

        public MultiTrade(VillagerTrades.ItemListing... trades) {
            this.trades = trades;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            return trades[random.nextInt(trades.length)].getOffer(entity, random);
        }
    }

    static class Trade implements VillagerTrades.ItemListing {
        private final Item buyingItem;
        private final Item sellingItem;
        private final int buyingAmount;
        private final int sellingAmount;
        private final int maxUses;
        private final int givenExp;
        private final float priceMultiplier;

        public Trade(ItemLike buyingItem, int buyingAmount, ItemLike sellingItem, int sellingAmount, int maxUses, int givenExp) {
            this.buyingItem = buyingItem.asItem();
            this.buyingAmount = buyingAmount;
            this.sellingItem = sellingItem.asItem();
            this.sellingAmount = sellingAmount;
            this.maxUses = maxUses;
            this.givenExp = givenExp;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            return new MerchantOffer(new ItemStack(this.buyingItem, this.buyingAmount), new ItemStack(sellingItem, sellingAmount), maxUses, givenExp, priceMultiplier);
        }
    }
}