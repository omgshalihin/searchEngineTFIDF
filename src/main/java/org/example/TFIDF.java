package org.example;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

public class TFIDF {
    public static double calculateTermFrequency(String TERM, Map<String, Integer> termWeightMap) {
        if (termWeightMap.get(TERM) == null) {
            return 0;
        }

        double termCount = termWeightMap.get(TERM);
        double wordsCount = termWeightMap.values().stream().mapToDouble(Double::valueOf).sum();

        return (termCount / wordsCount);
    }

    public static double calculateInverseDocumentFrequency(double CORPUS, double sizeOfFilteredDocuments) {
        return Math.log10(CORPUS / sizeOfFilteredDocuments);
    }

    public static double calculateTermFrequencyInverseDocumentFrequency (
            String TERM,
            Map<String, Integer> termWeightMap,
            double CORPUS,
            double sizeOfFilteredDocuments) {

        double idf = calculateInverseDocumentFrequency(CORPUS, sizeOfFilteredDocuments);

        // fail-fast: if IDF is 0, then TF-IDF will be 0
        if (idf == 0) {
            return 0;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        decimalFormat.setRoundingMode(RoundingMode.UP);

        double tf = calculateTermFrequency(TERM, termWeightMap);

        return Double.parseDouble(decimalFormat.format(tf * idf));
    }

}
