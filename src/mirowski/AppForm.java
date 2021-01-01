package mirowski;

import mirowski.dtos.ExecutableFileDto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppForm {
    public static final String RUN_MESSAGE = "The application is running - setup is being performed!";
    public static final String PATH_TO_THE_FILE = "C:\\Michał Mirowski\\Gry\\Heroes of Might and Magic 3";
    public static final String GUI_FRAME_TITLE = "Basic Setup with Mirowski";

    private JPanel mainWindow;
    private JButton runButton;

    public AppForm() {
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ExecutableFileDto> filesParameters = configureApplication();
                runBasicAEMSetup(filesParameters);
                JOptionPane.showMessageDialog(null, RUN_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        configureAndShowGUI(new AppForm().mainWindow);

    }

    private static void configureAndShowGUI(JPanel mainWindow) {
        JFrame frame = new JFrame(GUI_FRAME_TITLE);
        frame.setContentPane(mainWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(70, 70, 1100, 600);

        frame.setVisible(true);
    }

    private static List<ExecutableFileDto> configureApplication() {
        List<ExecutableFileDto> filesParameters = new ArrayList<>();
        prepareMockEntry(filesParameters);
        return filesParameters;
    }

    private static void prepareMockEntry(List<ExecutableFileDto> filesParameters) {
        ExecutableFileDto newEntry = new ExecutableFileDto().builder()
                // ToDo#2 Ask about files names with white spaces - why don't they run with the Windows 10 cmd?
                .name("editor.lnk")
                .location(PATH_TO_THE_FILE)
                .build();
        filesParameters.add(newEntry);
    }

    private static void runBasicAEMSetup(List<ExecutableFileDto> filesParameters) {
        for (ExecutableFileDto ef : filesParameters) {
            try {
                Runtime.getRuntime().exec("cmd /c " + ef.getName() + "", null, new File(ef.getLocation()));
            } catch (IOException e) {
                // ToDo#1 Ask about logger - log.warn("...")
                e.printStackTrace();
            }
        }
    }
}

