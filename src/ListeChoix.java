import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;


import java.awt.Image;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;

public class ListeChoix extends JFrame implements ActionListener {
	private JTextField Value_codeSecret;
	private JTextField Value_description;
	private JTextField Value_nbPersonneMax;
	private JTextField Value_etage;
	private JTextField Value_emailSalle;
	private JButton btnDeco;
	
	JComboBox liste = new JComboBox();
	String InfoDesSalles[] = new String[15];
	
	final static String driver = "com.mysql.jdbc.Driver";
	final static String url = "jdbc:mysql://127.0.0.1/digicode?autoReconnect=true&useSSL=false";
	final static String user = "root";
	final static String password = "";
	
	static String ListeSalle = "SELECT * FROM salles";
	//static String InfoSalle = "SELECT * FROM salles WHERE nom ='"+ nomSalle +"'";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListeChoix frame = new ListeChoix();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListeChoix() {
		Connexion C = new Connexion();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 300, 854, 314);
		getContentPane().setLayout(null);
		
		liste.setBounds(321, 40, 194, 20);
		getContentPane().add(liste);
		liste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nomSalle = liste.getSelectedItem().toString();
				
				RecuperationInfo(nomSalle);
			}
		});
		
		
		
		Value_codeSecret = new JTextField();
		Value_codeSecret.setBounds(374, 99, 86, 20);
		getContentPane().add(Value_codeSecret);
		Value_codeSecret.setColumns(10);
		Value_codeSecret.setEnabled(false);
		
		Value_description = new JTextField();
		Value_description.setForeground(Color.ORANGE);
		Value_description.setBounds(227, 144, 233, 20);
		getContentPane().add(Value_description);
		Value_description.setColumns(10);
		Value_description.setEnabled(false);
		
		Value_nbPersonneMax = new JTextField();
		Value_nbPersonneMax.setBounds(481, 144, 46, 20);
		getContentPane().add(Value_nbPersonneMax);
		Value_nbPersonneMax.setColumns(10);
		Value_nbPersonneMax.setEnabled(false);
		
		Value_etage = new JTextField();
		Value_etage.setBounds(481, 175, 46, 20);
		getContentPane().add(Value_etage);
		Value_etage.setColumns(10);
		Value_etage.setEnabled(false);
		
		Value_emailSalle = new JTextField();
		Value_emailSalle.setBounds(227, 175, 233, 20);
		getContentPane().add(Value_emailSalle);
		Value_emailSalle.setColumns(10);
		Value_emailSalle.setEnabled(false);
		
		JLabel lblNewLabel = new JLabel("Description");
		lblNewLabel.setFont(new Font("Agency FB", Font.BOLD, 13));
		lblNewLabel.setBounds(143, 146, 74, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Code secret");
		lblNewLabel_1.setFont(new Font("Agency FB", Font.BOLD, 13));
		lblNewLabel_1.setBounds(299, 102, 65, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Nombre personne Max");
		lblNewLabel_3.setFont(new Font("Agency FB", Font.BOLD, 13));
		lblNewLabel_3.setBounds(537, 147, 123, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Etage");
		lblNewLabel_4.setFont(new Font("Agency FB", Font.BOLD, 13));
		lblNewLabel_4.setBounds(537, 178, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Email de la salle");
		lblNewLabel_5.setFont(new Font("Agency FB", Font.BOLD, 13));
		lblNewLabel_5.setBounds(143, 177, 94, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblChoisirUneSalle = new JLabel("Choisir une salle");
		lblChoisirUneSalle.setFont(new Font("Sitka Text", Font.BOLD, 13));
		lblChoisirUneSalle.setBounds(359, 15, 127, 14);
		getContentPane().add(lblChoisirUneSalle);
		
		JLabel lblCopyrightBlondinMayunga = new JLabel("Copyright Blondin Mayunga");
		lblCopyrightBlondinMayunga.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblCopyrightBlondinMayunga.setBounds(374, 250, 135, 14);
		getContentPane().add(lblCopyrightBlondinMayunga);
		
		btnDeco = new JButton("Deconnexion");
		btnDeco.setBounds(22, 39, 127, 23);
		getContentPane().add(btnDeco);
		btnDeco.addActionListener(this);
		
		JLabel label = new JLabel("");
		label.setForeground(Color.ORANGE);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label.setBounds(22, 11, 178, 20);
		getContentPane().add(label);
		label.setText(C.getPrenomUti()+" "+ C.getNomUti());
		
		JLabel label_1 = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		img.getScaledInstance(20, 50, 50);
		label_1.setIcon(new ImageIcon(img));
		label_1.setBounds(650, 15, 165, 149);
		getContentPane().add(label_1);
		RecuperationSalle();
	}
	
	
	public void RecuperationSalle(){
		try{
			Class.forName(driver).newInstance();
			Connection con = DriverManager.getConnection(url, user, password);
			
			PreparedStatement ls = con.prepareStatement(ListeSalle);
			ResultSet ListeSalle = ls.executeQuery();
			while(ListeSalle.next()){
				liste.addItem(ListeSalle.getString(2));
			}
			
		} catch (Exception f) {
			JOptionPane.showMessageDialog(null, "Impossible de recuperer les salles");
		}
		
	}
	
	public void RecuperationInfo(String nomSalle){
		try{
			
			Class.forName(driver).newInstance();
			Connection con = DriverManager.getConnection(url, user, password);
			
			PreparedStatement ls = con.prepareStatement("SELECT * FROM salles WHERE nom ='"+ nomSalle +"'");
			ResultSet Info = ls.executeQuery();
			while(Info.next()){
				InfoDesSalles[3] = Info.getString(3);
				InfoDesSalles[4] = Info.getString(4);
				InfoDesSalles[5] = Info.getString(5);
				InfoDesSalles[6] = Info.getString(6);
				InfoDesSalles[7] = Info.getString(7);
				
				AjoutInfo(InfoDesSalles);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Impossible de recuperer les informations des salles");
		}
	}
	
	public void AjoutInfo(String InfoDesSalles[]){
		Value_description.setText(InfoDesSalles[3]);
		Value_nbPersonneMax.setText(InfoDesSalles[4]);
		Value_etage.setText(InfoDesSalles[5]);
		Value_emailSalle.setText(InfoDesSalles[6]);
		Value_codeSecret.setText(InfoDesSalles[7]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == btnDeco){
			new Connexion().setVisible(true);
			this.setVisible(false);
		}
		
		
	}
	
}
