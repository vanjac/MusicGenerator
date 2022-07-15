/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;

public class WeightedMelodyIterator implements MelodyIterator {
    private enum NoteType {
        ROOT, CHORD, SCALE, OTHER
    }
    
    
    private final WeightedMelodyIteratorProperties props;
    private final int minNote;
    private final int maxNote;
    
    private double currentRootNoteWeight;
    private double currentChordNoteWeight;
    private double currentScaleNoteWeight;
    private double currentOtherNoteWeight;
    
    private Note previousNote;
    
    private Chord currentChord;
    
    private final Random random;
    
    public WeightedMelodyIterator(WeightedMelodyIteratorProperties properties,
            Chord chord, int octave, int min, int max, Random random) {
        props = properties;
        currentChord = chord;
        minNote = min;
        maxNote = max;
        
        previousNote = new Note(currentChord.getRoot(), octave);
        
        currentRootNoteWeight = props.rootNoteWeight;
        currentChordNoteWeight = props.chordNoteWeight;
        currentScaleNoteWeight = props.scaleNoteWeight;
        currentOtherNoteWeight = props.otherNoteWeight;
        
        this.random = random;
    }
    
    @Override
    public Chord setChord(Chord c) {
        currentChord = c;
        return c;
    }

    @Override
    public boolean hasNext() {
        return true;
    }
    
    
    @Override
    public Note next() {
        Map<Note, Double> weightMap = constructNoteWeightMap();
        WeightedRandomizer<Note> randomize = new WeightedRandomizer<>(random);
        previousNote = randomize.pickRandom(weightMap);
        
        switch(getNoteType(previousNote.getKey())) {
            case ROOT:
                System.out.print(" RootNote: ");
                currentRootNoteWeight *= props.repeatedRootNoteWeight;
                currentChordNoteWeight = props.chordNoteWeight;
                currentScaleNoteWeight = props.scaleNoteWeight;
                currentOtherNoteWeight = props.otherNoteWeight;
                break;
            case CHORD:
                System.out.print("ChordNote: ");
                currentRootNoteWeight = props.rootNoteWeight;
                currentChordNoteWeight *= props.repeatedChordNoteWeight;
                currentScaleNoteWeight = props.scaleNoteWeight;
                currentOtherNoteWeight = props.otherNoteWeight;
                break;
            case SCALE:
                System.out.print("ScaleNote: ");
                currentRootNoteWeight = props.rootNoteWeight;
                currentChordNoteWeight = props.chordNoteWeight;
                currentScaleNoteWeight *= props.repeatedScaleNoteWeight;
                currentOtherNoteWeight = props.otherNoteWeight;
                break;
            default: // OTHER
                System.out.print("OtherNote: ");
                currentRootNoteWeight = props.rootNoteWeight;
                currentChordNoteWeight = props.chordNoteWeight;
                currentScaleNoteWeight = props.scaleNoteWeight;
                currentOtherNoteWeight *= props.repeatedOtherNoteWeight;
                break;
        }
        
        
        return previousNote;
    }
    
    private Map<Note, Double> constructNoteWeightMap() {
        Map<Note, Double> weightMap = new HashMap<>(maxNote - minNote);
        
        for(int i = minNote; i < maxNote; i++) {
            Note n = new Note(i);
            weightMap.put(n, getWeightForNote(n));
        }
        
        return weightMap;
    }
    
    private double getWeightForNote(Note n) {
        double weight;
        
        switch(getNoteType(n.getKey())) {
            case ROOT:
                weight = currentRootNoteWeight;
                break;
            case CHORD:
                weight = currentChordNoteWeight;
                break;
            case SCALE:
                weight = currentScaleNoteWeight;
                break;
            default: // OTHER
                weight = currentOtherNoteWeight;
                break;
        }
        
        int distanceToPrevious =
                Math.abs(n.getMidiNote() - previousNote.getMidiNote());
        
        weight *= Math.pow(props.noteDistanceWeight, distanceToPrevious);
        
        return weight;
    }
    
    private NoteType getNoteType(Key k) {
        if(k.equals(currentChord.getRoot()))
            return NoteType.ROOT;
        for(Key testK : currentChord.getChordNotes()) {
            if(testK.equals(k))
                return NoteType.CHORD;
        }
        for(Key testK : currentChord.getScaleNotes()) {
            if(testK.equals(k))
                return NoteType.SCALE;
        }
        
        return NoteType.OTHER;
    }
}
