package org.auioc.mcmod.hulsealib.game.particle;

import static org.auioc.mcmod.arnicalib.game.particle.ClientParticlePainter.drawCircle;
import static org.auioc.mcmod.arnicalib.game.particle.ClientParticlePainter.drawCuboid;
import static org.auioc.mcmod.arnicalib.game.particle.ClientParticlePainter.drawLine;
import static org.auioc.mcmod.arnicalib.game.particle.ClientParticlePainter.drawPolygon;
import org.auioc.mcmod.arnicalib.base.phys.Shape;
import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import org.auioc.mcmod.arnicalib.game.particle.ParticlePainterOptions;
import org.auioc.mcmod.arnicalib.game.world.phys.VectorUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientParticlePainterAdapter {

    public static void draw(Shape shape, ParticlePainterOptions options, CompoundTag data) {
        switch (shape) {
            case LINE -> {
                var start = NbtUtils.getVec3(data, "Start");
                var end = NbtUtils.getVec3(data, "End");
                drawLine(options, start, end);
            }
            case CUBOID -> {
                drawCuboid(options, NbtUtils.getAABB(data, "AABB"));
            }
            case CIRCLE -> {
                drawCircle(
                    options,
                    NbtUtils.getVec3(data, "Centre"), NbtUtils.getVec3(data, "Normal"),
                    data.getDouble("Radius"), data.getDouble("DeltaAngle")
                );
            }
            case POLYGON -> {
                if (data.contains("Vertexes")) {
                    drawPolygon(options, VectorUtils.fromDoubleArray(NbtUtils.getDoubleArray(data, "Vertexes")));
                } else {
                    drawPolygon(
                        options,
                        NbtUtils.getVec3(data, "Centre"), NbtUtils.getVec3(data, "Normal"),
                        data.getDouble("Circumradius"), data.getDouble("CentralAngle")
                    );
                }
            }
            default -> throw new IllegalArgumentException("Unexpected shape: " + shape);
        }
    }

}
