import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArabianRomanCalc {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine().toUpperCase();
        if (expression.length() < 2) {
            throw new IllegalArgumentException("Строка не является математическо операцией");
        }
        if (reg(expression)) {
            try {

                String[] values = expression.split("[*/+-]");

                if (values.length != 2) {
                    throw new IllegalArgumentException("Выражение должно содержать два операнда и один оператор");
                }

                String strNum1 = values[0].trim();
                String strNum2 = values[1].trim();
                char operator = expression.charAt(strNum1.length());

                int num1, num2;

                if (isRoman(strNum1) && isRoman(strNum2)) {
                    num1 = toArabic(strNum1);
                    num2 = toArabic(strNum2);
                    int result = calculate(num1, num2, operator);
                    if (result < 1) {
                        throw new IllegalArgumentException("Результат должен быть положительным числом в римской системе счисления");
                    }

                    System.out.println("Результат: " + toRoman(result));
                } else if (isArabic(strNum1) && isArabic(strNum2)) {
                    num1 = Integer.parseInt(strNum1);
                    num2 = Integer.parseInt(strNum2);
                    int result = calculate(num1, num2, operator);
                    System.out.println("Результат: " + result);
                } else {
                    throw new IllegalArgumentException("Выражение должно содержать числа одной системы счисления (арабской или римской)");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());

            }

        }
        else {
            System.err.print("Калькулятор работает только с целыми числами");

        }

    }

    private static boolean isArabic(String input) {
        return input.matches("\\d+");
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVXLCDM]+");
    }

    private static int toArabic(String input) {
        int arabic = 0;
        int lastValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int value = mapRomanNumeral(input.charAt(i));

            if (value < lastValue) {
                arabic -= value;
            } else {
                arabic += value;
            }

            lastValue = value;
        }

        return arabic;
    }

    private static String toRoman(int input) {
        if (input < 1 || input > 3999) {
            throw new IllegalArgumentException("Римское число может быть только в диапазоне от 1 до 3999");
        }

        String roman = "";

        for (int i = 0; i < intToRomanMap.length; i++) {
            while (input >= intToRomanMap[i]) {
                roman += romanMap[i];
                input -= intToRomanMap[i];
            }
        }

        return roman;
    }

    private static int[] intToRomanMap = {
            1000, 900, 500, 400,
            100, 90, 50, 40,
            10, 9, 5, 4,
            1
    };

    private static String[] romanMap = {
            "M", "CM", "D", "CD",
            "C", "XC", "L", "XL",
            "X", "IX", "V", "IV",
            "I"
    };

    private static int mapRomanNumeral(char numeral) {
        switch (numeral) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException("Недопустимое римское число: " + numeral);
        }
    }


    private static int calculate(int num1, int num2, char operator) {
        int result;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль недопустимо");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }

        return result;
    }
    public static boolean reg(String S) {
        Pattern p =Pattern.compile("[0-9|(\\+|\\-|\\*|\\/)|IVXLC]+");
        Matcher m = p.matcher(S);

        return m.matches();
    }

}
