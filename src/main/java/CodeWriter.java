import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CodeWriter {
    private FileWriter fileWriter;

    public CodeWriter(String filePath) {
        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeArithmetic(Map<Integer, String> command) {

    }

    public void writePushPop(Map<Integer, String> command) {

    }

    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
