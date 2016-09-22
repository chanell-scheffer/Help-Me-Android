package com.yolandabreakfast.helpme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Parameters;
/**
 * Created by YolandaBreakfast on 2016/09/14.
 */
public class Calculator extends MainActivity{


        // The function has one argument and its name is "sqrt"
    final Function sqrt = new Function("sqrt", 1);
    final Function factorial = new Function("!", 1);
    final Function cuberoot = new Function("crt", 1);
    final Function combination = new Function("comb", 2);
    final Function permutation = new Function("permu", 2);
    Parameters params;
    DoubleEvaluator evaluator;
    private double previousSum = 0;
    private double currentSum = 0;
    private String currentDisplay = "";
    //private String expressionUsedForParsing ="";
    private boolean isRadians = false;

    private TextView outputResult;
    private TextView shiftDisplay;
    private TextView degreeRad;
    private boolean isDegree = false;
    private boolean isInverse = false;
    private String lastResultObtain = "";
    private String currentDisplayedInput = "";
    private String inputToBeParsed = "";
    private Calculator mCalculator;
    private static String PREFS_NAME = "memory";
    private Button button0, button1, button2, button3, button4, button5, button6, button7,
            button8, button9, buttonClear, buttonDivide, buttonMultiply, buttonSubtract,
            buttonAdd, buttonPercentage, buttonEqual, buttonDecimal, closeParenthesis, openParenthesis, buttonAnswer,
            buttonSingleDelete, buttonExp;
    private TextView labelFactorial, labelCombination, labelPermutation, labelPi, labelE, labelComma, labelCubeRoot, labelCube,
            labelInverseX, labelInverseSin, labelInverseCos, labelInverseTan, labelExponential, labelTenPowerX, labelRCL,
            labelSTO, labelMMinus, labelFloat, labelDeg;
    private Button buttonSin, buttonLn, buttonCos, buttonLog, buttonTan, buttonSquareRoot, buttonXSquare, buttonYPowerX,
            buttonRnd;
    private Button buttonShift, buttonRad, buttonAbs, buttonMr, buttonMs, buttonMPlus;


