import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class Calculator implements ActionListener
{
    String result = "";
    String input = "";
    JFrame frame;
    JTextField tf = new JTextField(); 
    JButton[] numButtons = new JButton[10];
    JButton[] funcButtons = new JButton[7];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, eqButton, clrButton;
    JPanel panel;

    Font myFont = new Font("Courier Bold", Font.BOLD, 30);
    
    Calculator()
    {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);

        tf.setBounds(50, 25, 300, 50);
        tf.setFont(myFont);
        tf.setEditable(false);
        //Buttons
        //Creating our function buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("/");
        decButton = new JButton(".");
        clrButton = new JButton("clr");
        eqButton = new JButton("=");

        //Adding function buttons to funcButtons array
        funcButtons[0] = addButton;
        funcButtons[1] = subButton;
        funcButtons[2] = mulButton;
        funcButtons[3] = divButton;
        funcButtons[4] = decButton;
        funcButtons[5] = clrButton;
        funcButtons[6] = eqButton;

        //Adding action listeners and stuff to the func buttons
        for (int i = 0; i < funcButtons.length; i++)
        {
            funcButtons[i].addActionListener(this);
            funcButtons[i].setFont(myFont);
            funcButtons[i].setFocusable(false);
        }

        for (int i = 0; i < numButtons.length; i++)
        {
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].addActionListener(this);
            numButtons[i].setFocusable(false);
        }

        clrButton.setBounds(127, 430, 145, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4,4,10,10));
        //panel.setBackground(Color.gray);
        //Adding Buttons to panel
        panel.add(numButtons[1]);
        panel.add(numButtons[2]);
        panel.add(numButtons[3]);
        panel.add(addButton);
        panel.add(numButtons[4]);
        panel.add(numButtons[5]);
        panel.add(numButtons[6]);
        panel.add(subButton);
        panel.add(numButtons[7]);
        panel.add(numButtons[8]);
        panel.add(numButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numButtons[0]);
        panel.add(eqButton);
        panel.add(divButton);

        frame.add(panel);
        frame.add(clrButton);
        frame.add(tf);
        frame.setVisible(true);
    }
    public static void main(String args[])
    {
        Calculator calc = new Calculator();
        //String test = "10 + 2 + 4 / 2 - 3";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        for (int i = 0; i < 10; i++)
        {
            if (e.getSource() == numButtons[i])
            {
                tf.setText(tf.getText().concat(String.valueOf(i)));
                input = (input + String.valueOf(i));
            }
        }
        if (e.getSource() == decButton)
        {
            input = (input + ".");
            tf.setText(tf.getText().concat("."));
        }
        if (e.getSource() == addButton)
        {
            input = (input + " + ");
            tf.setText("");
        }
        if (e.getSource() == subButton)
        {
            input = (input + " - ");
            tf.setText("");
        }
        if (e.getSource() == mulButton)
        {
            input = (input + " * ");
            tf.setText("");
        }
        if (e.getSource() == divButton)
        {
            input = (input + " / ");
            tf.setText("");
        }

        if (e.getSource() == clrButton)
        {
            input = "";
            tf.setText("");
        }

        if (e.getSource() == eqButton)
        {
            tf.setText(String.valueOf(calculate(input)));
        }
        
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    public static double calculate(String inp)
    {
        double val = 0;
        Stack<Double> numStack = new Stack<Double>();
        Stack<String> opStack = new Stack<String>();
        double res = 0;
        String op1 = "";
        String op2 = "";
        String[] arr = inp.split(" ");
        for (int i = 0; i < arr.length; i++)
        {
            if (isNumber(arr[i]))
            {
                if (i >= 2 && isNumber(arr[i - 1]))
                {
                    break;
                    //error message (can't have two numbers next to eachother)
                }
                val = Double.parseDouble(arr[i]);
                numStack.push(val);
                if (opStack.size() == 1)
                {
                    if (opStack.peek().equals("*") || opStack.peek().equals("/"))
                    {
                        op1 = opStack.pop();
                        res = Operate(op1, numStack, res);
                        numStack.push(res);
                    }
                    else if (i == arr.length - 1)
                    {
                        op1 = opStack.pop();
                        res = Operate(op1, numStack, res);
                        numStack.push(res);
                    }
                }

                if (opStack.size() == 2)
                {
                    if (opStack.peek().equals("*") || opStack.peek().equals("/")) 
                    {
                        op1 = opStack.pop();
                        res = Operate(op1, numStack, res);
                        numStack.push(res);
                    }
                }

            }
            else
            {
                if (arr[i].equals("+") == false && arr[i].equals("-") == false)
                {
                    if (arr[i].equals("*") == false && arr[i].equals("/") == false)
                    {
                        break;
                        //error statement here "invalid syntax"
                    }
                } 
                else if (i == 0)
                {
                    break;
                    //error statement here "expression can't start with an operator"
                }
                else if (i == arr.length - 1)
                {
                    break;
                    //Error statement here "expression cannot end with an operator"
                }
                else if (isNumber(arr[i - 1]) == false)
                {
                    break;
                    //Error statement "two operators cannot be next to eachother"
                }
                opStack.push(arr[i]);
                if (opStack.size() == 2)
                {
                    if (opStack.peek().equals("/") == false && opStack.peek().equals("*") == false)
                    {
                        op1 = opStack.pop();
                        if (opStack.peek().equals("/") == false && opStack.peek().equals("*") == false)
                        {
                            op2 = opStack.pop();
                            res = Operate(op2, numStack, res);
                            numStack.push(res);
                            opStack.push(op1);
                        } 
                        else 
                        {
                            opStack.push(op1);
                        }
                    }

                }
            }
        }
        if (opStack.size() == 1)
        {
            op1 = opStack.pop();
            res = Operate(op1, numStack, res);
            numStack.push(res);
        }
        return res;
    }

    public static boolean isNumber(String s)
    {
        try 
        {
            double dub = 0.0;
            dub = Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
         }
    }

    public static double Operate(String op, Stack<Double> stack, double d)
    {
        double last = stack.pop();
        double first = stack.pop();
        switch(op)
        {
            case"+":
                d = first + last;
                break;
            case"-":
                d = first - last;
                break;
            case"*":
                d = first * last;
                break;
            case"/":
                d = first / last;
                break;
        }
        return d;
    }
}