public class Complex {
    private double real;
    private double imag;
    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }
    public double getReal() {
        return real;}

    public double getImag() {
        return imag;
    }
    public Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imag + other.imag);}

    public Complex subtract(Complex other) {
        return new Complex(this.real - other.real, this.imag - other.imag);
    }
    public Complex multiply(Complex other) {
        double resR = this.real * other.real - this.imag * other.imag;
        double resI = this.real * other.imag + this.imag * other.real;
        return new Complex(resR, resI);
    }

    public Complex divide(Complex other) {
        double denominator = other.real * other.real + other.imag * other.imag;
        if (Double.compare(denominator, 0.0) == 0) {
            throw new ArithmeticException("Деление на ноль запрещено");}
        double resR = (this.real * other.real + this.imag * other.imag) / denominator;
        double resI = (this.imag * other.real - this.real * other.imag) / denominator;
        return new Complex(resR, resI);
    }
    public boolean isZero() {
        return Math.abs(real) < 1e-10 && Math.abs(imag) < 1e-10;
    }
    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
    @Override
    public String toString() {
        double r = round(real);
        double i = round(imag);
        if (i >= 0) {
            return r + "+" + i + "i";
        }
        return r + "-" + (-i) + "i";
    }
}