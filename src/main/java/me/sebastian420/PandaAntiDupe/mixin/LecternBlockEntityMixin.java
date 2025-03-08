package me.sebastian420.PandaAntiDupe.mixin;

import me.sebastian420.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LecternBlockEntity.class)
public class LecternBlockEntityMixin extends BlockEntity {

    public LecternBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(
            method = "onBookRemoved",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/LecternBlock;setHasBook(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/block/BlockState;Z)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void onBookRemovedCheckIfBlockStillThere(CallbackInfo ci) {
        if (!PandaAntiDupeConfig.getDupeStatus("LecternDupe")) return;

        if (!this.isRemoved()) { // If the lectern block entity is removed, no world interactions should be done
            BlockState state = this.getWorld().getBlockState(this.getPos());
            if (state.isOf(Blocks.LECTERN)) {
                LecternBlock.setHasBook(null, this.getWorld(), this.getPos(), state, false);
            }
        }
        ci.cancel();
    }
}