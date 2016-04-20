import grails.util.Environment
//import org.apache.tools.ant.types.Environment
import restservice.City
import restservice.Healthcheck

//import org.apache.commons.configuration.PropertiesConfiguration
//import org.apache.commons.configuration.ConfigurationException

import javax.naming.ConfigurationException

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

        getInstanceInfo()
    }
    def destroy = {
        println "Application shutting down..."
    }

    private void getInstanceInfo() throws ConfigurationException {
        // get the availability zone where the application is executing

        //String propertiesFileName="resources/config.properties";
        //PropertiesConfiguration config=new PropertiesConfiguration(propertiesFileName);

        // set the default AZ to localhost
        //config.setProperty("availability_zone", "localhost")
        //config.save();

        URL url;
        InputStream is=null;
        BufferedReader br;
        String az;

        try {
            // the url of the AZ at our local metadata service
            url=new URL("http://169.254.169.254/latest/meta-data/placement/availability-zone");

            // open a connection, but set the timeout to 2 seconds
            URLConnection urlCxn=url.openConnection();
            urlCxn.setConnectTimeout(5000);
            urlCxn.setReadTimeout(5000);
            urlCxn.setAllowUserInteraction(false);
            urlCxn.setDoOutput(true);

            is=urlCxn.getInputStream();
            br= new BufferedReader(new InputStreamReader(is));

            // we're expecting one line of output from the URL
            while((line=br.readLine()) !=null) {
                az=line;

            }
        }
        catch(SocketTimeoutException ste) {
            az="localhost";

        }
        catch (ConnectException ce) {
            az="localhost";
        }
        catch (Exception e) {
            az="localhost";

            try {
                if (is != null) is.close();
            }
            catch (IOException ioe) {
                // noop
            }

        } // catch

        println("Availability zone: " + az.toUpperCase());
        //config.setProperty("availability_zone", az)

    }

    private void seedTestData() {
        def healthcheck=null
        def city = null

        print "Loading healthcheck data... "
        healthcheck=new Healthcheck(databaseHealth: 'good')
        assert healthcheck.save(failOnError: true, flush: true, insert:true)
        healthcheck.errors=null;
        println "done."

        print "Loading cities into database... "
        city = new City(cityName: 'Munich', postalCode: "81927", countryCode: 'DE', testField: 'foo', testField2: 'FOO')
        assert city.save(failOnError:true, flush:true, insert: true)
        city.errors = null

        city = new City(cityName: 'Berlin', postalCode: "10115", countryCode: 'DE', testField: 'bar', testField2: 'BAR')
        assert city.save(failOnError:true, flush:true, insert: true)
        city.errors = null

        city = new City(cityName: 'Dresden', postalCode: "01097", countryCode: 'DE', testField: 'bar', testField2: 'BAR')
        assert city.save(failOnError:true, flush:true, insert: true)
        city.errors = null
        
        assert City.count == 3;
        println "done, loaed $City.count cities into database"
    }
}
