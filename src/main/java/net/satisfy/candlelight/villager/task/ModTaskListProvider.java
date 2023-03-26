package net.satisfy.candlelight.villager.task;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.FindPointOfInterestTask;
import net.minecraft.entity.ai.brain.task.ForgetCompletedPointOfInterestTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.villager.memory.ModMemoryModuleType;
import net.satisfy.candlelight.villager.poi.ModPointOfInterestTypes;

import java.util.Optional;

public class ModTaskListProvider {

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createVineryStandTasks(Brain<VillagerEntity> brain, float speed) {
        return ImmutableList.of(
                Pair.of(0, new ShopAtVineryStandTask(speed, 2, 25, 1200)),
                Pair.of(0, new ForgetCompletedPointOfInterestTask((registryEntry) -> registryEntry.matchesKey(ModPointOfInterestTypes.SHOP_KEY), ModMemoryModuleType.SHOP)),
                Pair.of(10, new FindPointOfInterestTask((registryEntry) -> registryEntry.matchesKey(ModPointOfInterestTypes.SHOP_KEY), ModMemoryModuleType.SHOP, false, Optional.of((byte)0))));
    }

    public static void init() {
        Candlelight.LOGGER.debug("Register " + ModTaskListProvider.class);
    }
}
