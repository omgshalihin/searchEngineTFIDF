package org.example;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

public class TFIDF {

    public static double calculateInverseDocumentFrequency(double CORPUS, double sizeOfFilteredDocuments) {
        return Math.log10(CORPUS / sizeOfFilteredDocuments);
    }

    public static double calculateTermFrequency(String TERM, Map.Entry<String, Map<String, Integer>> document) {
        if (document.getValue().get(TERM) == null) {
            return 0;
        }

        double termCount = document.getValue().get(TERM);
        double wordsCount = document.getValue().values().stream().mapToDouble(Double::valueOf).sum();

        return termCount/wordsCount;
    }

    public static double calculateTermFrequencyInverseDocumentFrequency (Map.Entry<String, Map<String, Integer>> document, String TERM, double totalDocumentsInCorpus, double numberOfDocumentsWhereTermAppears) {

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        decimalFormat.setRoundingMode(RoundingMode.UP);

        double idf = calculateInverseDocumentFrequency(totalDocumentsInCorpus, numberOfDocumentsWhereTermAppears);
        double tf = calculateTermFrequency(TERM, document);

        return Double.parseDouble(decimalFormat.format(tf * idf));
    }

}
