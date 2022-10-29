package fr.naruse.api.v1_17;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.core.particles.Particles;

public class EnumParticle implements IEnumParticle {

    @Override
    public IParticle SOUL_FIRE_FLAME() {return () -> Particles.D;}

    @Override
    public IParticle FIRE() {
        return () -> Particles.C;
    }

    @Override
    public IParticle FLAME() {
        return () -> Particles.C;
    }

    @Override
    public IParticle BLOCK() {
        return () -> Particles.e;
    }

    @Override
    public IParticle REDSTONE() {
        return () -> Particles.p;
    }

    @Override
    public IParticle TOTEM_OF_UNDYING() {
        return () -> Particles.aa;
    }

    @Override
    public IParticle TOWN_AURA() {
        return () -> Particles.aa;
    }

    @Override
    public IParticle WITCH() {
        return () -> Particles.ad;
    }

    @Override
    public IParticle SPELL_WITCH() {
        return () -> Particles.ad;
    }

    @Override
    public IParticle CLOUD() {
        return () -> Particles.g;
    }

    @Override
    public IParticle HEART() {
        return () -> Particles.I;
    }

    @Override
    public IParticle SOUL() {
        return () -> Particles.E;
    }

    @Override
    public IParticle EXPLOSION() {
        return () -> Particles.y;
    }

    @Override
    public IParticle EXPLOSION_HUGE() {
        return () -> Particles.x;
    }

    @Override
    public IParticle SMOKE_NORMAL() {
        return () -> Particles.V;
    }

}
