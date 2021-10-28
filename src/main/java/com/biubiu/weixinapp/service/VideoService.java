package com.biubiu.weixinapp.service;


import com.biubiu.weixinapp.dto.AnalysisDTO;
import com.biubiu.weixinapp.enums.ChannelType;
import com.biubiu.weixinapp.model.DouYinModel;
import com.biubiu.weixinapp.utils.AnalysisUtils;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;

import java.util.Map;

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


}