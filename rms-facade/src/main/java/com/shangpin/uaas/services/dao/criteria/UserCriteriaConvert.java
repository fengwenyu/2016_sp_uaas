package com.shangpin.uaas.services.dao.criteria;

/** 
 * ClassName:UserCriteriaConvert <br/> 
 * @date    2016年6月11日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
public class UserCriteriaConvert {

	protected static final String MOBILE_REGX = "\\s*\\+?\\d+\\s*";
	protected static final String MAIL_REGX = "\\s*[_|0-9|a-z|A-Z]+@[0-9|a-z|A-Z]+\\.[a-z|A-Z]+\\s*";
	static final String LIKE="%";
	/*public static Specification<User> toUser(final UserCriteriaDTO uc){
		Specification<User> u = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>(); 
		        String realNameLike = uc.getRealNameLike();
		        Gender gender = uc.getGender();
		        String workLocation = uc.getWorkLocation();
		        Status status = uc.getStatus();
		        String organizationId = uc.getOrganizationId();

		        // strb存储、组装查询条件集合
		        if(StringUtils.isNotEmpty(realNameLike)){
		        	List<Predicate> orlist = new ArrayList<Predicate>();
		            if(realNameLike.matches(MOBILE_REGX)){
		            	orlist.add(cb.like(root.get("mobile").as(String.class), LIKE+realNameLike+LIKE));
		            }else if(realNameLike.matches(MAIL_REGX)){
		            	orlist.add(cb.like(root.get("email").as(String.class), LIKE+realNameLike+LIKE));
		            }
		            Predicate namelike=cb.like(root.get("realName").as(String.class), LIKE+realNameLike+LIKE);
		            if(orlist.size()>1){
		            	Predicate likeOr=null;
		            	orlist.add(namelike);
		            	likeOr = cb.or(orlist.toArray(new Predicate[orlist.size()]));
		            	//likeOr=cb.or(likeOr,namelike);
		            	list.add(likeOr);
		            }else{
		            	list.add(namelike);
		            }
		            
		        }
		        
		        if (null != gender) {
		        	list.add(cb.equal(root.get("gender").as(Gender.class), gender));
		        }
		        if (StringUtils.isNotEmpty(workLocation)) {
		        	list.add(cb.equal(root.get("workplace").as(String.class), workLocation));
		        }
		        if (null != status) {
		        	list.add(cb.equal(root.get("status").as(Boolean.class), status));
		        }
		        if (StringUtils.isNotEmpty(organizationId)) {
		        	list.add(cb.equal(root.get("organizationId").as(Boolean.class), organizationId));
		        }
		        if(StringUtils.isNotEmpty(uc.getOrganizationNameLike())){
		        	list.add(cb.like(root.get("organizationName").as(String.class),
		        			uc.getOrganizationNameLike()));		        	
		        }
		        Predicate[] pre = new Predicate[list.size()];
                query.where(list.toArray(pre));
                return query.getRestriction();
			}
		};
		return null;
	}*/
}
