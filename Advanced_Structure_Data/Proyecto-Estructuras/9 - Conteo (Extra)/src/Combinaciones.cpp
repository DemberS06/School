#include "Combinaciones.h"
#include <bits/stdc++.h>

using namespace std;

void backtrackingC(long long& n, long long& k, int i, string& s, vector<char>& v){
        if(s.size()==k){
            cout<<s<<"\n";
            return;
        }
        
        for(; i<n; i++){
            s+=v[i];
            backtrackingC(n, k, i+1, s, v);
            s.pop_back();
        }
}

void startCombin(){
    long long n, k;
    cout<<"Ingresa la cantidad de objetos en el conjunto: ";
    cin>>n;
    cout<<"Ingresa la cantidad de objetos a escoger: ";
    cin>>k;

    vector<char> v(n);

    cout<<"Ingresa los elementos del conjunto: ";

    for(int i=0;i<n;i++){
        cin>>v[i];
    }

    long long ans = factorial(n);
    ans = ans * factorialr(k) % mod;
    ans = ans * factorialr(n-k) % mod;

    cout<<"Hay "<<ans<<" Combinaciones.\n";

    string s="";
    vector<bool> vis(n, 0);

    cout<<"Combinaciones:\n";
    backtrackingC(n, k, 0, s, v);
}