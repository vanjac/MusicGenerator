/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;

/**
 *
 * @author jacob
 */
public class SimpleChordIterator implements ChordIterator {
    //Previous chord:
    private boolean major;
    private Key key;
    private final Random random;
    
    public SimpleChordIterator(Random random) {
        this.random = random;
        key = Key.randomKey(random);
        major = random.nextBoolean();
    }
    
    public SimpleChordIterator(Random random, Key previousKey, boolean wasMajor) {
        this.random = random;
        key = previousKey;
        major = wasMajor;
    }
    
    @Override
    public boolean hasNext() {
        return true;
    }
    
    @Override
    public Chord next() {
        if(major) {
            switch(random.nextInt(5)) {
                case 0: //A Minor
                    key = Key.getKeyForOffset(key.getOffset() + 9);
                    major = false;
                    break;
                case 1: //F Major
                    key = Key.getKeyForOffset(key.getOffset() + 5);
                    major = true;
                    break;
                case 2: //E Minor
                    key = Key.getKeyForOffset(key.getOffset() + 4);
                    major = false;
                    break;
                case 3: //G Major
                    key = Key.getKeyForOffset(key.getOffset() + 7);
                    major = true;
                    break;
                default: //Same chord
                    break;
            }
        } else {
            switch(random.nextInt(5)) {
                case 0: //F Major
                    key = Key.getKeyForOffset(key.getOffset() + 8);
                    major = true;
                    break;
                case 1: //D Minor
                    key = Key.getKeyForOffset(key.getOffset() + 5);
                    major = false;
                    break;
                case 2: //C Major
                    key = Key.getKeyForOffset(key.getOffset() + 3);
                    major = true;
                    break;
                case 3: //E Minor
                    key = Key.getKeyForOffset(key.getOffset() + 7);
                    major = false;
                    break;
                default: //Same chord
                    break;
            }
        }
        
        return makeChord(key, major);
    }
    
    private Chord makeChord(Key k, boolean major) {
        if(major) {
            return new MajorChord(k);
        } else {
            return new MinorChord(k);
        }
    }
}
