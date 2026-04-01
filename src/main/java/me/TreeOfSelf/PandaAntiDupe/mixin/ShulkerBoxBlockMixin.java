package me.TreeOfSelf.PandaAntiDupe.mixin;

import java.util.List;
import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerBoxBlock.class)
public abstract class ShulkerBoxBlockMixin {
	@Inject(method = "getDrops", at = @At("TAIL"))
	private void clearAfterGetDroppedStack(BlockState state, LootParams.Builder params, CallbackInfoReturnable<List<ItemStack>> cir) {
		if (!PandaAntiDupeConfig.getDupeStatus("ShulkerBoxDupe")) {
			return;
		}
		BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
		if (blockEntity instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
			shulkerBoxBlockEntity.clearContent();
		}
	}
}
