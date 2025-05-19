import java.util.Scanner;
import javax.script.ScriptException;

public class FunctionPlotter {
    private final CalculatorEngine engine;

    public FunctionPlotter(CalculatorEngine engine) {
        this.engine = engine;
    }

    public void plotFunction() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a function in terms of x (e.g., sin(x), x^2): ");
        String function = scanner.nextLine();

        int xStart = getSafeIntInput(scanner, "Enter x start value: ");
        int xEnd = getSafeIntInput(scanner, "Enter x end value: ");
        int rows = getSafeIntInput(scanner, "Enter number of rows (height of graph): ");
        int cols = getSafeIntInput(scanner, "Enter number of columns (width of graph): ");

        System.out.println("\n--- Function Plot ---");

        double[] yValues = new double[cols];
        double xRange = xEnd - xStart;
        double xStep = xRange / (cols - 1);

        double yMin = Double.POSITIVE_INFINITY;
        double yMax = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < cols; i++) {
            double x = xStart + i * xStep;
            try {
                double y = engine.evaluate(function.replaceAll("x", "(" + x + ")"));
                yValues[i] = y;
                yMin = Math.min(yMin, y);
                yMax = Math.max(yMax, y);
            } catch (ScriptException e) {
                System.out.println("Error evaluating function at x = " + x + ": " + e.getMessage());
                return;
            }
        }

        if (yMax == yMin) yMax += 1;

        for (int row = 0; row < rows; row++) {
            double threshold = yMax - ((yMax - yMin) * row / (rows - 1));
            for (int col = 0; col < cols; col++) {
                if (Math.abs(yValues[col] - threshold) < (yMax - yMin) / rows / 2) {
                    System.out.print("*");
                } else if (Math.abs(threshold) < (yMax - yMin) / rows / 2) {
                    System.out.print("-"); // x-axis
                } else if (Math.abs((xStart + col * xStep)) < xStep / 2) {
                    System.out.print("|"); // y-axis
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        System.out.println("-".repeat(cols));
        System.out.printf("x from %.2f to %.2f%n", (double) xStart, (double) xEnd);
    }

    private int getSafeIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
