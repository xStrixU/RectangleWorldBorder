package me.xstrixu.rectangleworldborder.listener;

import me.xstrixu.rectangleworldborder.point.Point2D;
import me.xstrixu.rectangleworldborder.util.SpaceUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.List;

public class PlayerMoveListener implements Listener {

    private final Point2D firstCorner;
    private final Point2D secondCorner;
    private final Point2D center;

    public PlayerMoveListener(Point2D firstCorner, Point2D secondCorner) {
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;

        center = new Point2D(firstCorner.getX() + (secondCorner.getX() - firstCorner.getX()) / 2, firstCorner.getZ() + (secondCorner.getZ() - firstCorner.getZ()) / 2);
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        Location to = event.getTo();

        if(event.getFrom().getBlock().equals(to.getBlock())) {
            return;
        }

        int xDiff = Math.abs(firstCorner.getX() - secondCorner.getX()) + 1;
        int zDiff = Math.abs(firstCorner.getZ() - secondCorner.getZ()) + 1;

        int addX = xDiff > zDiff ? to.getBlockX() - center.getX() : 0;
        int addZ = zDiff > xDiff ? to.getBlockZ() - center.getZ() : 0;

        WorldBorder worldBorder = new WorldBorder();

        worldBorder.setCenter(center.getX() + 0.5 + addX, center.getZ() + 0.5 + addZ);
        worldBorder.setSize(Math.min(xDiff, zDiff));

        int worldBorderHalfSize = (int) Math.floor(worldBorder.getSize() / 2);
        List<Point2D> points = null;

        if(addX != 0) {
            points = Arrays.asList(new Point2D(to.getBlockX() + worldBorderHalfSize, to.getBlockZ()), new Point2D(to.getBlockX() - worldBorderHalfSize, to.getBlockZ()));
        }

        if(addZ != 0) {
            points = Arrays.asList(new Point2D(to.getBlockX(), to.getBlockZ() + worldBorderHalfSize), new Point2D(to.getBlockX(), to.getBlockZ() - worldBorderHalfSize));
        }

        if(points != null) {
            for(Point2D point : points) {
                if(!SpaceUtil.isInsideSpace(firstCorner, secondCorner, point)) {
                    return;
                }
            }
        }

        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);

        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }
}
