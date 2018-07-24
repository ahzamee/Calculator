package com.hussain.calculator;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = findViewById(R.id.value);
    }

    public void calculate(View view) {
        String Value = value.getText().toString();
        try {
            String data = infixToPostfix(Value);
            double result = evaluatePostfix(data);
            showMessage("Calculated Value is: ", String.valueOf(result));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Values are not Correct",Toast.LENGTH_SHORT).show();
        }
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    int Prec(char ch) {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;

            case '%':
                return 4;
            }
            return -1;
        }


        String infixToPostfix(String exp)
        {
            // initializing empty String for result
            String result = new String("");

            // initializing empty stack
            Stack<Character> stack = new Stack<>();

            for (int i = 0; i<exp.length(); ++i)
            {
                char c = exp.charAt(i);

                // If the scanned character is an operand, add it to output.
                if (Character.isLetterOrDigit(c))
                    result += c;

                    // If the scanned character is an '(', push it to the stack.
                else if (c == '(')
                    stack.push(c);

                    //  If the scanned character is an ')', pop and output from the stack
                    // until an '(' is encountered.
                else if (c == ')')
                {
                    while (!stack.isEmpty() && stack.peek() != '(')
                        result += stack.pop();

                    if (!stack.isEmpty() && stack.peek() != '(')
                        return "Invalid Expression"; // invalid expression
                    else
                        stack.pop();
                }
                else // an operator is encountered
                {
                    while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
                        result += stack.pop();
                    stack.push(c);
                }
            }

            // pop all the operators from the stack
            while (!stack.isEmpty())
                result += stack.pop();

            return result;
        }

    static double evaluatePostfix(String exp)
    {
        //create a stack
        Stack<Double> stack=new Stack<>();

        // Scan all characters one by one
        for(int i=0;i<exp.length();i++)
        {
            char c=exp.charAt(i);

            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(Character.isDigit(c))
                stack.push(Double.valueOf(c - '0'));

                //  If the scanned character is an operator, pop two
                // elements from stack apply the operator
            else
            {
                double val1 = stack.pop();
                double val2 = stack.pop();

                switch(c)
                {
                    case '+':
                        stack.push(val2+val1);
                        break;

                    case '-':
                        stack.push(val2- val1);
                        break;

                    case '/':
                        stack.push(val2/val1);
                        break;

                    case '*':
                        stack.push(val2*val1);
                        break;

                    case '%':{
                        val2 = val2/100;
                        val1 = val2*val1;
                        stack.push(val1);
                        break;}
                }
            }
        }
        return stack.pop();
    }
}


