package fr.naruse.api.v1_20_2;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.core.particles.Particles;

public class EnumParticle implements IEnumParticle {

    @Override
    public IParticle SOUL_FIRE_FLAME() {
        return () -> {
            try {
                return Particles.class.getField("H").get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return FLAME();
        };
    }

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
        return () -> Particles.o;
    }

    @Override
    public IParticle TOTEM_OF_UNDYING() {
        return () -> Particles.ae;
    }

    @Override
    public IParticle TOWN_AURA() {
        return () -> Particles.ae;
    }

    @Override
    public IParticle WITCH() {
        return () -> Particles.ah;
    }

    @Override
    public IParticle SPELL_WITCH() {
        return () -> Particles.ah;
    }

    @Override
    public IParticle CLOUD() {
        return () -> Particles.f;
    }

    @Override
    public IParticle HEART() {
        return () -> {
            try {
                return Particles.class.getField("M").get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return FLAME();
        };
    }

    @Override
    public IParticle SOUL() {
        return () -> Particles.I;
    }

    @Override
    public IParticle EXPLOSION() {
        return () -> Particles.x;
    }

    @Override
    public IParticle EXPLOSION_HUGE() {
        return () -> Particles.x;
    }

    @Override
    public IParticle SMOKE_NORMAL() {
        return () -> Particles.Z;
    }

    @Override
    public IParticle DAMAGE_INDICATOR() {
        return () -> Particles.h;
    }

}
