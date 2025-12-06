#include "DP.h"
#include <bits/stdc++.h>

using namespace std;

const long long inf = 1e18;

void DP(Graph& g, vector<vector<vector<long long>>>& dp){
    for(int k=2; k<g.n; k++){
        for(int i=0; i<g.n; i++){
            for(int j=0; j<g.n; j++){
                for(int h=0; h<g.n; h++)dp[k][i][j]=min(dp[k][i][j], dp[1][i][h]+dp[k-1][h][j]);
            }   
        }
    }
}

void startDP(Graph &g){
    
    vector<vector<vector<long long>>> dp(g.n, vector<vector<long long>>(g.n, vector<long long>(g.n, inf)));
    
    for(int x=0; x<g.n; x++){
        for(auto& e:g.adj[x]){
            dp[1][x][e.to]=min(dp[1][x][e.to], e.weight);
        }
    }

    for(int i=0; i<g.n; i++)dp[0][i][i]=0;

    DP(g, dp);

    for(int i=0; i<g.n; i++){
        for(int j=0; j<g.n; j++){
            for(int k=0; k<g.n; k++){
                if(dp[k][i][j]==inf)cout<<"No hay camino de "<<i<<" a "<<j<<" con "<<k<<" aristas\n";
                else cout<<"El camino mas corto desde "<<i<<" a "<<j<<" pasando por "<<k<<" aristas tiene peso "<<dp[k][i][j]<<"\n";
            }
        }
    }
    
    return;
}

