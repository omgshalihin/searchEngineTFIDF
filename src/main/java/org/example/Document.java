package org.example;


import javax.print.Doc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Document {

    public String content;
    public int corpus;
    public String term;
    public Map<String, Integer> termWeightMap = new HashMap<>();

    public static Map<String, Map<String, Integer>> contentAndTermWeightMap = new HashMap<>();

    public Document(String content) {
        this.content = content;
        for (String word : content.toLowerCase().split(" ")) {
            Integer wordCount = termWeightMap.get(word);
            termWeightMap.put(word, (wordCount == null) ? 1 : wordCount + 1);
        }
        contentAndTermWeightMap.put(content, termWeightMap);
    }

    public Document(int corpus, String term) {
        this.corpus = corpus;
        this.term = term;
    }

    public int getCorpus() {
        return corpus;
    }

    public void setCorpus(int corpus) {
        this.corpus = corpus;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Document document = (Document) o;
//        return content.equals(document.content);
//    }
//
//    @Override
//    public int hashCode() {
//        return Integer.parseInt(this.content = content);
//    }
//
//    @Override
//    public String toString() {
//        return "Document{" +
//                "content='" + content + '\'' +
//                '}';
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return corpus == document.corpus && term.equals(document.term) && content.equals(document.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(corpus, term, content, termWeightMap, contentAndTermWeightMap);
    }

    @Override
    public String toString() {
        return "Document{" +
                "content='" + content + '\'' +
                '}';
    }
}
