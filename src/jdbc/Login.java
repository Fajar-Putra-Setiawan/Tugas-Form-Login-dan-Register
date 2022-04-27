package jdbc;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Login {
    public String no_mk, nama_mk, kelas, nip;

    Connector connector = new Connector();

    //DEKLARASI KOMPONEN
    JFrame window = new JFrame("Login Form");

    JLabel lusername = new JLabel("USERNAME  ");
    JTextField tfusername = new JTextField();
    JLabel lpassword= new JLabel("PASSWORD ");
    JPasswordField tfpassword = new JPasswordField();

    JButton btnLogin = new JButton("Login");
    JButton btnReset = new JButton("Reset");
    JButton btnRegister = new JButton("Register");

    public Login(){
        window.setLayout(null);
        window.setSize(500,200);
        //  window.setDefaultCloseOperation(3);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setResizable(false);


        // setDefaultCloseOperation(EXIT_ON_CLOSE);

//ADD COMPONENT
        window.add(lusername);
        window.add(tfusername);
        window.add(tfpassword);
        window.add(lpassword);
        window.add(btnLogin);
        window.add(btnReset);
        window.add(btnRegister);



//LABEL
        lusername.setBounds(5, 35, 120, 20);
        lpassword.setBounds(5,60,120,20);

//TEXTFIELD
        tfusername.setBounds(110, 35, 120, 20);
        tfpassword.setBounds(110, 60, 120, 20);


//BUTTON PANEL
        btnLogin.setBounds(250, 35, 90, 20);
        btnReset.setBounds(250,60,90,20);
        btnRegister.setBounds(250,90,90,20);


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int jumlah = 0;
                String username = tfusername.getText();
                String password = new String(tfpassword.getPassword());
                Boolean berhasilLogin = false;
                try {
                    String result[][] = new String[100][30];
                    String query = "SELECT * FROM users";
                    PreparedStatement pstmt = connector.conn.prepareStatement(query);
                    connector.statement = connector.conn.createStatement();
                    ResultSet resultSet = pstmt.executeQuery(query);

                    if (tfusername.getText().equals("") || tfpassword.getPassword().equals("")){
                        JOptionPane.showMessageDialog(null,"Data Tidak Boleh Kosong","Pesan", JOptionPane.ERROR_MESSAGE);
                        hapuslayar();
                    } else {
                        while (resultSet.next()) {
                            result[jumlah][0] = String.valueOf(resultSet.getInt("id"));
                            result[jumlah][1] = resultSet.getString("username");
                            result[jumlah][2] = resultSet.getString("password");
                            if ((result[jumlah][1].equals(username)) && (result[jumlah][2].equals(password))) {
                                berhasilLogin = true;
                            }
                            jumlah++;
                        }
                    }

                    if (berhasilLogin==true){
                        JOptionPane.showMessageDialog(null, "Login Berhasil!");
                    }else{
                        JOptionPane.showMessageDialog(null,"Login Gagal");
                        JOptionPane.showMessageDialog(null,"Invalid Username/Password");
                        hapuslayar();

                    }
                } catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                hapuslayar();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Register register = new Register();
                window.dispose();
            }
        });

    }
    public String getusername(){
        return tfusername.getText();
    }

    public String getpassword() {
        return tfpassword.getText();
    }

    private void hapuslayar(){
        tfusername.setText("");
        tfpassword.setText("");
    }
}
