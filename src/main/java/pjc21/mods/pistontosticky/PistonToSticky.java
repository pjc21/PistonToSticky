package pjc21.mods.pistontosticky;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.PistonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(PistonToSticky.MODID)
public class PistonToSticky
{
    public static final String MODID = "pistontosticky";
    private static boolean isSticky = true;

    public PistonToSticky() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = PistonToSticky.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class PistonCheckEvent {

        @SubscribeEvent
        public static void onPistonPreEvent(PistonEvent.Pre event) {

            if (event.getState().getBlock().getRegistryName() == Blocks.PISTON.getRegistryName()) {
                BlockPos blockPos = event.getFaceOffsetPos();
                Block block = event.getWorld().getBlockState(blockPos).getBlock();

                if (block.getRegistryName() == Blocks.SLIME_BLOCK.getRegistryName()) {
                    isSticky = false;
                    event.getWorld().destroyBlock(blockPos, false);
                }
            }
        }

        @SubscribeEvent
        public static void onPistonPostEvent(PistonEvent.Post event) {

            if (!isSticky) {
                event.getWorld().setBlock(event.getPos(), Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBlock.FACING, event.getDirection()), 0);
                isSticky = true;
            }
        }
    }
}
