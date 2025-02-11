package net.satisfy.candlelight.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.block.entity.*;
import net.satisfy.candlelight.core.util.CandlelightIdentifier;
import net.satisfy.farm_and_charm.core.block.entity.EffectFoodBlockEntity;

import java.util.HashSet;
import java.util.function.Supplier;

public class EntityTypeRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> STORAGE_BLOCK_ENTITY = registerBlockEntity("storage", () -> BlockEntityType.Builder.of(StorageBlockEntity::new, StorageTypeRegistry.registerBlocks(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<SideBoardBlockEntity>> SIDEBOARD_BLOCK_ENTITY = registerBlockEntity("sideboard", () -> BlockEntityType.Builder.of(SideBoardBlockEntity::new, ObjectRegistry.SIDEBOARD.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<LargeCookingPotBlockEntity>> COOKING_POT_BLOCK_ENTITY = registerBlockEntity("cooking_pot", () -> BlockEntityType.Builder.of(LargeCookingPotBlockEntity::new, ObjectRegistry.COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<TypeWriterEntity>> TYPE_WRITER_BLOCK_ENTITY = registerBlockEntity("type_writer", () -> BlockEntityType.Builder.of(TypeWriterEntity::new, ObjectRegistry.TYPEWRITER_IRON.get(), ObjectRegistry.TYPEWRITER_COPPER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingPanBlockEntity>> COOKING_PAN_BLOCK_ENTITY = registerBlockEntity("cooking_pan", () -> BlockEntityType.Builder.of(CookingPanBlockEntity::new, ObjectRegistry.COOKING_PAN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<EffectFoodBlockEntity>> EFFECT_FOOD_BLOCK_ENTITY = registerBlockEntity("effect_food_block", () -> BlockEntityType.Builder.of(EffectFoodBlockEntity::new, ObjectRegistry.LASAGNE_BLOCK.get(), ObjectRegistry.TOMATO_MOZZARELLA_BLOCK.get(), ObjectRegistry.PORK_RIBS_BLOCK.get(), ObjectRegistry.FRESH_GARDEN_SALAD_BLOCK.get(), ObjectRegistry.BEEF_WELLINGTON_BLOCK.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<DinnerBellBlockEntity>> DINNER_BELL_BLOCK_ENTITY = registerBlockEntity("dinner_bell", () -> BlockEntityType.Builder.of(DinnerBellBlockEntity::new, ObjectRegistry.DINNER_BELL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CStoveBlockEntity>> STOVE_BLOCK_ENTITY = registerBlockEntity("stove_block", () -> BlockEntityType.Builder.of(CStoveBlockEntity::new, ObjectRegistry.COBBLESTONE_STOVE.get(), ObjectRegistry.MUD_STOVE.get(), ObjectRegistry.GRANITE_STOVE.get(), ObjectRegistry.SANDSTONE_STOVE.get(), ObjectRegistry.STONE_BRICKS_STOVE.get(), ObjectRegistry.RED_NETHER_BRICKS_STOVE.get(), ObjectRegistry.DEEPSLATE_STOVE.get(), ObjectRegistry.QUARTZ_STOVE.get(), ObjectRegistry.END_STOVE.get(), ObjectRegistry.BASALT_STOVE.get(), ObjectRegistry.BAMBOO_STOVE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CabinetBlockEntity>> CABINET_BLOCK_ENTITY = registerBlockEntity("cabinet", () -> BlockEntityType.Builder.of(CabinetBlockEntity::new, StorageTypeRegistry.registerBlocks(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<CompletionistBannerEntity>> CANDLELIGHT_BANNER = registerBlockEntity("candlelight_banner", () -> BlockEntityType.Builder.of(CompletionistBannerEntity::new, ObjectRegistry.CANDLELIGHT_BANNER.get(), ObjectRegistry.CANDLELIGHT_WALL_BANNER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<TableSetBlockEntity>> TABLE_SET_BLOCK_ENTITY = registerBlockEntity("table_set", () -> BlockEntityType.Builder.of(TableSetBlockEntity::new, ObjectRegistry.TABLE_SET.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new CandlelightIdentifier(path), type);
    }

    public static void init() {
        ENTITY_TYPES.register();
        BLOCK_ENTITY_TYPES.register();
    }
}
