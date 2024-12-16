package main.java;
import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.net.*;

import static javax.sound.midi.ShortMessage.*;

public class MuzMachine {
    private String user;
    private int trackNumber;
    private Vector<String> listVector = new Vector<>();
    private HashMap<String, boolean[]> recivedCompositions = new HashMap<>();
    private JList<String> recivedList;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;

    private Gui gui;
    private List<JCheckBox> checkBoxs = new ArrayList<JCheckBox>();
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

        
    public MuzMachine(String userName) {
        this.user = userName; 
        configurateServerConection();
        configurateMidi();
        gui = new Gui();
    }

    
    private void configurateServerConection() {
        try (Socket socket = new Socket("127.0.0.1", 4242)) {
               output = new ObjectOutputStream(socket.getOutputStream()); 
               input = new ObjectInputStream(socket.getInputStream());
               ExecutorService executor = Executors.newFixedThreadPool(2);
               executor.execute(() -> new RemoteDataReader());
        } catch(IOException ex) {
            System.out.println("Unable to comunicated with teh server");
        }
        
    }
    
    private void configurateMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch(MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void createTracks() {
        ArrayList<Integer> trackList;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new ArrayList<>();
            int key = instruments[i];
            for (int j = 0; j < 16; j++) {
                if (checkBoxs.get(j + (16 * i)).isSelected()) 
                    trackList.add(key);
                else
                    trackList.add(null);
            }
            createTrack(trackList);
            track.add(createEvent(CONTROL_CHANGE, 1, 127, 0, 16));
        }
        track.add(createEvent(PROGRAM_CHANGE, 9, 1, 0, 15));
    }
      

    private void createTrack(ArrayList<Integer> trackList) {
        for (int i = 0; i < trackList.size(); i++) {
            Integer key  =  trackList.get(i);
            if (key != null) {
                track.add(createEvent(NOTE_ON, 9, key, 100, i));
                track.add(createEvent(NOTE_OFF, 9, key, 100, i+1));
            }
        }
    }

