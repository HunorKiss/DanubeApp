import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    static JPanel mainPanel = new JPanel();
    static MenuPanel menuPanel = new MenuPanel();
    static WorkspacePanel workspacePanel = new WorkspacePanel();
    static CardLayout layout = new CardLayout();

    public GameFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        mainPanel.setLayout( layout );
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(workspacePanel, "WORKSPACE");
        add(mainPanel);

        setTitle("Danube Essay Generator App");
        ImageIcon logo = new ImageIcon("images/note.jpg");
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
