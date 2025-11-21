#include <iostream>
using namespace std;

// Nodo doblemente enlazado
struct Node {
    int data;
    Node* prev;
    Node* next;
    Node(int val) : data(val), prev(nullptr), next(nullptr) {}
};

// Clase Cola (Queue)
class Queue {
private:
    Node* front;
    Node* rear;

public:
    Queue() : front(nullptr), rear(nullptr) {}

    ~Queue() {
        Node* curr = front;
        while (curr) {
            Node* next = curr->next;
            delete curr;
            curr = next;
        }
    }

    bool isEmpty() const {
        return front == nullptr;
    }

    void enqueue(int val) {
        Node* node = new Node(val);
        if (isEmpty()) {
            front = rear = node;
        } else {
            rear->next = node;
            node->prev = rear;
            rear = node;
        }
        cout << "Encolado: " << val << "\n";
    }

    int dequeue() {
        if (isEmpty()) {
            cout << "La cola está vacía\n";
            return -1;
        }
        int val = front->data;
        Node* temp = front;
        front = front->next;
        if (front) front->prev = nullptr;
        else rear = nullptr;
        delete temp;
        cout << "Desencolado: " << val << "\n";
        return val;
    }

    int peek() const {
        if (isEmpty()) {
            cout << "La cola está vacía\n";
            return -1;
        }
        return front->data;
    }

    void display() const {
        if (isEmpty()) {
            cout << "La cola está vacía\n";
            return;
        }
        cout << "Cola (frente -> final): ";
        for (Node* curr = front; curr; curr = curr->next)
            cout << curr->data << " ";
        cout << "\n";
    }
};

int main() {
    Queue cola;
    int opcion, valor;

    do {
        cout << "\n=== Menú Cola de Enteros (C++) ===\n"
             << "1. Encolar (enqueue)\n"
             << "2. Desencolar (dequeue)\n"
             << "3. Ver frente (peek)\n"
             << "4. Mostrar cola\n"
             << "5. Salir\n"
             << "Elige una opción: ";
        cin >> opcion;

        switch (opcion) {
            case 1:
                cout << "Valor a encolar: ";
                cin >> valor;
                cola.enqueue(valor);
                break;
            case 2:
                cola.dequeue();
                break;
            case 3:
                valor = cola.peek();
                if (valor != -1)
                    cout << "Frente actual: " << valor << "\n";
                break;
            case 4:
                cola.display();
                break;
            case 5:
                cout << "Saliendo...\n";
                break;
            default:
                cout << "Opción inválida. Intenta de nuevo.\n";
        }
    } while (opcion != 5);

    return 0;
}
