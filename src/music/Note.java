/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

public class Note {
    private final Key key;
    private final int octave;
    
    public Note(Key key, int octave) {
        this.key = key;
        this.octave = octave;
    }
    
    public Note(int midiNote) {
        key = Key.getKeyForOffset(midiNote % 12);
        octave = Math.floorDiv(midiNote, 12);
    }
    
    public Key getKey() {
        return key;
    }
    
    public int getOctave() {
        return octave;
    }
    
    public int getMidiNote() {
        return octave * 12 + key.getOffset();
    }
}
