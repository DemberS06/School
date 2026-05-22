/*
Universidad Autónoma de Aguascalientes
Centro de Ciencias Básicas
Departamento de Computación Inteligente

Alumno:   Salinas Muñoz Juan Pablo - 340350
Profesor: Dr. Luis Fernando Gutiérrez Marfileño

Descripción:
    Este programa implementa el método de bisecciones sucesivas
    para aproximar una raíz de la función
    f(x) = -x^3 - 5x^2 + 4x + 5
    dentro de un intervalo dado por el usuario y con una tolerancia
    de error también proporcionada por el usuario.
*/

#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;

double f(double x) {
    return -pow(x, 3) - 5 * pow(x, 2) + 4 * x + 5;
}

void biseccion(double xi, double xs, double tolerancia) {
    double xm, fxi, fxs, fxm;
    double error;
    int iteracion = 0;

    fxi = f(xi);
    fxs = f(xs);

    if (fxi == 0.0) {
        cout << "\nLa raiz exacta es xi = " << fixed << setprecision(9) << xi << endl;
        return;
    }

    if (fxs == 0.0) {
        cout << "\nLa raiz exacta es xs = " << fixed << setprecision(9) << xs << endl;
        return;
    }

    if (fxi * fxs > 0) {
        cout << "\nNo es posible aplicar el metodo de biseccion." << endl;
        cout << "f(xi) y f(xs) deben tener signos opuestos." << endl;
        cout << "f(" << fixed << setprecision(9) << xi << ") = " << fxi << endl;
        cout << "f(" << fixed << setprecision(9) << xs << ") = " << fxs << endl;
        cout << "\nUn intervalo sugerido para esta funcion es [-1, 0]" << endl;
        return;
    }

    cout << "\n=====================================================================\n";
    cout << " Metodo de Bisecciones Sucesivas para f(x) = -x^3 - 5x^2 + 4x + 5\n";
    cout << "=====================================================================\n";
    cout << "Intervalo inicial: [" << fixed << setprecision(9) << xi
         << ", " << xs << "]" << endl;
    cout << "Tolerancia: " << tolerancia << endl << endl;

    cout << left
         << setw(12) << "Iteracion"
         << setw(16) << "xi"
         << setw(16) << "xs"
         << setw(16) << "xm"
         << setw(16) << "f(xm)"
         << setw(16) << "Error" << endl;

    do {
        xm = (xi + xs) / 2.0;
        fxm = f(xm);

        if (iteracion == 0) {
            error = fabs(xs - xi);
        } else {
            error = fabs(fxm);
        }

        cout << left
             << setw(12) << iteracion + 1
             << setw(16) << fixed << setprecision(9) << xi
             << setw(16) << xs
             << setw(16) << xm
             << setw(16) << fxm
             << setw(16) << error << endl;

        if (fxm == 0.0) {
            cout << "\nSe encontro una raiz exacta." << endl;
            break;
        }

        if (fxi * fxm < 0) {
            xs = xm;
            fxs = fxm;
        } else {
            xi = xm;
            fxi = fxm;
        }

        iteracion++;

    } while (fabs(fxm) > tolerancia);

    cout << "\nAproximacion de la raiz: " << fixed << setprecision(9) << xm << endl;
    cout << "Valor de f(xm): " << fixed << setprecision(9) << fxm << endl;
    cout << "Numero de iteraciones: " << iteracion << endl;
}

int main() {
    int opcion;
    double xi, xs, tolerancia;

    do {
        cout << "\n=========== MENU PRINCIPAL ===========" << endl;
        cout << "1. Ejecutar metodo de biseccion" << endl;
        cout << "2. Salir" << endl;
        cout << "Seleccione una opcion: ";
        cin >> opcion;

        if (cin.fail()) {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "\nEntrada no valida. Intente de nuevo." << endl;
            continue;
        }

        switch (opcion) {
            case 1:
                cout << "\nIngrese el valor de xi: ";
                cin >> xi;
                while (cin.fail()) {
                    cin.clear();
                    cin.ignore(1000, '\n');
                    cout << "Entrada no valida. Ingrese nuevamente xi: ";
                    cin >> xi;
                }

                cout << "Ingrese el valor de xs: ";
                cin >> xs;
                while (cin.fail()) {
                    cin.clear();
                    cin.ignore(1000, '\n');
                    cout << "Entrada no valida. Ingrese nuevamente xs: ";
                    cin >> xs;
                }

                cout << "Ingrese la tolerancia al error: ";
                cin >> tolerancia;
                while (cin.fail() || tolerancia <= 0) {
                    cin.clear();
                    cin.ignore(1000, '\n');
                    cout << "Entrada no valida. Ingrese nuevamente la tolerancia: ";
                    cin >> tolerancia;
                }

                biseccion(xi, xs, tolerancia);
                break;

            case 2:
                cout << "\nSaliendo del programa..." << endl;
                cout << "Control devuelto al sistema." << endl;
                break;

            default:
                cout << "\nOpcion no valida. Intente de nuevo." << endl;
                break;
        }

    } while (opcion != 2);

    return 0;
}