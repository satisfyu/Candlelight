package net.satisfy.candlelight.registry;

import de.cristelknight.doapi.DoApiCommonEP;
import net.minecraft.world.level.block.Block;

public class FlammableBlockRegistry {

    public static void init() {
        add(5, 20, ObjectRegistry.FLOORBOARD.get());
        add(5, 20, ObjectRegistry.OAK_CABINET.get());
        add(5, 20, ObjectRegistry.OAK_DRAWER.get());
        add(5, 20, ObjectRegistry.OAK_CHAIR.get());
        add(5, 20, ObjectRegistry.OAK_TABLE.get());
        add(5, 20, ObjectRegistry.OAK_SHELF.get());
        add(5, 20, ObjectRegistry.OAK_BIG_TABLE.get());
        add(5, 20, ObjectRegistry.CABINET.get());
        add(5, 20, ObjectRegistry.DRAWER.get());
        add(5, 20, ObjectRegistry.CHAIR.get());
        add(5, 20, ObjectRegistry.TABLE.get());
        add(5, 20, ObjectRegistry.SIDEBOARD.get());
        add(5, 20, ObjectRegistry.SIDE_TABLE.get());
        add(5, 20, ObjectRegistry.LAMP.get());
        add(5, 20, ObjectRegistry.SOFA.get());
        add(5, 20, ObjectRegistry.SPRUCE_CABINET.get());
        add(5, 20, ObjectRegistry.SPRUCE_DRAWER.get());
        add(5, 20, ObjectRegistry.SPRUCE_CHAIR.get());
        add(5, 20, ObjectRegistry.SPRUCE_TABLE.get());
        add(5, 20, ObjectRegistry.SPRUCE_SHELF.get());
        add(5, 20, ObjectRegistry.SPRUCE_BIG_TABLE.get());
        add(5, 20, ObjectRegistry.BIRCH_CABINET.get());
        add(5, 20, ObjectRegistry.BIRCH_DRAWER.get());
        add(5, 20, ObjectRegistry.BIRCH_CHAIR.get());
        add(5, 20, ObjectRegistry.BIRCH_TABLE.get());
        add(5, 20, ObjectRegistry.BIRCH_SHELF.get());
        add(5, 20, ObjectRegistry.BIRCH_BIG_TABLE.get());
        add(5, 20, ObjectRegistry.MANGROVE_CABINET.get());
        add(5, 20, ObjectRegistry.MANGROVE_DRAWER.get());
        add(5, 20, ObjectRegistry.MANGROVE_CHAIR.get());
        add(5, 20, ObjectRegistry.MANGROVE_TABLE.get());
        add(5, 20, ObjectRegistry.MANGROVE_SHELF.get());
        add(5, 20, ObjectRegistry.MANGROVE_BIG_TABLE.get());
        add(5, 20, ObjectRegistry.JUNGLE_CABINET.get());
        add(5, 20, ObjectRegistry.JUNGLE_DRAWER.get());
        add(5, 20, ObjectRegistry.JUNGLE_CHAIR.get());
        add(5, 20, ObjectRegistry.JUNGLE_TABLE.get());
        add(5, 20, ObjectRegistry.JUNGLE_SHELF.get());
        add(5, 20, ObjectRegistry.JUNGLE_BIG_TABLE.get());
        add(5, 20, ObjectRegistry.ACACIA_CABINET.get());
        add(5, 20, ObjectRegistry.ACACIA_DRAWER.get());
        add(5, 20, ObjectRegistry.ACACIA_CHAIR.get());
        add(5, 20, ObjectRegistry.ACACIA_TABLE.get());
        add(5, 20, ObjectRegistry.ACACIA_SHELF.get());
        add(5, 20, ObjectRegistry.ACACIA_BIG_TABLE.get());
        add(5, 20, ObjectRegistry.DARK_OAK_CABINET.get());
        add(5, 20, ObjectRegistry.DARK_OAK_DRAWER.get());
        add(5, 20, ObjectRegistry.DARK_OAK_CHAIR.get());
        add(5, 20, ObjectRegistry.DARK_OAK_TABLE.get());
        add(5, 20, ObjectRegistry.DARK_OAK_SHELF.get());
        add(5, 20, ObjectRegistry.DARK_OAK_BIG_TABLE.get());
        add(5, 20, ObjectRegistry.WARPED_CABINET.get());
        add(5, 20, ObjectRegistry.WARPED_DRAWER.get());
        add(5, 20, ObjectRegistry.WARPED_CHAIR.get());
        add(5, 20, ObjectRegistry.WARPED_TABLE.get());
        add(5, 20, ObjectRegistry.WARPED_SHELF.get());
        add(5, 20, ObjectRegistry.WARPED_BIG_TABLE.get());
        add(5, 20, ObjectRegistry.CRIMSON_CABINET.get());
        add(5, 20, ObjectRegistry.CRIMSON_DRAWER.get());
        add(5, 20, ObjectRegistry.CRIMSON_CHAIR.get());
        add(5, 20, ObjectRegistry.CRIMSON_TABLE.get());
        add(5, 20, ObjectRegistry.CRIMSON_SHELF.get());
        add(5, 20, ObjectRegistry.CRIMSON_BIG_TABLE.get());
    }

    public static void add(int burnOdd, int igniteOdd, Block... blocks) {
        DoApiCommonEP.addFlammable(burnOdd, igniteOdd, blocks);
    }
}
