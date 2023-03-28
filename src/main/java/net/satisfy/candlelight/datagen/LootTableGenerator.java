package net.satisfy.candlelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.AlternativeLootCondition;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.StatePredicate;
import net.satisfy.candlelight.block.TableSetBlock;
import net.satisfy.candlelight.registry.ObjectRegistry;


public class LootTableGenerator extends FabricBlockLootTableProvider {

    private static final float[] SAPLING_DROP_CHANCE = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

    protected LootTableGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }


    @Override
    protected void generateBlockLootTables() {
        this.addDrop(ObjectRegistry.FLOORBOARD);
        this.addDrop(ObjectRegistry.TOMATO_CRATE);
        this.addDrop(ObjectRegistry.BROCCOLI_CRATE);
        this.addDrop(ObjectRegistry.STRAWBERRY_CRATE);
        this.addDrop(ObjectRegistry.SOFA);
        this.addDrop(ObjectRegistry.CHAIR);
        this.addDrop(ObjectRegistry.TABLE);
        this.addDrop(ObjectRegistry.LAMP);
        this.addDrop(ObjectRegistry.SIDEBOARD);
        this.addDrop(ObjectRegistry.DRAWER);
        this.addDrop(ObjectRegistry.CABINET);
        this.addDrop(ObjectRegistry.FLOORBOARD);
        this.addDrop(ObjectRegistry.WINE_STATION);
        this.addDrop(ObjectRegistry.CAKE_STAND);
        this.addDrop(ObjectRegistry.COOKING_POT);
        this.addDrop(ObjectRegistry.COOKING_PAN);
        this.addDrop(ObjectRegistry.TRAY);
        this.addDrop(ObjectRegistry.TABLE_SET, LootTableGenerator::tableSetDrops);
        this.addDrop(ObjectRegistry.STRAWBERRY_JAM);
        this.addDrop(ObjectRegistry.RED_WINE);
        this.addDrop(ObjectRegistry.PRAETORIAN_WINE);
        this.addDrop(ObjectRegistry.TABLE_SIGN);
        this.addDrop(ObjectRegistry.STREET_SIGN);
        this.addDrop(ObjectRegistry.PAINTING);
        this.addDrop(ObjectRegistry.HEARTH);
        this.addDrop(ObjectRegistry.ROSE);
        this.addPottedPlantDrop(ObjectRegistry.POTTED_ROSE);
        this.addDrop(ObjectRegistry.APPLE_TREE_SAPLING);
        this.addDrop(ObjectRegistry.APPLE_LEAVES, b -> leavesDrop(b, ObjectRegistry.APPLE_TREE_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(ObjectRegistry.JEWELRY_BOX);
        this.addDrop(ObjectRegistry.TYPEWRITER_IRON);
        this.addDrop(ObjectRegistry.TYPEWRITER_COPPER);
        this.addDrop(ObjectRegistry.OAK_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.OAK_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.OAK_DRAWER);
        this.addDrop(ObjectRegistry.OAK_CABINET);
        this.addDrop(ObjectRegistry.OAK_TABLE);
        this.addDrop(ObjectRegistry.OAK_CHAIR);
        this.addDrop(ObjectRegistry.COBBLESTONE_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.COBBLESTONE_STOVE);
        this.addDrop(ObjectRegistry.COBBLESTONE_KITCHEN_SINK);
        this.addDrop(ObjectRegistry.BIRCH_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.BIRCH_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.BIRCH_DRAWER);
        this.addDrop(ObjectRegistry.BIRCH_CABINET);
        this.addDrop(ObjectRegistry.BIRCH_TABLE);
        this.addDrop(ObjectRegistry.BIRCH_CHAIR);
        this.addDrop(ObjectRegistry.SANDSTONE_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.SANDSTONE_STOVE);
        this.addDrop(ObjectRegistry.SANDSTONE_KITCHEN_SINK);
        this.addDrop(ObjectRegistry.SPRUCE_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.SPRUCE_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.SPRUCE_DRAWER);
        this.addDrop(ObjectRegistry.SPRUCE_CABINET);
        this.addDrop(ObjectRegistry.SPRUCE_TABLE);
        this.addDrop(ObjectRegistry.SPRUCE_CHAIR);
        this.addDrop(ObjectRegistry.STONE_BRICKS_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.STONE_BRICKS_STOVE);
        this.addDrop(ObjectRegistry.STONE_BRICKS_KITCHEN_SINK);
        this.addDrop(ObjectRegistry.DARK_OAK_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.DARK_OAK_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.DARK_OAK_DRAWER);
        this.addDrop(ObjectRegistry.DARK_OAK_CABINET);
        this.addDrop(ObjectRegistry.DARK_OAK_TABLE);
        this.addDrop(ObjectRegistry.DARK_OAK_CHAIR);
        this.addDrop(ObjectRegistry.DEEPSLATE_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.DEEPSLATE_STOVE);
        this.addDrop(ObjectRegistry.DEEPSLATE_KITCHEN_SINK);
        this.addDrop(ObjectRegistry.ACACIA_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.ACACIA_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.ACACIA_DRAWER);
        this.addDrop(ObjectRegistry.ACACIA_CABINET);
        this.addDrop(ObjectRegistry.ACACIA_TABLE);
        this.addDrop(ObjectRegistry.ACACIA_CHAIR);
        this.addDrop(ObjectRegistry.GRANITE_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.GRANITE_STOVE);
        this.addDrop(ObjectRegistry.GRANITE_KITCHEN_SINK);
        this.addDrop(ObjectRegistry.JUNGLE_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.JUNGLE_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.JUNGLE_DRAWER);
        this.addDrop(ObjectRegistry.JUNGLE_CABINET);
        this.addDrop(ObjectRegistry.JUNGLE_TABLE);
        this.addDrop(ObjectRegistry.JUNGLE_CHAIR);
        this.addDrop(ObjectRegistry.END_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.END_STOVE);
        this.addDrop(ObjectRegistry.END_KITCHEN_SINK);
        this.addDrop(ObjectRegistry.MANGROVE_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.MANGROVE_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.MANGROVE_DRAWER);
        this.addDrop(ObjectRegistry.MANGROVE_CABINET);
        this.addDrop(ObjectRegistry.MANGROVE_TABLE);
        this.addDrop(ObjectRegistry.MANGROVE_CHAIR);
        this.addDrop(ObjectRegistry.MUD_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.MUD_STOVE);
        this.addDrop(ObjectRegistry.MUD_KITCHEN_SINK);
        this.addDrop(ObjectRegistry.CRIMSON_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.CRIMSON_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.CRIMSON_DRAWER);
        this.addDrop(ObjectRegistry.CRIMSON_CABINET);
        this.addDrop(ObjectRegistry.CRIMSON_TABLE);
        this.addDrop(ObjectRegistry.CRIMSON_CHAIR);
        this.addDrop(ObjectRegistry.RED_NETHER_BRICKS_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.RED_NETHER_BRICKS_STOVE);
        this.addDrop(ObjectRegistry.RED_NETHER_BRICKS_KITCHEN_SINK);
        this.addDrop(ObjectRegistry.WARPED_WINE_RACK_BIG);
        this.addDrop(ObjectRegistry.WARPED_WINE_RACK_SMALL);
        this.addDrop(ObjectRegistry.WARPED_DRAWER);
        this.addDrop(ObjectRegistry.WARPED_CABINET);
        this.addDrop(ObjectRegistry.WARPED_CHAIR);
        this.addDrop(ObjectRegistry.WARPED_TABLE);
        this.addDrop(ObjectRegistry.QUARTZ_WOOD_FIRED_OVEN);
        this.addDrop(ObjectRegistry.QUARTZ_STOVE);
        this.addDrop(ObjectRegistry.QUARTZ_KITCHEN_SINK);
    }

    public static LootTable.Builder tableSetDrops(Block block) {
        BlockStatePropertyLootCondition.Builder all = BlockStatePropertyLootCondition.builder(ObjectRegistry.TABLE_SET).properties(StatePredicate.Builder.create().exactMatch(TableSetBlock.PLATE_TYPE, TableSetBlock.PlateType.ALL));
        AlternativeLootCondition.Builder glass = BlockStatePropertyLootCondition.builder(ObjectRegistry.TABLE_SET).properties(StatePredicate.Builder.create().exactMatch(TableSetBlock.PLATE_TYPE, TableSetBlock.PlateType.GLASS)).or(all);
        AlternativeLootCondition.Builder napkin = BlockStatePropertyLootCondition.builder(ObjectRegistry.TABLE_SET).properties(StatePredicate.Builder.create().exactMatch(TableSetBlock.PLATE_TYPE, TableSetBlock.PlateType.NAPKIN)).or(all);

        return LootTable.builder().pool(LootPool.builder().with(
                ItemEntry.builder(ObjectRegistry.TABLE_SET)
        )).pool(LootPool.builder().with(
                ItemEntry.builder(ObjectRegistry.GLASS)
        ).conditionally(glass)).pool(LootPool.builder().with(
                ItemEntry.builder(ObjectRegistry.NAPKIN)
        ).conditionally(napkin));
    }
}