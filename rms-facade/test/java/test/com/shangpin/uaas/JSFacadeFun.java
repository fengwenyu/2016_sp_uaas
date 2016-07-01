package test.com.shangpin.uaas;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/** 
 * ClassName:JSFacadeFun <br/> 
 * TODO 该类的作用<br/>
 * @date    2016年6月22日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class JSFacadeFun {

	String funName;
	List<String> params;
	Class<?> facadeClass;
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer(funName+": function(");
		String param=params.toString();
		String noFacade=facadeClass.getSimpleName();
		noFacade=noFacade.substring(0,noFacade.length()-6);//去掉最后的Facade
		param=param.substring(1,param.length()-1);
		sb.append(param);
		sb.append("){");
		sb.append("var params = ["+param+"];");
        sb.append("var rsl;");
        sb.append("$.ajax({");
            sb.append("type: \"POST\",");
            sb.append("url: \"/uaas/facade/json/"+facadeClass.getPackage().getName()+"/"+noFacade+"/"+funName+"\",");
            sb.append("data: {\"params\": JSON.stringify(params)},");
            sb.append("dataType: \"json\",");
            sb.append("async: false,");
            sb.append("success: function(data) {");
                sb.append("rsl = data;");
            sb.append("},");
            sb.append("error: function(o){");
                sb.append("rsl = o;");
            sb.append("}");
        sb.append("});");
        sb.append("if(rsl.err) throw rsl.err;");
        sb.append("return rsl.val;");
		sb.append("}");
		return sb.toString();
	}
}
