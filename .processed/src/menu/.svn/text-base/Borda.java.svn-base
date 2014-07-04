package menu;

import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;


public class Borda extends TiledLayer {

	public Borda() throws IOException {
		super(10, 10, Image.createImage("/borda.png"),20,20);
		int[] mapa ={
				4, 6, 6, 6, 6, 6, 6, 6, 6, 8,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 2,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 2,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 2,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 2,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 2,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 2,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 2,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 2,
				7, 6, 6, 6, 6, 6, 6, 6, 6, 5,};

		for (int i = 0; i < mapa.length; i++) {
			int coluna = i % 10;
			int linha = (i - coluna) / 10;
			this.setCell(coluna, linha, mapa[i]);
		}
	}
}
