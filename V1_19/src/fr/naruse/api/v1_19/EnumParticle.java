package fr.naruse.api.v1_19;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.core.particles.Particles;

public class EnumParticle implements IEnumParticle {

    @Override
    public IParticle SOUL_FIRE_FLAME() {return () -> Particles.G;}

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
        return () -> Particles.c;
    }

    @Override
    public IParticle REDSTONE() {
        return () -> Particles.a;
    }

    @Override
    public IParticle TOTEM_OF_UNDYING() {
        return () -> Particles.ad;
    }

    @Override
    public IParticle TOWN_AURA() {
        return () -> Particles.ad;
    }

    @Override
    public IParticle WITCH() {
        return () -> Particles.ag;
    }

    @Override
    public IParticle SPELL_WITCH() {
        return () -> Particles.ag;
    }

    @Override
    public IParticle CLOUD() {
        return () -> Particles.f;
    }

    @Override
    public IParticle HEART() {
        return () -> Particles.L;
    }

    @Override
    public IParticle SOUL() {
        return () -> Particles.H;
    }

    @Override
    public IParticle EXPLOSION() {
        return () -> Particles.x;
    }

    @Override
    public IParticle EXPLOSION_HUGE() {
        return () -> Particles.w;
    }

    @Override
    public IParticle SMOKE_NORMAL() {
        return () -> Particles.Y;
    }

    @Override
    public IParticle DAMAGE_INDICATOR() {
        return () -> Particles.h;
    }

}
