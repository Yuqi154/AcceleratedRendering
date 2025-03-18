package com.github.argon4w.acceleratedrendering.features.items.mixins;

import com.github.argon4w.acceleratedrendering.core.buffers.builders.IAcceleratedVertexConsumer;
import com.github.argon4w.acceleratedrendering.features.items.IAcceleratedBakedModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BakedModel.class)
public interface BakedModelMixin extends IAcceleratedBakedModel {

    @Unique
    @Override
    default void renderItemFast(ItemStack itemStack, PoseStack poseStack, IAcceleratedVertexConsumer vertexConsumer, int combinedLight, int combinedOverlay) {
        throw new UnsupportedOperationException("Unsupported Operation.");
    }

    @Override
    default boolean isAccelerated() {
        return false;
    }

    @Override
    default boolean hasCustomColor() {
        return false;
    }

    @Unique
    @Override
    default int getCustomColor() {
        return -1;
    }
}
