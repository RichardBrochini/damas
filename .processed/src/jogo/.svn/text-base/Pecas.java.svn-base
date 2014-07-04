package jogo;
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;


public class Pecas extends TiledLayer {

	private int[][] MATRIZ;
	private int[] mapa;
	private int oponente,player;
	
	
	public Pecas() throws IOException {
		super(8,8,Image.createImage(Configs.PECAS),Configs.TAMANHO,Configs.TAMANHO);
	}
	
	public void Vermelho(){
		mapa = new int[]
		{0,1,0,1,0,1,0,1,
		 1,0,1,0,1,0,1,0,
		 0,1,0,1,0,1,0,1,
		 0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,
		 2,0,2,0,2,0,2,0,
		 0,2,0,2,0,2,0,2,
		 2,0,2,0,2,0,2,0,};
		 this.gerarMapa();
		 this.setMatriz();
		 oponente = 2;
		 player=4;
	}
	
	public void Azul(){
		mapa = new int[]
		{0,2,0,2,0,2,0,2,
		 2,0,2,0,2,0,2,0,
		 0,2,0,2,0,2,0,2,
		 0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,
		 1,0,1,0,1,0,1,0,
		 0,1,0,1,0,1,0,1,
		 1,0,1,0,1,0,1,0,};
		 this.gerarMapa();
		 this.setMatriz();
		 oponente = 1;
		 player = 5;
	}
	
	public void setMatriz(){
		MATRIZ = new int[8][8];
		int k = 0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				MATRIZ[i][j]=k++;
			}
		}
	}
	
	public void marcaPosicao(int pos){
		pos=pos-1;
		if(pos>-1 && pos<64){
			if(this.mapa[pos]!=1 && this.mapa[pos]!=2 && this.mapa[pos]!=4 && this.mapa[pos]!=5){
				this.mapa[pos]=3;
				this.gerarMapa();									
			}
		}
	}
	
	public boolean verificar_Espaco(int pos){
		pos=pos-1;
		if(pos>-1 && pos<64){
			if(this.mapa[pos]== 3){
				return true;
			}
		}
		return false;
	}
	
	public void retirar_Peca(int pos){
		pos=pos-1;
		if(pos>-1 && pos<64){
			this.mapa[pos]=0;
			this.gerarMapa();
		}
	}

	public void zeraPosicao(int pos){
		pos=pos-1;
		if(pos>-1 && pos<64){
			if(this.mapa[pos]!=1 && this.mapa[pos]!=2 && this.mapa[pos]!=4 && this.mapa[pos]!=5){
				this.mapa[pos]=0;
				this.gerarMapa();
			}
		}
	}

	public boolean adversario_Existente(int pos){
		pos=pos-1;
		if(pos>-1 && pos<64){
			if(this.mapa[pos]==oponente){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public boolean Vazio(int pos){
		pos=pos-1;
		if(pos>-1 && pos<64){
			if(this.mapa[pos]==0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
	public void gerarMapa(){
		for (int i = 0; i < mapa.length; i++) {
			int coluna = i % 8;
			int linha = (i - coluna) / 8;
			this.setCell(coluna, linha, mapa[i]);
		}
		this.setPosition(Configs.TAMANHO,Configs.TAMANHO);
	}
		
	public int pegarPosicao(int x,int y){	
		if(x<1){
			x=1;
		}
		if(x>8){
			x=8;
		}
		if(y<1){
			y=1;
		}
		if(y>8){
			y=8;
		}
		return (MATRIZ[y-1][x-1]+1);
	}	
	public void trocarPosicao(int pos1,int pos2){
		pos1=pos1-1;
		pos2=pos2-1;
		/**/
		if(this.mapa[pos2]!=1 && this.mapa[pos2]!=2 && this.mapa[pos2]!=4 && this.mapa[pos2]!=5){
			this.mapa[pos2]=this.mapa[pos1];
			this.mapa[pos1]=0;
			this.gerarMapa();
		}
		
	}
	public void Dama(int x, int y){
		if(y == 8 && !(this.isDamas(this.pegarPosicao(x, y)))){
			this.mapa[this.pegarPosicao(x, y)-1] = player;
			System.out.println("Mudou\t"+this.pegarPosicao(x, y));
			this.gerarMapa();
		}
	}
	public  boolean isDamas(int pos){
		pos=pos-1;
		if(this.mapa[pos]==player){
			return true;
		}
		return false;
	}
}
