class trianglemax {

    public static int max(int a, int b, int c, int d, int e){
        int val = a;
        val=Math.max(val, b);
        val=Math.max(val, c);
        val=Math.max(val, d);
        val=Math.max(val, e);
        return val;
    }

    public static void main(String[] arr){
        A tri = new A();
        int a=10, b=7, c=10, d=3, e=9;
        System.out.println(a+" "+b+" "+c+" "+d+" "+e);

        if(a==b && a==c)System.out.print("Equilatero ");
        else if(a==b || a==c || b==c)System.out.print("Isosceles ");
        else System.out.print("Escaleno ");
        System.out.println("\n"+tri.triangle(a, b, c));
        
        System.out.println("\n"+tri.triangle(1, 2, 3));
        System.out.println(tri.triangle(1, 1, 1)+"\n");

        System.out.println(max(a,b,c,d,e));
    }
}

class A{
    public static String triangle(int a, int b, int c){
        if(a==b && a==c)return "Equilatero";
        else if(a==b || a==c || b==c)return "Isosceles";
        else return "Escaleno";
    }
}