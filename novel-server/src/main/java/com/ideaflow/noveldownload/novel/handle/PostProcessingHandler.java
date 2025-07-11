package com.ideaflow.noveldownload.novel.handle;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import com.ideaflow.noveldownload.novel.model.Book;
import com.ideaflow.noveldownload.novel.util.FileUtils;

import java.io.File;

import static org.fusesource.jansi.AnsiRenderer.render;


public interface PostProcessingHandler {

    void handle(Book book, File saveDir);

    /**
     * 下载封面失败会导致生成中断，必须捕获异常
     */
    default void downloadCover(Book book, File saveDir) {
        try {
            Console.log("<== 正在下载封面：{}", book.getCoverUrl());
            File coverFile = HttpUtil.downloadFileFromUrl(book.getCoverUrl(), FileUtils.resolvePath(saveDir.toString()));
            FileUtil.rename(coverFile, "0_封面." + FileUtil.getType(coverFile), true);
        } catch (Exception e) {
            Console.error(render("封面下载失败：{}", "red"), e.getMessage());
        }
    }
}

