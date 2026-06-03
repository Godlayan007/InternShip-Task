// The Math Behind It: Cosine Similarity

//Before diving into the code, it helps to understand how the engine decides if two users are "similar." It compares their ratings using the Cosine Similarity formula: //

import java.util.*;

public class MovieRecommender {

    // The dataset: Maps a Username to a Map of (Movie Title -> Rating out of 5.0)
    private Map<String, Map<String, Double>> userRatings;

    public MovieRecommender() {
        userRatings = new HashMap<>();
    }

    /**
     * Adds a user's movie ratings to the dataset.
     */
    public void addUserRatings(String username, Map<String, Double> ratings) {
        userRatings.put(username, ratings);
    }

    /**
     * Calculates the Cosine Similarity between two users based on their ratings.
     */
    private double calculateCosineSimilarity(Map<String, Double> ratingsA, Map<String, Double> ratingsB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        // Calculate dot product for shared movies, and the magnitude (norm) for User A
        for (String movie : ratingsA.keySet()) {
            double ratingA = ratingsA.get(movie);
            if (ratingsB.containsKey(movie)) {
                double ratingB = ratingsB.get(movie);
                dotProduct += (ratingA * ratingB);
            }
            normA += Math.pow(ratingA, 2);
        }

        // Calculate the magnitude (norm) for User B
        for (Double ratingB : ratingsB.values()) {
            normB += Math.pow(ratingB, 2);
        }

        // Prevent division by zero
        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * Generates movie recommendations for a specific user.
     */
    public List<Map.Entry<String, Double>> getRecommendations(String targetUser) {
        Map<String, Double> targetUserRatings = userRatings.get(targetUser);
        
        if (targetUserRatings == null) {
            System.out.println("User not found in the database.");
            return new ArrayList<>();
        }

        // Map to store the predicted score for movies the user hasn't seen
        Map<String, Double> recommendationScores = new HashMap<>();
        // Map to store the sum of similarity weights for normalization
        Map<String, Double> similarityWeights = new HashMap<>();

        // 1. Loop through all other users to find similar tastes
        for (String otherUser : userRatings.keySet()) {
            if (otherUser.equals(targetUser)) continue; // Skip comparing the user to themselves

            Map<String, Double> otherUserRatings = userRatings.get(otherUser);
            double similarity = calculateCosineSimilarity(targetUserRatings, otherUserRatings);

            // If they have similar tastes (similarity > 0), look at their movies
            if (similarity > 0) {
                for (String movie : otherUserRatings.keySet()) {
                    // Only recommend movies the target user HAS NOT seen yet
                    if (!targetUserRatings.containsKey(movie)) {
                        double ratingGivenByOtherUser = otherUserRatings.get(movie);
                        
                        // Weighted score: Rating * Similarity
                        double weightedScore = ratingGivenByOtherUser * similarity;

                        recommendationScores.put(movie, recommendationScores.getOrDefault(movie, 0.0) + weightedScore);
                        similarityWeights.put(movie, similarityWeights.getOrDefault(movie, 0.0) + similarity);
                    }
                }
            }
        }

        // 2. Normalize the scores (Divide total weighted score by total similarity weight)
        Map<String, Double> finalPredictions = new HashMap<>();
        for (String movie : recommendationScores.keySet()) {
            double predictedRating = recommendationScores.get(movie) / similarityWeights.get(movie);
            finalPredictions.put(movie, predictedRating);
        }

        // 3. Sort the recommendations from highest predicted rating to lowest
        List<Map.Entry<String, Double>> sortedRecommendations = new ArrayList<>(finalPredictions.entrySet());
        sortedRecommendations.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        return sortedRecommendations;
    }

    // ==========================================
    // MAIN METHOD: Sample Data and Execution
    // ==========================================
    public static void main(String[] args) {
        MovieRecommender engine = new MovieRecommender();

        // Loading Sample Data
        // Alice likes Sci-Fi and Action
        engine.addUserRatings("Alice", Map.of(
            "The Matrix", 5.0,
            "Inception", 4.5,
            "Interstellar", 5.0,
            "The Notebook", 2.0
        ));

        // Bob has exact same taste as Alice, plus he watched Blade Runner
        engine.addUserRatings("Bob", Map.of(
            "The Matrix", 5.0,
            "Inception", 4.0,
            "Interstellar", 4.5,
            "Blade Runner 2049", 4.5
        ));

        // Charlie likes Romance and Drama, dislikes Sci-Fi
        engine.addUserRatings("Charlie", Map.of(
            "The Matrix", 1.0,
            "The Notebook", 5.0,
            "Titanic", 4.5,
            "Pride and Prejudice", 4.0
        ));

        // Dave has mixed tastes
        engine.addUserRatings("Dave", Map.of(
            "Inception", 3.0,
            "Titanic", 4.0,
            "Dune", 5.0
        ));

        // Let's get recommendations for Alice!
        String userToRecommend = "Alice";
        System.out.println("Generating recommendations for: " + userToRecommend);
        System.out.println("--------------------------------------------------");

        List<Map.Entry<String, Double>> recommendations = engine.getRecommendations(userToRecommend);

        if (recommendations.isEmpty()) {
            System.out.println("No new recommendations available.");
        } else {
            for (Map.Entry<String, Double> rec : recommendations) {
                // Formatting the output to show 2 decimal places
                System.out.printf("Movie: %-20s | Predicted Rating: %.2f / 5.0%n", rec.getKey(), rec.getValue());
            }
        }
    }
}
