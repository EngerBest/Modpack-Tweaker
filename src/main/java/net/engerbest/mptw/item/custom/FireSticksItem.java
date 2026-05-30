package net.engerbest.mptw.item.custom;

import net.engerbest.mptw.Mptw;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FireSticksItem extends Item {
    private static final int DELAY = 20;

    public FireSticksItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        BlockHitResult hitResult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!(pLivingEntity instanceof Player player)) return;

        if (!pLevel.isClientSide()) {
            int duration = this.getUseDuration(pStack) - pTimeCharged;

            if (duration >= DELAY) {
                BlockHitResult hitResult = getPlayerPOVHitResult(pLevel, player, ClipContext.Fluid.NONE);

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos targetPos = hitResult.getBlockPos();
                    BlockState clickedState = pLevel.getBlockState(targetPos);

                    float currentChance = Mptw.CONFIG.igniteChance;
                    boolean isSuccessful = pLevel.getRandom().nextFloat() < currentChance / 100;

                    if (!player.getAbilities().instabuild) {
                        pStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    }

                    if (isSuccessful) {
                        pLevel.playSound(null, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f,
                                1.0f);

                        if (clickedState.getBlock() instanceof CampfireBlock) {
                            if (!clickedState.getValue(BlockStateProperties.LIT)) {
                                pLevel.setBlock(targetPos, clickedState.setValue(BlockStateProperties.LIT, true), 11);

                                return;
                            }
                        }

                        BlockPos firePos = targetPos.relative(hitResult.getDirection());

                        if (BaseFireBlock.canBePlacedAt(pLevel, firePos, hitResult.getDirection())) {
                            BlockState fireState = BaseFireBlock.getState(pLevel, firePos);

                            pLevel.setBlock(firePos, fireState, 11);
                        }
                    } else {
                        pLevel.playSound(null, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f,
                                0.5f);
                    }
                }
            }
        }
    }
}
