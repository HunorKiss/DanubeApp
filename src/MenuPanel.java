import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

    private BufferedImage backgroundImage;
    private String basicPrompt;

    private MyButton geographyButton;
    private MyButton historyButton;
    private MyButton cultureButton;
    private MyButton otherTopicButton;
    private MyButton exitButton;
    public MenuPanel(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);

        // set background
        try {
            backgroundImage = ImageIO.read(new File("images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setButtons();
        setLayout(new GridLayout(1, 2));

        ImageIcon danubeIcon = new ImageIcon("images/danube.jpg");
        JLabel imageLabel = new JLabel(danubeIcon);

        JLabel buttonLabel = new JLabel();
        buttonLabel.setBackground( new Color(0, 0, 0, 0) );

        buttonLabel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20,20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonLabel.add(geographyButton, gbc);
        gbc.gridy = 1;
        buttonLabel.add(historyButton, gbc);
        gbc.gridy = 2;
        buttonLabel.add(cultureButton, gbc);
        gbc.gridy = 3;
        buttonLabel.add(exitButton, gbc);

        add(imageLabel);
        add(buttonLabel);

        basicPrompt = "Please write an interesting essay about the river Danube that describes its main" +
                "characteristics, considering additional aspects below:\n";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void setButtons() {

        geographyButton = new MyButton("Geography");
        geographyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.layout.show(GameFrame.mainPanel, "WORKSPACE");
                GameFrame.workspacePanel.SetActualTopic(basicPrompt + "I would like you to put" +
                        " the emphasis on its geographical properties, the countries and areas that it crosses" +
                        "alongside its route, and so on.\n");
            }
        });

        historyButton = new MyButton("History");
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.layout.show(GameFrame.mainPanel, "WORKSPACE");
                GameFrame.workspacePanel.SetActualTopic(basicPrompt + "I would like you to put" +
                        " the emphasis on its historical impact, the way it influenced human civilisation" +
                        " and the role it played in the flow of history.\n");
            }
        });

        cultureButton = new MyButton("Culture");
        cultureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.layout.show(GameFrame.mainPanel, "WORKSPACE");
                GameFrame.workspacePanel.SetActualTopic(basicPrompt + "I would like you to put" +
                        " the emphasis on its cultural influence, all the poets and writers that choose it" +
                        " as the subject of their work, also mentioning paintings, music and other forms of" +
                        " art about it.\n");
            }
        });

        otherTopicButton = new MyButton("Other topic");
        otherTopicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.layout.show(GameFrame.mainPanel, "WORKSPACE");
                GameFrame.workspacePanel.SetActualTopic(basicPrompt);
            }
        });

        exitButton = new MyButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
