
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

import jogo.Tabuleiro;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParserException;

import Conexao.Conectar;
import form.Login;
import form.Salas;
import menu.Menu;

public class Damas extends MIDlet implements CommandListener{
	private Login login;
	private Conectar conectar;
	private Menu menu;
	private Tabuleiro tab;

	public Damas(){
		this.login    = new Login("teste");
		this.conectar = new Conectar();
		this.menu = new Menu();
	}

	public void pauseApp(){
	}
	
	public void startApp(){
		this.login.setCommandListener(this);		
		this.menu.setFullScreenMode(true);
		//Display.getDisplay(this).setCurrent(this.menu);
		//Display.getDisplay(this).setCurrent(this.login);
		this.conectar.enviarMSG("<conectar login='teste2' senha='teste2' />");
		//System.out.println(this.conectar.ler());
		this.conectar.enviarMSG("<entrar sala='1'/>");
		this.tab = new Tabuleiro(this.conectar);
		Display.getDisplay(this).setCurrent(this.tab);	
		this.tab.start();
	}
	
	public void destroyApp(boolean unconditional){		
	}

	public void commandAction(Command c, Displayable arg1) {
		if(c.getLabel().equals("Entrar")){
			this.conectar.enviarMSG("<conectar login='"+this.login.getLogin()+"' senha='"+this.login.getSenha()+"' />");
			Salas s = new Salas(this.conectar.lerXML());
			this.conectar.enviarMSG("<entrar sala='1'/>");
			this.tab = new Tabuleiro(this.conectar);
			try{
				s.pegarSalas();
			}catch(XmlPullParserException e) {
				e.printStackTrace();
			}
			Display.getDisplay(this).setCurrent(this.tab);	
			//Display.getDisplay(this).setCurrent(s);
			//s.inciar();
		}
	}
}
