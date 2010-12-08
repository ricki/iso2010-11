package com.umbrella.worldconq.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.umbrella.worldconq.domain.UserManager;


public class StartupWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107198177153703399L;
	private JButton AcceptButton;
	private JButton RegisterButton;
	private JPanel StartupPanel;

	private JLabel mapLabel;
	private JLabel NoticeLabel;
	private JLabel UserLabel;
	private JLabel PasswdLabel;

	private JTextField UserTextField;
	private JPasswordField PasswdField;

	private UserManager Manager;

	public StartupWindow() {
		super();
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		try {
			this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/logo.png")).getImage());
		} catch (Exception e) {
			System.out.println("Imagen no encontrada");
		}
		this.setResizable(false);
		this.setTitle("La Conquista del Mundo");
		this.setSize(400, 250);

		StartupPanel = new JPanel();
		getContentPane().add(StartupPanel, BorderLayout.CENTER);
		StartupPanel.setLayout(null);

		NoticeLabel = new JLabel();
		NoticeLabel.setText("");
		NoticeLabel.setBounds(50, 10, 300, 25);
		NoticeLabel.setForeground(new Color (255, 0, 0));


		UserLabel = new JLabel();
		UserLabel.setText("Login :");
		UserLabel.setBounds(50, 53, 100, 16);

		UserTextField = new JTextField();
		UserTextField.setBounds(150, 50, 200, 30);
		UserTextField.setToolTipText("Introduzca aqui su nombre de usuario");
		UserTextField.addKeyListener(new StartupKeyAdapter(this));

		PasswdLabel = new JLabel();
		PasswdLabel.setText("Contraseña :");
		PasswdLabel.setBounds(50, 93, 100, 16);

		PasswdField = new JPasswordField();
		PasswdField.setBounds(150, 90, 200, 30);
		PasswdField.setToolTipText("Introduzca aqui su contraseña");
		PasswdField.addKeyListener(new StartupKeyAdapter(this));

		AcceptButton = new JButton("Aceptar");
		AcceptButton.setBounds(75, 160, 100, 30);
		AcceptButton.addMouseListener(new StartupAcceptMouseAdapter(this));

		RegisterButton = new JButton("Registrarse");
		RegisterButton.setBounds(225, 160, 100, 30);
		RegisterButton.addMouseListener(new StartupRegisterMouseAdapter(this));


		mapLabel = new JLabel();
		mapLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("image/mapa.png")));
		mapLabel.setBounds(20, 0, 357, 215);


		StartupPanel.add(NoticeLabel);
		StartupPanel.add(UserLabel);
		StartupPanel.add(UserTextField);
		StartupPanel.add(PasswdLabel);
		StartupPanel.add(PasswdField);
		StartupPanel.add(AcceptButton);
		StartupPanel.add(RegisterButton);
		StartupPanel.add(mapLabel);

		Manager = new UserManager();
	}

	private String getUser() {
		return UserTextField.getText();

	}


	@SuppressWarnings("deprecation")
	private String getPasswd() {
		return PasswdField.getText();		
	}


	private class StartupRegisterMouseAdapter extends MouseAdapter {

		private StartupWindow stw;

		public StartupRegisterMouseAdapter(StartupWindow stw) {
			this.stw = stw;
		}

		public void mouseClicked(MouseEvent evt) {

			stw.setVisible(false);
			JFrame f = new JFrame();
			RegisterDialog dlg = new RegisterDialog(f, "La Conquista del Mundo - Registro", true);
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);

			if (dlg.getSelection() == true) {
				try{
					stw.Manager.registerUser(dlg.getUser(), dlg.getPasswd(), dlg.getEmail());
					stw.NoticeLabel.setText("Usuario :" + dlg.getUser()+" registrado");
					NoticeLabel.setForeground(new Color (0, 200, 0));
				}
				catch(Exception e) {
					stw.NoticeLabel.setText("Error en el registro");
					NoticeLabel.setForeground(new Color (255, 0, 0));
				}
			}

			stw.setVisible(true);
			f.dispose();


		}
	}

	private class StartupAcceptMouseAdapter extends MouseAdapter {

		private StartupWindow stw;

		public StartupAcceptMouseAdapter(StartupWindow stw) {
			this.stw = stw;
		}

		public void mouseClicked(MouseEvent evt) {
			try{
				stw.Manager.createSession(stw.getUser(), stw.getPasswd());
				stw.NoticeLabel.setText("Logeado : " + stw.getUser());
				NoticeLabel.setForeground(new Color (0, 200, 0));
			}
			catch(Exception e) {
				stw.NoticeLabel.setText("Contraseña o login Erroneos");
				NoticeLabel.setForeground(new Color (255, 0, 0));
			}

		}
	}


	private class StartupKeyAdapter extends KeyAdapter {
		private StartupWindow stw;

		public  StartupKeyAdapter(StartupWindow stw) {
			this.stw = stw;
		}

		public void keyPressed(KeyEvent evt) {
			if(evt.getKeyCode() == 10) {
				try {
					stw.Manager.createSession(stw.getUser(), stw.getPasswd());
					stw.NoticeLabel.setText("Logeado : " + stw.getUser());
					NoticeLabel.setForeground(new Color (0, 200, 0));
				}
				catch(Exception e) {
					stw.NoticeLabel.setText("Contraseña o login Erroneos");
					NoticeLabel.setForeground(new Color (255, 0, 0));
				}
			}
		}
	}
}
