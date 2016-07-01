package com.shangpin.uaas.controller.admin;


import com.shangpin.uaas.api.admin.resource.ResourceNodeCriteriaDTO;
import com.shangpin.uaas.api.admin.resource.ResourceNodeDTO;
import com.shangpin.uaas.api.admin.resource.ResourceType;
import com.shangpin.uaas.services.admin.ResourceNodeAdminFacadeService;
import com.shangpin.uaas.util.ExportExcelUtils;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
@Controller
@RequestMapping("/resource")
public class ResourceController {
    protected static Logger log = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private ResourceNodeAdminFacadeService resourceAdminFacadeService;

    @RequestMapping("/export")
    public String export(HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        String resourceName=request.getParameter("resourceName");
        List<ResourceNodeDTO> resources=new ArrayList<ResourceNodeDTO>();
        if(StringUtils.isEmpty(resourceName)){
            resources = resourceAdminFacadeService.findResourceNodesByCriteria(new ResourceNodeCriteriaDTO());
        }else{
            resources = resourceAdminFacadeService.getSubResourcesByParentResourcesName(resourceName);
        }
        if (resources!=null && resources.size()>0) {
            for(int i=0;i<resources.size();i++){
                HashMap<String, Object> menusMap=new HashMap<String, Object>();
                menusMap.put("description", resources.get(i).getDescription());
                menusMap.put("id", resources.get(i).getId());
                menusMap.put("isEnabled", resources.get(i).getIsEnabled());
                menusMap.put("name", resources.get(i).getName());
                menusMap.put("parentId", resources.get(i).getParentId());
                menusMap.put("resourceId",resources.get(i).getResourceId());
                menusMap.put("type",resources.get(i).getType());
                menusMap.put("uri",resources.get(i).getUri());
                result.add(menusMap);
            }
            response.setContentType("application/x-download");// 设置为下载application/x-download
            String fileName = "resources";
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");
            OutputStream out = response.getOutputStream();
            String[] headers = {"description","id","isEnabled","name","parentId","resourceId","type","uri"};
            String[] columns = {"description","id","isEnabled","name","parentId","resourceId","type","uri"};
            ExportExcelUtils.exportExcel(fileName, headers, columns, result, out, "");
            out.close();
        }
        return null;
    }
    @RequestMapping("/upload")
    public HashMap<String, Object> upload (@RequestParam(value = "resource", required = true) MultipartFile resource, HttpServletRequest request,
                                           HttpServletResponse response)throws Exception {
        HashMap<String, Object> menusMap=new HashMap<String, Object>();
        if (resource==null) {
            menusMap.put("msg", "file cannot be empty");
            return menusMap;
        }
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(resource.getInputStream());
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            List<ResourceNodeDTO> rList = new ArrayList<ResourceNodeDTO>();
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                ResourceNodeDTO mDto = new ResourceNodeDTO();
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                DecimalFormat df=new DecimalFormat("0");
                try {
                    mDto.setParentId(hssfRow.getCell(0)==null ? null :df.format(hssfRow.getCell(0).getNumericCellValue()));
                } catch (Exception e) {
                    mDto.setParentId(hssfRow.getCell(0).getStringCellValue());
                }
                try {
                    mDto.setId(hssfRow.getCell(1)==null ? null :df.format(hssfRow.getCell(1).getNumericCellValue()));
                } catch (Exception e) {
                    mDto.setId(hssfRow.getCell(1).getStringCellValue());
                }
                mDto.setName(getValue(hssfRow.getCell(2)));
                ResourceType ec=null;
                if (hssfRow.getCell(3).equals("PAGE")) {
                    ec=ResourceType.PAGE;
                }else if (hssfRow.getCell(3).equals("BUTTON")) {
                    ec=ResourceType.BUTTON;
                }else{
                    ec=ResourceType.MENU;
                }
                mDto.setType(ec);
                mDto.setUri(getValue(hssfRow.getCell(4)));
                mDto.setIsEnabled(hssfRow.getCell(5).getBooleanCellValue());
                mDto.setDescription(getValue(hssfRow.getCell(6)));
                rList.add(mDto);
            }
            batchResourcesHandler(rList);
            menusMap.put("msg", "操作成功");
            menusMap.put("data", rList);
            return menusMap;
        }
        return menusMap;
    }

    private void batchResourcesHandler(List<ResourceNodeDTO> resourceNodeDTOList) throws NotFoundException{
        int initSize = resourceNodeDTOList.size();
        int prevPosition = initSize;
        int nextPosition = initSize;
        while(initSize++ == resourceNodeDTOList.size() || prevPosition != nextPosition){
//            println "---------------initSize : $initSize    prevPosition  :  $prevPosition    nextPosition   :    $nextPosition -----------------------------"
            prevPosition = nextPosition;
            Iterator<ResourceNodeDTO> it = resourceNodeDTOList.iterator();
            while(it.hasNext()) {
                ResourceNodeDTO resourceNodeDTO = it.next();
                if (!StringUtils.isEmpty(resourceNodeDTO.getUri())) {
                    try {
                        log.info("=============节点状态："+resourceNodeDTO.getIsEnabled()+"===============");
                        resourceAdminFacadeService.createResource(resourceNodeDTO);
                        it.remove();
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("======================该资源索引已存在 ： "+resourceNodeDTO.getResourceId()+"=====================================");
                    }
                }
            }
            nextPosition = resourceNodeDTOList.size();
        }
    }

    @SuppressWarnings("static-access")
    public static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

}
