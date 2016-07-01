package com.shangpin.uaas.services.dao;

import java.util.List;

import com.shangpin.uaas.entity.OrganizationUnion;
import com.shangpin.uaas.entity.example.OrganizationExample;
import com.shangpin.uaas.services.dao.mapper.OrganizationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shangpin.uaas.entity.Organization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 机构仓库
 */
//@Repository
//@Transactional
@Service
public class OrganizationRepoService {
    @Autowired
    OrganizationMapper organizationMapper;
    /**
     *
     * @param parentId
     * @return
     */
    public List<Organization> findByParentId(String parentId) {
        OrganizationExample example = new OrganizationExample();
        OrganizationExample.Criteria criteria = example.createCriteria();
        criteria.andParentidEqualTo(parentId);
        return organizationMapper.selectByExample(example);
    }

    /**
     * 更新机构
     *
     * @param organization
     */
    public int update(Organization organization){
        return organizationMapper.updateByPrimaryKey(organization);
    }

    /**
     * 根据机构名称和负责人唯一标识查询 <br />
     * 模糊查询
     * @param orgName
     * @return
     */
    // List<Organization> findByNameContaining(String orgName) {return null;}

    /**
     * 根据部门负责人查找部门
     *
     * @param leaderIds
     * @return
     */
    //List<Organization> findByLeaderIds(String leaderIds) {return null;}



    /**
     * 根据id获取部门
     *
     * @param id
     * @return
     */
    public Organization findById(String id){
        return organizationMapper.selectByPrimaryKey(id);
    }


    /**
     * 移动部门
     *
     * @param organizationId
     * @param parentOrganizationId
     */
   /*@Modifying
   @Query("update #{#entityName} set parentId=?2 where uuid=?1")*/
    //  public void moveOrganization(String organizationId, String parentOrganizationId){}


    /**
     * 根据机构名称获取机构
     *
     * @param orgName
     * @return
     */
    public List<Organization> findByName(String orgName){
        OrganizationExample example = new OrganizationExample();
        OrganizationExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(orgName);
        return organizationMapper.selectByExample(example);
    }

    public List<OrganizationUnion> findByUnionName(String orgName,String reg){
        String[] split = orgName.split(reg);
        OrganizationUnion union = new OrganizationUnion();
        for (int i = 0; i <split.length ; i++) {
            if(StringUtils.isNotBlank(split[i])){
                switch (i){
                    case 0:
                        union.setName1(split[i]);
                        break;
                    case 1:
                        union.setName2(split[i]);
                        break;
                    case 2:
                        union.setName3(split[i]);
                        break;
                    case 3:
                        union.setName4(split[i]);
                        break;
                    default:
                        break;
                }
            }
        }
        return organizationMapper.findByUnionName(union);
    }


    public List<Organization> findByCode(String code) {
        OrganizationExample example = new OrganizationExample();
        OrganizationExample.Criteria criteria = example.createCriteria();
        criteria.andCodeLike("%"+code+"%");
        return organizationMapper.selectByExample(example);
    }
    public List<Organization> findAll(){
        OrganizationExample example = new OrganizationExample();
        return organizationMapper.selectByExample(example);
    }
    public int save(Organization organization){
        return organizationMapper.insert(organization);
    }
    public int delete(String organizationId){
        return organizationMapper.deleteByPrimaryKey(organizationId);
    }
}
