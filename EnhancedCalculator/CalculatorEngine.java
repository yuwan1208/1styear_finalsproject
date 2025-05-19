import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalculatorEngine {
    private ScriptEngine engine;

    public CalculatorEngine() {
        engine = new ScriptEngineManager().getEngineByName("JavaScript");
    }

    public double evaluate(String expression) throws ScriptException {
        expression = preprocessExpression(expression);
        Object result = engine.eval(expression);
        return Double.parseDouble(result.toString());
    }

    private String preprocessExpression(String expr) {
        expr = expr.replaceAll("(?i)log\\s*\\(([^)]+)\\)", "Math.log($1)/Math.LN10");
        expr = expr.replaceAll("(?i)\\bln\\b", "Math.log");
        expr = expr.replaceAll("(?i)sin", "Math.sin");
        expr = expr.replaceAll("(?i)cos", "Math.cos");
        expr = expr.replaceAll("(?i)tan", "Math.tan");
        expr = expr.replaceAll("(?i)sqrt", "Math.sqrt");
        expr = expr.replaceAll("(?i)exp", "Math.exp");
        expr = expr.replaceAll("(?i)pi", "Math.PI");
        expr = expr.replaceAll("(?i)\\be\\b", "Math.E");

        expr = expr.replaceAll("Math\\.sin\\(([^)]+)\\)", "Math.sin(($1) * Math.PI / 180)");
        expr = expr.replaceAll("Math\\.cos\\(([^)]+)\\)", "Math.cos(($1) * Math.PI / 180)");
        expr = expr.replaceAll("Math\\.tan\\(([^)]+)\\)", "Math.tan(($1) * Math.PI / 180)");

        expr = replacePowerOperator(expr);
        return expr;
    }

    private String replacePowerOperator(String expr) {
        while (expr.contains("^")) {
            expr = expr.replaceAll(
                "(\\b\\(?[-+]?\\d*\\.?\\d+\\)?|\\([^()]+\\))\\s*\\^\\s*(\\(?[-+]?\\d*\\.?\\d+\\)?|\\([^()]+\\))",
                "Math.pow($1,$2)"
            );
        }
        return expr;
    }
}
