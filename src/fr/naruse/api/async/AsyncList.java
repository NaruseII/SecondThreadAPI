package fr.naruse.api.async;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AsyncList<T> {

    private final List<T> syncList = Lists.newArrayList();
    private final List<T> list = Lists.newArrayList();

    public void add(T object){
        ThreadGlobal.runSync(() -> {
            if(object instanceof Entity){
                if(((Entity) object).isDead()){
                    return;
                }
            }
            if (this.syncList.contains(object)) {
                return;
            }
            this.syncList.add(object);
        });
        Runnable runnable = () -> {
            if (this.list.contains(object)) {
                return;
            }
            this.list.add(object);
        };
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(runnable);
    }

    public void remove(T object){
        ThreadGlobal.runSync(() -> {
            this.syncList.remove(object);
        });
        Runnable runnable = () -> this.list.remove(object);
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(runnable);
    }

    public List<T> getList() {
        return list;
    }

    public List<T> getSyncList() {
        return syncList;
    }

    protected boolean needToFlushValue(T value){
        return false;
    }

    public void flush(){
        if(Bukkit.isPrimaryThread()){
            CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(() -> this.flush());
            return;
        }else{
            List<T> list = this.list.stream().filter(t -> this.needToFlushValue(t)).collect(Collectors.toList());
            this.list.removeAll(list);
            ThreadGlobal.runSync(() -> this.syncList.removeAll(list));
        }

    }
}
