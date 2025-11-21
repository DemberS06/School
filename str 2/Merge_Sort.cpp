#include <bits/stdc++.h>
#define MAX_SIZE 200000

using namespace std;

int a[MAX_SIZE], b[MAX_SIZE];

void Clean(){
    return;
    printf("\033[2J\033[H");
    printf("\033[2J\033[H");
    printf("\033[2J\033[H");
    printf("\033[2J\033[H");
    printf("\033[2J\033[H");
}
void TakeBreak(){
    //Limpiar Búfer
    //Enter para continuar;
}
int ReadInt(int l=-10000000, int r=10000000){
    int x;

    do{
        printf("Ingrese un valor entre %d y %d: ", l, r);
        scanf("%d", &x);
    }while(x<l || x>r);

    return x;
}

void PushSort(int& i, int& p){
    b[i]=a[p]; 
    p++;
}

void MergeSort(int l, int r, int& n){
    if(l<1 || r>n || r<=l)return;

    //printf("%d %d\n", l, r);

    int p=(l+r)/2;

    MergeSort(l, p, n);
    MergeSort(p+1, r, n);

    int x=l, y=p+1;

    for(int i=l; i<=r; i+=1){
        if(x>p){
            PushSort(i, y);
            continue;
        }
        if(y>r){
            PushSort(i, x);
            continue;
        }
        
        if(a[x]<a[y])PushSort(i, x);
        else PushSort(i, y);
    }

    for(int i=l; i<=r; i+=1)a[i]=b[i];
}


void Add(int& n){
    n+=1;
    printf("---Elemento del Arreglo---\n");
    a[n] = ReadInt();
}
void Less(int& n){
    if(n==0){
        printf("Arreglo vacio\n");
        return;
    }

    n--;
    printf("---Posicion del Arreglo---\n");
    int p = ReadInt(1, n);

    for(int i=p; i<=n; i+=1)a[i]=a[i+1];
}
void Change(int& n){
    if(n==0){
        printf("Arreglo vacio\n");
        return;
    }

    printf("---Posicion del Arreglo---\n");
    int p = ReadInt(1, n);

    printf("---Elemento del Arreglo---\n");
    a[p] = ReadInt();
}
void Print(int& n){
    printf("El Arreglo se ve asi:\n");

    for(int i=1; i<=n; i+=1)printf("%d ", a[i]);
    printf("\n");
}

void menu(int& n){
    printf("---- MENU----\n\n");
    printf("[1] Ordenar\n");
    printf("[2] Insertar elemento\n");
    printf("[3] Eliminar elemento\n");
    printf("[4] Cambiar elemento\n");
    printf("[5] Imprimir arreglo\n");
    printf("[6] Salir\n");

    int option = ReadInt(1, 6);

    Clean();

    switch(option){
        case 1:
            MergeSort(1, n, n);
            break;
        case 2:
            Add(n);
            break;
        case 3:
            Less(n);
            break;
        case 4:
            Change(n);
            break;
        case 5:
            Print(n);
            break;
        default: 
            printf("Saliendo del programa...");
            return;
    }

    TakeBreak();
    Clean();

    return menu(n);
}

int main(){
    int n = 0;
    
    menu(n);
    
    return 0;
}