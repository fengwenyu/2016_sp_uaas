package test.com.shangpin.uaas;
/** 
 * ClassName:JSFacade <br/> 
 * TODO 该类的作用<br/>
 * @date    2016年6月22日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JSFacade {

	String facadeName;
	Set<JSFacadeFun> funs = new HashSet<>();
	
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer(facadeName);
		sb.append("={");
		String param=funs.toString();
		param=param.substring(1,param.length()-1);
		sb.append(param);
		sb.append("};");
		return sb.toString();
	}
}
