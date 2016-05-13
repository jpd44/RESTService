package restservice;

import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;
import groovy.lang.*;
import groovy.util.*;
import static org.springframework.http.HttpStatus.*;

@grails.transaction.Transactional(readOnly=true) public class CityController
  extends grails.rest.RestfulController {
;
public CityController
() {
super ((java.lang.Class)null);
}
public static  java.lang.Object getAllowedMethods() { return null;}
public static  void setAllowedMethods(java.lang.Object value) { }
public  java.lang.Object index(java.lang.Integer max) { return null;}
public  java.lang.Object show(restservice.City cityInstance) { return null;}
public  java.lang.Object create() { return null;}
@grails.transaction.Transactional() public  java.lang.Object save(restservice.City cityInstance) { return null;}
public  java.lang.Object edit(restservice.City cityInstance) { return null;}
@grails.transaction.Transactional() public  java.lang.Object update(restservice.City cityInstance) { return null;}
@grails.transaction.Transactional() public  java.lang.Object delete(restservice.City cityInstance) { return null;}
protected  void notFound() { }
}
