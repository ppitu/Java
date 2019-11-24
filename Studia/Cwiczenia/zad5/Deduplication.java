import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Deduplication implements DeduplicationInterface {
    private Map<Integer, String> dictionary = new HashMap<>();
    private List<String> sentences = new ArrayList<>();
    private Map<String, Integer> wordOccurrences = new HashMap<>();

    @Override
    public Map<Integer, String> getDictionary() {
        return dictionary;
    }

    @Override
    public String decode(Map<Integer, String> dictionary, String toDecode) {
        for (Integer key : dictionary.keySet()) {
            toDecode = toDecode.replace("#" + key, dictionary.get(key));
        }
        return toDecode;
    }

    @Override
    public int addString(String newString) {
        String regex = "^(\\p{Punct}*)(\\p{L}+)(\\p{Punct}*)$";
        Pattern pattern = Pattern.compile(regex);

        String[] possibleWords = newString.split(" ");
        Arrays.stream(possibleWords)
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(word -> word.group(2))
                .forEach(word -> {
                    if (word.length() > 2) {
                        Integer value = wordOccurrences.getOrDefault(word, 0);
                        value++;
                        wordOccurrences.put(word, value);
                        if (value >= 3) {
                            Integer val = getKeysByValue(dictionary, word);
                            if (val == null) {
                                dictionary.put(dictionary.size() + 1, word);
                            }
                        }
                    }
                });
        sentences.add(newString);

        sentences = sentences.stream()
                .map(sentence ->
                        Arrays.stream(sentence.split(" "))
                                .map(word -> {
                                    Matcher m = pattern.matcher(word);
                                    if (m.find()) {
                                        String punctuationPrefix = m.group(1);
                                        String wordWithoutPunctuation = m.group(2);
                                        String punctuationSuffix = m.group(3);
                                        Integer count = wordOccurrences.get(wordWithoutPunctuation);
                                        if (count != null && count >= 3) {
                                            return punctuationPrefix + "#" + getKeysByValue(dictionary, wordWithoutPunctuation) + punctuationSuffix;
                                        }
                                    }
                                    return word;
                                })
                                .collect(Collectors.joining(" "))
                )
                .collect(Collectors.toList());

        return sentences.size();
    }

    @Override
    public String getString(int id) {
        return sentences.get(id - 1);
    }

    private Integer getKeysByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    // FOR TESTS ONLY
    Map<String, Integer> getWordOccurrences() {
        return wordOccurrences;
    }

    // FOR TESTS ONLY
    List<String> getSentences() {
        return sentences;
    }
}
