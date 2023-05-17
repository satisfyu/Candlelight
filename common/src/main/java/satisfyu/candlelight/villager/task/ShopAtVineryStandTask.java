package satisfyu.candlelight.villager.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import satisfyu.candlelight.block.entity.WineStationBlockEntity;
import satisfyu.candlelight.registry.BlockEntityRegistry;
import satisfyu.candlelight.registry.ObjectRegistry;
import satisfyu.candlelight.villager.memory.ModMemoryModuleType;

import java.util.Optional;

public class ShopAtVineryStandTask<E extends Villager> extends Behavior<E> {

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
        super(ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleType.SHOP.get(), MemoryStatus.VALUE_PRESENT, ModMemoryModuleType.LAST_SHOPED.get(), MemoryStatus.REGISTERED));
        this.destination = ModMemoryModuleType.SHOP.get();
        this.speed = speed;
        this.completionRange = completionRange;
        this.maxRange = maxRange;
        this.maxRunTime = maxRunTime;
    }



    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, Villager villager) {
        if (villager.isBaby() || wasHurt(villager) || isHostileNearby(villager)) {
            return false;
        }
        Brain<?> brain = villager.getBrain();

        GlobalPos globalPos = brain.getMemory(ModMemoryModuleType.SHOP.get()).get();
        if (world.dimension() != globalPos.dimension()) {
            return false;
        }
        BlockState blockState = world.getBlockState(globalPos.pos());
        Optional<WineStationBlockEntity> optionalBlockEntity = world.getBlockEntity(globalPos.pos(), BlockEntityRegistry.WINE_STATION_BLOCK_ENTITY.get());
        if (optionalBlockEntity.isPresent() && !optionalBlockEntity.get().hasWine()) {
            return false;
        }

        Optional<Long> optionalLastShopped = brain.getMemory(ModMemoryModuleType.LAST_SHOPED.get());
        if (optionalLastShopped.isPresent()) {
            long l = world.getGameTime() - optionalLastShopped.get();
            if (l > 0L && l < shopCooldown) {
                return false;
            }
        }
        return globalPos.pos().closerToCenterThan(villager.position(), distanceToShop) && blockState.getBlock() == ObjectRegistry.WINE_STATION;
    }

    @Override
    protected void tick(ServerLevel serverWorld, E villagerEntity, long l) {
        Brain<?> brain = villagerEntity.getBrain();

        Optional<GlobalPos> optional = brain.getMemory(this.destination);
        if (optional.isPresent()) {
            GlobalPos pos = optional.get();
            if (!this.dimensionMismatches(serverWorld, pos) && !this.shouldGiveUp(serverWorld, villagerEntity)) {
                if (this.exceedsMaxRange(villagerEntity, pos)) {
                    Vec3 vec3d = null;
                    int i = 0;

                    for(; i < 1000 && (vec3d == null || this.exceedsMaxRange(villagerEntity, GlobalPos.of(serverWorld.dimension(), new BlockPos(vec3d)))); ++i) {
                        vec3d = DefaultRandomPos.getPosTowards(villagerEntity, 15, 7, Vec3.atBottomCenterOf(pos.pos()), 1.5707963705062866);
                    }

                    if (i == 1000) {
                        this.giveUp(villagerEntity, l);
                        return;
                    }
                    brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, this.speed, this.completionRange));
                } else {
                    if (!this.reachedDestination(serverWorld, villagerEntity, pos)) {
                        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(pos.pos(), this.speed, this.completionRange));
                    } else {
                        // Is at Shop
                        Optional<WineStationBlockEntity> optionalBlockEntity = villagerEntity.level.getBlockEntity(pos.pos(), BlockEntityRegistry.WINE_STATION_BLOCK_ENTITY.get());
                        optionalBlockEntity.ifPresent(wineStationBlockEntity -> wineStationBlockEntity.buyWine(villagerEntity));

                        forgetVineryStand(serverWorld, villagerEntity, brain);
                    }
                }
            } else {
                this.giveUp(villagerEntity, l);
            }
        }
    }

    private void giveUp(Villager villager, long time) {
        Brain<?> brain = villager.getBrain();
        villager.releasePoi(this.destination);
        brain.eraseMemory(this.destination);
        brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
    }

    private boolean shouldGiveUp(ServerLevel world, Villager villager) {
        Optional<Long> optional = villager.getBrain().getMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        return optional.filter(aLong -> world.getGameTime() - aLong > (long) this.maxRunTime).isPresent();
    }

    private void forgetVineryStand(ServerLevel world, Villager villager, Brain<?> brain) {
        villager.releasePoi(ModMemoryModuleType.SHOP.get());

        brain.eraseMemory(this.destination);
        brain.setMemory(ModMemoryModuleType.LAST_SHOPED.get(), world.getGameTime());
        shopCooldown = RandomSource.create().nextIntBetweenInclusive(minTimeToShop, maxTimeToShop);
    }

    private boolean exceedsMaxRange(Villager villager, GlobalPos pos) {
        return pos.pos().distManhattan(villager.blockPosition()) > this.maxRange;
    }

    private boolean dimensionMismatches(ServerLevel world, GlobalPos pos) {
        return pos.dimension() != world.dimension();
    }

    private boolean reachedDestination(ServerLevel world, Villager villager, GlobalPos pos) {
        return pos.dimension() == world.dimension() && pos.pos().distManhattan(villager.blockPosition()) <= this.completionRange;
    }

    public static boolean isHostileNearby(LivingEntity entity) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_HOSTILE);
    }

    public static boolean wasHurt(LivingEntity entity) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
    }
}
