package satisfy.candlelight.registry;

import de.cristelknight.doapi.common.block.entity.SideBoardBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.block.entity.*;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class BlockEntityRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static final RegistrySupplier<BlockEntityType<LargeCookingPotBlockEntity>> COOKING_POT_BLOCK_ENTITY = create("cooking_pot", () -> BlockEntityType.Builder.of(LargeCookingPotBlockEntity::new, ObjectRegistry.COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SideBoardBlockEntity>> SIDEBOARD = create("sideboard", () -> BlockEntityType.Builder.of(SideBoardBlockEntity::new, ObjectRegistry.SIDEBOARD.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<TypeWriterEntity>> TYPE_WRITER_BLOCK_ENTITY = create("type_writer", () -> BlockEntityType.Builder.of(TypeWriterEntity::new, ObjectRegistry.TYPEWRITER_IRON.get(), ObjectRegistry.TYPEWRITER_COPPER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingPanBlockEntity>> COOKING_PAN_BLOCK_ENTITY = create("cooking_pan", () -> BlockEntityType.Builder.of(CookingPanBlockEntity::new, ObjectRegistry.COOKING_PAN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<EffectFoodBlockEntity>> EFFECT_FOOD_BLOCK_ENTITY = create("effect_food_block", () -> BlockEntityType.Builder.of(EffectFoodBlockEntity::new, ObjectRegistry.LASAGNE_BLOCK.get(), ObjectRegistry.TOMATO_MOZZARELLA_BLOCK.get(), ObjectRegistry.PORK_RIBS_BLOCK.get(), ObjectRegistry.FRESH_GARDEN_SALAD_BLOCK.get(), ObjectRegistry.BEEF_WELLINGTON_BLOCK.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<DinnerBellBlockEntity>> DINNER_BELL_BLOCK_ENTITY = create("dinner_bell", () -> BlockEntityType.Builder.of(DinnerBellBlockEntity::new, ObjectRegistry.DINNER_BELL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<StoveBlockEntity>> STOVE_BLOCK_ENTITY = create("stove_block", () -> BlockEntityType.Builder.of(StoveBlockEntity::new, ObjectRegistry.COBBLESTONE_STOVE.get(), ObjectRegistry.MUD_STOVE.get(), ObjectRegistry.GRANITE_STOVE.get(), ObjectRegistry.SANDSTONE_STOVE.get(), ObjectRegistry.STONE_BRICKS_STOVE.get(), ObjectRegistry.RED_NETHER_BRICKS_STOVE.get(), ObjectRegistry.DEEPSLATE_STOVE.get(), ObjectRegistry.QUARTZ_STOVE.get(), ObjectRegistry.END_STOVE.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(path, type);
    }

    public static void init(){
        Candlelight.LOGGER.debug("Registering Mod BlockEntities for " + Candlelight.MOD_ID);
        BLOCK_ENTITY_TYPES.register();
    }

}
