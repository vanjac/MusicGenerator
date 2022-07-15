/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;

public class FractalRhythm implements RhythmIterator {
    private final int limit;
    private final Random random;
    
    public FractalRhythm(Random _random, int _limit) {
        limit = _limit;
        random = _random;
    }
    
    @Override
    public boolean hasNext() {
        return true;
    }
    
    @Override
    public List<Integer> next() {
        return rhythmRecursive(0, limit, random);
    }
    
    private List<Integer> rhythmRecursive(int value, int limit, Random random) {
        List<Integer> currentList = new ArrayList<>();
        
        boolean splitRhythm;
        
        if(value == 0) {
            //a whole note rhythm is boring, so make it less likely
            splitRhythm = random.nextInt(4) != 0; //3 in 4 chance
        } else {
            splitRhythm = random.nextBoolean(); //1 in 2 chance
        }
        
        if(splitRhythm && value < limit) {
            if(random.nextBoolean() || value+1 >= limit) {
                currentList = rhythmRecursive(value + 1, limit, random);
                currentList.addAll(rhythmRecursive(value + 1, limit, random));
            } else {
                currentList = rhythmRecursive(value + 2, limit, random);
                currentList.addAll(rhythmRecursive(value + 1, limit, random));
                currentList.addAll(rhythmRecursive(value + 2, limit, random));
            }
        } else {
            currentList.add(value);
        }
        
        return currentList;
    }
}
