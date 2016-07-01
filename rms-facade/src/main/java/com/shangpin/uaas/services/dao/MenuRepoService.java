package com.shangpin.uaas.services.dao;

import java.util.List;

import javax.naming.Name;

import com.shangpin.uaas.entity.example.MenuExample;
import com.shangpin.uaas.services.dao.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shangpin.uaas.api.facade.e.APPCode;
import com.shangpin.uaas.entity.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//???
//@Repository
@Transactional
@Service
public class MenuRepoService{
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据id查找订单
     *
     * @param id 订单标识
     * @return
     */
    public Menu findById(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    /**
     * 移动菜单项
     *
     * @param parentId
     */
    /*@Modifying
    @Query("update #{#entityName} set parentId=?2 where uuid=?1")*/
   // public int moveMenu(String currentId, String parentId){return 0;}


    public List<Menu> findByParentId(String parentId) {
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andParentidEqualTo(parentId);
        return menuMapper.selectByExample(example);
    }

    /**
     * 查询所有子级的菜单项
     *
     * @return
     */
  //  public List<Menu> findAllMenuTreeByParentId(Name base) {return null;}


    public  List<Menu> findByAppCodeAndParentId(String appCode, String parentId){
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andAppcodeEqualTo(appCode);
        criteria.andParentidEqualTo(parentId);
        return menuMapper.selectByExample(example);
    }
    public int insert(Menu menu) {
        return menuMapper.insert(menu);
    }
    public int update(Menu menu) {
        return menuMapper.updateByPrimaryKey(menu);
    }
    public int delete(String meunId) {
        return menuMapper.deleteByPrimaryKey(meunId);
    }
    public  List<Menu> findAll() {
        MenuExample example = new MenuExample();
        return menuMapper.selectByExample(example);
    }
    public  List<Menu> findByAppCode(String appCode) {
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andAppcodeEqualTo(appCode);
        return menuMapper.selectByExample(example);
    }
}
