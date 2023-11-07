package handwriting;

import java.util.*;

public class Matrix2D {
	public double[] matrix;
	int w, h;
	
	public Matrix2D(int w, int h) {
		this(w, h, false);
	}
	
	public Matrix2D(int w, int h, boolean fillRandom) {
		this.matrix = new double[w * h];
		this.w = w;
		this.h = h;
		if (fillRandom) {
			Random r = new Random();
			for (int i = 0; i < this.matrix.length; i++)
				this.matrix[i] = r.nextGaussian();
		}
	}
	
	public void dump() {
		for (int i = 0; i < h; i++) {
			System.out.print("[");
			for (int j = 0; j < w; j++)
				System.out.printf("%f,", this.matrix[i*w+j]);
			System.out.println("],");
		}
	}
	
	public Matrix2D copyEmpty() {
		return new Matrix2D(this.w, this.h, false);
	}
	
	public void add(Matrix2D m) {
		if (m.matrix.length != this.matrix.length) {
			throw new IllegalStateException("Matricess must be same size?");
		}
		
		for (int i = 0; i < this.matrix.length; i++) 
			this.matrix[i] += m.matrix[i];
	}
	public void sub(Matrix2D m) {
		if (m.matrix.length != this.matrix.length) {
			throw new IllegalStateException("Matricess must be same size?");
		}
		
		for (int i = 0; i < this.matrix.length; i++) 
			this.matrix[i] -= m.matrix[i];
	}
	public void sub(double d) {
		for (int i = 0; i < this.matrix.length; i++) 
			this.matrix[i] -= d;
	}
	
	public void shape() {
		System.out.printf("h=%d w=%d\n", h, w);
	}
	
	public void sigmoid() {
		for (int i = 0; i < this.matrix.length; i++) {
			double v = this.matrix[i];
			v = 1.0 / (1.0 + Math.exp(-v));
			this.matrix[i] = v;
		}
	}
	
	static private double sigmoidScalar(double z) {
		return 1.0 / (1.0 + Math.exp(z));
	}
	
	public void sigmoidPrime() {
		for (int i = 0; i < this.matrix.length; i++) {
			double v = this.matrix[i];
			v = sigmoidScalar(v) * (1 - sigmoidScalar(v));
			this.matrix[i] = v;
		}
	}
	
	public void simpleMul(Matrix2D A) {
		if (A.matrix.length != this.matrix.length) {
			throw new IllegalStateException("Matricess must be same size?");
		}
		
		for (int i = 0; i < this.matrix.length; i++) 
			this.matrix[i] *= A.matrix[i];
	}
	
	public static Matrix2D multiply(Matrix2D A, Matrix2D B) {
		if (A.w != B.h) {
		    throw new IllegalArgumentException("Matrix dimensions are not compatible for multiplication.");
		}
		Matrix2D C = new Matrix2D(B.w, A.h);

		for (int i = 0; i < C.h; i++) {
		    for (int j = 0; j < C.w; j++) {
		        double sum = 0;
		        for (int k = 0; k < A.w; k++) {
		            sum += A.matrix[i * A.w + k] * B.matrix[k * B.w + j];
		        }
		        C.matrix[i * C.w + j] = sum;
		    }
		}
		return C;
	}
	
	public Matrix2D transpose() {
		Matrix2D m = new Matrix2D(this.h, this.w);
		for (int i = 0; i < this.h; i++) {
			for (int j = 0; j < this.w; j++) {
				double b = this.matrix[i * this.w + j];
				m.matrix[j * this.w + i] = b;
			}
		}
		return m;
	}
	
	public Matrix2D copy() {
		Matrix2D m = new Matrix2D(this.w, this.h);
		System.arraycopy(this.matrix, 0, m.matrix, 0, matrix.length);
		return m;
	}
}
