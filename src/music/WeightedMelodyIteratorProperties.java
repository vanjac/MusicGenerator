/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

/**
 *
 * @author jacob
 */
public class WeightedMelodyIteratorProperties {
    public WeightedMelodyIteratorProperties(
            double distanceWeight,
            double rootWeight, double chordWeight, double scaleWeight, double otherWeight,
            double repeatRootWeight, double repeatChordWeight, double repeatScaleWeight, double repeatOtherWeight) {

        noteDistanceWeight = distanceWeight;

        rootNoteWeight = rootWeight;
        chordNoteWeight = chordWeight;
        scaleNoteWeight = scaleWeight;
        otherNoteWeight = otherWeight;

        repeatedRootNoteWeight = repeatRootWeight;
        repeatedChordNoteWeight = repeatChordWeight;
        repeatedScaleNoteWeight = repeatScaleWeight;
        repeatedOtherNoteWeight = repeatOtherWeight;
    }

    public WeightedMelodyIteratorProperties() {
        noteDistanceWeight = 0.9;

        rootNoteWeight = 4;
        chordNoteWeight = 5;
        scaleNoteWeight = 3;
        otherNoteWeight = 0;

        repeatedRootNoteWeight = 0.5;
        repeatedChordNoteWeight = 0.8;
        repeatedScaleNoteWeight = 0.2;
        repeatedOtherNoteWeight = 0;
    }

    // Fraction by which note weight is multiplied by for every chromatic
    // note it is away from the previous note.
    public final double noteDistanceWeight;

    //Initial weight for each note type
    public final double rootNoteWeight;
    public final double chordNoteWeight;
    public final double scaleNoteWeight;
    public final double otherNoteWeight;

    // "Repeated weights" are the fraction by which the original weight is
    // multiplied every time the note type is used. The values reset to the
    // default when a different note type is used
    public final double repeatedRootNoteWeight;
    public final double repeatedChordNoteWeight;
    public final double repeatedScaleNoteWeight;
    public final double repeatedOtherNoteWeight;
}
