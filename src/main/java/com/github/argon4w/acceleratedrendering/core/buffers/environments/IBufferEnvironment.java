package com.github.argon4w.acceleratedrendering.core.buffers.environments;

import com.github.argon4w.acceleratedrendering.core.gl.buffers.IServerBuffer;
import com.github.argon4w.acceleratedrendering.core.programs.IPolygonProgramDispatcher;
import com.github.argon4w.acceleratedrendering.core.programs.processing.IExtraVertexData;
import com.github.argon4w.acceleratedrendering.core.programs.transform.TransformProgramDispatcher;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.minecraft.client.renderer.RenderType;

public interface IBufferEnvironment {

    void setupBufferState();
    boolean isAccelerated(VertexFormat vertexFormat);
    IExtraVertexData getExtraVertex(VertexFormat.Mode mode);
    VertexFormat getActiveFormat();
    IServerBuffer getServerMeshBuffer();
    TransformProgramDispatcher selectTransformProgramDispatcher();
    IPolygonProgramDispatcher selectCullProgramDispatcher(RenderType renderType);
    IPolygonProgramDispatcher selectProcessingProgramDispatcher(VertexFormat.Mode mode);
    RenderType getRenderType(RenderType renderType);
    int getOffset(VertexFormatElement element);
    int getFlags(VertexFormat.Mode mode);
    int getVertexSize();

    class Presets {

        public static final IBufferEnvironment ENTITY = new VanillaBufferEnvironment(DefaultVertexFormat.NEW_ENTITY);
        public static final IBufferEnvironment POS_TEX_COLOR = new VanillaBufferEnvironment(DefaultVertexFormat.POSITION_TEX_COLOR);
        public static final IBufferEnvironment POS_TEX = new VanillaBufferEnvironment(DefaultVertexFormat.POSITION_TEX);
        public static final IBufferEnvironment POS_COLOR_TEX_LIGHT = new VanillaBufferEnvironment(DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
    }
}
