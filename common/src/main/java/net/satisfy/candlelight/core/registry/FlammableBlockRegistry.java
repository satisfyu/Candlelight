package net.satisfy.candlelight.core.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class FlammableBlockRegistry {

    public static void init() {
        addFlammable(5, 20,
                ObjectRegistry.FLOORBOARD.get(),
                ObjectRegistry.OAK_CABINET.get(),
                ObjectRegistry.OAK_DRAWER.get(),
                ObjectRegistry.OAK_CHAIR.get(),
                ObjectRegistry.OAK_TABLE.get(),
                ObjectRegistry.OAK_SHELF.get(),
                ObjectRegistry.OAK_BIG_TABLE.get(),
                ObjectRegistry.CABINET.get(),
                ObjectRegistry.DRAWER.get(),
                ObjectRegistry.CHAIR.get(),
                ObjectRegistry.TABLE.get(),
                ObjectRegistry.SIDEBOARD.get(),
                ObjectRegistry.SIDE_TABLE.get(),
                ObjectRegistry.LAMP.get(),
                ObjectRegistry.SOFA.get(),
                ObjectRegistry.SPRUCE_CABINET.get(),
                ObjectRegistry.SPRUCE_DRAWER.get(),
                ObjectRegistry.SPRUCE_CHAIR.get(),
                ObjectRegistry.SPRUCE_TABLE.get(),
                ObjectRegistry.SPRUCE_SHELF.get(),
                ObjectRegistry.SPRUCE_BIG_TABLE.get(),
                ObjectRegistry.BIRCH_CABINET.get(),
                ObjectRegistry.BIRCH_DRAWER.get(),
                ObjectRegistry.BIRCH_CHAIR.get(),
                ObjectRegistry.BIRCH_TABLE.get(),
                ObjectRegistry.BIRCH_SHELF.get(),
                ObjectRegistry.BIRCH_BIG_TABLE.get(),
                ObjectRegistry.MANGROVE_CABINET.get(),
                ObjectRegistry.MANGROVE_DRAWER.get(),
                ObjectRegistry.MANGROVE_CHAIR.get(),
                ObjectRegistry.MANGROVE_TABLE.get(),
                ObjectRegistry.MANGROVE_SHELF.get(),
                ObjectRegistry.MANGROVE_BIG_TABLE.get(),
                ObjectRegistry.JUNGLE_CABINET.get(),
                ObjectRegistry.JUNGLE_DRAWER.get(),
                ObjectRegistry.JUNGLE_CHAIR.get(),
                ObjectRegistry.JUNGLE_TABLE.get(),
                ObjectRegistry.JUNGLE_SHELF.get(),
                ObjectRegistry.JUNGLE_BIG_TABLE.get(),
                ObjectRegistry.ACACIA_CABINET.get(),
                ObjectRegistry.ACACIA_DRAWER.get(),
                ObjectRegistry.ACACIA_CHAIR.get(),
                ObjectRegistry.ACACIA_TABLE.get(),
                ObjectRegistry.ACACIA_SHELF.get(),
                ObjectRegistry.ACACIA_BIG_TABLE.get(),
                ObjectRegistry.DARK_OAK_CABINET.get(),
                ObjectRegistry.DARK_OAK_DRAWER.get(),
                ObjectRegistry.DARK_OAK_CHAIR.get(),
                ObjectRegistry.DARK_OAK_TABLE.get(),
                ObjectRegistry.DARK_OAK_SHELF.get(),
                ObjectRegistry.DARK_OAK_BIG_TABLE.get(),
                ObjectRegistry.WARPED_CABINET.get(),
                ObjectRegistry.WARPED_DRAWER.get(),
                ObjectRegistry.WARPED_CHAIR.get(),
                ObjectRegistry.WARPED_TABLE.get(),
                ObjectRegistry.WARPED_SHELF.get(),
                ObjectRegistry.WARPED_BIG_TABLE.get(),
                ObjectRegistry.CRIMSON_CABINET.get(),
                ObjectRegistry.CRIMSON_DRAWER.get(),
                ObjectRegistry.CRIMSON_CHAIR.get(),
                ObjectRegistry.CRIMSON_TABLE.get(),
                ObjectRegistry.CRIMSON_SHELF.get(),
                ObjectRegistry.CRIMSON_BIG_TABLE.get()
        );
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        for (Block block : blocks) {
            fireBlock.setFlammable(block, burnOdd, igniteOdd);
        }
    }
}
