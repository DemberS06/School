#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

// 1. Suma de dos números usando punteros y malloc
void sumaConPunteros() {
    int *a = malloc(sizeof(int));
    int *b = malloc(sizeof(int));

    if (!a || !b) {
        fprintf(stderr, "Error al asignar memoria\n");
        exit(EXIT_FAILURE);
    }

    printf("\n[Suma de 2 enteros]\n");
    printf("Ingresa el primer número: ");
    scanf("%d", a);
    printf("Ingresa el segundo número: ");
    scanf("%d", b);

    printf("La suma de %d y %d es: %d\n", *a, *b, *a + *b);

    free(a);
    free(b);
}

// 2. Promedio de 10 números usando memoria dinámica
void promedioConPunteros() {
    const int n = 10;
    int *numeros = malloc(n * sizeof(int));
    if (!numeros) {
        fprintf(stderr, "Error al asignar memoria\n");
        exit(EXIT_FAILURE);
    }

    printf("\n[Promedio de 10 números]\n");
    for (int i = 0; i < n; i++) {
        printf("Número %d: ", i + 1);
        scanf("%d", &numeros[i]);
    }

    int suma = 0;
    for (int i = 0; i < n; i++) {
        suma += numeros[i];
    }
    printf("El promedio es: %.2f\n", (float)suma / n);

    free(numeros);
}

// 3. Conversión de cadena a minúsculas y mayúsculas con malloc
void conversionCadena() {
    char buffer[256];

    printf("\n[Conversión de cadena]\n");
    // Limpiar el buffer de scanf anterior
    getchar();
    printf("Ingresa una cadena de texto: ");
    fgets(buffer, sizeof(buffer), stdin);

    // Eliminar '\n' final
    size_t len = strlen(buffer);
    if (len > 0 && buffer[len - 1] == '\n')
        buffer[--len] = '\0';

    char *minusculas = malloc(len + 1);
    char *mayusculas = malloc(len + 1);
    if (!minusculas || !mayusculas) {
        fprintf(stderr, "Error al asignar memoria\n");
        exit(EXIT_FAILURE);
    }

    strcpy(minusculas, buffer);
    strcpy(mayusculas, buffer);

    // Convertir a minúsculas
    for (char *p = minusculas; *p; p++)
        *p = tolower((unsigned char)*p);

    // Convertir a mayúsculas
    for (char *p = mayusculas; *p; p++)
        *p = toupper((unsigned char)*p);

    printf("En minúsculas: %s\n", minusculas);
    printf("En mayúsculas: %s\n", mayusculas);

    free(minusculas);
    free(mayusculas);
}

int main() {
    int opcion;

    do {
        printf("\n=== Menú de Operaciones ===\n");
        printf("1. Sumar 2 enteros\n");
        printf("2. Promedio de 10 enteros\n");
        printf("3. Convertir cadena (minúsculas/mayúsculas)\n");
        printf("4. Salir\n");
        printf("Elige una opción: ");
        scanf("%d", &opcion);

        switch (opcion) {
            case 1:
                sumaConPunteros();
                break;
            case 2:
                promedioConPunteros();
                break;
            case 3:
                conversionCadena();
                break;
            case 4:
                printf("¡Hasta luego!\n");
                break;
            default:
                printf("Opción no válida, intenta de nuevo.\n");
        }
    } while (opcion != 4);

    return 0;
}
