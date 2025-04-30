package com.handytools.testproject.service;

import com.handytools.testproject.util.AlgorithmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExcelServiceBean implements ExcelService {

    @Override
    public Integer getMinimalNumberFromFile(String pathToFile, int number) throws RuntimeException {
        if (number < 0) {
            throw new IllegalArgumentException("Число не должно быть меньше 0");
        }
        if (!pathToFile.endsWith(".xlsx")) {
            throw new IllegalArgumentException("Формат файла не .xlsx");
        }

        Path path = Paths.get(pathToFile);

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Файл не найден: " + path);
        }

        try (InputStream in = Files.newInputStream(path);
             Workbook workbook = new XSSFWorkbook(in)) {
            Sheet sheet = workbook.getSheetAt(0);

            //Выносим цифры из первого столбца в коллекцию, так как находить N-ое число будем через алгоритм QuickSelect,
            //который в свою очередь может поменять местами часть элементов в коллекции, так как логика работы наследуется от QuickSort.
            List<Integer> firstColumnIntegersList = new ArrayList<>();

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        firstColumnIntegersList.add((int) cell.getNumericCellValue());
                    } else if (cell.getCellType() == CellType.STRING) {
                        firstColumnIntegersList.add(Integer.parseInt(cell.getStringCellValue()));
                    }
                }
            }
            return AlgorithmsUtil.findNthMinimalNumberFromCollection(firstColumnIntegersList, number);
        } catch (IOException e) {
            log.error("Ошибка во время чтения файла.", e);
            throw new RuntimeException("Ошибка во время чтения файла.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Формат числа в первом столбце не соответствует целому.");
        }
    }
}
