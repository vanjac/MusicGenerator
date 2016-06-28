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
public interface ChordIterator extends Iterator<Chord> {
    @Override
    public Chord next();
}
