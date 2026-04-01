package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LecternBlockEntity.class)
public abstract class LecternBlockEntityMixin {
	@Inject(
		method = "onBookItemRemove",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/LecternBlock;resetBookState(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	private void onBookRemovedCheckIfBlockStillThere(CallbackInfo ci) {
		if (!PandaAntiDupeConfig.getDupeStatus("LecternDupe")) {
			return;
		}
		BlockEntity self = (BlockEntity) (Object) this;
		if (!self.isRemoved()) {
			Level level = self.getLevel();
			BlockPos pos = self.getBlockPos();
			BlockState state = level.getBlockState(pos);
			if (state.is(Blocks.LECTERN)) {
				LecternBlock.resetBookState(null, level, pos, state, false);
			}
		}
		ci.cancel();
	}
}
