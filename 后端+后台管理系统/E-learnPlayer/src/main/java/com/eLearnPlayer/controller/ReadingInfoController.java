package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.*;
import com.eLearnPlayer.service.ReadingInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author
 * @create 2024-04-17 12:21
 */
@RestController
@RequestMapping(value = "/ReadingInfo")
public class ReadingInfoController {
    @Resource
    private ReadingInfoService readingInfoService;

    /**
     * 分页获取Reading_Passages——web网页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("page/{pageNum}")
    public Result<PageInfo<ReadingPassagesInfoWithGrade>> page(@PathVariable Integer pageNum,
                                                               @RequestParam(defaultValue = "20") Integer pageSize){
        return Result.success(readingInfoService.findPage(pageNum,pageSize));
    }

    /**
     * 根据passageId获取read的文章、问题、选项、答案等——web网页
     * @param passageId
     * @return
     */
    @GetMapping("/details/{passageId}")
    public Result<ReadingData> getReadingDetails(@PathVariable int passageId){
        ReadingData details = readingInfoService.getReadingDetails(passageId);
//        System.out.println(details);
//        System.out.println(details.get(0));
        return Result.success(details);
    }

    /**
     * 通过选中的关卡，水平获得阅读数据（ReadingData封装）——小程序端
     * @param level
     * @param selectedRank
     * @return
     */
    @GetMapping("/getReadingByLevelAndRank")
    public Result<ReadingData> getReadingByLevelAndRank(@RequestParam("level") Integer level, @RequestParam("selectedRank") Integer selectedRank){
        System.out.println("Received request with level: " + level + ", selectedRank: " + selectedRank);
        ReadingData readingDataList = readingInfoService.getReadingByLevelAndRank(level, selectedRank);
        System.out.println(readingDataList);
        return Result.success(readingDataList);
    }

    /**
     *用户上传阅读
     * @return
     */
    @PostMapping("/upLoadReadingData")
    public Result upLoadReadingData(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("level") Integer level,
                                    @RequestParam("answers") String answers){
        try {
            // 读取文件内容
            // 使用Apache POI读取Word文件内容
            InputStream inputStream = file.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream);

            StringBuilder passageBuilder = new StringBuilder();
            List<String> questions = new ArrayList<>();
            List<List<String>> choices = new ArrayList<>();

            boolean readingQuestionsStarted = false;

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String line = paragraph.getText().trim();
                // 如果遇到题目开头的标志（如数字+句点），表示开始读取题目部分
                if (line.matches("^\\d+\\..*")) {
                    readingQuestionsStarted = true;
                }
                // 根据是否读取到题目部分，将内容分别放入文章和题目列表中
                if (!readingQuestionsStarted) {
                    passageBuilder.append(line).append("\n");
//                    System.out.println("passageBuilder:"+passageBuilder);
                } else {
                    if (!line.isEmpty()) {
                        if (line.matches("^\\d+\\..*")) {
                            // 题目开始
                            questions.add(line.substring(line.indexOf('.') + 2).trim());
                            choices.add(new ArrayList<>());//新的选项列表
                        } else {
                            // 选项
                            int lastQuestionIndex = questions.size() - 1;
                            choices.get(lastQuestionIndex).add(line);
                        }
                    }
                }
            }

            System.out.println("passageBuilder:"+passageBuilder);
            System.out.println("___________________________________________________________:");
            System.out.println("questions:"+questions);
            System.out.println("choices:"+choices);

            // 处理答案字符串
            List<String> extractedAnswers = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\d+\\.([A-Za-z])");//数字+.+A/a
            Matcher matcher = pattern.matcher(answers);
            while (matcher.find()) {
                extractedAnswers.add(matcher.group(1));
            }
            System.out.println("——————————————————————————————");
            System.out.println("extractedAnswers:"+extractedAnswers);


            //封装
            ReadingData readingData = new ReadingData();

            ReadingPassagesInfo passage = new ReadingPassagesInfo();
            passage.setPassageText(String.valueOf(passageBuilder));
            passage.setPassageTitle(title);
            passage.setLevel(level);
//            System.out.println(passage);

            List<ReadingQuestionsInfo> questionList = new ArrayList<>();
            List<ReadingChoicesInfo> choiceList = new ArrayList<>();
            for (int i = 0; i<questions.size();i++){
                ReadingQuestionsInfo readingQuestionsInfo = new ReadingQuestionsInfo();
                readingQuestionsInfo.setPassageText(questions.get(i));
                readingQuestionsInfo.setCorrectAnswer(extractedAnswers.get(i));
                questionList.add(readingQuestionsInfo);
                List<String> choice = choices.get(i);
                for (int j =0;j<4;j++){
                    ReadingChoicesInfo readingChoicesInfo = new ReadingChoicesInfo();
                    readingChoicesInfo.setChoiceIndex(j+1);
                    readingChoicesInfo.setChoiceText(choice.get(j));
                    choiceList.add(readingChoicesInfo);
                }
            }

            readingData.setPassage(passage);
            readingData.setQuestions(questionList);
            readingData.setChoices(choiceList);

            System.out.println(readingData);
            System.out.println("——————————————————————————————");

            readingInfoService.upDataReadingData(readingData);

            return Result.success();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error();
    }

}
