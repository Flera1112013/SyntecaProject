import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class Operation {
    public void operateWithFile(){
        String inputFilePath = "src/main/resources/inputFile.txt";
        OperationWithInputList operationWithInputList = new OperationWithInputList();
        List<String> wordLineList = operationWithInputList.getInputFileWordList(inputFilePath);
        List<String> listWithoutAndWithNumbers = operationWithInputList.getListWithoutAndWithNumbersInInputFile(wordLineList);
        List<String[]> listArrayContainsWord = operationWithInputList.getListArrayContainsWord(listWithoutAndWithNumbers);
        Map<String, String> initialMap = operationWithInputList.createInitialMap(listArrayContainsWord);
        operationWithInputList.getResultMap(listArrayContainsWord, initialMap);
        List<String> resultList = operationWithInputList.getResultList(operationWithInputList.resultMap);
        operateWithOutputFile(resultList);

    }

    public void operateWithOutputFile( List<String> resultList){
        try (FileOutputStream fos = new FileOutputStream("outputFile.txt");
             PrintStream printStream = new PrintStream(fos)) {
            for (String s : resultList) {
                printStream.println(s);
            }
            System.out.println("Запись в файл произведена");
        } catch (IOException ex) {
            System.out.println("что-то пошло не так " + ex);

            System.out.println(ex.getMessage());
        }
    }

}
