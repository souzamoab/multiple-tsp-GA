package arquivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cidade.Cidade;

public class Arquivo {
	
	private int nCidades;
	private Cidade[] cidades;
	
	public Cidade[] carregarCidades(String nomeArquivo) throws IOException {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(nomeArquivo));
			String linha = reader.readLine();
			nCidades = Integer.parseInt(linha);
			cidades = new Cidade[nCidades];
			
			inicializarCidades(cidades, nCidades);
			
			int coordX = 0;
			int coordY = 0;
			int index = 0;
			
			while((linha = reader.readLine()) != null) {

				int length = linha.length();
				
				for(int i = 0; i < length; i++) {
					if(linha.substring(i, i + 1).equals(" ")) {
						int pos = i + 1;
						coordX = Integer.parseInt(linha.substring(0, pos -1));
						coordY = Integer.parseInt(linha.substring(pos, length));
						gerarCoordenadas(index, coordX, coordY);
					}
				}
				
				index++;
				
			}
			return cidades;
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			reader.close();
		}
		return null;
	}
	
	public void gerarCoordenadas(int index, int coordX, int coordY) {
		Cidade cidade = cidades[index];
		cidade.setLabel(index);
		cidade.setX(coordX);
		cidade.setY(coordY);
	}
	
	public void inicializarCidades(Cidade[] cidades, int nCidades) {
		for(int i = 0; i < nCidades; i++) {
			cidades[i] = new Cidade(0, 0, 0);
		}
	}
	
	public void printarCidadesCoord(ArrayList<Cidade> cidades) {
		for(Cidade city : cidades) {
			System.out.println("Cidade "+city.getLabel()+ " = ("+city.getX()+","+city.getY()+")");
		}
	}
	
}
