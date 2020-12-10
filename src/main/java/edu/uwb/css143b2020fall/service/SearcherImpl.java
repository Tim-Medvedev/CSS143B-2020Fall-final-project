package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.*;

/*
DO NOT CHANGE
 */

@Service
public class SearcherImpl implements Searcher {
    public List<Integer> search(String keyPhrase, Map<String, List<List<Integer>>> index) {
        List<Integer> result = new ArrayList<>();


        if (keyPhrase.equals("")) {
            return result;
        }
        keyPhrase = keyPhrase.trim();
        String[] words = keyPhrase.split("\\s+");
        List<List<Integer>> list = new ArrayList<>();


        //nested for loops for adding all docs which contain at least one of the user entered search words
        for (int userWord = 0; userWord < words.length; userWord++) {
            List<Integer> docList = new ArrayList<>();
            if (index.containsKey(words[userWord])) {
                for (int j = 0; j < index.get(words[userWord]).size(); j++) {
                    if (index.get(words[userWord]).get(j).isEmpty()){
                        continue;
                    } else {
                        docList.add(j);
                    }
                }
                list.add(docList);
            } else {
                list.add(docList);
                continue;
            }
        }

        //loops to find all docs that contain ALL of the user entered search words.
        Set<Integer> intersectionSet = new HashSet<>(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < list.get(i).size(); j++) {
                set.add(list.get(i).get(j));
            }
            intersectionSet.retainAll(set);
        }
        List<Integer> commonDocs = new ArrayList<>(intersectionSet);
        if (commonDocs.isEmpty()) {
            return result;
        }
        //verify word positions in documents.

        //put word positions from index into hashtable where documents from commonDocs are the keys
        Map<Integer, List<List<Integer>>> wordPositions = new HashMap<>();
        for (int i = 0; i < commonDocs.size(); i++) {
            wordPositions.put(commonDocs.get(i), new ArrayList<>());
        }

        for (int i = 0; i < commonDocs.size(); i++) {
            for (int j = 0; j < words.length; j++) {
                if (index.containsKey(words[j])) {
                    for (int k = 0; k < index.get(words[j]).size(); k++) {
                        if (k == commonDocs.get(i)) {
                            wordPositions.get(commonDocs.get(i)).add(index.get(words[j]).get(k));
                        }
                    }
                }
            }
        }


        for (int i = 0; i < commonDocs.size(); i++) {
            List<List<Integer>> finalSet = new ArrayList<>();
            List<List<Integer>> wordPlaces = new ArrayList<>(wordPositions.get(commonDocs.get(i)));
            for (int j = 0; j < wordPlaces.size(); j++) {
                List<Integer> word = wordPlaces.get(j);
                Integer[] wordCopy = new Integer[word.size()];
                for (int n = 0; n < wordCopy.length; n++) {
                    wordCopy[n] = word.get(n);
                }
                for (int k = 0; k < wordCopy.length; k++) {
                    wordCopy[k] = wordCopy[k] - j;
                }
                List<Integer> intList = new ArrayList<Integer>(wordCopy.length);
                for (int l : wordCopy) {
                    intList.add(l);
                }
                finalSet.add(intList);
            }


            Set<Integer> finalIntersection = new HashSet<>(finalSet.get(0));
            for (int p = 1; p < finalSet.size(); p++) {
                Set<Integer> set = new HashSet<>(finalSet.get(p));
                for (int g = 0; g < finalSet.get(p).size(); g++) {
                    set.add(finalSet.get(p).get(g));
                }
                finalIntersection.retainAll(set);
            }

            List<Integer> finalList = new ArrayList<>(finalIntersection);

            if (finalList.isEmpty()) {
                continue;
            } else {
                result.add(commonDocs.get(i));
            }

        }


        return result;
        }



}