package cidade;

public class Cidade {
	private int label;
	private int x;
	private int y;

	public Cidade(int label, int x, int y) {
		this.label = label;
		this.x = x;
		this.y = y;
	}

	//Cálculo de distância euclidiana
	public double calcularDist(Cidade cidade) {
		double x = Math.pow((cidade.getX() - this.getX()), 2);
		double y = Math.pow((cidade.getY() - this.getY()), 2);

		double distancia = Math.sqrt(Math.abs(x + y));
		return distancia;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
