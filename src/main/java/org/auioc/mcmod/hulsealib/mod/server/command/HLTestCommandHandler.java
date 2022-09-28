package org.auioc.mcmod.hulsealib.mod.server.command;

import org.auioc.mcmod.hulsealib.game.particle.ServerParticlePainter;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

// @SuppressWarnings("unused")
public class HLTestCommandHandler {

    public static int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = ctx.getSource().getPlayerOrException();
        ServerParticlePainter.drawCuboid(player, new AABB(3, 6, 14, 0, 9, 11));
        ServerParticlePainter.drawLine(player, 3, 6, 14, 0, 9, 11);
        ServerParticlePainter.drawPolygon(player, new Vec3[] {new Vec3(0, 9, 14), new Vec3(0, 9, 11), new Vec3(3, 9, 12.5)});
        return 1;
    }

    // var nbt = new CompoundTag();
    // nbt.put("Centre", NbtUtils.writeVec3(new Vec3(1.5D, 9.0D, 12.5D)));
    // nbt.put("Point", NbtUtils.writeVec3(new Vec3(3.5D, 9.0D, 14.5D)));
    // AHPacketHandler.sendToClient(player, new ClientboundDrawParticleShapePacket(Shape.CIRCLE, nbt, ParticlePainter.DEFAULT_OPTIONS));

    // public static void drawCircle(ServerPlayer player, Vec3 centre, Vec3 point, Options options) {
    //     draw(player, Shape.CIRCLE, options, (nbt) -> {
    //         nbt.put("Centre", NbtUtils.writeVec3(centre));
    //         nbt.put("Point", NbtUtils.writeVec3(point));
    //     });
    // }

    // public static void drawCircle(Vec3 centre, Vec3 point, Options options) {
    //     drawLine(0, 0, 0, point.x, point.y, point.z, options);
    //     drawLine(0, 0, 0, centre.x, centre.y, centre.z, options);
    //     Vec3 v = point;
    //     Vec3 u = centre.normalize();
    //     for (int i = 0; i < 360; i += 1) {
    //         double sinT = Math.sin(i / Math.PI * 180);
    //         double cosT = Math.cos(i / Math.PI * 180);
    //         Vec3 p = v.scale(cosT).add(u.cross(v).scale(sinT)).add(u.scale(1 - cosT).scale(u.dot(v)));
    //         drawPoint(p.x, p.y, p.z, options);
    //     }
    // }
}


