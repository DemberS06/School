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
    Este programa implementa el método de Newton Raphson
    para aproximar la raíz de la función:
            f(x) = cos(x) - x
    El valor inicial se ingresa en grados y se convierte
    internamente a radianes para los cálculos.
*/

#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;

double f(double x) {
    return cos(x) - x;
}

double df(double x) {
    return -sin(x) - 1.0;
}

double gradosARadianes(double grados) {
    return grados * M_PI / 180.0;
}

void newtonRaphson(double x0_grados, double tolerancia, int maxIter) {
    double x0 = gradosARadianes(x0_grados);
    double x1, fx, dfx;
    double error;
    int iter = 0;

    cout << "\n============================================================\n";
    cout << "Metodo de Newton Raphson para f(x) = cos(x) - x\n";
    cout << "============================================================\n";

    cout << left
         << setw(12) << "Iteracion"
         << setw(18) << "x"
         << setw(18) << "f(x)"
         << setw(18) << "f'(x)"
         << setw(18) << "Error" << endl;

    do {
        fx = f(x0);
        dfx = df(x0);

        if (dfx == 0) {
            cout << "\nDerivada igual a cero. No se puede continuar." << endl;
            return;
        }

        x1 = x0 - fx / dfx;
        error = fabs(x1 - x0);

        cout << left
             << setw(12) << iter + 1
             << setw(18) << fixed << setprecision(9) << x0
             << setw(18) << fx
             << setw(18) << dfx
             << setw(18) << error << endl;

        x0 = x1;
        iter++;

    } while (error > tolerancia && iter < maxIter);

    cout << "\nRaiz aproximada: "
         << fixed << setprecision(9) << x0 << endl;

    cout << "f(x): "
         << fixed << setprecision(9) << f(x0) << endl;

    cout << "Iteraciones: " << iter << endl;
}

int main() {
    int opcion;
    double x0, tolerancia;
    int maxIter;

    do {
        cout << "\n=========== MENU PRINCIPAL ===========" << endl;
        cout << "1. Ejecutar metodo Newton Raphson" << endl;
        cout << "2. Salir" << endl;
        cout << "Seleccione opcion: ";
        cin >> opcion;

        if (cin.fail()) {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "Entrada no valida" << endl;
            continue;
        }

        switch (opcion) {

        case 1:

            cout << "\nIngrese valor inicial (grados): ";
            cin >> x0;

            cout << "Ingrese tolerancia: ";
            cin >> tolerancia;

            cout << "Ingrese max iteraciones: ";
            cin >> maxIter;

            newtonRaphson(x0, tolerancia, maxIter);
            break;

        case 2:
            cout << "\nSaliendo..." << endl;
            break;

        default:
            cout << "\nOpcion invalida" << endl;
        }

    } while (opcion != 2);

    return 0;
}