package com.danikvitek.statetools;

import com.danikvitek.statetools.utils.Colored;
import com.danikvitek.statetools.utils.ToolsEnum;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class InteractionsEventListener implements Listener {
    private static final HashMap<Player, Long> cooldowns = new HashMap<>();

    public static BukkitRunnable paintBrushRunnable = new BukkitRunnable() {
        @Override
        public void run() {
        for (Player player: Bukkit.getOnlinePlayers()) {
                if (ToolsEnum.isBrush(player.getInventory().getItemInMainHand()) && player.getInventory().getItemInOffHand().getType().getKey().getKey().endsWith("_dye")) {
                    ItemStack paintedBrush = player.getInventory().getItemInMainHand();
                    LeatherArmorMeta colorMeta = (LeatherArmorMeta) paintedBrush.getItemMeta();
                    assert colorMeta != null;
                    colorMeta.setCustomModelData(Objects.requireNonNull(ToolsEnum.PAINTED_BRUSH.getItem().getItemMeta()).getCustomModelData());
                    colorMeta.setColor(DyeColor.valueOf(player.getInventory().getItemInOffHand().getType().getKey().getKey().substring(0, player.getInventory().getItemInOffHand().getType().getKey().getKey().indexOf("_")).toUpperCase()).getColor());
                    paintedBrush.setItemMeta(colorMeta);
                    player.getInventory().setItemInMainHand(paintedBrush);
                }
                else if (ToolsEnum.isPaintedBrush(player.getInventory().getItemInMainHand()) && player.getInventory().getItemInOffHand().getType().getKey().getKey().endsWith("_dye")) {
                    ItemStack paintedBrush = player.getInventory().getItemInMainHand();
                    LeatherArmorMeta colorMeta = (LeatherArmorMeta) paintedBrush.getItemMeta();
                    assert colorMeta != null;
                    colorMeta.setColor(DyeColor.valueOf(player.getInventory().getItemInOffHand().getType().getKey().getKey().substring(0, player.getInventory().getItemInOffHand().getType().getKey().getKey().indexOf("_")).toUpperCase()).getColor());
                    paintedBrush.setItemMeta(colorMeta);
                }
                else if (ToolsEnum.isBrush(player.getInventory().getItemInOffHand()) && player.getInventory().getItemInMainHand().getType().getKey().getKey().endsWith("_dye")) {
                    ItemStack paintedBrush = player.getInventory().getItemInOffHand();
                    LeatherArmorMeta colorMeta = (LeatherArmorMeta) paintedBrush.getItemMeta();
                    assert colorMeta != null;
                    colorMeta.setCustomModelData(Objects.requireNonNull(ToolsEnum.PAINTED_BRUSH.getItem().getItemMeta()).getCustomModelData());
                    colorMeta.setColor(DyeColor.valueOf(player.getInventory().getItemInMainHand().getType().getKey().getKey().substring(0, player.getInventory().getItemInMainHand().getType().getKey().getKey().indexOf("_")).toUpperCase()).getColor());
                    paintedBrush.setItemMeta(colorMeta);
                    player.getInventory().setItemInOffHand(paintedBrush);
                }
                else if (ToolsEnum.isPaintedBrush(player.getInventory().getItemInOffHand()) && player.getInventory().getItemInMainHand().getType().getKey().getKey().endsWith("_dye")) {
                    ItemStack paintedBrush = player.getInventory().getItemInOffHand();
                    LeatherArmorMeta colorMeta = (LeatherArmorMeta) paintedBrush.getItemMeta();
                    assert colorMeta != null;
                    colorMeta.setColor(DyeColor.valueOf(player.getInventory().getItemInMainHand().getType().getKey().getKey().substring(0, player.getInventory().getItemInMainHand().getType().getKey().getKey().indexOf("_")).toUpperCase()).getColor());
                    paintedBrush.setItemMeta(colorMeta);
                }
                // todo: unpaint all other brushes in the inventory
                for (int i = 0; i <= 35; i++) {
                    if (i != player.getInventory().getHeldItemSlot() || !player.getInventory().getItemInOffHand().getType().getKey().getKey().endsWith("_dye")) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (ToolsEnum.isPaintedBrush(itemStack)) {
                            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
                            assert meta != null;
                            meta.setColor(null);
                            meta.setCustomModelData(Objects.requireNonNull(ToolsEnum.BRUSH.getItem().getItemMeta()).getCustomModelData());
                            itemStack.setItemMeta(meta);
                        }
                    }
                }
                if (!player.getInventory().getItemInMainHand().getType().getKey().getKey().endsWith("_dye")) {
                    ItemStack itemStack = player.getInventory().getItemInOffHand();
                    if (ToolsEnum.isPaintedBrush(itemStack)) {
                        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
                        assert meta != null;
                        meta.setColor(null);
                        meta.setCustomModelData(Objects.requireNonNull(ToolsEnum.BRUSH.getItem().getItemMeta()).getCustomModelData());
                        itemStack.setItemMeta(meta);
                    }
                }
                if (ToolsEnum.isPaintedBrush(player.getItemOnCursor())) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) player.getItemOnCursor().getItemMeta();
                    assert meta != null;
                    meta.setColor(null);
                    meta.setCustomModelData(Objects.requireNonNull(ToolsEnum.BRUSH.getItem().getItemMeta()).getCustomModelData());
                    player.getItemOnCursor().setItemMeta(meta);
                }
            }
        }
    };

    @EventHandler
    public void onInteractWithTool(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getHand() != null &&
            event.getClickedBlock() != null &&
            player.isSneaking()) {
            if (Objects.equals(event.getHand(), EquipmentSlot.HAND)) {
                ItemStack mainHandItem = player.getInventory().getItemInMainHand();
                if (mainHandItem.isSimilar(ToolsEnum.WRENCH.getItem())) {
                    if (cooldowns.get(player) == null || cooldowns.get(player) < System.currentTimeMillis())
                        switch (event.getClickedBlock().getType()) {
                            case HOPPER:
                            case PISTON:
                            case DISPENSER:
                            case DROPPER:
                            case OBSERVER: {
                                if (event.getClickedBlock().getBlockData() instanceof Piston && ((Piston) event.getClickedBlock().getBlockData()).isExtended())
                                    break;
                                Directional directionalData = (Directional) event.getClickedBlock().getBlockData();
                                List<BlockFace> faces = new ArrayList<>(directionalData.getFaces());
                                faces.sort(Enum::compareTo);
                                int nextIndex = (faces.indexOf(directionalData.getFacing()) + 1) % faces.size();
                                directionalData.setFacing(faces.get(nextIndex));
                                event.getClickedBlock().setBlockData(directionalData);
                                cooldowns.put(player, System.currentTimeMillis() + 100L);
                                break;
                            }
                        }
                }
                else if (mainHandItem.isSimilar(ToolsEnum.HAMMER.getItem())) {
                    if (cooldowns.get(player) == null || cooldowns.get(player) < System.currentTimeMillis()) {
                        if (Tag.STAIRS.isTagged(event.getClickedBlock().getType())) {
                            Stairs stairsData = (Stairs) event.getClickedBlock().getBlockData();
                            List<Bisected.Half> halves = Arrays.asList(Bisected.Half.values());
                            List<Stairs.Shape> shape = Arrays.asList(Stairs.Shape.values());
                            List<BlockFace> faces = new ArrayList<>(stairsData.getFaces());
                            faces.sort(Enum::compareTo);

                            final int[] i = {
                                    halves.indexOf(stairsData.getHalf()),
                                    shape.indexOf(stairsData.getShape()),
                                    faces.indexOf(stairsData.getFacing()) };
                            i[0]++;
                            if (i[0] >= halves.size()) {
                                i[0] = 0;
                                i[1]++;
                                if (i[1] >= shape.size()) {
                                    i[1] = 0;
                                    i[2]++;
                                    if (i[2] >= faces.size())
                                        i[2] = 0;
                                }
                            }
                            stairsData.setHalf(halves.get(i[0]));
                            stairsData.setShape(shape.get(i[1]));
                            stairsData.setFacing(faces.get(i[2]));
                            event.getClickedBlock().setBlockData(stairsData);
                            cooldowns.put(player, System.currentTimeMillis() + 100L);
                        } else if (Tag.SLABS.isTagged(event.getClickedBlock().getType())) {
                            Slab slabData = (Slab) event.getClickedBlock().getBlockData();
                            if (!slabData.getType().equals(Slab.Type.DOUBLE)) {
                                List<Slab.Type> halves = Arrays.asList(Slab.Type.TOP, Slab.Type.BOTTOM);
                                int nextIndex = (halves.indexOf(slabData.getType()) + 1) % 2;
                                slabData.setType(halves.get(nextIndex));
                                event.getClickedBlock().setBlockData(slabData);
                                cooldowns.put(player, System.currentTimeMillis() + 100L);
                            }
                        } else if (Tag.LOGS.isTagged(event.getClickedBlock().getType()) ||
                                event.getClickedBlock().getType().equals(Material.QUARTZ_PILLAR) ||
                                event.getClickedBlock().getType().equals(Material.PURPUR_PILLAR) ||
                                event.getClickedBlock().getType().equals(Material.HAY_BLOCK)) {
                            Orientable orientableData = (Orientable) event.getClickedBlock().getBlockData();
                            List<Axis> axes = new ArrayList<>(orientableData.getAxes());
                            axes.sort(Enum::compareTo);
                            int nextIndex = (axes.indexOf(orientableData.getAxis()) + 1) % axes.size();
                            orientableData.setAxis(axes.get(nextIndex));
                            event.getClickedBlock().setBlockData(orientableData);
                            cooldowns.put(player, System.currentTimeMillis() + 100L);
                        }
                    }
                }
                else if (ToolsEnum.isPaintedBrush(mainHandItem)) {
                    if ((cooldowns.get(player) == null || cooldowns.get(player) < System.currentTimeMillis()) &&
                        Colored.hasColor(event.getClickedBlock()) && !Colored.hasSameColor(mainHandItem, event.getClickedBlock())) {
                        event.getClickedBlock().setType(
                                Material.valueOf(
                                        Arrays.stream(Material.values()).map(Material::toString).collect(Collectors.toList()).contains(event.getClickedBlock().getType().toString().replace(event.getClickedBlock().getType().toString().substring(0, event.getClickedBlock().getType().toString().indexOf("_")), Objects.requireNonNull(DyeColor.getByColor(((LeatherArmorMeta) Objects.requireNonNull(mainHandItem.getItemMeta())).getColor())).toString()))
                                        ? event.getClickedBlock().getType().toString().replace(event.getClickedBlock().getType().toString().substring(0, event.getClickedBlock().getType().toString().indexOf("_")), Objects.requireNonNull(DyeColor.getByColor(((LeatherArmorMeta) Objects.requireNonNull(mainHandItem.getItemMeta())).getColor())).toString())
                                        : event.getClickedBlock().getType().toString().replace(event.getClickedBlock().getType().toString().substring(0, event.getClickedBlock().getType().toString().indexOf("_", event.getClickedBlock().getType().toString().indexOf("_") + 1)), Objects.requireNonNull(DyeColor.getByColor(((LeatherArmorMeta) Objects.requireNonNull(mainHandItem.getItemMeta())).getColor())).toString())));
                        if (!player.getGameMode().equals(GameMode.CREATIVE))
                            player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
                        cooldowns.put(player, System.currentTimeMillis());
                    }
                }
            }
            else if (Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) {
                ItemStack offHandItem = player.getInventory().getItemInOffHand();
                if (offHandItem.isSimilar(ToolsEnum.WRENCH.getItem())) {
                    if (cooldowns.get(player) == null || cooldowns.get(player) < System.currentTimeMillis())
                        switch (event.getClickedBlock().getType()) {
                            case HOPPER:
                            case PISTON:
                            case DISPENSER:
                            case DROPPER:
                            case OBSERVER: {
                                if (event.getClickedBlock().getBlockData() instanceof Piston && ((Piston) event.getClickedBlock().getBlockData()).isExtended())
                                    break;
                                Directional directionalData = (Directional) event.getClickedBlock().getBlockData();
                                List<BlockFace> faces = new ArrayList<>(directionalData.getFaces());
                                faces.sort(Enum::compareTo);
                                int nextIndex = (faces.indexOf(directionalData.getFacing()) + 1) % faces.size();
                                directionalData.setFacing(faces.get(nextIndex));
                                event.getClickedBlock().setBlockData(directionalData);
                                cooldowns.put(player, System.currentTimeMillis());
                                break;
                            }
                        }
                }
                else if (offHandItem.isSimilar(ToolsEnum.HAMMER.getItem())) {
                    if (cooldowns.get(player) == null || cooldowns.get(player) < System.currentTimeMillis()) {
                        if (Tag.STAIRS.isTagged(event.getClickedBlock().getType())) {
                            Stairs stairsData = (Stairs) event.getClickedBlock().getBlockData();
                            List<Bisected.Half> halves = Arrays.asList(Bisected.Half.values());
                            List<Stairs.Shape> shape = Arrays.asList(Stairs.Shape.values());
                            List<BlockFace> faces = new ArrayList<>(stairsData.getFaces());
                            faces.sort(Enum::compareTo);

                            final int[] i = {
                                    halves.indexOf(stairsData.getHalf()),
                                    shape.indexOf(stairsData.getShape()),
                                    faces.indexOf(stairsData.getFacing()) };
                            i[0]++;
                            if (i[0] >= halves.size()) {
                                i[0] = 0;
                                i[1]++;
                                if (i[1] >= shape.size()) {
                                    i[1] = 0;
                                    i[2]++;
                                    if (i[2] >= faces.size())
                                        i[2] = 0;
                                }
                            }
                            stairsData.setHalf(halves.get(i[0]));
                            stairsData.setShape(shape.get(i[1]));
                            stairsData.setFacing(faces.get(i[2]));
                            event.getClickedBlock().setBlockData(stairsData);
                            cooldowns.put(player, System.currentTimeMillis());
                        } else if (Tag.SLABS.isTagged(event.getClickedBlock().getType())) {
                            Slab slabData = (Slab) event.getClickedBlock().getBlockData();
                            if (!slabData.getType().equals(Slab.Type.DOUBLE)) {
                                List<Slab.Type> halves = Arrays.asList(Slab.Type.TOP, Slab.Type.BOTTOM);
                                int nextIndex = (halves.indexOf(slabData.getType()) + 1) % 2;
                                slabData.setType(halves.get(nextIndex));
                                event.getClickedBlock().setBlockData(slabData);
                                cooldowns.put(player, System.currentTimeMillis());
                            }
                        } else if (Tag.LOGS.isTagged(event.getClickedBlock().getType()) ||
                                event.getClickedBlock().getType().equals(Material.QUARTZ_PILLAR) ||
                                event.getClickedBlock().getType().equals(Material.PURPUR_PILLAR) ||
                                event.getClickedBlock().getType().equals(Material.HAY_BLOCK)) {
                            Orientable orientableData = (Orientable) event.getClickedBlock().getBlockData();
                            List<Axis> axes = new ArrayList<>(orientableData.getAxes());
                            axes.sort(Enum::compareTo);
                            int nextIndex = (axes.indexOf(orientableData.getAxis()) + 1) % axes.size();
                            orientableData.setAxis(axes.get(nextIndex));
                            event.getClickedBlock().setBlockData(orientableData);
                            cooldowns.put(player, System.currentTimeMillis());
                        }
                    }
                }
                else if (ToolsEnum.isPaintedBrush(offHandItem)) {
                    if ((cooldowns.get(player) == null || cooldowns.get(player) < System.currentTimeMillis()) &&
                        Colored.hasColor(event.getClickedBlock()) && !Colored.hasSameColor(offHandItem, event.getClickedBlock())) {
                        event.getClickedBlock().setType(
                                Material.valueOf(
                                        Arrays.stream(Material.values()).map(Material::toString).collect(Collectors.toList()).contains(event.getClickedBlock().getType().toString().replace(event.getClickedBlock().getType().toString().substring(0, event.getClickedBlock().getType().toString().indexOf("_")), Objects.requireNonNull(DyeColor.getByColor(((LeatherArmorMeta) Objects.requireNonNull(offHandItem.getItemMeta())).getColor())).toString()))
                                        ? event.getClickedBlock().getType().toString().replace(event.getClickedBlock().getType().toString().substring(0, event.getClickedBlock().getType().toString().indexOf("_")), Objects.requireNonNull(DyeColor.getByColor(((LeatherArmorMeta) Objects.requireNonNull(offHandItem.getItemMeta())).getColor())).toString())
                                        : event.getClickedBlock().getType().toString().replace(event.getClickedBlock().getType().toString().substring(0, event.getClickedBlock().getType().toString().indexOf("_", event.getClickedBlock().getType().toString().indexOf("_") + 1)), Objects.requireNonNull(DyeColor.getByColor(((LeatherArmorMeta) Objects.requireNonNull(offHandItem.getItemMeta())).getColor())).toString())));
                        if (!player.getGameMode().equals(GameMode.CREATIVE))
                            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                        cooldowns.put(player, System.currentTimeMillis());
                    }
                }
            }
        }
    }
}
