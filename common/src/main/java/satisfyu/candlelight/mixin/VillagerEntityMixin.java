package satisfyu.candlelight.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.candlelight.villager.memory.ModMemoryModuleType;
import satisfyu.candlelight.villager.poi.ModPointOfInterestTypes;
import satisfyu.candlelight.villager.task.ModTaskListProvider;

import java.util.Map;
import java.util.function.BiPredicate;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin {
    @Shadow
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            ModMemoryModuleType.SHOP.get(), ModMemoryModuleType.LAST_SHOPED.get(),
            //Minecraft
            MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE,
            MemoryModuleType.MEETING_POINT, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.WALK_TARGET,
             MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.BREED_TARGET, MemoryModuleType.PATH, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_BED,
            MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE,
            MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WOKEN, MemoryModuleType.LAST_WORKED_AT_POI);

    @Shadow
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<Villager, Holder<PoiType>>> POINTS_OF_INTEREST = ImmutableMap.of(
            MemoryModuleType.HOME, (villager, registryEntry) -> registryEntry.is(PoiTypes.HOME),
            MemoryModuleType.JOB_SITE, (villager, registryEntry) -> villager.getVillagerData().getProfession().heldJobSite().test(registryEntry),
            MemoryModuleType.POTENTIAL_JOB_SITE, (villager, registryEntry) -> VillagerProfession.ALL_ACQUIRABLE_JOBS.test(registryEntry),
            MemoryModuleType.MEETING_POINT, (villager, registryEntry) -> registryEntry.is(PoiTypes.MEETING),
            ModMemoryModuleType.SHOP.get(), (villager, registryEntry) -> registryEntry.is(ModPointOfInterestTypes.SHOP_KEY));




    @Shadow public abstract void releaseTicketFor(MemoryModuleType<GlobalPos> memoryModuleType);

    @Inject(method = "initBrain", at = @At("TAIL"))
    private void injectMethod(Brain<Villager> brain, CallbackInfo ci) {
        brain.addActivity(Activity.CORE, ModTaskListProvider.createVineryStandTasks(brain, 0.5f));
    }

    @Inject(method = "releaseAllTickets", at = @At("TAIL"))
    private void releaseShopTicket(CallbackInfo ci) {
            this.releaseTicketFor(ModMemoryModuleType.SHOP);
    }
}
