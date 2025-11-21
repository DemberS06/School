#include <stdio.h>
#include <ctype.h> // Para tolower y toupper

void convertirMinusculas(char *cadena) {
    while (*cadena != '\0') {
        *cadena = tolower(*cadena);
        cadena++;
    }
}

void convertirMayusculas(char *cadena) {
    while (*cadena != '\0') {
        *cadena = toupper(*cadena);
        cadena++;
    }
}

int main() {
    char texto[100];

    printf("Ingresa una cadena de texto: ");
    fgets(texto, sizeof(texto), stdin); // Lee la cadena con espacios

    // Convertir a minúsculas
    char minusculas[100];
    char mayusculas[100];

    // Copiar original para mantenerlo
    for (int i = 0; texto[i] != '\0'; i++) {
        minusculas[i] = texto[i];
        mayusculas[i] = texto[i];
    }

    convertirMinusculas(minusculas);
    convertirMayusculas(mayusculas);

    printf("En minúsculas: %s", minusculas);
    printf("En mayúsculas: %s", mayusculas);

    return 0;
}
