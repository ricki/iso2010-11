package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.umbrella.worldconq.WorldConqApp;
import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.exceptions.EmptyStringException;
import com.umbrella.worldconq.exceptions.MalformedEmailException;

import exceptions.UserAlreadyExistsException;
import exceptions.WrongLoginException;

public class StartupWindow extends JFrame {

	private final WorldConqApp app;
	private final UserManager usrMgr;

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

	public StartupWindow(WorldConqApp app, UserManager usrMgr) {
		super();
		this.app = app;
		this.usrMgr = usrMgr;
		this.initGUI();
	}

	private void initGUI() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		try {
			this.setIconImage(new ImageIcon(
				this.getClass().getClassLoader().getResource("image/logo.png")).getImage());
		} catch (final Exception e) {
			System.out.println("Imagen no encontrada");
		}
		this.setResizable(false);
		this.setTitle("La Conquista del Mundo");
		this.setSize(400, 250);

		StartupPanel = new JPanel();
		this.getContentPane().add(StartupPanel, BorderLayout.CENTER);
		StartupPanel.setLayout(null);

		NoticeLabel = new JLabel();
		NoticeLabel.setText("");
		NoticeLabel.setBounds(50, 10, 300, 25);
		NoticeLabel.setForeground(Color.RED);

		UserLabel = new JLabel();
		UserLabel.setText("Login :");
		UserLabel.setBounds(50, 53, 100, 16);

		UserTextField = new JTextField();
		UserTextField.setBounds(150, 50, 200, 30);
		UserTextField.setToolTipText("Introduzca aqui su nombre de usuario");
		UserTextField.addActionListener(new CreateSessionAction(this));

		PasswdLabel = new JLabel();
		PasswdLabel.setText("Contraseña :");
		PasswdLabel.setBounds(50, 93, 100, 16);

		PasswdField = new JPasswordField();
		PasswdField.setBounds(150, 90, 200, 30);
		PasswdField.setToolTipText("Introduzca aqui su contraseña");
		PasswdField.addActionListener(new CreateSessionAction(this));

		AcceptButton = new JButton("Aceptar");
		AcceptButton.setBounds(75, 160, 100, 30);
		AcceptButton.addActionListener(new CreateSessionAction(this));

		RegisterButton = new JButton("Registrarse");
		RegisterButton.setBounds(225, 160, 100, 30);
		RegisterButton.addMouseListener(new StartupRegisterMouseAdapter(this));

		mapLabel = new JLabel();
		mapLabel.setIcon(new ImageIcon(
			this.getClass().getClassLoader().getResource("image/mapa.png")));
		mapLabel.setBounds(20, 0, 357, 215);

		StartupPanel.add(NoticeLabel);
		StartupPanel.add(UserLabel);
		StartupPanel.add(UserTextField);
		StartupPanel.add(PasswdLabel);
		StartupPanel.add(PasswdField);
		StartupPanel.add(AcceptButton);
		StartupPanel.add(RegisterButton);
		StartupPanel.add(mapLabel);
		this.setLocationRelativeTo(null);

	}

	private String getUser() {
		return UserTextField.getText();

	}

	@SuppressWarnings("deprecation")
	private String getPasswd() {
		return PasswdField.getText();
	}

	private class StartupRegisterMouseAdapter extends MouseAdapter {

		private final StartupWindow stw;

		public StartupRegisterMouseAdapter(StartupWindow stw) {
			this.stw = stw;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			boolean invalidArgument = false;

			do {
				invalidArgument = true;
				final JFrame f = new JFrame();
				final RegisterDialog dlg = new RegisterDialog(f,
					"La Conquista del Mundo - Registro", true);
				dlg.setLocationRelativeTo(null);
				dlg.setVisible(true);

				if (dlg.getSelection() == true) {
					System.out.println("Iniciando registro");
					try {
						usrMgr.registerUser(
							dlg.getUser(), dlg.getPasswd(), dlg.getEmail());
						stw.NoticeLabel.setText("Usuario registrado con éxito.");
						NoticeLabel.setForeground(new Color(0, 200, 0));
						stw.UserTextField.setText(dlg.getUser());
						stw.PasswdField.requestFocusInWindow();
						invalidArgument = false;
					} catch (final RemoteException e) {
						JOptionPane.showMessageDialog(stw, e.toString(),
							e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
						app.setStartupMode();
						invalidArgument = false;
					} catch (final UserAlreadyExistsException e) {
						stw.NoticeLabel.setText("El usuario seleccionado ya existe.");
						NoticeLabel.setForeground(Color.RED);
						invalidArgument = false;
					} catch (final MalformedEmailException e) {
						JOptionPane.showMessageDialog(stw,
							"Dirección de email mal escrita.",
							e.getClass().getName(), JOptionPane.WARNING_MESSAGE);
					} catch (final EmptyStringException e) {
						JOptionPane.showMessageDialog(stw,
							"Debes rellenar todos los parámetros.",
							e.getClass().getName(), JOptionPane.WARNING_MESSAGE);
					}
					System.out.println("Fin registro");
				} else {
					stw.NoticeLabel.setText("");
					invalidArgument = false;
				}
				f.dispose();
			} while (invalidArgument);
		}
	}

	private class CreateSessionAction extends AbstractAction {

		private static final long serialVersionUID = 7000145813855380346L;
		private final StartupWindow win;

		public CreateSessionAction(StartupWindow win) {
			this.win = win;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				win.usrMgr.createSession(win.getUser(), win.getPasswd());
				app.setMainMode();
			} catch (final RemoteException e) {
				win.NoticeLabel.setText(e.toString());
				win.NoticeLabel.setForeground(Color.RED);
			} catch (final WrongLoginException e) {
				win.NoticeLabel.setText("Datos de usuario incorrectos.");
				win.NoticeLabel.setForeground(Color.RED);
			}
		}

	}

}
