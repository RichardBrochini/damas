package form;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

public class Login extends Form{
	private TextField login;
	private TextField senha;
	private Command autenticar;

	public Login(String nome){
		super("Login de Usuario");	
		this.login = new TextField("Login","", 32, TextField.ANY); 
        this.senha = new TextField("Senha","", 32, TextField.ANY); 
        this.append(this.login);
        this.append(this.senha);
        this.autenticar = new Command("Entrar",Command.OK,1);
        this.addCommand(this.autenticar);
	}
	
	public String getLogin(){
		return this.login.getString();
	}

	public String getSenha(){
		return this.senha.getString();
	}

}
