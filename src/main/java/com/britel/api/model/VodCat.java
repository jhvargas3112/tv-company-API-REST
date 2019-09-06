package com.britel.api.model;

import java.util.ArrayList;

public class VodCat {
  Category padre;  //categoria padre
  ArrayList<Cat> hijos;
  public VodCat(Category father,ArrayList<Cat> son){
    son = hijos;
    father = padre ;
  }
  public Category getPadre() {
    return padre;
  }
  public void setPadre(Category padre) {
    this.padre = padre;
  }
  public ArrayList<Cat> getHijos() {
    return hijos;
  }
  public void setHijos(ArrayList<Cat> hijos) {
    this.hijos = hijos;
  }

}