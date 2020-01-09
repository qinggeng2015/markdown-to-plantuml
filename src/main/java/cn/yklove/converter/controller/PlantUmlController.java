package cn.yklove.converter.controller;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author qinggeng
 */
@RestController
@Slf4j
public class PlantUmlController {

    private final String basePath = "plant-tmp";
    private final String originPath = basePath + "/origin";

    @PostConstruct
    public void init() throws IOException {
        File originFolder = new File(originPath);
        if (!originFolder.exists()) {
            originFolder.mkdirs();
            log.info("创建文件夹：{}", originFolder.getCanonicalPath());
        }
    }

    @RequestMapping(value = "svg",produces = "image/svg+xml")
    public byte[] svg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("query = {}", request.getQueryString());
        String decode = URLDecoder.decode(request.getQueryString());
        decode = decode.replaceAll("\\u200B","");
        char[] chars = decode.toCharArray();
        int line = 0;
        for (char charTmp : chars) {
            if(charTmp == '\n'){
                line++;
            }
        }
        if(line == 0){
            // 在连续四个空格前追加换行符
            decode = decode.replaceAll("\\u0020\\u0020\\u0020\\u0020","\n    ");
        }
        String originMd5 = DigestUtils.md5DigestAsHex(decode.getBytes());
        File originFile = new File(originPath + "/" + originMd5);
        if (!originFile.exists()) {
            originFile.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(originFile));
            bufferedWriter.write(decode);
            bufferedWriter.close();
        }
        File converterFile = new File(originPath + "/" + originMd5 + "." + "svg");
        if (!converterFile.exists()) {
            SourceFileReader sourceFileReader = new SourceFileReader(originFile,originFile.getAbsoluteFile().getParentFile(),new FileFormatOption(
                    FileFormat.SVG));
            List<GeneratedImage> generatedImages = sourceFileReader.getGeneratedImages();
            if (CollectionUtils.isEmpty(generatedImages)) {
                return null;
            }
        }
        FileInputStream inputStream = new FileInputStream(converterFile);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

}
