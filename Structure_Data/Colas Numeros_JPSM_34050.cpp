#include <stdio.h>
#include <stdlib.h>

// Definición del nodo para la cola
typedef struct Node {
    int data;
    struct Node* next;
} Node;

// Estructura de la cola con punteros a frente y fondo
typedef struct {
    Node* front;
    Node* rear;
} Queue;

// Prototipos
Queue* createQueue();
void enqueue(Queue* q, int value);
int dequeue(Queue* q);
int peek(Queue* q);
int isEmpty(Queue* q);
void display(Queue* q);
void freeQueue(Queue* q);

int main() {
    Queue* q = createQueue();
    int opcion, valor;

    do {
        printf("\n=== Menú Cola de Enteros ===\n");
        printf("1. Encolar (enqueue)\n");
        printf("2. Desencolar (dequeue)\n");
        printf("3. Ver frente (peek)\n");
        printf("4. Mostrar cola\n");
        printf("5. Salir\n");
        printf("Elige una opción: ");
        scanf("%d", &opcion);

        switch (opcion) {
            case 1:
                printf("Valor a encolar: ");
                scanf("%d", &valor);
                enqueue(q, valor);
                printf("Encolado: %d\n", valor);
                break;
            case 2:
                if (!isEmpty(q)) {
                    valor = dequeue(q);
                    printf("Desencolado: %d\n", valor);
                } else {
                    printf("La cola está vacía\n");
                }
                break;
            case 3:
                if (!isEmpty(q)) {
                    valor = peek(q);
                    printf("Frente actual: %d\n", valor);
                } else {
                    printf("La cola está vacía\n");
                }
                break;
            case 4:
                display(q);
                break;
            case 5:
                printf("Saliendo...\n");
                break;
            default:
                printf("Opción inválida, intenta de nuevo.\n");
        }
    } while (opcion != 5);

    freeQueue(q);
    return 0;
}

// Crea una cola vacía
Queue* createQueue() {
    Queue* q = (Queue*)malloc(sizeof(Queue));
    if (!q) {
        fprintf(stderr, "Error al asignar memoria para la cola\n");
        exit(EXIT_FAILURE);
    }
    q->front = q->rear = NULL;
    return q;
}

// Inserta al fondo de la cola
void enqueue(Queue* q, int value) {
    Node* newNode = (Node*)malloc(sizeof(Node));
    if (!newNode) {
        fprintf(stderr, "Error al asignar memoria\n");
        exit(EXIT_FAILURE);
    }
    newNode->data = value;
    newNode->next = NULL;
    if (q->rear) {
        q->rear->next = newNode;
    } else {
        q->front = newNode;
    }
    q->rear = newNode;
}

// Quita del frente de la cola y devuelve el valor
int dequeue(Queue* q) {
    if (isEmpty(q)) {
        fprintf(stderr, "Error: cola vacía\n");
        exit(EXIT_FAILURE);
    }
    Node* temp = q->front;
    int val = temp->data;
    q->front = temp->next;
    if (!q->front) {
        q->rear = NULL;
    }
    free(temp);
    return val;
}

// Devuelve el valor del frente sin quitarlo
int peek(Queue* q) {
    if (isEmpty(q)) {
        fprintf(stderr, "Error: cola vacía\n");
        exit(EXIT_FAILURE);
    }
    return q->front->data;
}

// Comprueba si la cola está vacía
int isEmpty(Queue* q) {
    return q->front == NULL;
}

// Muestra todos los elementos de la cola de frente a fondo
void display(Queue* q) {
    if (isEmpty(q)) {
        printf("La cola está vacía\n");
        return;
    }
    printf("Cola (frente -> fondo): ");
    for (Node* cur = q->front; cur != NULL; cur = cur->next) {
        printf("%d ", cur->data);
    }
    printf("\n");
}

// Libera toda la memoria de la cola
void freeQueue(Queue* q) {
    while (!isEmpty(q)) {
        dequeue(q);
    }
    free(q);
}
