<%@ page import="restservice.Healthcheck" %>



<div class="fieldcontain ${hasErrors(bean: healthcheckInstance, field: 'databaseHealth', 'error')} required">
	<label for="databaseHealth">
		<g:message code="healthcheck.databaseHealth.label" default="Database Health" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="databaseHealth" required="" value="${healthcheckInstance?.databaseHealth}"/>

</div>

