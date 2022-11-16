import info.debatty.java.stringsimilarity.Levenshtein;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class OperationWithInputList {
    String commonString = null;
    String[] arrayStringFirst;
    String[] arrayStringSecond;

    List<Integer> listNumbersInInputFile = new ArrayList<>();
    Map<String, String> resultMap = new HashMap<>();


    public List<String> getInputFileWordList(String inputFilePath) {
        File inputFile = new File(inputFilePath);
        List<String> inputFileWordList = new ArrayList<>();
        try {
            Scanner scannerInputFile = new Scanner(inputFile);
            while (scannerInputFile.hasNextLine()) {
                String wordLine = scannerInputFile.nextLine();
                if (StringUtils.isNotEmpty(wordLine)) {
                    inputFileWordList.add(wordLine);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Что-то опять не так " + e);
        }

        return inputFileWordList;
    }


    public List<String> getListWithoutAndWithNumbersInInputFile(List<String> inputFileWordList) {
        List<String> listWithoutNumbers = new ArrayList<>();
        for (String wordLine : inputFileWordList) {
            if (isNumeric(wordLine)) {
                listNumbersInInputFile.add(Integer.parseInt(wordLine));
            } else {
                listWithoutNumbers.add(wordLine.toLowerCase());
            }
        }
        return listWithoutNumbers;
    }


    public List<String[]> getListArrayContainsWord(List<String> listWithoutNumbers) {
        List<String[]> listArrayContainsWords = new ArrayList<>();
        for (String wordLine : listWithoutNumbers) {
            String[] arrayWords = wordLine.split(" ");
            listArrayContainsWords.add(arrayWords);
        }
        return listArrayContainsWords;
    }

    public Map<String, String> createInitialMap(List<String[]> listArrayWords) {
        Map<String, String> initialMap = new HashMap<>();
        for (int i = 0; i < listNumbersInInputFile.get(0); i++) {
            String[] arrayString = listArrayWords.get(i);
            String firstString = String.join(" ", arrayString);
            initialMap.put(Arrays.toString(arrayString), firstString + ":?");
        }
        return initialMap;

    }

    public Map<String, String> getResultMap(List<String[]> listArrayContainsWords, Map<String, String> initialMap) {
        resultMap = initialMap;
        for (int i = 0; i < listNumbersInInputFile.get(0); i++) {
            arrayStringFirst = listArrayContainsWords.get(i);
            commonString = null;
            for (int j = listNumbersInInputFile.get(0); j < listArrayContainsWords.size(); j++) {
                if (!resultMap.containsValue(commonString)) {
                    arrayStringSecond = listArrayContainsWords.get(j);
                    compareWord();
                }
            }
        }
        return resultMap;
    }

    private void compareWord() {
        for (int i = 0; i < arrayStringFirst.length; i++) {
            if (i < arrayStringSecond.length) {
                for (int j = i; j < arrayStringSecond.length; j++) {
                    if (listNumbersInInputFile.get(0).equals(listNumbersInInputFile.get(1)) &&
                            listNumbersInInputFile.get(0) == 1) {
                        createCommonString();
                        resultMap.put(Arrays.toString(arrayStringFirst), commonString);

                    } else {
                        Levenshtein levenshtein = new Levenshtein();
                        double indexLevenshteina = levenshtein.distance(arrayStringFirst[i], arrayStringSecond[j]);
                        if (indexLevenshteina < 3) {
                            createCommonString();
                            resultMap.put(Arrays.toString(arrayStringFirst), commonString);

                        }

                    }
                }
            }
        }

    }

    private void createCommonString() {
        String firstString = String.join(" ", arrayStringFirst);
        String secondString = String.join(" ", arrayStringSecond);
        commonString = firstString + ":" +
                secondString;

    }

    public List<String> getResultList(Map<String, String> resultMap) {
        List<String> resultList = new ArrayList<>();

        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            resultList.add(entry.getValue());
        }
        return resultList;
    }


}

