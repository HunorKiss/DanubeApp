import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class WorkspacePanel extends JPanel {
    private JTextArea topicDescriptionTextArea;
    private JTextArea aiResponseTextArea;
    private BufferedImage backgroundImage;
    private String actualTopic;

    private MyButton generateButton;
    private MyButton deleteButton;
    private MyButton saveButton;
    private MyButton menuButton;

    public WorkspacePanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);

        // set background
        try {
            backgroundImage = ImageIO.read(new File("images/e3.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new GridLayout(1, 2));

        // left panel for specifying the topic
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);  // Make inputPanel transparent

        GridBagConstraints gbcInputPanel = new GridBagConstraints();
        gbcInputPanel.gridx = 0;
        gbcInputPanel.gridy = 0;
        gbcInputPanel.insets = new Insets(10, 10, 10, 10);

        // Label for specifying the topic further
        JLabel topicLabel = new JLabel("Please write a few more sentences about the topic you want your essay about:");
        Font currentFont1 = topicLabel.getFont();
        Font newFont1 = currentFont1.deriveFont(Font.BOLD, 16);
        topicLabel.setFont(newFont1);
        topicLabel.setForeground(Color.WHITE);
        inputPanel.add(topicLabel, gbcInputPanel);

        // Text area for the essay
        topicDescriptionTextArea = new JTextArea(3, 40);
        JScrollPane descriptionScrollPane = new JScrollPane(topicDescriptionTextArea);
        gbcInputPanel.gridy++;
        inputPanel.add(descriptionScrollPane, gbcInputPanel);

        // right panel for the result
        JPanel outputPanel = new JPanel(new GridBagLayout());
        outputPanel.setOpaque(false);  // Make outputPanel transparent
        GridBagConstraints gbcOutputPanel = new GridBagConstraints();
        gbcOutputPanel.gridx = 0;
        gbcOutputPanel.gridy = 0;
        gbcOutputPanel.insets = new Insets(20,20,20,20);

        // Label for AI Response
        JLabel aiResponseLabel = new JLabel("Here is the essay generated for your needs:");
        Font currentFont2 = aiResponseLabel.getFont();
        Font newFont2 = currentFont2.deriveFont(Font.BOLD, 16);
        aiResponseLabel.setFont(newFont2);
        aiResponseLabel.setForeground(Color.WHITE);
        outputPanel.add(aiResponseLabel, gbcOutputPanel);

        // Text area for AI Response
        aiResponseTextArea = new JTextArea(30, 30);
        aiResponseTextArea.setEditable(true);  // we may want to edit the text after generation before we save it
        aiResponseTextArea.setLineWrap(true);  // Enable line wrapping
        aiResponseTextArea.setWrapStyleWord(true);  // Wrap at word boundaries
        JScrollPane responseScrollPane = new JScrollPane(aiResponseTextArea);
        gbcOutputPanel.gridy++;
        gbcOutputPanel.gridheight = 3;
        outputPanel.add(responseScrollPane, gbcOutputPanel);

        setButtons();

        gbcInputPanel.gridy++;
        inputPanel.add(generateButton, gbcInputPanel);

        gbcOutputPanel.gridy--;
        gbcOutputPanel.gridx++;
        gbcOutputPanel.gridheight = 1;
        outputPanel.add(deleteButton, gbcOutputPanel);
        gbcOutputPanel.gridy++;
        outputPanel.add(saveButton, gbcOutputPanel);
        gbcOutputPanel.gridy++;
        outputPanel.add(menuButton, gbcOutputPanel);

        add(inputPanel);
        add(outputPanel);
    }

    public void setButtons(){

        generateButton = new MyButton("Generate!");
        generateButton.addActionListener(e -> {

            // Disable the button to prevent multiple clicks
            generateButton.setEnabled(false);

            // Use SwingWorker to perform the task in a separate thread
            SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    // Call your AI generation method here
                    String userInput = actualTopic + topicDescriptionTextArea.getText();

                    // Call your AI method with userInput and get the response
                    String aiResponse = null;
                    try {
                        aiResponse = ChatGPTClient.chatGPT(userInput);
                        aiResponse = aiResponse.replace("\\n", "\n");
                    } catch (URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }

                    return aiResponse;
                }

                @Override
                protected void done() {
                    try {
                        // Update the UI with the response
                        aiResponseTextArea.setText(get());
                    } catch (Exception ex) {
                        // Handle any exceptions that occurred during the background task
                        ex.printStackTrace();
                    } finally {
                        // Re-enable the button
                        generateButton.setEnabled(true);
                    }
                }
            };

            // Execute the SwingWorker
            worker.execute();
        });

        deleteButton = new MyButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aiResponseTextArea.setText("");
            }
        });

        saveButton = new MyButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        menuButton = new MyButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.layout.show( GameFrame.mainPanel, "MENU");
            }
        });

    }

    public void SetActualTopic(String topic){
        actualTopic = topic;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
