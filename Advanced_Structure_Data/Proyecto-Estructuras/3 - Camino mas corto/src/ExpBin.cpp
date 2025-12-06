#include "DP.h"
#include <bits/stdc++.h>

using namespace std;

const long long inf = 1e18;

void mult(vector<vector<long long>>& res, vector<vector<long long>>& mat, int &n){
    vector<vector<long long>> x(n, vector<long long> (n, inf));
    for(int i=0; i<n; i++){
        for(int j=0; j<n; j++){
            for(int k=0; k<n; k++){
                x[i][k]=min(x[i][k], res[i][j]+mat[j][k]);
            }
        }
    }
    res=x;
}

void expbin(vector<vector<long long>>& mat, int& n, int k){
    vector<vector<long long>> res(n, vector<long long>(n, inf));
    for(int i=0; i<n; i++)res[i][i]=0;

    while(k){
        if(k&1)mult(res, mat, n);
        mult(mat, mat, n);
        k/=2;
    }
    
    mat=res;
}

void startExpBin(Graph &g){
    vector<vector<long long>> mat(g.n, vector<long long>(g.n, inf));
    
    for(int x=0; x<g.n; x++){
        for(auto& e:g.adj[x]){
            mat[x][e.to]=min(mat[x][e.to], e.weight);
        }
    }

    int n=g.n, k;
    cout<<"Ingresa el tamano del camino: ";
    cin>>k;

    expbin(mat, n, k);

    for(int i=0; i<g.n; i++){
        for(int j=0; j<g.n; j++){
            if(mat[i][j]==inf)cout<<"No hay camino de "<<i<<" a "<<j<<" con "<<k<<" aristas\n";
            else cout<<"El camino mas corto desde "<<i<<" a "<<j<<" pasando por "<<k<<" aristas tiene peso "<<mat[i][j]<<"\n";
        }
    }
    
    return;
}

