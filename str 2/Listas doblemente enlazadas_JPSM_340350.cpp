/*
  Fecha: 24 de Marzo de 2025
  Autor: Juan Pablo Salinas Muñoz ID:340350
  Materia: Estructuras computacionales
  Grupo: ICI - Segundo semestre
*/
#include <bits/stdc++.h>
using namespace std;

void clean(){
    printf("\033[2J\033[H");
    printf("\033[2J\033[H");
    printf("\033[2J\033[H");
    return;
}

void Wait(){
    this_thread::sleep_for(chrono::seconds(3));
    return clean();
}

typedef struct Nodo{
    int dato;
    struct Nodo* anterior;
    struct Nodo* siguiente;
} Nodo;

Nodo* cabeza=NULL;

void insertarInicio(int dato) {
    Nodo* nuevo=(Nodo*)malloc(sizeof(Nodo));
    nuevo->dato=dato;
    nuevo->anterior=NULL;
    nuevo->siguiente=cabeza;

    if(cabeza!=NULL)cabeza->anterior=nuevo;
    
    cabeza=nuevo;
}

void insertarFinal(int dato) {
    Nodo* nuevo=(Nodo*)malloc(sizeof(Nodo));
    nuevo->dato=dato;
    nuevo->siguiente=NULL;

    if(cabeza==NULL){
        nuevo->anterior=NULL;
        cabeza=nuevo;
        return;
    }

    Nodo* temp=cabeza;
    while(temp->siguiente!=NULL)temp=temp->siguiente;

    temp->siguiente=nuevo;
    nuevo->anterior=temp;
    
    return;
}

void insertarEnPosicion(int dato, int posicion) {
    if(posicion==0)return insertarInicio(dato);

    Nodo* nuevo=(Nodo*)malloc(sizeof(Nodo));
    nuevo->dato=dato;

    Nodo* temp=cabeza;
    for(int i=1; i<posicion && temp!=NULL; i++)temp=temp->siguiente;
    

    if(temp==NULL){
        printf("Posición inválida.\n");
        free(nuevo);
        return Wait();
    }

    nuevo->siguiente=temp->siguiente;
    nuevo->anterior=temp;

    if(temp->siguiente!=NULL)temp->siguiente->anterior=nuevo;

    temp->siguiente=nuevo;
    
    return;
}

void eliminarInicio() {
    if(cabeza==NULL){
        printf("La lista está vacía.\n");
        return Wait();
    }

    Nodo* temp=cabeza;
    cabeza=cabeza->siguiente;

    if(cabeza!=NULL)cabeza->anterior=NULL;

    free(temp);
    
    return;
}

void eliminarFinal() {
    if(cabeza==NULL) {
        printf("La lista está vacía.\n");
        return Wait();
    }

    Nodo* temp=cabeza;
    while(temp->siguiente!=NULL)temp=temp->siguiente;
    

    if(temp->anterior!=NULL)temp->anterior->siguiente=NULL;
    else cabeza=NULL;

    free(temp);
}

void eliminarEnPosicion(int posicion) {
    if(cabeza==NULL) {
        printf("La lista está vacía.\n");
        return Wait();
    }

    if(posicion==0){
        eliminarInicio();
        return;
    }

    Nodo* temp=cabeza;
    for (int i=0; i<posicion && temp!=NULL; i++)temp=temp->siguiente;
    

    if (temp==NULL) {
        printf("Posición inválida.\n");
        return Wait();
    }

    if(temp->siguiente!=NULL)temp->siguiente->anterior=temp->anterior;
    if(temp->anterior!=NULL)temp->anterior->siguiente=temp->siguiente;

    free(temp);
}

void mostrar(){
    if(cabeza==NULL){
        printf("La lista está vacía.\n");
        return Wait();
    }

    Nodo* temp=cabeza;
    while (temp!=NULL) {
        printf("%d ", temp->dato);
        temp=temp->siguiente;
    }
    printf("\n");
    
    return Wait();
}

