package algoritmo;


import java.awt.Point;

import controle.Constantes;

public class Ladrao extends ProgramaLadrao {
	public int[][] mapa = new int[30][30];
	public int rodadas = 0;
	public boolean parado;
	public int pacienciaMinima = 700;
	public int pacienciaMaxima = 1000;
	public int paciencia = pacienciaMaxima;
	public int valorPaciencia = 10;
	public int acao() {
		Point pos = sensor.getPosicao();
		mapa[pos.x][pos.y]++;
		rodadas++;
		paciencia--;
		//Funcoes de premissas
		loboBeta();
		return andarilio();		
		
		
	}
	
	public int andarilio() {
		int andarNoMenosRepetido = andarNoMenosRepetido();
		int aproximarDoPoupador = aproximarDoPoupador();
		int aproximarDoPoupadorPeloCheiro = aproximarDoPoupadorPeloCheiro();

		//Prioridade 1
		if(roubar() != -1) {
			System.out.println("Opa roubei kkkk");
			return roubar();
		}
		if(aproximarDoPoupadorPeloCheiro != -1) {
			if(loboSolitario() != -1) {
				return loboSolitario();
			}
			return aproximarDoPoupadorPeloCheiro;
		}
		//Prioridade 2
		if(aproximarDoPoupador != -1) {
			if(loboSolitario() != -1) {
				return loboSolitario();
			}
			return aproximarDoPoupador;
		}
		//Prioridade 3
//		if(camperarBanco() != -1) {
//			return camperarBanco();
//		}
		//Prioridade 4
		return andarNoMenosRepetido;
		
	}
	
	
	public int aproximarDoPoupadorPeloCheiro() {
		int posPoupadorCheiroso = procurarVisaoPoupadorCheiroRecente();
//		System.out.println(posPoupadorCheiroso);
		int cima = 1;
		int baixo = 6;
		int dir= 4;
		int esque = 3;
		int tentativa = -1;
		int moveUp = 1;
		int moveDown = 2;
		int moveRight = 3;
		int moveLeft = 4;
		//subir
		if(posPoupadorCheiroso == cima) {
			tentativa = moveUp;
		}
		//descer
		if(posPoupadorCheiroso == baixo) {
			tentativa = moveDown;
		}
		//direita
		if(posPoupadorCheiroso == dir) {
			tentativa =  moveRight;
		}
		//esquerda
		if(posPoupadorCheiroso == esque) {
			tentativa = moveLeft;
		}
		//Tentar subir
		if(posPoupadorCheiroso == 0 || posPoupadorCheiroso == 2 && codigoDoPiso(1) == 0) {
			tentativa = moveUp;
		}
		//Tentar descer
		if(posPoupadorCheiroso == 5 || posPoupadorCheiroso == 7 && codigoDoPiso(2) == 0) {
			tentativa = moveDown;
		}
		
		if(tentativa != -1) {
//			System.out.println("Vou priorizar caçar o player indo por: "+tentativa);
			return tentativa;
		}
		return -1;
	}
	public int aproximarDoPoupador() {
		int posPoupador = procurarVisaoPoupador();
		int tentativa = -1;
		if(posPoupador != -1) {
			//subir
			if(posPoupador < 10 && codigoDoPiso(1) == 0)
				tentativa = 1;
			//descer
			if(posPoupador > 13 && codigoDoPiso(2) == 0)
				tentativa = 2;
			//direita
			if(posPoupador == 12 || posPoupador == 13 && codigoDoPiso(3) == 0)
				tentativa =  3;
			//esquerda
			if(posPoupador == 10 || posPoupador == 11 && codigoDoPiso(4) == 0)
				tentativa = 4;
	
		}

	
		if(tentativa != -1) {
//			System.out.println("Vou priorizar caçar o player indo por: "+tentativa);
			return tentativa;
		}
		return -1;
	}
	
