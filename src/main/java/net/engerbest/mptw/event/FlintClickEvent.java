package net.engerbest.mptw.event;

import net.engerbest.mptw.Mptw;
import net.engerbest.mptw.item.ModItems;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mptw.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FlintClickEvent {
    @SubscribeEvent
    public static void onBlockClick(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);
        ItemStack itemStack = event.getItemStack();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();

        if (!itemStack.is(Items.FLINT) || !blockState.is(Tags.Blocks.STONE)) return;

        player.swing(hand);

        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;
            float currentChance = Mptw.CONFIG.fragmentingChance;
            boolean isSuccessful = level.random.nextFloat() < (currentChance / 100.0f);

            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f,
                    level.random.nextFloat() * 0.4f + 0.8f);

            Direction face = event.getFace();

            if (face == null) face = Direction.UP;

            double pX = pos.getX() + 0.5 + face.getStepX() * 0.52;
            double pY = pos.getY() + 0.5 + face.getStepY() * 0.52;
            double pZ = pos.getZ() + 0.5 + face.getStepZ() * 0.52;

            if (isSuccessful) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                ItemStack newItem = new ItemStack(ModItems.FLINT_FRAGMENT.get());

                if (!player.getInventory().add(newItem)) {
                    player.drop(newItem, false);
                }

                int particleCount = 2;

                for (int i = 0; i < particleCount; i++) {
                    double vX = face.getStepX() * 0.2;
                    double vY = face.getStepY() * 0.2;
                    double vZ = face.getStepZ() * 0.2;

                    if (face.getStepX() == 0) vX += (level.random.nextDouble() - 0.5) * 0.3;
                    if (face.getStepY() == 0) vY += (level.random.nextDouble() - 0.5) * 0.3;
                    if (face.getStepZ() == 0) vZ += (level.random.nextDouble() - 0.5) * 0.3;

                    if (face != Direction.UP && face != Direction.DOWN) {
                        vY += 0.05;
                    }

                    serverLevel.sendParticles(
                            ParticleTypes.CRIT,
                            pX, pY, pZ,
                            0,
                            vX, vY, vZ,
                            1.0
                    );
                }
            } else {
                int particleCount = 2;

                for (int i = 0; i < particleCount; i++) {
                    double vX = face.getStepX() * 0.08;
                    double vY = face.getStepY() * 0.08;
                    double vZ = face.getStepZ() * 0.08;

                    if (face.getStepX() == 0) vX += (level.random.nextDouble() - 0.5) * 0.15;
                    if (face.getStepY() == 0) vY += (level.random.nextDouble() - 0.5) * 0.15;
                    if (face.getStepZ() == 0) vZ += (level.random.nextDouble() - 0.5) * 0.15;

                    serverLevel.sendParticles(
                            ParticleTypes.SMOKE,
                            pX, pY, pZ,
                            0,
                            vX, vY, vZ,
                            1.0
                    );
                }
            }
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }
}
