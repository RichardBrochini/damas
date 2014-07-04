package jogo;
import java.util.*;

public class Rules {

	static boolean movimento = true;
	static boolean capturar_peca;
	static boolean verificacao;
	static Vector pecas_Validas = new  Vector();
	static Vector xpecas = new Vector();
	static Vector ypecas = new Vector();
	static int [][]tabul = new int[8][8];
	static int [][] back = new int[2][2]; 
	
	
	static public void  limparBack(){
		for(int i=0; i<2; i++){
			for(int j=0; j<2; j++)
				back[i][j]=0;
		}
	}
	
	public Rules(){
		int []y={1,1,1,1,2,2,2,2,3,3,3,3};
		int []x={2,4,6,8,1,3,5,7,2,4,6,8};
		int var=0;
		Rules.limparBack();
		
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				
				//System.out.println("y[i] = "+(y[i])+"\tx[i] = "+(x[j]));
				if(var != 12){
					if(((i+1) == y[var]) && ((j+1) == x[var])){
						Rules.tabul[i][j] = 1;
						Rules.ypecas.addElement(String.valueOf(y[var]));
						Rules.xpecas.addElement(String.valueOf(x[var]));
						var++;
						continue;
					}
				}
				Rules.tabul[i][j] = 0;
			}
		}
		y = null;
		x =null;
	}
	
	static public void capturar_Mais(int x, int y, Pecas aux){
		int c1 = aux.pegarPosicao(x-1, y+1);
		int c2 = aux.pegarPosicao(x+1, y+1);
		if(aux.adversario_Existente(c1)){
			if(aux.Vazio(aux.pegarPosicao(x-2, y+2)) && !((x-2) < 1 || (x-2) > 8 || (y+2) < 1 || (y+2) > 8)){
				Rules.pecas_Validas.addElement(String.valueOf(aux.pegarPosicao(x, y)));
				Rules.capturar_peca = true;
				return;
			}
		}
		if(aux.adversario_Existente(c2)){
			if(aux.Vazio(aux.pegarPosicao(x+2, y+2)) && !((x+2) < 1 || (x+2) > 8 || (y+2) < 1 || (y+2) > 8)){
				Rules.pecas_Validas.addElement(String.valueOf(aux.pegarPosicao(x, y)));
				Rules.capturar_peca = true;
			}
		}
	}
	static public boolean peca_Valida(int pos){
		for(int i = 0; i< pecas_Validas.size(); i++){
			if((Integer.parseInt(pecas_Validas.elementAt(i).toString())) == pos){
				return true;
			}
		}
		return false;
	}
	static public void retirar_Coord(int x, int y){
		int x1,y1;
		for(int i=0; i<Rules.xpecas.size(); i++){
			x1 = Integer.parseInt(Rules.xpecas.elementAt(i).toString());
			y1 = Integer.parseInt(Rules.ypecas.elementAt(i).toString());
			
			if((x == x1) && (y == y1)){
				Rules.xpecas.removeElementAt(i);
				Rules.ypecas.removeElementAt(i);
				break;
			}
		}
	}
	
	static public void Mov(int x, int y, int x1, int y1){
		Rules.tabul[x-1][y-1] = 0;
		Rules.tabul[x1][y1] = 1;
	}
}
