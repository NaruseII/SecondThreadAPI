package fr.naruse.api.v1_14;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.server.v1_14_R1.Particles;

public class EnumParticle implements IEnumParticle {

    @Override
    public IParticle SOUL_FIRE_FLAME() {return () -> Particles.FLAME;}

    @Override
    public IParticle FIRE() {
        return () -> Particles.FLAME;
    }

    @Override
    public IParticle FLAME() {
        return () -> Particles.FLAME;
    }

    @Override
    public IParticle BLOCK() {
        return () -> Particles.BLOCK;
    }

    @Override
    public IParticle REDSTONE() {
        return () -> Particles.DUST;
    }

    @Override
    public IParticle TOTEM_OF_UNDYING() {
        return () -> Particles.TOTEM_OF_UNDYING;
    }

    @Override
    public IParticle TOWN_AURA() {
        return () -> Particles.TOTEM_OF_UNDYING;
    }

    @Override
    public IParticle WITCH() {
        return () -> Particles.WITCH;
    }

    @Override
    public IParticle SPELL_WITCH() {
        return () -> Particles.WITCH;
    }

    @Override
    public IParticle CLOUD() {
        return () -> Particles.CLOUD;
    }

    @Override
    public IParticle HEART() {
        return () -> Particles.HEART;
    }

    @Override
    public IParticle SOUL() {
        return () -> Particles.FLAME;
    }

    @Override
    public IParticle EXPLOSION() {
        return () -> Particles.EXPLOSION;
    }

    @Override
    public IParticle EXPLOSION_HUGE() {
        return () -> Particles.EXPLOSION_EMITTER;
    }

    @Override
    public IParticle SMOKE_NORMAL() {
        return () -> Particles.SMOKE;
    }

    @Override
    public IParticle DAMAGE_INDICATOR() {
        return () -> Particles.DAMAGE_INDICATOR;
    }

}
