package com.axon.java.stack;

import com.bytedance.douyincloud.configcenter.DycConfigCenter;
import com.bytedance.douyincloud.configcenter.internal.DefaultConfig;

public class Douyin {
    public static void main(String[] args) {
        DefaultConfig config = (DefaultConfig) DycConfigCenter.getDycConfig();
        String res = config.getStringValue("key_name", "default_value");

    }
}
