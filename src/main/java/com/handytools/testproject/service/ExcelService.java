package com.handytools.testproject.service;

public interface ExcelService {
    /**
     * Метод находит локальный файл формата .xlsx, считывает целые числа из первого столбца
     * и находит N-ое минимальное число.
     * @param pathToFile - путь к файлу
     * @param number - N-ый номер
     * @return N-ое минимальное число
     * @throws RuntimeException Если возникла ошибка во время чтения файла
     */
    Integer getMinimalNumberFromFile(String pathToFile, int number) throws RuntimeException;
}
