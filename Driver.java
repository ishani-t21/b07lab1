import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		
		// Test Case 1
		double [] co_1 = {-3, 5, -2};
		int [] exp_1 = {4, 2, 0};
		Polynomial p1 = new Polynomial(co_1, exp_1);
		System.out.println("p1 evaluated at x=3: " + p1.evaluate(3));

		double [] co_2 = {3, -5, 2};
		int [] exp_2 = {4, 2, 0};
		Polynomial p2 = new Polynomial(co_2, exp_2);

		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));

		Polynomial m = p1.multiply(p2);
		System.out.println("Product of p1 and p2 evaluated at x=2: " + m.evaluate(2));
		
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		
		// Adding a polynomial to a file
    		String file_name = "polynomial_p1.txt";
    		try {
        		p1.saveToFile(file_name);
        		System.out.println("p1 saved to " + file_name);
    		} catch (IOException e) {
        		System.out.println("Error saving p1: " + e.getMessage());
    		}
		
		// Reading from file
    		try {
        		File file = new File(file_name);
        		Polynomial pFromFile = new Polynomial(file);
        		System.out.println("Polynomial read from file evaluated at x=2: " + pFromFile.evaluate(2));
    		} catch (FileNotFoundException e) {
        		System.out.println("File not found: " + e.getMessage());
    		} catch (IOException e) {
        		System.out.println("Error reading polynomial from file: " + e.getMessage());
    		}
		
		// Test Case 2: Polynomials with zero coefficients
		double[] co_3 = {0, 0, 0};
		int[] exp_3 = {0, 1, 2};
		Polynomial p3 = new Polynomial(co_3, exp_3);
		System.out.println("p3 evaluated at x=5: " + p3.evaluate(5)); // Should be 0

		String file_name_0_co = "polynomial_0_co.txt";
		try {
    			p3.saveToFile(file_name_0_co);
    			System.out.println("Zero coefficient polynomial saved to " + file_name_0_co);
		} catch (IOException e) {
    			System.out.println("Error with zero coefficient polynomial: " + e.getMessage());
		}

		// Test Case 4: Polynomial with negative coefficients
    		double[] co_5 = {-1, -2};
    		int[] exp_5 = {1, 0};
    		Polynomial p5 = new Polynomial(co_5, exp_5);
    		System.out.println("p5 evaluated at x=1: " + p5.evaluate(1)); // Should be -3

    		String file_name_neg_co = "polynomial_neg_co.txt";
    		try {
        		p5.saveToFile(file_name_neg_co);
        		File fileNegCo = new File(file_name_neg_co);
        		Polynomial pFromFileNegCo = new Polynomial(fileNegCo);
        		System.out.println("Single term polynomial read from file evaluated at x=1: " + pFromFileNegCo.evaluate(1)); // Should be -3
    		} catch (IOException e) {
        		System.out.println("Error with negative coefficient polynomial: " + e.getMessage());
    		}

		// Test Case 5: Testing with decimal and whole coefficients and decimal argument for x
    		double[] co_6 = {0.5, 1.35, 2, 3};
    		int[] exp_6 = {7, 3, 5, 2};
    		Polynomial p6 = new Polynomial(co_6, exp_6);
    		System.out.println("p6 evaluated at x=4.5: " + p6.evaluate(4.5)); // Should be 45257.4435

    		String file_name_dec_co = "polynomial_dec_co.txt";
    		try {
        		p6.saveToFile(file_name_dec_co);
        		File fileDecCo = new File(file_name_dec_co);
        		Polynomial pFromFileDecCo = new Polynomial(fileDecCo);
        		System.out.println("Single term polynomial read from file evaluated at x=4.5: " + pFromFileDecCo.evaluate(4.5)); // Should be 45257.4435
    		} catch (IOException e) {
        		System.out.println("Error with decimal coefficient polynomial: " + e.getMessage());
    		}

		// Test Case 6: Multiplying polynomials that result in a zero polynomial
    		Polynomial p7 = new Polynomial(new double[]{0}, new int[]{0});
    		Polynomial productZero = p1.multiply(p7);
    		System.out.println("Product of p1 and a zero polynomial evaluated at x=1: " + productZero.evaluate(1)); // Should be 0
		
		// Test Case 7: Reading from an invalid file
    		try {
        		File invalidFile = new File("invalid_polynomial.txt");
        		Polynomial invalidPoly = new Polynomial(invalidFile);
    		} catch (FileNotFoundException e) {
        		System.out.println("Expected error: " + e.getMessage());
    		} catch (IOException e) {
        		System.out.println("Unexpected error: " + e.getMessage());
    		}
		
		// Test Case 8: Polynomial with large exponents
    		double [] co_7 = {1, 2, 3};
    		int [] exp_7 = {10, 20, 30};
    		Polynomial p8 = new Polynomial(co_7, exp_7);
    		System.out.println("p8 evaluated at x=1: " + p8.evaluate(1)); // Should be 6

		// Test Case 9: Adding a nonzero polynomial to a zero polynomial
		Polynomial addZero = p1.add(p7);
		System.out.println("Adding zero polynomial to p1: " + addZero.evaluate(3)); // Should be same as p1 evaluated at 3
		
		// Test Case 10: Adding identical polynomials
        	Polynomial identicalAdd = p1.add(p1);
        	System.out.println("Identical polynomials added (p1 + p1): " + identicalAdd.evaluate(3)); // Should be double p1 evaluated at 3

		// Multiplying by one polynomial
        	Polynomial onePoly = new Polynomial(new double[]{1}, new int[]{0});
        	Polynomial multiplyOne = p1.multiply(onePoly);
        	System.out.println("Multiplying by one polynomial: " + multiplyOne.evaluate(3)); // Should be same as p1 evaluated at 3
		
		// Polynomial known to have root
        	Polynomial p9 = new Polynomial(new double[]{1, -1}, new int[]{1, 0}); // x - 1
        	System.out.println("p9 has root at 1: " + p9.hasRoot(1)); // Should be true
		
		// Polynomial without roots
        	Polynomial p10 = new Polynomial(new double[]{1, 0, 1}, new int[]{2, 1, 0}); // x^2 + 1
        	System.out.println("p10 has root at 0: " + p10.hasRoot(0)); // Should be false
		
	}
}