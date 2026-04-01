package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleContainer.class)
public abstract class SimpleContainerMixin {
	@Shadow
	public abstract void setItem(int slot, ItemStack stack);

	@Shadow
	public abstract int getContainerSize();

	@Inject(method = "fromItemList", at = @At("HEAD"))
	private void fromItemListWithoutDupe(ValueInput.TypedInputList<ItemStack> items, CallbackInfo ci) {
		if (!PandaAntiDupeConfig.getDupeStatus("NBTListDupe")) {
			return;
		}
		for (int j = 0; j < this.getContainerSize(); j++) {
			this.setItem(j, ItemStack.EMPTY);
		}
	}
}
