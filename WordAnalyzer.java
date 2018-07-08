import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordAnalyzer {

    public List<String> getMostUsedWords(String words, List<String> exclusions){
        List<String> mostUsedWordsList = null;

        if(words != null){
            mostUsedWordsList = new ArrayList<>();

            Map<String, Integer> wordCounts = new HashMap<>();

            //Get rid of anything except letters, numbers and spaces
            String onlyWords = words.replaceAll("/[^A-Za-z0-9\\s]","").replaceAll("\\s+", " ").replaceAll("\\p{Punct}|\\d","").toLowerCase();

            //Split on spaces
            String[] tokenizedWords = onlyWords.split("\\s");

            //Populate word count map with excluded words and set these to 0 count
            if(exclusions != null && exclusions.size() > 0) {
                for (String excludedWord : exclusions){
                    if(excludedWord != null && excludedWord.length() > 0){
                        wordCounts.put(excludedWord.toLowerCase(), 0);
                    }
                }
            }

            //Iterate over tokenized words, add them to wordCount map if not present and increment count by 1
            for(String word : tokenizedWords){
                Integer count = wordCounts.getOrDefault(word, null);

                //If count is not found (null) then add it to the map and set initial value to 1
                if(count == null){
                    wordCounts.put(word, 1);
                }
                else if(count != 0){
                    //If found, check to see if the value is not 0 (meaning it's an excluded word
                    //If not 0 then increment and set the new value
                    wordCounts.put(word, ++count);
                }
            }

            //After processing has been completed, get each word from the list
            for(Map.Entry<String, Integer> entry : wordCounts.entrySet()){

                //If the entry count (value) is greater than 0, then add it to the most used words list
                if(entry.getValue() > 0){
                    mostUsedWordsList.add(entry.getKey());
                }
            }
        }

        return mostUsedWordsList;
    }
}
