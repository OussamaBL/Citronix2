package com.citronix.citronix.Util;

import com.citronix.citronix.domain.Enum.Saison;
import com.citronix.citronix.exception.SaisonInvalidException;

import java.time.LocalDateTime;
import java.time.Month;

public class SeasonUtils {
    public static Saison seasonFromDate(LocalDateTime date) {
        Month month = date.getMonth();

        switch (month) {
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                return Saison.WINTER;
            case MARCH:
            case APRIL:
            case MAY:
                return Saison.SPRING;
            case JUNE:
            case JULY:
            case AUGUST:
                return Saison.SUMMER;
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
                return Saison.AUTUMN;
            default:
                throw new SaisonInvalidException("Invalid month in date: " + date);
        }
    }
}
