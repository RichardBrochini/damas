package menu;

import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;

import form.Login;

import jogo.Tabuleiro;

public class Menu extends Canvas {
	private Jogar jogar;
	private About about;
	private LayerManager layerManager;	
	private int pos;
	private String opcao;

	public Menu(){
		try {
			this.jogar = new Jogar();
			this.about = new About();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.layerManager = new LayerManager();
		this.layerManager.append(this.jogar);
		this.layerManager.append(this.about);
		this.jogar.setFrame(0);
		this.about.setFrame(1);
		this.pos=2;	
		this.opcao = "JOGAR";
	}

	protected void keyPressed(int keyCode){
		switch (getGameAction(keyCode)) {
			case Canvas.DOWN:
			case Canvas.RIGHT:
			case Canvas.LEFT:
			case Canvas.UP:
				if(this.pos==1){
					this.jogar.setFrame(0);
					this.about.setFrame(1);
					this.pos=2;	
					this.opcao = "JOGAR";
				}else{
					this.jogar.setFrame(1);
					this.about.setFrame(0);
					this.pos=1;	
					this.opcao = "GRUPO";
				}
			break;
			case Canvas.FIRE:
				if(this.pos==2){
					this.notifyAll();
				}else if(this.pos==1){
				}
			break;			
		}
		this.repaint();
	}
	
	protected void paint(Graphics g) {
		try {
			g.setColor(0xffffff);
			g.fillRect(0, 0, 300, 500);
			g.setColor(0x000000);
			g.drawImage(Image.createImage("/fundmenu.jpg"),0,30,0);
			this.layerManager.paint(g,0,0);
			g.drawString(this.opcao,70,30,0);
		}catch (IOException e){
			e.printStackTrace();
		}
	}	
}