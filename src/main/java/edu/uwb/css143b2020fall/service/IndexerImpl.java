package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class IndexerImpl implements Indexer {
    private static List<List<Integer>> makeDoubleList(int size) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i, new ArrayList<>());
        }
        return list;
    }

    public Map<String, List<List<Integer>>> index(List<String> docs) {
        Map<String, List<List<Integer>>> indexes = new HashMap<>();
        for (int i = 0; i < docs.size(); i++) {
            String doc = docs.get(i).trim();
            if (doc.isEmpty()) {
                return indexes;
            }
            String[] words = doc.split("\\s+");
            for (int j = 0; j < words.length; j++) {
                if (indexes.containsKey(words[j])) {
                    indexes.get(words[j]).get(i).add(j);
                } else {
                    indexes.put(words[j], makeDoubleList(docs.size()));
                    indexes.get(words[j]).get(i).add(j);
                }
            }
        }
        return indexes;
    }
}