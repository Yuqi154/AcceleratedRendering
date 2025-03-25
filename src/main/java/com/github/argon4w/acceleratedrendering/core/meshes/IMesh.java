package com.github.argon4w.acceleratedrendering.core.meshes;

import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.IAcceleratedVertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

public interface IMesh {

    void write(IAcceleratedVertexConsumer extension, int color, int light, int overlay);

    interface Builder {

        IMesh build(MeshCollector collector);
        void close();
    }
}
