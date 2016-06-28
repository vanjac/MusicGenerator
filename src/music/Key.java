/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;
import java.util.Map.*;

/**
 *
 * @author jacob
 */
public enum Key {
    
    C (0, "C", "C"),
    Db (1, "C#", "Db"),
    D (2, "D", "D"),
    Eb (3, "D#", "Eb"),
    E (4, "E", "E"),
    F (5, "F", "F"),
    Gb (6, "F#", "Gb"),
    G (7, "G", "G"),
    Ab (8, "G#", "Ab"),
    A (9, "A", "A"),
    Bb (10, "A#", "Bb"),
    B (11, "B", "B");
    
    private int offset;
    private String sharpName;
    private String flatName;
    
    Key(int _offset, String sName, String fName) {
        offset = _offset;
        sharpName = sName;
        flatName = fName;
    }
    
    public static Key randomKey(Random random) {
        int key = (int)Math.floor(random.nextDouble() * 12);
        return getKeyForOffset(key);
    }
    
    @Override
    public String toString() {
        return sharpName;
    }
    
    public int getOffset() {
        return offset;
    }
    
    
    public static Key getKeyForOffset(int offset) {
        switch(offset % 12) {
            case 0:
                return C;
            case 1:
                return Db;
            case 2:
                return D;
            case 3:
                return Eb;
            case 4:
                return E;
            case 5:
                return F;
            case 6:
                return Gb;
            case 7:
                return G;
            case 8:
                return Ab;
            case 9:
                return A;
            case 10:
                return Bb;
            case 11:
                return B;
            default:
                return null;
        }
    }
    
    
    public Key nextRandomKey(Random random, Map<Integer, Double> offsetMap) {
        int o = getOffsetFromMap(random, offsetMap);
        
        Key k = getKeyForOffset((offset + o) % 12);
        
        return k;
    }
    
    private static int getOffsetFromMap(Random random, Map<Integer, Double> offsetMap) {
        double randomDouble = random.nextDouble();
        
        double totalProbability = 0;
        
        for(Entry<Integer, Double> e : offsetMap.entrySet()) {
            totalProbability += e.getValue();
            if(randomDouble < totalProbability) {
                return e.getKey();
            }
        }
        
        return 0;
    }
}
