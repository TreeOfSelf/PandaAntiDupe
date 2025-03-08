package me.sebastian420.PandaAntiDupe.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.sebastian420.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin
{


    @Inject(
            method = "move",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                           target = "Lnet/minecraft/block/BlockState;hasBlockEntity()Z"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;size()I",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    private void setAllToBeMovedBlockToAirFirst(
            World world, BlockPos pos, Direction dir, boolean retract,
            CallbackInfoReturnable<Boolean> cir,
            @Local Map<BlockPos, BlockState> map,
            @Local(ordinal = 0) List<BlockPos> list,  // pistonHandler.getMovedBlocks()
            @Local(ordinal = 1) List<BlockState> list2  // states of list
    )
    {
            if (!PandaAntiDupeConfig.getDupeStatus("PistonDupe")) return;

            for (int l = list.size() - 1; l >= 0; --l)
            {
                BlockPos toBeMovedBlockPos = list.get(l);
                BlockState toBeMovedBlockState = world.getBlockState(toBeMovedBlockPos);
                world.setBlockState(toBeMovedBlockPos, Blocks.AIR.getDefaultState(), 2 | 4 | 16 | 64);
                list2.set(l, toBeMovedBlockState);
                map.put(toBeMovedBlockPos, toBeMovedBlockState);
            }

    }


    @Inject(
            method = "move",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/block/PistonBlock;sticky:Z"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;keySet()Ljava/util/Set;",
                    ordinal = 0
            )
    )
    private void makeSureStatesInBlockStatesIsCorrect(
            World world, BlockPos pos, Direction dir, boolean retract,
            CallbackInfoReturnable<Boolean> cir,
            @Local(ordinal = 0) List<BlockPos> list,  // pistonHandler.getMovedBlocks()
            @Local(ordinal = 1) List<BlockState> list2,  // states of list
            @Local(ordinal = 2) List<BlockPos> list3,  // pistonHandler.getBrokenBlocks()
            @Local BlockState[] blockStates,
            @Local(ordinal = 0) int j
    )
    {
        if (!PandaAntiDupeConfig.getDupeStatus("PistonDupe")) return;


        int j2 = list3.size();
            for (int l2 = list.size() - 1; l2 >= 0; --l2)
            {
                blockStates[j2++] = list2.get(l2);
            }

    }
}