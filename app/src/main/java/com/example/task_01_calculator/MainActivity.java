package com.example.task_01_calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import soup.neumorphism.NeumorphButton;

public class MainActivity extends AppCompatActivity {

    private TextView resultDisplay;
    private NeumorphButton currentOperatorButton;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the TextView for result display
        resultDisplay = findViewById(R.id.result_display);

        // Set up click listeners for the buttons
        setupButtons();
    }

    private void setupButtons() {
        // Number buttons
        findViewById(R.id.button_0).setOnClickListener(v -> appendNumber("0"));
        findViewById(R.id.button_00).setOnClickListener(v -> appendNumber("00"));
        findViewById(R.id.button_1).setOnClickListener(v -> appendNumber("1"));
        findViewById(R.id.button_2).setOnClickListener(v -> appendNumber("2"));
        findViewById(R.id.button_3).setOnClickListener(v -> appendNumber("3"));
        findViewById(R.id.button_4).setOnClickListener(v -> appendNumber("4"));
        findViewById(R.id.button_5).setOnClickListener(v -> appendNumber("5"));
        findViewById(R.id.button_6).setOnClickListener(v -> appendNumber("6"));
        findViewById(R.id.button_7).setOnClickListener(v -> appendNumber("7"));
        findViewById(R.id.button_8).setOnClickListener(v -> appendNumber("8"));
        findViewById(R.id.button_9).setOnClickListener(v -> appendNumber("9"));
        findViewById(R.id.button_dot).setOnClickListener(v -> appendDot());

        // Operator buttons
        findViewById(R.id.button_add).setOnClickListener(v -> handleOperator("+", (NeumorphButton) v));
        findViewById(R.id.button_subtract).setOnClickListener(v -> handleOperator("-", (NeumorphButton) v));
        findViewById(R.id.button_multiply).setOnClickListener(v -> handleOperator("×", (NeumorphButton) v));
        findViewById(R.id.button_divide).setOnClickListener(v -> handleOperator("÷", (NeumorphButton) v));
        findViewById(R.id.button_equals).setOnClickListener(v -> calculateResult());

        // Clear buttons
        findViewById(R.id.button_ac).setOnClickListener(v -> clearAll());
        findViewById(R.id.button_c).setOnClickListener(v -> clearEntry());
        findViewById(R.id.button_percent).setOnClickListener(v -> calculatePercentage());
    }

    private void appendNumber(String number) {
        if (isOperatorPressed) {
            currentInput = "";
            isOperatorPressed = false;
        }
        currentInput += number;
        resultDisplay.setText(currentInput);
    }

    private void appendDot() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            resultDisplay.setText(currentInput);
        }
    }

    private void handleOperator(String op, NeumorphButton button) {
        if (!currentInput.isEmpty()) {
            firstOperand = Double.parseDouble(currentInput);
            operator = op;
            isOperatorPressed = true;

            // Update button appearance
            if (currentOperatorButton != null) {
                resetOperatorButtonAppearance(currentOperatorButton);
            }
            currentOperatorButton = button;
            button.setTextColor(Color.GRAY);
        }
    }

    private void resetOperatorButtonAppearance(NeumorphButton button) {
        button.setTextColor(getResources().getColor(R.color.txt_clr)); // Reset color to default
    }

    private void calculateResult() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "×":
                    result = firstOperand * secondOperand;
                    break;
                case "÷":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        resultDisplay.setText("Error");
                        return;
                    }
                    break;
            }

            currentInput = String.valueOf(result);
            resultDisplay.setText(currentInput);
            operator = "";
            if (currentOperatorButton != null) {
                resetOperatorButtonAppearance(currentOperatorButton);
                currentOperatorButton = null;
            }
        }
    }

    private void clearAll() {
        currentInput = "";
        firstOperand = 0;
        operator = "";
        resultDisplay.setText("0");
        if (currentOperatorButton != null) {
            resetOperatorButtonAppearance(currentOperatorButton);
            currentOperatorButton = null;
        }
    }

    private void clearEntry() {
        currentInput = "";
        resultDisplay.setText("0");
    }

    private void calculatePercentage() {
        if (!currentInput.isEmpty()) {
            double value = Double.parseDouble(currentInput);
            currentInput = String.valueOf(value / 100);
            resultDisplay.setText(currentInput);
        }
    }
}