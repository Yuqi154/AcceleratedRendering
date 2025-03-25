package com.github.argon4w.acceleratedrendering.features.blocks;

import com.github.argon4w.acceleratedrendering.configs.FeatureConfig;
import com.github.argon4w.acceleratedrendering.configs.FeatureStatus;
import com.github.argon4w.acceleratedrendering.configs.PipelineSetting;
import com.github.argon4w.acceleratedrendering.core.meshes.IMesh;
import com.github.argon4w.acceleratedrendering.core.meshes.MeshType;

import java.util.ArrayDeque;
import java.util.Deque;

public class AcceleratedBlockEntityRenderingFeature {

    private static final Deque<PipelineSetting> PIPELINE_CONTROLLER_STACK = new ArrayDeque<>();

    public static boolean isEnabled() {
        return FeatureConfig.CONFIG.acceleratedBlockEntityRenderingFeatureStatus.get() == FeatureStatus.ENABLED;
    }

    public static boolean shouldUseAcceleratedPipeline() {
        return getPipelineSetting() == PipelineSetting.ACCELERATED;
    }

    public static MeshType getMeshType() {
        return FeatureConfig.CONFIG.acceleratedBlockEntityRenderingMeshType.get();
    }

    public static IMesh.Builder getMeshBuilder() {
        return getMeshType().getBuilder();
    }

    public static void useVanillaPipeline() {
        PIPELINE_CONTROLLER_STACK.push(PipelineSetting.VANILLA);
    }

    public static void forceUseAcceleratedPipeline() {
        PIPELINE_CONTROLLER_STACK.push(PipelineSetting.ACCELERATED);
    }

    public static void forceSetPipeline(PipelineSetting pipeline) {
        PIPELINE_CONTROLLER_STACK.push(pipeline);
    }

    public static void resetPipelineSetting() {
        PIPELINE_CONTROLLER_STACK.pop();
    }

    public static PipelineSetting getPipelineSetting() {
        return PIPELINE_CONTROLLER_STACK.isEmpty() ? getDefaultPipelineSetting() : PIPELINE_CONTROLLER_STACK.peek();
    }

    public static PipelineSetting getDefaultPipelineSetting() {
        return FeatureConfig.CONFIG.acceleratedBlockEntityRenderingDefaultPipeline.get();
    }

    public static void checkControllerState() {
        if (!PIPELINE_CONTROLLER_STACK.isEmpty()) {
            throw new IllegalStateException("Default pipeline Controller stack not empty!");
        }
    }
}
