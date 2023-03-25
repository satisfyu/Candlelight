package net.satisfy.candlelight.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CreateFloorboardBlock extends Block {

    public CreateFloorboardBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        TagManager tagManager = world.getTagManager();
        if (itemStack.getItem() == Items.BONE_MEAL && tagManager.getBlockTags().getTagOrEmpty(BlockTags.PLANKS.getId()).containsId(Block.getRawIdFromState(state))) {
            if (!world.isClient) {
                }
            }
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }



    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient && world.getBlockState(pos.up()).isAir()) {
            world.setBlockState(pos.up(), Blocks.OAK_SLAB.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
            this.scheduleTick(state, world, pos, random);
        }
    }

    private void scheduleTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!BlockTags.LOGS.contains(state.getBlock())) {
            world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
        }
    }

    @Override
    public int getTickRate(ServerWorld world) {
        return 20;
    }
}

}