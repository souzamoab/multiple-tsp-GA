package heuristica;

import java.util.Arrays;

import cidade.Cidade;

public class AlgoritmoGenetico {

	private int tamanhoPopulacao;
	private double taxaMutacao;
	private double taxaCrossover;
	private int elitismoCont;
	private int tamanhoTorneio;
	private int maxGeracoes;

	public AlgoritmoGenetico(int tamanhoPopulacao, double taxaMutacao, double taxaCrossover, int elitismoCont,
			int tamanhoTorneio, int maxGeracoes) {

		this.tamanhoPopulacao = tamanhoPopulacao;
		this.taxaMutacao = taxaMutacao;
		this.taxaCrossover = taxaCrossover;
		this.elitismoCont = elitismoCont;
		this.tamanhoTorneio = tamanhoTorneio;
		this.maxGeracoes = maxGeracoes;
	}

	public Populacao initPopulacao(int tamanhoCromossomos) {
		Populacao populacao = new Populacao(this.tamanhoPopulacao, tamanhoCromossomos);
		return populacao;
	}

	//Condição de parada
	public boolean isParada(int geracoesCont, int maxGeracoes) {
		return (geracoesCont > maxGeracoes);
	}

	public double calcFitness(Individuo individuo, Cidade cidades[]) {
		Rota rota = new Rota(individuo, cidades);
		double fitness = 1 / rota.getDistancia();

		individuo.setFitness(fitness);

		return fitness;
	}

	
	public void avaliarPopulacao(Populacao populacao, Cidade cidades[]) {
		double populacaoFitness = 0;

		//Calculando fitness da população total
		for (Individuo individuo : populacao.getIndividuos()) {
			populacaoFitness += this.calcFitness(individuo, cidades);
		}

		double fitness = populacaoFitness / populacao.size();
		populacao.setPopulacaoFitness(fitness);
	}

	//Selecionando pais para crossover usando seleção por torneio
	public Individuo selecionarPai(Populacao populacao) {
		
		Populacao torneio = new Populacao(this.tamanhoTorneio);

		//Adicionando individuos aleatórios ao torneio
		populacao.shuffle();
		for (int i = 0; i < this.tamanhoTorneio; i++) {
			Individuo torneioIndividuo = populacao.getIndividuo(i);
			torneio.setIndividuo(i, torneioIndividuo);
		}

		//Retornando o melhor
		return torneio.getFittest(0);
	}

	public Populacao crossoverPopulacao(Populacao populacao) {
		
		Populacao novaPopulacao = new Populacao(populacao.size());

		
		for (int populacaoIndex = 0; populacaoIndex < populacao.size(); populacaoIndex++) {
			
			Individuo pai1 = populacao.getFittest(populacaoIndex);

			
			if (this.taxaCrossover > Math.random() && populacaoIndex >= this.elitismoCont) {
				
				Individuo pai2 = this.selecionarPai(populacao);

				int descendentes[] = new int[pai1.getTamanhoCromossomos()];
				Arrays.fill(descendentes, -1);
				Individuo descendente = new Individuo(descendentes);

				int gene1 = (int) (Math.random() * pai1.getTamanhoCromossomos());
				int gene2 = (int) (Math.random() * pai1.getTamanhoCromossomos());

				final int startSubstr = Math.min(gene1, gene2);
				final int endSubstr = Math.max(gene1, gene2);

				for (int i = startSubstr; i < endSubstr; i++) {
					descendente.setGene(i, pai1.getGene(i));
				}
				
				for (int i = 0; i < pai2.getTamanhoCromossomos(); i++) {
					int parent2Gene = i + endSubstr;
					if (parent2Gene >= pai2.getTamanhoCromossomos()) {
						parent2Gene -= pai2.getTamanhoCromossomos();
					}

					if (descendente.containsGene(pai2.getGene(parent2Gene)) == false) {
						for (int j = 0; j < descendente.getTamanhoCromossomos(); j++) {
							if (descendente.getGene(j) == -1) {
								descendente.setGene(j, pai2.getGene(parent2Gene));
								break;
							}
						}
					}
				}

				novaPopulacao.setIndividuo(populacaoIndex, descendente);
			} else {
				novaPopulacao.setIndividuo(populacaoIndex, pai1);
			}
		}

		return novaPopulacao;
	}

	public Populacao mutacaoPopulacao(Populacao populacao) {
		
		Populacao novaPopulacao = new Populacao(this.tamanhoPopulacao);

		//Realizando mutação utilizando a técnica de swap
		for (int populacaoIndex = 0; populacaoIndex < populacao.size(); populacaoIndex++) {
			Individuo individuo = populacao.getFittest(populacaoIndex);

			if (populacaoIndex >= this.elitismoCont) {
				for (int geneIndex = 0; geneIndex < individuo.getTamanhoCromossomos(); geneIndex++) {
					if (this.taxaMutacao > Math.random()) {
						int novoGenePos = (int) (Math.random() * individuo.getTamanhoCromossomos());

						int gene1 = individuo.getGene(novoGenePos);
						int gene2 = individuo.getGene(geneIndex);
						
						individuo.setGene(geneIndex, gene1);
						individuo.setGene(novoGenePos, gene2);
					}
				}
			}

			
			novaPopulacao.setIndividuo(populacaoIndex, individuo);
		}

		return novaPopulacao;
	}

	public int getMaxGeracoes() {
		return maxGeracoes;
	}

	public void setMaxGeracoes(int maxGeracoes) {
		this.maxGeracoes = maxGeracoes;
	}

}
