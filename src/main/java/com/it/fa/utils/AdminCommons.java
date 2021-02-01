package com.it.fa.utils;


import com.it.fa.model.Label;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 后台公共函数
 * <p>
 */
@Component
public final class AdminCommons {

    /**
     * 判断文章范畴是否存在标签中
     *
     * @param cats
     * @return
     */
    public static boolean exist_cat(Label category, String cats) {
        String[] arr = StringUtils.split(cats, ",");
        if (null != arr && arr.length > 0) {
            for (String c : arr) {
                if (c.trim().equals(category.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};

    public static String rand_color() {
        int r = Common.random(0,COLORS.length-1);
        return COLORS[r];
    }

}
