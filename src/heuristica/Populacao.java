package heuristica;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Populacao {
	private Individuo populacao[];
	private double populacaoFitness = -1;

	/**
	 * Initializes blank population of individuals
	 * 
	 * @param populationSize
	 *            The size of the population
	 */
	public Populacao(int tamanhoPopulacao) {
		// Initial population
		this.populacao = new Individuo[tamanhoPopulacao];
	}

	/**
	 * Initializes population of individuals
	 * 
	 * @param populationSize
	 *            The size of the population
	 * @param chromosomeLength
	 *            The length of the individuals chromosome
	 */
	public Populacao(int tamanhoPopulacao, int tamanhoCromossomos) {
		// Initial population
		this.populacao = new Individuo[tamanhoPopulacao];

		// Loop over population size
		for (int individualCount = 0; individualCount < tamanhoPopulacao; individualCount++) {
			// Create individual
			Individuo individuo = new Individuo(tamanhoCromossomos);
			// Add individual to population
			this.populacao[individualCount] = individuo;
		}
	}

	/**
	 * Get individuals from the population
	 * 
	 * @return individuals Individuals in population
	 */
	public Individuo[] getIndividuos() {
		return this.populacao;
	}

	/**
	 * Find fittest individual in the population
	 * 
	 * @param offset
	 * @return individual Fittest individual at offset
	 */
	public Individuo getFittest(int index) {
		// Order population by fitness
		Arrays.sort(this.populacao, new Comparator<Individuo>() {
			@Override
			public int compare(Individuo o1, Individuo o2) {
				if (o1.getFitness() > o2.getFitness()) {
					return -1;
				} else if (o1.getFitness() < o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});

		// Return the fittest individual
		return this.populacao[index];
	}

	/**
	 * Set population's fitness
	 * 
	 * @param fitness
	 *            The population's total fitness
	 */
	public void setPopulacaoFitness(double fitness) {
		this.populacaoFitness = fitness;
	}

	/**
	 * Get population's fitness
	 * 
	 * @return populationFitness The population's total fitness
	 */
	public double getPopulacaoFitness() {
		return this.populacaoFitness;
	}

	/**
	 * Get population's size
	 * 
	 * @return size The population's size
	 */
	public int size() {
		return this.populacao.length;
	}

	/**
	 * Set individual at offset
	 * 
	 * @param individuo
	 * @param offset
	 * @return individual
	 */
	public Individuo setIndividuo(int index, Individuo individuo) {
		return populacao[index] = individuo;
	}

	/**
	 * Get individual at offset
	 * 
	 * @param offset
	 * @return individual
	 */
	public Individuo getIndividuo(int index) {
		return populacao[index];
	}

	/**
	 * Shuffles the population in-place
	 * 
	 * @param void
	 * @return void
	 */
	public void shuffle() {
		Random rnd = new Random();
		for (int i = populacao.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			Individuo a = populacao[index];
			populacao[index] = populacao[i];
			populacao[i] = a;
		}
	}

}