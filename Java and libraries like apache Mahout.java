import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class ProductRecommender {

    public static void main(String[] args) {
        try {
            // 1. Load the Data
            // Point this to where your dataset.csv is located.
            File dataFile = new File("dataset.csv");
            DataModel model = new FileDataModel(dataFile);

            // 2. Define the Similarity Metric
            // Pearson Correlation measures how similar two users are based on the items they both rated.
            // Think of it as answering: "Do User A and User B have the same taste pattern?"
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // 3. Define the User Neighborhood
            // This groups users together. "NearestN" means we will look at the top 2 users 
            // who are most similar to our target user.
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // 4. Create the Recommender Engine
            // We combine the data, the similarity logic, and the neighborhood to build the engine.
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // 5. Generate Recommendations
            // Let's get 3 recommendations for User ID 1.
            int targetUserId = 1;
            int numberOfRecommendations = 3;
            List<RecommendedItem> recommendations = recommender.recommend(targetUserId, numberOfRecommendations);

            // 6. Print the Results to the console
            System.out.println("Generating recommendations for User ID: " + targetUserId);
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Recommended Item ID: " + recommendation.getItemID() + 
                                   " | Predicted Score: " + recommendation.getValue());
            }

        } catch (Exception e) {
            System.out.println("Oops! Something went wrong while building the recommendations.");
            e.printStackTrace();
        }
    }
}
