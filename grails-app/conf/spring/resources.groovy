// Place your Spring DSL code here
import grails.rest.render.xml.*
import grails.rest.render.json.JsonRenderer
import restservice.City

beans = {
    cityXmlRenderer(XmlRenderer, City) {
        excludes = ['class','dateCreated']
    }

    cityJsonRenderer(JsonRenderer, City) {
        excludes=['class','dateCreated']
    }

    /*
    CityXmlCollectionRenderer(CityXmlCollectionRenderer, City) {
        excludes = ['class','dateCreated']
    }

    CityJsonCollectionRenderer(CityJsonCollectionRenderer, City) {
        excludes = ['class','dateCreated']
    }
    */

}
