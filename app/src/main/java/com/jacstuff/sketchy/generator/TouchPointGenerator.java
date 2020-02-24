package com.jacstuff.sketchy.generator;

import com.jacstuff.sketchy.Orb;
import com.jacstuff.sketchy.TouchPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TouchPointGenerator {


    public TouchPointGenerator(){

    }


    public List<Orb> generate(int size){
        List<Orb> orbs = new ArrayList<>(size);
        for(int i=0; i< size; i++){
            orbs.add(new Orb(getRandom(), getRandom(), getRandomBool(), getRandomBool(),4));
        }
        return orbs;
    }

    public void alter(List<Orb> orbs){
        for(Orb orb : orbs){
            orb.update();
        }

    }


    private int getRandom(){
        return ThreadLocalRandom.current().nextInt(800);

    }

    private boolean getRandomBool(){
        return ThreadLocalRandom.current().nextBoolean();
    }

}
