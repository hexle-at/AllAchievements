package at.hexle.api;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;

/**
 * The class that handles some NMS/Reflection methods.
 * @author CroaBeast
 * @since 1.0
 */
public abstract class NMSHandler {

    /**
     * Server version. Example: 1.16.5
     */
    private static final String VERSION = Bukkit.getBukkitVersion().split("-")[0];
    /**
     * Server major version. Example: if {@link #VERSION} is 1.16.5, the result will be 16.
     */
    public static final int MAJOR_VERSION = Integer.parseInt(VERSION.split("\\.")[1]);

    protected Class<?> getNMSClass(String pack, String name, boolean useVs) {
        Package aPackage = Bukkit.getServer().getClass().getPackage();

        String version = aPackage.getName().split("\\.")[3];
        pack = pack != null ? pack : "net.minecraft.server";

        try {
            return Class.forName(pack + (useVs ? "." + version : "") + "." + name);
        } catch (Exception e) {
            return null;
        }
    }

    protected Class<?> getBukkitClass(String name) {
        return getNMSClass("org.bukkit.craftbukkit", name, true);
    }

    protected Object getObject(Class<?> clazz, Object initial, String method) {
        try {
            clazz = clazz != null ? clazz : initial.getClass();
            return clazz.getDeclaredMethod(method).invoke(initial);
        } catch (Exception e) {
            return null;
        }
    }

    protected Object getObject(Object initial, String method) {
        return getObject(null, initial, method);
    }

    protected Object getBukkitItem(Object nmsItem) {
        Class<?> clazz = getBukkitClass("inventory.CraftItemStack");
        if (clazz == null) return null;

        Constructor<?> ct;
        try {
            ct = clazz.getDeclaredConstructor(nmsItem.getClass());
        } catch (NoSuchMethodException e) {
            return null;
        }

        ct.setAccessible(true);
        try {
            return ct.newInstance(nmsItem);
        } catch (Exception e) {
            return null;
        }
    }

    protected String checkValue(Object value, String def) {
        return value == null ? def : value.toString();
    }

    protected String checkValue(Object value) {
        return checkValue(value, null);
    }
}