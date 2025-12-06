#include "Greedy.h"
#include <bits/stdc++.h>

using namespace std;

void startGreedy(Graph &g) {
    int n = g.n;
    vector<int> used(n, 0);
    vector<pair<int,int>> matching;

    for(int u=0; u<n; u++) {
        if(used[u])continue;
        for(auto &v:g.adj[u]) {
            if(!used[v]){
                used[u]=used[v]=1;
                matching.push_back({u, v});
                break;
            }
        }
    }

    cout<<"Tamano del emparejamiento: "<<matching.size()<<"\n";
    cout<<"Emparejamiento Greedy encontrado:\n";
    for(auto &p:matching){
        cout<<p.first<<"<->"<<p.second<<"\n";
    }
}

