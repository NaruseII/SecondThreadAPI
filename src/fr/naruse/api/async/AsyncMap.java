package fr.naruse.api.async;

import com.google.common.collect.Maps;

import java.util.Map;

public class AsyncMap<T, E> {

    private final Map<T, E> syncMap = Maps.newHashMap();
    private final Map<T, E> map = Maps.newHashMap();
    private boolean locked = false;

    public void put(T object, E value){
        ThreadGlobal.runSync(() -> this.syncMap.put(object, value));
        Runnable runnable = () -> {
            if(this.locked){
                this.put(object, value);
                return;
            }
            this.map.put(object, value);
        };
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(runnable);
    }

    public void remove(T object){
        ThreadGlobal.runSync(() -> this.syncMap.remove(object));
        Runnable runnable = () -> {
            if(this.locked){
                this.remove(object);
                return;
            }
            this.map.remove(object);
        };
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(runnable);
    }

    public void clear(){
        ThreadGlobal.runSync(() -> this.syncMap.clear());
        Runnable runnable = () -> {
            if(this.locked){
                this.clear();
                return;
            }
            this.map.clear();
        };
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(runnable);
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public Map<T, E> getMap() {
        return this.map;
    }

    public Map<T, E> getSyncMap() {
        return this.syncMap;
    }
}
