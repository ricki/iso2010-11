

	package com.umbrella.worldconq.ui;
	import java.awt.BorderLayout;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;

	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JFrame;

	import javax.swing.JToolBar;
	import javax.swing.WindowConstants;





	public class MainWindow extends JFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5107198177153703399L;

		
		public MainWindow() {
			super();
			initGUI();
		}

		private void initGUI() {
			try {
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				
				try {
					this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/logo.png")).getImage());
				} catch (Exception e) {
					System.out.println("Imagen no encontrada");
				}
				this.setResizable(false);
				this.setTitle("La Conquista del Mundo");
				this.setSize(800, 500);

				
				JToolBar bar = new JToolBar();
				

				JButton createGameButton = new JButton("Crear partida");
				bar.add(createGameButton);
				getContentPane().add(bar, BorderLayout.NORTH);
				createGameButton.addMouseListener(new CreateGameMouseAdapter(this));
				
		
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		


		

		
		

		
		private class CreateGameMouseAdapter extends MouseAdapter {
			
			private MainWindow mw;

			public CreateGameMouseAdapter(MainWindow mw) {
				this.mw = mw;
			}

			public void mouseClicked(MouseEvent evt) {
				

					JFrame f = new JFrame();
					CreateGameDialog dlg = new CreateGameDialog(f, "La Conquista del Mundo - Nueva partida", true);
					dlg.setLocationRelativeTo(null);
					dlg.setVisible(true);
			
					if (dlg.getSelection() == true) {
						try{
///*							mw.Manager.registerUser(dlg.getUser(), dlg.getPasswd(), dlg.getEmail());
//							mw.NoticeLabel.setText("Usuario :" + dlg.getUser()+" registrado");
//							NoticeLabel.setForeground(new Color (0, 200, 0));*/
						}
						catch(Exception e) {
//							stw.NoticeLabel.setText(" Error en el registro");
//							NoticeLabel.setForeground(new Color (255, 0, 0));
						}	
					}
					
					mw.setVisible(true);
					f.dispose();
			
				
			}
		}
		
	
	}
	
	
	

