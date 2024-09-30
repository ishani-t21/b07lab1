import java.lang.Math;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Polynomial {
	
	//fields
	double[] non_zero_coeff;
	int[] exp;
	

	//constructors
	public Polynomial() {
		non_zero_coeff = new double[]{0};
		exp = new int[]{0};
	}


	public Polynomial(double[] coeff, int[] exponents) {
		int valid_coeff = 0;
		
		for (int i = 0; i < coeff.length; i++) {
			if (coeff[i] != 0) {
				valid_coeff++;
			}
		}
		
		non_zero_coeff = new double[valid_coeff];
		exp = new int[valid_coeff];

		int k = 0;
		for (int i=0; i < coeff.length; i++) {
			if (coeff[i] != 0) {
				non_zero_coeff[k] = coeff[i];
				exp[k] = exponents[i];
				k++;
			}
		}
	}


	public Polynomial(File file) throws IOException{
		Scanner scanner = new Scanner(file);
		String poly_to_read = "";
		
		if (scanner.hasNextLine()) {
			poly_to_read += scanner.nextLine();
		}
		scanner.close();

		String[] terms = poly_to_read.split("(?=[+-])");
		
		non_zero_coeff = new double[terms.length];
        	exp = new int[terms.length];
		double co_val;
		int exp_val;
		
		for (int i = 0; i < terms.length; i++) {
			if (!terms[i].contains("x")) {
				exp_val = 0;
				co_val = Double.parseDouble(terms[i]);
			} else {
				String[] co_exp_part = terms[i].split("x");
				if (co_exp_part.length > 1) {
					exp_val = Integer.parseInt(co_exp_part[1]);
				} else {
					exp_val = 1;
				}
				co_val = Double.parseDouble(co_exp_part[0]);
			}
			
			exp[i] = exp_val;
			non_zero_coeff[i] = co_val;
		}
	}
				

	//other methods
	public Polynomial add(Polynomial p_add) {
		
		int coeff_length = this.non_zero_coeff.length + p_add.non_zero_coeff.length;
		double[] new_coeff = new double[coeff_length];
		int[] new_exp = new int[coeff_length];

		int k = 0;
        
        	for (int i = 0; i < this.non_zero_coeff.length; i++) {
           		new_coeff[k] = this.non_zero_coeff[i];
            		new_exp[k] = this.exp[i];
            		k++;
        	}

		for (int i = 0; i < p_add.non_zero_coeff.length; i++) {
			boolean found = false;
			for (int j = 0; j < k; j++) {
				if (new_exp[j] == p_add.exp[i]) {
					new_coeff[j] += p_add.non_zero_coeff[i];
					found = true;
					break;
				}
			}
			
			if (!found) {
				new_coeff[k] = p_add.non_zero_coeff[i];
				new_exp[k] = p_add.exp[i];
				k++;
			}
		}
		
		return new Polynomial(Arrays.copyOf(new_coeff, k), Arrays.copyOf(new_exp, k));
	}


	public double evaluate(double val_x) {
		double result = 0;
		for (int i=0; i < non_zero_coeff.length; i++) {
			result += non_zero_coeff[i] * Math.pow(val_x, exp[i]);
		}
		return result;
	}


	public boolean hasRoot(double val_root) {
		return (evaluate(val_root) == 0);
	}


	public Polynomial multiply(Polynomial p_mult) {
    		double[] temp_coeff = new double[this.non_zero_coeff.length * p_mult.non_zero_coeff.length];
    		int[] temp_exp = new int[this.non_zero_coeff.length * p_mult.non_zero_coeff.length];

    		int k = 0;
		
		for (int i = 0; i < this.non_zero_coeff.length; i++) {
			for (int j = 0; j < p_mult.non_zero_coeff.length; j++) {
				temp_coeff[k] = this.non_zero_coeff[i] * p_mult.non_zero_coeff[j];
				temp_exp[k] = this.exp[i] + p_mult.exp[j];
				k++;
			}
		}

		double[] new_coeff = new double[k];
		int[] new_exp = new int[k];
		
		int result_index = 0;
		
		for (int i = 0; i < k; i++) {
			if (temp_coeff[i] != 0) {
				boolean found = false;
				
				for (int j = 0; j < result_index; j++) {
					if (new_exp[j] == temp_exp[i]) {
						new_coeff[j] += temp_coeff[i]; 
						found = true;
						break;
					}
				}
				
				if (!found) {
					new_coeff[result_index] = temp_coeff[i];
					new_exp[result_index] = temp_exp[i];
					result_index++;
				}
			}
		}
		
		int count = 0;

		for (int i = 0; i < new_coeff.length; i++) {
			if (new_coeff[i] != 0) {
				count++;
			}
		}
		
		double[] final_coeff = new double[count];
		int[] final_exp = new int[count];
		
		int m = 0;
		for (int i = 0; i < result_index; i++) {
			if (new_coeff[i] != 0) {
				final_coeff[m] = new_coeff[i];
				m++;
			}
		}
		
		return new Polynomial(final_coeff, final_exp);
				
	}


	public void saveToFile(String file_name) throws IOException {
		FileWriter output = new FileWriter(file_name);
		String poly_to_write = "";
		
		for (int i = 0; i < non_zero_coeff.length; i++) {
			double coeff = non_zero_coeff[i];
			int exponent = exp[i];

			if (i > 0 && coeff > 0) {
				poly_to_write += "+";
			}
			
			if (exponent == 0) {
				poly_to_write += coeff;
			} else if (exponent == 1) {
				poly_to_write += coeff + "x";
			} else {
				poly_to_write += coeff + "x" + exponent;
			}	
			
		}
		
		output.write(poly_to_write);
		output.close();
        }

}