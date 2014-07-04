package jogo;
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


public class Peca extends Sprite {
	private char direcao;
	public Peca() throws IOException {
		super(Image.createImage(Configs.PECA),Configs.TAMANHO,Configs.TAMANHO);
		this.setPosition(Configs.TAMANHO,Configs.TAMANHO);
	}

	public final void mover(int x,int y) {
		x=x-30;
		y=y-15;
		this.setPosition(x,y);
	}

	public final void mover(int passos) {
		switch (this.direcao){
			case 'c' :
				this.move(0,-passos);
				break;
			case 'b' :
				this.move(0,passos);
				break;
			case 'e' :
				this.move(-passos,0);
				break;
			case 'd' :
				this.move(passos,0);
				break;
		}
	}

	public final void setDirecao(char c) {
		if(c!=this.direcao) {
			this.direcao = c;
		}
	}
}
