public class MiArreglo {
    private int[] numeros = new int[10];

    public MiArreglo(int[] numeros) {
        if(numeros.length==10){
            this.numeros=numeros;
        } 
        else{
            throw new IllegalArgumentException("El arreglo debe tener exactamente 10 números");
        }
    }

    public int contarCuatros(){
        int contador=0;
        for(int n:numeros){
            if(n==4){
                contador++;
            }
        }
        return contador;
    }

    public int contarPares(){
        int contador=0;
        for (int n:numeros){
            if(n%2==0){
                contador++;
            }
        }
        return contador;
    }

    public int contarIguales(){
        int contador=0,x=-1;
        for (int n:numeros){
            if(n==x){
                contador++;
            }
            x=n;
        }
        return contador;
    }

    public void mostrarArreglo(){
        for(int n:numeros){
            System.out.print(n+" ");
        }
        System.out.println();
    }
    
    public static void main(String[] args){
        int[] datos={4, 7, 8, 10, 10, 3, 2, 4, 4, 6};
        MiArreglo arreglo=new MiArreglo(datos);

        arreglo.mostrarArreglo();
        System.out.println("Cantidad de 4's: " + arreglo.contarCuatros());
        System.out.println("Cantidad de pares: " + arreglo.contarPares());
        System.out.println("Cantidad de iguales: " + arreglo.contarIguales());
    }
}
