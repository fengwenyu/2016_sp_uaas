package test.com.shangpin.uaas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/** 
 * ClassName:TestScanerEntryToMySQl <br/> 
 * TODO 该类的作用<br/>
 * @date    2016年6月9日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
public class FacadeScanner {
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private MetadataReaderFactory metadataReaderFactory =
			new CachingMetadataReaderFactory(this.resourcePatternResolver);
	static String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +"/com/shangpin/uaas/services/**/*FacadeService.class";
	/**
	 *  扫描com/shangpin/uaas/entity/** /*.class,找打有Entry注解的类
	 * <br/>
	 * @return 得到类名
	 */
	public Set<Class<?>> scanFacadeInterface(){
		Set<Class<?>> set = new HashSet<>();
		try {
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for (Resource resource : resources) {
				if(resource.isReadable()){
					MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
					AnnotationMetadata am=metadataReader.getAnnotationMetadata();
					if(am.isConcrete()&& ArrayUtils.isNotEmpty(am.getInterfaceNames())){//facade的interface
						set.add(Class.forName(am.getClassName()));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return set;
	}
	
	public static Map<String,List<String>> getMethodParamNames(Class<?> clazz){
		List<String> paramNames = null;
		Map<String,List<String>> methodParams=new HashMap<>(clazz.getDeclaredMethods().length);
		try{
			ClassPool pool = ClassPool.getDefault();  
			CtClass cc = pool.get(clazz.getName());
			CtMethod[] cms=cc.getDeclaredMethods();
			for (CtMethod cm : cms) {
				if(!Modifier.isPublic(cm.getModifiers()))
					continue;
				MethodInfo methodInfo = cm.getMethodInfo();  
				CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
				LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
				if (attr == null) {  
					// exception  
				}
				int paraSize=cm.getParameterTypes().length;
				paramNames = new ArrayList<>(paraSize);  
				int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
				for (int i = 0; i < paraSize; i++) {
					paramNames.add(attr.variableName(i + pos)); 					
				}
				methodParams.put(methodInfo.getName(), paramNames);
			}
		}catch(Exception e){
			
		}
        return methodParams;
	}
	
	public static JSFacade printJs(Class<?> clz){
		JSFacade f = new JSFacade();
		f.setFacadeName(clz.getInterfaces()[0].getName());
		Map<String,List<String>> paramNames=getMethodParamNames(clz);
		Set<Entry<String, List<String>>> set = paramNames.entrySet();
		Set<JSFacadeFun> funs = new HashSet<>();
		for (Entry<String, List<String>> entry : set) {
			JSFacadeFun fn = new JSFacadeFun();
			fn.setFunName(entry.getKey());
			fn.setFacadeClass(clz.getInterfaces()[0]);
			fn.setParams(entry.getValue());
			funs.add(fn);
		}
		/*Method[] ms=clz.getMethods();
		for (Method method : ms) {
			List<String> paramNames = new ArrayList<>(params.length);
			for (Class<?> class1 : params) {
				String pname=class1.getSimpleName();
				pname=pname.substring(0, 1).toLowerCase()+pname.substring(1,pname.length());
				paramNames.add(pname);
			}
		}*/
		f.setFuns(funs);
		return f;
	}
	
	public static void main(String[] args) {
		FacadeScanner scanner = new FacadeScanner();
		Set<Class<?>> clzs=scanner.scanFacadeInterface();
		List<JSFacade> jsfs = new ArrayList<>(clzs.size());
		for (Class<?> class1 : clzs) {
			jsfs.add(printJs(class1));
		}
		Set<String> parentPackage=new HashSet<>();
		for (JSFacade jsFacade : jsfs) {
			parentPackage.addAll(getParentPgk(jsFacade.getFacadeName()));
		}
		System.out.print("var ");			
		for (String pkg : parentPackage) {
			System.out.println(pkg);			
		}
		
		for (JSFacade jsFacade : jsfs) {
			System.out.println(jsFacade.toString());			
		}
	}

	private static List<String> getParentPgk(String facadeName) {
		String[] pkgs=facadeName.split("\\.");
		List<String> lst=new ArrayList<>(pkgs.length-3);
		String defaultPkg=pkgs[0]+".";
		String p=defaultPkg;
		for (int i = 1; i < pkgs.length-1; i++) {
			p=p+pkgs[i];
			lst.add(p+" = {};");
			p=p+".";
		}
		return lst;
	}
	
	
	
}
