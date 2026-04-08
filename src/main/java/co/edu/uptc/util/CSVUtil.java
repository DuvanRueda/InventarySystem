package co.edu.uptc.util;

import java.io.FileReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class CSVUtil {

    public static <T> void exportToCsv(String filePath, List<T> data) {
        ensureDirectoryExists(filePath);
        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath))) {
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(',')
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> importFromCsv(String filePath, Class<T> type) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) return Collections.emptyList();
        try (FileReader reader = new FileReader(filePath)) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static void ensureDirectoryExists(String filePath) {
        Path parent = Paths.get(filePath).getParent();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
