package jogo;
import java.io.IOException;

import Conexao.Conectar;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Tabuleiro extends GameCanvas implements Runnable {

	private boolean executando;
	private boolean dm = false;
	private LayerManager layerManager;
	private MontarTabuleiro tab;
	private Borda         borda;
	private Peca          peca;
	private Pecas         pecas;
	private int pos1;
	private int c1,c2,c3,c4;
	private Conectar c;
	
	public Tabuleiro(Conectar con){
		super(true);
		this.c1=0;
		this.c2=0;
		this.c3 = -1;
		this.c4 = -1;
		this.pos1  = 0;
		this.tab   = null;
		this.borda = null;
		this.peca  = null;
		this.pecas = null;
		this.c = con;
		new Rules();		
		Rules.verificacao = true;
		try {
			this.tab   = new MontarTabuleiro();
			this.borda = new Borda();
			this.peca  = new Peca();
			this.pecas = new Pecas();
		} catch (IOException e) {
			e.printStackTrace();
		}
			//Rules.movimento = false;
		this.layerManager = new LayerManager();
		this.layerManager.append(this.peca);
		this.layerManager.append(this.pecas);
		this.layerManager.append(this.borda);
		this.layerManager.append(this.tab);		
		this.pecas.Vermelho();
	}

	public final void start() {
		this.executando = true;
		Thread t = new Thread(this);
		t.start();
	}

	public final void stop() {
		this.executando = false;
	}
	/*
	 * Metodo responsavel pelos movimentos do jogador
	 */
	private final void movimenta() {
		int keyStates = this.getKeyStates();
		int x,y;
		if(Rules.verificacao){
			this.Verificar_Tabuleiro();
			Rules.verificacao = false;
			
		}
		if((keyStates & FIRE_PRESSED) !=0){
			x = this.peca.getX()/Configs.TAMANHO;
			y = this.peca.getY()/Configs.TAMANHO;
			if(this.pecas.isDamas(this.pecas.pegarPosicao(x, y)) || dm){
				this.mov_Dama(x, y);
				return;
			}
		if(this.pos1==0){
			this.pos1 = this.pecas.pegarPosicao(x, y);
			this.peca.nextFrame();
			if(!Rules.capturar_peca){
				this.c1   = this.pecas.pegarPosicao((x-1), (y+1));
				this.c2   = this.pecas.pegarPosicao((x+1), (y+1));
				if((this.c1-this.c2)==2 || (this.c1-this.c2)==-2){
						this.pecas.marcaPosicao(this.c1);						
						this.pecas.marcaPosicao(this.c2);											
				}else{
					if(x==1){
						this.pecas.marcaPosicao(this.c2);
					}else{
						this.pecas.marcaPosicao(this.c1);																	
					}
				}
			}
			else{
				if(Rules.peca_Valida(pos1) && this.pecas.adversario_Existente(this.pecas.pegarPosicao(x-1, y+1))){
					if(this.pecas.Vazio(this.pecas.pegarPosicao(x-2, y+2))){
						this.c1 = this.pecas.pegarPosicao(x-2, y+2);
						this.pecas.marcaPosicao(this.pecas.pegarPosicao(x-2, y+2));
					}
				}
				if(Rules.peca_Valida(pos1) && this.pecas.adversario_Existente(this.pecas.pegarPosicao(x+1, y+1))){
					if(this.pecas.Vazio(this.pecas.pegarPosicao(x+2, y+2))){
						this.c2 = this.pecas.pegarPosicao(x+2, y+2);
						this.pecas.marcaPosicao(this.pecas.pegarPosicao(x+2, y+2));
					}
				}
			}
		}
		else{
			int pos2 = this.pecas.pegarPosicao(x, y);			
			if(!Rules.capturar_peca){	
				if(this.c1==pos2 || this.c2==pos2){
					this.layerManager.remove(this.pecas);
					this.pecas.zeraPosicao(this.c1);
					this.pecas.zeraPosicao(this.c2);
	                this.pecas.trocarPosicao(this.pos1,pos2);
					if(pos2 == c1){
						Rules.back[0][0] = x+1;
						Rules.back[0][1] = y-1;
						Rules.Mov(x+1, y-1, x, y);
						Rules.retirar_Coord(x+1, y-1);
					}
					else{
						Rules.back[0][0] = x-1;
						Rules.back[0][1] = y-1;
						Rules.Mov(x-1, y-1, x, y);
						Rules.retirar_Coord(x-1, y-1);
					}
					Rules.back[1][0]= x;
					Rules.back[1][1]= y;
					Rules.tabul[x][y] = 1;
					Rules.xpecas.addElement(String.valueOf(x));
					Rules.ypecas.addElement(String.valueOf(y));
					this.c1   = 0;
					this.c2   = 0;				
					this.pos1 = 0;
					this.layerManager.insert(this.pecas,0);
					this.pecas.Dama(x, y);
					this.c.enviarMSG("<mover x='"+(Rules.back[0][0]-1)+"' y='"+(Rules.back[0][1]-1)+"' xx='"+(Rules.back[1][0]-1)+"' yy='"+(Rules.back[1][1]-1)+"'/>");
					Rules.movimento = false;
					this.peca.nextFrame();
					
					}else{
						this.pecas.zeraPosicao(this.c1);
						this.pecas.zeraPosicao(this.c2);
					    this.c1 = 0;
						this.c2 = 0;
						this.pos1 = 0;
						this.peca.prevFrame();										
					}
			}
			else{
				if(this.pecas.verificar_Espaco(pos2)){
					Rules.capturar_peca = false;
					Rules.pecas_Validas.removeAllElements();
					if(pos2 == this.c1){
						this.pecas.retirar_Peca(this.pecas.pegarPosicao(x+1, y-1));
						Rules.retirar_Coord(x+2, y-2);
						Rules.back[0][0] = x+2;
						Rules.back[0][1] = y-2;
						Rules.capturar_Mais(x, y, this.pecas);	
					}
					else{
						this.pecas.retirar_Peca(this.pecas.pegarPosicao(x-1, y-1));
						Rules.retirar_Coord(x-2, y-2);
						Rules.back[0][0] = x-2;
						Rules.back[0][1] = y-2;
						Rules.capturar_Mais(x, y, this.pecas);
					}
					Rules.back[0][0] = x;
					Rules.back[0][1] = y;
					c.enviarMSG("<mover x='"+(Rules.back[0][0] - 1)+"+' y='"+(Rules.back[0][1] - 1)+"' xx='"+(Rules.back[1][0] - 1)+"' yy='"+(Rules.back[1][1] - 1)+"'/>");
					Rules.limparBack();
					Rules.xpecas.addElement(String.valueOf(x));
					Rules.ypecas.addElement(String.valueOf(y));
					this.pecas.zeraPosicao(c1);
					this.pecas.zeraPosicao(c2);
					Rules.movimento = false;
					this.pecas.trocarPosicao(this.pos1,pos2);
					this.pecas.Dama(x, y);
					this.c1 = 0;
					this.c2 = 0;
					this.pos1 = 0;
					this.peca.nextFrame();
				}
				else{
					this.pecas.zeraPosicao(this.c1);
					this.pecas.zeraPosicao(this.c2);
				    this.c1 = 0;
					this.c2 = 0;
					this.pos1 = 0;
					this.peca.prevFrame();
				}
			}
		}
	} else if ((keyStates & this.LEFT_PRESSED) != 0) {
		this.peca.setDirecao('e');
		this.peca.mover(Configs.TAMANHO);
	} else if ((keyStates & this.RIGHT_PRESSED) != 0) {
		this.peca.setDirecao('d');
		this.peca.mover(Configs.TAMANHO);
	} else if ((keyStates & this.UP_PRESSED) != 0) {
		this.peca.setDirecao('c');
		this.peca.mover(Configs.TAMANHO);
	} else if ((keyStates & this.DOWN_PRESSED) != 0) {
		this.peca.setDirecao('b');
		this.peca.mover(Configs.TAMANHO);
	}
	}
	private final void verificaColisaoParede() {
		if (this.peca.collidesWith(this.borda, false)) {
			this.peca.mover(-Configs.TAMANHO);
		}
	}
	/*
	 * Metodo responsável pela verificação do tabuleiro a cada turno.
	 * Verifica se há peças para capturar, e havendo, obriga o jogador realiza-la
	 */
	public void Verificar_Tabuleiro(){
		int x,y,i;
		for(i=0; i<Rules.xpecas.size(); i++){
			x = Integer.parseInt(Rules.xpecas.elementAt(i).toString());
			y = Integer.parseInt(Rules.ypecas.elementAt(i).toString());
			
			if(this.pecas.isDamas(this.pecas.pegarPosicao(x, y))){
				if(this.pecas.adversario_Existente(this.pecas.pegarPosicao(x-1, y-1)) && !((x-1) < 1 || (x-1) > 8 || (y-1) < 1 || (y-1) > 8)){
					
					if((this.pecas.Vazio(this.pecas.pegarPosicao(x-2, y-2)) && !((x-2) < 1 || (x-2) > 8 || (y-2) < 1 || (y-2) > 8)) && !Rules.peca_Valida(this.pecas.pegarPosicao(x, y))){
						
						Rules.pecas_Validas.addElement(String.valueOf(this.pecas.pegarPosicao(x, y)));
						Rules.capturar_peca = true;
						continue;
					}
				}
				if(this.pecas.adversario_Existente(this.pecas.pegarPosicao(x+1, y-1)) && !((x+1) < 1 || (x+1) > 8 || (y-1) < 1 || (y-1) > 8)){
					if((this.pecas.Vazio(this.pecas.pegarPosicao(x+2, y-2))&& !((x+2) < 1 || (x+2) > 8 || (y-2) < 1 || (y-2) > 8)) && !Rules.peca_Valida(this.pecas.pegarPosicao(x, y))){
						Rules.pecas_Validas.addElement(String.valueOf(this.pecas.pegarPosicao(x, y)));
						Rules.capturar_peca = true;
						continue;
					}
				}
			}
			
			if(this.pecas.adversario_Existente(this.pecas.pegarPosicao(x-1, y+1)) && !((x-1) < 1 || (x-1) > 8 || (y+1) < 1 || (y+1) > 8)){
				
				if((this.pecas.Vazio(this.pecas.pegarPosicao(x-2, y+2)) && !((x-2) < 1 || (x-2) > 8 || (y+2) < 1 || (y+2) > 8)) && !Rules.peca_Valida(this.pecas.pegarPosicao(x, y))){
					
					Rules.pecas_Validas.addElement(String.valueOf(this.pecas.pegarPosicao(x, y)));
					Rules.capturar_peca = true;
					continue;
				}
			}
			if(this.pecas.adversario_Existente(this.pecas.pegarPosicao(x+1, y+1)) && !((x+1) < 1 || (x+1) > 8 || (y+1) < 1 || (y+1) > 8)){
				if((this.pecas.Vazio(this.pecas.pegarPosicao(x+2, y+2))&& !((x+2) < 1 || (x+2) > 8 || (y+2) < 1 || (y+2) > 8)) && !Rules.peca_Valida(this.pecas.pegarPosicao(x, y))){
					Rules.pecas_Validas.addElement(String.valueOf(this.pecas.pegarPosicao(x, y)));
					Rules.capturar_peca = true;
				}
			}
			
		}
		Rules.verificacao = false;
		
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		Graphics g = this.getGraphics();
		while (this.executando){
			this.renderiza(g);
			if(Rules.movimento){
				this.movimenta();
				this.verificaColisaoParede();
			}
			else{
				this.atualiza();
				Rules.movimento = true;
				Rules.verificacao = true;
				this.peca.nextFrame();
			}
			g.setColor(0x00ff00);
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}		
	}
	
	private boolean primer(){
		KXmlParser xml = this.c.lerXML();
		int total = 0;
		try {
			xml.nextTag();			
			System.out.println("aki");		
			System.out.println(xml.getName());		
			System.out.println("foi");		
			while(!xml.getName().equals("entrada")){
				System.out.println("hahaha");		
				xml.nextTag();
			}
			
			System.out.println(xml.getName());
			System.out.println(xml.getAttributeCount());
			if(xml.getAttributeName(0).equals("total")){
				total = Integer.parseInt(xml.getAttributeValue(0));
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(total == 0){
			return true;			
		}else{
			return false;			
		}
	}

	private void atualiza(){
		KXmlParser xml = this.c.lerXML();
		int a,b,c,d;
		a = b = c = d = 0;
		try {
			xml.nextTag();
			while(!xml.getName().equals("andar")){
				xml.nextTag();
			}
			System.out.println(xml.getName());
			if(xml.getAttributeName(0).equals("y")){
				b = Integer.parseInt(xml.getAttributeValue(0));
			}
			if(xml.getAttributeName(1).equals("x")){
				a = Integer.parseInt(xml.getAttributeValue(1));
			}
			if(xml.getAttributeName(2).equals("xx")){
				 c = Integer.parseInt(xml.getAttributeValue(2));
			}
			if(xml.getAttributeName(3).equals("yy")){
				 d = Integer.parseInt(xml.getAttributeValue(3));
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pecas.trocarPosicao(this.pecas.pegarPosicao(a+1, b+1), this.pecas.pegarPosicao(c+1, d+1));
		//this.peca.nextFrame();
	}
	
	private final void renderiza(Graphics g) {
		this.layerManager.paint(g,0,0);
		flushGraphics();
	}
	
	private final void mov_Dama(int x, int y){
		if(this.pos1==0){
			this.pos1 = this.pecas.pegarPosicao(x, y);
			this.peca.nextFrame();
			if(!Rules.capturar_peca){
				this.c1   = this.pecas.pegarPosicao((x-1), (y+1));
				this.c2   = this.pecas.pegarPosicao((x+1), (y+1));
				this.c3   = this.pecas.pegarPosicao((x-1), (y-1));
				this.c4   = this.pecas.pegarPosicao((x+1), (y-1));
				
				if(((this.c1-this.c2)==2 || (this.c1-this.c2)==-2 || (this.c3-this.c4)==2 || (this.c3-this.c4)==-2) && !((x-1) < 1 || (x-1) > 8 || (y+1) < 1 || (y+1) > 8)){
						this.pecas.marcaPosicao(this.c1);						
						this.pecas.marcaPosicao(this.c2);
						this.pecas.marcaPosicao(this.c3);
						this.pecas.marcaPosicao(this.c4);
				}else{
					if(x==1){
						if(y<8)
							this.pecas.marcaPosicao(this.c2);
						this.pecas.marcaPosicao(this.c4);
					}else{
						if(y<8)
							this.pecas.marcaPosicao(this.c1);
						this.pecas.marcaPosicao(this.c3);
					}
				}
			}
			else{
				if(Rules.peca_Valida(pos1) && this.pecas.adversario_Existente(this.pecas.pegarPosicao(x-1, y+1))){
					if(this.pecas.Vazio(this.pecas.pegarPosicao(x-2, y+2)) && !((x-2) < 1 || (x-2) > 8 || (y+2) < 1 || (y+2) > 8)){
						this.c1 = this.pecas.pegarPosicao(x-2, y+2);
						this.pecas.marcaPosicao(this.pecas.pegarPosicao(x-2, y+2));
					}
				}
				if(Rules.peca_Valida(pos1) && this.pecas.adversario_Existente(this.pecas.pegarPosicao(x+1, y+1))){
					if(this.pecas.Vazio(this.pecas.pegarPosicao(x+2, y+2)) && !((x+2) < 1 || (x+2) > 8 || (y+2) < 1 || (y+2) > 8)){
						this.c2 = this.pecas.pegarPosicao(x+2, y+2);
						this.pecas.marcaPosicao(this.pecas.pegarPosicao(x+2, y+2));
					}
				}
				if(Rules.peca_Valida(pos1) && this.pecas.adversario_Existente(this.pecas.pegarPosicao(x-1, y-1))){
					if(this.pecas.Vazio(this.pecas.pegarPosicao(x-2, y-2)) && !((x-2) < 1 || (x-2) > 8 || (y-2) < 1 || (y-2) > 8)){
						this.c3 = this.pecas.pegarPosicao(x-2, y-2);
						this.pecas.marcaPosicao(this.pecas.pegarPosicao(x-2, y-2));
					}
				}
				if(Rules.peca_Valida(pos1) && this.pecas.adversario_Existente(this.pecas.pegarPosicao(x+1, y-1))){
					if(this.pecas.Vazio(this.pecas.pegarPosicao(x+2, y-2)) && !((x+2) < 1 || (x+2) > 8 || (y-2) < 1 || (y-2) > 8)){
						this.c4 = this.pecas.pegarPosicao(x+2, y-2);
						this.pecas.marcaPosicao(this.pecas.pegarPosicao(x+2, y-2));
					}
				}
			}
			dm = true;
		}
		else{
			int pos2 = this.pecas.pegarPosicao(x, y);
			if(!Rules.capturar_peca){	
				if(this.c1==pos2 || this.c2==pos2 || this.c3==pos2 || this.c4==pos2){
					this.layerManager.remove(this.pecas);
					this.pecas.zeraPosicao(this.c1);
					this.pecas.zeraPosicao(this.c2);
					this.pecas.zeraPosicao(this.c3);
					this.pecas.zeraPosicao(this.c4);
	                this.pecas.trocarPosicao(this.pos1,pos2);
					if(pos2 == c1){
						Rules.retirar_Coord(x+1, y-1);
					}
					else{
						if(pos2 == c2){
							Rules.retirar_Coord(x-1, y-1);
						}
						else{
							if(pos2 == c3){
								Rules.retirar_Coord(x+1, y+1);
							}
							else{
								Rules.retirar_Coord(x-1, y+1);
							}
						}
					}
					Rules.xpecas.addElement(String.valueOf(x));
					Rules.ypecas.addElement(String.valueOf(y));
					this.c1   = 0;
					this.c2   = 0;	
					this.c3	  = 0;
					this.c4   = 0;
					this.pos1 = 0;
					this.layerManager.insert(this.pecas,0);
					Rules.verificacao = true;
					this.peca.prevFrame();
					
					}else{
						this.pecas.zeraPosicao(this.c1);
						this.pecas.zeraPosicao(this.c2);
						this.pecas.zeraPosicao(this.c3);
						this.pecas.zeraPosicao(this.c4);
					    this.c1 = 0;
						this.c2 = 0;
						this.c3 = 0;
						this.c4 = 0;
						this.pos1 = 0;
						this.peca.prevFrame();										
					}
			}
			else{
				if(this.pecas.verificar_Espaco(pos2)){
					Rules.capturar_peca = false;
					Rules.pecas_Validas.removeAllElements();
					if(pos2 == this.c1){
						this.pecas.retirar_Peca(this.pecas.pegarPosicao(x+1, y-1));
						Rules.retirar_Coord(x+2, y-2);
						Rules.capturar_Mais(x, y, this.pecas);
					}
					else{
						if(pos2 == this.c2){
							this.pecas.retirar_Peca(this.pecas.pegarPosicao(x-1, y-1));
							Rules.retirar_Coord(x-2, y-2);
							Rules.capturar_Mais(x, y, this.pecas);
						}
						else{
							if(pos2 == this.c3){
								this.pecas.retirar_Peca(this.pecas.pegarPosicao(x+1, y+1));
								Rules.retirar_Coord(x-2, y+2);
								Rules.capturar_Mais(x, y, this.pecas);
							}
							else{
								this.pecas.retirar_Peca(this.pecas.pegarPosicao(x-1, y+1));
								Rules.retirar_Coord(x+2, y+2);
								Rules.capturar_Mais(x, y, this.pecas);
							}
						}
					}
					Rules.xpecas.addElement(String.valueOf(x));
					Rules.ypecas.addElement(String.valueOf(y));
					this.pecas.zeraPosicao(c1);
					this.pecas.zeraPosicao(c2);
					Rules.verificacao = true;
					this.pecas.trocarPosicao(this.pos1,pos2);
					this.pecas.Dama(x, y);
					this.c1 = 0;
					this.c2 = 0;
					this.pos1 = 0;
					this.peca.prevFrame();
				}
				else{
					this.pecas.zeraPosicao(this.c1);
					this.pecas.zeraPosicao(this.c2);
				    this.c1 = 0;
					this.c2 = 0;
					this.pos1 = 0;
					this.peca.prevFrame();
				}
			}
			dm = false;
		}
	}
}
