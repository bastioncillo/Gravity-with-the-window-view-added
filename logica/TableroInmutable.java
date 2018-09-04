package pr3.logica;

public interface TableroInmutable {
	int getAlto();
	int getAncho();
	Ficha getFicha(int col, int fila);
	String dibujarTablero();
}
