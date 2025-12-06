#include "Bitmask.h"
#include <bits/stdc++.h>

using namespace std;

int maxMatching(vector<pair<int, int>>& arist, long long& mask){
    int n=arist.size();

    for(long long i=0; i<(1ll<<n); i++){
        bool ok=1;

        map<int, int> mp;

        for(int h=0; h<n; h++){
            if((i>>h)&1)mp[arist[h].first]++, mp[arist[h].second]++;
        }

        for(auto& [_, x]:mp)ok&=(x==1);

        if(ok && __popcount(mask)<__popcount(i))mask=i;
    }

    return __popcount(mask);
}

void startBitmask(Graph &g){
    if(g.m>40){
        cout<<"Valora tu computadora.\n";
        return;
    }
    vector<pair<int, int>> arist;
    long long mask=0;

    for(int i=0; i<g.n; i++){
        for(auto& u:g.adj[i])if(i<u)arist.push_back({i, u});
    }

    cout<<"El Grafo tiene "<<maxMatching(arist, mask)<<" aristas en su emparejamiento maximo.\n";
    cout<<"Para el algoritmo de Bitmask se determino este como el pareo:\n";
    
    for(int h=0; h<arist.size(); h++){
        if((mask>>h)&1)cout<<arist[h].first<<"<->"<<arist[h].second<<"\n"; 
    }
    
    return;
}

