package com.alchemy.gateway.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class NetUtils {

    private static String nodeId;

    static {
        try {
            // 在 Docker 环境下，hostname 等于 docker 容器的 id
            nodeId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            nodeId = UUID.randomUUID().toString();
        }
    }

    /**
     * 获取唯一的机器节点 id
     *
     * @return 唯一的节点 id
     */
    public static String getNodeId() {
        return nodeId;
    }
}
