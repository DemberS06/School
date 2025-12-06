#include <bits/stdc++.h>
#include "Coloracion.h"

using namespace std;

int bfsColor(Graph& g, int x, vector<int>& color){    
    queue<int> bfs;
    color[x] = 0;
    bfs.push(x);

    while (!bfs.empty()) {
        int u = bfs.front();
        bfs.pop();

        for (int v : g.adj[u]) {
            if (color[v]==-1) {
                color[v]=color[u]^1; 
                bfs.push(v);
            } else if (color[v] == color[u]) {
                return 0;
            }
        }
    }

    return 1;
}

int esBipartito(Graph& g){
    int n = g.n;
    vector<int> color(n, -1);

    for (int i=0; i<n; i++) {
        if (color[i] == -1) {
            if (!bfsColor(g, i, color)) {
                return 0;
            }
        }
    }
    return 1;
}

void startColoracion(Graph& g){
    int n = g.n;
    vector<int> color(n, -1);

    for (int i = 0; i<n; i++) {
        if (color[i] == -1) {
            if (!bfsColor(g, i, color)) {
                cout<<"\nEl grafo no es bipartito\n";
                return;
            }
        }
    }

    cout << "\nEl grafo es bipartito.\n";

    vector<int> grupoA, grupoB;
    for (int v = 0; v < n; v++) {
        if (color[v] == 0) grupoA.push_back(v);
        else if (color[v] == 1) grupoB.push_back(v);
    }

    cout << "El grupo A se compone de: \n";
    for (auto& v : grupoA)cout<<v<<' ';
    cout<<"\n";

    cout<<"El grupo B se compone de: \n";
    for (auto& v : grupoB)cout<<v<<' ';
    cout << "\n";
}
