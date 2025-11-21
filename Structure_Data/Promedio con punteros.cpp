#include <stdio.h>

float calcularPromedio(int *vec, int n) {
    int suma = 0;
    for (int i = 0; i < n; i++) {
        suma += *(vec + i); // Acceso con puntero
    }
    return (float)suma / n;
}

int main() {
    int numeros[10];
    int *ptr = numeros;

    printf("Ingresa 10 números enteros:\n");
    for (int i = 0; i < 10; i++) {
        printf("Número %d: ", i + 1);
        scanf("%d", &numeros[i]);
    }

    float promedio = calcularPromedio(ptr, 10);

    printf("El promedio es: %.2f\n", promedio);

    return 0;
}
