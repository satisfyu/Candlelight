package net.satisfy.candlelight.block;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.satisfy.candlelight.registry.StorageTypes;

public class TrayBlock extends CakeStandBlock {
    public TrayBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int size() {
        return 6;
    }

    @Override
    public Identifier type() {
        return StorageTypes.TRAY;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN};
    }

    @Override
    public int getSection(Float f, Float y) {
        return 0;
        /*
        int nSection;
        float oneS = (float) 1 / 6;

        if (f < oneS) {
            nSection = 0;
        }
        else if(f < oneS*2){
            nSection = 1;
        }
        else if(f < oneS*3){
            nSection = 2;
        }
        else if(f < oneS*4){
            nSection = 3;
        }
        else if(f < oneS*5){
            nSection = 4;
        }
        else nSection = 5;

        return 5 - nSection;
         */
    }
}
