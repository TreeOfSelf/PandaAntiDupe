package me.sebastian420.PandaAntiDupe.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
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
    private void tryEnterHiveIfLoaded(Entity entity, CallbackInfo ci) {
        if (!entity.getWorld().isChunkLoaded(
                ChunkSectionPos.getSectionCoord(this.pos.getX()),
                ChunkSectionPos.getSectionCoord(this.pos.getY())
        )) {
            ci.cancel();
        }
    }
}