package com.ideaflow.noveldownload.novel.handle;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.ideaflow.noveldownload.novel.model.AppConfig;
import com.ideaflow.noveldownload.novel.model.Book;
import com.ideaflow.noveldownload.novel.util.FileUtils;
import lombok.AllArgsConstructor;

import java.io.File;
import java.util.List;


@AllArgsConstructor
public class TxtMergeHandler implements PostProcessingHandler {

    private final AppConfig config;

    @Override
    public void handle(Book book, File saveDir) {
        String outputPath = StrUtil.format("{}{}({}).txt",
                config.getDownloadPath() + File.separator, book.getBookName(), book.getAuthor());
        // 删除旧的同名 txt 文件
        FileUtil.del(outputPath);

        File outputFile = FileUtil.isAbsolutePath(outputPath)
                ? FileUtil.touch(outputPath)
                : FileUtil.touch(System.getProperty("user.dir"), outputPath);
        FileAppender appender = new FileAppender(outputFile, 16, true);
        List<String> info = List.of(
                StrUtil.format("书名：{}", book.getBookName()),
                StrUtil.format("作者：{}", book.getAuthor()),
                StrUtil.format("简介：{}\n", StrUtil.isEmpty(book.getIntro()) ? "暂无" : HtmlUtil.cleanHtmlTag(book.getIntro()))
        );
        // 首页添加书籍信息
        info.forEach(appender::append);

        for (File f : FileUtils.sortFilesByName(saveDir)) {
            appender.append(FileUtil.readUtf8String(f));
        }
        appender.flush();

        downloadCover(book, saveDir);
    }

}