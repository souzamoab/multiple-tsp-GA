package mtsp;

import cidade.Cidade;
import heuristica.AlgoritmoGenetico;
import heuristica.Populacao;
import heuristica.Rota;

public class MTSP {

	public void solveMTSP(AlgoritmoGenetico ag, Cidade[] cidades) {
		
		//Inicializando população
		Populacao populacao = ag.initPopulacao(cidades.length);

		//Avaliando população
		ag.avaliarPopulacao(populacao, cidades);

		/*
		 * 
		 * Rota inicial deve ser dividida entre os caixeiros de acordo com o número pré-definido
		 * 
		 * rotaInicial: 1->2->3->4->5->6->7->8->9->10->11->12->13->14->15
		 * 
		 * 1) Atribuir 1 a primeira e última cidade da rota de cada caixeiro
		 * 2) Se 
		 * 		
		 *  
		 * 
		 */
		Rota rotaInicial = new Rota(populacao.getFittest(0), cidades);
		System.out.println("Distância inicial: " + rotaInicial.getDistancia());
		
		int geracao = 1;
		
		while (ag.isTerminationConditionMet(geracao, ag.getMaxGeracoes()) == false) {
			//Melhor individuo da população
			Rota rota = new Rota(populacao.getFittest(0), cidades);
			System.out.println("G" + geracao + " Melhor distância: " + rota.getDistancia());

			populacao = ag.crossoverPopulacao(populacao);

			populacao = ag.mutacaoPopulacao(populacao);

			ag.avaliarPopulacao(populacao, cidades);

			geracao++;
		}

		System.out.println("Parado após " + ag.getMaxGeracoes() + " gerações.");
		Rota rota = new Rota(populacao.getFittest(0), cidades);
		System.out.println("\nMelhor distância: " + rota.getDistancia());

		System.out.print("Rota : ");
		for (Cidade cidade : rota.getRota()) {
			System.out.print(cidade.getLabel() + " -> ");
		}
		
		System.out.println(rota.getCidade(0).getLabel());
	
	}
}
