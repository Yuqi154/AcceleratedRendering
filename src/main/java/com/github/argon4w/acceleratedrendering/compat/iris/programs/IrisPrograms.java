package com.github.argon4w.acceleratedrendering.compat.iris.programs;

import com.github.argon4w.acceleratedrendering.AcceleratedRenderingModEntry;
import com.github.argon4w.acceleratedrendering.compat.iris.programs.culling.IrisCullingProgramSelector;
import com.github.argon4w.acceleratedrendering.compat.iris.programs.processing.IrisEntityPolygonProcessor;
import com.github.argon4w.acceleratedrendering.core.backends.programs.BarrierFlags;
import com.github.argon4w.acceleratedrendering.core.programs.LoadComputeShaderEvent;
import com.github.argon4w.acceleratedrendering.core.programs.culling.LoadCullingProgramSelectorEvent;
import com.github.argon4w.acceleratedrendering.core.programs.processing.LoadPolygonProcessorEvent;
import com.github.argon4w.acceleratedrendering.core.programs.transform.FixedTransformProgramSelector;
import com.github.argon4w.acceleratedrendering.core.programs.transform.LoadTransformProgramSelectorEvent;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.irisshaders.iris.vertices.IrisVertexFormats;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;

public class IrisPrograms {

    public static final ResourceLocation IRIS_ENTITY_VERTEX_TRANSFORM_KEY = AcceleratedRenderingModEntry.location("compat_entity_vertex_transform_iris");
    public static final ResourceLocation IRIS_GLYPH_VERTEX_TRANSFORM_KEY = AcceleratedRenderingModEntry.location("compat_glyph_vertex_transform_iris");
    public static final ResourceLocation IRIS_ENTITY_QUAD_CULLING_KEY = AcceleratedRenderingModEntry.location("compat_entity_quad_cull_iris");
    public static final ResourceLocation IRIS_ENTITY_TRIANGLE_CULLING_KEY = AcceleratedRenderingModEntry.location("compat_entity_triangle_cull_iris");
    public static final ResourceLocation IRIS_ENTITY_QUAD_PROCESSING_KEY = AcceleratedRenderingModEntry.location("compat_entity_quad_processing_iris");
    public static final ResourceLocation IRIS_ENTITY_TRIANGLE_PROCESSING_KEY = AcceleratedRenderingModEntry.location("compat_entity_triangle_processing_iris");
    public static final ResourceLocation IRIS_GLYPH_QUAD_PROCESSING_KEY = AcceleratedRenderingModEntry.location("compat_glyph_quad_processing_iris");
    public static final ResourceLocation IRIS_GLYPH_TRIANGLE_PROCESSING_KEY = AcceleratedRenderingModEntry.location("compat_glyph_triangle_processing_iris");

    @SubscribeEvent
    public static void onLoadComputeShaders(LoadComputeShaderEvent event) {
        event.loadComputeShader(
                IRIS_ENTITY_VERTEX_TRANSFORM_KEY,
                AcceleratedRenderingModEntry.location("shaders/compat/transform/iris_entity_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                IRIS_GLYPH_VERTEX_TRANSFORM_KEY,
                AcceleratedRenderingModEntry.location("shaders/compat/transform/iris_glyph_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                IRIS_ENTITY_QUAD_CULLING_KEY,
                AcceleratedRenderingModEntry.location("shaders/compat/culling/iris_entity_quad_culling_shader.compute"),
                BarrierFlags.SHADER_STORAGE,
                BarrierFlags.ATOMIC_COUNTER
        );

        event.loadComputeShader(
                IRIS_ENTITY_TRIANGLE_CULLING_KEY,
                AcceleratedRenderingModEntry.location("shaders/compat/culling/iris_entity_triangle_culling_shader.compute"),
                BarrierFlags.SHADER_STORAGE,
                BarrierFlags.ATOMIC_COUNTER
        );

        event.loadComputeShader(
                IRIS_ENTITY_QUAD_PROCESSING_KEY,
                AcceleratedRenderingModEntry.location("shaders/compat/processing/iris_entity_quad_processing_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                IRIS_ENTITY_TRIANGLE_PROCESSING_KEY,
                AcceleratedRenderingModEntry.location("shaders/compat/processing/iris_entity_triangle_processing_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                IRIS_GLYPH_QUAD_PROCESSING_KEY,
                AcceleratedRenderingModEntry.location("shaders/compat/processing/iris_glyph_quad_processing_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                IRIS_GLYPH_TRIANGLE_PROCESSING_KEY,
                AcceleratedRenderingModEntry.location("shaders/compat/processing/iris_glyph_triangle_processing_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );
    }

    @SubscribeEvent
    public static void onLoadTransformPrograms(LoadTransformProgramSelectorEvent event) {
        event.loadFor(
                IrisVertexFormats.ENTITY,
                parent -> new FixedTransformProgramSelector(IRIS_ENTITY_VERTEX_TRANSFORM_KEY)
        );

        event.loadFor(
                IrisVertexFormats.GLYPH,
                parent -> new FixedTransformProgramSelector(IRIS_GLYPH_VERTEX_TRANSFORM_KEY)
        );
    }

    @SubscribeEvent
    public static void onLoadCullingPrograms(LoadCullingProgramSelectorEvent event) {
        event.loadFor(IrisVertexFormats.ENTITY, parent -> new IrisCullingProgramSelector(
                parent,
                VertexFormat.Mode.TRIANGLES,
                IRIS_ENTITY_TRIANGLE_CULLING_KEY
        ));

        event.loadFor(IrisVertexFormats.ENTITY, parent -> new IrisCullingProgramSelector(
                parent,
                VertexFormat.Mode.QUADS,
                IRIS_ENTITY_QUAD_CULLING_KEY
        ));
    }

    @SubscribeEvent
    public static void onLoadPolygonProcessors(LoadPolygonProcessorEvent event) {
        event.loadFor(IrisVertexFormats.ENTITY, parent -> new IrisEntityPolygonProcessor(
                parent,
                IrisVertexFormats.ENTITY,
                VertexFormat.Mode.TRIANGLES,
                IRIS_ENTITY_TRIANGLE_PROCESSING_KEY
        ));

        event.loadFor(IrisVertexFormats.ENTITY, parent -> new IrisEntityPolygonProcessor(
                parent,
                IrisVertexFormats.ENTITY,
                VertexFormat.Mode.QUADS,
                IRIS_ENTITY_QUAD_PROCESSING_KEY
        ));

        event.loadFor( IrisVertexFormats.GLYPH, parent -> new IrisEntityPolygonProcessor(
                parent,
                IrisVertexFormats.GLYPH,
                VertexFormat.Mode.QUADS,
                IRIS_GLYPH_QUAD_PROCESSING_KEY
        ));

        event.loadFor( IrisVertexFormats.GLYPH, parent -> new IrisEntityPolygonProcessor(
                parent,
                IrisVertexFormats.GLYPH,
                VertexFormat.Mode.TRIANGLES,
                IRIS_GLYPH_TRIANGLE_PROCESSING_KEY
        ));
    }
}
