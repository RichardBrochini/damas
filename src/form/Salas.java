package form;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Salas extends GameCanvas implements Runnable{
	private KXmlParser xml;
	private int total;
	private boolean executar;
	
	public Salas(KXmlParser xml){
		super(true);
		this.xml   = xml;
		this.total = 0;
		this.executar = true;
	}

	public void entrarSala(){
		
	}

	public void criarSala(String nome){
		
	}

	public void inciar(){
		Thread t = new Thread(this);
		t.start();
	}

	public void parar(){
		this.executar= false;
	}

	public void pegarSalas() throws XmlPullParserException{
		try {
			this.xml.nextTag();
			this.xml.require(XmlPullParser.START_TAG, null, "salas");
			if(this.xml.getAttributeName(0).equals("total")){
				this.total = Integer.parseInt(this.xml.getAttributeValue(0));
			}
			if(this.total!=0){
				while (this.xml.nextTag () != XmlPullParser.END_TAG){
					this.xml.require(XmlPullParser.START_TAG, null, "sala");
					this.xml.require(XmlPullParser.END_TAG, null, "sala");
				}	
			}else{
				while (this.xml.nextTag () != XmlPullParser.END_TAG);			
			}
			this.xml.require(XmlPullParser.END_TAG, null, "salas");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		Graphics g = this.getGraphics();
		while(this.executar){
			g.drawString("Escolha uma Sala:",0,0,0);
			g.drawString("Criar Sala:",0,20,0);			
			this.paint(g);
			this.flushGraphics();
		}
	}
}