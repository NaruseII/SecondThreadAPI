package fr.naruse.api.async;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CollectionManager {

    public static final AntiConcurrentBufferSet<Runnable> INFINITE_SECOND_THREAD_RUNNABLE_SET = new AntiConcurrentBufferSet();
    public static final AntiConcurrentBufferSet<Runnable> SECOND_THREAD_RUNNABLE_SET = new AntiConcurrentBufferSet();
    public static final PoolExecutor POOL_EXECUTOR = new PoolExecutor();
    public static final AsyncList<Entity> ASYNC_ENTITY_LIST = new AsyncList<Entity>(){

        @Override
        protected boolean needToFlushValue(Entity entity) {
            return entity.isDead();
        }
    };

    static {

        INFINITE_SECOND_THREAD_RUNNABLE_SET.add(new Runnable() {

            private long millis = 0;

            @Override
            public void run() {
                if(System.currentTimeMillis()-this.millis > 15000){
                    this.millis = System.currentTimeMillis();
                    ASYNC_ENTITY_LIST.flush();
                }
            }
        });

    }

    public static class AntiConcurrentBufferSet<T extends Runnable> implements Iterable<T> {

        private final Set<T> set = Sets.newHashSet();

        public void add(T key){
            ThreadGlobal.getExecutorService().submit(() -> {
                set.add(key);
            });
        }

        public boolean contains(T key){
            return set.contains(key);
        }

        public boolean isEmpty(){
            return set.isEmpty();
        }

        public void clear(){
            set.clear();
        }

        @Override
        public Iterator<T> iterator() {
            return set.iterator();
        }
    }

    public static class PoolExecutor {

        public void submit(Runnable runnable){
            Future future = ThreadGlobal.getPoolExecutor().submit(runnable);
            ThreadGlobal.getPoolExecutor().submit(() -> {
                try {
                    future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public static class BiMap<T, K> {

        private final com.google.common.collect.BiMap<T, K> map = HashBiMap.create();

        public BiMap(BiMap<T, K> map) {
            this.map.putAll(map.map);
        }

        public BiMap() {
        }

        protected void onPut(T t, K k){

        }

        public void put(T t, K k){
            this.onPut(t, k);

            this.map.put(t, k);
        }

        public K get(T t){
            return this.map.get(t);
        }

        public void remove(T t){
            this.map.remove(t);
        }

        public int size(){
            return this.map.size();
        }

        public boolean contains(T t){
            return this.map.containsKey(t);
        }

        public com.google.common.collect.BiMap<K, T> inverse(){
            return this.map.inverse();
        }

        public void forEachValue(Consumer<K> consumer){
            this.map.values().forEach(consumer);
        }

        public void forEachKey(Consumer<T> consumer){
            this.map.keySet().forEach(consumer);
        }

        public void forEach(BiConsumer<T, K> consumer){
            this.map.forEach(consumer);
        }

        public void clear(){
            this.map.clear();
        }

        public boolean isEmpty(){
            return this.map.isEmpty();
        }

        public BiMap<T, K> clone(){
            return new BiMap<T, K>(this);
        }

        public Set<T> keySet(){
            return this.map.keySet();
        }

        public Set<T> clonedKeySet(){
            return Sets.newHashSet(this.map.keySet());
        }

        public java.util.List<T> clonedKeyList(){
            return Lists.newArrayList(this.map.keySet());
        }

        public Set<K> values(){
            return this.map.values();
        }

        public Set<K> clonedValues(){
            return Sets.newHashSet(this.map.values());
        }
    }

    public static class Map<T, K> {

        private final java.util.Map<T, K> map = Maps.newHashMap();

        public Map(Map<T, K> map) {
            this.map.putAll(map.map);
        }

        public Map() {
        }

        protected void onPut(T t, K k){

        }

        public void put(T t, K k){
            this.onPut(t, k);

            this.map.put(t, k);
        }

        public K get(T t){
            return this.map.get(t);
        }

        public void remove(T t){
            this.map.remove(t);
        }

        public int size(){
            return this.map.size();
        }

        public boolean contains(T t){
            return this.map.containsKey(t);
        }

        public void forEachValue(Consumer<K> consumer){
            this.map.values().forEach(consumer);
        }

        public void forEachKey(Consumer<T> consumer){
            this.map.keySet().forEach(consumer);
        }

        public void forEach(BiConsumer<T, K> consumer){
            this.map.forEach(consumer);
        }

        public void clear(){
            this.map.clear();
        }

        public boolean isEmpty(){
            return this.map.isEmpty();
        }

        public Map<T, K> clone(){
            return new Map<T, K>(this);
        }

        public Set<T> keySet(){
            return this.map.keySet();
        }

        public Set<T> clonedKeySet(){
            return Sets.newHashSet(this.map.keySet());
        }

        public java.util.List<T> clonedKeyList(){
            return Lists.newArrayList(this.map.keySet());
        }

        public Collection<K> values(){
            return this.map.values();
        }

        public Set<K> clonedValues(){
            return Sets.newHashSet(this.map.values());
        }
    }

    public static class List<T> {

        private final java.util.List<T> list = Lists.newArrayList();

        public List(List<T> list) {
            this.list.addAll(list.list);
        }

        public List() {
        }

        protected void onAdd(T t){

        }

        public void add(T t){
            this.onAdd(t);

            this.list.add(t);
        }

        public T getByIndex(int index){
            return this.list.get(index);
        }

        public void remove(T t){
            this.list.remove(t);
        }

        public int size(){
            return this.list.size();
        }

        public boolean contains(T t){
            return this.list.contains(t);
        }

        public void forEach(Consumer<T> consumer){
            this.list.forEach(consumer);
        }

        public void clear(){
            this.list.clear();
        }

        public boolean isEmpty(){
            return this.list.isEmpty();
        }

        public List<T> clone(){
            return new List<T>(this);
        }
    }

}
