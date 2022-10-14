package org.auioc.mcmod.hulsealib.game.particle;

import java.util.function.Consumer;
import org.auioc.mcmod.arnicalib.base.phys.Shape;
import org.auioc.mcmod.arnicalib.game.nbt.NbtUtils;
import org.auioc.mcmod.arnicalib.game.particle.ParticlePainterOptions;
import org.auioc.mcmod.arnicalib.game.world.phys.VectorUtils;
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

    public static void drawLine(ServerPlayer player, ParticlePainterOptions options, Vec3 start, Vec3 end) {
        draw(player, Shape.LINE, options, (nbt) -> {
            nbt.put("Start", NbtUtils.writeVec3(start));
            nbt.put("End", NbtUtils.writeVec3(end));
        });
    }

    public static void drawPolygon(ServerPlayer player, ParticlePainterOptions options, Vec3[] vertexes) {
        draw(player, Shape.POLYGON, options, (nbt) -> {
            nbt.put("Vertexes", NbtUtils.writeDoubleArray(VectorUtils.toDoubleArray(vertexes)));
        });
    }

    public static void drawPolygon(ServerPlayer player, ParticlePainterOptions options, Vec3 centre, Vec3 normalVector, double circumradius, double centralAngle) {
        draw(player, Shape.POLYGON, options, (nbt) -> {
            nbt.put("Centre", NbtUtils.writeVec3(centre));
            nbt.put("Normal", NbtUtils.writeVec3(normalVector));
            nbt.putDouble("Circumradius", circumradius);
            nbt.putDouble("CentralAngle", centralAngle);
        });
    }

    public static void drawCircle(ServerPlayer player, ParticlePainterOptions options, Vec3 centre, Vec3 normal, double radius, double deltaAngle) {
        draw(player, Shape.CIRCLE, options, (nbt) -> {
            nbt.put("Centre", NbtUtils.writeVec3(centre));
            nbt.put("Normal", NbtUtils.writeVec3(normal));
            nbt.putDouble("Radius", radius);
            nbt.putDouble("DeltaAngle", deltaAngle);
        });
    }

    public static void drawCuboid(ServerPlayer player, ParticlePainterOptions options, AABB aabb) {
        draw(player, Shape.CUBOID, options, (nbt) -> {
            nbt.put("AABB", NbtUtils.writeAABB(aabb));
        });
    }


    // #region overload

    public static void drawLine(ServerPlayer player, double x1, double y1, double z1, double x2, double y2, double z2) {
        drawLine(player, DEFAULT_OPTIONS, new Vec3(x1, y1, z1), new Vec3(x2, y2, z2));
    }

    public static void drawLine(ServerPlayer player, Vec3 start, Vec3 end) {
        drawLine(player, DEFAULT_OPTIONS, start, end);
    }

    public static void drawLine(ServerPlayer player, Vec3i start, Vec3i end) {
        drawLine(player, Vec3.atCenterOf(start), Vec3.atCenterOf(end));
    }

    public static void drawCircle(ServerPlayer player, Vec3 centre, Vec3 normal, double radius) {
        drawCircle(player, DEFAULT_OPTIONS, centre, normal, radius, 1.0D);
    }

    public static void drawPolygon(ServerPlayer player, Vec3[] vertexes) {
        drawPolygon(player, DEFAULT_OPTIONS, vertexes);
    }

    public static void drawPolygon(ServerPlayer player, Vec3 centre, Vec3 normalVector, double circumradius, double centralAngle) {
        drawPolygon(player, DEFAULT_OPTIONS, centre, normalVector, circumradius, centralAngle);
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
