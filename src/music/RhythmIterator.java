/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;

public interface RhythmIterator extends Iterator<List<Integer>> {
    //0 is a whole note, 1 is a half note, 2 is a quarter note, etc
    @Override
    public List<Integer> next();
}
