package com.biubiu.weixinapp.service;


import com.biubiu.weixinapp.dto.AnalysisDTO;
import com.biubiu.weixinapp.enums.ChannelType;
import com.biubiu.weixinapp.model.DouYinModel;
import com.biubiu.weixinapp.utils.AnalysisUtils;
import com.google.common.collect.ImmutableMap;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：张音乐
 * @date ：Created in 2021/4/13 上午9:26
 * @description：视频业务处理
 * @email: zhangyule1993@sina.com
 * @version: 1.0
 */
@Service
public class VideoService {


    /**
     * 二次解析后返回地址就好了, 是否需要下载让前端去选择
     * @param analysisDTO
     * @return
     */
    public Map<String, String> analysis(AnalysisDTO analysisDTO)  {
        String text = analysisDTO.getText();
        String code = analysisDTO.getCode();
        ChannelType channel = ChannelType.getByCode(code);
        if(channel == null) {
            return null;
        }
        DouYinModel addrModel = AnalysisUtils.douYin(text);
        if(addrModel == null) {
            return null;
        }
        return ImmutableMap.of("video", addrModel.getVideoAddr(), "cover", addrModel.getCoverAddr());
    }


    public void download(String playUrl, HttpServletResponse response) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Connection", "keep-alive");
        headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        BufferedInputStream in = Jsoup.connect(playUrl).headers(headers).timeout(10000).ignoreContentType(true).execute().bodyStream();
        OutputStream out = response.getOutputStream();
        // 重置response
        response.reset();
        // ContentType，即告诉客户端所发送的数据属于什么类型
        response.setContentType("application/octet-stream; charset=UTF-8");
        // 设置编码格式
        response.setHeader("Content-Disposition", "attachment;fileName=" + UUID.randomUUID().toString().replace("-", "") + ".mp4");
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        out.close();//关闭输出流
        in.close(); //关闭输入流
    }

}