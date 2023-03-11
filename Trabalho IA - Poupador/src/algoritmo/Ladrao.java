package algoritmo;


import java.awt.Point;

import controle.Constantes;

public class Ladrao extends ProgramaLadrao {
	public int[][] mapa = new int[30][30];
	public int rodadas = 0;
	
	public int esquerda = 2;
	public int direita = 2;
	public int cima = 0;
	public int baixo = 0;
	
	public int acao() {
		Point pos = sensor.getPosicao();
		mapa[pos.x][pos.y]++;
		rodadas++;
		int andarNoMenosRepetido = andarNoMenosRepetido();
		return andarNoMenosRepetido;
	}
	public int andarNoMenosRepetido() {
		Point pos = sensor.getPosicao();
		int menorValor = mapa[pos.x][pos.y];
		int aux = 0;
		
		//cima
		if(codigoDoPiso(1) == 0 && mapa[pos.x][pos.y - 1] < menorValor) {
			System.out.println("Cima ta vago e é menor q o menor");
			menorValor = mapa[pos.x][pos.y - 1];
			aux = 1;
		}
//		//baixo
		if(codigoDoPiso(2) == 0 && mapa[pos.x][pos.y + 1] < menorValor) {
			System.out.println("Baixo ta vago e é menor q o menor");
			menorValor = mapa[pos.x][pos.y + 1];
			aux = 2;
		}		
//		//direita
		if(codigoDoPiso(3) == 0 && mapa[pos.x + 1][pos.y] < menorValor) {
			System.out.println("Direita ta vago e é menor q o menor");
			menorValor = mapa[pos.x + 1][pos.y];
			aux = 3;
		}
//		//esquerda 
		if(codigoDoPiso(4) == 0 && mapa[pos.x - 1][pos.y] < menorValor) {
			System.out.println("Esquerda ta vago e é menor q o menor");
			menorValor = mapa[pos.x - 1][pos.y];
			aux = 4;
		}


		
		return aux;
	}
	public int codigoDoPiso(int direcao) {
		Point pos = sensor.getPosicao();
		int[] areaVisivel = sensor.getVisaoIdentificacao();
		int codigo = 0;
		if(direcao == 1) //cima
			codigo = areaVisivel[7];
		if(direcao == 2)//baixo
			codigo = areaVisivel[16];
		if(direcao == 3)//direita
			codigo = areaVisivel[12];
		if(direcao == 4)//esquerda
			codigo = areaVisivel[11];
		
		return codigo;
	}
	/*
	 * Printa na tela o mapa percorrido até dado momento pelo agente
	 * 
	 * */
	public void printarMapaIndividual() {
		String point = "";
		for(int i = 0; i< mapa.length;i++) {
			for(int j = 0; j< mapa.length;j++) {
				point+="["+mapa[i][j]+"] ";
			}
			System.out.println(point);
			point = "";
		}
	}
}
