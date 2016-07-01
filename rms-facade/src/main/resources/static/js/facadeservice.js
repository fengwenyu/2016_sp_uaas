var com = {};
com.shangpin = {};
com.shangpin.uaas = {};
com.shangpin.uaas.api = {};
com.shangpin.uaas.api.facade = {};
com.shangpin.uaas.api.facade.auth = {};
com.shangpin.uaas.api.facade.user = {};
com.shangpin.uaas.api.admin = {};
com.shangpin.uaas.api.admin.permission = {};
com.shangpin.uaas.api.admin.menu = {};
com.shangpin.uaas.api.admin.user = {};
com.shangpin.uaas.api.admin.resource = {};
com.shangpin.uaas.api.admin.org = {};
com.shangpin.uaas.api.admin.role = {};

com.shangpin.uaas.api.admin.resource.ResourceAdminFacade = {
    modifyUri: function(resourceNodeDTO) {
        var params = [resourceNodeDTO];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceAdmin/modifyUri",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getResourceNode: function(resourceId) {
        var params = [resourceId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceAdmin/getResourceNode",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    createResource: function(resourceNodeDTO) {
        var params = [resourceNodeDTO];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceAdmin/createResource",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    modifyResource: function(resourceNodeDTO) {
        var params = [resourceNodeDTO];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceAdmin/modifyResource",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getSubResourcesByParentResourcesName: function(parentResourceName) {
        var params = [parentResourceName];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceAdmin/getSubResourcesByParentResourcesName",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    deleteResource: function(resourceNodeId) {
        var params = [resourceNodeId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceAdmin/deleteResource",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    modifyEnable: function(resourceNodeId, isEnabled) {
        var params = [resourceNodeId, isEnabled];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceAdmin/modifyEnable",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getAllResources: function() {
        var params = [];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceAdmin/getAllResources",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.facade.auth.AuthorizationFacade = {
    isPermitted: function(token, resourceUris) {
        var params = [token, resourceUris];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.auth/Authorization/isPermitted",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getSubjectByToken: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.auth/Authorization/getSubjectByToken",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.admin.role.RoleAdminFacade = {
    assignRoleToUser: function(roleId, userId) {
        var params = [roleId, userId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/assignRoleToUser",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllRoles: function(paginator) {
        var params = [paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/findAllRoles",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    createRole: function(role) {
        var params = [role];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/createRole",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllRolesByCriteria: function(roleCriteria, paginator) {
        var params = [roleCriteria, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/findAllRolesByCriteria",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getRoleByCode: function(name) {
        var params = [name];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/getRoleByCode",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    modifyRole: function(role) {
        var params = [role];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/modifyRole",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findRolesByUserId: function(userId, paginator) {
        var params = [userId, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/findRolesByUserId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    enabledRole: function(roleId, isEnabled) {
        var params = [roleId, isEnabled];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/enabledRole",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllUsersWithRoleByCriteria: function(criteria, roleId, paginator) {
        var params = [criteria, roleId, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/findAllUsersWithRoleByCriteria",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    createUserRole: function(user, role) {
        var params = [user, role];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/createUserRole",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getRole: function(roleId) {
        var params = [roleId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/getRole",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    modifyRoleAndUserBindings: function(userId, roleIds) {
        var params = [userId, roleIds];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/modifyRoleAndUserBindings",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    deleteRole: function(roleId) {
        var params = [roleId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/deleteRole",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    revokeRoleFromUser: function(roleId, userId) {
        var params = [roleId, userId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/revokeRoleFromUser",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    registerPermission: function(role, resource) {
        var params = [role, resource];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.role/RoleAdmin/registerPermission",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.facade.auth.AuthenticationFacade = {
    logout: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.auth/Authentication/logout",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    isValid: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.auth/Authentication/isValid",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    login: function(userLogin, password) {
        var params = [userLogin, password];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.auth/Authentication/login",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    touch: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.auth/Authentication/touch",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.admin.org.OrganizationAdminFacade = {
    findOrganizationByCode: function(code) {
        var params = [code];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/findOrganizationByCode",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    createOrganization: function(organization) {
        var params = [organization];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/createOrganization",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllOrganizationsByCriteria: function(organizationCriteria, paginator) {
        var params = [organizationCriteria, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/findAllOrganizationsByCriteria",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    deleteOrganization: function(id) {
        var params = [id];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/deleteOrganization",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findOneLevelOrganizationsByParentId: function(parentId) {
        var params = [parentId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/findOneLevelOrganizationsByParentId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getOrganization: function(id) {
        var params = [id];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/getOrganization",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllSubOrganizationsByParentId: function(parentId, paginator) {
        var params = [parentId, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/findAllSubOrganizationsByParentId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    modifyOrganization: function(organization) {
        var params = [organization];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/modifyOrganization",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getAllOrganizations: function() {
        var params = [];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/getAllOrganizations",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    moveOrganization: function(organizationId, parentOrganizationId) {
        var params = [organizationId, parentOrganizationId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/OrganizationAdmin/moveOrganization",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.admin.org.GroupAdminFacade = {
    getGroup: function(id) {
        var params = [id];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/getGroup",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findRolesByGroupId: function(groupId) {
        var params = [groupId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/findRolesByGroupId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    enabledGroup: function(groupId, isEnabled) {
        var params = [groupId, isEnabled];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/enabledGroup",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    updateGroup: function(groupDTO) {
        var params = [groupDTO];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/updateGroup",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllGroups: function(paginator) {
        var params = [paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/findAllGroups",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    addUserToGroup: function(userId, groupId) {
        var params = [userId, groupId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/addUserToGroup",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findGroupsByName: function(name) {
        var params = [name];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/findGroupsByName",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    bindingUserWithGroups: function(userId, groupIds) {
        var params = [userId, groupIds];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/bindingUserWithGroups",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    revokeUserToGroup: function(userId, groupId) {
        var params = [userId, groupId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/revokeUserToGroup",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    bindingRolesWithGroup: function(groupId, roleIds) {
        var params = [groupId, roleIds];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/bindingRolesWithGroup",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findUserByGroupId: function(userCriteriaDTO, groupId, paginator) {
        var params = [userCriteriaDTO, groupId, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/findUserByGroupId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findGroupByUserId: function(userId) {
        var params = [userId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/findGroupByUserId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    createGroup: function(groupDTO) {
        var params = [groupDTO];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.org/GroupAdmin/createGroup",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.admin.menu.MenuAdminFacade = {
    modifyMenu: function(menuDTO) {
        var params = [menuDTO];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.menu/MenuAdmin/modifyMenu",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getAllMenusByAppCode: function(appCode) {
        var params = [appCode];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.menu/MenuAdmin/getAllMenusByAppCode",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findMenusByParentId: function(parentId, paginator) {
        var params = [parentId, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.menu/MenuAdmin/findMenusByParentId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    deleteMenu: function(menuId) {
        var params = [menuId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.menu/MenuAdmin/deleteMenu",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getMenu: function(id) {
        var params = [id];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.menu/MenuAdmin/getMenu",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    createMenu: function(menuDTO) {
        var params = [menuDTO];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.menu/MenuAdmin/createMenu",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    moveMenu: function(menuId, targetMenuId) {
        var params = [menuId, targetMenuId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.menu/MenuAdmin/moveMenu",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getAllMenus: function() {
        var params = [];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.menu/MenuAdmin/getAllMenus",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.facade.user.UserFacade = {
    findAllRolesByToken: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findAllRolesByToken",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findUsersByGroup: function(token, groupId) {
        var params = [token, groupId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findUsersByGroup",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getUserByTokenAndUserId: function(token, userId) {
        var params = [token, userId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/getUserByTokenAndUserId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findMenusByAppCode: function(token, appCode) {
        var params = [token, appCode];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findMenusByAppCode",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findOrganizationByToken: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findOrganizationByToken",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findGroupsByUser: function(token, userId) {
        var params = [token, userId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findGroupsByUser",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllGroups: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findAllGroups",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findMenusByUserIdAndAppCode: function(userId, appCode, token) {
        var params = [userId, appCode, token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findMenusByUserIdAndAppCode",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    changePassword: function(token, oldPassword, newPassword) {
        var params = [token, oldPassword, newPassword];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/changePassword",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findUsersByRoles: function(token, roleIds) {
        var params = [token, roleIds];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findUsersByRoles",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findUsersByOrganizationId: function(token, organizationId) {
        var params = [token, organizationId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findUsersByOrganizationId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getUser: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/getUser",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllOrganizationsByToken: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findAllOrganizationsByToken",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findOrganizationsByTokenAndParentId: function(token, parentId) {
        var params = [token, parentId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findOrganizationsByTokenAndParentId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findTopMenusByToken: function(token) {
        var params = [token];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.facade.user/User/findTopMenusByToken",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.admin.permission.PermissionAdminFacade = {
    bindRoleWithPermissions: function(roleId, resourceNodeIds) {
        var params = [roleId, resourceNodeIds];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.permission/PermissionAdmin/bindRoleWithPermissions",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    bindPermissionWithRoles: function(resourceNodeId, roleIds) {
        var params = [resourceNodeId, roleIds];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.permission/PermissionAdmin/bindPermissionWithRoles",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllPermissionsByRoles: function(roleIds, paginator) {
        var params = [roleIds, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.permission/PermissionAdmin/findAllPermissionsByRoles",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllResourceNodesByRoles: function(roleIds, resourceType, paginator) {
        var params = [roleIds, resourceType, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.permission/PermissionAdmin/findAllResourceNodesByRoles",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllRolesByResourceNode: function(resourceNodeId) {
        var params = [resourceNodeId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.permission/PermissionAdmin/findAllRolesByResourceNode",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.admin.resource.ResourceNodeAdminFacade = {
    findResourceNodeWithFunctionsByParentResourceId: function(parentResourceId) {
        var params = [parentResourceId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceNodeAdmin/findResourceNodeWithFunctionsByParentResourceId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findResourceNodesByRoleId: function(roleIds, paginator) {
        var params = [roleIds, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceNodeAdmin/findResourceNodesByRoleId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findResourceNodesByCriteria: function(resourceNodeCriteriaDTO) {
        var params = [resourceNodeCriteriaDTO];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceNodeAdmin/findResourceNodesByCriteria",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findResourceNodeByParentResourceId: function(parentResourceId) {
        var params = [parentResourceId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.resource/ResourceNodeAdmin/findResourceNodeByParentResourceId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};
com.shangpin.uaas.api.admin.user.UserAdminFacade = {
    getUser: function(userId) {
        var params = [userId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/getUser",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getUserByUserCode: function(userCode) {
        var params = [userCode];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/getUserByUserCode",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllTreeUsersByOrganizationId: function(organizationId, paginator) {
        var params = [organizationId, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/findAllTreeUsersByOrganizationId",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    findAllUsersByCriteria: function(userCriteriaDTO, paginator) {
        var params = [userCriteriaDTO, paginator];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/findAllUsersByCriteria",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    verifyUserName: function(userId, userName) {
        var params = [userId, userName];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/verifyUserName",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    deleteUser: function(userId) {
        var params = [userId];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/deleteUser",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    createUser: function(user) {
        var params = [user];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/createUser",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    modifyUser: function(user) {
        var params = [user];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/modifyUser",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    getUserByUsername: function(username) {
        var params = [username];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/getUserByUsername",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    },
    modifyEnable: function(userId, status) {
        var params = [userId, status];
        var rsl;
        $.ajax({
            type: "POST",
            url: "/facade/json/com.shangpin.uaas.api.admin.user/UserAdmin/modifyEnable",
            data: {
                "params": JSON.stringify(params)
            },
            dataType: "json",
            async: false,
            success: function(data) {
                rsl = data;
            },
            error: function(o) {
                rsl = o;
            }
        });
        if (rsl.err) throw rsl.err;
        return rsl.val;
    }
};