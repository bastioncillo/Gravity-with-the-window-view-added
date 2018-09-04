package pr3.logica;

import java.awt.Color;

public enum Ficha {
	VACIA, BLANCA, NEGRA;
	
	public String toString() {
		String simbolo = " ";
		if (this == BLANCA) {
			simbolo = "O";
		}
		else if (this == NEGRA) {
			simbolo = "x";
		}
		return simbolo;
	}
	
	public Color getColor(){
		Color color = null;
		switch(this){
			case VACIA:{
				color = new Color(0, 200, 100);
				break;
			}
			case NEGRA:{
				color = new Color(0, 0, 0);
				break;
			}
			case BLANCA:{
				color = new Color(255, 255, 255);
				break;
			}
		}
		return color;
	}

}
