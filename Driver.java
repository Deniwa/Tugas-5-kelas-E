import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Driver extends JFrame {

    private JTextField nameField, nimField, prodiField, emailField;
    private Connection connection;

    public Driver() {
        setTitle("Data Mahasiswa");
        setLayout(null); // Menggunakan layout null untuk menggunakan setBounds

        // Font untuk semua komponen
        Font font = new Font("Roboto", Font.PLAIN, 14);

        // Inisialisasi komponen
        JLabel lbNama = new JLabel("Nama Lengkap:");
        lbNama.setFont(font);
        lbNama.setBounds(20, 20, 150, 30);
        add(lbNama);
        nameField = new JTextField();
        nameField.setFont(font);
        nameField.setBounds(180, 20, 300, 30);
        add(nameField);

        JLabel lbNIM = new JLabel("NIM:");
        lbNIM.setFont(font);
        lbNIM.setBounds(20, 70, 150, 30);
        add(lbNIM);
        nimField = new JTextField();
        nimField.setFont(font);
        nimField.setBounds(180, 70, 300, 30);
        add(nimField);

        JLabel lbProdi = new JLabel("Prodi:");
        lbProdi.setFont(font);
        lbProdi.setBounds(20, 120, 150, 30);
        add(lbProdi);
        prodiField = new JTextField();
        prodiField.setFont(font);
        prodiField.setBounds(180, 120, 300, 30);
        add(prodiField);

        JLabel lbEmail = new JLabel("E-mail:");
        lbEmail.setFont(font);
        lbEmail.setBounds(20, 170, 150, 30);
        add(lbEmail);
        emailField = new JTextField();
        emailField.setFont(font);
        emailField.setBounds(180, 170, 300, 30);
        add(emailField);

        JButton submitButton = new JButton("Simpan");
        submitButton.setFont(font);
        submitButton.setBounds(180, 220, 100, 40);
        add(submitButton);

        JButton viewButton = new JButton("Lihat Data Mahasiswa");
        viewButton.setFont(font);
        viewButton.setBounds(300, 220, 180, 40);
        add(viewButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DataViewWindow();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(515, 320);
        setLocationRelativeTo(null);
        setVisible(true);

        // Database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "Ridhox13+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        String name = nameField.getText();
        String nim = nimField.getText();
        String prodi = prodiField.getText();
        String email = emailField.getText();

        try {
            String query = "INSERT INTO mahasiswa (NAMA, NIM, PRODI, EMAIL) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, nim);
            statement.setString(3, prodi);
            statement.setString(4, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
                new ConfirmationWindow(name, nim, prodi, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // This will print detailed error information to the console
            JOptionPane.showMessageDialog(this, "Data gagal disimpan! Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Driver();
    }
}

class ConfirmationWindow extends JFrame {

    public ConfirmationWindow(String name, String nim, String prodi, String email) {
        setTitle("Data Mahasiswa");
        setLayout(null);

        Font font = new Font("Roboto", Font.PLAIN, 14);

        JLabel nameLabel = new JLabel("Nama: " + name);
        nameLabel.setFont(font);
        nameLabel.setBounds(20, 20, 400, 30);
        add(nameLabel);

        JLabel nimLabel = new JLabel("NIM: " + nim);
        nimLabel.setFont(font);
        nimLabel.setBounds(20, 60, 400, 30);
        add(nimLabel);

        JLabel prodiLabel = new JLabel("Prodi: " + prodi);
        prodiLabel.setFont(font);
        prodiLabel.setBounds(20, 100, 400, 30);
        add(prodiLabel);

        JLabel emailLabel = new JLabel("E-mail: " + email);
        emailLabel.setFont(font);
        emailLabel.setBounds(20, 140, 400, 30);
        add(emailLabel);

        JButton backButton = new JButton("Kembali");
        backButton.setFont(font);
        backButton.setBounds(180, 200, 100, 40);
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Driver();
                dispose();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(515, 320);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class DataViewWindow extends JFrame {

    public DataViewWindow() {
        setTitle("Data Mahasiswa");
        setSize(515, 320);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Times New Roman", Font.PLAIN, 14);

        String[] columnNames = {"NIM", "Nama", "Prodi", "Email"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(font);
        table.getTableHeader().setFont(font);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "Ridhox13+");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM mahasiswa")) {

            while (resultSet.next()) {
                String nim = resultSet.getString("NIM");
                String name = resultSet.getString("NAMA");
                String prodi = resultSet.getString("PRODI");
                String email = resultSet.getString("EMAIL");
                model.addRow(new Object[]{nim, name, prodi, email});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(scrollPane);
        setVisible(true);
    }
}
