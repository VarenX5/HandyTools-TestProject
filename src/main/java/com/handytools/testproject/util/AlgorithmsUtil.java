package com.handytools.testproject.util;


import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class AlgorithmsUtil {

    //Можно добавить Unit тестирования метода

    /**
     * Реализация алгоритма QuickSelect для нахождения N-ого минимального числа в коллекции.
     *<br/>
     * Алгоритм работает через два элемента: Pivot – случайный элемент в указанном диапазоне и partition – процесс перестановки элементов вокруг выбранного пивота.
     * Пивот выбирается случайно из-за того, что идет высокий разброс по производительности при выборе худшего и лучшего случая.
     *<br/>
     * Выбирается пивот, далее элементы меньше пивота уходят левее, а элементы равные или больше уходят правее,
     * по итогу индекс пивота сдвигается и встает на место на котором он и должен быть, если бы массив был отсортирован.
     * Дальше уже смотрим если индекс пивота равен тому, который был указан, то мы нашли требуемое число, иначе мы смотри где находится наше число, слева или справа и рекурсивно его проверяем.
     *
     * @param list - лист элементов
     * @param number - N-ый номер
     * @return Возвращает N-ое минимальное число в коллекции
     */
    public static int findNthMinimalNumberFromCollection(List<Integer> list, int number) throws IllegalArgumentException {
        if (list.size() <= number) {
            throw new IllegalArgumentException("N-ый номер не должен быть больше или равен количеству элементов в коллекции.");
        }

        return quickSelect(list, 0, list.size() - 1, number);
    }

    private static int quickSelect(List<Integer> list, int left, int right, int number) {
        if (left == right) return list.get(left);
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);

        pivotIndex = partition(list, left, right, pivotIndex);

        if (number == pivotIndex) {
            return list.get(pivotIndex);
        } else if (number < pivotIndex) {
            return quickSelect(list, left, pivotIndex - 1, number);
        } else {
            return quickSelect(list, pivotIndex + 1, right, number);
        }
    }

    private static int partition(List<Integer> list, int left, int right, int pivotIndex) {
        int pivotValue = list.get(pivotIndex);
        Collections.swap(list, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (list.get(i) < pivotValue) {
                Collections.swap(list, storeIndex, i);
                storeIndex++;
            }
        }
        Collections.swap(list, right, storeIndex);
        return storeIndex;
    }
}
