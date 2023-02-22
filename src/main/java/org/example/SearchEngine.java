package org.example;

import java.util.*;

public class SearchEngine {

    public static List<Document> simpleSearchEngine(String term, List<Document> documents) throws Exception {
        // CONSTANTS
        final int CORPUS = documents.size();
        final String TERM = term.toLowerCase();

        // get only the documents that were searched for
        List<Document> filteredDocuments = documents.stream()
                .filter(document -> document.content.toLowerCase().contains(TERM))
                .toList();

        // fail-fast: if there are no documents or if searched term is not found in document
        if (CORPUS == 0 || filteredDocuments.size() == 0) {
            throw new Exception("No documents found");
        }

        // to handle a map of documents and sort DESC order based on document's key i.e. TF-IDF
        Map<Double, Document> documentMap = new TreeMap<>(Comparator.reverseOrder());

        for (Document document : filteredDocuments) {
            /**
             * calculate TF-IDF
             * TERM: lower case string of term i.e. search input
             * invertedIndexImplementation(document): to collect a map of words based on its occurrences in each document
             * CORPUS: total number of documents in the corpus
             * filteredDocuments.size(): number of documents where the term appears
             */
            double tfIdf = TFIDF.calculateTermFrequencyInverseDocumentFrequency (
                    TERM,
                    InvertedIndexImplementation.invertedIndexImplementation(document),
                    CORPUS,
                    filteredDocuments.size()
            );

            // fail-fast: if TF-IDF is 0, then should return list of documents without sorting,
            // which implies that the term is not very informative as it appears in all documents.
            if (tfIdf == 0.0) {
                return new ArrayList<>(filteredDocuments);
            }
            //otherwise, add the document to the map of documents
            documentMap.put(tfIdf, document);
        }
        // once all documents have been scanned, return the documents as a list, sorted based on the importance of a term in a corpus
        return new ArrayList<>(documentMap.values());
    }

    public static void main(String[] args) throws Exception {
        Document doc1 = new Document("the brown fox jumped over the brown dog");
        Document doc2 = new Document("the lazy brown dog sat in the corner");
        Document doc3 = new Document("the red fox bit the lazy dog");
        List<Document> documents = new ArrayList<>(List.of(doc1, doc2, doc3));
//        List<Document> documents = new ArrayList<>();
        System.out.println("-----------search for \"brown\"---------------------");
        System.out.println(simpleSearchEngine("brOwn", documents));
        System.out.println("-----------search for \"fox\"---------------------");
        System.out.println(simpleSearchEngine("FOx", documents));
//        System.out.println("-----------search for \"the\"---------------------");
//        System.out.println(simpleSearchEngine("tHe", documents));
    }
}