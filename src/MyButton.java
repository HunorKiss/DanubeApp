import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    MyButton(String name){

        setPreferredSize(new Dimension(300, 50));
        setText(name);

        setBorder(BorderFactory.createLineBorder(Color.WHITE,5));
        setMargin(new Insets(30,30,30,30));
        setFont( new Font("Arial", Font.BOLD, 24));
        setOpaque(false);
        setContentAreaFilled(false);
        setForeground(new Color(255, 255, 255));
    }
}
