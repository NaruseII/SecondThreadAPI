package fr.naruse.api.v1_19_4;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.core.particles.Particles;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;

public class EnumParticle implements IEnumParticle {

    @Override
    public IParticle SOUL_FIRE_FLAME() {return () -> Particles.J;}

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
        return () -> Particles.ag;
    }

    @Override
    public IParticle TOWN_AURA() {
        return () -> Particles.ag;
    }

    @Override
    public IParticle WITCH() {
        return () -> Particles.aj;
    }

    @Override
    public IParticle SPELL_WITCH() {
        return () -> Particles.aj;
    }

    @Override
    public IParticle CLOUD() {
        return () -> Particles.f;
    }

    @Override
    public IParticle HEART() {
        return () -> {
            try {
                return Particles.class.getField("O").get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return FLAME();
        };
    }

    @Override
    public IParticle SOUL() {
        return () -> Particles.K;
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
        return () -> Particles.ab;
    }

}
