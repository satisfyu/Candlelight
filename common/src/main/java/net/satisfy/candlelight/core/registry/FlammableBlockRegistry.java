package net.satisfy.candlelight.core.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

import static net.satisfy.candlelight.core.registry.ObjectRegistry.*;

public class FlammableBlockRegistry {

    public static void init() {
        addFlammable(5, 20,
                BAMBOO_STOVE.get(),
                FLOORBOARD.get(),
                OAK_CABINET.get(),
                OAK_DRAWER.get(),
                OAK_CHAIR.get(),
                OAK_TABLE.get(),
                OAK_SHELF.get(),
                OAK_BIG_TABLE.get(),
                CABINET.get(),
                DRAWER.get(),
                CHAIR.get(),
                TABLE.get(),
                SIDEBOARD.get(),
                SIDE_TABLE.get(),
                LAMP.get(),
                SOFA.get(),
                SPRUCE_CABINET.get(),
                SPRUCE_DRAWER.get(),
                SPRUCE_CHAIR.get(),
                SPRUCE_TABLE.get(),
                SPRUCE_SHELF.get(),
                SPRUCE_BIG_TABLE.get(),
                BIRCH_CABINET.get(),
                BIRCH_DRAWER.get(),
                BIRCH_CHAIR.get(),
                BIRCH_TABLE.get(),
                BIRCH_SHELF.get(),
                BIRCH_BIG_TABLE.get(),
                MANGROVE_CABINET.get(),
                MANGROVE_DRAWER.get(),
                MANGROVE_CHAIR.get(),
                MANGROVE_TABLE.get(),
                MANGROVE_SHELF.get(),
                MANGROVE_BIG_TABLE.get(),
                JUNGLE_CABINET.get(),
                JUNGLE_DRAWER.get(),
                JUNGLE_CHAIR.get(),
                JUNGLE_TABLE.get(),
                JUNGLE_SHELF.get(),
                JUNGLE_BIG_TABLE.get(),
                ACACIA_CABINET.get(),
                ACACIA_DRAWER.get(),
                ACACIA_CHAIR.get(),
                ACACIA_TABLE.get(),
                ACACIA_SHELF.get(),
                ACACIA_BIG_TABLE.get(),
                DARK_OAK_CABINET.get(),
                DARK_OAK_DRAWER.get(),
                DARK_OAK_CHAIR.get(),
                DARK_OAK_TABLE.get(),
                DARK_OAK_SHELF.get(),
                DARK_OAK_BIG_TABLE.get(),
                WARPED_CABINET.get(),
                WARPED_DRAWER.get(),
                WARPED_CHAIR.get(),
                WARPED_TABLE.get(),
                WARPED_SHELF.get(),
                WARPED_BIG_TABLE.get(),
                CRIMSON_CABINET.get(),
                CRIMSON_DRAWER.get(),
                CRIMSON_CHAIR.get(),
                CRIMSON_TABLE.get(),
                CRIMSON_SHELF.get(),
                CRIMSON_BIG_TABLE.get()
        );
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        for (Block block : blocks) {
            fireBlock.setFlammable(block, burnOdd, igniteOdd);
        }
    }
}
