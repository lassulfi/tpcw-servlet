import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
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

	private List<Evaluation> avaliacoes;

	@Before
	public void setup() {
		
	}
	
	@Test
	public void deveGerarRecomendacoes() throws Exception, IOException {

		// Cenário

		File dados = new File("dados.txt");
		try {
			FileWriter fWriter = new FileWriter(dados, true);
			for (Evaluation avaliacao : avaliacoes) {
				fWriter.write(avaliacao.getEvaluationData() + "\n");
			}
			fWriter.close();
		} catch (IOException e) {
			System.out.println("erro ao gravar arquivo");
		}

		// Ação
		Recommender recommender = new Recommender(dados);

		// Teste
		Assert.assertNotNull(recommender.GetRecommender());
	}

}
