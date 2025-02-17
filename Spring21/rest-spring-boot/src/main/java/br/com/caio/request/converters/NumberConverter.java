package br.com.caio.request.converters;

public class NumberConverter {

    public NumberConverter() {
    }

    public static Double convertToDouble(String strNumber) throws IllegalArgumentException {
        if (strNumber == null || strNumber.isEmpty()) throw new UnsupportedOperationException("Please set a numeric value");
        String number = strNumber.replace(",", "."); // convert R$ 5,00 to 5.00

        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", "."); // convert R$ 5,00 to 5.00
        return (number.matches("[-+]?[0-9]*\\.?[0-9]+")); // if is numeric
    }
}
