package com.shangpin.uaas.controller.facade;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/*import org.nofdev.servicefacade.AbstractBusinessException;
import org.nofdev.servicefacade.UnhandledException;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shangpin.uaas.api.common.ErrorDTO;
import com.shangpin.uaas.api.common.FacadeDTO;
/*import com.shangpin.http.ExceptionMessage;
import com.shangpin.http.HttpJsonResponse;*/
import com.shangpin.uaas.util.Json2MethodParamUtil;

/**
 * Created by Qiang on 8/6/14.
 */
@RestController
@RequestMapping("/facade")
public class FacadeController {
    private static final Logger logger = LoggerFactory.getLogger(FacadeController.class);

    /* @Autowired
    private ExcepitonSettings excepitonSettings;*/

    @Autowired
    private ApplicationContext context;
    @ResponseBody
    @RequestMapping("/json/{packageName}/{interfaceName}/{methodName}")
    public Object json(@PathVariable String packageName, @PathVariable String interfaceName,
                                                 @PathVariable String methodName,@RequestParam(value="params",required = false) String params,HttpServletRequest request) {
    	logger.info(packageName+  "---c---"+interfaceName +"----m--"+methodName+"-----p------"+params);
        if("com.shangpin.uaas.api".equals(packageName)||"com.shangpin.uaas.api.admin".equals(packageName)){
            switch (interfaceName){
                case "GroupAdmin":
                    packageName="com.shangpin.uaas.api.admin.org";
                    break;
                case "MenuAdmin":
                    packageName="com.shangpin.uaas.api.admin.menu";
                    break;
                case "OrganizationAdmin":
                    packageName="com.shangpin.uaas.api.admin.org";
                    break;
                case "PermissionAdmin":
                    packageName="com.shangpin.uaas.api.admin.permission";
                    break;
                case "ResourceAdmin":
                    packageName="com.shangpin.uaas.api.admin.resource";
                    break;
                case "ResourceNodeAdmin":
                    packageName="com.shangpin.uaas.api.admin.resource";
                    break;
                case "RoleAdmin":
                    packageName="com.shangpin.uaas.api.admin.role";
                    break;
                case "UserAdmin":
                    packageName="com.shangpin.uaas.api.admin.user";
                    break;
                case "Authentication":
                    packageName="com.shangpin.uaas.api.facade.auth";
                    break;
                case "Authorization":
                    packageName="com.shangpin.uaas.api.facade.auth";
                    break;
                case "User":
                    packageName="com.shangpin.uaas.api.facade.user";
                    break;
                default:
                    break;
            }
        }

        if (!interfaceName.endsWith("Facade")) {
            interfaceName += "Facade";
        }
        interfaceName = packageName + '.' + interfaceName;
        FacadeDTO<Object> facadResult= new FacadeDTO<>(UUID.randomUUID().toString());
        logger.info("转换后：--->  "+packageName+  "---c---"+interfaceName +"----m--"+methodName+"-----p------"+params);
        //logger.debug("ask instance for interface {}", interfaceName);
        Object val = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> interfaceClazz = classLoader.loadClass(interfaceName);
            Object service = context.getBean(interfaceClazz);

            Method[] methods = ReflectionUtils.getAllDeclaredMethods(interfaceClazz);
            Method method = null;
            for (Method m : methods) {
                if (methodName.equals(m.getName())) {
                    method = m;
                    break;
                } else {
                    continue;
                }
            }

            if (params!=null && !"null".equals(params)) {
				val = ReflectionUtils.invokeMethod(
						method,
						service,
						Json2MethodParamUtil.gsonDeserialize(params,method.getGenericParameterTypes()).toArray());
            } else {
                val = ReflectionUtils.invokeMethod(method, service);
            }
        }  catch (Exception e) {
            logger.error(e.getMessage(),e);
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setCause(e.getCause()==null?null:e.getCause().toString());
            errorDTO.setMsg(e.getLocalizedMessage());
            errorDTO.setName(e.getClass().getName());
            facadResult.setErr(errorDTO);
        }
      // logger.info("val:---"+val);
        facadResult.setVal(val);
        return facadResult;
    }
    
    
   /* public String name() {
        final endl = System.properties.'line.separator'
        if (params.containsKey("wsdl")) return redirect(action: jsonWsdl, params: params.wsdl ? [lib: params.wsdl] : null)
        def result = [callId: UUID.randomUUID().toString(), val: null, err: null]
        def start = new Date()
        String methodName = ''
        String facadeName = ''
        String packageName = ''
        try {
            facadeName = params.facadeName + 'Facade'
            String facadeBeanName = "${facadeName[0].toLowerCase()}${facadeName[1..-1]}FacadeService"
            methodName = params.methodName
            packageName = params.packageName
            final interfaceName = packageName + '.' + facadeName
            log.debug("ask instance for interface($interfaceName)")
            Class<?> interfaceClazz = Class.forName(interfaceName, true, Thread.currentThread().contextClassLoader)
            def service = ctx.getBean(interfaceClazz)

            String rawParams = params.params.toString()
            log.debug("${endl}JSON facade call(callId:${result.callId}): $interfaceName.$facadeName.$methodName$rawParams")

            if (params.params) {
                Class<?> serviceClazz = service.getClass()
                def unproxiedServiceClazz = serviceClazz.methods.find{it.name.equals("getTargetClass")}?.invoke(service)?:serviceClazz
                Type[] paramTypes = unproxiedServiceClazz.methods.find {it.name.equals(methodName)}.getGenericParameterTypes()
                log.trace "paramTypes: ${paramTypes.inspect()}"

                List methodParams = deserializeParamList(rawParams, paramTypes)
                log.debug "deserialized to: ${methodParams.inspect()}"
                result.val = service."$methodName"(* methodParams)
            }
            else {
                result.val = service."$methodName"()
            }
        }
        catch (e) {
            log.warn("${endl}JSON facade call error: ${e.message}!", e)
            result.err = formatException(e)
        }
        def json = result as JSON
        def end = new Date()
        long millis = end.time - start.time
        def slow = ''
        if(millis > 500) slow = "${endl}SLOW!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!${endl}"
        log.debug("${endl}${packageName}.$facadeName.$methodName result($slow$millis ms$slow): $result ->${endl}$json")
        render(json)
	}
    */
    /*private List deserializeParamList(String rawParams, Type[] paramTypes) {
        def deserializer = new SimpleJSONDeserializer()
        deserializer.paramTypes = paramTypes
        deserializer.deserialize(rawParams)
    }*/
    
    /*def jsonWsdl = {
            List<String> packageNames = grailsApplication.config.grails.plugin.servicefacade.scanlist
            log.debug "Generate JSON WSDL for package: $packageNames"
            //List<PackageMeta> packages = PackageScanner.Scan(packageNames)
            List<Class> classes = []
            packageNames.each { classes.addAll(PackageUtil.getClasses(it)) }
            def serviceInterfaces = classes.findAll {
                it.isInterface() && it.simpleName.endsWith('Facade')
            }
            response.addHeader("Cache-Control", "max-age=43200")
            [services: serviceInterfaces, baseUrl: request.contextPath]
        }*/
    	
	/* private def formatException(Throwable throwable) {
	        if (throwable == null) return null
	        def pretty = [:]
	        pretty.name = throwable.getClass().name
	        pretty.msg = throwable.localizedMessage
	        if (Environment.developmentMode) pretty.stack = throwable.stackTrace.toString()
	        pretty.cause = formatException(throwable.cause)
	        pretty
	    }*/
		
}
