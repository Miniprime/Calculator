package com.miniprime1.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import android.view.*;
import android.widget.*;

import com.miniprime1.calculator.databinding.ActivityMainBinding;

import android.util.Log;
public class MainActivity extends AppCompatActivity {

    boolean isFirstInput = true;
    boolean isOperatorClick = false;
    double resultNumber = 0;
    double inputNumber = 0;
    String operator = "=";
    String lastOperator = "+";
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void numButtonClick(View view) {
        String text = view.getTag().toString();
        if (isFirstInput) {
            activityMainBinding.reseulTextView.setText(text);
            isFirstInput = false;
            if (operator.equals("=")) {
                activityMainBinding.mathTextView.setText(null);
                isOperatorClick = false;
            }
        } else {
            activityMainBinding.reseulTextView.append(text);
        }
    }

    public void operatorClick(View view) {
        isOperatorClick = true;
        lastOperator = view.getTag().toString();
        if (isFirstInput) {
            if (operator.equals("=")) {
                operator = view.getTag().toString();
                resultNumber = Double.parseDouble(activityMainBinding.reseulTextView.getText().toString());
                activityMainBinding.mathTextView.setText(resultNumber + " " + operator + " ");
            } else {
                operator = view.getTag().toString();
                String mathText = activityMainBinding.mathTextView.getText().toString();
                String subString = mathText.substring(0, mathText.length() - 2);
                activityMainBinding.mathTextView.setText(subString);
                activityMainBinding.mathTextView.append(operator + " ");
            }
        } else {
            inputNumber = Double.parseDouble(activityMainBinding.reseulTextView.getText().toString());
            resultNumber = calculator(resultNumber, inputNumber, operator);
            activityMainBinding.reseulTextView.setText(String.valueOf(resultNumber));
            isFirstInput = true;
            operator = view.getTag().toString();
            activityMainBinding.mathTextView.append(inputNumber + " " + operator + " ");
        }
    }

    public void equalsButonClick(View view) {
        if (isFirstInput) {
            if (isOperatorClick) {
                activityMainBinding.mathTextView.setText(resultNumber + " " + lastOperator + " " + inputNumber + " =");
                resultNumber = calculator(resultNumber, inputNumber, lastOperator);
                activityMainBinding.reseulTextView.setText(String.valueOf(resultNumber));
            }
        } else {
            inputNumber = Double.parseDouble(activityMainBinding.reseulTextView.getText().toString());
            resultNumber = calculator(resultNumber, inputNumber, operator);
            lastOperator = operator;
            activityMainBinding.reseulTextView.setText(String.valueOf(resultNumber));
            isFirstInput = true;
            operator = view.getTag().toString();
            activityMainBinding.mathTextView.append(inputNumber + " " + operator + " ");
        }
    }

    private double calculator(double resultNumber, double inputNumber, String operator) {
        if (operator.equals("=")) {return inputNumber;}
        else if (operator.equals("+")) {return resultNumber + inputNumber;}
        else if (operator.equals("-")) {return resultNumber - inputNumber;}
        else if (operator.equals("*")) {return resultNumber * inputNumber;}
        else if (operator.equals("/")) {return resultNumber / inputNumber;}
        else if (operator.equals("%")) {return resultNumber % inputNumber;}
        return resultNumber;
    }

    public void clearButtonClick(View view) {
        activityMainBinding.reseulTextView.setText("0");
        activityMainBinding.mathTextView.setText("");
        resultNumber = 0;
        operator = "=";
        isFirstInput = true;
        isOperatorClick = false;
    }

    public void pointButtonClick(View view) {
        if (isFirstInput) {
            activityMainBinding.reseulTextView.setText("0" + view.getTag().toString());
            isFirstInput = false;
        } else {
            if (activityMainBinding.reseulTextView.getText().toString().contains(".")) {
                Toast.makeText(this, "A floating-point already exists", Toast.LENGTH_SHORT);
            } else {
                activityMainBinding.reseulTextView.append(view.getTag().toString());
            }
        }
    }

    public void backspaceButtonClick(View view) {
        if (!isFirstInput) {
            String resultText = activityMainBinding.reseulTextView.getText().toString();
            if (resultText.length() > 0) {
                String subString = resultText.substring(0, resultText.length() - 1);
                activityMainBinding.reseulTextView.setText(subString);
            } else {
                activityMainBinding.reseulTextView.setText("0");
                isFirstInput = true;
            }
        }
    }
}