    public Calculator() {
        addFunctions();
        //Adds the functions to the evaluator
        evaluator = new DoubleEvaluator(params) {


            @Override
            protected Double evaluate(Function function, Iterator arguments, Object evaluationContext) {
                if (function == sqrt)
                    return Math.sqrt((Double) arguments.next());
                else if(function == cuberoot){
                    return Math.cbrt((Double) arguments.next());
                }
                else if(function == combination){
                    double numberInputs = 0;
                    ArrayList<Double> saveValue = new ArrayList<Double>();
                    while(arguments.hasNext()) {
                        numberInputs = (Double) arguments.next();
                        saveValue.add(numberInputs);
                    }
                    double firstArgument = saveValue.get(0);
                    double secondArgument = saveValue.get(1);
                    double denominator = getFactorial((int) firstArgument);
                    double nominator = getFactorial((int)secondArgument) * (getFactorial((int)(firstArgument - secondArgument)));
                    return denominator / nominator;
                }
                else if(function == permutation){
                    double numberInputs = 0;
                    ArrayList<Double> saveValue = new ArrayList<Double>();
                    while(arguments.hasNext()) {
                        numberInputs = (Double) arguments.next();
                        saveValue.add(numberInputs);
                    }
                    double firstArgument = saveValue.get(0);
                    double secondArgument = saveValue.get(1);
                    double denominator = getFactorial((int) firstArgument);
                    double nominator = (getFactorial((int)(firstArgument - secondArgument)));
                    return denominator / nominator;
                }
                else if (function == factorial) {
                    double result = 1;
                    double num = (Double) arguments.next();
                    for (int i = 2; i <= num; i++) {
                        result = result * i;
                    }
                    return result;
                } else
                    return super.evaluate(function, arguments, evaluationContext);
            }
        };
    }
    private int getFactorial(int n)    {
        int result;
        if(n==0 || n==1)
            return 1;
        result = getFactorial(n-1) * n;
        return result;
    }
    public void addFunctions() {
        params = DoubleEvaluator.getDefaultParameters();
        params.add(sqrt);
        params.add(factorial);
        params.add(cuberoot);
        params.add(combination);
        params.add(permutation);
    }
    public String getResult(String currentDisplay, String expressionUsedForParsing) {
        //Tries to parse the information as it is entered, if the parser can't handle it, the word error is shown on screen
        try {
            System.out.println("Displayed Output " + expressionUsedForParsing);
            currentSum = evaluator.evaluate(fixExpression(expressionUsedForParsing));
            currentSum = convertToRadians(currentSum);
            currentDisplay = String.valueOf(currentSum);
            //previousSum = currentSum;
        } catch (Exception e) {
            currentDisplay = "Error";
        }
        return currentDisplay;
    }
    public double convertToRadians(double sum){
        double newSum = sum;
        if(isRadians == true)
            newSum = Math.toRadians(sum);
        return newSum;
    }
    //Used to show display to user
    public String getCurrentDisplay() {
        return currentDisplay;
    }
    //Handles fixing the expression before parsing. Adding parens, making sure parens can multiply with each other,
    public String fixExpression(String exp) {
        int openParens = 0;
        int closeParens = 0;
        char openP = '(';
        char closeP = ')';
        String expr = exp;
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == openP)
                openParens++;
            else if (exp.charAt(i) == closeP)
                closeParens++;
        }
        while (openParens > 0) {
            expr += closeP;
            openParens--;
        }
        while (closeParens > 0) {
            expr = openP + expr;
            closeParens--;
        }
        expr = multiplicationForParens(expr);
        return expr;
    }
    //Used to fix multiplication between parentheses
    public String multiplicationForParens(String s) {
        String fixed = "";
        for (int position = 0; position < s.length(); position++) {
            fixed += s.charAt(position);
            if (position == s.length() - 1)
                continue;
            if (s.charAt(position) == ')' && s.charAt(position + 1) == '(')
                fixed += '*';
            if (s.charAt(position) == '(' && s.charAt(position + 1) == ')')
                fixed += '1';
        }
        return fixed;
    }


    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_calculator);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        outputResult = (TextView) findViewById(R.id.display);
        outputResult.setText("");
        shiftDisplay = (TextView) findViewById(R.id.shift_display);
        degreeRad = (TextView) findViewById(R.id.degree);
        button0 = (Button) findViewById(R.id.zero_button);
        button1 = (Button) findViewById(R.id.one_button);
        button2 = (Button) findViewById(R.id.two_button);
        button3 = (Button) findViewById(R.id.three_button);
        button4 = (Button) findViewById(R.id.four_button);
        button5 = (Button) findViewById(R.id.five_button);
        button6 = (Button) findViewById(R.id.six_button);
        button7 = (Button) findViewById(R.id.seven_button);
        button8 = (Button) findViewById(R.id.eight_button);
        button9 = (Button) findViewById(R.id.nine_button);
        buttonDivide = (Button) findViewById(R.id.division);
        buttonMultiply = (Button) findViewById(R.id.multiplication);
        buttonSubtract = (Button) findViewById(R.id.subtraction);
        buttonAdd = (Button) findViewById(R.id.addition);
        buttonPercentage = (Button) findViewById(R.id.percent);
        buttonDecimal = (Button) findViewById(R.id.dot);
        closeParenthesis = (Button) findViewById(R.id.close_bracket);
        openParenthesis = (Button) findViewById(R.id.open_bracket);
        buttonExp = (Button) findViewById(R.id.exp);
        buttonSquareRoot = (Button) findViewById(R.id.square_root);
        buttonXSquare = (Button) findViewById(R.id.x_square);
        buttonYPowerX = (Button) findViewById(R.id.x_power_y);
        buttonSin = (Button) findViewById(R.id.sin_sign);
        buttonCos = (Button) findViewById(R.id.cos_sign);
        buttonTan = (Button) findViewById(R.id.tan_sign);
        buttonLn = (Button) findViewById(R.id.natural_log);
        buttonLog = (Button) findViewById(R.id.log);
        buttonRnd = (Button) findViewById(R.id.hys);
        buttonDivide.setText(Html.fromHtml(Helpers.division));
        buttonSquareRoot.setText(Html.fromHtml(Helpers.squareRoot));
        buttonXSquare.setText(Html.fromHtml(Helpers.xSquare));
        buttonYPowerX.setText(Html.fromHtml(Helpers.yPowerX));
        buttonShift = (Button) findViewById(R.id.shift);
        buttonRad = (Button) findViewById(R.id.rad);
        buttonAbs = (Button) findViewById(R.id.abs);
        buttonMr = (Button) findViewById(R.id.mr);
        buttonMs = (Button) findViewById(R.id.ms);
        buttonMPlus = (Button) findViewById(R.id.m_plus);
        buttonClear = (Button) findViewById(R.id.clear);
        buttonSingleDelete = (Button) findViewById(R.id.single_delete);
        buttonEqual = (Button) findViewById(R.id.equal_sign);
        buttonAnswer = (Button) findViewById(R.id.ans);
        labelFactorial = (TextView) findViewById(R.id.factorial);
        labelCombination = (TextView) findViewById(R.id.combination);
        labelPermutation = (TextView) findViewById(R.id.permutation);
        labelPi = (TextView) findViewById(R.id.pi);
        labelE = (TextView) findViewById(R.id.e);
        labelComma = (TextView) findViewById(R.id.comma);
        labelCubeRoot = (TextView) findViewById(R.id.cube_root);
        labelCube = (TextView) findViewById(R.id.cube);
        labelInverseX = (TextView) findViewById(R.id.one_over_x);
        labelInverseSin = (TextView) findViewById(R.id.inverse_sin);
        labelInverseCos = (TextView) findViewById(R.id.inverse_cos);
        labelInverseTan = (TextView) findViewById(R.id.inverse_tan);
        labelExponential = (TextView) findViewById(R.id.expo);
        labelTenPowerX = (TextView) findViewById(R.id.ten_power_x);
        labelRCL = (TextView) findViewById(R.id.rcl);
        labelSTO = (TextView) findViewById(R.id.sto);
        labelMMinus = (TextView) findViewById(R.id.m_minus);
        labelFloat = (TextView) findViewById(R.id.float_number);
        labelDeg = (TextView) findViewById(R.id.degree);
        labelInverseSin.setText(Html.fromHtml(Helpers.inverseSin));
        labelInverseCos.setText(Html.fromHtml(Helpers.inverseCos));
        labelInverseTan.setText(Html.fromHtml(Helpers.inverseTan));
        labelExponential.setText(Html.fromHtml(Helpers.exponential));
        labelTenPowerX.setText(Html.fromHtml(Helpers.tenPowerX));
        labelCubeRoot.setText(Html.fromHtml(Helpers.cubeSquare));
        labelCube.setText(Html.fromHtml(Helpers.cubeRoot));
        labelPi.setText(Html.fromHtml(Helpers.pi));
        button0.setOnClickListener((View.OnClickListener) this);
        button1.setOnClickListener((View.OnClickListener) this);
        button2.setOnClickListener((View.OnClickListener)this);
        button3.setOnClickListener((View.OnClickListener)this);
        button4.setOnClickListener((View.OnClickListener)this);
        button5.setOnClickListener((View.OnClickListener)this);
        button6.setOnClickListener((View.OnClickListener)this);
        button7.setOnClickListener((View.OnClickListener)this);
        button8.setOnClickListener((View.OnClickListener)this);
        button9.setOnClickListener((View.OnClickListener)this);
        buttonClear.setOnClickListener((View.OnClickListener)this);
        buttonDivide.setOnClickListener((View.OnClickListener)this);
        buttonMultiply.setOnClickListener((View.OnClickListener)this);
        buttonSubtract.setOnClickListener((View.OnClickListener)this);
        buttonAdd.setOnClickListener((View.OnClickListener)this);
        buttonPercentage.setOnClickListener((View.OnClickListener)this);
        buttonEqual.setOnClickListener((View.OnClickListener)this);
        buttonDecimal.setOnClickListener((View.OnClickListener)this);
        closeParenthesis.setOnClickListener((View.OnClickListener)this);
        openParenthesis.setOnClickListener((View.OnClickListener)this);
        buttonSingleDelete.setOnClickListener((View.OnClickListener)this);
        buttonExp.setOnClickListener((View.OnClickListener)this);
        buttonSquareRoot.setOnClickListener((View.OnClickListener)this);
        buttonXSquare.setOnClickListener((View.OnClickListener)this);
        buttonYPowerX.setOnClickListener((View.OnClickListener)this);
        buttonSin.setOnClickListener((View.OnClickListener)this);
        buttonCos.setOnClickListener((View.OnClickListener)this);
        buttonTan.setOnClickListener((View.OnClickListener)this);
        buttonLn.setOnClickListener((View.OnClickListener)this);
        buttonLog.setOnClickListener((View.OnClickListener)this);
        buttonRnd.setOnClickListener((View.OnClickListener)this);
        buttonShift.setOnClickListener((View.OnClickListener)this);
        buttonRad.setOnClickListener((View.OnClickListener)this);
        buttonAbs.setOnClickListener((View.OnClickListener)this);
        buttonMr.setOnClickListener((View.OnClickListener)this);
        buttonMs.setOnClickListener((View.OnClickListener)this);
        buttonMPlus.setOnClickListener((View.OnClickListener)this);
    }

    private void obtainInputValues(String input) {
        switch (input) {
            case "0":
                currentDisplayedInput += "0";
                inputToBeParsed += "0";
                break;
            case "1":
                if (isInverse) {
                    currentDisplayedInput += "π";
                    inputToBeParsed += "pi";
                } else {
                    currentDisplayedInput += "1";
                    inputToBeParsed += "1";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "2":
                if (isInverse) {
                    currentDisplayedInput += "e";
                    inputToBeParsed += "e";
                } else {
                    currentDisplayedInput += "2";
                    inputToBeParsed += "2";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "3":
                if (isInverse) {
                    currentDisplayedInput += ",";
                    inputToBeParsed += ",";
                } else {
                    currentDisplayedInput += "3";
                    inputToBeParsed += "3";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "4":
                if (isInverse) {
                    currentDisplayedInput += "!(";
                    inputToBeParsed += "!(";
                } else {
                    currentDisplayedInput += "4";
                    inputToBeParsed += "4";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "5":
                if (isInverse) {
                    currentDisplayedInput += "comb(";
                    inputToBeParsed += "comb(";
                } else {
                    currentDisplayedInput += "5";
                    inputToBeParsed += "5";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "6":
                if (isInverse) {
                    currentDisplayedInput += "permu(";
                    inputToBeParsed += "permu(";
                } else {
                    currentDisplayedInput += "6";
                    inputToBeParsed += "6";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "7":
                currentDisplayedInput += "7";
                inputToBeParsed += "7";
                break;
            case "8":
                currentDisplayedInput += "8";
                inputToBeParsed += "8";
                break;
            case "9":
                currentDisplayedInput += "9";
                inputToBeParsed += "9";
                break;
            case ".":
                currentDisplayedInput += ".";
                inputToBeParsed += ".";
                break;
            case "+":
                currentDisplayedInput += "+";
                inputToBeParsed += "+";
                break;
            case "-":
                currentDisplayedInput += "-";
                inputToBeParsed += "-";
                break;
            case "÷":
                currentDisplayedInput += "÷";
                inputToBeParsed += "/";
                break;
            case "x":
                currentDisplayedInput += "*";
                inputToBeParsed += "*";
                break;
            case "(":
                currentDisplayedInput += "(";
                inputToBeParsed += "(";
                break;
            case ")":
                currentDisplayedInput += ")";
                inputToBeParsed += ")";
                break;
            case "%":
                if (isInverse) {
                    currentDisplayedInput += "1÷";
                    inputToBeParsed += "1÷";
                } else {
                    currentDisplayedInput += "%";
                    inputToBeParsed += "%";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "ln":
                if (isInverse) {
                    currentDisplayedInput += "e^";
                    inputToBeParsed += "e^";
                } else {
                    currentDisplayedInput += "ln(";
                    inputToBeParsed += "ln(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "log":
                if (isInverse) {
                    currentDisplayedInput += "10^";
                    inputToBeParsed += "10^";
                } else {
                    currentDisplayedInput += "log(";
                    inputToBeParsed += "log(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "√":
                if (isInverse) {
                    currentDisplayedInput += "3√(";
                    inputToBeParsed += "crt(";
                } else {
                    currentDisplayedInput += "√(";
                    inputToBeParsed += "sqrt(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "Yx":
                currentDisplayedInput += "^";
                inputToBeParsed += "^";
                break;
            case "sin":
                if (isInverse) {
                    currentDisplayedInput += "asin(";
                    inputToBeParsed += "asin(";
                } else {
                    currentDisplayedInput += "sin(";
                    inputToBeParsed += "sin(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "cos":
                if (isInverse) {
                    currentDisplayedInput += "acos(";
                    inputToBeParsed += "acos(";
                } else {
                    currentDisplayedInput += "cos(";
                    inputToBeParsed += "cos(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "tan":
                if (isInverse) {
                    currentDisplayedInput += "atan(";
                    inputToBeParsed += "atan(";
                } else {
                    currentDisplayedInput += "tan(";
                    inputToBeParsed += "tan(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "exp":
                currentDisplayedInput += "E";
                inputToBeParsed += "E0";
                break;
            case "x2":
                if (isInverse) {
                    currentDisplayedInput += "^3";
                    inputToBeParsed += "^3";
                } else {
                    currentDisplayedInput += "^2";
                    inputToBeParsed += "^2";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "rnd":
                double ran = Math.random();
                currentDisplayedInput += String.valueOf(ran);
                inputToBeParsed += String.valueOf(ran);
                break;
            case "ABS":
                currentDisplayedInput += "abs(";
                inputToBeParsed += "abs(";
                break;
            case "MR":
                String mValue = getStoredPreferenceValue(Calculator.this);
                String result = removeTrailingZero(mValue);
                if (!result.equals("0")) {
                    currentDisplayedInput += result;
                    inputToBeParsed += result;
                }
                break;
            case "MS":
                clearMemoryStorage(Calculator.this);
                break;
            case "M+":
                if (isInverse) {
                    double inputValueMinus = isANumber(outputResult.getText().toString());
                    if (!Double.isNaN(inputValueMinus)) {
                        subtractMemoryStorage(Calculator.this, inputValueMinus);
                    }
                } else {
                    double inputValue = isANumber(outputResult.getText().toString());
                    if (!Double.isNaN(inputValue)) {
                        addToMemoryStorage(Calculator.this, inputValue);
                    }
                }
                toggleInverse();
                toggleShiftButton();
                break;
        }
        outputResult.setText(currentDisplayedInput);
    }


    public void onClick(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();
        //Toast.makeText(this, "Click " + data, Toast.LENGTH_LONG).show();
        if (data.equals("AC")) {
            outputResult.setText("");
            currentDisplayedInput = "";
            inputToBeParsed = "";
        } else if (data.equals("del")) {
            String enteredInput = outputResult.getText().toString();
            if (enteredInput.length() > 0) {
                enteredInput = enteredInput.substring(0, enteredInput.length() - 1);
                currentDisplayedInput = enteredInput;
                inputToBeParsed = enteredInput;
                outputResult.setText(currentDisplayedInput);
            }
        } else if (data.equals("=")) {
            String enteredInput = outputResult.getText().toString();
            // call a function that will return the result of the calculate.
            String resultObject = mCalculator.getResult(currentDisplayedInput, inputToBeParsed);
            outputResult.setText(removeTrailingZero(resultObject));
        } else if (data.equals("Ans")) {
            String enteredInput = outputResult.getText().toString();
            enteredInput += lastResultObtain;
            outputResult.setText(enteredInput);
        } else if (data.equals("SHIFT")) {
            if (!isInverse) {
                isInverse = true;
            } else {
                isInverse = false;
            }
            toggleShiftButton();
        } else if (data.equals("RAD")) {
            buttonRad.setText("DEG");
            degreeRad.setText("RAD");
        } else if (data.equals("DEG")) {
            buttonRad.setText("RAD");
            degreeRad.setText("DEG");
        } else {
            obtainInputValues(data);
        }
    }

    private String removeTrailingZero(String formattingInput) {
        if (!formattingInput.contains(".")) {
            return formattingInput;
        }
        int dotPosition = formattingInput.indexOf(".");
        String newValue = formattingInput.substring(dotPosition, formattingInput.length());
        if (newValue.equals(".0")) {
            return formattingInput.substring(0, dotPosition);
        }
        return formattingInput;
    }

    private void toggleInverse() {
        if (isInverse) {
            isInverse = false;
        }
    }

    private void toggleShiftButton() {
        if (isInverse) {
            shiftDisplay.setText("SHIFT");
        } else {
            shiftDisplay.setText("");
        }
    }


    public boolean onCreateOptionsMABoolean(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    public  boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
  return super.onOptionsItemSelected(item);
    }

    private double isANumber(String numberInput) {
        double result = Double.NaN;
        try {
            result = Double.parseDouble(numberInput);
        } catch (NumberFormatException nfe) {
        }
        return result;
    }

    private void addToMemoryStorage(Context context, double inputToStore) {
        float returnPrefValue = getPreference(context);
        float newValue = returnPrefValue + (float) inputToStore;
        setPreference(context, newValue);
    }

    private void subtractMemoryStorage(Context context, double inputToStore) {
        float returnPrefValue = getPreference(context);
        float newValue = returnPrefValue - (float) inputToStore;
        setPreference(context, newValue);
    }

    private void clearMemoryStorage(Context context) {
        setPreference(context, 0);
    }

    private String getStoredPreferenceValue(Context context) {
        float returnedValue = getPreference(context);
        return String.valueOf(returnedValue);
    }

    static public boolean setPreference(Context c, float value) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("key", value);
        return editor.commit();
    }

    static public float getPreference(Context c) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        float value = settings.getFloat("key", 0);
        return value;
    }


}
