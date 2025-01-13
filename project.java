import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecureFileDeletionGUI {

    private final List<File> selectedFiles = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SecureFileDeletionGUI::new);
    }

    public SecureFileDeletionGUI() {
        JFrame frame = new JFrame("Secure File Deletion System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel instructions = new JLabel("Choose an option to securely delete files:");
        instructions.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(instructions);

        JButton deleteSingleFileButton = new JButton("Delete a Single File");
        deleteSingleFileButton.addActionListener(e -> handleSingleFileDeletion(frame));
        panel.add(deleteSingleFileButton);

        JButton deleteBatchFilesButton = new JButton("Delete Multiple Files");
        deleteBatchFilesButton.addActionListener(e -> handleBatchFileSelection(frame));
        panel.add(deleteBatchFilesButton);

        JButton startBatchDeletionButton = new JButton("Start Batch Deletion");
        startBatchDeletionButton.addActionListener(e -> startBatchDeletion(frame));
        panel.add(startBatchDeletionButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void handleSingleFileDeletion(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a File to Delete");
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String passesInput = JOptionPane.showInputDialog(frame,
                    "Enter the number of overwrite passes (default is 3):");
            int passes = passesInput != null && passesInput.matches("\\d+") ? Integer.parseInt(passesInput) : 3;

            boolean success = overwriteFile(file, passes);
            JOptionPane.showMessageDialog(frame,
                    success ? "File deleted successfully!" : "Failed to delete the file.",
                    "Result",
                    success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBatchFileSelection(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setDialogTitle("Select Files for Batch Deletion");

        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files) {
                if (!selectedFiles.contains(file)) {
                    selectedFiles.add(file);
                }
            }
            JOptionPane.showMessageDialog(frame,
                    "Selected " + files.length + " files for batch deletion.",
                    "Batch Selection",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void startBatchDeletion(JFrame frame) {
        if (selectedFiles.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "No files selected for batch deletion.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String passesInput = JOptionPane.showInputDialog(frame, "Enter the number of overwrite passes (default is 3):");
        int passes = passesInput != null && passesInput.matches("\\d+") ? Integer.parseInt(passesInput) : 3;

        for (File file : selectedFiles) {
            overwriteFile(file, passes);
        }

        JOptionPane.showMessageDialog(frame,
                "Batch deletion completed.",
                "Result",
                JOptionPane.INFORMATION_MESSAGE);
        selectedFiles.clear();
    }

    private boolean overwriteFile(File file, int passes) {
        if (!file.exists() || !file.isFile()) {
            return false;
        }

        try {
            Random random = new Random();
            long length = file.length();
            byte[] buffer = new byte[1024];

            try (FileOutputStream out = new FileOutputStream(file)) {
                for (int pass = 0; pass < passes; pass++) {
                    long bytesWritten = 0;
                    while (bytesWritten < length) {
                        random.nextBytes(buffer);
                        int bytesToWrite = (int) Math.min(buffer.length, length - bytesWritten);
                        out.write(buffer, 0, bytesToWrite);
                        bytesWritten += bytesToWrite;
                    }
                    out.flush();
                }
            }

            return file.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
