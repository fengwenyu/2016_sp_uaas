package com.shangpin.uaas.controller.admin;
/*package uaas

import com.shangpin.uaas.admin.GroupAdminFacadeService
import com.shangpin.uaas.admin.UserAdminFacadeService
import com.shangpin.uaas.api.GroupUserDTO
import com.shangpin.uaas.api.admin.GroupDTO
import com.shangpin.uaas.api.admin.UserDTO
import com.shangpin.uaas.util.CommonExcelImport
import org.apache.commons.lang.StringUtils
import org.nofdev.servicefacade.Paginator
import org.springframework.web.multipart.MultipartHttpServletRequest

class GroupController {
    def exportService
    def userAdminFacadeService
    def groupAdminFacadeService

    def GROUP_USER_CLOUMN_PROPERTIES = [
            "groupName" ,
            "userName"  ,
            "groupDes"  ,
            "isEnabled"
    ]


    def export() {
        def format = "excel"
        Paginator paginator = new Paginator()
        paginator.page = 1
        paginator.pageSize = 1000
        def result = groupAdminFacadeService.findAllGroups(paginator)
        response.contentType = grailsApplication.config.grails.mime.types[format]
        response.setHeader("Content-disposition", "attachment; filename=group.xls")
        exportService.export(format, response.outputStream,result.list, [:], [:])
    }

    *//**
     * 上传用户组excel
     *//*
    def upload = {
        println "========================进入方法========================="
        def f = ((MultipartHttpServletRequest)request).getFile('group')
        if (f.empty) {
            flash.message = 'file cannot be empty'
            render "该文件是空文件"
            return
        }
        println "========================上传文件不为空========================="

        CommonExcelImport<UserDTO> commonExcelImport = new CommonExcelImport<UserDTO>(f.inputStream , GROUP_USER_CLOUMN_PROPERTIES)
        def groupUserDTOList = commonExcelImport.getModelList(GroupUserDTO.class)

        batchGroupUserHandler(groupUserDTOList)

        render (contentType: "application/json"){
            msg = "操作成功"
            data = groupUserDTOList
        }
    }

    private batchGroupUserHandler(List groupUserDTOList){
        groupUserDTOList.each {GroupUserDTO groupUserDTO ->
            if(StringUtils.isNotEmpty(groupUserDTO.groupName)){
                def groupDTOList = groupAdminFacadeService.findGroupsByName(groupUserDTO.groupName)
                if(groupDTOList.size() == 0){
                    def groupDTO = new GroupDTO(name : groupUserDTO.groupName ,
                    description: groupUserDTO.groupDes , isEnabled: groupUserDTO.isEnabled)
                    groupUserDTO.groupId = groupAdminFacadeService.createGroup(groupDTO)
                }else{
                    println "-----------------------groupDTOList[0].class :    ${groupDTOList[0].class}-------------------------------"
                    groupUserDTO.groupId = groupDTOList[0]["id"]
                }
                if(StringUtils.isNotEmpty(groupUserDTO.userName)){
                    def userDTO = userAdminFacadeService.getUserByUsername(groupUserDTO.userName)
                    if(userDTO != null){
                        groupUserDTO.userId = userDTO.id
                        groupAdminFacadeService.bindingUserWithGroups(groupUserDTO.userId , [groupUserDTO.groupId])
                    }
                }
            }
        }
    }

}
*/