void buscar(int elemento) {
    Nodo* temp=cabeza;
    int posicion=0;

    while(temp!=NULL) {
        if(temp->dato==elemento)printf("Elemento encontrado en la posición %d\n", posicion);
        temp=temp->siguiente;
        posicion++;
    }
    
    return Wait();
}

Nodo* merge(Nodo* izquierda, Nodo* derecha) {
    if (!izquierda)return derecha;
    if (!derecha)return izquierda;

    if(izquierda->dato<=derecha->dato){
        izquierda->siguiente=merge(izquierda->siguiente, derecha);
        izquierda->siguiente->anterior=izquierda;
        izquierda->anterior=NULL;
        return izquierda;
    }else{
        derecha->siguiente=merge(izquierda, derecha->siguiente);
        derecha->siguiente->anterior=derecha;
        derecha->anterior=NULL;
        return derecha;
    }
}

Nodo* dividir(Nodo* cabeza) {
    Nodo* rapido=cabeza;
    Nodo* lento=cabeza;

    while(rapido->siguiente && rapido->siguiente->siguiente){
        rapido=rapido->siguiente->siguiente;
        lento=lento->siguiente;
    }

    Nodo* mitad=lento->siguiente;
    
    lento->siguiente=NULL;
    if(mitad)mitad->anterior=NULL;
    
    return mitad;
}

Nodo* mergeSort(Nodo* cabeza) {
    if(!cabeza || !cabeza->siguiente)return cabeza;

    Nodo* mitad=dividir(cabeza);

    Nodo* izquierda=mergeSort(cabeza);
    Nodo* derecha=mergeSort(mitad);

    return merge(izquierda, derecha);
}

void ordenar(){
    cabeza=mergeSort(cabeza);
}

void actualizar(int posicion, int nuevoDato) {
    Nodo* temp=cabeza;

    for(int i=0; i<posicion && temp!=NULL; i++)temp=temp->siguiente;
    
    if(temp==NULL){
        printf("Posición inválida.\n");
        return Wait();
    }

    temp->dato=nuevoDato;
    return;
}

void menu(){
    int opcion, dato, posicion;
    printf("\nMenú:\n");
    printf("[1] Insertar al inicio\n");
    printf("[2] Insertar al final\n");
    printf("[3] Insertar en la posición i\n");
    printf("[4] Eliminar al inicio\n");
    printf("[5] Eliminar al final\n");
    printf("[6] Eliminar en la posición i\n");
    printf("[7] Mostrar\n");
    printf("[8] Buscar un elemento\n");
    printf("[9] Ordenar la lista\n");
    printf("[10] Actualizar un elemento\n");
    printf("[11] Salir\n");
    
    scanf("%d", &opcion);
    
    clean();

    switch(opcion){
        case 1:
            printf("Ingrese el dato: ");
            scanf("%d", &dato);
            insertarInicio(dato);
        break;
        case 2:
            printf("Ingrese el dato: ");
            scanf("%d", &dato);
            insertarFinal(dato);
        break;
        case 3:
            printf("Ingrese el dato: ");
            scanf("%d", &dato);
            printf("Ingrese la posición: ");
            scanf("%d", &posicion);
            insertarEnPosicion(dato, posicion);
        break;
        case 4:
            eliminarInicio();
        break;
        case 5:
            eliminarFinal();
        break;
        case 6:
            printf("Ingrese la posición: ");
            scanf("%d", &posicion);
            eliminarEnPosicion(posicion);
        break;
        case 7:
            mostrar();
        break;
        case 8:
            printf("Ingrese el elemento a buscar: ");
            scanf("%d", &dato);
            buscar(dato);
        break;
        case 9:
            ordenar();
        break;
        case 10:
            printf("Ingrese la posición: ");
            scanf("%d", &posicion);
            printf("Ingrese el nuevo dato: ");
            scanf("%d", &dato);
            actualizar(posicion, dato);
        break;
        case 11:
            printf("Saliendo...\n");
            return Wait();
        break;
        default:
            printf("Opción inválida.\n");
        break;
    }
    
    clean();
    
    return menu();
}

int main(){

    menu();

    return 0;
}
