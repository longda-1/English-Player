package com.eLearnPlayer.controller;

import com.eLearnPlayer.common.Result;
import com.eLearnPlayer.entity.ListeningAudioInfo;
import com.eLearnPlayer.entity.ListeningChoicesInfo;
import com.eLearnPlayer.entity.ListeningData;
import com.eLearnPlayer.entity.ListeningQuestionsInfo;
import com.eLearnPlayer.service.ListeningInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author
 * @create 2024-04-16 16:02
 */
@RestController
@RequestMapping(value = "/ListeningInfo")
public class ListeningInfoController {
    @Resource
    private ListeningInfoService listeningInfoService;

    /**
     * 从后端获取ListeningData——小程序
     * @param level
     * @param selectedRank
     * @return
     */
    @GetMapping("/getListeningByLevelAndRank")
    public Result<ListeningData> getListeningByLevelAndRank(@RequestParam("level") Integer level, @RequestParam("selectedRank") Integer selectedRank){
        System.out.println("Received request with level: " + level + ", selectedRank: " + selectedRank);
        ListeningData listeningDataList = listeningInfoService.getListeningByLevelAndRank(level,selectedRank);
        System.out.println(listeningDataList);
        return Result.success(listeningDataList);
    }

    @GetMapping("page/{pageNum}")
    public Result<PageInfo<ListeningData>> page(@PathVariable Integer pageNum,
                                                               @RequestParam(defaultValue = "20") Integer pageSize){
        return Result.success(listeningInfoService.findPage(pageNum,pageSize));
    }

    @GetMapping("/getListeningData/{audioId}")
    public Result<ListeningData> getListeningData(@PathVariable int audioId) {
        ListeningData listeningData = listeningInfoService.getListeningData(audioId);
        return Result.success(listeningData);
    }

    /**
     *用户上传听力
     * @return
     */
    @PostMapping("/upLoadListeningData")
    public Result upLoadListeningData(@RequestParam("audioFile") MultipartFile audioFile,
                                      @RequestParam("wordFile") MultipartFile wordFile,
                                      @RequestParam("title") String title,
                                      @RequestParam("level") Integer level,
                                      @RequestParam("answers") String answers) {
        try {
            String audioFileName = audioFile.getOriginalFilename(); // 获取音频文件名
            audioFileName = "e-Audio\\"+audioFileName;
            //System.out.println("audioFileName"+audioFileName);
            // 读取文件内容
            // 使用Apache POI读取Word文件内容
            InputStream inputStream = wordFile.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream);

            StringBuilder passageBuilder = new StringBuilder();
            List<String> questions = new ArrayList<>();
            List<List<String>> choices = new ArrayList<>();

            boolean listeningQuestionsStarted = false;

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String line = paragraph.getText().trim();
                if (line.matches("^\\d+\\..*")) {
                    listeningQuestionsStarted = true;
                }
                if (!listeningQuestionsStarted) {
                    passageBuilder.append(line).append("\n");
                    System.out.println("passageBuilder:"+passageBuilder);
                } else {
                    if (!line.isEmpty()) {
                        if (line.matches("^\\d+\\..*")) {
                            // 题目开始
                            questions.add(line.substring(line.indexOf('.') + 2).trim());
                            choices.add(new ArrayList<>());
                        }else {
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
            Pattern pattern = Pattern.compile("\\d+\\.([A-Za-z])");
            Matcher matcher = pattern.matcher(answers);
            while (matcher.find()) {
                extractedAnswers.add(matcher.group(1));
            }
            System.out.println("——————————————————————————————");
            System.out.println("extractedAnswers:"+extractedAnswers);

            //封装
            ListeningData listeningData = new ListeningData();

            ListeningAudioInfo audioInfo = new ListeningAudioInfo();
            audioInfo.setAudioTitle(title);
            audioInfo.setAudioFile(audioFileName);
            audioInfo.setLevel(level);

            List<ListeningQuestionsInfo> questionList = new ArrayList<>();
            List<ListeningChoicesInfo> choiceList = new ArrayList<>();

            for (int i = 0; i < questions.size(); i++) {
                ListeningQuestionsInfo question = new ListeningQuestionsInfo();

                if (questions.get(i) == null || "null".equals(questions.get(i))){
                    question.setPassageText(i+1+".");
                }else {
                    question.setPassageText(questions.get(i));
                }

                question.setCorrectAnswer(extractedAnswers.get(i));
                questionList.add(question);

                List<String> choice = choices.get(i);
                for (int j = 0; j < choice.size(); j++) {
                    ListeningChoicesInfo choiceInfo = new ListeningChoicesInfo();
                    choiceInfo.setChoiceIndex(j + 1);
                    choiceInfo.setChoiceText(choice.get(j));
                    choiceList.add(choiceInfo);
                }
            }

            listeningData.setAudioInfo(audioInfo);
            listeningData.setQuestions(questionList);
            listeningData.setChoices(choiceList);

            System.out.println(listeningData);
            System.out.println("——————————————————————————————");

            listeningInfoService.upDataListeningData(listeningData);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.error();
    }
}
