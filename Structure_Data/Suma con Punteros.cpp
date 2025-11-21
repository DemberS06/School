#include <stdio.h>

int main() {
    int a, b, suma;
    int *p1, *p2;

    // Pedir los dos números
    printf("Ingresa el primer número: ");
    scanf("%d", &a);

    printf("Ingresa el segundo número: ");
    scanf("%d", &b);

    // Asignar punteros a las variables
    p1 = &a;
    p2 = &b;

    // Sumar usando punteros
    suma = *p1 + *p2;

    printf("La suma de %d y %d es: %d\n", *p1, *p2, suma);

    return 0;
}
