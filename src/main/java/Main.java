import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }

        final File indexableDirectory = new File(args[0]);
        // Get all filenames
        File[] listOfFiles = indexableDirectory.listFiles();
        // Read each file content and save it to a Map
        Map<String, String> contents = new HashMap<>();
        // A map for final result
        Map<String, Integer> ranking = new HashMap<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                Path filename = Path.of(String.valueOf(listOfFiles[i]));
                try {
                    String content = Files.readString(filename);
                    contents.put(listOfFiles[i].getName(), content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.print("search>") ;
            final String line = keyboard.nextLine();
            if(line.equals(":quit")) {
                break;
            }
            findTheWord(line, contents, ranking);
            // Sort ranking
            Map<String, Integer> sortedRanking = sortRanking(ranking);
            // Print ranking result
            printRanking(sortedRanking);
        }
    }

    private static void findTheWord(String keyword, Map<String, String> contents, Map<String, Integer> ranking) {
        for(Map.Entry<String, String> item : contents.entrySet()) {
            // If the keyword is in the string, then ranking is 100%
            if (item.getValue().contains(keyword)) {
                ranking.put(item.getKey(), 100);
            } else {
                // Otherwise, we will return another format of ranking
                findByEachWord(keyword, contents, ranking);
            }
        }
    }

    private static void findByEachWord(String keyword, Map<String, String> contents, Map<String, Integer> ranking) {
        // For each matched word, we will add ranking 100/amount%
        // Get the numbers of words
        String[] words = keyword.split("\s");
        int amount = words.length;
        int rankSum = 0;
        int i = 0;
        for(Map.Entry<String, String> item : contents.entrySet()) {
            // Check each word in the keyword
            for(String s : words){
                if(item.getValue().contains(s)) {
                    rankSum += 100/amount;
                    i++;
                }
            }
            if (i == amount) {
                // If all words are matched, give rank score 100
                ranking.put(item.getKey(), 100);
            } else {
                ranking.put(item.getKey(), rankSum);
            }
            rankSum = 0;
            i = 0;
        }
    }

    private static Map<String, Integer> sortRanking(Map<String, Integer> ranking) {
        return ranking.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static void printRanking(Map<String, Integer> ranking) {
        int i = 0;
        int j = 0;
        for(Map.Entry<String, Integer> item : ranking.entrySet()) {
            System.out.println(item.getKey() + ": " + item.getValue() + "%");
            if (item.getValue() == 0) {
                i++;
            }
            j++;
            // Print the top 10 rank scores
            if (j == 10) {
                break;
            }
        }
        if (i == ranking.size()) {
            System.out.println("no matches found");
        }
    }
}
