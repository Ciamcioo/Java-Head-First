package main.java.com;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class App {
    private GraphicPanel panel;
    private Random random = new Random();

     public static void main(String[] args){
        App app = new App();
        app.run();
     }
    
     public void guiConfiguration() {
        JFrame frame = new JFrame("Animation");
        panel = new GraphicPanel();
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(30, 30, 300, 300);
        frame.setVisible(true);
     }

  

     public void run() {
        guiConfiguration(); 

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(panel, new int[]{127});
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            int note;
            for (int i = 5; i < 61; i++) {
                note = random.nextInt(50) + 1;
                track.add(createEvent(ShortMessage.NOTE_ON, 1, note, 100, i));
                track.add(createEvent(ShortMessage.CONTROL_CHANGE, 1, 127, 0, i));
                track.add(createEvent(ShortMessage.NOTE_OFF, 1, note, 100, i + 2));
            }

            sequencer.setSequence(sequence);
            sequencer.start();
            sequencer.setTempoInBPM(220);
        } catch(Exception e) {
            e.printStackTrace();
        }
     }

    public static MidiEvent createEvent(int req, int channel, int note, int instrument, int tact) {
        MidiEvent event = null;
        try {
            ShortMessage msg = new ShortMessage(req, channel, note, instrument);
            event = new MidiEvent(msg, tact);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return event;
    } 

    class GraphicPanel extends JPanel implements ControllerEventListener{
        private static int COLOR_SEED = 256, SIZE_SEED = 120, LOCATION_SEED = 40;
        private boolean isDrawable = false;
        
        public void controlChange(ShortMessage event) {
            isDrawable = true;
            repaint();
        }
        
        public void paintComponent(Graphics graphics) {
            Graphics2D graphics2d = (Graphics2D) graphics;
            if (isDrawable) {
                int red = random.nextInt(COLOR_SEED);
                int green = random.nextInt(COLOR_SEED);
                int blue = random.nextInt(COLOR_SEED);

                graphics2d.setColor(new Color(red, green, blue));
                
                int width = random.nextInt(SIZE_SEED);
                int height = random.nextInt(SIZE_SEED);

                int x = random.nextInt(LOCATION_SEED);
                int y = random.nextInt(LOCATION_SEED);

                graphics2d.fillRect(x, y, width, height);
                isDrawable = false; 
            }
        }
    }
    
}
