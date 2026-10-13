import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static ComplexMatrix inputMatrix(Scanner sc, String label) {
        System.out.println("Ввод параметров для матрицы " + label);
        System.out.print("Строк: ");
        int r = sc.nextInt();
        System.out.print("Столбцов: ");
        int c = sc.nextInt();
        ComplexMatrix mat = new ComplexMatrix(r, c);
        System.out.println("Введите элементы (вещ. и мним. часть через пробел):");
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(label + "[" + (i + 1)+ "][" + (j +1) +"] = ");
                double realPart = sc.nextDouble();
                double imagPart = sc.nextDouble();
                mat.set(i, j, new Complex(realPart, imagPart));
            }}
        System.out.println("Загружена матрица " + label + ":\n" + mat);
        return mat;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);
        boolean running = true;
        while (running) {
            System.out.println("\n--- Операции над комплексными матрицами ---");
            System.out.println("1 - Сложение");
            System.out.println("2 - Умножение");
            System.out.println("3 - Деление (A * B^-1)");
            System.out.println("4 - Транспонирование");
            System.out.println("5 - Вычисление определителя");
            System.out.println("0 - Выйти");
            System.out.print("Ваш выбор: ");
            try {
                int option = sc.nextInt();
                switch (option) {
                    case 0:
                        System.out.println("Завершение работы.");
                        running = false;
                        break;
                    case 1:
                        System.out.println("Сумма:");
                        System.out.println(inputMatrix(sc, "A").add(inputMatrix(sc, "B")));
                        break;
                    case 2:
                        System.out.println("Произведение:");
                        System.out.println(inputMatrix(sc, "A").multiply(inputMatrix(sc, "B")));
                        break;
                    case 3:
                        System.out.println("Частное:");
                        System.out.println(inputMatrix(sc, "A").divide(inputMatrix(sc, "B")));
                        break;
                    case 4:
                        System.out.println("Результат транспонирования:");
                        System.out.println(inputMatrix(sc, "A").transpose());
                        break;
                    case 5:
                        System.out.println("Det: " + inputMatrix(sc, "A").determinant());
                        break;
                    default:
                        System.out.println("Неверная команда.");
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Ошибка ввода. Ожидалось число.");
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Произошла ошибка: " + ex.getMessage());
            }}
        sc.close();
}
}