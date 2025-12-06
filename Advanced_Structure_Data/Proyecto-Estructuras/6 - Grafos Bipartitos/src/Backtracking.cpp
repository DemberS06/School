#include "Backtracking.h"
#include <bits/stdc++.h>

using namespace std;

bool Backtracking(Graph& g, set<int>& A, set<int>& B, int i=0){
    if(i==g.n)return true;

    for(int ok; i<g.n; i++){
        ok=0;
        for(auto& u:g.adj[i])ok|=A.count(u);
        if(!ok){
            A.insert(i);
            if(Backtracking(g, A, B, i+1))return true;
            A.erase(i);
        }
        ok=0;
        for(auto& u:g.adj[i])ok|=B.count(u);
        if(!ok){
            B.insert(i);
            if(Backtracking(g, A, B, i+1))return true;
            B.erase(i);
        }
    }

    return false;
}

void startBacktracking(Graph& g){
    vector<int> color(g.n, -1);

    if(g.n>40){
        cout<<"Valora tu computadora.\n";
        return;
    }

    bool ok=0;
    set<int> GroupA, GroupB;

    ok = Backtracking(g, GroupA, GroupB, 0);

    if(!ok){
        cout<<"El grafo no es bipartito.\n";
        return;
    }

    cout<<"El grupo A se compone de:\n{";
    for(auto& u:GroupA)cout<<u<<((u==*GroupA.rbegin()?"}.\n":", "));
    
    cout<<"El grupo B se compone de:\n{";
    for(auto& u:GroupB)cout<<u<<((u==*GroupB.rbegin()?"}.\n":", "));

    return;
}
