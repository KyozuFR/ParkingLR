package lru.linmul;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSV {
    private CSVReader reader;
    private CSVWriter writer;

    public CSV(String filePath) {
        try {
            this.reader = new CSVReader(new FileReader(filePath));
            this.writer = new CSVWriter(new FileWriter(filePath, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String[]> readAll() {
        try {
            List<String[]> allRows = reader.readAll();
            return allRows.subList(1, allRows.size());
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeAll(List<String[]> data) {
        try {
            writer.writeAll(data);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
