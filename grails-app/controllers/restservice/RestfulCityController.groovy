package restservice

import grails.rest.RestfulController

class RestfulCityController extends RestfulController {
    //static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", list: "GET"]
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET"]
    static responseFormats=['json','xml']

    RestfulCityController() {
        super(City)

    }

} // class
