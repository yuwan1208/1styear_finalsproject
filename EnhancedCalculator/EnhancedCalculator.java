import java.util.Scanner;
import javax.script.ScriptException;

public class EnhancedCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CalculatorEngine engine = new CalculatorEngine();
        HistoryManager history = new HistoryManager();
        FunctionPlotter plotter = new FunctionPlotter(engine);

        while (true) {
            System.out.println("\n--- Enhanced Calculator with Function Plotting ---");
            System.out.println("1. Basic / Advanced Calculation");
            System.out.println("2. View Calculation History");
            System.out.println("3. Plot a Function");
            System.out.println("4. Clear Calculation History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter expression (e.g., 5 + 2 * 3, sin(30)): ");
                    String expression = scanner.nextLine();
                    try {
                        double result = engine.evaluate(expression);
                        System.out.println("Result: " + result);
                        history.log(expression, result);
                    } catch (ScriptException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "2":
                    history.showHistory();
                    break;
                case "3":
                    plotter.plotFunction();
                    break;
                case "4":
                    history.clearHistory();
                    break;
                case "5":
                    System.out.println("Exiting Calculator... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid number from 1-5.");
            }
        }
    }
}
