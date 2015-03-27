package co.edu.uniandes.useritem.server;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.recommender101.data.Rating;



import co.edu.uniandes.useritem.client.UserUserService;
import co.edu.uniandes.useritem.shared.IndexType;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserUserServiceImpl extends RemoteServiceServlet implements
		UserUserService {

	@Override
	public List<String> getUserUserRecommended(String user, int numVecinos, double numSimilarity, int indexType) {

		Db.Init();

		switch (indexType) {
		case IndexType.JACCARD:
			return getUserRecommendedJaccard(user, numVecinos, numSimilarity);
		case IndexType.COSINE:
			return getUserRecommendedCosine(user, numVecinos, numSimilarity);
		case IndexType.PEARSON:
			return getUserRecommendedPearson(user, numVecinos, numSimilarity);
		default:
			break;
		}

		return null;
	}

	public List<String> getUserRecommendedJaccard(String user, int numVecinos, double numSimilarity) {

		JaccardCoefficient jc = new JaccardCoefficient();

		Set<Rating> rts = Db.model.getRatingsOfUser(Integer.parseInt(user));

		Set<String> peliculas = new HashSet<String>();
		for (Rating rt : rts) {
			peliculas.add(String.valueOf(rt.item));
		}

		Map<Integer, List<Double>> arrSimilarity = new HashMap<Integer, List<Double>>();
		Map<Integer, Double> promSimilarity = new HashMap<Integer, Double>();

		int neighborgs=0;
		
		for (Integer userId : Db.model.getUsers()) {

			Set<Rating> rtsAll = Db.model.getRatingsOfUser(userId);
			Set<String> peliculasAll = new HashSet<String>();

			for (Rating rt : rtsAll) {
				peliculasAll.add(String.valueOf(rt.item));
			}

			double similarity = jc
					.similarity(
							peliculas.toArray(new String[peliculas.size()]),
							(String[]) peliculasAll
									.toArray(new String[peliculas.size()]));
			
			
			
			if (similarity >= numSimilarity) {

				System.out.println("--> " + userId.toString() + " --> " + Double.toString(similarity));
				// sacar peliculas que no he visto
				for (Rating rt : rtsAll) {
					if (!peliculas.contains(String.valueOf(rt.item))) {
						// multiplico calificacion por la similaridad
						double multi = similarity * ((int) rt.rating);
						// guardo
						// promedio valor
						if (arrSimilarity.containsKey(rt.item)) {
							int size = arrSimilarity.get(rt.item).size();
							arrSimilarity.get(rt.item).add(similarity);
							promSimilarity
									.put(rt.item, (promSimilarity.get(rt.item)
											* size + multi)
											/ (size + 1));
						} else {
							List<Double> lista = new ArrayList<Double>();
							lista.add(multi);
							arrSimilarity.put(rt.item, lista);
							promSimilarity.put(rt.item, multi);
						}

					}
				}
				neighborgs++;
				
				if(neighborgs == numVecinos){
					break;
				}
			}
		}
		// ordeno por calificacion
		ValueComparator bvc = new ValueComparator(promSimilarity);
		TreeMap<Integer, Double> sorted_map = new TreeMap<Integer, Double>(bvc);
		sorted_map.putAll(promSimilarity);
		// saco las x primeras
		int i = 0;
		String[] resultado = new String[(sorted_map.keySet().size() > 10 ? 10
				: sorted_map.keySet().size())];
		for (Integer pelicula : sorted_map.keySet()) {
			resultado[i] = "" + pelicula;
			i++;
			if (i == 10) {
				break;
			}
		}

		return getNameMovie(resultado);

	}

	public List<String> getUserRecommendedPearson(String user, int numVecinos, double numSimilarity) {

		Set<Rating> rts = Db.model.getRatingsOfUser(Integer.parseInt(user));

		Set<String> peliculas = new HashSet<String>();
		double[] ratings = new double[rts.size()];

		int j = 0;
		for (Rating rt : rts) {
			peliculas.add(String.valueOf(rt.item));
			ratings[j] = Double.valueOf(rt.rating);
			j++;
		}

		Map<Integer, List<Double>> arrSimilarity = new HashMap<Integer, List<Double>>();
		Map<Integer, Double> promSimilarity = new HashMap<Integer, Double>();

		int neighborgs=0;
		for (Integer userId : Db.model.getUsers()) {

			Set<Rating> rtsAll = Db.model.getRatingsOfUser(userId);
			Set<String> peliculasAll = new HashSet<String>();
			double[] ratingsAll = new double[rtsAll.size()];

			int k = 0;
			for (Rating rt : rtsAll) {
				peliculasAll.add(String.valueOf(rt.item));
				ratingsAll[k] = Double.valueOf(rt.rating);
				k++;
			}

			double[][] indice = new double[2][(ratingsAll.length > ratings.length ? ratingsAll.length
					: ratings.length)];
			for (int i = 0; i < (ratingsAll.length > ratings.length ? ratingsAll.length
					: ratings.length); i++) {
				if (i >= ratingsAll.length) {
					indice[0][i] = 0;
				} else {
					indice[0][i] = ratingsAll[i];
				}

				if (i >= ratings.length) {
					indice[1][i] = 0;
				} else {
					indice[1][i] = ratings[i];
				}
			}

			double similarity = new PearsonsCorrelation().correlation(
					indice[0], indice[1]);
			
			if (similarity >= numSimilarity) {

				System.out.println(userId.toString() + ";" + Double.toString(similarity));
				// sacar peliculas que no he visto
				for (Rating rt : rtsAll) {
					if (!peliculas.contains(String.valueOf(rt.item))) {
						// multiplico calificacion por la similaridad
						double multi = similarity * ((int) rt.rating);
						// guardo
						// promedio valor
						if (arrSimilarity.containsKey(rt.item)) {
							int size = arrSimilarity.get(rt.item).size();
							arrSimilarity.get(rt.item).add(similarity);
							promSimilarity
									.put(rt.item, (promSimilarity.get(rt.item)
											* size + multi)
											/ (size + 1));
						} else {
							List<Double> lista = new ArrayList<Double>();
							lista.add(multi);
							arrSimilarity.put(rt.item, lista);
							promSimilarity.put(rt.item, multi);
						}

					}
				}
			}
			neighborgs++;
			
			if(neighborgs == numVecinos){
				break;
			}
		}
		// ordeno por calificacion
		ValueComparator bvc = new ValueComparator(promSimilarity);
		TreeMap<Integer, Double> sorted_map = new TreeMap<Integer, Double>(bvc);
		sorted_map.putAll(promSimilarity);
		// saco las x primeras
		int i = 0;
		String[] resultado = new String[(sorted_map.keySet().size() > 10 ? 10
				: sorted_map.keySet().size())];
		for (Integer pelicula : sorted_map.keySet()) {
			resultado[i] = "" + pelicula;
			i++;
			if (i == 10) {
				break;
			}
		}

		return getNameMovie(resultado);

	}

	public List<String> getUserRecommendedCosine(String user, int numVecinos, double numSimilarity) {

		Set<Rating> rts = Db.model.getRatingsOfUser(Integer.parseInt(user));

		Set<String> peliculas = new HashSet<String>();
		HashMap<String, Double> ratings = new HashMap<String, Double>();

		for (Rating rt : rts) {
			peliculas.add(String.valueOf(rt.item));
			if (!ratings.containsKey(rt.item)) {
				ratings.put(String.valueOf(rt.item), Double.valueOf(rt.rating));
			}
		}

		Map<Integer, List<Double>> arrSimilarity = new HashMap<Integer, List<Double>>();
		Map<Integer, Double> promSimilarity = new HashMap<Integer, Double>();

		
		int neighborgs=0;
		for (Integer userId : Db.model.getUsers()) {

			Set<Rating> rtsAll = Db.model.getRatingsOfUser(userId);
			Set<String> peliculasAll = new HashSet<String>();
			HashMap<String, Double> ratingsAll = new HashMap<String, Double>();

			for (Rating rt : rtsAll) {
				peliculasAll.add(String.valueOf(rt.item));

				if (!ratingsAll.containsKey(rt.item)) {
					ratingsAll.put(String.valueOf(rt.item),
							Double.valueOf(rt.rating));
				}
			}

			double similarity = new CosineCoefficient().similarity(ratings,
					ratingsAll);
			
			if (similarity >= numSimilarity) {

				System.out.println(userId.toString() + ";" + Double.toString(similarity));
				// sacar peliculas que no he visto
				for (Rating rt : rtsAll) {
					if (!peliculas.contains(String.valueOf(rt.item))) {
						// multiplico calificacion por la similaridad
						double multi = similarity * ((int) rt.rating);
						// guardo
						// promedio valor
						if (arrSimilarity.containsKey(rt.item)) {
							int size = arrSimilarity.get(rt.item).size();
							arrSimilarity.get(rt.item).add(similarity);
							promSimilarity
									.put(rt.item, (promSimilarity.get(rt.item)
											* size + multi)
											/ (size + 1));
						} else {
							List<Double> lista = new ArrayList<Double>();
							lista.add(multi);
							arrSimilarity.put(rt.item, lista);
							promSimilarity.put(rt.item, multi);
						}

					}
				}
				neighborgs++;
				
				if(neighborgs == numVecinos){
					break;
				}
			}
		}
		// ordeno por calificacion
		ValueComparator bvc = new ValueComparator(promSimilarity);
		TreeMap<Integer, Double> sorted_map = new TreeMap<Integer, Double>(bvc);
		sorted_map.putAll(promSimilarity);
		// saco las x primeras
		int i = 0;
		String[] resultado = new String[(sorted_map.keySet().size() > 10 ? 10
				: sorted_map.keySet().size())];
		for (Integer pelicula : sorted_map.keySet()) {
			resultado[i] = "" + pelicula;
			i++;
			if (i == 10) {
				break;
			}
		}

		return getNameMovie(resultado);
	}

	private List<String> getNameMovie(String[] arrMovieId) {

		Db db = new Db();
		List<String> arrNames = new ArrayList<String>();

		for (String movieId : arrMovieId) {
			arrNames.add(db.getNameMovie(movieId));
		}

		return arrNames;
	}

	/*public static void main(String[] args) {
		UserUserServiceImpl u = new UserUserServiceImpl();
		Db.Init();
		List<String> datos = u.getUserRecommendedJaccard("10653", 1000, 0.0);
		for (String d : datos) {
			System.out.println("--> " + d);
		}
	}*/

}
