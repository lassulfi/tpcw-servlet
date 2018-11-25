import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	private List<Evaluation> evaluations;
	private Bookstore bookstore = new Bookstore();

	@Before
	public void setup() {
		TPCW_Database.populate(10000, 1000, 1000, 10000, 1000);
		bookstore.populate(10000, 1000, 1000, 10000, 1000, 1000, 1000);
	}

	@Test
	public void deveGerarRecomendacoes() throws IOException {

		// Cenário
		evaluations = bookstore.populaAvaliacao();
		File dados = new File("dados.txt");
		BufferedWriter bfWritter = new BufferedWriter(new FileWriter(dados, false));
		//Primeiramente apaga o conteudo do arquivo
		PrintWriter pWriter = new PrintWriter(bfWritter);
		try {
			pWriter.flush();
			for (Evaluation evaluation : evaluations) {
				bfWritter.write(evaluation.getEvaluationData() + "\n");
			}
		} catch (IOException e) {
			System.out.println("erro ao gravar arquivo");
		} finally {
			pWriter.close();
			bfWritter.close();
		}

		FileReader fr = new FileReader(dados);
		BufferedReader bf = new BufferedReader(fr);
		String linha = bf.readLine();
		String[] s = linha.split(",");
		bf.close();
		fr.close();

		// Ação
		Recommender recommender = new Recommender(dados);
		recommender.setPreference(0.1);

		try {
			List<RecommendedItem> recomendations = recommender.GetRecommender(Long.valueOf(s[0]), 2);
			// Teste
			System.out.println("ID Cliente: " + s[0]);
			System.out.println("Recomendações:");
			for (RecommendedItem r : recomendations) {
				System.out.println(r);
			}
			Assert.assertFalse(recomendations.isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
