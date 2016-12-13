package Quez;

/**
 * Created by ilasm on 29.11.2016.
 */

public class Question {

    public int left;
    public int right;
    public int correctQuestion;
    public int[] randomAnswers;
    public MathOperation operation;

    @Override
    public String toString() {
        String operationString = "";
        if (operation == MathOperation.Addition) {
            operationString = "+";
        } else if (operation == MathOperation.Subtraction) {
            operationString = "-";
        } else if (operation == MathOperation.Division) {
            operationString = ":";
        } else if (operation == MathOperation.Multiplication) {
            operationString = "x";
        }
        return left + operationString + right;
    }
    public String toDebugString()
    {
        String operationString = "";
        if (operation == MathOperation.Addition) {
            operationString = "+";
        } else if (operation == MathOperation.Subtraction) {
            operationString = "-";
        } else if (operation == MathOperation.Division) {
            operationString = ":";
        } else if (operation == MathOperation.Multiplication) {
            operationString = "x";
        }
        return left + operationString + right + "="+correctQuestion;
    }
}