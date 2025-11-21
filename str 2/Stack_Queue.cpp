/**
 * @file main.cpp
 * @brief Simulación de un sistema KYC de venta de hamburguesas usando estructuras de datos pila y cola.
 * @date 4 de Abril de 2025
 * @course Estructuras Computacionales, ICI - Segundo semestre
 */
/**
 * En esta franquicia de hamburguesas, los productos (hamburguesas) se apilan,
 * y los clientes forman una fila. Cuando un cliente es atendido, indica cuántas
 * hamburguesas desea, pero el tamaño y tipo de cada una es el que corresponde
 * al tope de la pila en ese momento (LIFO).
 */

 #include <bits/stdc++.h>
 using namespace std;
 
 /**
  * @brief Limpia la pantalla usando secuencias de escape ANSI.
  */
 void clean() {
     // Se envían varias veces para asegurar la limpieza completa
     printf("\033[2J\033[H");
     printf("\033[2J\033[H");
     printf("\033[2J\033[H");
 }
 
 /**
  * @brief Pausa la ejecución durante un número de segundos y limpia la pantalla.
  * @param t Tiempo de espera en segundos (por defecto 1).
  */
 void wait(int t = 1) {
     this_thread::sleep_for(chrono::seconds(t));
     clean();
 }
 
 /**
  * @struct Persona
  * @brief Representa a un cliente con nombre y cantidad de hamburguesas deseadas.
  */
 struct Persona {
     string name;  /**< Nombre del cliente */
     int cant;     /**< Cantidad de hamburguesas que desea */
 };
 
 /**
  * @struct Hamburguesa
  * @brief Representa una hamburguesa con tamaño y tipo.
  */
 struct Hamburguesa {
     string tam;  /**< Tamaño de la hamburguesa */
     string tip;  /**< Tipo de la hamburguesa */
 };
 
 // Estructuras de datos globales
 queue<Persona> Fila;        /**< Cola de clientes en espera */
 stack<Hamburguesa> Pila;    /**< Pila de hamburguesas preparadas */
 
 /**
  * @brief Entrega la hamburguesa que está en el tope de la pila.
  * Poppea la hamburguesa y muestra sus características.
  */
 void Entregar_Hamburguesa() {
     Hamburguesa ham = Pila.top();
     Pila.pop();
     cout << "Hamburguesa tamaño " << ham.tam << " de " << ham.tip << "\n";
 }
 
 /**
  * @brief Atiende al siguiente cliente en la fila.
  * Comprueba si hay suficientes hamburguesas y entrega la cantidad solicitada.
  * Si no hay clientes o hamburguesas suficientes, muestra un mensaje.
  * Tras atender, espera y llama recursivamente para atender al siguiente.
  */
 void Atender_Cliente() {
     if (Fila.empty()) {
         printf("No hay clientes\n");
         return wait();
     }
     Persona cliente = Fila.front();
     if (cliente.cant > (int)Pila.size()) {
         printf("No hay suficientes hamburguesas\n");
         return wait();
     }
 
     Fila.pop();
     cout << cliente.name << " lleva " << cliente.cant << " hamburguesas:\n";
     while (cliente.cant--) Entregar_Hamburguesa();
     
     wait(3);
     // Llamada recursiva para continuar atendiendo
     Atender_Cliente();
 }
 
 /**
  * @brief Registra un nuevo cliente en la fila.
  * Solicita nombre y cantidad deseada.
  */
 void Recibir_Cliente() {
     Persona cliente;
     printf("Ingrese el nombre del cliente:\n");
     cin >> cliente.name;
     printf("Ingrese la cantidad de hamburguesas que llevará:\n");
     cin >> cliente.cant;
     Fila.push(cliente);
     printf("Ingresando...\n");
     wait();
 }
 
 /**
  * @brief Prepara una hamburguesa y la apila.
  * Solicita tamaño y tipo al usuario.
  */
 void Preparar_Hamburguesa() {
     Hamburguesa ham;
     printf("Ingrese el tamaño de la hamburguesa:\n");
     cin >> ham.tam;
     printf("Ingrese el tipo de la hamburguesa:\n");
     cin >> ham.tip;
     Pila.push(ham);
     printf("Ingresando...\n");
     wait();
 }
 
 /**
  * @brief Muestra el menú principal y gestiona las opciones del usuario.
  * Opciones:
  *  1. Registrar cliente
  *  2. Preparar hamburguesa
  *  3. Salir
  * Tras cada acción, se atiende a los clientes en fila.
  */
 void menu() {
     int opcion = 0;
     while (true) {
         printf("\nMenú:\n");
         printf("[1] Llega un cliente\n");
         printf("[2] Preparas una hamburguesa\n");
         printf("[3] Salir\n");
         scanf("%d", &opcion);
         clean();
 
         switch (opcion) {
             case 1:
                 Recibir_Cliente();
                 break;
             case 2:
                 Preparar_Hamburguesa();
                 break;
             case 3:
                 printf("Saliendo...\n");
                 wait();
                 return;
             default:
                 printf("Opción no válida\n");
                 wait();
                 continue;
         }
         // Tras la acción, se atiende a todos los clientes posibles
         Atender_Cliente();
         clean();
     }
 }
 
 /**
  * @brief Función principal.
  * Inicia el menú de la aplicación.
  * @return int Código de salida (0 si todo salió bien).
  */
 int main() {
     menu();
     return 0;
 }
 








