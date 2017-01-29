package me.poutian.schematics;

import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileInputStream;

@SuppressWarnings("deprecation")
public class Schematic {

    public static void pasteSchematic(Location loc, File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            NBTTagCompound nbt = NBTCompressedStreamTools.a(fis);

            short width = nbt.getShort("Width");
            short height = nbt.getShort("Height");
            short length = nbt.getShort("Length");

            byte[] blocks = nbt.getByteArray("Blocks");
            byte[] data = nbt.getByteArray("Data");

            fis.close();




            for(int x = 0; x < width; ++x) {
                for(int y = 0; y < height; ++y) {
                    for(int z = 0; z < length; ++z) {
                        int index = y * width * length + z * width + x;

                        int xModifier = x - width / 2;
                        int yModifier = y - height / 2;
                        int zModifier = z - length / 2;

                        final Location l = new Location(loc.getWorld(), xModifier + loc.getBlockX(), yModifier + loc.getBlockY(), zModifier + loc.getBlockZ());
                        int b = blocks[index] & 0xFF;
                        final Block block = l.getBlock();
                        block.setType(Material.getMaterial(b));
                        block.setData(data[index]);
                    }
                }
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
