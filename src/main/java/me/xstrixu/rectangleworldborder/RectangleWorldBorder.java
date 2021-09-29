package me.xstrixu.rectangleworldborder;

import me.xstrixu.rectangleworldborder.listener.PlayerMoveListener;
import me.xstrixu.rectangleworldborder.point.Point2D;
import org.bukkit.plugin.java.JavaPlugin;

public class RectangleWorldBorder extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Point2D firstCorner = new Point2D(getConfig().getInt("firstCorner.x"), getConfig().getInt("firstCorner.z"));
        Point2D secondCorner = new Point2D(getConfig().getInt("secondCorner.x"), getConfig().getInt("secondCorner.z"));

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(firstCorner, secondCorner), this);

        getLogger().info("Wlaczono!");
    }
}
