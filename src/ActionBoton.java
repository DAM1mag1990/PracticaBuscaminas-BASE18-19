import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendr√° que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author jesusredondogarcia
 **
 */
public class ActionBoton implements ActionListener{
	VentanaPrincipal ventana;
	int i;
	int j;

	public ActionBoton(VentanaPrincipal ven, int i,int j) {
	this.ventana=ven;
	this.i=i;
	this.j=j;
	}
	
	/**
	 *Si se puede abrir la casillamostrara el numerod eminas que hay alrededor, en caso de que sea 0 abrir· las casilla que estan en contacto directo con ella sabiendo que ninguna va a  ser bomba.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int resp;
		
		if(ventana.juego.abrirCasilla(i, j)) {
			ventana.mostrarNumMinasAlrededor(i, j);
			if(ventana.juego.getTablero()[i][j]==0) {
				ventana.abrirCeros(i, j);
			}
			
			ventana.actualizarPuntuacion();
			ventana.refrescarPantalla();
		}else {
			if(ventana.juego.esFinJuego()) {
				ventana.mostrarFinJuego(false);	
			}else {
				ventana.mostrarFinJuego(true);

			}
			
	}
	}

}
