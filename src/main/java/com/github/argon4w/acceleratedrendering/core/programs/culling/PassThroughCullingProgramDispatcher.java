package com.github.argon4w.acceleratedrendering.core.programs.culling;

import com.github.argon4w.acceleratedrendering.core.backends.programs.ComputeProgram;
import com.github.argon4w.acceleratedrendering.core.backends.programs.Uniform;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.AcceleratedBufferBuilder;
import com.github.argon4w.acceleratedrendering.core.programs.ComputeShaderProgramLoader;
import com.github.argon4w.acceleratedrendering.core.programs.ComputeShaderPrograms;
import com.github.argon4w.acceleratedrendering.core.programs.IPolygonProgramDispatcher;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;

public class PassThroughCullingProgramDispatcher implements IPolygonProgramDispatcher {

    public static final PassThroughCullingProgramDispatcher QUAD = new PassThroughCullingProgramDispatcher(VertexFormat.Mode.QUADS, ComputeShaderPrograms.CORE_PASS_THROUGH_QUAD_CULLING_KEY);
    public static final PassThroughCullingProgramDispatcher TRIANGLE = new PassThroughCullingProgramDispatcher(VertexFormat.Mode.TRIANGLES, ComputeShaderPrograms.CORE_PASS_THROUGH_TRIANGLE_CULLING_KEY);

    private static final int GROUP_SIZE = 128;

    private final VertexFormat.Mode mode;
    private final ComputeProgram program;
    private final Uniform polygonCountUniform;
    private final Uniform vertexOffsetUniform;

    public PassThroughCullingProgramDispatcher(VertexFormat.Mode mode, ComputeProgram program) {
        this.mode = mode;
        this.program = program;
        this.polygonCountUniform = program.getUniform("polygonCount");
        this.vertexOffsetUniform = program.getUniform("vertexOffset");
    }

    public PassThroughCullingProgramDispatcher(VertexFormat.Mode mode, ResourceLocation key) {
        this(mode, ComputeShaderProgramLoader.getProgram(key));
    }

    @Override
    public int dispatch(AcceleratedBufferBuilder builder) {
        int vertexCount = builder.getVertexCount();
        int vertexOffset = builder.getVertexOffset();
        int polygonCount = vertexCount / mode.primitiveLength;

        polygonCountUniform.uploadUnsignedInt(polygonCount);
        vertexOffsetUniform.uploadUnsignedInt(vertexOffset);

        program.useProgram();
        program.dispatch((polygonCount + GROUP_SIZE - 1) / GROUP_SIZE);
        program.resetProgram();

        return program.getBarrierFlags();
    }
}
