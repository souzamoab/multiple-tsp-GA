package main;

import java.io.IOException;
import java.util.Scanner;

import arquivo.Arquivo;
import cidade.Cidade;
import heuristica.AlgoritmoGenetico;
import mtsp.MTSP;

public class Main {
	
	public static int TAM_POPULACAO = 500;
	public static int TAM_ELITISMO = 2;
	public static int TAM_TORNEIO = 5;
	public static double TAXA_MUTACAO = 0.001;
	public static double TAXA_CROSSOVER = 0.9;
	public static int MAX_GERACOES = 1000;
	
	public static void main(String[] args) {
		System.out.println("-----Multiple Traveling Salesman Problem-----");
		
		try {
			Arquivo arquivo = new Arquivo();
			AlgoritmoGenetico ag = new AlgoritmoGenetico(TAM_POPULACAO, TAXA_MUTACAO, TAXA_CROSSOVER, TAM_ELITISMO, TAM_TORNEIO, MAX_GERACOES);
			MTSP mtsp = new MTSP();
			Cidade[] cidades;
			Scanner in = new Scanner(System.in);
			
			System.out.println("1) 30 cidades\n2) 100 cidades");
			int op = in.nextInt();
			in.close();
			
			switch(op) {
			
			case 1:
				cidades = arquivo.carregarCidades("resources/ncit30.dat");
				mtsp.solveMTSP(ag, cidades);
				break;
			
			case 2:
				cidades = arquivo.carregarCidades("resources/ncit100.dat");
				mtsp.solveMTSP(ag, cidades);
				break;
				
			default:
				System.out.println("Opção inválida!!!");
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
