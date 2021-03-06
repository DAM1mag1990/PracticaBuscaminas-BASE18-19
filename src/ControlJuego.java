import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posición guarda el número -1
 * Si no hay una mina, se guarda cuántas minas hay alrededor.
 * Almacena la puntuación de la partida
 * @author ManuelAmorGonzalez
 *
 */
public class ControlJuego {
	
	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int [][] tablero;
	private int puntuacion;
	
	
	public ControlJuego() {
		//Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		
		//Inicializamos una nueva partida
		inicializarPartida();
	}
	
	
	/**Método para generar un nuevo tablero de partida:
	 
	 * @post: La estructura tablero debe existir. 
	 * Al final el tablero se habrá inicializado con tantas minas como marque la variable MINAS_INICIALES. 
	 * 			El resto de posiciones que no son minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida(){
		
		puntuacion=0;
		
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		
		//TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.
		int minasReparto=20;
		while (minasReparto>0) {
			Random rd= new Random();
			int x= rd.nextInt(10);
			int z = rd.nextInt(10);
			
			if (tablero[x][z] != MINA){
						tablero[x][z]=MINA;
						minasReparto--;
					
			}
			
		}
		System.out.println(minasReparto);
		
		
		
		//Al final del m�todo hay que guardar el n�mero de minas para las casillas que no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA){
					tablero[i][j] = calculoMinasAdjuntas(i,j);
					
				}
			}
		}
	}
	
	/**Cálculo de las minas adjuntas: 
	 *  @pre Para calcular el número de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * @pre  Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 *  @param Por lo tanto, como poco la i y la j valdrán 0.
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j){
		int numMinas=0;
		
			for (int k = i-1; k <= i+1; k++) {
			for (int k2 = j-1; k2 <= j+1; k2++) {
				
				if(k>=0&&k<LADO_TABLERO&&k2>=0&&k2<LADO_TABLERO) {
					
					if(tablero[k][k2]==-1) {
				numMinas++;	
				}
				
				}
				
			}
		}
		
		
return numMinas;
	}
	
	
public void sumaPunto() {
	puntuacion++;
}
	public int[][] getTablero() {
		return tablero;
	}


	public void setTablero(int[][] tablero) {
		this.tablero = tablero;
	}


	/**
	 * Método que nos permite 
	 *
	 * @post : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @post: tambien controlo que si la casilla es 0 no se le sume aqui la puntuacion ya que se hara desde otro m�todo.
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		boolean v=true;

		if(tablero[i][j]==-1) {
			v= false;
		}else {
			v=true;
			
			if(tablero[i][j]!=0) {
				puntuacion++;
			}
			
			if(puntuacion==80) {
				v=false;
			}
			
		}
		return v;
	}
	
	
	
	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		if(puntuacion==80) {
			return true;
		}
		return false;
		
	}
	
	
	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: "+puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * @post : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Método que devuelve la puntuación actual
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}
	
}
