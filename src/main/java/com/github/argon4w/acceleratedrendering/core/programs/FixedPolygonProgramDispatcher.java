package com.github.argon4w.acceleratedrendering.core.programs;

import com.github.argon4w.acceleratedrendering.core.backends.programs.ComputeProgram;
import com.github.argon4w.acceleratedrendering.core.backends.programs.Uniform;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.AcceleratedBufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;

import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;

public class FixedPolygonProgramDispatcher implements IPolygonProgramDispatcher {

    private static final int GROUP_SIZE = 128;

    private final VertexFormat.Mode mode;
    private final ComputeProgram program;
    private final Uniform polygonCountUniform;
    private final Uniform vertexOffsetUniform;

    public FixedPolygonProgramDispatcher(VertexFormat.Mode mode, ComputeProgram program) {
        this.mode = mode;
        this.program = program;
        this.polygonCountUniform = this.program.getUniform("polygonCount");
        this.vertexOffsetUniform = program.getUniform("vertexOffset");
    }

    public FixedPolygonProgramDispatcher(VertexFormat.Mode mode, ResourceLocation key) {
        this(mode, ComputeShaderProgramLoader.getProgram(key));
    }

    @Override
    public int dispatch(AcceleratedBufferBuilder builder) {
        int vertexCount = builder.getVertexCount();
        int vertexOffset = builder.getVertexOffset();
        int polygonCount = vertexCount / mode.primitiveLength;

        builder.getVaryingBuffer().bindBase(GL_SHADER_STORAGE_BUFFER, 3);

        polygonCountUniform.uploadUnsignedInt(polygonCount);
        vertexOffsetUniform.uploadUnsignedInt(vertexOffset);

        program.useProgram();
        program.dispatch((polygonCount + GROUP_SIZE - 1) / GROUP_SIZE);
        program.resetProgram();

        return program.getBarrierFlags();
    }
}
