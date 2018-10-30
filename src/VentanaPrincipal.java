import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class VentanaPrincipal {

	//La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	//Todos los botones se meten en un panel independiente.
	//Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel [][] panelesJuego;
	JButton [][] botonesJuego;
	
	//Correspondencia de colores para las minas:
	Color correspondenciaColores [] = {Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};
	
	JButton botonEmpezar;
	JTextField pantallaPuntuacion;
	
	
	//LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;
	
	
	//Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}
	
	//Inicializa todos los componentes del frame
	public void inicializarComponentes(){
		
		//Definimos el layout:
		ventana.setLayout(new GridBagLayout());
		
		//Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1,1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1,1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10,10));
		
		
		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));
		
			
		//Colocamos los componentes:
		//AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		//VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		//AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		//ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);
		
		//Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1,1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}
		
		//Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}
		
		//BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);
		
	}
	
	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el programa
	 */
	public void inicializarListeners(){
		
	
		botonEmpezar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				for (int i = 0; i < panelesJuego.length; i++) {
					for (int j = 0; j < panelesJuego[i].length; j++) {
						panelesJuego[i][j].removeAll();
					}
				}
				for (int i = 0; i < botonesJuego.length; i++) {
					for (int j = 0; j < botonesJuego[i].length; j++) {
						botonesJuego[i][j] = new JButton("-");
						panelesJuego[i][j].add(botonesJuego[i][j]);
					}
				}
				
				juego.inicializarPartida();
				actualizarPuntuacion();
				refrescarPantalla();
				juego.depurarTablero();
				inicializarListeners();
			
			}
		});
		
			for (int i = 0; i < botonesJuego.length; i++) {
			
			int z=i;
			for (int j = 0; j < botonesJuego[i].length; j++) {
			int p=j;
			botonesJuego[i][j].addActionListener(new ActionBoton(VentanaPrincipal.this,z,p));
			}
		}
		
	
		
	}
	
	
	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda
	 * Saca el botón que haya en la celda determinada y añade un JLabel centrado y no editable con el número de minas alrededor.
	 * Se pinta el color del texto según la siguiente correspondecia (consultar la variable correspondeciaColor)	 * - 0 : negro
	 * - 1 : cyan
	 * - 2 : verde
	 * - 3 : naranja
	 * - 4 ó más : rojo 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i , int j) {
		panelesJuego[i][j].removeAll();
		//String num=;
	
		
	JLabel tex=new JLabel();
		switch (String.valueOf(juego.getMinasAlrededor(i, j))) {
		
		case "1":
	System.out.println(juego.getMinasAlrededor(i, j));
			tex.setHorizontalAlignment(SwingConstants.CENTER);
			tex.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
			tex.setForeground(Color.CYAN);
			panelesJuego[i][j].add(tex);
			
			break;
		case "2":
			
			
			tex.setHorizontalAlignment(SwingConstants.CENTER);
			tex.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
			tex.setForeground(Color.GREEN);
			
			panelesJuego[i][j].add(tex);	
			break;
		case "3":
			tex.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
			tex.setHorizontalAlignment(SwingConstants.CENTER);
			tex.setForeground(Color.ORANGE);
			panelesJuego[i][j].add(tex);			
			break;
		case "4":
			tex.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
			tex.setHorizontalAlignment(SwingConstants.CENTER);
			tex.setForeground(Color.RED);
			panelesJuego[i][j].add(tex);			
			break;


		default:
			System.out.println(juego.getMinasAlrededor(i, j)+" PENE");
			break;
		}
		
		}
	public void abrirCeros(int i, int j) {
		int [][]tabl= juego.getTablero();
		
			for (int k = i-1; k <= i+1; k++) {
				for (int k2 = j-1; k2 <= j+1; k2++) {
					
					if(k>=0&&k<10&&k2>=0&&k2<10) {
						
						if(tabl[k][k2]==0) {
							
								
//mi intencion era hacer este metodo recursivo de tal forma que si al casilla vale 0, vuelva a comprobar si hay 0a su alrededor y los siga abriendo,peor me da error.
							
							juego.sumaPunto();
							panelesJuego[k][k2].removeAll();
							JLabel tex=new JLabel("");
							//tex.setHorizontalAlignment(SwingConstants.CENTER);
							panelesJuego[k][k2].add(tex);
							
					}
					
					}
					
				}
			}
			
		
		
	}
	
	
	/**
	 * Muestra una ventana que indica el fin del juego
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha explotado una mina (true) o bien porque hemos desactivado todas (false) 
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		String mensaje;
		for (int i = 0; i < panelesJuego.length; i++) {
		for (int j = 0; j < panelesJuego[i].length; j++) {
	botonesJuego[i][j].setEnabled(false);
		}	
		}
		if(porExplosion) {
			mensaje=" Puntuacion: "+String.valueOf(getJuego().getPuntuacion())+" \n  �Quieres jugar de nuevo?";

				JOptionPane.showMessageDialog(null, mensaje,"BoooM te peto amiJO!!!", JOptionPane.INFORMATION_MESSAGE);
				
				

		}else {
			mensaje=" Puntuacion: "+String.valueOf(getJuego().getPuntuacion())+" \n  �Quieres jugar de nuevo?";

			JOptionPane.showMessageDialog(null, mensaje,"Felicidades bribon!", JOptionPane.CANCEL_OPTION);
			
		
			
			

		}
		//TODO
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(String.valueOf(juego.getPuntuacion()));
	}
	
	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla(){
		ventana.revalidate(); 
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar(){
		//IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();	
		juego.depurarTablero();
		inicializarListeners();	

	}



}
