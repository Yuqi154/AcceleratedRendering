package com.github.argon4w.acceleratedrendering.core.programs;

import com.github.argon4w.acceleratedrendering.AcceleratedRenderingModEntry;
import com.github.argon4w.acceleratedrendering.core.backends.programs.BarrierFlags;
import com.github.argon4w.acceleratedrendering.core.programs.processing.FixedPolygonProcessor;
import com.github.argon4w.acceleratedrendering.core.programs.processing.LoadPolygonProcessorEvent;
import com.github.argon4w.acceleratedrendering.core.programs.transform.FixedTransformProgramSelector;
import com.github.argon4w.acceleratedrendering.core.programs.transform.LoadTransformProgramSelectorEvent;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@EventBusSubscriber(modid = AcceleratedRenderingModEntry.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ComputeShaderPrograms {

    public static final ResourceLocation CORE_ENTITY_VERTEX_TRANSFORM_KEY = AcceleratedRenderingModEntry.location("core_entity_vertex_transform");
    public static final ResourceLocation CORE_POS_TEX_COLOR_VERTEX_TRANSFORM_KEY = AcceleratedRenderingModEntry.location("core_pos_tex_color_vertex_transform");
    public static final ResourceLocation CORE_POS_TEX_VERTEX_TRANSFORM_KEY = AcceleratedRenderingModEntry.location("core_pos_tex_vertex_transform");
    public static final ResourceLocation CORE_POS_COLOR_TEX_LIGHT_VERTEX_TRANSFORM_KEY = AcceleratedRenderingModEntry.location("core_pos_color_tex_light_vertex_transform");
    public static final ResourceLocation CORE_PASS_THROUGH_QUAD_CULLING_KEY = AcceleratedRenderingModEntry.location("core_pass_through_quad_culling");
    public static final ResourceLocation CORE_PASS_THROUGH_TRIANGLE_CULLING_KEY = AcceleratedRenderingModEntry.location("core_pass_through_triangle_culling");

    @SubscribeEvent
    public static void onLoadComputeShaders(LoadComputeShaderEvent event) {
        event.loadComputeShader(
                CORE_ENTITY_VERTEX_TRANSFORM_KEY,
                AcceleratedRenderingModEntry.location("shaders/core/transform/entity_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_POS_TEX_COLOR_VERTEX_TRANSFORM_KEY,
                AcceleratedRenderingModEntry.location("shaders/core/transform/pos_tex_color_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_POS_TEX_VERTEX_TRANSFORM_KEY,
                AcceleratedRenderingModEntry.location("shaders/core/transform/pos_tex_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_POS_COLOR_TEX_LIGHT_VERTEX_TRANSFORM_KEY,
                AcceleratedRenderingModEntry.location("shaders/core/transform/pos_color_tex_light_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_PASS_THROUGH_QUAD_CULLING_KEY,
                AcceleratedRenderingModEntry.location("shaders/core/culling/pass_through_quad_culling_shader.compute"),
                BarrierFlags.SHADER_STORAGE,
                BarrierFlags.ATOMIC_COUNTER
        );

        event.loadComputeShader(
                CORE_PASS_THROUGH_TRIANGLE_CULLING_KEY,
                AcceleratedRenderingModEntry.location("shaders/core/culling/pass_through_triangle_culling_shader.compute"),
                BarrierFlags.SHADER_STORAGE,
                BarrierFlags.ATOMIC_COUNTER
        );
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLoadTransformPrograms(LoadTransformProgramSelectorEvent event) {
        event.loadFor(DefaultVertexFormat.NEW_ENTITY, parent -> new FixedTransformProgramSelector(CORE_ENTITY_VERTEX_TRANSFORM_KEY));
        event.loadFor(DefaultVertexFormat.POSITION_TEX_COLOR, parent -> new FixedTransformProgramSelector(CORE_POS_TEX_COLOR_VERTEX_TRANSFORM_KEY));
        event.loadFor(DefaultVertexFormat.POSITION_TEX, parent -> new FixedTransformProgramSelector(CORE_POS_TEX_VERTEX_TRANSFORM_KEY));
        event.loadFor(DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, parent -> new FixedTransformProgramSelector(CORE_POS_COLOR_TEX_LIGHT_VERTEX_TRANSFORM_KEY));
    }

    @SubscribeEvent
    public static void onRegisterResourceReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ComputeShaderProgramLoader.INSTANCE);
    }
}
