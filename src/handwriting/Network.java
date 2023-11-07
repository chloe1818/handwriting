package handwriting;

import java.util.*;

public class Network {
	public static class Input {
		Matrix2D data;
		Matrix2D label;
		int label2;
	}
	int[] layerSizes;
	Matrix2D[] biases;
	Matrix2D[] weights; 
	public Network(int[] layerSizes) {
		this.layerSizes = layerSizes;
		this.biases = new Matrix2D[layerSizes.length - 1];
		
		// Create the bias layer
		for (int i = 1; i < layerSizes.length; i++)
			this.biases[i - 1] = new Matrix2D(layerSizes[i], 1, true);
		
		// Create the weight layer.
		this.weights = new Matrix2D[layerSizes.length - 1];
		for (int i = 0; i < layerSizes.length - 1; i++) {
			int columns = layerSizes[i],
				rows = layerSizes[i + 1];
			this.weights[i] = new Matrix2D(columns, rows, true);
		}
	}
	
	public Matrix2D feedforward(Matrix2D inputs) {
		for (int i = 0; i < this.biases.length; i++) {
			Matrix2D bias = this.biases[i], 
					 weights = this.weights[i];
			
			Matrix2D product = Matrix2D.multiply(weights, inputs);
			product.add(bias);
			product.sigmoid();
			
			inputs = product;
		}
		
		return inputs;
	}
	/*
	public void shuffle(Input[] data) {
		Random r = new Random();
		for (int i = data.length - 1; i > 0; i--) {
			Input a = data[index];
			data[index] = data[i];
			data[i] = a;
		}
	}
	
	public void stochasticGradientDescent(Input[] data, int epochs, int batch_size, double step, Input[] test) {
		for (int i = 0; i < epochs; i++) {
			// Randomly shuffle training data around so that we get different results each time. 
			shuffle(data);
			
			for (int j = 0; j < data.length; j += batch_size) {
				updateBatch(data, j, batch_size, step);
			}
		}
	}
	
	void updateBatch(Input[] data, int start, int len, double step) {
		int layerCount = this.biases.length;
		Matrix2D[] nabla_b1 = new Matrix2D[layerCount];
		Matrix2D[] nabla_w1 = new Matrix2D[layerCount];
		for (int i = 0; i < layerCount; i++)
			nabla_b1[i] = this.biases[i].copyEmpty();
		for (int i = 0; i < layerCount; i++)
			nabla_w1[i] = this.weights[i].copyEmpty();
		
		for (int i = 0; i < data.length; i++) {
			// First, perform a feedforward pass. 
			Matrix2D activation = data[i].data;
			// Holds the "output value" of each layer. Note that we count the input layer too. 
			Matrix2D[] activationsByLayer = new Matrix2D[layerCount + 1];
			// Holds raw inputs (sans sigmoid) to each layer
			Matrix2D[] zVectors = new Matrix2D[layerCount];

			// The very first layer's activation is, by definition, the input.
			activationsByLayer[i] = activation;
			
			// Perform forward propagation, as you might expect. 
			for (int i = 0; i < layerCount; i++) {
				Matrix2D bias = this.biases[i], 
						 weights = this.weights[i];
				
				Matrix2D product = Matrix2D.multiply(weights, activation);
				product.add(bias);
				
				// Add the raw input into the zVectors array. 
				zVectors[i] = product.copy();
				
				// Perform the sigmoid activation function to get the "real" activation of this layer.
				product.sigmoid();
				
				activation = product;
				activationsByLayer[i + 1] = activation;
			}
			
			// Now, we would like to see how off we are. Start from the right-most layer and move towards the input layer.
			Matrix2D off = activationsByLayer[activationsByLayer.length - 1].sub(data[i].label);
			Matrix2D delta = off.simpleMul(zVectors[zVectors.length - 1]);
			
			Matrix2D[] nabla_b = new Matrix2D[layerCount];
			Matrix2D[] nabla_w = new Matrix2D[layerCount];
			
			nabla_b[nabla_b.length - 1] = delta;
			nabla_w[nabla_w.length - 1] = Matrix2D.multiply(delta, activationsByLayer[activationsByLayer.length - 2]);
			
			for (int i = 2; i <= layerCount; i++) {
				Matrix2D z = zVectors[i].sigmoidPrime();
				delta = Matrix2D.multiply(this.weights[this.weights.length - i + 1].transpose(), delta).simpleMul(z);
				nabla_b[nabla_b.length - i] = delta;
				nabla_w[nabla_w.length - i] = Matrix2D.multiply(delta, activationsByLayer[activationsByLayer.length - i - 1].transpose());
			}
			
			for (int i = 0; i < nabla_b.length; i++) {
				nabla_b1[i].add(nabla_b[i]);
				nabla_w1[i].add(nabla_w[i]);
			}
		}
		
		double adjust = step / data.length;
		for (int i = 0; i < nabla_b.length; i++) {
			// (w - step / sizeof(batch)) * nabla_w
			this.weights[i].simpleMul(nabla_w.sub(adjust));
			this.biases[i].simpleMul(nabla_b.sub(adjust));
		}
	}*/
}
