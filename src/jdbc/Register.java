package jdbc;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Register {
    public String no_mk, nama_mk, kelas, nip;

    Connector connector = new Connector();

    //DEKLARASI KOMPONEN
    JFrame window = new JFrame("Register Form");

    JLabel lusername = new JLabel("USERNAME  ");
    JTextField tfusername = new JTextField();
    JLabel lpassword= new JLabel("PASSWORD ");
    JPasswordField tfpassword = new JPasswordField();

    JButton btnTambahPanel = new JButton("Tambah");
    JButton btnReset = new JButton("Reset");
    JButton btnBack = new JButton("Back");

    public Register(){
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
        window.add(btnTambahPanel);
        window.add(btnReset);
        window.add(btnBack);



//LABEL
        lusername.setBounds(5, 35, 120, 20);
        lpassword.setBounds(5,60,120,20);

//TEXTFIELD
        tfusername.setBounds(110, 35, 120, 20);
        tfpassword.setBounds(110, 60, 120, 20);


//BUTTON PANEL
        btnTambahPanel.setBounds(250, 35, 90, 20);
        btnReset.setBounds(250,60,90,20);
        btnBack.setBounds(250,90,90,20);


        btnTambahPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int jum = 0;
                String username = tfusername.getText();
                Boolean username2 = false;
                try {
                    String result[][]= new String[50][30];
                    String querySelect = "SELECT * from users";
                    PreparedStatement pstmtSelect = connector.conn.prepareStatement(querySelect);
                    connector.statement = connector.conn.createStatement();
                    ResultSet resultSet = pstmtSelect.executeQuery(querySelect);
                    while (resultSet.next()){
                        result[jum][0] = String.valueOf(resultSet.getInt("id"));
                        result[jum][1] = resultSet.getString("username");
                        result[jum][2] = resultSet.getString("password");
                        if (result[jum][1].equals(username)){
                            username2 = true;
                            break;
                        }
                        jum++;
                    }

                    if (username2 == true){
                        JOptionPane.showMessageDialog(null,"Username Telah Digunakan");
                        hapuslayar();
                    }else {
                        String query = "INSERT INTO `users`(`id`, `username`,`password`) VALUES (NULL,'"+getusername()+"','"+getpassword()+"')";

                        connector.statement = connector.conn.createStatement();
                        connector.statement.executeUpdate(query);

                        System.out.println("Insert Berhasil");
                        JOptionPane.showMessageDialog(null,"Insert Berhasil !!");
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

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Login login = new Login();
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
