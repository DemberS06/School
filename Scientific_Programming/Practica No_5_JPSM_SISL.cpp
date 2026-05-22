/*
Universidad Autónoma de Aguascalientes
Centro de Ciencias Básicas
Departamento de Computación Inteligente

Materia: Programación Científica

Alumnos:
                Salinas Muñoz Juan Pablo - 340350
                Soledad Limón Sofia Isabel - 339987

Profesor: Dr. Luis Fernando Gutiérrez Marfileño

Descripción:
    Este programa implementa el método de la Regla Falsa
    para aproximar una raíz de la función:
            f(x) = e^(-x) - ln(x)
    usando un intervalo inicial, tolerancia y número máximo
    de iteraciones proporcionados por el usuario.
*/

#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;

double f(double x) {
    return exp(-x) - log(x);
}

void reglaFalsa(double xi, double xs, double tolerancia, int maxIter) {
    double xm, fxi, fxs, fxm;
    double error = 0.0;
    int iteracion = 0;

    fxi = f(xi);
    fxs = f(xs);

    if (fxi * fxs > 0) {
        cout << "\nNo es posible aplicar el metodo de Regla Falsa." << endl;
        cout << "f(xi) y f(xs) deben tener signos opuestos." << endl;
        return;
    }

    cout << "\n===============================================================\n";
    cout << " Metodo de Regla Falsa para f(x) = e^(-x) - ln(x)\n";
    cout << "===============================================================\n";
    cout << "Intervalo inicial: [" << fixed << setprecision(9) << xi
         << ", " << xs << "]" << endl;
    cout << "Tolerancia: " << tolerancia << endl;
    cout << "Max iteraciones: " << maxIter << endl << endl;

    cout << left
         << setw(12) << "Iteracion"
         << setw(16) << "xi"
         << setw(16) << "xs"
         << setw(16) << "xm"
         << setw(16) << "f(xm)"
         << setw(16) << "Error" << endl;

    do {
        xm = xs - (fxs * (xi - xs)) / (fxi - fxs);
        fxm = f(xm);

        if (iteracion == 0)
            error = fabs(fxm);
        else
            error = fabs(fxm);

        cout << left
             << setw(12) << iteracion + 1
             << setw(16) << fixed << setprecision(9) << xi
             << setw(16) << xs
             << setw(16) << xm
             << setw(16) << fxm
             << setw(16) << error << endl;

        if (fxm == 0.0)
            break;

        if (fxi * fxm < 0) {
            xs = xm;
            fxs = fxm;
        } else {
            xi = xm;
            fxi = fxm;
        }

        iteracion++;

    } while (error > tolerancia && iteracion < maxIter);

    cout << "\nAproximacion de la raiz: "
         << fixed << setprecision(9) << xm << endl;

    cout << "Valor de f(xm): "
         << fixed << setprecision(9) << fxm << endl;

    cout << "Numero de iteraciones: " << iteracion << endl;
}

int main() {
    int opcion;
    double xi, xs, tolerancia;
    int maxIter;

    do {
        cout << "\n=========== MENU PRINCIPAL ===========" << endl;
        cout << "1. Ejecutar metodo de Regla Falsa" << endl;
        cout << "2. Salir" << endl;
        cout << "Seleccione una opcion: ";
        cin >> opcion;

        if (cin.fail()) {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "\nEntrada no valida." << endl;
            continue;
        }

        switch (opcion) {

        case 1:
            cout << "\nIngrese xi: ";
            cin >> xi;

            cout << "Ingrese xs: ";
            cin >> xs;

            cout << "Ingrese tolerancia: ";
            cin >> tolerancia;

            cout << "Ingrese numero maximo de iteraciones: ";
            cin >> maxIter;

            reglaFalsa(xi, xs, tolerancia, maxIter);
            break;

        case 2:
            cout << "\nSaliendo del programa..." << endl;
            break;

        default:
            cout << "\nOpcion no valida." << endl;
        }

    } while (opcion != 2);

    return 0;
}