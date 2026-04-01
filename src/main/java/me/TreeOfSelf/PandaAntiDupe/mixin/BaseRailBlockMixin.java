package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseRailBlock.class)
public abstract class BaseRailBlockMixin {
	@Inject(
		method = "neighborChanged",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/BaseRailBlock;updateState(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V"
		),
		require = 1,
		cancellable = true
	)
	private void checkIfRailStillExists(
		BlockState state,
		Level level,
		BlockPos pos,
		Block neighborBlock,
		@Nullable Orientation orientation,
		boolean movedByPiston,
		CallbackInfo ci
	) {
		if (!PandaAntiDupeConfig.getDupeStatus("RailDupe")) {
			return;
		}
		if (!(level.getBlockState(pos).getBlock() instanceof BaseRailBlock)) {
			ci.cancel();
		}
	}
}
