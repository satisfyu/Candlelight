package satisfyu.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.block.entity.*;

import java.util.function.Supplier;

public class BlockEntityRegistry {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<CookingPotEntity>> COOKING_POT_BLOCK_ENTITY = create("cooking_pot", () -> BlockEntityType.Builder.of(CookingPotEntity::new, ObjectRegistry.COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WoodFiredOvenBlockEntity>> WOOD_FIRED_OVEN_BLOCK_ENTITY = create("wood_fired_oven",() -> BlockEntityType.Builder.of(WoodFiredOvenBlockEntity::new, ObjectRegistry.SANDSTONE_WOOD_FIRED_OVEN.get(), ObjectRegistry.RED_NETHER_BRICKS_WOOD_FIRED_OVEN.get(), ObjectRegistry.MUD_WOOD_FIRED_OVEN.get(), ObjectRegistry.GRANITE_WOOD_FIRED_OVEN.get(), ObjectRegistry.STONE_BRICKS_WOOD_FIRED_OVEN.get(), ObjectRegistry.COBBLESTONE_WOOD_FIRED_OVEN.get(), ObjectRegistry.QUARTZ_WOOD_FIRED_OVEN.get(), ObjectRegistry.END_WOOD_FIRED_OVEN.get(), ObjectRegistry.DEEPSLATE_WOOD_FIRED_OVEN.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<SideBoardBlockEntity>> SIDEBOARD = create("sideboard", () -> BlockEntityType.Builder.of(SideBoardBlockEntity::new, ObjectRegistry.SIDEBOARD.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<TypeWriterEntity>> TYPE_WRITER_BLOCK_ENTITY = create("type_writer", () -> BlockEntityType.Builder.of(TypeWriterEntity::new, ObjectRegistry.TYPEWRITER_IRON.get(), ObjectRegistry.TYPEWRITER_COPPER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingPanEntity>> COOKING_PAN_BLOCK_ENTITY = create("cooking_pan", () -> BlockEntityType.Builder.of(CookingPanEntity::new, ObjectRegistry.COOKING_PAN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<EffectFoodBlockEntity>> EFFECT_FOOD_BLOCK_ENTITY = create("effect_food_block", () -> BlockEntityType.Builder.of(EffectFoodBlockEntity::new, ObjectRegistry.LASAGNA_BLOCK.get(), ObjectRegistry.TOMATO_MOZZARELLA_BLOCK.get(), ObjectRegistry.PORK_RIBS_BLOCK.get(), ObjectRegistry.BROCCOLI_TOMATO_BLOCK.get(), ObjectRegistry.BEEF_WELLINGTON_BLOCK.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WineStationBlockEntity>> WINE_STATION_BLOCK_ENTITY = create("wine_station", () -> BlockEntityType.Builder.of(WineStationBlockEntity::new, ObjectRegistry.WINE_STATION.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<WineBottleBlockEntity>> WINE_BOTTLE_ENTITY = create("wine_bottle", () -> BlockEntityType.Builder.of(WineBottleBlockEntity::new,
            ObjectRegistry.RED_WINE.get(), ObjectRegistry.PRAETORIAN_WINE.get()).build(null));


    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(path, type);
    }

    public static void init(){
        Candlelight.LOGGER.debug("Registering Mod BlockEntities for " + Candlelight.MOD_ID);
        BLOCK_ENTITY_TYPES.register();
    }

}
