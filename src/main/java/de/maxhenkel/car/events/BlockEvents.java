package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockEvents {

    private static ResourceLocation GRASS_LOOT_TABLE = new ResourceLocation(Main.MODID, "blocks/grass");

    @SubscribeEvent
    public void breakEvent(BlockEvent.BreakEvent event) {
        if (!event.getState().getBlock().equals(Blocks.GRASS)) {
            return;
        }

        if (!(event.getPlayer().world instanceof ServerWorld)) {
            return;
        }

        ServerWorld serverWorld = (ServerWorld) event.getPlayer().world;

        LootContext.Builder builder = new LootContext.Builder(serverWorld)
                .withRandom(serverWorld.rand)
                .withParameter(LootParameters.field_237457_g_, new Vector3d(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ()))
                .withParameter(LootParameters.TOOL, event.getPlayer().getHeldItemMainhand())
                .withParameter(LootParameters.THIS_ENTITY, event.getPlayer())
                .withParameter(LootParameters.BLOCK_STATE, event.getState());

        LootContext lootContext = builder.build(LootParameterSets.BLOCK);

        LootTable lootTable = serverWorld.getServer().getLootTableManager().getLootTableFromLocation(GRASS_LOOT_TABLE);

        lootTable.generate(lootContext).forEach((stack) -> {
            Block.spawnAsEntity(serverWorld, event.getPos(), stack);
        });
    }

}
