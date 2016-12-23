package me.florestanii.christmasevent.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ActionBar {
    private static final String NMSVER;

    static {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        NMSVER = packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    public static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + NMSVER + "." + nmsClassName);
    }

    private Object packet;
    private Object connection;
    private int age = 0;
    private int scheduler = -1;

    public ActionBar(Player p, String msg, final int duration, Plugin plugin) {
        try {
            Object chat = getNmsClass("IChatBaseComponent$ChatSerializer")
                    .getMethod("a", new Class[]{String.class})
                    .invoke(null, "{'text': '" + msg + "'}");
            packet = getNmsClass("PacketPlayOutChat")
                    .getConstructor(new Class[]{getNmsClass("IChatBaseComponent"), Byte.TYPE})
                    .newInstance(chat, (byte) 2);
            Object nmsPlayer = p.getClass().getMethod("getHandle", new Class[0]).invoke(p);
            connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.scheduler = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (duration > 0) {
                age++;
                if (age >= (duration - 2) * 20) {
                    end();
                }
            }

            try {
                connection.getClass()
                        .getMethod("sendPacket", new Class[]{getNmsClass("Packet")})
                        .invoke(connection, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1, 0);
    }

    public void end() {
        Bukkit.getServer().getScheduler().cancelTask(scheduler);
        scheduler = -1;
    }
}
