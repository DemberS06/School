package model;

import java.io.Serializable;

public class Calc implements Serializable {
    private int a, b, t;
    private String o;
    
    public Calc() {
        super();
    }
    
    public Calc(int num1, int num2, String operator){
        this.a=num1;
        this.b=num2;
        this.o=operator;
        setResult();
    }

    public int getNum1(){return a;}
    public int getNum2(){return b;}
    public String getOperator(){return o;}
    public int getResult(){
        setResult();
        return t;
    }
    public String getResultS(){
        Integer res = new Integer(getResult());
        return res.toString();
    }

    public void setNum1(int num1){this.a=num1;}
    public void setNum2(int num2){this.b=num2;}
    public void setOperator(String operator){this.o=operator;}
    public void setResult(){
        if(o.equals("+"))this.t=a+b;
        else if(o.equals("-"))t=a-b;
        else if(o.equals("*"))t=a*b;
        else if(b==0)t=-35505;
        else if(o.equals("/"))t=a/b;
        else if(o.equals("%"))t=a%b;
    }
    
}
