package satisfyu.candlelight.registry;

import de.cristelknight.doapi.DoApiExpectPlatform;
import net.minecraft.world.level.block.Block;

public class FlammableBlockRegistry {

    public static void init(){

        add(5, 20, ObjectRegistry.FLOORBOARD.get());
    }

    public static void add(int burnOdd, int igniteOdd, Block... blocks){
        DoApiExpectPlatform.addFlammable(burnOdd, igniteOdd, blocks);
    }
}
