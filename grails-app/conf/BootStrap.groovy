import grails.util.Environment
//import org.apache.tools.ant.types.Environment
import restservice.City
import restservice.Healthcheck
import restservice.AWSMetadata

class BootStrap {
    def grailsApplication

    def init = { servletContext ->
        def result="############ running in UNCLEAR mode."
        println "Application starting..."

        switch(Environment.current) {
            case Environment.DEVELOPMENT:
                result='now running in DEV mode.'
                seedTestData()
                break;
            case Environment.TEST:
                result='now running in TEST mode.'
                seedTestData()
                break;
            case Environment.PRODUCTION:
                result='now running in PROD mode.'
                seedTestData()
                break;

        }

        println "current environment: $Environment.current"
        println "$result"

    } // init
    def destroy = {
        println "Application shutting down..."

    }


    private void seedTestData() {
        def healthcheck=null
        def city = null
        def ami_id=null

        def now=new Date();
        def dateString=now.getDateTimeString();
        def healthcheckString

        AWSMetadata awsMetaData=AWSMetadata.getInstance();
        print "AZ: " + awsMetaData.getPlacement_availability_zone();

        print "Loading healthcheck data... "

        healthcheckString=dateString + ": " + awsMetaData.getLocal_hostname() + ", in AZ " + awsMetaData.getPlacement_availability_zone() + " (res. id: " + awsMetaData.getReservation_id() + ")";

        healthcheck=new Healthcheck(databaseHealth: healthcheckString);
        assert healthcheck.save(failOnError: true, flush: true, insert:true)
        healthcheck.errors=null;
        print "done."

        print "Loading test city entries into database... "
        city = new City(cityName: 'Munich', postalCode: "81927", countryCode: 'DE', testField: 'foo', testField2: 'FOO')
        assert city.save(failOnError:true, flush:true, insert: true)
        city.errors = null

        city = new City(cityName: 'Berlin', postalCode: "10115", countryCode: 'DE', testField: 'bar', testField2: 'BAR')
        assert city.save(failOnError:true, flush:true, insert: true)
        city.errors = null

        //assert City.count == 2;
        //println "done, loaed $City.count cities into database"
    }
}
