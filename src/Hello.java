import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hello extends JFrame {
    private JPanel panelMain;
    private JTextField txtName;
    private JButton submitButton;

    public Hello() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(submitButton, "Welcome "+txtName.getText());
            }
        });
    }

    public static void main(String[] args) {
       Hello h = new Hello();
       h.setContentPane(h.panelMain);
       // h.setSize(300,400);

        h.setBounds(600,200,300,300);

        h.setTitle("First Panel");
       h.setVisible(true);
       h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
