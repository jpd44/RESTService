package restservice

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames=true,includeFields=true,excludes='d')
@EqualsAndHashCode

class City {
    Long id
    Long version

    Date dateCreated
    Date lastUpdated

    String cityName
    String postalCode
    String countryCode
    String testField
    String testField2

    static constraints = {
        postalCode blank:false, nullable:false
        cityName blank:false, nullable:false
        countryCode minSize:2, maxSize:3, blank:false, nullable:false
        testField nullable:true, blank:true
        testField2 nullable:true, blank:true

    }
}
