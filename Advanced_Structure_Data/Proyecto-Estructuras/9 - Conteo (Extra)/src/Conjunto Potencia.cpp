#include "Conjunto potencia.h"
#include <bits/stdc++.h>

using namespace std;

void conjValores(){
    long long n;
    cout<<"Ingresa el tamano del conjunto: ";
    cin>>n;

    vector<char> v(n);
    cout<<"Ingrese los valores del conjunto: ";
    for(int i=0;i<n;i++)cin>>v[i];
    
    for(int i=0; i<(1<<n); i++){
        for(int h=0; h<n; h++){
            if((i>>h)&1)cout<<v[h];
        }
        cout<<'\n';
    }   
}

void conjBinario(){
    long long n;
    cout<<"Ingresa el tamano del conjunto: ";
    cin>>n;
    
    for(int i=0; i<(1<<n); i++){
        for(int h=0; h<n; h++){
            cout<<((i>>h)&1)<<' ';
        }
        cout<<'\n';
    }   
}

void cardinalidad(){
    long long n;
    cout<<"Ingresa el tamano del conjunto: ";
    cin>>n;

    cout<<(expbin(2, n))<<'\n';
}

void startConjPot(){
    vector<string> menu = {
        "Regresar", 
        "Conjunto potencia en binario",
        "Conjunto potencia con valores",
        "La cardinalidad del conjunto potencia"
    };

    while(true){
        for(int i=0; i<menu.size(); i++){
            cout<<'['<<i<<']'<<menu[i]<<'\n';
        }

        int x;
        cin>>x;

        switch(x){
            case 0: return;
            case 1: conjBinario(); break;
            case 2: conjValores(); break;
            case 3: cardinalidad(); break;
            default: cout<<"Opcion no valida\n"; break;
        }
    }
}