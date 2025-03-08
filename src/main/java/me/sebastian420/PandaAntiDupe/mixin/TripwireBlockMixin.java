package me.sebastian420.PandaAntiDupe.mixin;

import me.sebastian420.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.block.TripwireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TripwireBlock.class)
public class TripwireBlockMixin {
    @ModifyArg(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/TripwireHookBlock;update(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;" +
                            "ZZILnet/minecraft/block/BlockState;)V"
            ),
            index = 5
    )
    private int alwaysNegativeOne(int i) {
        if (!PandaAntiDupeConfig.getDupeStatus("TripwireDupe")) return i;


        return -1;
    }
}