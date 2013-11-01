import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PreProcess {

	public ArrayList<Product> readProductFile(String fileName,
			HashMap<Integer, Integer> productIndex) throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Product> productList = mapper.readValue(new File(fileName),
				new TypeReference<List<Product>>() {
				});
		int i = 0;
		for (Product rec : productList) {
			productIndex.put(rec.getProductId(), i);
			i++;
		}

		return productList;
	}

	// TODO make the exception no generic
	public ArrayList<Query> create_query_index(String fileName,
			HashMap<String, ArrayList<Integer>> queryIndex,
			HashMap<String, ArrayList<Integer>> artistIndex,
			ArrayList<Product> productData,
			HashMap<Integer, Integer> productIndex) throws JsonParseException,
			JsonMappingException, IOException

	{
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Query> queryList = mapper.readValue(new File(fileName),
				new TypeReference<List<Query>>() {
				});
		int i = 0;
		for (Query rec : queryList) {
			Product product = productData.get(productIndex.get(rec
					.getProductId()));
			String query = rec.getQuery();
			if (product != null) {
				rec.setArtist(product.getArtist());
				rec.setProductId(product.getProductId());
				rec.setProductName(product.getProductName());
				rec.setGenre(product.getGenre());
			}

			try {
				createIndex(queryIndex, i, query);
			} catch (Exception e) {

				// e.printStackTrace();
			}
			String artist = rec.getArtist();
			if (artist != null && !artist.trim().isEmpty()) {
				try {
					createIndex(artistIndex, i, artist.trim());
				} catch (EmptyQueryException e) {
					// e.printStackTrace();
				}
			}
			i++;

		}

		return queryList;
	}

	public void createIndex(HashMap<String, ArrayList<Integer>> queryIndex,
			int index, String query) throws EmptyQueryException {
		if (queryIndex == null) {
			throw new EmptyQueryException(
					"Passed a empty arraylist for indexing!!");
		}

		if (query == null || query.isEmpty()) {
			throw new EmptyQueryException("Passed a empty Query!!");
		}

		if (queryIndex.containsKey(query)) {
			ArrayList<Integer> indexes = queryIndex.get(query);
			indexes.add(index);
		} else {
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			indexes.add(index);
			queryIndex.put(query, indexes);
		}
	}

	public ArrayList<Query> process(String dataFilePath, String queryFilePath, HashMap<String, ArrayList<Integer>> queryIndex,
						HashMap<String, ArrayList<Integer>> artistIndex)
			throws JsonParseException, JsonMappingException, IOException {
		
		ArrayList<Product> productList = new ArrayList<Product>();
		HashMap<Integer, Integer> productIndex = new HashMap<Integer, Integer>();

		productList = readProductFile(dataFilePath, productIndex);
		ArrayList<Query>queryList = create_query_index(queryFilePath, queryIndex, artistIndex, productList, productIndex);
		return queryList;
	}

}