	public int loboSolitario() {
		int posLadraoComPoupador = procurarVisaoLadraoComPoupador();
		int andarNoMenosRepetido = andarNoMenosRepetido();
		if(posLadraoComPoupador != -1 && paciencia <= pacienciaMinima) {
			//Se sente um lobo ladrão solitario e vai embora
			return andarNoMenosRepetido;
		}
		return -1;
	}
	//Procurar um jeito de se juntar
	public void loboBeta() {
		int posLadraoPoupador = procurarVisaoLadraoComPoupador();
		if(posLadraoPoupador == -1 && paciencia < pacienciaMinima) {
			System.out.println("Agora estou sozinho e recuperando energias...");
			paciencia+=valorPaciencia;
		}
		if(paciencia >= pacienciaMaxima) {
			System.out.println("Energias Recaregadas! Virei chad");
		}
	}
	

	public int procurarVisaoLadrao() {
		int[] visor = sensor.getVisaoIdentificacao();
		for(int i = 0; i < visor.length;i++)
			if(visor[i] == Constantes.numeroLadrao01 || visor[i] == Constantes.numeroLadrao02 || visor[i] == Constantes.numeroLadrao03 || visor[i] == Constantes.numeroLadrao04)
				return i;
		return -1;
	}
	
	public int procurarVisaoLadraoComPoupador() {
		boolean temPop = false;
		int[] visor = sensor.getVisaoIdentificacao();
		for(int i = 0; i < visor.length;i++) {
			if(visor[i] == Constantes.numeroPoupador01 || visor[i] == Constantes.numeroPoupador02)
				temPop = true;
			if(visor[i] == Constantes.numeroLadrao01 || visor[i] == Constantes.numeroLadrao02 || visor[i] == Constantes.numeroLadrao03 || visor[i] == Constantes.numeroLadrao04 && temPop)
				return i;
			}
		return -1;
	}
	
	public int procurarVisaoPoupador() {
		int[] visor = sensor.getVisaoIdentificacao();
		for(int i = 0; i < visor.length;i++)
			if(visor[i] == Constantes.numeroPoupador01 || visor[i] == Constantes.numeroPoupador02)
				return i;
		return -1;
	}
	
	
	public int procurarVisaoPoupadorCheiroRecente() {
		int[] cheiros = sensor.getAmbienteOlfatoPoupador();
		for(int i = 0; i < cheiros.length; i++) {
			if(cheiros[i] > 0) {
				return i;
			}
		}
		return -1;
	}
	
	
	public int andarNoMenosRepetido() {
		Point pos = sensor.getPosicao();
		int menorValor = mapa[pos.x][pos.y];
		int aux = 0;
		
		//cima
		if(codigoDoPiso(1) == 0 && mapa[pos.x][pos.y - 1] < menorValor) {
//			System.out.println("Cima ta vago e é menor q o menor");
			menorValor = mapa[pos.x][pos.y - 1];
			aux = 1;
		}
//		//baixo
		if(codigoDoPiso(2) == 0 && mapa[pos.x][pos.y + 1] < menorValor) {
//			System.out.println("Baixo ta vago e é menor q o menor");
			menorValor = mapa[pos.x][pos.y + 1];
			aux = 2;
		}		
//		//direita
		if(codigoDoPiso(3) == 0 && mapa[pos.x + 1][pos.y] < menorValor) {
//			System.out.println("Direita ta vago e é menor q o menor");
			menorValor = mapa[pos.x + 1][pos.y];
			aux = 3;
		}
//		//esquerda 
		if(codigoDoPiso(4) == 0 && mapa[pos.x - 1][pos.y] < menorValor) {
//			System.out.println("Esquerda ta vago e é menor q o menor");
			menorValor = mapa[pos.x - 1][pos.y];
			aux = 4;
		}
	
		return aux;
	}
	public int roubar() {
		int tentativa = -1;
		//cima
		if(codigoDoPiso(1) == Constantes.numeroPoupador01 || codigoDoPiso(1) == Constantes.numeroPoupador02) {
			return 1;
		}
		//baixo
		if(codigoDoPiso(2) == Constantes.numeroPoupador01 || codigoDoPiso(2) == Constantes.numeroPoupador02) {
			return 2;
		}
		//direita
		if(codigoDoPiso(3) == Constantes.numeroPoupador01 || codigoDoPiso(3) == Constantes.numeroPoupador02) {
			return 3;
		}
		//esquerda
		if(codigoDoPiso(4) == Constantes.numeroPoupador01 || codigoDoPiso(4) == Constantes.numeroPoupador02) {
			return 4;
		}
		return tentativa;
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
//			System.out.println(point);
			point = "";
		}
	}
}
