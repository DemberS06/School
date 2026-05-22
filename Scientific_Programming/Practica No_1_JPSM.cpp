/*
Universidad Autónoma de Aguascalientes
Centro de Ciencias Básicas
Departamento de Computación Inteligente

Alumno:   Salinas Muñoz Juan Pablo - 340350
Profesor: Dr. Luis Fernando Gutiérrez Marfileño

Descripción:
Programa que evalúa la función f(x) = e^(5x) - x usando la serie de Taylor de e^(5x)
centrada en 0. Suma términos hasta que la magnitud del siguiente término (estimación
del error de truncamiento) sea menor que la tolerancia ingresada por el usuario.

Serie de Taylor de e^(5x) (en torno a 0):
e^(5x) = sum(n, 0 -> inf): (5x)^n / n!
Por lo tanto:
f(x) = e^(5x) - x = ( sum(n, 0 -> inf): (5x)^n / n! ) - x
*/

#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;

int main() {
    double x;
    double error;
    int cifrasMostrar;

    cout << "Aproximacion de f(x) = e^(5x) - x mediante serie de Taylor (Maclaurin)\n\n";

    cout << "Usa la expansion:\n";
    cout << "e^(5x) = 1 + (5x) + (5x)^2/2! + (5x)^3/3! + ...\n\n";

    cout << "Ingresa el valor de x: ";
    cin >> x;

    do {
        cout << "Ingresa el error maximo permitido (positivo), ejemplo 1e-6: ";
        cin >> error;
        if (error <= 0.0) {
            cout << "Error: debes ingresar un valor positivo.\n";
        }
    } while (error <= 0.0);

    cout << "Ingresa cuantas cifras decimales mostrar (ej. 6): ";
    cin >> cifrasMostrar;
    if (cifrasMostrar < 0) cifrasMostrar = 6; // valor por defecto si introduce algo raro

    // Inicializacion
    double sumaExp = 1.0;        // t0 = 1
    double termino = 1.0;        // termino actual = (5x)^0 / 0! = 1
    int n = 1;                   // usamos n para calcular el siguiente termino: (5x)^n / n!

    // Generamos terminos hasta que el siguiente termino sea menor que 'error'
    while (true) {
        double siguienteTermino = termino * (5.0 * x) / n; // relación recursiva
        if (fabs(siguienteTermino) < error) {
            // criterio de paro: no añadimos el siguiente término
            break;
        }
        sumaExp += siguienteTermino;
        termino = siguienteTermino;
        n++;
    }

    double resultado = sumaExp - x;

    cout << fixed << setprecision(cifrasMostrar);
    cout << "\n-----------------------------------------------------\n";
    cout << "Valor aproximado de f(x) = e^(5x) - x : " << resultado << "\n";
    cout << "Numero de terminos usados en la serie de e^(5x): " << n << "\n";
    cout << "Criterio de paro: |siguiente termino| < " << error << "\n";
    cout << "-----------------------------------------------------\n";

    return 0;
}