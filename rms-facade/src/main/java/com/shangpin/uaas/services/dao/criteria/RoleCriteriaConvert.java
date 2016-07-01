package com.shangpin.uaas.services.dao.criteria;

/**
 * ClassName:UserCriteriaConvert <br/>
 * 
 * @date 2016年6月11日 <br/>
 * @author 陈小峰
 * @since JDK 7
 */
public class RoleCriteriaConvert {

	static final String LIKE = "%";

	/*public static Specification<Role> toUser(final RoleCriteria roleCriteria) {
		Specification<Role> u = new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				String nameLike = roleCriteria.getNameLike();

				if (StringUtils.isNotEmpty(nameLike)) {
					list.add(cb.like(root.get("name").as(String.class), LIKE + nameLike + LIKE));
				}
				if (null != roleCriteria.getIsEnabled()) {
					list.add(cb.equal(root.get("status").as(Boolean.class), roleCriteria.getIsEnabled()));
				}
				Predicate[] pre = new Predicate[list.size()];
				query.where(list.toArray(pre));
				return query.getRestriction();
			}
		};
		return null;
	}*/
}
