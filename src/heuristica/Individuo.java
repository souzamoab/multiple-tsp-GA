package heuristica;

public class Individuo {
	
	/**
	 * In this case, the chromosome is an array of integers rather than a string. 
	 */
	private int[] cromossomos;
	private double fitness = -1;

	/**
	 * Initializes individual with specific chromosome
	 * 
	 * @param chromosome
	 *            The chromosome to give individual
	 */
	public Individuo(int[] cromossomos) {
		// Create individualchromosome
		this.cromossomos = cromossomos;
	}

	/**
	 * Initializes random individual
	 * 
	 * @param chromosomeLength
	 *            The length of the individuals chromosome
	 */
	public Individuo(int tamCromossomos) {
		// Create random individual
		int[] individuos;
		individuos = new int[tamCromossomos];
		
		/**
		 * In this case, we can no longer simply pick 0s and 1s -- we need to
		 * use every city index available. We also don't need to randomize or
		 * shuffle this chromosome, as crossover and mutation will ultimately
		 * take care of that for us.
		 */
		for (int gene = 0; gene < tamCromossomos; gene++) {
			individuos[gene] = gene;
		}
		
		this.cromossomos = individuos;
	}

	/**
	 * Gets individual's chromosome
	 * 
	 * @return The individual's chromosome
	 */
	public int[] getCromossomos() {
		return this.cromossomos;
	}

	/**
	 * Gets individual's chromosome length
	 * 
	 * @return The individual's chromosome length
	 */
	public int getTamanhoCromossomos() {
		return this.cromossomos.length;
	}

	/**
	 * Set gene at offset
	 * 
	 * @param gene
	 * @param offset
	 */
	public void setGene(int index, int gene) {
		this.cromossomos[index] = gene;
	}

	/**
	 * Get gene at offset
	 * 
	 * @param offset
	 * @return gene
	 */
	public int getGene(int index) {
		return this.cromossomos[index];
	}

	/**
	 * Store individual's fitness
	 * 
	 * @param fitness
	 *            The individuals fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * Gets individual's fitness
	 * 
	 * @return The individual's fitness
	 */
	public double getFitness() {
		return this.fitness;
	}

	/**
	 * Search for a specific integer gene in this individual.
	 * 
	 * For instance, in a Traveling Salesman Problem where cities are encoded as
	 * integers with the range, say, 0-99, this method will check to see if the
	 * city "42" exists.
	 * 
	 * @param gene
	 * @return
	 */
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.cromossomos.length; i++) {
			if (this.cromossomos[i] == gene) {
				return true;
			}
		}
		return false;
	}


	
}
