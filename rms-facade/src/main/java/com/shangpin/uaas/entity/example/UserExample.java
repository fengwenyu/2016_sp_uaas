package com.shangpin.uaas.entity.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNull() {
            addCriterion("realName is null");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("realName is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("realName =", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("realName <>", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("realName >", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("realName >=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("realName <", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("realName <=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("realName like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("realName not like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameIn(List<String> values) {
            addCriterion("realName in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotIn(List<String> values) {
            addCriterion("realName not in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("realName between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("realName not between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(String value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(String value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(String value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(String value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(String value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(String value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLike(String value) {
            addCriterion("gender like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotLike(String value) {
            addCriterion("gender not like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<String> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<String> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(String value1, String value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(String value1, String value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andUsercodeIsNull() {
            addCriterion("userCode is null");
            return (Criteria) this;
        }

        public Criteria andUsercodeIsNotNull() {
            addCriterion("userCode is not null");
            return (Criteria) this;
        }

        public Criteria andUsercodeEqualTo(String value) {
            addCriterion("userCode =", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeNotEqualTo(String value) {
            addCriterion("userCode <>", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeGreaterThan(String value) {
            addCriterion("userCode >", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeGreaterThanOrEqualTo(String value) {
            addCriterion("userCode >=", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeLessThan(String value) {
            addCriterion("userCode <", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeLessThanOrEqualTo(String value) {
            addCriterion("userCode <=", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeLike(String value) {
            addCriterion("userCode like", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeNotLike(String value) {
            addCriterion("userCode not like", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeIn(List<String> values) {
            addCriterion("userCode in", values, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeNotIn(List<String> values) {
            addCriterion("userCode not in", values, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeBetween(String value1, String value2) {
            addCriterion("userCode between", value1, value2, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeNotBetween(String value1, String value2) {
            addCriterion("userCode not between", value1, value2, "usercode");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNull() {
            addCriterion("createdTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNotNull() {
            addCriterion("createdTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeEqualTo(Date value) {
            addCriterion("createdTime =", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotEqualTo(Date value) {
            addCriterion("createdTime <>", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThan(Date value) {
            addCriterion("createdTime >", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createdTime >=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThan(Date value) {
            addCriterion("createdTime <", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThanOrEqualTo(Date value) {
            addCriterion("createdTime <=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIn(List<Date> values) {
            addCriterion("createdTime in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotIn(List<Date> values) {
            addCriterion("createdTime not in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeBetween(Date value1, Date value2) {
            addCriterion("createdTime between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotBetween(Date value1, Date value2) {
            addCriterion("createdTime not between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIsNull() {
            addCriterion("updatedTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIsNotNull() {
            addCriterion("updatedTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeEqualTo(Date value) {
            addCriterion("updatedTime =", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotEqualTo(Date value) {
            addCriterion("updatedTime <>", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeGreaterThan(Date value) {
            addCriterion("updatedTime >", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updatedTime >=", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeLessThan(Date value) {
            addCriterion("updatedTime <", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeLessThanOrEqualTo(Date value) {
            addCriterion("updatedTime <=", value, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeIn(List<Date> values) {
            addCriterion("updatedTime in", values, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotIn(List<Date> values) {
            addCriterion("updatedTime not in", values, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeBetween(Date value1, Date value2) {
            addCriterion("updatedTime between", value1, value2, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andUpdatedtimeNotBetween(Date value1, Date value2) {
            addCriterion("updatedTime not between", value1, value2, "updatedtime");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andTelephoneIsNull() {
            addCriterion("telephone is null");
            return (Criteria) this;
        }

        public Criteria andTelephoneIsNotNull() {
            addCriterion("telephone is not null");
            return (Criteria) this;
        }

        public Criteria andTelephoneEqualTo(String value) {
            addCriterion("telephone =", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotEqualTo(String value) {
            addCriterion("telephone <>", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneGreaterThan(String value) {
            addCriterion("telephone >", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneGreaterThanOrEqualTo(String value) {
            addCriterion("telephone >=", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLessThan(String value) {
            addCriterion("telephone <", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLessThanOrEqualTo(String value) {
            addCriterion("telephone <=", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLike(String value) {
            addCriterion("telephone like", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotLike(String value) {
            addCriterion("telephone not like", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneIn(List<String> values) {
            addCriterion("telephone in", values, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotIn(List<String> values) {
            addCriterion("telephone not in", values, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneBetween(String value1, String value2) {
            addCriterion("telephone between", value1, value2, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotBetween(String value1, String value2) {
            addCriterion("telephone not between", value1, value2, "telephone");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIsNull() {
            addCriterion("organizationId is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIsNotNull() {
            addCriterion("organizationId is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationidEqualTo(String value) {
            addCriterion("organizationId =", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotEqualTo(String value) {
            addCriterion("organizationId <>", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidGreaterThan(String value) {
            addCriterion("organizationId >", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidGreaterThanOrEqualTo(String value) {
            addCriterion("organizationId >=", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLessThan(String value) {
            addCriterion("organizationId <", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLessThanOrEqualTo(String value) {
            addCriterion("organizationId <=", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidLike(String value) {
            addCriterion("organizationId like", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotLike(String value) {
            addCriterion("organizationId not like", value, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidIn(List<String> values) {
            addCriterion("organizationId in", values, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotIn(List<String> values) {
            addCriterion("organizationId not in", values, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidBetween(String value1, String value2) {
            addCriterion("organizationId between", value1, value2, "organizationid");
            return (Criteria) this;
        }

        public Criteria andOrganizationidNotBetween(String value1, String value2) {
            addCriterion("organizationId not between", value1, value2, "organizationid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidIsNull() {
            addCriterion("directLeaderId is null");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidIsNotNull() {
            addCriterion("directLeaderId is not null");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidEqualTo(String value) {
            addCriterion("directLeaderId =", value, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidNotEqualTo(String value) {
            addCriterion("directLeaderId <>", value, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidGreaterThan(String value) {
            addCriterion("directLeaderId >", value, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidGreaterThanOrEqualTo(String value) {
            addCriterion("directLeaderId >=", value, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidLessThan(String value) {
            addCriterion("directLeaderId <", value, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidLessThanOrEqualTo(String value) {
            addCriterion("directLeaderId <=", value, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidLike(String value) {
            addCriterion("directLeaderId like", value, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidNotLike(String value) {
            addCriterion("directLeaderId not like", value, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidIn(List<String> values) {
            addCriterion("directLeaderId in", values, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidNotIn(List<String> values) {
            addCriterion("directLeaderId not in", values, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidBetween(String value1, String value2) {
            addCriterion("directLeaderId between", value1, value2, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andDirectleaderidNotBetween(String value1, String value2) {
            addCriterion("directLeaderId not between", value1, value2, "directleaderid");
            return (Criteria) this;
        }

        public Criteria andJoblevelIsNull() {
            addCriterion("jobLevel is null");
            return (Criteria) this;
        }

        public Criteria andJoblevelIsNotNull() {
            addCriterion("jobLevel is not null");
            return (Criteria) this;
        }

        public Criteria andJoblevelEqualTo(String value) {
            addCriterion("jobLevel =", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelNotEqualTo(String value) {
            addCriterion("jobLevel <>", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelGreaterThan(String value) {
            addCriterion("jobLevel >", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelGreaterThanOrEqualTo(String value) {
            addCriterion("jobLevel >=", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelLessThan(String value) {
            addCriterion("jobLevel <", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelLessThanOrEqualTo(String value) {
            addCriterion("jobLevel <=", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelLike(String value) {
            addCriterion("jobLevel like", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelNotLike(String value) {
            addCriterion("jobLevel not like", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelIn(List<String> values) {
            addCriterion("jobLevel in", values, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelNotIn(List<String> values) {
            addCriterion("jobLevel not in", values, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelBetween(String value1, String value2) {
            addCriterion("jobLevel between", value1, value2, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelNotBetween(String value1, String value2) {
            addCriterion("jobLevel not between", value1, value2, "joblevel");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andWorkplaceIsNull() {
            addCriterion("workplace is null");
            return (Criteria) this;
        }

        public Criteria andWorkplaceIsNotNull() {
            addCriterion("workplace is not null");
            return (Criteria) this;
        }

        public Criteria andWorkplaceEqualTo(String value) {
            addCriterion("workplace =", value, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceNotEqualTo(String value) {
            addCriterion("workplace <>", value, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceGreaterThan(String value) {
            addCriterion("workplace >", value, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceGreaterThanOrEqualTo(String value) {
            addCriterion("workplace >=", value, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceLessThan(String value) {
            addCriterion("workplace <", value, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceLessThanOrEqualTo(String value) {
            addCriterion("workplace <=", value, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceLike(String value) {
            addCriterion("workplace like", value, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceNotLike(String value) {
            addCriterion("workplace not like", value, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceIn(List<String> values) {
            addCriterion("workplace in", values, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceNotIn(List<String> values) {
            addCriterion("workplace not in", values, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceBetween(String value1, String value2) {
            addCriterion("workplace between", value1, value2, "workplace");
            return (Criteria) this;
        }

        public Criteria andWorkplaceNotBetween(String value1, String value2) {
            addCriterion("workplace not between", value1, value2, "workplace");
            return (Criteria) this;
        }

        public Criteria andBirthIsNull() {
            addCriterion("birth is null");
            return (Criteria) this;
        }

        public Criteria andBirthIsNotNull() {
            addCriterion("birth is not null");
            return (Criteria) this;
        }

        public Criteria andBirthEqualTo(Date value) {
            addCriterionForJDBCDate("birth =", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotEqualTo(Date value) {
            addCriterionForJDBCDate("birth <>", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThan(Date value) {
            addCriterionForJDBCDate("birth >", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth >=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThan(Date value) {
            addCriterionForJDBCDate("birth <", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth <=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthIn(List<Date> values) {
            addCriterionForJDBCDate("birth in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotIn(List<Date> values) {
            addCriterionForJDBCDate("birth not in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth not between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andJobtitleIsNull() {
            addCriterion("jobTitle is null");
            return (Criteria) this;
        }

        public Criteria andJobtitleIsNotNull() {
            addCriterion("jobTitle is not null");
            return (Criteria) this;
        }

        public Criteria andJobtitleEqualTo(String value) {
            addCriterion("jobTitle =", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleNotEqualTo(String value) {
            addCriterion("jobTitle <>", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleGreaterThan(String value) {
            addCriterion("jobTitle >", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleGreaterThanOrEqualTo(String value) {
            addCriterion("jobTitle >=", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleLessThan(String value) {
            addCriterion("jobTitle <", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleLessThanOrEqualTo(String value) {
            addCriterion("jobTitle <=", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleLike(String value) {
            addCriterion("jobTitle like", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleNotLike(String value) {
            addCriterion("jobTitle not like", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleIn(List<String> values) {
            addCriterion("jobTitle in", values, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleNotIn(List<String> values) {
            addCriterion("jobTitle not in", values, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleBetween(String value1, String value2) {
            addCriterion("jobTitle between", value1, value2, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleNotBetween(String value1, String value2) {
            addCriterion("jobTitle not between", value1, value2, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andEntrydateIsNull() {
            addCriterion("entryDate is null");
            return (Criteria) this;
        }

        public Criteria andEntrydateIsNotNull() {
            addCriterion("entryDate is not null");
            return (Criteria) this;
        }

        public Criteria andEntrydateEqualTo(String value) {
            addCriterion("entryDate =", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateNotEqualTo(String value) {
            addCriterion("entryDate <>", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateGreaterThan(String value) {
            addCriterion("entryDate >", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateGreaterThanOrEqualTo(String value) {
            addCriterion("entryDate >=", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateLessThan(String value) {
            addCriterion("entryDate <", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateLessThanOrEqualTo(String value) {
            addCriterion("entryDate <=", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateLike(String value) {
            addCriterion("entryDate like", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateNotLike(String value) {
            addCriterion("entryDate not like", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateIn(List<String> values) {
            addCriterion("entryDate in", values, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateNotIn(List<String> values) {
            addCriterion("entryDate not in", values, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateBetween(String value1, String value2) {
            addCriterion("entryDate between", value1, value2, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateNotBetween(String value1, String value2) {
            addCriterion("entryDate not between", value1, value2, "entrydate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateIsNull() {
            addCriterion("jobTitleDate is null");
            return (Criteria) this;
        }

        public Criteria andJobtitledateIsNotNull() {
            addCriterion("jobTitleDate is not null");
            return (Criteria) this;
        }

        public Criteria andJobtitledateEqualTo(String value) {
            addCriterion("jobTitleDate =", value, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateNotEqualTo(String value) {
            addCriterion("jobTitleDate <>", value, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateGreaterThan(String value) {
            addCriterion("jobTitleDate >", value, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateGreaterThanOrEqualTo(String value) {
            addCriterion("jobTitleDate >=", value, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateLessThan(String value) {
            addCriterion("jobTitleDate <", value, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateLessThanOrEqualTo(String value) {
            addCriterion("jobTitleDate <=", value, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateLike(String value) {
            addCriterion("jobTitleDate like", value, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateNotLike(String value) {
            addCriterion("jobTitleDate not like", value, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateIn(List<String> values) {
            addCriterion("jobTitleDate in", values, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateNotIn(List<String> values) {
            addCriterion("jobTitleDate not in", values, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateBetween(String value1, String value2) {
            addCriterion("jobTitleDate between", value1, value2, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andJobtitledateNotBetween(String value1, String value2) {
            addCriterion("jobTitleDate not between", value1, value2, "jobtitledate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateIsNull() {
            addCriterion("contractStartDate is null");
            return (Criteria) this;
        }

        public Criteria andContractstartdateIsNotNull() {
            addCriterion("contractStartDate is not null");
            return (Criteria) this;
        }

        public Criteria andContractstartdateEqualTo(String value) {
            addCriterion("contractStartDate =", value, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateNotEqualTo(String value) {
            addCriterion("contractStartDate <>", value, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateGreaterThan(String value) {
            addCriterion("contractStartDate >", value, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateGreaterThanOrEqualTo(String value) {
            addCriterion("contractStartDate >=", value, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateLessThan(String value) {
            addCriterion("contractStartDate <", value, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateLessThanOrEqualTo(String value) {
            addCriterion("contractStartDate <=", value, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateLike(String value) {
            addCriterion("contractStartDate like", value, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateNotLike(String value) {
            addCriterion("contractStartDate not like", value, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateIn(List<String> values) {
            addCriterion("contractStartDate in", values, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateNotIn(List<String> values) {
            addCriterion("contractStartDate not in", values, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateBetween(String value1, String value2) {
            addCriterion("contractStartDate between", value1, value2, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andContractstartdateNotBetween(String value1, String value2) {
            addCriterion("contractStartDate not between", value1, value2, "contractstartdate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateIsNull() {
            addCriterion("probationEndDate is null");
            return (Criteria) this;
        }

        public Criteria andProbationenddateIsNotNull() {
            addCriterion("probationEndDate is not null");
            return (Criteria) this;
        }

        public Criteria andProbationenddateEqualTo(String value) {
            addCriterion("probationEndDate =", value, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateNotEqualTo(String value) {
            addCriterion("probationEndDate <>", value, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateGreaterThan(String value) {
            addCriterion("probationEndDate >", value, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateGreaterThanOrEqualTo(String value) {
            addCriterion("probationEndDate >=", value, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateLessThan(String value) {
            addCriterion("probationEndDate <", value, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateLessThanOrEqualTo(String value) {
            addCriterion("probationEndDate <=", value, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateLike(String value) {
            addCriterion("probationEndDate like", value, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateNotLike(String value) {
            addCriterion("probationEndDate not like", value, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateIn(List<String> values) {
            addCriterion("probationEndDate in", values, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateNotIn(List<String> values) {
            addCriterion("probationEndDate not in", values, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateBetween(String value1, String value2) {
            addCriterion("probationEndDate between", value1, value2, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andProbationenddateNotBetween(String value1, String value2) {
            addCriterion("probationEndDate not between", value1, value2, "probationenddate");
            return (Criteria) this;
        }

        public Criteria andPositivedateIsNull() {
            addCriterion("positiveDate is null");
            return (Criteria) this;
        }

        public Criteria andPositivedateIsNotNull() {
            addCriterion("positiveDate is not null");
            return (Criteria) this;
        }

        public Criteria andPositivedateEqualTo(String value) {
            addCriterion("positiveDate =", value, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateNotEqualTo(String value) {
            addCriterion("positiveDate <>", value, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateGreaterThan(String value) {
            addCriterion("positiveDate >", value, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateGreaterThanOrEqualTo(String value) {
            addCriterion("positiveDate >=", value, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateLessThan(String value) {
            addCriterion("positiveDate <", value, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateLessThanOrEqualTo(String value) {
            addCriterion("positiveDate <=", value, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateLike(String value) {
            addCriterion("positiveDate like", value, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateNotLike(String value) {
            addCriterion("positiveDate not like", value, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateIn(List<String> values) {
            addCriterion("positiveDate in", values, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateNotIn(List<String> values) {
            addCriterion("positiveDate not in", values, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateBetween(String value1, String value2) {
            addCriterion("positiveDate between", value1, value2, "positivedate");
            return (Criteria) this;
        }

        public Criteria andPositivedateNotBetween(String value1, String value2) {
            addCriterion("positiveDate not between", value1, value2, "positivedate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateIsNull() {
            addCriterion("firstContractEndDate is null");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateIsNotNull() {
            addCriterion("firstContractEndDate is not null");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateEqualTo(String value) {
            addCriterion("firstContractEndDate =", value, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateNotEqualTo(String value) {
            addCriterion("firstContractEndDate <>", value, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateGreaterThan(String value) {
            addCriterion("firstContractEndDate >", value, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateGreaterThanOrEqualTo(String value) {
            addCriterion("firstContractEndDate >=", value, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateLessThan(String value) {
            addCriterion("firstContractEndDate <", value, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateLessThanOrEqualTo(String value) {
            addCriterion("firstContractEndDate <=", value, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateLike(String value) {
            addCriterion("firstContractEndDate like", value, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateNotLike(String value) {
            addCriterion("firstContractEndDate not like", value, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateIn(List<String> values) {
            addCriterion("firstContractEndDate in", values, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateNotIn(List<String> values) {
            addCriterion("firstContractEndDate not in", values, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateBetween(String value1, String value2) {
            addCriterion("firstContractEndDate between", value1, value2, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andFirstcontractenddateNotBetween(String value1, String value2) {
            addCriterion("firstContractEndDate not between", value1, value2, "firstcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateIsNull() {
            addCriterion("secondContractEndDate is null");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateIsNotNull() {
            addCriterion("secondContractEndDate is not null");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateEqualTo(String value) {
            addCriterion("secondContractEndDate =", value, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateNotEqualTo(String value) {
            addCriterion("secondContractEndDate <>", value, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateGreaterThan(String value) {
            addCriterion("secondContractEndDate >", value, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateGreaterThanOrEqualTo(String value) {
            addCriterion("secondContractEndDate >=", value, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateLessThan(String value) {
            addCriterion("secondContractEndDate <", value, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateLessThanOrEqualTo(String value) {
            addCriterion("secondContractEndDate <=", value, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateLike(String value) {
            addCriterion("secondContractEndDate like", value, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateNotLike(String value) {
            addCriterion("secondContractEndDate not like", value, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateIn(List<String> values) {
            addCriterion("secondContractEndDate in", values, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateNotIn(List<String> values) {
            addCriterion("secondContractEndDate not in", values, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateBetween(String value1, String value2) {
            addCriterion("secondContractEndDate between", value1, value2, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andSecondcontractenddateNotBetween(String value1, String value2) {
            addCriterion("secondContractEndDate not between", value1, value2, "secondcontractenddate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateIsNull() {
            addCriterion("turnoverDate is null");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateIsNotNull() {
            addCriterion("turnoverDate is not null");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateEqualTo(String value) {
            addCriterion("turnoverDate =", value, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateNotEqualTo(String value) {
            addCriterion("turnoverDate <>", value, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateGreaterThan(String value) {
            addCriterion("turnoverDate >", value, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateGreaterThanOrEqualTo(String value) {
            addCriterion("turnoverDate >=", value, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateLessThan(String value) {
            addCriterion("turnoverDate <", value, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateLessThanOrEqualTo(String value) {
            addCriterion("turnoverDate <=", value, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateLike(String value) {
            addCriterion("turnoverDate like", value, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateNotLike(String value) {
            addCriterion("turnoverDate not like", value, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateIn(List<String> values) {
            addCriterion("turnoverDate in", values, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateNotIn(List<String> values) {
            addCriterion("turnoverDate not in", values, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateBetween(String value1, String value2) {
            addCriterion("turnoverDate between", value1, value2, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andTurnoverdateNotBetween(String value1, String value2) {
            addCriterion("turnoverDate not between", value1, value2, "turnoverdate");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusIsNull() {
            addCriterion("employeeStatus is null");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusIsNotNull() {
            addCriterion("employeeStatus is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusEqualTo(String value) {
            addCriterion("employeeStatus =", value, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusNotEqualTo(String value) {
            addCriterion("employeeStatus <>", value, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusGreaterThan(String value) {
            addCriterion("employeeStatus >", value, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusGreaterThanOrEqualTo(String value) {
            addCriterion("employeeStatus >=", value, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusLessThan(String value) {
            addCriterion("employeeStatus <", value, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusLessThanOrEqualTo(String value) {
            addCriterion("employeeStatus <=", value, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusLike(String value) {
            addCriterion("employeeStatus like", value, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusNotLike(String value) {
            addCriterion("employeeStatus not like", value, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusIn(List<String> values) {
            addCriterion("employeeStatus in", values, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusNotIn(List<String> values) {
            addCriterion("employeeStatus not in", values, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusBetween(String value1, String value2) {
            addCriterion("employeeStatus between", value1, value2, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andEmployeestatusNotBetween(String value1, String value2) {
            addCriterion("employeeStatus not between", value1, value2, "employeestatus");
            return (Criteria) this;
        }

        public Criteria andIdentityIsNull() {
            addCriterion("identity is null");
            return (Criteria) this;
        }

        public Criteria andIdentityIsNotNull() {
            addCriterion("identity is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityEqualTo(String value) {
            addCriterion("identity =", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotEqualTo(String value) {
            addCriterion("identity <>", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityGreaterThan(String value) {
            addCriterion("identity >", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityGreaterThanOrEqualTo(String value) {
            addCriterion("identity >=", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLessThan(String value) {
            addCriterion("identity <", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLessThanOrEqualTo(String value) {
            addCriterion("identity <=", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLike(String value) {
            addCriterion("identity like", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotLike(String value) {
            addCriterion("identity not like", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityIn(List<String> values) {
            addCriterion("identity in", values, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotIn(List<String> values) {
            addCriterion("identity not in", values, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityBetween(String value1, String value2) {
            addCriterion("identity between", value1, value2, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotBetween(String value1, String value2) {
            addCriterion("identity not between", value1, value2, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberIsNull() {
            addCriterion("identityNumber is null");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberIsNotNull() {
            addCriterion("identityNumber is not null");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberEqualTo(String value) {
            addCriterion("identityNumber =", value, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberNotEqualTo(String value) {
            addCriterion("identityNumber <>", value, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberGreaterThan(String value) {
            addCriterion("identityNumber >", value, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberGreaterThanOrEqualTo(String value) {
            addCriterion("identityNumber >=", value, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberLessThan(String value) {
            addCriterion("identityNumber <", value, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberLessThanOrEqualTo(String value) {
            addCriterion("identityNumber <=", value, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberLike(String value) {
            addCriterion("identityNumber like", value, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberNotLike(String value) {
            addCriterion("identityNumber not like", value, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberIn(List<String> values) {
            addCriterion("identityNumber in", values, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberNotIn(List<String> values) {
            addCriterion("identityNumber not in", values, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberBetween(String value1, String value2) {
            addCriterion("identityNumber between", value1, value2, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andIdentitynumberNotBetween(String value1, String value2) {
            addCriterion("identityNumber not between", value1, value2, "identitynumber");
            return (Criteria) this;
        }

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Short value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Short value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Short value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Short value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Short value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Short value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Short> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Short> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Short value1, Short value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Short value1, Short value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationalityIsNull() {
            addCriterion("nationality is null");
            return (Criteria) this;
        }

        public Criteria andNationalityIsNotNull() {
            addCriterion("nationality is not null");
            return (Criteria) this;
        }

        public Criteria andNationalityEqualTo(String value) {
            addCriterion("nationality =", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityNotEqualTo(String value) {
            addCriterion("nationality <>", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityGreaterThan(String value) {
            addCriterion("nationality >", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityGreaterThanOrEqualTo(String value) {
            addCriterion("nationality >=", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityLessThan(String value) {
            addCriterion("nationality <", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityLessThanOrEqualTo(String value) {
            addCriterion("nationality <=", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityLike(String value) {
            addCriterion("nationality like", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityNotLike(String value) {
            addCriterion("nationality not like", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityIn(List<String> values) {
            addCriterion("nationality in", values, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityNotIn(List<String> values) {
            addCriterion("nationality not in", values, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityBetween(String value1, String value2) {
            addCriterion("nationality between", value1, value2, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityNotBetween(String value1, String value2) {
            addCriterion("nationality not between", value1, value2, "nationality");
            return (Criteria) this;
        }

        public Criteria andEducationIsNull() {
            addCriterion("education is null");
            return (Criteria) this;
        }

        public Criteria andEducationIsNotNull() {
            addCriterion("education is not null");
            return (Criteria) this;
        }

        public Criteria andEducationEqualTo(String value) {
            addCriterion("education =", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotEqualTo(String value) {
            addCriterion("education <>", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThan(String value) {
            addCriterion("education >", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThanOrEqualTo(String value) {
            addCriterion("education >=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThan(String value) {
            addCriterion("education <", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThanOrEqualTo(String value) {
            addCriterion("education <=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLike(String value) {
            addCriterion("education like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotLike(String value) {
            addCriterion("education not like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationIn(List<String> values) {
            addCriterion("education in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotIn(List<String> values) {
            addCriterion("education not in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationBetween(String value1, String value2) {
            addCriterion("education between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotBetween(String value1, String value2) {
            addCriterion("education not between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("degree is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("degree is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("degree =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("degree <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("degree >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("degree >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("degree <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("degree <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("degree like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("degree not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("degree in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("degree not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("degree between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("degree not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andLearningtypeIsNull() {
            addCriterion("learningType is null");
            return (Criteria) this;
        }

        public Criteria andLearningtypeIsNotNull() {
            addCriterion("learningType is not null");
            return (Criteria) this;
        }

        public Criteria andLearningtypeEqualTo(String value) {
            addCriterion("learningType =", value, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeNotEqualTo(String value) {
            addCriterion("learningType <>", value, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeGreaterThan(String value) {
            addCriterion("learningType >", value, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeGreaterThanOrEqualTo(String value) {
            addCriterion("learningType >=", value, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeLessThan(String value) {
            addCriterion("learningType <", value, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeLessThanOrEqualTo(String value) {
            addCriterion("learningType <=", value, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeLike(String value) {
            addCriterion("learningType like", value, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeNotLike(String value) {
            addCriterion("learningType not like", value, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeIn(List<String> values) {
            addCriterion("learningType in", values, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeNotIn(List<String> values) {
            addCriterion("learningType not in", values, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeBetween(String value1, String value2) {
            addCriterion("learningType between", value1, value2, "learningtype");
            return (Criteria) this;
        }

        public Criteria andLearningtypeNotBetween(String value1, String value2) {
            addCriterion("learningType not between", value1, value2, "learningtype");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationIsNull() {
            addCriterion("politicalAffiliation is null");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationIsNotNull() {
            addCriterion("politicalAffiliation is not null");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationEqualTo(String value) {
            addCriterion("politicalAffiliation =", value, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationNotEqualTo(String value) {
            addCriterion("politicalAffiliation <>", value, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationGreaterThan(String value) {
            addCriterion("politicalAffiliation >", value, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationGreaterThanOrEqualTo(String value) {
            addCriterion("politicalAffiliation >=", value, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationLessThan(String value) {
            addCriterion("politicalAffiliation <", value, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationLessThanOrEqualTo(String value) {
            addCriterion("politicalAffiliation <=", value, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationLike(String value) {
            addCriterion("politicalAffiliation like", value, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationNotLike(String value) {
            addCriterion("politicalAffiliation not like", value, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationIn(List<String> values) {
            addCriterion("politicalAffiliation in", values, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationNotIn(List<String> values) {
            addCriterion("politicalAffiliation not in", values, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationBetween(String value1, String value2) {
            addCriterion("politicalAffiliation between", value1, value2, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffiliationNotBetween(String value1, String value2) {
            addCriterion("politicalAffiliation not between", value1, value2, "politicalaffiliation");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateIsNull() {
            addCriterion("workStartDate is null");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateIsNotNull() {
            addCriterion("workStartDate is not null");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateEqualTo(String value) {
            addCriterion("workStartDate =", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateNotEqualTo(String value) {
            addCriterion("workStartDate <>", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateGreaterThan(String value) {
            addCriterion("workStartDate >", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateGreaterThanOrEqualTo(String value) {
            addCriterion("workStartDate >=", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateLessThan(String value) {
            addCriterion("workStartDate <", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateLessThanOrEqualTo(String value) {
            addCriterion("workStartDate <=", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateLike(String value) {
            addCriterion("workStartDate like", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateNotLike(String value) {
            addCriterion("workStartDate not like", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateIn(List<String> values) {
            addCriterion("workStartDate in", values, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateNotIn(List<String> values) {
            addCriterion("workStartDate not in", values, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateBetween(String value1, String value2) {
            addCriterion("workStartDate between", value1, value2, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateNotBetween(String value1, String value2) {
            addCriterion("workStartDate not between", value1, value2, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeIsNull() {
            addCriterion("graduationTime is null");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeIsNotNull() {
            addCriterion("graduationTime is not null");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeEqualTo(String value) {
            addCriterion("graduationTime =", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeNotEqualTo(String value) {
            addCriterion("graduationTime <>", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeGreaterThan(String value) {
            addCriterion("graduationTime >", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeGreaterThanOrEqualTo(String value) {
            addCriterion("graduationTime >=", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeLessThan(String value) {
            addCriterion("graduationTime <", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeLessThanOrEqualTo(String value) {
            addCriterion("graduationTime <=", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeLike(String value) {
            addCriterion("graduationTime like", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeNotLike(String value) {
            addCriterion("graduationTime not like", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeIn(List<String> values) {
            addCriterion("graduationTime in", values, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeNotIn(List<String> values) {
            addCriterion("graduationTime not in", values, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeBetween(String value1, String value2) {
            addCriterion("graduationTime between", value1, value2, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeNotBetween(String value1, String value2) {
            addCriterion("graduationTime not between", value1, value2, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterIsNull() {
            addCriterion("familyRegister is null");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterIsNotNull() {
            addCriterion("familyRegister is not null");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterEqualTo(String value) {
            addCriterion("familyRegister =", value, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterNotEqualTo(String value) {
            addCriterion("familyRegister <>", value, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterGreaterThan(String value) {
            addCriterion("familyRegister >", value, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterGreaterThanOrEqualTo(String value) {
            addCriterion("familyRegister >=", value, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterLessThan(String value) {
            addCriterion("familyRegister <", value, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterLessThanOrEqualTo(String value) {
            addCriterion("familyRegister <=", value, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterLike(String value) {
            addCriterion("familyRegister like", value, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterNotLike(String value) {
            addCriterion("familyRegister not like", value, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterIn(List<String> values) {
            addCriterion("familyRegister in", values, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterNotIn(List<String> values) {
            addCriterion("familyRegister not in", values, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterBetween(String value1, String value2) {
            addCriterion("familyRegister between", value1, value2, "familyregister");
            return (Criteria) this;
        }

        public Criteria andFamilyregisterNotBetween(String value1, String value2) {
            addCriterion("familyRegister not between", value1, value2, "familyregister");
            return (Criteria) this;
        }

        public Criteria andForeignenglishIsNull() {
            addCriterion("foreignEnglish is null");
            return (Criteria) this;
        }

        public Criteria andForeignenglishIsNotNull() {
            addCriterion("foreignEnglish is not null");
            return (Criteria) this;
        }

        public Criteria andForeignenglishEqualTo(String value) {
            addCriterion("foreignEnglish =", value, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishNotEqualTo(String value) {
            addCriterion("foreignEnglish <>", value, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishGreaterThan(String value) {
            addCriterion("foreignEnglish >", value, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishGreaterThanOrEqualTo(String value) {
            addCriterion("foreignEnglish >=", value, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishLessThan(String value) {
            addCriterion("foreignEnglish <", value, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishLessThanOrEqualTo(String value) {
            addCriterion("foreignEnglish <=", value, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishLike(String value) {
            addCriterion("foreignEnglish like", value, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishNotLike(String value) {
            addCriterion("foreignEnglish not like", value, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishIn(List<String> values) {
            addCriterion("foreignEnglish in", values, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishNotIn(List<String> values) {
            addCriterion("foreignEnglish not in", values, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishBetween(String value1, String value2) {
            addCriterion("foreignEnglish between", value1, value2, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignenglishNotBetween(String value1, String value2) {
            addCriterion("foreignEnglish not between", value1, value2, "foreignenglish");
            return (Criteria) this;
        }

        public Criteria andForeignrussianIsNull() {
            addCriterion("foreignRussian is null");
            return (Criteria) this;
        }

        public Criteria andForeignrussianIsNotNull() {
            addCriterion("foreignRussian is not null");
            return (Criteria) this;
        }

        public Criteria andForeignrussianEqualTo(String value) {
            addCriterion("foreignRussian =", value, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianNotEqualTo(String value) {
            addCriterion("foreignRussian <>", value, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianGreaterThan(String value) {
            addCriterion("foreignRussian >", value, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianGreaterThanOrEqualTo(String value) {
            addCriterion("foreignRussian >=", value, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianLessThan(String value) {
            addCriterion("foreignRussian <", value, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianLessThanOrEqualTo(String value) {
            addCriterion("foreignRussian <=", value, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianLike(String value) {
            addCriterion("foreignRussian like", value, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianNotLike(String value) {
            addCriterion("foreignRussian not like", value, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianIn(List<String> values) {
            addCriterion("foreignRussian in", values, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianNotIn(List<String> values) {
            addCriterion("foreignRussian not in", values, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianBetween(String value1, String value2) {
            addCriterion("foreignRussian between", value1, value2, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignrussianNotBetween(String value1, String value2) {
            addCriterion("foreignRussian not between", value1, value2, "foreignrussian");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchIsNull() {
            addCriterion("foreignFrench is null");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchIsNotNull() {
            addCriterion("foreignFrench is not null");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchEqualTo(String value) {
            addCriterion("foreignFrench =", value, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchNotEqualTo(String value) {
            addCriterion("foreignFrench <>", value, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchGreaterThan(String value) {
            addCriterion("foreignFrench >", value, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchGreaterThanOrEqualTo(String value) {
            addCriterion("foreignFrench >=", value, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchLessThan(String value) {
            addCriterion("foreignFrench <", value, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchLessThanOrEqualTo(String value) {
            addCriterion("foreignFrench <=", value, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchLike(String value) {
            addCriterion("foreignFrench like", value, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchNotLike(String value) {
            addCriterion("foreignFrench not like", value, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchIn(List<String> values) {
            addCriterion("foreignFrench in", values, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchNotIn(List<String> values) {
            addCriterion("foreignFrench not in", values, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchBetween(String value1, String value2) {
            addCriterion("foreignFrench between", value1, value2, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignfrenchNotBetween(String value1, String value2) {
            addCriterion("foreignFrench not between", value1, value2, "foreignfrench");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanIsNull() {
            addCriterion("foreignKorean is null");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanIsNotNull() {
            addCriterion("foreignKorean is not null");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanEqualTo(String value) {
            addCriterion("foreignKorean =", value, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanNotEqualTo(String value) {
            addCriterion("foreignKorean <>", value, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanGreaterThan(String value) {
            addCriterion("foreignKorean >", value, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanGreaterThanOrEqualTo(String value) {
            addCriterion("foreignKorean >=", value, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanLessThan(String value) {
            addCriterion("foreignKorean <", value, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanLessThanOrEqualTo(String value) {
            addCriterion("foreignKorean <=", value, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanLike(String value) {
            addCriterion("foreignKorean like", value, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanNotLike(String value) {
            addCriterion("foreignKorean not like", value, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanIn(List<String> values) {
            addCriterion("foreignKorean in", values, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanNotIn(List<String> values) {
            addCriterion("foreignKorean not in", values, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanBetween(String value1, String value2) {
            addCriterion("foreignKorean between", value1, value2, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignkoreanNotBetween(String value1, String value2) {
            addCriterion("foreignKorean not between", value1, value2, "foreignkorean");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseIsNull() {
            addCriterion("foreignJapanese is null");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseIsNotNull() {
            addCriterion("foreignJapanese is not null");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseEqualTo(String value) {
            addCriterion("foreignJapanese =", value, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseNotEqualTo(String value) {
            addCriterion("foreignJapanese <>", value, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseGreaterThan(String value) {
            addCriterion("foreignJapanese >", value, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseGreaterThanOrEqualTo(String value) {
            addCriterion("foreignJapanese >=", value, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseLessThan(String value) {
            addCriterion("foreignJapanese <", value, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseLessThanOrEqualTo(String value) {
            addCriterion("foreignJapanese <=", value, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseLike(String value) {
            addCriterion("foreignJapanese like", value, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseNotLike(String value) {
            addCriterion("foreignJapanese not like", value, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseIn(List<String> values) {
            addCriterion("foreignJapanese in", values, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseNotIn(List<String> values) {
            addCriterion("foreignJapanese not in", values, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseBetween(String value1, String value2) {
            addCriterion("foreignJapanese between", value1, value2, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andForeignjapaneseNotBetween(String value1, String value2) {
            addCriterion("foreignJapanese not between", value1, value2, "foreignjapanese");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusIsNull() {
            addCriterion("maritalStatus is null");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusIsNotNull() {
            addCriterion("maritalStatus is not null");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusEqualTo(String value) {
            addCriterion("maritalStatus =", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusNotEqualTo(String value) {
            addCriterion("maritalStatus <>", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusGreaterThan(String value) {
            addCriterion("maritalStatus >", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusGreaterThanOrEqualTo(String value) {
            addCriterion("maritalStatus >=", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusLessThan(String value) {
            addCriterion("maritalStatus <", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusLessThanOrEqualTo(String value) {
            addCriterion("maritalStatus <=", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusLike(String value) {
            addCriterion("maritalStatus like", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusNotLike(String value) {
            addCriterion("maritalStatus not like", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusIn(List<String> values) {
            addCriterion("maritalStatus in", values, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusNotIn(List<String> values) {
            addCriterion("maritalStatus not in", values, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusBetween(String value1, String value2) {
            addCriterion("maritalStatus between", value1, value2, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusNotBetween(String value1, String value2) {
            addCriterion("maritalStatus not between", value1, value2, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andBirthplaceIsNull() {
            addCriterion("birthPlace is null");
            return (Criteria) this;
        }

        public Criteria andBirthplaceIsNotNull() {
            addCriterion("birthPlace is not null");
            return (Criteria) this;
        }

        public Criteria andBirthplaceEqualTo(String value) {
            addCriterion("birthPlace =", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceNotEqualTo(String value) {
            addCriterion("birthPlace <>", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceGreaterThan(String value) {
            addCriterion("birthPlace >", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceGreaterThanOrEqualTo(String value) {
            addCriterion("birthPlace >=", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceLessThan(String value) {
            addCriterion("birthPlace <", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceLessThanOrEqualTo(String value) {
            addCriterion("birthPlace <=", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceLike(String value) {
            addCriterion("birthPlace like", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceNotLike(String value) {
            addCriterion("birthPlace not like", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceIn(List<String> values) {
            addCriterion("birthPlace in", values, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceNotIn(List<String> values) {
            addCriterion("birthPlace not in", values, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceBetween(String value1, String value2) {
            addCriterion("birthPlace between", value1, value2, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceNotBetween(String value1, String value2) {
            addCriterion("birthPlace not between", value1, value2, "birthplace");
            return (Criteria) this;
        }

        public Criteria andSalarybankIsNull() {
            addCriterion("salaryBank is null");
            return (Criteria) this;
        }

        public Criteria andSalarybankIsNotNull() {
            addCriterion("salaryBank is not null");
            return (Criteria) this;
        }

        public Criteria andSalarybankEqualTo(String value) {
            addCriterion("salaryBank =", value, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankNotEqualTo(String value) {
            addCriterion("salaryBank <>", value, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankGreaterThan(String value) {
            addCriterion("salaryBank >", value, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankGreaterThanOrEqualTo(String value) {
            addCriterion("salaryBank >=", value, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankLessThan(String value) {
            addCriterion("salaryBank <", value, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankLessThanOrEqualTo(String value) {
            addCriterion("salaryBank <=", value, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankLike(String value) {
            addCriterion("salaryBank like", value, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankNotLike(String value) {
            addCriterion("salaryBank not like", value, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankIn(List<String> values) {
            addCriterion("salaryBank in", values, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankNotIn(List<String> values) {
            addCriterion("salaryBank not in", values, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankBetween(String value1, String value2) {
            addCriterion("salaryBank between", value1, value2, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybankNotBetween(String value1, String value2) {
            addCriterion("salaryBank not between", value1, value2, "salarybank");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberIsNull() {
            addCriterion("salaryBankNumber is null");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberIsNotNull() {
            addCriterion("salaryBankNumber is not null");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberEqualTo(String value) {
            addCriterion("salaryBankNumber =", value, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberNotEqualTo(String value) {
            addCriterion("salaryBankNumber <>", value, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberGreaterThan(String value) {
            addCriterion("salaryBankNumber >", value, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberGreaterThanOrEqualTo(String value) {
            addCriterion("salaryBankNumber >=", value, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberLessThan(String value) {
            addCriterion("salaryBankNumber <", value, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberLessThanOrEqualTo(String value) {
            addCriterion("salaryBankNumber <=", value, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberLike(String value) {
            addCriterion("salaryBankNumber like", value, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberNotLike(String value) {
            addCriterion("salaryBankNumber not like", value, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberIn(List<String> values) {
            addCriterion("salaryBankNumber in", values, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberNotIn(List<String> values) {
            addCriterion("salaryBankNumber not in", values, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberBetween(String value1, String value2) {
            addCriterion("salaryBankNumber between", value1, value2, "salarybanknumber");
            return (Criteria) this;
        }

        public Criteria andSalarybanknumberNotBetween(String value1, String value2) {
            addCriterion("salaryBankNumber not between", value1, value2, "salarybanknumber");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}