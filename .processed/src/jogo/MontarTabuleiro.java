package jogo;
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;


public class MontarTabuleiro extends TiledLayer {

	public MontarTabuleiro() throws IOException {
		super(8,8,Image.createImage(Configs.TABULEIRO),Configs.TAMANHO,Configs.TAMANHO);
		final int[] mapa ={
		1,2,1,2,1,2,1,2,
		2,1,2,1,2,1,2,1,	 
		1,2,1,2,1,2,1,2,
		2,1,2,1,2,1,2,1,		 
		1,2,1,2,1,2,1,2,
		2,1,2,1,2,1,2,1,		 
		1,2,1,2,1,2,1,2,
		2,1,2,1,2,1,2,1,};

		for (int i = 0; i < mapa.length; i++) {
			int coluna = i % 8;
			int linha = (i - coluna) / 8;
			this.setCell(coluna, linha, mapa[i]);
		}
		this.setPosition(Configs.TAMANHO,Configs.TAMANHO);
	}
}
