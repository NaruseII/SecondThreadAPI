package fr.naruse.api.particle.version;

import fr.naruse.api.v1_12.Version1_12;
import fr.naruse.api.v1_13.Version1_13;
import fr.naruse.api.v1_14.Version1_14;
import fr.naruse.api.v1_15.Version1_15;
import fr.naruse.api.v1_16.Version1_16;
import fr.naruse.api.v1_17.*;
import fr.naruse.api.v1_18.Version1_18;
import fr.naruse.api.v1_19.Version1_19;

public enum MinecraftVersion {

    V1_12("1.12", Version1_12.class),
    V1_13("1.13", Version1_13.class),
    V1_14("1.14", Version1_14.class),
    V1_15("1.15", Version1_15.class),
    V1_16("1.16", Version1_16.class),
    V1_17("1.17", Version1_17.class),
    V1_18("1.18", Version1_18.class),
    V1_19("1.19", Version1_19.class),
    ;


    private static MinecraftVersion currentVersion = V1_12;

    private final String versionTag;
    private final Class<? extends IVersion> clazz;
    private IVersion version;

    MinecraftVersion(String versionTag, Class<? extends IVersion> clazz) {
        this.versionTag = versionTag;
        this.clazz = clazz;
    }

    public IVersion build(){
        try {
            return this.version = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getVersionTag() {
        return versionTag;
    }

    public static void setVersion(String version){
        for (MinecraftVersion value : values()) {
            if(version.contains(value.getVersionTag())){
                currentVersion = value;
                return;
            }
        }
    }

    public static MinecraftVersion getCurrentVersion(){
        return currentVersion;
    }
}
