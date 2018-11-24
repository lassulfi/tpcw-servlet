import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * C02T03 - Classe de Teste de Modelagem do sistema de recomendações
 * 
 * @author esoft35
 *
 */
public class RecomendationTest {

	private ArrayList<Evaluation> avaliacoes;
	private Bookstore bookstore = new Bookstore();

	@Before
	public void setup() {
		TPCW_Database.populate(10000, 1000, 1000, 10000, 1000);
		bookstore.populate(10000, 1000, 1000, 10000, 1000, 1000, 1000);
	}

	@Test
	public void deveGerarRecomendacoes() throws IOException {

		// Cenário
		avaliacoes = bookstore.populaAvaliacao();
		File dados = new File("dados.txt");
		FileWriter fWriter = new FileWriter(dados, true);
		try {
			for (Evaluation avaliacao : avaliacoes) {
				fWriter.write(avaliacao.getEvaluationData() + "\n");
			}
		} catch (IOException e) {
			System.out.println("erro ao gravar arquivo");
		} finally {
			fWriter.close();
		}
		
		FileReader fr = new FileReader(dados);
	    BufferedReader bf = new BufferedReader(fr);
	    String linha = bf.readLine();
	    String[] s = linha.split(",");
	    bf.close();
	    fr.close();

		// Ação
		Recommender recommender = new Recommender(dados);
		
		List<RecommendedItem> recomendations = null;
		
		try {
			recomendations = recommender.GetRecommender(Integer.parseInt(s[0]),5);
		} catch (NumberFormatException | TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Teste
		System.out.println("ID Cliente: " + s[0]);
		System.out.println("Recomendações:");
		for(RecommendedItem r : recomendations){
			System.out.println(r);
		}
		Assert.assertTrue(recomendations.isEmpty());
	}

}
