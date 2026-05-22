/*
Universidad Autónoma de Aguascalientes
Centro de Ciencias Básicas
Departamento de Ciencias de la Computación
Materia: Métodos Numéricos

Práctica realizada por:
Juan Pablo Salinas Muñoz 340350
Sofia Isabel Soledad Limon 339987

Profesor:
Dr. en C. Luis Fernando Gutiérrez Marfileño

Descripción:
Este programa implementa el método gráfico para la función
f(x) = x^3 + 2x^2 + 3x - 5.
Permite capturar un intervalo inicial [a, b] y una tolerancia,
divide el intervalo en 20 secciones iguales, guarda los límites
de cada sección, evalúa la función en dichos puntos y muestra
los resultados en pantalla.
*/

#include <iostream>
#include <iomanip>
#include <vector>
#include <cmath>

using namespace std;

// Función dada en la práctica
double f(double x) {
    return pow(x, 3) + 2 * pow(x, 2) + 3 * x - 5;
}

// Función para mostrar menú
void mostrarMenu() {
    cout << "\n========== MENU PRINCIPAL ==========\n";
    cout << "1. Capturar intervalo inicial y tolerancia\n";
    cout << "2. Ejecutar metodo grafico\n";
    cout << "3. Salir\n";
    cout << "Seleccione una opcion: ";
}

// Función para ejecutar el método gráfico
void ejecutarMetodoGrafico(double a, double b, double tolerancia) {
    const int secciones = 20;
    vector<double> limites(secciones + 1);
    vector<double> valoresFx(secciones + 1);

    double paso = (b - a) / secciones;

    // Calcular límites de cada sección
    for (int i = 0; i <= secciones; i++) {
        limites[i] = a + i * paso;
        valoresFx[i] = f(limites[i]);
    }

    cout << "\n=============================================\n";
    cout << "       RESULTADOS DEL METODO GRAFICO\n";
    cout << "=============================================\n";
    cout << fixed << setprecision(9);
    cout << "Intervalo: [" << a << ", " << b << "]\n";
    cout << "Tolerancia: " << tolerancia << "\n";
    cout << "Numero de secciones: " << secciones << "\n";
    cout << "Tamano de cada seccion: " << paso << "\n\n";

    cout << left
         << setw(10) << "Indice"
         << setw(20) << "x"
         << setw(20) << "f(x)" << "\n";

    cout << "--------------------------------------------------\n";

    for (int i = 0; i <= secciones; i++) {
        cout << left
             << setw(10) << i
             << setw(20) << limites[i]
             << setw(20) << valoresFx[i] << "\n";
    }

    // Buscar cambio de signo
    bool encontroRaiz = false;
    for (int i = 0; i < secciones; i++) {
        if (valoresFx[i] == 0) {
            cout << "\nSe encontro una raiz exacta en x = " << limites[i] << "\n";
            encontroRaiz = true;
        } else if (valoresFx[i] * valoresFx[i + 1] < 0) {
            cout << "\nHay cambio de signo entre x = " << limites[i]
                 << " y x = " << limites[i + 1] << ".\n";

            // Interpolación lineal directa
            double xAprox = limites[i] - (valoresFx[i] * (limites[i + 1] - limites[i])) /
                                         (valoresFx[i + 1] - valoresFx[i]);

            cout << "Raiz aproximada por interpolacion directa: " << xAprox << "\n";
            cout << "f(x aproximada) = " << f(xAprox) << "\n";
            encontroRaiz = true;
        }
    }

    if (!encontroRaiz) {
        cout << "\nNo se detecto cambio de signo en las 20 secciones del intervalo.\n";
    }

    cout << "\nMagnitud de la tolerancia al error final: " << tolerancia << "\n";
    cout << "=============================================\n";
}

int main() {
    double a = -10.0, b = 10.0;
    double tolerancia = 1.0;
    int opcion;

    do {
        mostrarMenu();
        cin >> opcion;

        switch (opcion) {
            case 1:
                cout << "\nIngrese el valor inferior del intervalo: ";
                cin >> a;
                cout << "Ingrese el valor superior del intervalo: ";
                cin >> b;
                cout << "Ingrese la tolerancia al error: ";
                cin >> tolerancia;

                if (a >= b) {
                    cout << "\nError: el limite inferior debe ser menor que el superior.\n";
                    // Restaurar valores por defecto si el intervalo es inválido
                    a = -10.0;
                    b = 10.0;
                    tolerancia = 1.0;
                    cout << "Se restablecieron los valores por defecto.\n";
                } else {
                    cout << "\nDatos guardados correctamente.\n";
                }
                break;

            case 2:
                ejecutarMetodoGrafico(a, b, tolerancia);
                break;

            case 3:
                cout << "\nSaliendo del programa...\n";
                cout << "Control devuelto al sistema.\n";
                break;

            default:
                cout << "\nOpcion no valida. Intente de nuevo.\n";
        }

    } while (opcion != 3);

    return 0;
}