    private static MidiEvent createEvent(int req, int chanel, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage msg = new ShortMessage(req, chanel, one, two);
            event = new MidiEvent(msg, tick);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public void play() {
        createTracks();
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(120);
            sequencer.start();
        }catch(Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static void main(String[] args) {
        MuzMachine machine = new MuzMachine(args[0]);
    }

    private void  changePace(float tempo) {
        float basicTempo = sequencer.getTempoFactor();
        sequencer.setTempoFactor(tempo * basicTempo);
    }

    private void sendMusicPackage() {
        boolean[] track = gui.getCheckBoxesStatus();
        try {
            output.writeObject(user + trackNumber + ": " + gui.userMessage.getText()); 
            output.writeObject(track);
        } catch(IOException ex) {
            System.out.println("Sending message failed");
            ex.printStackTrace();
        }
        gui.userMessage.setText("");
    }

    private void saveTrack() {
        boolean[] track = gui.getCheckBoxesStatus();
        File file = gui.getSelectedFile();

        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
            os.writeObject(track);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        gui.userMessage.setText("");
         
    }
    
    private void loadTrack() {
        File sourceFile = gui.getSelectedFile();
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(sourceFile))) { boolean[] compozition = (boolean[]) is.readObject();
            gui.modifieCompozition(compozition);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sequencer.stop();
        play();
    }

    class Gui {
        private JFrame frame;
        private Box buttonArea, labelArea;
        private JPanel checkBoxPane;
        private JTextArea userMessage;
        private JList<String> recivedMessages;

        public Gui() {
            frame = configurateFrame();
            buttonArea = configurateButtonArea();
            configurateUserMessageArea();
            configurateRecivedMessageArea();
            labelArea = configurateLabelArea();
            checkBoxPane = configurateCheckBoxPane();
            addComponents(); 
            frame.setBounds(50,50,300,300);
            frame.pack();                
            frame.setVisible(true);
        }

        private JFrame configurateFrame() {
           JFrame frame = new JFrame("MuzMachine");
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setContentPane(configurateMainPane());
           return frame;
        }

        private JPanel configurateMainPane() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return panel;
        } 
        
        private Box configurateButtonArea(){
            Box buttonArea = new Box(BoxLayout.Y_AXIS);

            JButton start = new JButton("Start");
            start.addActionListener(e -> play()); // We do not need to specifie the object here because the inside class is conected with object
            buttonArea.add(start);                                      

            JButton stop = new JButton("Stop");
            stop.addActionListener(e -> sequencer.stop());
            buttonArea.add(stop);

            JButton speedUp = new JButton("Speed up");
            speedUp.addActionListener(e -> changePace(1.1f));
            buttonArea.add(speedUp);            

            JButton slowDown = new JButton("Slow down");
            slowDown.addActionListener(e -> changePace(0.9f));
            buttonArea.add(slowDown);

            JButton save = new JButton("Save");
            save.addActionListener(event -> saveTrack());
            buttonArea.add(save);

            JButton load = new JButton("Load");
            load.addActionListener(event -> loadTrack());
            buttonArea.add(load);

            JButton clearCheckBoxes = new JButton("Clear compozition");
            clearCheckBoxes.addActionListener(event -> clearCheckBoxes());
            buttonArea.add(clearCheckBoxes);

            JButton send = new JButton("Send");
            send.addActionListener(event -> sendMusicPackage());
            buttonArea.add(send);

            return buttonArea;
        }

        private void configurateUserMessageArea() {
            userMessage = new JTextArea();
            userMessage.setLineWrap(true);
            userMessage.setWrapStyleWord(true);
            JScrollPane userMessagePane = new JScrollPane(userMessage);
            buttonArea.add(userMessagePane);
        }

        private void configurateRecivedMessageArea() {
            recivedMessages = new JList<>();
            recivedMessages.addListSelectionListener(new ListSelectedUnit());
            recivedMessages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane list = new JScrollPane(recivedMessages);
            buttonArea.add(list);
            recivedMessages.setListData(listVector);
        }

        private Box configurateLabelArea() {
            Box labelArea = new Box(BoxLayout.Y_AXIS);

            String[] instrumentNames  = {"Bass Drum", "Clsed Hi-Hat", "Open Hi-Hat",
                                    "Acoustic Snare", "Crash Cymbal", "Hand Clap",
                                    "High Tom", "Hi Bongo", "Maracas", "Whistle", 
                                    "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom",
                                    "High Agogo", "Open HI Conga"};

            for (String instrumentName : instrumentNames) { 
                JLabel label = new JLabel(instrumentName);
                label.setBorder(BorderFactory.createEmptyBorder(4,1,4,1));
                labelArea.add(label);
            }
            return labelArea;
        }

        private JPanel configurateCheckBoxPane() {
            JPanel checkBox = new JPanel();
            GridLayout grid  = new GridLayout(16, 16);
            grid.setVgap(1);
            grid.setHgap(2);
            checkBox.setLayout(grid);
            

            for (int i = 0; i < 256; i++) {
                JCheckBox box = new JCheckBox();
                box.setSelected(false);
                checkBox.add(box);
                checkBoxs.add(box);
            }
            return checkBox;
        }
        
        private void addComponents() {
            frame.getContentPane().add(BorderLayout.WEST, labelArea);
            frame.getContentPane().add(BorderLayout.CENTER, checkBoxPane);
            frame.getContentPane().add(BorderLayout.EAST, buttonArea);
        }

        private boolean[] getCheckBoxesStatus() {
            boolean[] trackStatus = new boolean[256];
            for (JCheckBox box : checkBoxs) 
                if (box.isSelected()) 
                    trackStatus[checkBoxs.indexOf(box)] = true;
            return trackStatus;
               
        }

        private File getSelectedFile() {
           JFileChooser fileChooser = new JFileChooser(); 
           fileChooser.showOpenDialog(frame);
           return fileChooser.getSelectedFile();
        }
        // This option is also correct but this belowe is more "good-looking"
        //
        //private void modifieCompozition(boolean[] compozitionToLoad) {
        //    clearCheckBoxes();
        //    for (int i = 0; i < 256; i++) 
        //        if (compozitionToLoad[i])
        //            checkBoxs.get(i).setSelected(true);
        //}

        // I'm talking about this option 
        private void modifieCompozition(boolean[] compozitionToLoad) {
            for (int i = 0; i < 256; i++) 
               checkBoxs.get(i).setSelected(compozitionToLoad[i]); 
            
        }
        private void clearCheckBoxes() {
            for (JCheckBox box : checkBoxs) 
                if (box.isSelected()) 
                    box.setSelected(false);
        }
    }
    class RemoteDataReader implements Runnable {
        public void run() {
            try {
                Object obj;
                while ((obj = input.readObject()) != null) {
                    System.out.println("Object read from server");
                    System.out.println(obj.getClass());
                    
                    String nameToDisplay = (String) obj;
                    boolean[] checkedFildes = (boolean[]) input.readObject();
                    recivedCompositions.put(nameToDisplay, checkedFildes);
                    listVector.add(nameToDisplay);
                    recivedList.setListData(listVector);
                }
            } catch(IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }

    class ListSelectedUnit implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent lse) {
            if (!lse.getValueIsAdjusting()) {
                String marked = gui.recivedMessages.getSelectedValue();
                if (marked != null) {
                    boolean[] checkFields = recivedCompositions.get(marked);
                    gui.modifieCompozition(checkFields); 
                    sequencer.stop();
                    play();
                }
            }
        }
    }
}

