package com.britel.api.model;

import java.util.ArrayList;

public class Cat {
  Category padre;  //categoria padre
  //Set<Video> videos;
  ArrayList<Category> hijos;

  public Cat(Category father){
    father = padre ;
  }

  public ArrayList<Category> getHijos() {
    return hijos;
  }

  public void setHijos(ArrayList<Category> hijos) {
    this.hijos = hijos;
  }

  public Cat(Category father,ArrayList<Category> son){
    //padre
    father = padre ;
    //hijos
    son = hijos;
  }

  public Category getPadre() {
    return padre;
  }

  public void setPadre(Category padre) {
    this.padre = padre;
  }

}