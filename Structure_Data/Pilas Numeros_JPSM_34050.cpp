#include <stdio.h>
#include <stdlib.h>

// Definición del nodo para la pila
typedef struct Node {
    int data;
    struct Node* next;
} Node;

// Prototipos de las funciones
void push(Node** top, int value);
int pop(Node** top);
int peek(Node* top);
int isEmpty(Node* top);
void display(Node* top);
void freeStack(Node** top);

int main() {
    Node* stackTop = NULL;  // Puntero al tope de la pila
    int opcion, valor;

    do {
        printf("\n=== Menú Pila con Nodos ===\n");
        printf("1. Apilar (push)\n");
        printf("2. Desapilar (pop)\n");
        printf("3. Ver tope (peek)\n");
        printf("4. Mostrar pila\n");
        printf("5. Salir\n");
        printf("Elige una opción: ");
        if (scanf("%d", &opcion) != 1) {
            fprintf(stderr, "Entrada no válida\n");
            break;
        }

        switch (opcion) {
            case 1:
                printf("Valor a apilar: ");
                scanf("%d", &valor);
                push(&stackTop, valor);
                printf("Apilado: %d\n", valor);
                break;
            case 2:
                if (!isEmpty(stackTop)) {
                    valor = pop(&stackTop);
                    printf("Desapilado: %d\n", valor);
                } else {
                    printf("La pila está vacía\n");
                }
                break;
            case 3:
                if (!isEmpty(stackTop)) {
                    valor = peek(stackTop);
                    printf("Tope actual: %d\n", valor);
                } else {
                    printf("La pila está vacía\n");
                }
                break;
            case 4:
                display(stackTop);
                break;
            case 5:
                printf("Saliendo...\n");
                break;
            default:
                printf("Opción inválida, intenta de nuevo.\n");
        }
    } while (opcion != 5);

    freeStack(&stackTop);
    return 0;
}

// Inserta un nuevo nodo al tope de la pila
void push(Node** top, int value) {
    Node* newNode = (Node*)malloc(sizeof(Node));
    if (!newNode) {
        fprintf(stderr, "Error al asignar memoria\n");
        exit(EXIT_FAILURE);
    }
    newNode->data = value;
    newNode->next = *top;
    *top = newNode;
}

// Elimina el nodo del tope y devuelve su valor
int pop(Node** top) {
    if (*top == NULL) {
        fprintf(stderr, "Error: pila vacía\n");
        exit(EXIT_FAILURE);
    }
    Node* temp = *top;
    int val = temp->data;
    *top = temp->next;
    free(temp);
    return val;
}

// Devuelve el valor del tope sin eliminarlo
int peek(Node* top) {
    if (top == NULL) {
        fprintf(stderr, "Error: pila vacía\n");
        exit(EXIT_FAILURE);
    }
    return top->data;
}

// Comprueba si la pila está vacía
int isEmpty(Node* top) {
    return top == NULL;
}

// Muestra todos los elementos de la pila desde el tope hacia abajo
void display(Node* top) {
    if (top == NULL) {
        printf("La pila está vacía\n");
        return;
    }
    printf("Pila (tope -> fondo): ");
    for (Node* cur = top; cur != NULL; cur = cur->next) {
        printf("%d ", cur->data);
    }
    printf("\n");
}

// Libera todos los nodos de la pila
void freeStack(Node** top) {
    Node* cur = *top;
    while (cur) {
        Node* next = cur->next;
        free(cur);
        cur = next;
    }
    *top = NULL;
}
