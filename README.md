# 🍽️ Food Change Mood App

This Kotlin-based application helps users explore, search, and interact with a wide variety of meals from around the world. The app leverages the `food.csv` file to provide smart suggestions, fun games, and healthy food options.

---

## 📁 Dataset Information

- The `food.csv` file contains detailed information about each meal including: name, description, ingredients, preparation time, and nutritional values.
- The `Nutrition` column contains an array of values in the following order:
  - **Calories, Total Fat, Sugar, Sodium, Protein, Saturated Fat, Carbohydrates**
- About **2%** of the meals in the dataset have `null` descriptions.

⚠️ **Note**: The dataset file is large and has not been uploaded to the repository.  
 [[Click here to download the file](https://drive.google.com/file/d/1px860X8gO_AFHNkcNFe64e_il_bDaKSI/view)]Once downloaded, place the file in the project's root directory.

---

## ✅ App Features

1. **Healthy Fast Food Suggestions**  
   Suggest meals that can be prepared in 15 minutes or less and are low in fat, saturated fat, and carbs.

2. **Smart Meal Search**  
   Search meals by name using a fast and typo-tolerant algorithm (e.g., Knuth-Morris-Pratt).

3. **Iraqi Meals Detector**  
   Identify meals tagged as "iraqi" or with "Iraq" in the description.

4. **Easy Food Suggestion**  
   Suggest 10 random easy-to-prepare meals (≤30 minutes, ≤5 ingredients, ≤6 steps).

5. **Guess the Preparation Time Game**  
   Users guess the prep time of a random meal in 3 attempts with hints after each try.

6. **Sweets with No Eggs**  
   Recommend random egg-free sweets; users can like (see full details) or dislike (get another suggestion).

7. **Keto Diet Meal Helper**  
   Suggest keto-friendly meals one at a time, without repetition.

8. **Search Foods by Add Date**  
   Input a date to list meal IDs and names added on that day, with exception handling for:
   - Wrong date format
   - No meals found on the given date

9. **Gym Helper**  
   Input desired calories and protein to get matching or approximate meal suggestions.

10. **Explore Global Food Culture**  
    Enter a country name to explore up to 20 random meals related to that country.

11. **Ingredient Guessing Game**  
    Guess the correct ingredient among 3 choices for a random meal. 1000 points per correct guess. Game ends after 1 wrong or 15 correct guesses.

12. **I Love Potato**  
    Show 10 random meals that include potatoes in their ingredients.

13. **So Thin Problem**  
    Suggest a high-calorie meal (over 700 calories) using the same logic as the egg-free sweets feature.

14. **Seafood Protein Ranking**  
    Display a list of seafood meals ranked by protein content (from highest to lowest), showing:
    - Rank
    - Meal name
    - Protein amount

15. **Italian Group Meals**  
    Suggest original Italian meals suitable for large groups, tagged with "for-large-groups".

---


Happy coding! 🎉
