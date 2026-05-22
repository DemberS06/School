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
    Este programa implementa la Regla de Cramer
    para resolver sistemas de ecuaciones lineales
    mediante el cálculo de determinantes.
*/

#include <iostream>
#include <iomanip>

using namespace std;

double determinante3x3(double A[3][3]) {

    return
        A[0][0] * (
            A[1][1] * A[2][2] -
            A[1][2] * A[2][1]
        )
        -
        A[0][1] * (
            A[1][0] * A[2][2] -
            A[1][2] * A[2][0]
        )
        +
        A[0][2] * (
            A[1][0] * A[2][1] -
            A[1][1] * A[2][0]
        );
}

void copiarMatriz(double origen[3][3], double destino[3][3]) {

    for(int i = 0; i < 3; i++) {
        for(int j = 0; j < 3; j++) {
            destino[i][j] = origen[i][j];
        }
    }
}

void reglaCramer() {

    double A[3][3];
    double B[3];

    double Ax[3][3];
    double Ay[3][3];
    double Az[3][3];

    double detA, detAx, detAy, detAz;
    double x, y, z;

    cout << "\nIngrese los coeficientes de la matriz A:\n";

    for(int i = 0; i < 3; i++) {
        for(int j = 0; j < 3; j++) {

            cout << "A[" << i + 1 << "][" << j + 1 << "]: ";
            cin >> A[i][j];
        }
    }

    cout << "\nIngrese los valores del vector independiente:\n";

    for(int i = 0; i < 3; i++) {

        cout << "B[" << i + 1 << "]: ";
        cin >> B[i];
    }

    detA = determinante3x3(A);

    if(detA == 0) {

        cout << "\nEl sistema no tiene solucion unica." << endl;
        cout << "det(A) = 0" << endl;
        return;
    }

    copiarMatriz(A, Ax);
    copiarMatriz(A, Ay);
    copiarMatriz(A, Az);

    for(int i = 0; i < 3; i++) {
        Ax[i][0] = B[i];
        Ay[i][1] = B[i];
        Az[i][2] = B[i];
    }

    detAx = determinante3x3(Ax);
    detAy = determinante3x3(Ay);
    detAz = determinante3x3(Az);

    x = detAx / detA;
    y = detAy / detA;
    z = detAz / detA;

    cout << "\n======================================" << endl;
    cout << "Resultados por Regla de Cramer" << endl;
    cout << "======================================" << endl;

    cout << fixed << setprecision(9);

    cout << "det(A)  = " << detA << endl;
    cout << "det(Ax) = " << detAx << endl;
    cout << "det(Ay) = " << detAy << endl;
    cout << "det(Az) = " << detAz << endl;

    cout << "\nSoluciones:" << endl;

    cout << "x1 = " << x << endl;
    cout << "x2 = " << y << endl;
    cout << "x3 = " << z << endl;
}

int main() {

    int opcion;

    do {

        cout << "\n=========== MENU PRINCIPAL ===========" << endl;
        cout << "1. Ejecutar Regla de Cramer" << endl;
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

                reglaCramer();
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