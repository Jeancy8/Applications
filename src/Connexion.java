import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

//import com.mysql.jdbc.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Connexion extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField saisie_utilisateur;
	private JTextField saisie_password;
	private JButton btnValider;
	
	public static String nomUti, prenomUti;
	
	final static String driver = "com.mysql.jdbc.Driver";
	final static String url = "jdbc:mysql://127.0.0.1/digicode?autoReconnect=true&useSSL=false";
	final static String user = "root";
	final static String password = "";
	
	static String verification = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_passe = ?";
	static String connecter = "UPDATE utilisateurs SET etat = ? WHERE nom_utilisateur = ?";
	static String nbConnexion = "SELECT nbConnexion FROM utilisateurs WHERE nom_utilisateur = ?";
	
	String InfoUti[] =  new String [1];
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connexion frame = new Connexion();
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
	public Connexion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(690, 300, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nom d'utilisateur");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(164, 36, 113, 14);
		contentPane.add(lblNewLabel);
		
		saisie_utilisateur = new JTextField();
		saisie_utilisateur.setBounds(110, 64, 223, 20);
		contentPane.add(saisie_utilisateur);
		saisie_utilisateur.setColumns(10);
		saisie_utilisateur.addActionListener(this);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMotDePasse.setBounds(172, 122, 94, 14);
		contentPane.add(lblMotDePasse);
		
		saisie_password = new JPasswordField();
		saisie_password.setBounds(110, 147, 223, 20);
		contentPane.add(saisie_password);
		saisie_password.setColumns(10);
		saisie_password.addActionListener(this);
		
		btnValider = new JButton("Valider");
		btnValider.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnValider.setBounds(172, 199, 89, 23);
		contentPane.add(btnValider);
		btnValider.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == btnValider){
			
			String Value_utilisateur = saisie_utilisateur.getText();
			String Value_password = saisie_password.getText();
			
			try {
				Class.forName(driver).newInstance();
				Connection con = DriverManager.getConnection(url, user, password);
				
				PreparedStatement st = con.prepareStatement(verification);
				st.setString(1, Value_utilisateur);
				st.setString(2, Value_password);
				
				ResultSet resultat = st.executeQuery();
				resultat.next();
				nomUti = resultat.getString(2);
				prenomUti = resultat.getString(3);
				
				resultat.last();
				
				int nb = resultat.getRow();
				resultat.beforeFirst();
				st.close();
				if(nb==1){
					PreparedStatement ps = con.prepareStatement(connecter);
					ps.setInt(1, 1);
					ps.setString(2, Value_utilisateur);
					ps.executeUpdate();
					
					new ListeChoix().setVisible(true);
					this.setVisible(false);
				}
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect");
			}
		}
	}

	public static String getPrenomUti() {
		return prenomUti;
	}

	public static String getNomUti() {
		return nomUti;
	}
	
	
	
	
}
