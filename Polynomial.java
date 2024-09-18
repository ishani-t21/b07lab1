import java.lang.Math;

public class Polynomial {
	
	//fields
	double[] coefficients;
	
	//constructors
	public Polynomial() {
		coefficients = new double[]{0};
	}

	public Polynomial(double[] coeff) {
		coefficients = new double[coeff.length];
		for (int i=0; i < coeff.length; i++) {
			coefficients[i] = coeff[i];
		}
	}
	
	//other methods
	public Polynomial add(Polynomial p_add) {
		int max_length = Math.max(this.coefficients.length, p_add.coefficients.length);
		double[] new_coeff = new double[max_length];
		
		for (int i=0; i < this.coefficients.length; i++) {
			new_coeff[i] = this.coefficients[i];
		}

		for (int i=0; i < p_add.coefficients.length; i++) {
			new_coeff[i] += p_add.coefficients[i];
		}
		
		return new Polynomial(new_coeff);
	}

	public double evaluate(double val_x) {
		double result = 0;
		for (int i=0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(val_x, i);
		}
		return result;
	}

	public boolean hasRoot(double val_root) {
		return (evaluate(val_root) == 0);
	}

}