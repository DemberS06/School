#include "Bitmask.h"
#include <bits/stdc++.h>

using namespace std;

bool compare(pair<int, int>& x, pair<int, int>& y){
    if(x.first==y.first)return false;
    if(x.first==y.second)return false;
    if(x.second==y.first)return false;
    if(x.second==y.second)return false;
    return true;
}

void backtracking(vector<pair<int, int>>& arist, vector<pair<int, int>>& best, vector<pair<int, int>>& curr, int i){
    if(curr.size()>best.size())best=curr;

    for(; i<arist.size(); i++){
        bool ok=1;
        for(auto& u:curr)ok&=compare(u, arist[i]);
        if(!ok)continue;
        curr.push_back(arist[i]);
        backtracking(arist, best, curr, i+1);
        curr.pop_back();
    }

    return;
}

int maxMatching(vector<pair<int, int>>& arist, vector<pair<int, int>>& best){
    vector<pair<int, int>> curr;

    backtracking(arist, best, curr, 0);
    
    return best.size();
}

void startBacktracking(Graph &g){
    if(g.m>40){
        cout<<"Valora tu computadora.\n";
        return;
    }
    vector<pair<int, int>> arist, best, curr;

    for(int i=0; i<g.n; i++){
        for(auto& u:g.adj[i])if(i<u)arist.push_back({i, u});
    }

    cout<<"El Grafo tiene "<<maxMatching(arist, best)<<" aristas en su emparejamiento maximo.\n";
    cout<<"Para el algoritmo de Backtracking se determino este como el pareo:\n";
    
    for(auto& [x, y]:best)cout<<x<<"<->"<<y<<"\n";
    
    return;
}

