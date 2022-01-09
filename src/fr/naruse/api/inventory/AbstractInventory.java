package fr.naruse.api.inventory;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.naruse.api.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public abstract class AbstractInventory implements Listener {

    protected JavaPlugin pl;
    protected Player p;
    private boolean isDone = false;
    protected Inventory inventory;

    public AbstractInventory(JavaPlugin pl, Player p, String invName, int size) {
        this(pl, p, invName, size, true);
    }

    public AbstractInventory(JavaPlugin pl, Player p, String invName, int size, boolean initInventory) {
        this.pl = pl;
        this.p = p;
        this.inventory = this.createInventory(size, invName);

        Bukkit.getPluginManager().registerEvents(this, pl);
        if(initInventory){
            initInventory(inventory);
            p.openInventory(inventory);
        }
    }

    protected abstract void initInventory(Inventory inventory);

    protected abstract void actionPerformed(Player p, ItemStack item, InventoryAction action, int slot);

    public void onClose() { }

    protected Inventory createInventory(int size, String invName){
        return Bukkit.createInventory(null, size, invName);
    }

    @EventHandler
    public void onClickEvent(InventoryClickEvent e){
        if(isDone){
            return;
        }
        if(!(e.getWhoClicked() instanceof Player)){
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if(p != this.p){
            return;
        }
        if(!e.getInventory().equals(inventory)){
            return;
        }
        if(!this.bypassOnClickedInventory(p, e.getClickedInventory())){
            if(this.cancelIfItemNull(p, e.getCurrentItem(), e.getAction(), e.getSlot()) && e.getCurrentItem() == null){
                e.setCancelled(true);
                return;
            }
            if (this.cancelInteract(p, e.getCurrentItem(), e.getAction(), e.getSlot())) {
                e.setCancelled(true);
            }
        }

        actionPerformed(p, e.getCurrentItem(), e.getAction(), e.getSlot());
    }

    @EventHandler
    public void onCloseEvent(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        if(p != this.p){
            return;
        }
        if(!e.getInventory().equals(inventory)){
            return;
        }
        onClose();
        isDone = true;
    }

    @EventHandler
    public void openEvent(InventoryOpenEvent e){
        Player p = (Player) e.getPlayer();
        if(p != this.p){
            return;
        }
        if(!e.getInventory().equals(inventory)){
            return;
        }
        isDone = false;
    }

    protected boolean cancelInteract(Player p, ItemStack item, InventoryAction action, int slot){
        return true;
    }

    protected boolean cancelIfItemNull(Player p, ItemStack item, InventoryAction action, int slot){
        return true;
    }

    protected boolean bypassOnClickedInventory(Player p, Inventory clickedInventory){
        return false;
    }

    public ItemStack buildItem(Material material, int data, String name, boolean enchant, List<String> lore){
        ItemStack itemStack = new ItemStack(material, 1, (byte) data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        if(enchant){
            meta.addEnchant(Enchantment.LUCK, 1, true);
        }
        if(lore != null){
            meta.setLore(lore);
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack buildSkull(Material material, String url, String name, boolean enchant, List<String> lore) {
        ItemStack head = this.buildItem(material, 3, name, enchant, lore);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        StringBuilder s_url = new StringBuilder();
        s_url.append(url);

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", s_url).getBytes());
        gameProfile.getProperties().put("textures", new Property("textures", new String(data)));

        try {
            Field field = skullMeta.getClass().getDeclaredField("profile");

            field.setAccessible(true);
            field.set(skullMeta, gameProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        skullMeta.setDisplayName(name);
        head.setItemMeta(skullMeta);

        return head;
    }

    private int color = -1;
    public void setDecoration(Material material, boolean useColor) {
        if(color == -1){
            color = MathUtils.RANDOM.nextInt(16);
        }
        if(color == 8){
            color++;
        }
        for (int i : this.getDecorationSlots()) {
            inventory.setItem(i, buildItem(material, useColor ? color : 0, "", false, null));
        }
    }

    public void decorateLine(Material material, boolean useColor, int line) {
        if(color == -1){
            color = MathUtils.RANDOM.nextInt(16);
        }
        if(color == 8){
            color++;
        }
        for (int i = line*9; i < line*9+9; i++) {
            inventory.setItem(i, buildItem(material, useColor ? color : 0, "", false, null));
        }
    }

    public List<Integer> getDecorationSlots() {
        List<Integer> slots = Lists.newArrayList();
        int lines = inventory.getSize() / 9;

        for (int i = 0; i < 9; i++) {
            slots.add(i);
            slots.add(i + (lines - 1) * 9);
        }

        for (int i = 0; i < lines - 1; i++) {
            slots.add(i * 9);
            slots.add(i * 9 + 8);
        }

        return slots;
    }

    public void refresh(){
        inventory.clear();
        this.initInventory(inventory);
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }

    public Inventory getInventory() {
        return inventory;
    }
}

