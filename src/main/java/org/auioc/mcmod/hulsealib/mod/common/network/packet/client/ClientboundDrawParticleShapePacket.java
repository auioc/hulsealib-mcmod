package org.auioc.mcmod.hulsealib.mod.common.network.packet.client;

import org.auioc.mcmod.arnicalib.base.phys.Shape;
import org.auioc.mcmod.arnicalib.game.network.IHPacket;
import org.auioc.mcmod.arnicalib.game.particle.ParticlePainterOptions;
import org.auioc.mcmod.arnicalib.game.registry.RegistryUtils;
import org.auioc.mcmod.hulsealib.game.particle.ClientParticlePainterAdapter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class ClientboundDrawParticleShapePacket implements IHPacket {

    private final Shape shape;
    private final ParticlePainterOptions options;
    private final CompoundTag data;

    public ClientboundDrawParticleShapePacket(Shape shape, ParticlePainterOptions options, CompoundTag data) {
        this.shape = shape;
        this.options = options;
        this.data = data;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(Context ctx) {
        ClientParticlePainterAdapter.draw(this.shape, this.options, this.data);
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(this.shape);
        buffer.writeDouble(this.options.stepLength());
        buffer.writeResourceLocation(RegistryUtils.id(this.options.particle().getType()));
        this.options.particle().writeToNetwork(buffer);
        buffer.writeBoolean(this.options.force());
        buffer.writeNbt(this.data);
    }

    public static ClientboundDrawParticleShapePacket decode(FriendlyByteBuf buffer) {
        var shape = buffer.readEnum(Shape.class);
        ParticlePainterOptions options;
        {
            var stepLength = buffer.readDouble();
            var _particleType = ForgeRegistries.PARTICLE_TYPES.getValue(buffer.readResourceLocation());
            var particle = readParticle(buffer, _particleType);
            var force = buffer.readBoolean();
            options = new ParticlePainterOptions(stepLength, particle, force);
        }
        var data = buffer.readNbt();
        return new ClientboundDrawParticleShapePacket(shape, options, data);
    }

    @SuppressWarnings("deprecation")
    private static <T extends ParticleOptions> T readParticle(FriendlyByteBuf buffer, ParticleType<T> particleType) {
        return particleType.getDeserializer().fromNetwork(particleType, buffer);
    }

}
