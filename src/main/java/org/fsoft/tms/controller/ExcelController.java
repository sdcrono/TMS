package org.fsoft.tms.controller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.service.RoleService;
import org.fsoft.tms.service.UserService;
import org.fsoft.tms.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Created by DELL on 6/2/2017.
 */
@Controller
@RequestMapping(value = "/tms/trainees/excel")
public class ExcelController {

    private String FILE_NAME = "";
    private static String UPLOADED_FOLDER = "D://temp//";

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/read")
    @Transactional
    public String readExcel(Model model) {
        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);

            int rowNum = datatypeSheet.getPhysicalNumberOfRows();
            for(int i = 1; i < rowNum; i++) {
                Row row = datatypeSheet.getRow(i);

                User user = new User();
                TraineeInfo traineeInfo = new TraineeInfo();
                Cell cell = row.getCell(0);
                user.setUsername(cell.getStringCellValue());
                user.setPassword(cell.getStringCellValue());
                traineeInfo.setUser(user);
                cell = row.getCell(1);
                traineeInfo.setName(cell.getStringCellValue());
                cell = row.getCell(2);
                traineeInfo.setBirthDate(cell.getStringCellValue());
                cell = row.getCell(3);
                traineeInfo.setEducation(cell.getStringCellValue());
                cell = row.getCell(4);
                traineeInfo.setProgrammingLanguage(cell.getStringCellValue());
                cell = row.getCell(5);
                traineeInfo.setToeicScore(cell.getNumericCellValue()+"");
                cell = row.getCell(6);
                traineeInfo.setExperienceDetail(cell.getStringCellValue());
                cell = row.getCell(7);
                traineeInfo.setDepartment(cell.getStringCellValue());
                cell = row.getCell(8);
                traineeInfo.setLocation(cell.getStringCellValue());
                if(userService.checkUsername(traineeInfo.getUser().getUsername())) {
                    userService.addTrainee(traineeInfo.getUser(), CurrentUser.getInstance().getUser().getId());
                    userService.saveTrainee(traineeInfo);
                }
            }
            model.addAttribute("messages", "Write file successful");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("messages", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("messages", "IOexception: " + e.toString());
        }

        return "excel/uploadStatus";
    }

    @GetMapping("/")
    public String index() {
        return "excel/upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("messages", "Please select a file to upload");
            return "excel/uploadStatus";
        }

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            FILE_NAME = UPLOADED_FOLDER + file.getOriginalFilename();

            Files.write(path, bytes);
            return "redirect:/tms/trainees/excel/read";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("messages",
                    "You failed uploaded '" + file.getOriginalFilename() + "'");
            e.printStackTrace();
        }
        return "excel/uploadStatus";
    }
}
