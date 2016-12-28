package com.tm.braveti.spark.predictivemodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

public class BravitiPredictionEngine implements Serializable {

	public void process(Integer userid, Integer numberOfRecommendation) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("Java Collaborative Filtering Example");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		// Step 1 : Load and parse the data
		String path = "src\\com\\tm\\braveti\\resources\\training.data";
		JavaRDD<String> data = jsc.textFile(path);
		// Step 2 : Prepare rating Java Rdd
		JavaRDD<Rating> ratings = data.map(new Function<String, Rating>() {
			public Rating call(String s) {
				String[] sarray = s.split(",");
				return new Rating(Integer.parseInt(sarray[0]), Integer.parseInt(sarray[1]),
						Double.parseDouble(sarray[2]));
			}
		});

		// Step 3 : Build the recommendation model using ALS
		int rank = 10;
		int numIterations = 10;
		MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), rank, numIterations, 0.01);

		// ***** Product recommendation by category
		List<RecommendedCatagary> recommendedData = new ArrayList<RecommendedCatagary>();
		Rating[] rdata = model.recommendProducts(userid, 2);
		for (int i = 0; i < rdata.length; i++) {
			recommendedData.add(new RecommendedCatagary(rdata[i].user(), rdata[i].product()));
			System.out.println("Recommended products =" + rdata[i].product());
		}
	}

	public static void main(String[] args) {
		BravitiPredictionEngine bravitiPredictionEngine = new BravitiPredictionEngine();
		bravitiPredictionEngine.process(2,2);

	}

}
