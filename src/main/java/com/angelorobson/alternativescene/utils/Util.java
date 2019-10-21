package com.angelorobson.alternativescene.utils;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class Util {

    public void addPagingRestrictions(TypedQuery<?> query, Pageable pageRequest) {
        int currentPage = pageRequest.getPageNumber();
        int totalRecordsPerPage = pageRequest.getPageSize();
        int firstPageRegistration = currentPage * totalRecordsPerPage;

        query.setFirstResult(firstPageRegistration);
        query.setMaxResults(totalRecordsPerPage);
    }

    public String convertToCurrencyPTBR(Double valor) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return numberFormat.format(valor);
    }

    public String generateAleatoryNumbers() {
        int numero;
        StringBuffer resultado = new StringBuffer();
        int[] num = new int[4];
        Random r = new Random();
        for (int i = 0; i < num.length; i++) {
            numero = r.nextInt(60) + 1;
            for (int j = 0; j < num.length; j++) {
                if (numero == num[j] && j != i) {
                    numero = r.nextInt(60) + 1;
                } else {
                    num[i] = numero;
                }
            }
        }

        for (int i = 0; i < num.length; i++) {
            System.out.print(num[i] + "  ");
            resultado.append(num[i]);
        }

        return resultado.toString();
    }

    public Long calcularDiasRestantes(LocalDate data) {
        LocalDate dataAtual = LocalDate.now();
        return DAYS.between(dataAtual, data);
    }

    public String formatarDataPadraoBrasil(LocalDate data) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        return data.format(formatters);
    }
}