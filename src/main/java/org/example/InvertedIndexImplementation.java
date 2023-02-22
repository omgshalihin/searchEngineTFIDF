package org.example;

import java.util.HashMap;
import java.util.Map;

public class InvertedIndexImplementation {

    public static Map<String, Integer> invertedIndexImplementation(Document document) {
        Map<String, Integer> termWeightMap = new HashMap<>();

        for (String word : document.content.toLowerCase().split(" ")) {
            Integer wordCount = termWeightMap.get(word);
            termWeightMap.put(word, (wordCount == null) ? 1 : wordCount + 1);
        }

        return termWeightMap;
    }
}
