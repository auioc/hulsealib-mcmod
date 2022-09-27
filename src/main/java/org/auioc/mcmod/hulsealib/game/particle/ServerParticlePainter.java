package org.auioc.mcmod.hulsealib.game.particle;

import java.util.function.Consumer;
import org.auioc.mcmod.arnicalib.base.phys.Shape;
import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import org.auioc.mcmod.arnicalib.game.particle.ParticlePainterOptions;
import org.auioc.mcmod.hulsealib.mod.common.network.HLPacketHandler;
import org.auioc.mcmod.hulsealib.mod.common.network.packet.client.ClientboundDrawParticleShapePacket;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ServerParticlePainter {

    public static final ParticlePainterOptions DEFAULT_OPTIONS = new ParticlePainterOptions(0.25D, ParticleTypes.ELECTRIC_SPARK, true);

    private static void draw(ServerPlayer player, Shape shape, ParticlePainterOptions options, Consumer<CompoundTag> serializer) {
        var nbt = new CompoundTag();
        serializer.accept(nbt);
        HLPacketHandler.sendToClient(player, new ClientboundDrawParticleShapePacket(shape, options, nbt));
    }

    public static void drawLine(ServerPlayer player, ParticlePainterOptions options, double x1, double y1, double z1, double x2, double y2, double z2) {
        draw(player, Shape.LINE, options, (nbt) -> {
            nbt.put("StartPoint", NbtUtils.writeDoubleArray(x1, y1, z1));
            nbt.put("EndPoint", NbtUtils.writeDoubleArray(x2, y2, z2));
        });
    }

    public static void drawPolygon(ServerPlayer player, ParticlePainterOptions options, Vec3[] vertexes) {
        draw(player, Shape.POLYGON, options, (nbt) -> {
            double[] p = new double[vertexes.length * 3];
            for (int i = 0, j = 0; i < vertexes.length; i++, j = i * 3) {
                p[j] = vertexes[i].x;
                p[j + 1] = vertexes[i].y;
                p[j + 2] = vertexes[i].z;
            }
            nbt.put("Vertexes", NbtUtils.writeDoubleArray(p));
        });
    }

    public static void drawCuboid(ServerPlayer player, ParticlePainterOptions options, AABB aabb) {
        draw(player, Shape.CUBOID, options, (nbt) -> {
            nbt.put("AABB", NbtUtils.writeAABB(aabb));
        });
    }


    // #region overload

    public static void drawLine(ServerPlayer player, double x1, double y1, double z1, double x2, double y2, double z2) {
        drawLine(player, DEFAULT_OPTIONS, x1, y1, z1, x2, y2, z2);
    }

    public static void drawLine(ServerPlayer player, Vec3 start, Vec3 end) {
        drawLine(player, DEFAULT_OPTIONS, start.x, start.y, start.z, end.x, end.y, end.z);
    }

    public static void drawLine(ServerPlayer player, Vec3i start, Vec3i end) {
        drawLine(player, Vec3.atCenterOf(start), Vec3.atCenterOf(end));
    }

    public static void drawPolygon(ServerPlayer player, Vec3[] vertexes) {
        drawPolygon(player, DEFAULT_OPTIONS, vertexes);
    }

    public static void drawPolygon(ServerPlayer player, Vec3i[] vertexes) {
        Vec3[] _vertexes = new Vec3[vertexes.length];
        for (int i = 0; i < vertexes.length; i++) _vertexes[i] = Vec3.atCenterOf(vertexes[i]);
        drawPolygon(player, _vertexes);
    }

    public static void drawCuboid(ServerPlayer player, AABB aabb) {
        drawCuboid(player, DEFAULT_OPTIONS, aabb);
    }

    // #endregion overload

}
