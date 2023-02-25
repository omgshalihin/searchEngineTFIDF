package org.example;

import java.sql.SQLOutput;
import java.util.*;

public class SearchEngine {


    public static List<Document> simpleSearchEngine(String term, List<Document> corpus) throws Exception {

        // fail-fast: if there are no documents
        if (corpus.size() == 0) {
            throw new Exception("No documents found");
        }

        Document constants = new Document(corpus.size(), term.toLowerCase());

        int totalDocumentsInCorpus = constants.getCorpus();
        String TERM = constants.getTerm();

        Map<String, Map<String, Integer>> documents = Document.contentAndTermWeightMap;

        double numberOfDocumentsWhereTermAppears = documents.values().stream()
                .filter(document -> document.containsKey(TERM))
                .count();

        // fail-fast: queried word not available in corpus
        if (numberOfDocumentsWhereTermAppears == 0) {
            throw new Exception("Queried word not found in corpus");
        }
        // fail-fast: if TF-IDF is 0, then should return list of documents without sorting,
        if (totalDocumentsInCorpus == numberOfDocumentsWhereTermAppears) {
            return new ArrayList<>(corpus);
        }
        // to handle a map of documents and sort DESC order based on document's key i.e. TF-IDF
        Map<Double, Document> resultsSortedByTFIDF = new TreeMap<>(Comparator.reverseOrder());

        for (var document : documents.entrySet()) {

            double tfidf = TFIDF.calculateTermFrequencyInverseDocumentFrequency(document, TERM, totalDocumentsInCorpus, numberOfDocumentsWhereTermAppears);

            // fail-fast: if TF-IDF is 0, then should return list of documents without sorting,
            // which implies that the term is not very informative as it appears in all documents.
            // otherwise, add the document to the map of documents
            if (tfidf != 0.0) {
                corpus.stream()
                        .filter(el -> el.getContent().equals(document.getKey()))
                        .forEach(el -> resultsSortedByTFIDF.put(tfidf, el));
            }
        }

        // once all documents have been scanned, return the documents as a list, sorted based on the importance of a term in a corpus
        return new ArrayList<>(resultsSortedByTFIDF.values());


//        // get only the documents that were searched for
//        List<Document> filteredDocuments = documents.stream()
//                .filter(document -> document.content.toLowerCase().contains(TERM))
//                .toList();
//
//        // fail-fast: if there are no documents or if searched term is not found in document
//        if (CORPUS == 0 || filteredDocuments.size() == 0) {
//            throw new Exception("No documents found");
//        }
//
//        // to handle a map of documents and sort DESC order based on document's key i.e. TF-IDF
//        Map<Double, Document> documentMap = new TreeMap<>(Comparator.reverseOrder());
//
//        for (Document document : filteredDocuments) {
//            /**
//             * calculate TF-IDF
//             * TERM: lower case string of term i.e. search input
//             * invertedIndexImplementation(document): to collect a map of words based on its occurrences in each document
//             * CORPUS: total number of documents in the corpus
//             * filteredDocuments.size(): number of documents where the term appears
//             */
//            double tfIdf = TFIDF.calculateTermFrequencyInverseDocumentFrequency (
//                    TERM,
//                    InvertedIndexImplementation.invertedIndexImplementation(document),
//                    CORPUS,
//                    filteredDocuments.size()
//            );
//
//            // fail-fast: if TF-IDF is 0, then should return list of documents without sorting,
//            // which implies that the term is not very informative as it appears in all documents.
//            if (tfIdf == 0.0) {
//                return new ArrayList<>(filteredDocuments);
//            }
//            //otherwise, add the document to the map of documents
//            documentMap.put(tfIdf, document);
//        }
//        // once all documents have been scanned, return the documents as a list, sorted based on the importance of a term in a corpus
//        return new ArrayList<>(documentMap.values());
    }

    public static void main(String[] args) throws Exception {
        Document doc1 = new Document("the brown fox jumped over the brown dog");
        Document doc2 = new Document("the lazy brown dog sat in the corner");
        Document doc3 = new Document("the red fox bit the lazy dog");
        List<Document> corpus = new ArrayList<>(List.of(doc1, doc2, doc3));
//        List<Document> corpus = new ArrayList<>();
        System.out.println("-----------search for \"brown\"---------------------");
        System.out.println(simpleSearchEngine("brOwn", corpus));
        System.out.println("-----------search for \"fox\"---------------------");
        System.out.println(simpleSearchEngine("FOx", corpus));
        System.out.println("-----------search for \"the\"---------------------");
        System.out.println(simpleSearchEngine("tHe", corpus));
    }
}
