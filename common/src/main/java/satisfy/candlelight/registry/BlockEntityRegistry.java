package satisfy.candlelight.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfy.candlelight.Candlelight;
import satisfy.candlelight.block.entity.*;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static final RegistrySupplier<BlockEntityType<CookingPotBlockEntity>> COOKING_POT_BLOCK_ENTITY = create("cooking_pot", () -> BlockEntityType.Builder.of(CookingPotBlockEntity::new, ObjectRegistry.COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<TypeWriterEntity>> TYPE_WRITER_BLOCK_ENTITY = create("type_writer", () -> BlockEntityType.Builder.of(TypeWriterEntity::new, ObjectRegistry.TYPEWRITER_IRON.get(), ObjectRegistry.TYPEWRITER_COPPER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingPanBlockEntity>> COOKING_PAN_BLOCK_ENTITY = create("cooking_pan", () -> BlockEntityType.Builder.of(CookingPanBlockEntity::new, ObjectRegistry.COOKING_PAN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<EffectFoodBlockEntity>> EFFECT_FOOD_BLOCK_ENTITY = create("effect_food_block", () -> BlockEntityType.Builder.of(EffectFoodBlockEntity::new, ObjectRegistry.LASAGNA_BLOCK.get(), ObjectRegistry.TOMATO_MOZZARELLA_BLOCK.get(), ObjectRegistry.PORK_RIBS_BLOCK.get(), ObjectRegistry.LETTUCE_TOMATO_BLOCK.get(), ObjectRegistry.BEEF_WELLINGTON_BLOCK.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(path, type);
    }

    public static void init(){
        Candlelight.LOGGER.debug("Registering Mod BlockEntities for " + Candlelight.MOD_ID);
        BLOCK_ENTITY_TYPES.register();
    }

}
