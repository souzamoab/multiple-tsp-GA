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
	public boolean isTerminationConditionMet(int geracoesCont, int maxGeracoes) {
		return (geracoesCont > maxGeracoes);
	}

	public double calcFitness(Individuo individuo, Cidade cidades[]) {
		// Get fitness
		Rota rota = new Rota(individuo, cidades);
		double fitness = 1 / rota.getDistancia();

		// Store fitness
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

	/**
	 * Ordered crossover mutation
	 * 
	 * Chromosomes in the TSP require that each city is visited exactly once.
	 * Uniform crossover can break the chromosome by accidentally selecting a city
	 * that has already been visited from a parent; this would lead to one city
	 * being visited twice and another city being skipped altogether.
	 * 
	 * Additionally, uniform or random crossover doesn't really preserve the most
	 * important aspect of the genetic information: the specific order of a group of
	 * cities.
	 * 
	 * We need a more clever crossover algorithm here. What we can do is choose two
	 * pivot points, add chromosomes from one parent for one of the ranges, and then
	 * only add not-yet-represented cities to the second range. This ensures that no
	 * cities are skipped or visited twice, while also preserving ordered batches of
	 * cities.
	 * 
	 * @param populacao
	 * @return The new population
	 */
	public Populacao crossoverPopulacao(Populacao populacao) {
		// Create new population
		Populacao novaPopulacao = new Populacao(populacao.size());

		// Loop over current population by fitness
		for (int populacaoIndex = 0; populacaoIndex < populacao.size(); populacaoIndex++) {
			// Get parent1
			Individuo pai1 = populacao.getFittest(populacaoIndex);

			// Apply crossover to this individual?
			if (this.taxaCrossover > Math.random() && populacaoIndex >= this.elitismoCont) {
				// Find parent2 with tournament selection
				Individuo pai2 = this.selecionarPai(populacao);

				// Create blank offspring chromosome
				int descendentes[] = new int[pai1.getTamanhoCromossomos()];
				Arrays.fill(descendentes, -1);
				Individuo descendente = new Individuo(descendentes);

				// Get subset of parent chromosomes
				int gene1 = (int) (Math.random() * pai1.getTamanhoCromossomos());
				int gene2 = (int) (Math.random() * pai1.getTamanhoCromossomos());

				// make the smaller the start and the larger the end
				final int startSubstr = Math.min(gene1, gene2);
				final int endSubstr = Math.max(gene1, gene2);

				// Loop and add the sub tour from parent1 to our child
				for (int i = startSubstr; i < endSubstr; i++) {
					descendente.setGene(i, pai1.getGene(i));
				}

				// Loop through parent2's city tour
				for (int i = 0; i < pai2.getTamanhoCromossomos(); i++) {
					int parent2Gene = i + endSubstr;
					if (parent2Gene >= pai2.getTamanhoCromossomos()) {
						parent2Gene -= pai2.getTamanhoCromossomos();
					}

					// If offspring doesn't have the city add it
					if (descendente.containsGene(pai2.getGene(parent2Gene)) == false) {
						// Loop to find a spare position in the child's tour
						for (int j = 0; j < descendente.getTamanhoCromossomos(); j++) {
							// Spare position found, add city
							if (descendente.getGene(j) == -1) {
								descendente.setGene(j, pai2.getGene(parent2Gene));
								break;
							}
						}
					}
				}

				// Add child
				novaPopulacao.setIndividuo(populacaoIndex, descendente);
			} else {
				// Add individual to new population without applying crossover
				novaPopulacao.setIndividuo(populacaoIndex, pai1);
			}
		}

		return novaPopulacao;
	}

	/**
	 * Apply mutation to population
	 * 
	 * Because the traveling salesman problem must visit each city only once, this
	 * form of mutation will randomly swap two genes instead of bit-flipping a gene
	 * like in earlier examples.
	 * 
	 * @param populacao The population to apply mutation to
	 * @return The mutated population
	 */
	public Populacao mutacaoPopulacao(Populacao populacao) {
		// Initialize new population
		Populacao novaPopulacao = new Populacao(this.tamanhoPopulacao);

		// Loop over current population by fitness
		for (int populacaoIndex = 0; populacaoIndex < populacao.size(); populacaoIndex++) {
			Individuo individuo = populacao.getFittest(populacaoIndex);

			// Skip mutation if this is an elite individual
			if (populacaoIndex >= this.elitismoCont) {
				// System.out.println("Mutating population member "+populationIndex);
				// Loop over individual's genes
				for (int geneIndex = 0; geneIndex < individuo.getTamanhoCromossomos(); geneIndex++) {
					// System.out.println("\tGene index "+geneIndex);
					// Does this gene need mutation?
					if (this.taxaMutacao > Math.random()) {
						// Get new gene position
						int novoGenePos = (int) (Math.random() * individuo.getTamanhoCromossomos());
						// Get genes to swap
						int gene1 = individuo.getGene(novoGenePos);
						int gene2 = individuo.getGene(geneIndex);
						// Swap genes
						individuo.setGene(geneIndex, gene1);
						individuo.setGene(novoGenePos, gene2);
					}
				}
			}

			// Add individual to population
			novaPopulacao.setIndividuo(populacaoIndex, individuo);
		}

		// Return mutated population
		return novaPopulacao;
	}

	public int getMaxGeracoes() {
		return maxGeracoes;
	}

	public void setMaxGeracoes(int maxGeracoes) {
		this.maxGeracoes = maxGeracoes;
	}

}
