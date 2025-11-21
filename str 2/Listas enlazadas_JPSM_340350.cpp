#include <iostream>
using namespace std;

// Nodo de la lista enlazada simple
struct Node {
    int data;
    Node* next;
    Node(int val) : data(val), next(nullptr) {}
};

// Clase para manejar la lista enlazada
class LinkedList {
private:
    Node* head;

public:
    LinkedList() : head(nullptr) {}

    ~LinkedList() {
        // Liberar toda la memoria al destruir la lista
        Node* current = head;
        while (current) {
            Node* toDelete = current;
            current = current->next;
            delete toDelete;
        }
    }

    // Insertar al final
    void insert(int val) {
        Node* newNode = new Node(val);
        if (!head) {
            head = newNode;
        } else {
            Node* cur = head;
            while (cur->next)
                cur = cur->next;
            cur->next = newNode;
        }
        cout << "Insertado: " << val << "\n";
    }

    // Eliminar la primera ocurrencia de val
    void remove(int val) {
        if (!head) {
            cout << "La lista está vacía.\n";
            return;
        }
        // Si el nodo a eliminar es el primero
        if (head->data == val) {
            Node* toDelete = head;
            head = head->next;
            delete toDelete;
            cout << "Eliminado: " << val << "\n";
            return;
        }
        // Buscar nodo anterior al que queremos eliminar
        Node* cur = head;
        while (cur->next && cur->next->data != val)
            cur = cur->next;
        if (cur->next) {
            Node* toDelete = cur->next;
            cur->next = toDelete->next;
            delete toDelete;
            cout << "Eliminado: " << val << "\n";
        } else {
            cout << "Valor no encontrado: " << val << "\n";
        }
    }

    // Buscar si existe val en la lista
    bool search(int val) {
        Node* cur = head;
        while (cur) {
            if (cur->data == val)
                return true;
            cur = cur->next;
        }
        return false;
    }

    // Mostrar todos los elementos
    void display() {
        if (!head) {
            cout << "La lista está vacía.\n";
            return;
        }
        cout << "Lista: ";
        Node* cur = head;
        while (cur) {
            cout << cur->data;
            if (cur->next) cout << " -> ";
            cur = cur->next;
        }
        cout << "\n";
    }
};

int main() {
    LinkedList lista;
    int opcion, valor;

    do {
        cout << "\n=== Menú Lista Enlazada Simple ===\n"
             << "1. Insertar elemento\n"
             << "2. Eliminar elemento\n"
             << "3. Buscar elemento\n"
             << "4. Mostrar lista\n"
             << "5. Salir\n"
             << "Elige una opción: ";
        cin >> opcion;

        switch (opcion) {
            case 1:
                cout << "Ingresa valor a insertar: ";
                cin >> valor;
                lista.insert(valor);
                lista.display();
                break;
            case 2:
                cout << "Ingresa valor a eliminar: ";
                cin >> valor;
                lista.remove(valor);
                lista.display();
                break;
            case 3:
                cout << "Ingresa valor a buscar: ";
                cin >> valor;
                if (lista.search(valor))
                    cout << "Elemento " << valor << " encontrado.\n";
                else
                    cout << "Elemento " << valor << " NO encontrado.\n";
                break;
            case 4:
                lista.display();
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
