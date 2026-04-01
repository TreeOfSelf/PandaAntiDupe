package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {
	@ModifyArg(
		method = "doClick",
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/inventory/ContainerInput;SWAP:Lnet/minecraft/world/inventory/ContainerInput;"
			)
		),
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/inventory/Slot;setByPlayer(Lnet/minecraft/world/item/ItemStack;)V",
			ordinal = 2
		),
		index = 0
	)
	private ItemStack modifyStack(ItemStack stack) {
		if (!PandaAntiDupeConfig.getDupeStatus("StackSplitDupe")) {
			return stack;
		}
		return stack.split(stack.getCount());
	}
}
