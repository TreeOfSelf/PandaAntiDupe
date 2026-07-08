package me.TreeOfSelf.PandaAntiDupe.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import java.util.Map;
import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBaseBlock.class)
public abstract class PistonBaseBlockMixin {
	@Inject(
		method = "moveBlocks",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/piston/PistonStructureResolver;getToDestroy()Ljava/util/List;", shift = At.Shift.BEFORE)
	)
	private void setPushTargetsToAirFirst(
		Level level,
		BlockPos pistonPos,
		Direction direction,
		boolean extending,
		CallbackInfoReturnable<Boolean> cir,
		@Local(name = "toPush") List<BlockPos> toPush,
		@Local(name = "toPushShapes") List<BlockState> toPushShapes,
		@Local(name = "deleteAfterMove") Map<BlockPos, BlockState> deleteAfterMove
	) {
		if (!PandaAntiDupeConfig.getDupeStatus("PistonDupe")) {
			return;
		}
		for (int i = toPush.size() - 1; i >= 0; i--) {
			BlockPos pos = toPush.get(i);
			BlockState state = level.getBlockState(pos);
			if (state.hasBlockEntity()) {
				continue;
			}
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2 | 4 | 16 | 64);
			toPushShapes.set(i, state);
			deleteAfterMove.put(pos, state);
		}
	}
}
