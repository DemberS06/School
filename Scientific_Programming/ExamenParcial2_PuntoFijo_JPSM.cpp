/*
========================================================
Nombre de la Institución: Universidad Autónoma de Aguascalientes
Nombre del Centro: Centro de Ciencias Básicas
Nombre del Departamento: Departamento de Computación Inteligente
Nombre del alumno: Juan Pablo Salinas Muñoz
Nombre del profesor: Luis Fernando Marfileño
========================================================

Programa: Método de Bisección
Descripción:
Este programa utiliza el método de Bisección para aproximar
una raíz de la ecuación f(x) = -x^3 - 5x^2 + 4x + 5 = 0.

El programa solicita:
- Límite inferior del intervalo
- Límite superior del intervalo
- Error de truncamiento e

Y muestra:
- Las iteraciones realizadas
- La raíz aproximada si existe cambio de signo en el intervalo
- Un mensaje indicando que no se encontró raíz en el intervalo si no se puede aplicar el método
*/

#include <iostream>
#include <cmath>
#include <iomanip>

using namespace std;

// Función original
double f(double x) {
    return -pow(x, 3) - 5 * pow(x, 2) + 4 * x + 5;
}

int main() {
    double xi, xs, xm, e, error;
    double fxi, fxs, fxm;
    int iter = 0;
    const int MAX_ITER = 1000;

    cout << "=============================================\n";
    cout << "         METODO DE BISECCION\n";
    cout << "=============================================\n";
    cout << "Este programa aproxima una raiz de la ecuacion:\n";
    cout << "f(x) = -x^3 - 5x^2 + 4x + 5 = 0\n";
    cout << "Un intervalo sugerido para esta funcion es [-1, 0]\n\n";

    cout << "Ingrese el limite inferior del intervalo xi: ";
    cin >> xi;

    cout << "Ingrese el limite superior del intervalo xs: ";
    cin >> xs;

    cout << "Ingrese la magnitud del error de truncamiento e: ";
    cin >> e;

    if (e <= 0) {
        cout << "\nError: la magnitud del error debe ser mayor que 0.\n";
        return 0;
    }

    fxi = f(xi);
    fxs = f(xs);

    if (fxi == 0.0) {
        cout << "\nLa raiz exacta es xi = " << fixed << setprecision(10) << xi << endl;
        return 0;
    }

    if (fxs == 0.0) {
        cout << "\nLa raiz exacta es xs = " << fixed << setprecision(10) << xs << endl;
        return 0;
    }

    if (fxi * fxs > 0) {
        cout << "\nNo se encontraron raices en el intervalo dado.\n";
        cout << "f(xi) y f(xs) deben tener signos opuestos para aplicar Biseccion.\n";
        cout << "f(" << xi << ") = " << fxi << endl;
        cout << "f(" << xs << ") = " << fxs << endl;
        return 0;
    }

    cout << "\n=====================================================================\n";
    cout << " Iteraciones del Metodo de Biseccion\n";
    cout << "=====================================================================\n";

    cout << left
         << setw(12) << "Iteracion"
         << setw(16) << "xi"
         << setw(16) << "xs"
         << setw(16) << "xm"
         << setw(16) << "f(xm)"
         << setw(16) << "Error" << endl;

    error = 1e9;

    while (error > e && iter < MAX_ITER) {
        xm = (xi + xs) / 2.0;
        fxm = f(xm);

        if (iter == 0) {
            error = fabs(xs - xi);
        } else {
            error = fabs(fxm);
        }

        cout << left
             << setw(12) << iter + 1
             << setw(16) << fixed << setprecision(10) << xi
             << setw(16) << xs
             << setw(16) << xm
             << setw(16) << fxm
             << setw(16) << error << endl;

        if (fxm == 0.0) {
            cout << "\nSe encontro una raiz exacta.\n";
            break;
        }

        if (fxi * fxm < 0) {
            xs = xm;
            fxs = fxm;
        } else {
            xi = xm;
            fxi = fxm;
        }

        iter++;
    }

    cout << fixed << setprecision(10);

    if (iter < MAX_ITER) {
        cout << "\nLa raiz aproximada es: " << xm << endl;
        cout << "f(" << xm << ") = " << f(xm) << endl;
        cout << "Numero de iteraciones: " << iter << endl;
        cout << "Error final: " << error << endl;
    } else {
        cout << "\nNo se encontro una raiz con la precision solicitada.\n";
        cout << "El metodo alcanzo el maximo numero de iteraciones.\n";
    }

    return 0;
}