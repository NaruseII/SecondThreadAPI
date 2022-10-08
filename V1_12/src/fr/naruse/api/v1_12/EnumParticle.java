package fr.naruse.api.v1_12;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.version.particle.IEnumParticle;

public class EnumParticle implements IEnumParticle {

    @Override
    public IParticle SOUL_FIRE_FLAME() {return () -> net.minecraft.server.v1_12_R1.EnumParticle.FLAME;}

    @Override
    public IParticle FIRE() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.FLAME;
    }

    @Override
    public IParticle FLAME() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.FLAME;
    }

    @Override
    public IParticle BLOCK() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.BLOCK_DUST;
    }

    @Override
    public IParticle REDSTONE() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.REDSTONE;
    }

    @Override
    public IParticle TOTEM_OF_UNDYING() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.TOTEM;
    }

    @Override
    public IParticle TOWN_AURA() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.TOWN_AURA;
    }

    @Override
    public IParticle WITCH() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.SPELL_WITCH;
    }

    @Override
    public IParticle SPELL_WITCH() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.SPELL_WITCH;
    }

    @Override
    public IParticle CLOUD() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.CLOUD;
    }

    @Override
    public IParticle HEART() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.HEART;
    }

    @Override
    public IParticle SOUL() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.FLAME;
    }

    @Override
    public IParticle EXPLOSION() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.EXPLOSION_NORMAL;
    }

    @Override
    public IParticle EXPLOSION_HUGE() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.EXPLOSION_HUGE;
    }

    @Override
    public IParticle SMOKE_NORMAL() {
        return () -> net.minecraft.server.v1_12_R1.EnumParticle.SMOKE_NORMAL;
    }

}
