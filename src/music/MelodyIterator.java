/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;

public interface MelodyIterator extends Iterator<Note> {
    @Override
    public Note next();
    
    public Chord setChord(Chord c);
}
