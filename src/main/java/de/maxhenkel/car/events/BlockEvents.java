package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockEvents {

    private static ResourceLocation GRASS_LOOT_TABLE = new ResourceLocation(Main.MODID, "blocks/grass");

    @SubscribeEvent
    public void capabilityAttach(BlockEvent.BreakEvent event) {
        if (!event.getState().getBlock().equals(Blocks.GRASS)) {
            return;
        }

        if (!(event.getPlayer().world instanceof ServerWorld)) {
            return;
        }

        ServerWorld serverWorld = (ServerWorld) event.getPlayer().world;

        LootContext.Builder builder = new LootContext.Builder(serverWorld)
                .withRandom(serverWorld.rand)
                .withParameter(LootParameters.POSITION, event.getPos())
                .withParameter(LootParameters.TOOL, event.getPlayer().getHeldItemMainhand())
                .withParameter(LootParameters.THIS_ENTITY, event.getPlayer())
                .withParameter(LootParameters.BLOCK_STATE, event.getState());

        LootContext lootContext = builder.build(LootParameterSets.BLOCK);

        LootTable lootTable=serverWorld.getServer().getLootTableManager().getLootTableFromLocation(GRASS_LOOT_TABLE);

        lootTable.generate(lootContext).forEach((stack) -> {
            Block.spawnAsEntity(serverWorld, event.getPos(), stack);
        });
    }

}
