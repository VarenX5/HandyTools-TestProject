package com.handytools.testproject.controller;

import com.handytools.testproject.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/excel")
@Tag(name = "ExcelController", description = "Работа со всеми Excel операциями")
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelService excelService;

    @GetMapping("/file/nth-minimal-number")
    @Operation(
            summary = "Находит локальный файл формата .xlsx и находит N-ное минимальное число из первого столбца. " +
                    "Диапазон от 0 до ко-во чисел - 1.",
            description = "Найти N-ное минимальное число."
    )
    @ApiResponse(responseCode = "200", description = "Успех")
    @ApiResponse(responseCode = "400", description = "Неверно указано имя; Формат файла не .xlsx; Первый столбец пуст; " +
            "Формат числа в Excel не соответствует целому числу;")
    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    public ResponseEntity<String> findNthMinimalNumber(@Parameter(description = "Полный путь к файлу формата xlsx.")
                                                       String pathToFile,
                                                       @Parameter(description = "N-ое минимальное число")
                                                       int number) {

        /*
         В идеале нужно создать общий ExceptionHandler, который будет обрабатывать ошибки во время выполнения методов контроллера,
         в ином случае придется обрабатывать ошибки в каждом методе, а это лишний код.

         И возвращать ошибки лучше с каким-нибудь ExceptionDTO, в котором будет указан код, время и краткое описание ошибки.
         */
        try {
            return ResponseEntity.ok(excelService.getMinimalNumberFromFile(pathToFile, number).toString());
        } catch (IllegalArgumentException e) {
            log.error("Ошибка во время нахождения N-ого минимального числа", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
