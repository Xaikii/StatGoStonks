package net.perpetualeve.statgostonks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.world.item.ItemStack;
import net.perpetualeve.statgostonks.StatGoStonks;

@Mixin(value = ItemStack.class, priority = 123675)
public class ItemStackMixin {

	@ModifyReturnValue(method = "getMaxDamage", at = @At("RETURN"))
	public int sgs$modifyDurability(int original) {
		return (int) (original * StatGoStonks.DURABILITY_MULTIPLIER.get( ));
	}
}
