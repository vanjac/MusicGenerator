/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;

public class WeightedRandomizer<E> {
    private Random random;
    
    public WeightedRandomizer(Random r) {
        random = r;
    }
    
    public E pickRandom(Map<E, Double> weightMap) {
        double totalWeight = getTotalWeight(weightMap);
        double randomDouble = random.nextDouble() * totalWeight;
        double totalProbability = 0;
        
        for(Map.Entry<E, Double> e : weightMap.entrySet()) {
            totalProbability += e.getValue();
            if(randomDouble < totalProbability) {
                return e.getKey();
            }
        }
        
        return null;
    }
    
    private double getTotalWeight(Map<E, Double> weightMap) {
        double totalWeight = 0;
        for(Map.Entry<E, Double> e : weightMap.entrySet()) {
            totalWeight += e.getValue();
        }
        
        return totalWeight;
    }
}
