package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.world.level.block.TripWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TripWireBlock.class)
public abstract class TripwireBlockMixin {
	@ModifyArg(
		method = "updateSource",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/TripWireHookBlock;calculateState(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;ZZILnet/minecraft/world/level/block/state/BlockState;)V"
		),
		index = 5
	)
	private int alwaysNegativeOne(int wireSource) {
		if (!PandaAntiDupeConfig.getDupeStatus("TripwireDupe")) {
			return wireSource;
		}
		return -1;
	}
}
