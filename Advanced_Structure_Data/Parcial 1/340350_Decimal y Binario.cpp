// hola soy Dember :D
/*
 *Juan Pablo Salinas Muñoz - 340350
 *15-08-2025
 *Combinaciones y Permutaciones
 Cumple con:
 Convertir numero de decimal a binario
 Convertir numero de  binario a decimal
*/

#include <bits/stdc++.h>

using namespace std;

const long long mod=1e9+7;

int main(){
    //cin.tie(0); cout.tie(0); ios_base::sync_with_stdio(0);
    
    long long n=0;
    string s="";
    int t;
    
    cout<<"Ingrese 0 si desea transformad de Binario a decimal o cualquier otro entero de lo contrario: ";
    cin>>t;
    
    if(t){
        cout<<"Ingrese el número en Decimal:\n";
        cin>>n;
        
        if(n==0)s="0";
        
        while(n){
            if(n&1)s+='1';
            else s+='0';
            n/=2;
        }
        reverse(s.begin(), s.end());
        cout<<s;
        return 0;
    }
    cout<<"Ingrese el número en Binario:\n";
    cin>>s;
    
    for(long long x=1; !s.empty(); x=x*2%mod){
        if(s.back()=='1')n=(n+x)%mod;
        s.pop_back();
    }
    
    cout<<n;
    
    
    return 0;
}