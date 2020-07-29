package converter;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            int sourceRadix = Integer.parseInt(scanner.nextLine());
            String number = scanner.nextLine();
            String decnum = convert(number, sourceRadix, 10);
            int destinationRadix = Integer.parseInt(scanner.nextLine());
            if (destinationRadix < 1 || destinationRadix > 36) {
                throw new NumberFormatException("bad radix " + destinationRadix);
            }
            System.out.println(convert(number, sourceRadix, destinationRadix));
        } catch (NumberFormatException e) {
            System.out.println("error");
        }

    }

    private static String convert(String number, int sourceRadix, int destinationRadix) throws NumberFormatException {
        String[] parts = number.split("\\.");
        parts[0] = convertIntPart(parts[0], sourceRadix, destinationRadix);
        String fracPart = "";
        if (parts.length > 1) {
            double fracPart10 = convertFracPartToDecimal(parts[1], sourceRadix);
            fracPart = convertBase10FracPart(fracPart10, destinationRadix);
            return parts[0] + "." + fracPart;
        } else {
            return parts[0];
        }
    }

    private static String convertBase10FracPart(double fracPart10, int destinationRadix) {
        StringBuilder sb = new StringBuilder();
        while (fracPart10 > 0.000001 && sb.length() < 5) {
            int digit = (int) (fracPart10 * destinationRadix);
            sb.append(convertIntPart(String.valueOf(digit), 10, destinationRadix));
            fracPart10 = fracPart10 * destinationRadix - digit;
        }
        return sb.toString();
    }

    private static double convertFracPartToDecimal(String number, int sourceRadix) {
        double value = 0.0;
        if (sourceRadix == 10) {
            return Double.valueOf(number) / Math.pow(10, number.length());
        }
        for (int i = 0; i < number.length(); i++) {
            value += Double.valueOf(
                    convertIntPart(String.valueOf(number.charAt(i)),
                            sourceRadix,
                            10)) / Math.pow(sourceRadix, i + 1);

        }
        return value;
    }

    private static String convertIntPart(String number, int sourceRadix, int destinationRadix) throws NumberFormatException {
        int num;
        if (sourceRadix == 1) {
            num = number.length();
        } else {
            num = Integer.parseInt(number, sourceRadix);
        }
        if (destinationRadix == 1) {
            char[] str = new char[num];
            Arrays.fill(str, '1');
            return new String(str);
        } else {
            return Long.toString(num, destinationRadix);
        }
    }
}
