package restservice

import grails.rest.RestfulController

class CityController extends RestfulController {

    static responseFormats = ['json', 'xml']

    CityController() {
        super(City)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 100000, 100000)
        AWSMetadata regionInfo=AWSMetadata.getInstance();

        respond City.list(params), model:[cityCount: City.count()]

        // can't do this!
        // RegionInfo regionInfo=RegionInfo.getInstance();
        // respond regionInfo.getRegion();
    }
}

