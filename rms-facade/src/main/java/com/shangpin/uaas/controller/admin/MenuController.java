package com.shangpin.uaas.controller.admin;

import com.shangpin.uaas.api.admin.menu.MenuDTO;
import com.shangpin.uaas.services.admin.MenuAdminFacadeService;
import com.shangpin.uaas.util.ExportExcelUtils;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
@Controller
@RequestMapping("/menu")
class MenuController {
    protected static Logger log = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuAdminFacadeService menuAdminFacadeService;

    // private static String[] RESOURCE_CLOUMN_PROPERTIES = {"type","id","name","uri","description","isEnabled","parentId"};

    private String[] headers = {"appCode","id","name","sort","uri","url"};
    private String[] columns = {"appCode","id","name","sort","uri","url"};
    /**
     * 导出
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/export")
    public String export(HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        String appCode=request.getParameter("appCode");
        List<MenuDTO> menus=new ArrayList<MenuDTO>();
        if(StringUtils.isEmpty(appCode)){
            menus = menuAdminFacadeService.getAllMenus();
        }else{
            menus = menuAdminFacadeService.getAllMenusByAppCode(appCode);
        }
        if (menus!=null && menus.size()>0) {
            //活动导出的值
            for(int i=0;i<menus.size();i++){
                HashMap<String, Object> menusMap=new HashMap<String, Object>();
                menusMap.put("appCode", menus.get(i).getAppCode());
                menusMap.put("id", menus.get(i).getId());
                menusMap.put("name", menus.get(i).getName());
                menusMap.put("sort", menus.get(i).getSort());
                menusMap.put("uri",menus.get(i).getUri());
                menusMap.put("url",menus.get(i).getUrl());
                result.add(menusMap);
            }
            response.setContentType("application/x-download");// 设置为下载application/x-download
            String fileName = "menu";
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");
            OutputStream out = response.getOutputStream();
            ExportExcelUtils.exportExcel(fileName, headers, columns, result, out, "");
            out.close();
        }
        return null;
    }

    /**
     * 导入
     * @param menu
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    public HashMap<String, Object> upload (@RequestParam(value = "menu", required = true) MultipartFile menu, HttpServletRequest request,
                                           HttpServletResponse response)throws Exception {
        HashMap<String, Object> menusMap=new HashMap<String, Object>();
        if (menu==null) {
            menusMap.put("msg", "file cannot be empty");
            return menusMap;
        }
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(menu.getInputStream());
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            List<MenuDTO> rList = new ArrayList<>();
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                MenuDTO mDto = new MenuDTO();
                XSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                mDto.setParentId(hssfRow.getCell(0).getStringCellValue());
                mDto.setId(hssfRow.getCell(1).getStringCellValue());
                mDto.setName(hssfRow.getCell(2).getStringCellValue());
                mDto.setSort(new Float(Float.parseFloat(hssfRow.getCell(3).getStringCellValue())).intValue());
                mDto.setAppCode(hssfRow.getCell(4).getStringCellValue());
                mDto.setUri(hssfRow.getCell(5).getStringCellValue());
                mDto.setUrl(hssfRow.getCell(6).getStringCellValue());
                rList.add(mDto);
            }
            batchMenusHandler(rList);
            menusMap.put("msg", "操作成功");
            menusMap.put("data", rList);
            return menusMap;
        }
        return menusMap;
    }


    /**
     * 保存数据
     * @param MenuDTOList
     * @throws AlreadyBoundException
     * @throws NotFoundException
     */
    private void batchMenusHandler(List<MenuDTO> MenuDTOList) throws AlreadyBoundException, NotFoundException{
        int initSize = MenuDTOList.size();
        int prevPosition = initSize;
        int nextPosition = initSize;
        while(initSize++ == MenuDTOList.size() || prevPosition != nextPosition){
//            println "---------------initSize : $initSize    prevPosition  :  $prevPosition    nextPosition   :    $nextPosition -----------------------------"
            prevPosition = nextPosition;
            Iterator<MenuDTO> it = MenuDTOList.iterator();
            while(it.hasNext()) {
                MenuDTO menuDTO = it.next();
                if (!StringUtils.isEmpty(menuDTO.getUri())) {
                    try {
                        menuAdminFacadeService.createMenu(menuDTO);
                        it.remove();
                    } catch(RuntimeException e){
                        log.debug("======================校验错误信息 ： ${e.getMessage()}=====================================");
                    }
                }
            }
            nextPosition = MenuDTOList.size();
        }
    }
}