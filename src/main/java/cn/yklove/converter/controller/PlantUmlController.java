package cn.yklove.converter.controller;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author qinggeng
 */
@RestController
@Slf4j
public class PlantUmlController {

    private final String basePath = "plant-tmp";
    private final String originPath = basePath + "/origin";
    private final Map<String, FileFormatOption> type;
    private final Map<String, String> contextType;

    public PlantUmlController() {
        this.type = new HashMap<>();
        this.type.put("png", new FileFormatOption(FileFormat.PNG));
        this.type.put("svg", new FileFormatOption(FileFormat.SVG));
        this.type.put("jpeg", new FileFormatOption(FileFormat.MJPEG));

        this.contextType = new HashMap<>();
        this.contextType.put("png", MediaType.IMAGE_PNG_VALUE);
        this.contextType.put("svg", "image/svg+xml");
        this.contextType.put("jpeg", MediaType.IMAGE_JPEG_VALUE);

    }

    @PostConstruct
    public void init() throws IOException {
        File originFolder = new File(originPath);
        if (!originFolder.exists()) {
            originFolder.mkdirs();
            log.info("创建文件夹：{}", originFolder.getCanonicalPath());
        }
    }

    @RequestMapping(value = "{type}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] svg(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileFormatOption fileFormatOption = this.type.get(type);
        if (Objects.isNull(fileFormatOption)) {
            return null;
        }
        String mediaType = contextType.get(type);
        if (Strings.isBlank(mediaType)) {
            return null;
        }
        log.info("query = {}", request.getQueryString());
        String decode = URLDecoder.decode(request.getQueryString());
        decode = decode.replaceAll("\\u200B","");
        String originMd5 = DigestUtils.md5DigestAsHex(decode.getBytes());
        File originFile = new File(originPath + "/" + originMd5);
        if (!originFile.exists()) {
            originFile.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(originFile));
            bufferedWriter.write(decode);
            bufferedWriter.close();
        }
        File converterFile = new File(originPath + "/" + originMd5 + "." + type);
        if (!converterFile.exists()) {
            SourceFileReader sourceFileReader = new SourceFileReader(originFile,
                    originFile.getAbsoluteFile().getParentFile(), fileFormatOption);
            List<GeneratedImage> generatedImages = sourceFileReader.getGeneratedImages();
            if (CollectionUtils.isEmpty(generatedImages)) {
                return null;
            }
        }
        FileInputStream inputStream = new FileInputStream(converterFile);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        response.setHeader("Content-Type",mediaType);
        return bytes;
    }

}
