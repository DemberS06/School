// hola soy Dember :D
/*
    Programa Exmen 2do Parcial
    
    Una persona dispone de un departamento que desea alquilar durante el verano. 
    Luego de publicarlo en los clasificados obtiene N ofertas. Cada oferta consta 
    de 3 datos: el monto ofrecido, el día de inicio y el día de finalización. 
    Esta persona desea maximizar sus ganancias. 
    Modelar el problema como un problema de grafos y resolverlo con un algoritmo 
    trabajado en la unidad II.
    
    Solución: Dijkstra
*/
# include<bits/stdc++.h>

using namespace std;

int main(){
    map<int, vector<pair<int, long long>>> graph;
    set<int> hours;
    int n, raiz, best;
    
    cin>>n;
    for(long long i=0, x, y, w; i<n; i++){
        cin>>x>>y>>w;
        graph[x].push_back({y, w});
        
        hours.insert(x);
        hours.insert(y);
    }
    
    multiset<pair<int, long long>> dijkstra;
    map<int, long long> distance;
    set<int> visited;
    
    
    {
        for(auto& x:hours){
            raiz=best=x;
            break;
        }
        int bfr=raiz;
        for(auto& x:hours){
            if(bfr==x)continue;
            graph[bfr].push_back({x, 0ll});
            bfr=x;
        }
    }
    
    
    dijkstra.insert({raiz, 0ll});
    
    while(!dijkstra.empty()){
        auto [nodo, acum] = *dijkstra.begin();
        dijkstra.erase({nodo, acum});
        
        if(visited.count(nodo))continue;
        visited.insert(nodo);
        distance[nodo]=acum;
        
        for(auto& [to, peso]:graph[nodo]){
            if(dijkstra.empty()){
                dijkstra.insert({to, acum+peso});
                continue;
            }
            auto heap=*dijkstra.lower_bound({to, 0ll});
            if(heap.first!=to){
                dijkstra.insert({to, acum+peso});
                continue;
            }
            dijkstra.erase(heap);
            heap.second=max(heap.second, acum+peso);
            dijkstra.insert(heap);
        }
    }
    
    for(auto& [nodo, peso]:distance){
        if(distance[best]<peso)best=nodo;
    }
    
    cout<<best<<' '<<distance[best];
    
    return 0;
}

/* Cases:
2 
2 4 1
10 12 1

7
1 2 1
4 5 1
9 14 1
1 5 1
3 10 1 
7 20 1
10 19 1

*/


