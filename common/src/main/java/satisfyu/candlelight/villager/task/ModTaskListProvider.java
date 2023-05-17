package satisfyu.candlelight.villager.task;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.ValidateNearbyPoi;
import net.minecraft.world.entity.npc.Villager;
import satisfyu.candlelight.villager.memory.ModMemoryModuleType;
import satisfyu.candlelight.villager.poi.ModPointOfInterestTypes;

import java.util.Optional;

public class ModTaskListProvider {

    public static ImmutableList<Pair<Integer, ? extends Behavior<? super Villager>>> createVineryStandTasks(Brain<Villager> brain, float speed) {
        return ImmutableList.of(
                Pair.of(0, new ShopAtVineryStandTask<>(speed, 2, 25, 1200)),
                Pair.of(0, new ValidateNearbyPoi((registryEntry) -> registryEntry.is(ModPointOfInterestTypes.SHOP_KEY), ModMemoryModuleType.SHOP.get())),
                Pair.of(10, new AcquirePoi((registryEntry) -> registryEntry.is(ModPointOfInterestTypes.SHOP_KEY), ModMemoryModuleType.SHOP.get(), false, Optional.of((byte)0))));
    }

}
