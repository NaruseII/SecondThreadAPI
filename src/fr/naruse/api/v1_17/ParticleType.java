package fr.naruse.api.v1_17;

import com.google.common.collect.Maps;
import fr.naruse.api.ParticleUtils;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.core.particles.Particles;

import java.util.Map;

public class ParticleType {

    public static ParticleParam SOUL_FIRE_FLAME;
    public static ParticleParam FIRE;
    public static ParticleParam FLAME;
    public static net.minecraft.core.particles.Particle<ParticleParamBlock> BLOCK;
    public static net.minecraft.core.particles.Particle<ParticleParamRedstone> REDSTONE;
    public static ParticleParam TOTEM_OF_UNDYING;
    public static ParticleParam TOWN_AURA;
    public static ParticleParam WITCH;
    public static ParticleParam SPELL_WITCH;
    public static ParticleParam CLOUD;
    public static ParticleParam HEART;
    public static ParticleParam SOUL;
    public static ParticleParam EXPLOSION;
    public static ParticleParam EXPLOSION_HUGE;
    public static ParticleParam SMOKE_NORMAL;

    public static final Map<String, String> FIELD_NAME_FOR_PARTICLE = Maps.newHashMap();

    static{
        if(ParticleUtils.DOUBLE_VERSION == 1.17){
            FIELD_NAME_FOR_PARTICLE.put("SOUL_FIRE_FLAME", "D");
            FIELD_NAME_FOR_PARTICLE.put("FIRE", "C");
            FIELD_NAME_FOR_PARTICLE.put("FLAME", "C");
            FIELD_NAME_FOR_PARTICLE.put("BLOCK", "r");
            FIELD_NAME_FOR_PARTICLE.put("REDSTONE", "p");
            FIELD_NAME_FOR_PARTICLE.put("TOTEM_OF_UNDYING", "aa");
            FIELD_NAME_FOR_PARTICLE.put("TOWN_AURA", "aa");
            FIELD_NAME_FOR_PARTICLE.put("WITCH", "ad");
            FIELD_NAME_FOR_PARTICLE.put("SPELL_WITCH", "ad");
            FIELD_NAME_FOR_PARTICLE.put("CLOUD", "g");
            FIELD_NAME_FOR_PARTICLE.put("HEART", "I");
            FIELD_NAME_FOR_PARTICLE.put("SOUL", "E");
            FIELD_NAME_FOR_PARTICLE.put("EXPLOSION", "y");
            FIELD_NAME_FOR_PARTICLE.put("EXPLOSION_HUGE", "y");
            FIELD_NAME_FOR_PARTICLE.put("SMOKE_NORMAL", "V");
        }else if(ParticleUtils.DOUBLE_VERSION == 1.18){
            FIELD_NAME_FOR_PARTICLE.put("SOUL_FIRE_FLAME", "C");
            FIELD_NAME_FOR_PARTICLE.put("FIRE", "B");
            FIELD_NAME_FOR_PARTICLE.put("FLAME", "B");
            FIELD_NAME_FOR_PARTICLE.put("BLOCK", "c");
            FIELD_NAME_FOR_PARTICLE.put("REDSTONE", "o");
            FIELD_NAME_FOR_PARTICLE.put("TOTEM_OF_UNDYING", "Z");
            FIELD_NAME_FOR_PARTICLE.put("TOWN_AURA", "Z");
            FIELD_NAME_FOR_PARTICLE.put("WITCH", "ac");
            FIELD_NAME_FOR_PARTICLE.put("SPELL_WITCH", "ac");
            FIELD_NAME_FOR_PARTICLE.put("CLOUD", "f");
            FIELD_NAME_FOR_PARTICLE.put("HEART", "H");
            FIELD_NAME_FOR_PARTICLE.put("SOUL", "D");
            FIELD_NAME_FOR_PARTICLE.put("EXPLOSION", "x");
            FIELD_NAME_FOR_PARTICLE.put("EXPLOSION_HUGE", "x");
            FIELD_NAME_FOR_PARTICLE.put("SMOKE_NORMAL", "V");
        }

        FIELD_NAME_FOR_PARTICLE.forEach((currentField, particleField) -> {
            try {
                ParticleType.class.getDeclaredField(currentField).set(null, Particles.class.getDeclaredField(particleField).get(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
