package fr.naruse.api.particle.sender;

import fr.naruse.api.particle.Particle;
import org.bukkit.entity.Player;

import java.util.Set;

public final class ParticleSender {

    private final SendType sendType;
    private final Object[] objects;

    public ParticleSender(SendType sendType, Object... objects) {
        this.sendType = sendType;
        this.objects = objects;
    }

    public static ParticleSender buildToAll() {
        return new ParticleSender(SendType.TO_ALL);
    }

    public static ParticleSender buildToNearby(int x, int y, int z) {
        return new ParticleSender(SendType.TO_NEARBY, x, y, z);
    }

    public static ParticleSender buildToNearbyFifty() {
        return new ParticleSender(SendType.TO_NEARBY, 50, 50, 50);
    }

    public static ParticleSender buildToOne(Player player) {
        return new ParticleSender(SendType.TO_ONE, player);
    }

    public static ParticleSender buildToSome(Set<Player> set) {
        return new ParticleSender(SendType.TO_SOME_SET, set);
    }

    public static ParticleSender buildToSome(Player... players) {
        return new ParticleSender(SendType.TO_SOME_ARRAY, players);
    }

    public void send(Particle particle) {
        switch (sendType) {
            case TO_ALL:
                particle.toAll();
                break;
            case TO_ONE:
                particle.toOne((Player) objects[0]);
                break;
            case TO_NEARBY:
                particle.toNearby((int) objects[0], (int) objects[1], (int) objects[2]);
                break;
            case TO_SOME_SET:
                particle.toSome((Set<Player>) objects[0]);
                break;
            case TO_SOME_ARRAY:
                particle.toSome((Player[]) objects[0]);
                break;
        }
    }

    public SendType sendType() {
        return sendType;
    }

    public Object[] objects() {
        return objects;
    }

}
