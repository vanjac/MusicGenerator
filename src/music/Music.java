/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.util.*;
import javax.sound.midi.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author jacob
 */
public class Music extends Thread {
    private boolean isPlaying;
    private double measureLength;
    
    private Synthesizer synth;
    private Receiver synthReceiver;
    
    private Random random;
    
    ShortMessage inst1Message;
    ShortMessage inst2Message;
    
    public Music() {
        super();
        isPlaying = false;
        measureLength = 2000;
        
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            synthReceiver = synth.getReceiver();
        } catch (MidiUnavailableException e) {
            System.err.println(e);
            return;
        }
        
        random = new Random();
        
        try {
            inst1Message = new ShortMessage();
            inst1Message.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 0, 0);

            inst2Message = new ShortMessage();
            inst2Message.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 0, 0);
        } catch(InvalidMidiDataException e) {
            System.err.println(e);
            return;
        }
    }
    
    public void stopMusic() {
        System.out.println("Stop music...");
        isPlaying = false;
    }
    
    public double setMeasureLength(double length) {
        measureLength = length;
        return length;
    }
    
    public int setInstrument1(int inst) {
        try {
            inst1Message.setMessage(ShortMessage.PROGRAM_CHANGE, 0, inst, 0);
        } catch (InvalidMidiDataException e) {
            System.err.println(e);
            return inst;
        }
        
        return inst;
    }
    
    public int setInstrument2(int inst) {
        try {
            inst2Message.setMessage(ShortMessage.PROGRAM_CHANGE, 0, inst, 0);
        } catch (InvalidMidiDataException e) {
            System.err.println(e);
            return inst;
        }
        
        return inst;
    }
    
    @Override
    public void run() {
        System.out.println("Start music...");
        
        isPlaying = true;
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println(e);
            return;
        }
        
        ChordIterator cIterate = new SimpleChordIterator(random);
        Chord c = cIterate.next();
        
        MelodyIterator mIterate;
        mIterate = new WeightedMelodyIterator(
                new WeightedMelodyIteratorProperties(),
                c, 5, 48, 84, random);
        
        
        try {
            while(isPlaying) {
                List<Integer> rhythm = new ArrayList<>();
                rhythm.addAll(new FractalRhythm(random, 3).next());
                //System.out.println("New rhythm...");
                
                c = cIterate.next();
                System.out.println("--- Chord: " + c + " ---");
                
                mIterate.setChord(c);
                
                List<Key> chordNotes = c.getChordNotes();
                
                chordNotes.add(c.getRoot());

                List<ShortMessage> playMessages = new ArrayList<>();
                List<ShortMessage> stopMessages = new ArrayList<>();
                for(Key key : chordNotes) {
                    int octave = 5;
                    if(random.nextBoolean()) {
                        octave = 4;
                    }
                    if(key.equals(c.getRoot())) {
                        octave = 3;
                    }

                    Note n = new Note(key, octave);

                    ShortMessage playMessage = new ShortMessage();
                    ShortMessage stopMessage = new ShortMessage();

                    playMessage.setMessage(ShortMessage.NOTE_ON, 0, n.getMidiNote(), 93);
                    stopMessage.setMessage(ShortMessage.NOTE_OFF, 0, n.getMidiNote(), 93);

                    playMessages.add(playMessage);
                    stopMessages.add(stopMessage);
                }


                synthReceiver.send(inst1Message, -1);
                for(ShortMessage playMessage : playMessages) {
                    synthReceiver.send(playMessage, -1);
                }
                
                for(Integer length : rhythm) {
                    ShortMessage playMessage = new ShortMessage();
                    ShortMessage stopMessage = new ShortMessage();
                    
                    Note n = mIterate.next();
                    
                    System.out.print(n.getKey());
                    
                    playMessage.setMessage(ShortMessage.NOTE_ON, 0, n.getMidiNote(), 93);
                    stopMessage.setMessage(ShortMessage.NOTE_OFF, 0, n.getMidiNote(), 93);
                    
                    synthReceiver.send(inst2Message, -1);
                    synthReceiver.send(playMessage, -1);
                    wait(length);
                    synthReceiver.send(stopMessage, -1);
                }
                
                for(ShortMessage stopMessage : stopMessages) {
                    synthReceiver.send(stopMessage, -1);
                }
            }
        } catch (InvalidMidiDataException e) {
            System.err.println(e);
            return;
        }
        
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println(e);
            return;
        }
        
        synth.close();
    }
    
    private void wait(int length) {
        try {
            System.out.println(": " + (int)Math.pow(2.0, length));
            Thread.sleep((long)(measureLength / Math.pow(2.0, length)));
        } catch (InterruptedException e) {
            System.err.println(e);
            return;
        }
    }
    
}
