#include "Permutaciones.h"
#include <bits/stdc++.h>

using namespace std;

void backtrackingP(long long& n, long long& k, vector<bool>& vis, string& s, vector<char>& v){
    if(s.size()==k){
        cout<<s<<"\n";
        return;
    }

    for(int i=0; i<n; i++){
        if(vis[i])continue;
        vis[i]=1;
        s+=v[i];
        backtrackingP(n, k, vis, s, v);
        vis[i]=0;
        s.pop_back();
    }
}

void startPermut(){
    long long n, k;
    cout<<"Ingresa la cantidad de objetos en el conjunto: ";
    cin>>n;
    cout<<"Ingresa la cantidad de objetos a escoger: ";
    cin>>k;

    vector<char> v(n);

    cout<<"Ingresa los elementos del conjunto: \n";

    for(int i=0;i<n;i++){
        cin>>v[i];
    }

    cout<<"Hay "<<(factorial(n)*factorialr(k))%mod<<" Permutaciones."<<'\n';

    string s="";
    vector<bool> vis(n, 0);

    cout<<"Permutaciones:\n";
    backtrackingP(n, k, vis, s, v);
}