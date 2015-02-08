package br.eti.augusto.transactional;

public class BeanBase {
  public String getId() {
    return "ObjectId for this bean is " + this + "";
  }
  public String getId(String s) {
    return "ObjectId for this bean is " + this + " and "+ s;
  }
}


