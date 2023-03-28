package net.satisfy.candlelight.villager.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.satisfy.candlelight.block.entity.WineStationBlockEntity;
import net.satisfy.candlelight.registry.CandlelightEntityTypes;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.villager.memory.ModMemoryModuleType;

import java.util.Optional;

public class ShopAtVineryStandTask<E extends VillagerEntity> extends Task<E> {

    private final int minTimeToShop = 6000;
    private final int maxTimeToShop = 24000;
    private int shopCooldown;
    private final int distanceToShop = 15;
    private final MemoryModuleType<GlobalPos> destination;
    private final float speed;
    private final int completionRange;
    private final int maxRange;
    private final int maxRunTime;

    public ShopAtVineryStandTask(float speed, int completionRange, int maxRange, int maxRunTime) {
        super(ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
                ModMemoryModuleType.SHOP, MemoryModuleState.VALUE_PRESENT, ModMemoryModuleType.LAST_SHOPED, MemoryModuleState.REGISTERED));
        this.destination = ModMemoryModuleType.SHOP;
        this.speed = speed;
        this.completionRange = completionRange;
        this.maxRange = maxRange;
        this.maxRunTime = maxRunTime;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, VillagerEntity villager) {
        if (villager.isBaby() || wasHurt(villager) || isHostileNearby(villager)) {
            return false;
        }
        Brain<?> brain = villager.getBrain();

        GlobalPos globalPos = brain.getOptionalMemory(ModMemoryModuleType.SHOP).get();
        if (world.getRegistryKey() != globalPos.getDimension()) {
            return false;
        }
        BlockState blockState = world.getBlockState(globalPos.getPos());
        Optional<WineStationBlockEntity> optionalBlockEntity = world.getBlockEntity(globalPos.getPos(), CandlelightEntityTypes.WINE_STATION_BLOCK_ENTITY);
        if (optionalBlockEntity.isPresent() && !optionalBlockEntity.get().hasWine()) {
            return false;
        }

        Optional<Long> optionalLastShopped = brain.getOptionalMemory(ModMemoryModuleType.LAST_SHOPED);
        if (optionalLastShopped.isPresent()) {
            long l = world.getTime() - optionalLastShopped.get();
            if (l > 0L && l < shopCooldown) {
                return false;
            }
        }
        return globalPos.getPos().isWithinDistance(villager.getPos(), distanceToShop) && blockState.getBlock() == ObjectRegistry.WINE_STATION;
    }

    @Override
    protected void run(ServerWorld serverWorld, E villagerEntity, long l) {
        Brain<?> brain = villagerEntity.getBrain();

        Optional<GlobalPos> optional = brain.getOptionalMemory(this.destination);
        if (optional.isPresent()) {
            GlobalPos pos = optional.get();
            if (!this.dimensionMismatches(serverWorld, pos) && !this.shouldGiveUp(serverWorld, villagerEntity)) {
                if (this.exceedsMaxRange(villagerEntity, pos)) {
                    Vec3d vec3d = null;
                    int i = 0;

                    for(; i < 1000 && (vec3d == null || this.exceedsMaxRange(villagerEntity, GlobalPos.create(serverWorld.getRegistryKey(), new BlockPos(vec3d)))); ++i) {
                        vec3d = NoPenaltyTargeting.findTo(villagerEntity, 15, 7, Vec3d.ofBottomCenter(pos.getPos()), 1.5707963705062866);
                    }

                    if (i == 1000) {
                        this.giveUp(villagerEntity, l);
                        return;
                    }
                    brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, this.speed, this.completionRange));
                } else {
                    if (!this.reachedDestination(serverWorld, villagerEntity, pos)) {
                        brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(pos.getPos(), this.speed, this.completionRange));
                    } else {
                        // Is at Shop
                        Optional<WineStationBlockEntity> optionalBlockEntity = villagerEntity.world.getBlockEntity(pos.getPos(), CandlelightEntityTypes.WINE_STATION_BLOCK_ENTITY);
                        if (optionalBlockEntity.isPresent()) {
                            optionalBlockEntity.get().buyWine(villagerEntity);
                        }

                        forgetVineryStand(serverWorld, villagerEntity, brain);
                    }
                }
            } else {
                this.giveUp(villagerEntity, l);
            }
        }
    }

    private void giveUp(VillagerEntity villager, long time) {
        Brain<?> brain = villager.getBrain();
        villager.releaseTicketFor(this.destination);
        brain.forget(this.destination);
        brain.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
    }

    private boolean shouldGiveUp(ServerWorld world, VillagerEntity villager) {
        Optional<Long> optional = villager.getBrain().getOptionalMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        if (optional.isPresent()) {
            return world.getTime() - optional.get() > (long)this.maxRunTime;
        } else {
            return false;
        }
    }

    private void forgetVineryStand(ServerWorld world, VillagerEntity villager, Brain<?> brain) {
        villager.releaseTicketFor(ModMemoryModuleType.SHOP);

        brain.forget(this.destination);
        brain.remember(ModMemoryModuleType.LAST_SHOPED, world.getTime());
        shopCooldown = Random.create().nextBetween(minTimeToShop, maxTimeToShop);
    }

    private boolean exceedsMaxRange(VillagerEntity villager, GlobalPos pos) {
        return pos.getPos().getManhattanDistance(villager.getBlockPos()) > this.maxRange;
    }

    private boolean dimensionMismatches(ServerWorld world, GlobalPos pos) {
        return pos.getDimension() != world.getRegistryKey();
    }

    private boolean reachedDestination(ServerWorld world, VillagerEntity villager, GlobalPos pos) {
        return pos.getDimension() == world.getRegistryKey() && pos.getPos().getManhattanDistance(villager.getBlockPos()) <= this.completionRange;
    }

    public static boolean isHostileNearby(LivingEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_HOSTILE);
    }

    public static boolean wasHurt(LivingEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.HURT_BY);
    }
}
