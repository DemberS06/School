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
    Este programa implementa el Método de Gauss
    para resolver sistemas de ecuaciones lineales
    mediante eliminación hacia adelante y
    sustitución hacia atrás.
*/

#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;

const int MAX = 10;

void metodoGauss() {

    int n;

    double A[MAX][MAX + 1];
    double x[MAX];

    cout << "\nIngrese la dimension del sistema (max 10): ";
    cin >> n;

    if(n <= 0 || n > MAX) {

        cout << "\nDimension invalida." << endl;
        return;
    }

    cout << "\nIngrese los coeficientes de la matriz aumentada:\n";

    for(int i = 0; i < n; i++) {

        for(int j = 0; j < n + 1; j++) {

            if(j == n)
                cout << "Termino independiente [" << i + 1 << "]: ";
            else
                cout << "A[" << i + 1 << "][" << j + 1 << "]: ";

            cin >> A[i][j];
        }
    }

    // Eliminacion hacia adelante
    for(int k = 0; k < n - 1; k++) {

        if(A[k][k] == 0) {

            cout << "\nDivision entre cero detectada." << endl;
            return;
        }

        for(int i = k + 1; i < n; i++) {

            double factor = A[i][k] / A[k][k];

            for(int j = k; j < n + 1; j++) {

                A[i][j] = A[i][j] - factor * A[k][j];
            }
        }
    }

    // Sustitucion hacia atras
    for(int i = n - 1; i >= 0; i--) {

        double suma = 0;

        for(int j = i + 1; j < n; j++) {

            suma += A[i][j] * x[j];
        }

        x[i] = (A[i][n] - suma) / A[i][i];
    }

    cout << "\n======================================" << endl;
    cout << "Solucion del sistema" << endl;
    cout << "======================================" << endl;

    cout << fixed << setprecision(9);

    for(int i = 0; i < n; i++) {

        cout << "x" << i + 1 << " = " << x[i] << endl;
    }
}

int main() {

    int opcion;

    do {

        cout << "\n=========== MENU PRINCIPAL ===========" << endl;
        cout << "1. Ejecutar Metodo de Gauss" << endl;
        cout << "2. Salir" << endl;
        cout << "Seleccione una opcion: ";
        cin >> opcion;

        if(cin.fail()) {

            cin.clear();
            cin.ignore(1000, '\n');

            cout << "\nEntrada no valida." << endl;
            continue;
        }

        switch(opcion) {

            case 1:

                metodoGauss();
                break;

            case 2:

                cout << "\nSaliendo del programa..." << endl;
                cout << "Control devuelto al sistema." << endl;
                break;

            default:

                cout << "\nOpcion no valida." << endl;
        }

    } while(opcion != 2);

    return 0;
}