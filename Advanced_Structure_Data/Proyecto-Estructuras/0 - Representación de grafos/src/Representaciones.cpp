#include "Representaciones.h"
#include <bits/stdc++.h>

using namespace std;

void imprimirlistadeadj(Graph& g){
    cout<<"Lista de adyacencia\n";
    for(int u=0; u<g.n; u++){
        cout<<u<<" -> ";
        for(auto& e:g.adj[u]){
            cout<<"("<<e.to<<", w="<<e.weight<<") ";
        }cout<<"\n";
    }
}

void imprimirmatrizdeadj(Graph& g){
    vector<vector<long long>> mat(g.n, vector<long long>(g.n, 0));

    for(int u=0; u<g.n; u++){
        for(auto& e:g.adj[u]){
            mat[u][e.to]=e.weight;
        }
    }

    cout<<"Matriz de adyacencia\n";
    cout<<"    ";
    for(int j=0; j<g.n; j++)cout<<j<< " "; cout<<"\n";

    for(int i=0; i<g.n; i++){
        cout<<i<<" : ";
        for(int j=0; j<g.n; j++){
            cout<<mat[i][j]<<" ";
        }cout<<"\n";
    }
}

void imprimirmatrizdeincidencia(Graph& g) {
    vector<pair<int,int>> edges;
    edges.reserve(g.m);

    for(int u=0; u<g.n; u++){
        for(auto& e:g.adj[u]){
            edges.emplace_back(u, e.to);
        }
    }

    int m=edges.size();
    vector<vector<int>> mat(g.n, vector<int>(m, 0));

    for(int j=0; j<m; j++){
        int u=edges[j].first;
        int v=edges[j].second;
        mat[u][j]--;
        mat[v][j]++;
    }

    cout<<"Matriz de incidencia\n";
    if(m==0){
        cout<<"(No hay aristas)\n";
        return;
    }

    cout<<"      ";
    for(int j=0; j<m; j++)cout<<"e"<<j<<" "; cout<<"\n";

    for(int i=0; i<g.n; i++){
        cout<<"v"<<i<<" : ";
        for(int j=0; j<m; j++){
            cout<<mat[i][j]<<" ";
        }cout<<"\n";
    }
}
