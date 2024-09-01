import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Hello extends JFrame {
    private JPanel panelMain;
    private JTextField txtName;
    private JButton submitButton;
    private JPasswordField passwordField1;
    private JButton registrationButton;

    // Percorso del database SQLite
    private static final String DATABASE_URL = "jdbc:sqlite:database.db";

    public Hello() {
        // Inizializza il database e la tabella
        initializeDatabase();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInput(submitButton, "Welcome ", false);
            }
        });

        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInput(registrationButton, "Welcome new user ", true);
            }
        });
    }

    // Questo metodo inizializza il database e crea la tabella se non esiste
    private void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    name TEXT NOT NULL,\n"
                + "    password TEXT NOT NULL\n"  // Cambiato 'email' a 'password' per coerenza
                + ");";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabella 'users' creata con successo.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Errore nella creazione del database: " + e.getMessage());
            System.exit(1);
        }
    }

    // Questo metodo gestisce l'input e mostra il messaggio appropriato
    private void handleInput(JButton button, String messagePrefix, boolean register) {
        String name = txtName.getText().trim();
        char[] password = passwordField1.getPassword();

        if (name.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(button, "Please insert input");
        } else {
            String passwordStr = new String(password); // Converti il char[] in String

            // Inserisci i dati nel database se necessario
            if (register) {
                insertData(name, passwordStr);
            }
            JOptionPane.showMessageDialog(button, messagePrefix + name);
        }
    }

    // Questo metodo inserisce i dati nel database
    private void insertData(String name, String password) {
        String sql = "INSERT INTO users(name, password) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, password); // Considera di hashare la password
            pstmt.executeUpdate();
            System.out.println("Dati inseriti con successo.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Errore nell'inserimento dei dati: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Hello h = new Hello();
        h.setContentPane(h.panelMain);
        h.setBounds(600, 200, 300, 300);
        h.setTitle("First Panel");
        h.setVisible(true);
        h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
