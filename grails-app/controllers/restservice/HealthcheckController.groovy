package restservice



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HealthcheckController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Healthcheck.list(params), model:[healthcheckInstanceCount: Healthcheck.count()]
    }

    def show(Healthcheck healthcheckInstance) {
        respond healthcheckInstance
    }

    def create() {
        respond new Healthcheck(params)
    }

    @Transactional
    def save(Healthcheck healthcheckInstance) {
        if (healthcheckInstance == null) {
            notFound()
            return
        }

        if (healthcheckInstance.hasErrors()) {
            respond healthcheckInstance.errors, view:'create'
            return
        }

        healthcheckInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'healthcheck.label', default: 'Healthcheck'), healthcheckInstance.id])
                redirect healthcheckInstance
            }
            '*' { respond healthcheckInstance, [status: CREATED] }
        }
    }

    def edit(Healthcheck healthcheckInstance) {
        respond healthcheckInstance
    }

    @Transactional
    def update(Healthcheck healthcheckInstance) {
        if (healthcheckInstance == null) {
            notFound()
            return
        }

        if (healthcheckInstance.hasErrors()) {
            respond healthcheckInstance.errors, view:'edit'
            return
        }

        healthcheckInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Healthcheck.label', default: 'Healthcheck'), healthcheckInstance.id])
                redirect healthcheckInstance
            }
            '*'{ respond healthcheckInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Healthcheck healthcheckInstance) {

        if (healthcheckInstance == null) {
            notFound()
            return
        }

        healthcheckInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Healthcheck.label', default: 'Healthcheck'), healthcheckInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'healthcheck.label', default: 'Healthcheck'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
