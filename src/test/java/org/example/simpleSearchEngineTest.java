package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class simpleSearchEngineTest {

    private final Document exampleDoc1 = new Document("the brown fox jumped over the brown dog");
    private final Document exampleDoc2 = new Document("the lazy brown dog sat in the corner");
    private final Document exampleDoc3 = new Document("the red fox bit the lazy dog");
    List<Document> example = new ArrayList<>(List.of(exampleDoc1, exampleDoc2, exampleDoc3));

    @Test
    void searchForBrownShouldReturnListOfDoc1FollowedByDoc2() throws Exception {
        var result = SearchEngine.simpleSearchEngine("brown", example);
        assertEquals(List.of(example.get(0), example.get(1)), result);
        System.out.println(result);
    }

    @Test
    void searchForFoxShouldReturnListOfDoc3FollowedByDoc1() throws Exception {
        var result = SearchEngine.simpleSearchEngine("fox", example);
        assertEquals(List.of(example.get(2), example.get(0)), result);
        System.out.println(result);
    }

    @Test
    void searchForThisFromWikiExample() throws Exception {
        var tf1 = TFIDF.calculateTermFrequency("this", new HashMap<>(Map.of("this", 1, "is", 1, "a", 2, "sample", 1)));
        var tf2 = TFIDF.calculateTermFrequency("this", new HashMap<>(Map.of("this", 1, "is", 1, "another", 2, "example", 3)));
        var idf = TFIDF.calculateInverseDocumentFrequency(2,2);
        var tfidf1 = tf1 * idf;
        var tfidf2 = tf2 * idf;
        assertEquals(0.2, Double.parseDouble(String.format("%.1f", tf1)));
        assertEquals(0.14, Double.parseDouble(String.format("%.2f", tf2)));
        assertEquals(0, idf);
        assertEquals(0, tfidf1);
        assertEquals(0, tfidf2);
        System.out.println("tf1: " + tf1 + " tf2: " + tf2 + " idf: " + idf + " tfidf1: " + tfidf1 + " tfIdf2: " + tfidf2);
    }

    @Test
    void searchForExampleFromWikiExample() throws Exception {
        var tf1 = TFIDF.calculateTermFrequency("example", new HashMap<>(Map.of("this", 1, "is", 1, "a", 2, "sample", 1)));
        var tf2 = TFIDF.calculateTermFrequency("example", new HashMap<>(Map.of("this", 1, "is", 1, "another", 2, "example", 3)));
        var idf = TFIDF.calculateInverseDocumentFrequency(2,1);
        var tfidf1 = tf1 * idf;
        var tfidf2 = tf2 * idf;
        assertEquals(0, Double.parseDouble(String.format("%.1f", tf1)));
        assertEquals(0.429, Double.parseDouble(String.format("%.3f", tf2)));
        assertEquals(0.301, Double.parseDouble(String.format("%.3f", idf)));
        assertEquals(0, tfidf1);
        assertEquals(0.129, Double.parseDouble(String.format("%.3f", tfidf2)));
        System.out.println("tf1: " + tf1 + " tf2: " + tf2 + " idf: " + idf + " tfidf1: " + tfidf1 + " tfIdf2: " + tfidf2);
    }

    @Test
    void searchForFox() throws Exception {
        var tf1 = TFIDF.calculateTermFrequency("fox", new HashMap<>(Map.of("a", 2, "quick", 1, "brown", 1, "fox", 2, "jumps", 1, "over", 1, "the", 1, "lazy", 1, "dog", 1, "What", 1)));
        var tf2 = TFIDF.calculateTermFrequency("fox", new HashMap<>(Map.of("a", 2, "quick", 1, "brown", 1, "fox", 3, "jumps", 1, "over", 1, "the", 1, "lazy", 1, "What", 1)));
        var idf = TFIDF.calculateInverseDocumentFrequency(2,2);
        var tfidf1 = tf1 * idf;
        var tfidf2 = tf2 * idf;
        assertEquals(0.17, Double.parseDouble(String.format("%.2f", tf1)));
        assertEquals(0.25, Double.parseDouble(String.format("%.2f", tf2)));
        assertEquals(0, Double.parseDouble(String.format("%.3f", idf)));
        assertEquals(0, tfidf1);
        assertEquals(0, Double.parseDouble(String.format("%.3f", tfidf2)));
        System.out.println("tf1: " + tf1 + " tf2: " + tf2 + " idf: " + idf + " tfidf1: " + tfidf1 + " tfIdf2: " + tfidf2);
    }

}