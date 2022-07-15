/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;

public class MajorChord implements Chord {
    private final Key root;
    
    public MajorChord(Key _root) {
        root = _root;
    }
    
    @Override
    public List<Key> getChordNotes() {
        List<Key> keys = new ArrayList<>(2);
        int rootOffset = root.getOffset();
        keys.add(Key.getKeyForOffset(rootOffset + 4));
        keys.add(Key.getKeyForOffset(rootOffset + 7));
        
        return keys;
    }
    
    @Override
    public List<Key> getScaleNotes() {
        List<Key> keys = new ArrayList<>(4);
        int rootOffset = root.getOffset();
        keys.add(Key.getKeyForOffset(rootOffset + 2));
        keys.add(Key.getKeyForOffset(rootOffset + 5));
        keys.add(Key.getKeyForOffset(rootOffset + 9));
        keys.add(Key.getKeyForOffset(rootOffset + 11));
        
        return keys;
    }
    
    @Override
    public List<Key> getOtherNotes() {
        List<Key> keys = new ArrayList<>(5);
        int rootOffset = root.getOffset();
        keys.add(Key.getKeyForOffset(rootOffset + 1));
        keys.add(Key.getKeyForOffset(rootOffset + 3));
        keys.add(Key.getKeyForOffset(rootOffset + 6));
        keys.add(Key.getKeyForOffset(rootOffset + 8));
        keys.add(Key.getKeyForOffset(rootOffset + 10));
        
        return keys;
    }
    
    @Override
    public Key getRoot() {
        return root;
    }
    
    @Override
    public String toString() {
        return root.toString();
    }
    
}
