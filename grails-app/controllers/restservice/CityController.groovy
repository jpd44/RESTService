package restservice

import grails.rest.RestfulController

class CityController extends RestfulController {

    static responseFormats = ['json', 'xml']

    CityController() {
        super(City)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        respond City.list(params), model:[cityCount: City.count()]
    }
}

