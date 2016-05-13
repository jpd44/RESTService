// Place your Spring DSL code here
import grails.rest.render.xml.*
import grails.rest.render.json.JsonRenderer
import restservice.AWSMetadata
import restservice.City

beans = {
    cityXmlRenderer(XmlRenderer, City) {
        excludes = ['class','dateCreated']
    }

    cityJsonRenderer(JsonRenderer, City) {
        excludes=['class','dateCreated']
    }

    awsMetadata(restservice.AWSMetadata) {
        bean -> bean.scope='request'
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
