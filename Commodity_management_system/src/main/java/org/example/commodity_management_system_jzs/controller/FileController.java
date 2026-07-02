package org.example.commodity_management_system_jzs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件管理控制器
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private static final String UPLOAD_DIR = "D:/spring-boot/期末设计/uploads/";

    /**
     * 文件管理页面
     */
    @GetMapping("/list")
    public String listPage(Model model) {
        List<Map<String, Object>> fileList = scanFiles();
        model.addAttribute("files", fileList);
        model.addAttribute("totalCount", fileList.size());
        return "file-list";
    }

    /**
     * 删除文件
     */
    @GetMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String filename) {
        Map<String, Object> result = new HashMap<>();
        try {
            File file = new File(UPLOAD_DIR, filename);
            if (file.exists() && file.delete()) {
                result.put("code", 200);
                result.put("msg", "删除成功");
            } else {
                result.put("code", 500);
                result.put("msg", "文件不存在或删除失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 扫描上传目录中的所有文件
     */
    private List<Map<String, Object>> scanFiles() {
        List<Map<String, Object>> list = new ArrayList<>();
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            return list;
        }
        File[] files = dir.listFiles();
        if (files == null) return list;

        return Arrays.stream(files)
                .filter(File::isFile)
                .sorted((a, b) -> Long.compare(b.lastModified(), a.lastModified()))
                .map(f -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", f.getName());
                    map.put("size", formatSize(f.length()));
                    map.put("url", "/uploads/" + f.getName());
                    map.put("lastModified", LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(f.lastModified()), ZoneId.systemDefault()));
                    map.put("isImage", isImage(f.getName()));
                    return map;
                })
                .collect(Collectors.toList());
    }

    private String formatSize(long size) {
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format("%.1f KB", size / 1024.0);
        return String.format("%.1f MB", size / (1024.0 * 1024.0));
    }

    private boolean isImage(String name) {
        name = name.toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg")
                || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".webp");
    }
}
