#include "Mathtools.h"
#include <bits/stdc++.h>

using namespace std;

vector<long long> fact(1, 1); 
vector<long long> factr(1, 1); 

long long expbin(long long x, long long y){
    long long ans=1;
    
    while(y){
        if(y&1)ans=ans*x%mod;
        x=x*x%mod;
        y/=2;
    }
    
    return ans;
}

long long factorial(long long n){
    if(n>=fact.size()){
        int oldSize=fact.size(); 
        fact.resize(n+1);
        factr.resize(n+1);

        for(int i=oldSize; i<=n; i++){
            fact[i]=(fact[i-1]*i)%mod; 
            factr[i]=expbin(fact[i], mod-2);
        }
    }

    return fact[n];
}

long long factorialr(long long n){return factr[n];}