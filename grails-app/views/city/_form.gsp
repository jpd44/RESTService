<%@ page import="restservice.City" %>



<div class="fieldcontain ${hasErrors(bean: cityInstance, field: 'postalCode', 'error')} required">
	<label for="postalCode">
		<g:message code="city.postalCode.label" default="Postal Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="postalCode" required="" value="${cityInstance?.postalCode}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cityInstance, field: 'cityName', 'error')} required">
	<label for="cityName">
		<g:message code="city.cityName.label" default="City Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cityName" required="" value="${cityInstance?.cityName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cityInstance, field: 'countryCode', 'error')} required">
	<label for="countryCode">
		<g:message code="city.countryCode.label" default="Country Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="countryCode" maxlength="3" required="" value="${cityInstance?.countryCode}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cityInstance, field: 'testField', 'error')} ">
	<label for="testField">
		<g:message code="city.testField.label" default="Test Field" />
		
	</label>
	<g:textField name="testField" value="${cityInstance?.testField}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cityInstance, field: 'testField2', 'error')} ">
	<label for="testField2">
		<g:message code="city.testField2.label" default="Test Field2" />
		
	</label>
	<g:textField name="testField2" value="${cityInstance?.testField2}"/>

</div>

