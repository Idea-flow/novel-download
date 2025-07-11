package com.ideaflow.noveldownload.novel.handle;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.ideaflow.noveldownload.novel.model.Book;
import com.ideaflow.noveldownload.novel.util.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class HtmlTocHandler implements PostProcessingHandler {

    @Override
    public void handle(Book book, File saveDir) {
        List<String> strings = new ArrayList<>();
        String regex = "<title>(.*?)</title>";

        strings.add("文件名\t\t章节名");
        List<File> files = FileUtils.sortFilesByName(saveDir);
        int i = Integer.parseInt(StrUtil.subBefore(files.get(0).getName(), "_", false));
        for (File file : files) {
            FileReader fr = FileReader.create(file, StandardCharsets.UTF_8);
            // 获取 <title> 内容
            String title = ReUtil.getGroup1(regex, fr.readString());
            strings.add(StrUtil.format("{}_.html\t\t{}", i++, title));
        }

        File file = FileUtil.touch(saveDir + File.separator, "0_目录.txt");
        FileWriter fw = FileWriter.create(file, StandardCharsets.UTF_8);
        fw.writeLines(strings);

        downloadCover(book, saveDir);
    }

}