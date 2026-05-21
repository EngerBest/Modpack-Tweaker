package net.engerbest.mptw.mixin;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public abstract class CampfireMixin extends Block {
    public CampfireMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    private void overridePlacementState(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        BlockState originalState = cir.getReturnValue();

        if (originalState != null) {
            BlockState extinguishedState = originalState.setValue(BlockStateProperties.LIT, false);

            cir.setReturnValue(extinguishedState);
        }
    }
}
