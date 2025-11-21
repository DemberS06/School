#include <iostream>
using namespace std;

// Nodo de lista doblemente enlazada
struct Node {
    int data;
    Node* prev;
    Node* next;
    Node(int val) : data(val), prev(nullptr), next(nullptr) {}
};

// Clase Stack usando lista doblemente enlazada
class Stack {
private:
    Node* top;  // apunta al tope de la pila

public:
    Stack() : top(nullptr) {}
    ~Stack() {
        // Liberar todos los nodos
        Node* cur = top;
        while (cur) {
            Node* nxt = cur->next;
            delete cur;
            cur = nxt;
        }
    }

    bool isEmpty() const {
        return top == nullptr;
    }

    void push(int val) {
        Node* node = new Node(val);
        node->next = top;
        if (top) top->prev = node;
        top = node;
        cout << "Apilado: " << val << "\n";
    }

    int pop() {
        if (isEmpty()) {
            cout << "La pila está vacía\n";
            return -1;  // centinela
        }
        Node* node = top;
        int val = node->data;
        top = node->next;
        if (top) top->prev = nullptr;
        delete node;
        cout << "Desapilado: " << val << "\n";
        return val;
    }

    int peek() const {
        if (isEmpty()) {
            cout << "La pila está vacía\n";
            return -1;
        }
        return top->data;
    }

    void display() const {
        if (isEmpty()) {
            cout << "La pila está vacía\n";
            return;
        }
        cout << "Pila (tope -> fondo): ";
        for (Node* cur = top; cur; cur = cur->next)
            cout << cur->data << " ";
        cout << "\n";
    }
};

int main() {
    Stack pila;
    int opcion, valor;

    do {
        cout << "\n=== Menú Pila de Enteros (C++) ===\n"
             << "1. Apilar (push)\n"
             << "2. Desapilar (pop)\n"
             << "3. Ver tope (peek)\n"
             << "4. Mostrar pila\n"
             << "5. Salir\n"
             << "Elige una opción: ";
        cin >> opcion;

        switch (opcion) {
            case 1:
                cout << "Valor a apilar: ";
                cin >> valor;
                pila.push(valor);
                break;
            case 2:
                pila.pop();
                break;
            case 3:
                valor = pila.peek();
                if (valor != -1)
                    cout << "Tope actual: " << valor << "\n";
                break;
            case 4:
                pila.display();
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
