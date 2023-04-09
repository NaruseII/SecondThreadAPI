package fr.naruse.api.v1_13;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.version.particle.IEnumParticle;

public class EnumParticle implements IEnumParticle {

    @Override
    public IParticle SOUL_FIRE_FLAME() {return () -> net.minecraft.server.v1_13_R2.Particles.y;}

    @Override
    public IParticle FIRE() {
        return () -> net.minecraft.server.v1_13_R2.Particles.y;
    }

    @Override
    public IParticle FLAME() {
        return () -> net.minecraft.server.v1_13_R2.Particles.y;
    }

    @Override
    public IParticle BLOCK() {
        return () -> net.minecraft.server.v1_13_R2.Particles.d;
    }

    @Override
    public IParticle REDSTONE() {
        return () -> net.minecraft.server.v1_13_R2.Particles.m;
    }

    @Override
    public IParticle TOTEM_OF_UNDYING() {
        return () -> net.minecraft.server.v1_13_R2.Particles.p;
    }

    @Override
    public IParticle TOWN_AURA() {
        return () -> net.minecraft.server.v1_13_R2.Particles.P;
    }

    @Override
    public IParticle WITCH() {
        return () -> net.minecraft.server.v1_13_R2.Particles.S;
    }

    @Override
    public IParticle SPELL_WITCH() {
        return () -> net.minecraft.server.v1_13_R2.Particles.S;
    }

    @Override
    public IParticle CLOUD() {
        return () -> net.minecraft.server.v1_13_R2.Particles.g;
    }

    @Override
    public IParticle HEART() {
        return () -> net.minecraft.server.v1_13_R2.Particles.A;
    }

    @Override
    public IParticle SOUL() {
        return () -> net.minecraft.server.v1_13_R2.Particles.y;
    }

    @Override
    public IParticle EXPLOSION() {
        return () -> net.minecraft.server.v1_13_R2.Particles.u;
    }

    @Override
    public IParticle EXPLOSION_HUGE() {
        return () -> net.minecraft.server.v1_13_R2.Particles.t;
    }

    @Override
    public IParticle SMOKE_NORMAL() {
        return () -> net.minecraft.server.v1_13_R2.Particles.M;
    }

    @Override
    public IParticle DAMAGE_INDICATOR() {
        return () -> net.minecraft.server.v1_13_R2.Particles.i;
    }
}
