public class ComplexMatrix {
    private int rowCount;
    private int colCount;
    private Complex[][] matrixData;
    public ComplexMatrix(int r, int c) {
        if (r <= 0 || c <= 0) {
            throw new IllegalArgumentException("Неверные размеры матрицы");
        }
        rowCount = r;
        colCount = c;
        matrixData = new Complex[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                matrixData[i][j] = new Complex(0, 0);
            }
        }
    }
    public int getRowCount() { return rowCount; }
    public int getColCount() { return colCount; }
    public Complex get(int r, int c) { return matrixData[r][c]; }
    public void set(int r, int c, Complex val) { matrixData[r][c] = val; }

    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rowCount != other.rowCount || this.colCount != other.colCount) {
            throw new IllegalArgumentException("Размерности матриц не совпадают для сложения");
        }
        ComplexMatrix res = new ComplexMatrix(rowCount, colCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                res.matrixData[i][j] = this.matrixData[i][j].add(other.matrixData[i][j]);
            }}
        return res;}
    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.colCount != other.rowCount) {
            throw new IllegalArgumentException("Несовпадение размерностей: столбцы А != строки B");}
        ComplexMatrix res = new ComplexMatrix(this.rowCount, other.colCount);
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < other.colCount; j++) {
                Complex tempSum = new Complex(0, 0);
                for (int k = 0; k < this.colCount; k++) {
                    tempSum = tempSum.add(this.matrixData[i][k].multiply(other.matrixData[k][j]));}
                res.matrixData[i][j] = tempSum;
            }}
        return res;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix res = new ComplexMatrix(colCount, rowCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                res.matrixData[j][i] = matrixData[i][j];
            }}
        return res;
    }
    private ComplexMatrix getMinor(int skipR, int skipC) {
        ComplexMatrix minorMat = new ComplexMatrix(rowCount - 1, colCount - 1);
        int rIdx = 0;
        for (int i = 0; i < rowCount; i++) {
            if (i == skipR) continue;
            int cIdx = 0;
            for (int j = 0; j < colCount; j++) {
                if (j == skipC) continue;
                minorMat.matrixData[rIdx][cIdx] = matrixData[i][j];
                cIdx++;}
            rIdx++;}
        return minorMat;
    }
    public Complex determinant() {
        if (rowCount != colCount) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }
        if (rowCount == 1) return matrixData[0][0];
        if (rowCount == 2) {
            Complex mainDiag = matrixData[0][0].multiply(matrixData[1][1]);
            Complex secDiag = matrixData[0][1].multiply(matrixData[1][0]);
            return mainDiag.subtract(secDiag);}
        Complex detVal = new Complex(0, 0);
        for (int i = 0; i < rowCount; i++) {
            Complex curTerm = matrixData[i][0].multiply(getMinor(i, 0).determinant());
            if (i % 2 == 0) {
                detVal = detVal.add(curTerm);
            } else {
                detVal = detVal.subtract(curTerm);}
        }
        return detVal;
    }

    public ComplexMatrix inverse() {
        if (rowCount != colCount) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }
        Complex det = determinant();
        if (det.isZero()) {
            throw new ArithmeticException("Определитель равен нулю, обратной матрицы нет");
        }
        ComplexMatrix invMat = new ComplexMatrix(rowCount, colCount);
        if (rowCount == 1) {
            invMat.matrixData[0][0] = new Complex(1, 0).divide(matrixData[0][0]);
            return invMat;
        }
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Complex algDop = getMinor(i, j).determinant();
                if ((i + j) % 2 != 0) {
                    algDop = new Complex(0, 0).subtract(algDop);
                }
                invMat.matrixData[j][i] = algDop.divide(det);
            }}
        return invMat;
    }
    public ComplexMatrix divide(ComplexMatrix other) {
        return this.multiply(other.inverse());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                sb.append(matrixData[i][j]).append("\t");
            }
            sb.append("\n");}
        return sb.toString();
    }}