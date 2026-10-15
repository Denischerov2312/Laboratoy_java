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
    public static ComplexMatrix[] chooseOperands(Scanner sc, ComplexMatrix lastResult, boolean binary) {
        if (lastResult == null) {
            ComplexMatrix a = inputMatrix(sc, "A");
            ComplexMatrix b = binary ? inputMatrix(sc, "B") : null;
            return new ComplexMatrix[]{a, b};}
        System.out.println("Результат прошлой операции:\n" + lastResult);
        if (binary) {
            System.out.println("1 - Ввести новые данные двух матриц");
            System.out.println("2 - Использовать старую матрицу как A, ввести новую B");
            System.out.println("3 - Ввести новую A, использовать старую матрицу как B");
        } else {
            System.out.println("1 - Ввести новую матрицу A");
            System.out.println("2 - Использовать старую матрицу");}
        System.out.print("Ваш выбор: ");
        int choice = sc.nextInt();
        if (choice == 2) {
            ComplexMatrix b = binary ? inputMatrix(sc, "B") : null;
            return new ComplexMatrix[]{lastResult, b};}
        if (choice == 3 && binary) {
            return new ComplexMatrix[]{inputMatrix(sc, "A"), lastResult};}
        if (choice != 1) {
            System.out.println("Неверный выбор, вводим новые данные.");}
        ComplexMatrix a = inputMatrix(sc, "A");
        ComplexMatrix b = binary ? inputMatrix(sc, "B") : null;
        return new ComplexMatrix[]{a, b};}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);
        boolean running = true;
        ComplexMatrix lastResult = null;
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
                    case 1: {
                        ComplexMatrix[] m = chooseOperands(sc, lastResult, true);
                        lastResult = m[0].add(m[1]);
                        System.out.println("Сумма:");
                        System.out.println(lastResult);
                        break;
                    }
                    case 2: {
                        ComplexMatrix[] m = chooseOperands(sc, lastResult, true);
                        lastResult = m[0].multiply(m[1]);
                        System.out.println("Произведение:");
                        System.out.println(lastResult);
                        break;
                    }
                    case 3: {
                        ComplexMatrix[] m = chooseOperands(sc, lastResult, true);
                        lastResult = m[0].divide(m[1]);
                        System.out.println("Частное:");
                        System.out.println(lastResult);
                        break;
                    }
                    case 4: {
                        ComplexMatrix[] m = chooseOperands(sc, lastResult, false);
                        lastResult = m[0].transpose();
                        System.out.println("Результат транспонирования:");
                        System.out.println(lastResult);
                        break;
                    }
                    case 5: {
                        ComplexMatrix[] m = chooseOperands(sc, lastResult, false);
                        System.out.println("Det: " + m[0].determinant());
                        break;
                    }
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