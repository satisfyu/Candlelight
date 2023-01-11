package net.satisfy.candlelight.registry;

import daniking.vinery.block.entity.WoodFiredOvenBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.satisfy.candlelight.block.entity.SideBoardBlockEntity;
import net.satisfy.candlelight.util.CandlelightIdentifier;

import java.util.HashMap;
import java.util.Map;

public class CBlockEntityTypes {

    private static final Map<Identifier, BlockEntityType<?>> BLOCK_ENTITY_TYPES = new HashMap<>();

    public static final BlockEntityType<SideBoardBlockEntity> SIDEBOARD = create("sideboard", FabricBlockEntityTypeBuilder.create(SideBoardBlockEntity::new, ObjectRegistry.SIDEBOARD).build());



    private static <T extends BlockEntityType<?>> T create(final String path, final T type) {
        BLOCK_ENTITY_TYPES.put(new CandlelightIdentifier(path), type);
        return type;
    }

    public static void init() {
        for (Map.Entry<Identifier, BlockEntityType<?>> entry : BLOCK_ENTITY_TYPES.entrySet()) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, entry.getKey(), entry.getValue());
        }
    }
}
