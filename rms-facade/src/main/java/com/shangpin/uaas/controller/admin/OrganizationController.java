package com.shangpin.uaas.controller.admin;
/*package com.shangpin.rms.controller;

import com.shangpin.uaas.api.admin.UserCriteriaDTO
import com.shangpin.uaas.api.admin.UserDTO
import org.nofdev.servicefacade.PagedList
import org.nofdev.servicefacade.Paginator

class OrganizationController {
    def exportService
    def organizationAdminFacadeService

    def index() {}

    def export() {
        def format = "excel"
        def result = organizationAdminFacadeService.getAllOrganizations()
        response.contentType = grailsApplication.config.grails.mime.types[format]
        response.setHeader("Content-disposition", "attachment; filename=organization.xls")
        exportService.export(format, response.outputStream,result, [:], [:])
    }
}
*/