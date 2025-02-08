package net.satisfy.candlelight.core.mixin;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Zombie.class)
public abstract class ZombieMixin {
    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void addCookingHat(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag compound, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (Math.random() < 0.03) {
            ((Zombie)(Object)this).setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.COOKING_HAT.get()));
        }
    }
}
