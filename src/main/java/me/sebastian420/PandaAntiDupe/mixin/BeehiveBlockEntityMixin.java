package me.sebastian420.PandaAntiDupe.mixin;

import me.sebastian420.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveBlockEntity.class)
public abstract class BeehiveBlockEntityMixin extends BlockEntity {

    public BeehiveBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Inject(
            method = "tryEnterHive",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tryEnterHiveIfLoaded(BeeEntity entity, CallbackInfo ci) {
        if (!PandaAntiDupeConfig.getDupeStatus("BeeDupe")) return;

        if (!entity.getWorld().isChunkLoaded(
                ChunkSectionPos.getSectionCoord(this.getPos().getX()),
                ChunkSectionPos.getSectionCoord(this.getPos().getZ())
        )) {
            ci.cancel();
        }
    }
}