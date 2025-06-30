package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(AbstractRailBlock.class)
public abstract class AbstractRailBlockMixin
{
    @Inject(
            method = "neighborUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/AbstractRailBlock;updateBlockState(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"
            ),
            require = 1,
            cancellable = true
    )
    private void checkIfRailStillExists(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify, CallbackInfo ci)
    {
        if (!PandaAntiDupeConfig.getDupeStatus("RailDupe")) return;

        if (!(world.getBlockState(pos).getBlock() instanceof AbstractRailBlock))
        {
            ci.cancel();
        }
    }

}