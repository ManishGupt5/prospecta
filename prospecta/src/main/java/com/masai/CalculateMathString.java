package com.masai;

public class CalculateMathString {

	public static void main(String[] args) {
		// Sample input string with multi-digit and decimal numbers
		String input = "5548-48";

		// Calculate and print the result
		double result = calculateExpression(input);
		System.out.println("Result: " + result);
	}

	// Method to calculate the result of the expression
	public static double calculateExpression(String expression) {
		double result = 0;
		double currentNumber = 0;
		char currentOperator = '+';

		// Iterate over the characters in the string
		for (int i = 0; i < expression.length(); i++) {
			char ch = expression.charAt(i);

			// Handle multi-digit and decimal numbers
			if (Character.isDigit(ch) || ch == '.') {
				StringBuilder numBuilder = new StringBuilder();

				// Build the number (consider multi-digit and decimal point)
				while (i < expression.length()
						&& (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
					numBuilder.append(expression.charAt(i));
					i++;
				}

				// Parse the number as double
				currentNumber = Double.parseDouble(numBuilder.toString());
				i--; // Move back by one as we have moved extra in the loop
			}

			// Handle operators or end of expression
			if (!Character.isDigit(ch) && ch != '.' || i == expression.length() - 1) {
				if (isOperator(ch) || i == expression.length() - 1) {
					result = applyOperation(result, currentNumber, currentOperator);
					currentOperator = ch;
					currentNumber = 0;
				}
			}
		}

		return result;
	}

	// Method to check if a character is an operator
	public static boolean isOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/';
	}

	// Method to apply the current operation
	public static double applyOperation(double result, double currentNumber, char operator) {
		switch (operator) {
		case '+':
			return result + currentNumber;
		case '-':
			return result - currentNumber;
		case '*':
			return result * currentNumber;
		case '/':
			if (currentNumber != 0) {
				return result / currentNumber;
			} else {
				System.out.println("Error: Division by zero.");
				return result;
			}
		default:
			return result;
		}
	}
}
