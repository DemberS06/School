package models;

import java.util.ArrayList;

public class Carrito {
    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<Integer> frq = new ArrayList<>();

    public void add(int id){
        for(int i=0; i<ids.size(); i++){
            if( ids.get(i) == id ){
                frq.set(i, frq.get(i)+1);
                return;
            }
        }
        ids.add(id);
        frq.add(1);
    }

    public void remove(int id){
        for(int i=0; i<ids.size(); i++){
            if( ids.get(i) == id ){
                frq.remove(i);
                ids.remove(i);
                return;
            }
        }
    }

    public ArrayList<Integer> getIds(){
        return ids;
    }
    public ArrayList<Integer> getFrq(){
        return frq;
    }

    public int count(int id){
        for(int i=0; i<ids.size(); i++){
            if( ids.get(i) == id )return frq.get(i);
        }
        return 0;
    }

    public void clear(){
        ids.clear();
        frq.clear();
    }
}