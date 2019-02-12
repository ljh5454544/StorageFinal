package com.jia.storage.service.impl;

import com.jia.storage.entity.Commodity;
import com.jia.storage.entity.Tenant;
import com.jia.storage.service.FileSerice;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileSerivceImpl implements FileSerice {

    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public FileSerivceImpl(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }


    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     *  校验Excel
     * */
    public boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }
    /**
     * 读EXCEL文件，获取客户信息集合
     * @param fileName 文件名称
     * @param Mfile 文件对象
     * @return
     */
    public List<?> getExcelInfo(String fileName,MultipartFile Mfile,String type) throws IOException {

        //在linux根目录下创建一个file文件进行上传的文件保存
        File file = new  File("/file");
        //ClassPathResource classPathResource = new ClassPathResource("file");
        if (!file.exists()) file.mkdirs();
        File filePath = new File(file.getAbsolutePath() , new Date().getTime() + ".xlsx");
        //将上传的文件写入新建的文件中
        System.out.println(filePath.getAbsolutePath());
        Mfile.transferTo(new File(filePath.getAbsolutePath()));

        List<?> list=new ArrayList();
        InputStream is = null;
        try{
            if(!validateExcel(fileName)){
                return null;
            }
            boolean isExcel2003 = true;
            if(isExcel2007(fileName)){
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = new FileInputStream(filePath);
            //根据excel里面的内容读取客户信息
            list = getExcelInfo(is, isExcel2003, type);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public  List<?> getExcelInfo(InputStream is, boolean isExcel2003,String type){
        List<?> list=null;
        try{
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            //当excel是2003时
            if(isExcel2003){
                wb = new HSSFWorkbook(is);
            }
            else{//当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面客户的信息
            list=readExcelValue(wb, type);
        }
        catch (IOException e)  {
            e.printStackTrace();
        }
        return list;
    }



    /**
     *  读取Excel里面的信息
     * */
    private List<?> readExcelValue(Workbook wb, String type) throws UnsupportedEncodingException {
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);

        //得到Excel的行数
        this.totalRows=sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
        }

        if ("comm".equals(type)){
            List<Commodity> list = new ArrayList<Commodity>();
            Commodity customer;
            //循环Excel行数,从第二行开始。标题不入库
            for(int r=1;r<totalRows;r++){
                Row row = sheet.getRow(r);
                if (row == null) continue;
                customer = new Commodity();

                //循环Excel的列
                for(int c = 0; c <this.totalCells; c++){
                    Cell cell = row.getCell(c);
                    if (null != cell){
                        Double price = 0.0;
                        if(c==0){//第一列不读
                        }else if(c==1){
                            customer.setName(cell.getStringCellValue());//商品简称
                        }else if(c==2){
                            customer.setLocation(cell.getStringCellValue()); // 商品生产地
                        }else if(c==3){
                            try {
                                price = cell.getNumericCellValue();
                            } catch (Exception e) {
                                price = 0.0;
                            } finally{
                                customer.setPrice(price);
                            }
                        }
                    }
                }
                //添加客户
                list.add(customer);
            }
            return list;
        }else{
            List<Tenant> list = new ArrayList<Tenant>();
            Tenant tenant;
            for(int r=1;r<totalRows;r++){
                Row row = sheet.getRow(r);
                if (row == null) continue;
                tenant = new Tenant();

                //循环Excel的列
                for(int c = 0; c <this.totalCells; c++){
                    Cell cell = row.getCell(c);
                    if (null != cell){
                        Double price = 0.0;
                        if(c==0){//第一列不读
                        }else if(c==1){
                            tenant.setName(cell.getStringCellValue());//商铺简称
                        }else if(c==2){
                            tenant.setAgent(cell.getStringCellValue()); // 代理人
                        }else if(c==3){
                            tenant.setLocation(cell.getStringCellValue()); // 所在位置
                        }else if(c==4){
                            String phone;
                            try {
                                double value = cell.getNumericCellValue();
                                phone = String.valueOf((long) value);
                                if (phone.length() > 11) {
                                    phone = "";
                                }
                            } catch (IllegalStateException e) {
                                phone = "";
                            }
                            tenant.setTelphone(phone); // 电话
                        }else if(c==5){
                            tenant.setRemark(cell.getStringCellValue()); // 说明
                        }
                    }
                }
                //添加客户
                list.add(tenant);
            }
            return list;

        }
    }

    @Override
    public List<Commodity> commFileRes(MultipartFile file) throws IOException {
        List<Commodity> comm = (List<Commodity>) getExcelInfo(file.getOriginalFilename(), file, "comm");
        return comm;
    }

    @Override
    public List<Tenant> tenantFileRes(MultipartFile file) throws IOException {
        List<Tenant> tenant = (List<Tenant>) getExcelInfo(file.getOriginalFilename(), file, "tenant");
        return tenant;
    }
}
