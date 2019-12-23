package de.maxhenkel.car.villagers;
/*
import com.google.common.collect.ImmutableList;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Random;

public class VillagerEvents {

    @SubscribeEvent
    public void villagerTrades(VillagerTradesEvent event) {
        if (event.getType().equals(ModVillagerProfessions.VILLAGER_PROFESSION_GAS_STATION_ATTENDANT)) {
            event.getTrades().put(1, ImmutableList.of(
                    new EmeraldForItemsTrade(ModItems.CANOLA, 20, 16, 2),
                    new Trade(Items.EMERALD, 16, ModBlocks.ASPHALT, 4, 16, 2)
            ));
            event.getTrades().put(2, ImmutableList.of(
                    new MultiTrade(
                            new Trade(Items.EMERALD, 8, ModItems.PAINTER, 1, 8, 3),
                            new Trade(Items.EMERALD, 8, ModItems.PAINTER_YELLOW, 1, 8, 3)
                    ),
                    new MultiTrade(
                            new Trade(Items.EMERALD, 16, ModItems.WRENCH, 1, 3, 6),
                            new Trade(Items.EMERALD, 16, ModItems.SCREW_DRIVER, 1, 3, 6),
                            new Trade(Items.EMERALD, 16, ModItems.HAMMER, 1, 3, 6)
                    )
            ));
            event.getTrades().put(3, ImmutableList.of(
                    new Trade(Items.EMERALD, 18, ModItems.CANISTER, 1, 3, 8),
                    new Trade(Items.EMERALD, 24, ModItems.REPAIR_KIT, 1, 3, 8)
            ));
            event.getTrades().put(4, ImmutableList.of(
                    new MultiTrade(
                            new Trade(Items.EMERALD, 32, ModItems.BATTERY, 1, 3, 12),
                            new Trade(Items.EMERALD, 32, ModItems.LICENSE_PLATE, 1, 3, 12)
                    )
            ));
        }
    }

    static class EmeraldForItemsTrade extends Trade {
        public EmeraldForItemsTrade(IItemProvider buyingItem, int buyingAmount, int maxUses, int givenExp) {
            super(buyingItem, buyingAmount, Items.EMERALD, 1, maxUses, givenExp);
        }
    }

    static class MultiTrade implements VillagerTrades.ITrade {
        private final VillagerTrades.ITrade[] trades;

        public MultiTrade(VillagerTrades.ITrade... trades) {
            this.trades = trades;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            return trades[random.nextInt(trades.length)].getOffer(entity, random);
        }
    }

    static class Trade implements VillagerTrades.ITrade {
        private final Item buyingItem;
        private final Item sellingItem;
        private final int buyingAmount;
        private final int sellingAmount;
        private final int maxUses;
        private final int givenExp;
        private final float priceMultiplier;

        public Trade(IItemProvider buyingItem, int buyingAmount, IItemProvider sellingItem, int sellingAmount, int maxUses, int givenExp) {
            this.buyingItem = buyingItem.asItem();
            this.buyingAmount = buyingAmount;
            this.sellingItem = sellingItem.asItem();
            this.sellingAmount = sellingAmount;
            this.maxUses = maxUses;
            this.givenExp = givenExp;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity entity, Random random) {
            return new MerchantOffer(new ItemStack(this.buyingItem, this.buyingAmount), new ItemStack(sellingItem, sellingAmount), maxUses, givenExp, priceMultiplier);
        }
    }
}
*/