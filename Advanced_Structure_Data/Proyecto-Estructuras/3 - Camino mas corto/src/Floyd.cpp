#include "DP.h"
#include <bits/stdc++.h>

using namespace std;

const long long inf = 1e18;

void Floyd(Graph& g, vector<vector<long long>>& mat){
    for(int k=0; k<g.n; k++){
        for(int i=0; i<g.n; i++){
            for(int j=0; j<g.n; j++){
                mat[i][j]=min(mat[i][j], mat[i][k]+mat[k][j]);
            }   
        }
    }
}

void startFloyd(Graph &g){
    vector<vector<long long>> mat(g.n, vector<long long>(g.n, inf));
    
    for(int x=0; x<g.n; x++){
        for(auto& e:g.adj[x]){
            mat[x][e.to]=min(mat[x][e.to], e.weight);
        }
    }

    for(int i=0; i<g.n; i++)mat[i][i]=0;

    Floyd(g, mat);

    for(int i=0; i<g.n; i++){
        for(int j=0; j<g.n; j++){
            if(mat[i][j]==inf)cout<<"No hay camino de "<<i<<" a "<<j<<"\n";
                else cout<<"El camino mas corto desde "<<i<<" a "<<j<<" tiene peso "<<mat[i][j]<<"\n";
        }
    }
    
    return;
}

