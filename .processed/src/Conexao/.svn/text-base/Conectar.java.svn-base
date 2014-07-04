package Conexao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Conectar {
	private SocketConnection sc;
	private OutputStream enviar;
	private InputStream receber;
	private KXmlParser parser;
	public Conectar(){
		try {
			this.sc = (SocketConnection) Connector.open("socket://200.204.145.243:2424");
			this.sc.setSocketOption(SocketConnection.LINGER, 5);
			this.receber  = sc.openInputStream();
			this.enviar   = sc.openOutputStream();
			this.parser   = new KXmlParser();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarMSG(String msg){
		try {
			msg=msg+"\0";
			this.enviar.write(msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.enviar.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String ler(){
		String msg  = new String();
		String temp = new String();
		byte[] buf = new byte[1];
        while(true){
            try {
            	this.receber.read(buf);
            } catch (IOException e) {
    			e.printStackTrace();
    	    }
            temp = new String(new String(buf).intern()); 		    	
            if(temp.equals("\0")){
	    		break;            		
        	}else{
        		msg=msg+temp;
        	}
        }
		return msg;
	}

	public KXmlParser lerXML(){
		try {
			this.parser.setInput(new InputStreamReader(this.receber));
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return this.parser;
	}
	
	public void fechar() throws IOException{
        this.receber.close();
		this.enviar.close();
		this.sc.close();		
	}
}
